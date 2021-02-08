package com.kryptkode.cardinfofinder.util.viewbinding

import android.view.View
import androidx.fragment.app.Fragment
import androidx.lifecycle.DefaultLifecycleObserver
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.LifecycleOwner
import androidx.lifecycle.Observer
import androidx.viewbinding.ViewBinding
import kotlin.properties.ReadOnlyProperty
import kotlin.reflect.KProperty

/**
 * This class extends [ReadOnlyProperty] and serves as a delegate helper
 * for binding viewBinding generated classes in [Fragment] subclasses
 *
 * Below is a sample usage with a layout resource file named `sample_fragment`
 * and the generated binding class of `SampleFragmentBinding`.
 *
 * ```
 * class SampleFragment: Fragment(R.layout.sample_fragment) {
 *
 * private val binding by viewBinding(SampleFragmentBinding::bind)
 *
 * override fun onViewCreated(view: View, bundle: Bundle?) {
 *      super.onViewCreated(view, bundle)
 *
 *      binding.sampleButton.setOnClickListener {
 *          showToast("Hello view binding!")
 *      }
 *   }
 *
 * }
 * ```
 * [Source](http://bit.ly/3iz9VKO)
 * */
class FragmentViewBindingProperty<R : Fragment, T : ViewBinding>(
    val fragment: R,
    val viewBindingFactory: (View) -> T
) : ReadOnlyProperty<R, T> {
    private var binding: T? = null

    init {
        fragment.lifecycle.addObserver(
            object : DefaultLifecycleObserver {
                val viewLifecycleOwnerLiveDataObserver =
                    Observer<LifecycleOwner?> {
                        val viewLifecycleOwner = it ?: return@Observer

                        viewLifecycleOwner.lifecycle.addObserver(
                            object : DefaultLifecycleObserver {
                                override fun onDestroy(owner: LifecycleOwner) {
                                    binding = null
                                }
                            }
                        )
                    }

                override fun onCreate(owner: LifecycleOwner) {
                    fragment.viewLifecycleOwnerLiveData.observeForever(viewLifecycleOwnerLiveDataObserver)
                }

                override fun onDestroy(owner: LifecycleOwner) {
                    fragment.viewLifecycleOwnerLiveData.removeObserver(viewLifecycleOwnerLiveDataObserver)
                }
            }
        )
    }

    override fun getValue(thisRef: R, property: KProperty<*>): T {
        val binding = binding
        if (binding != null) {
            return binding
        }

        val lifecycle = fragment.viewLifecycleOwner.lifecycle
        if (!lifecycle.currentState.isAtLeast(Lifecycle.State.INITIALIZED)) {
            throw IllegalStateException("Should not attempt to get bindings when Fragment views are destroyed.")
        }

        return viewBindingFactory(thisRef.requireView()).also { this.binding = it }
    }
}

/**
 * This class extends [ReadOnlyProperty] and serves as a delegate helper
 * for binding viewBinding generated classes in [Fragment] subclasses
 *
 * Below is a sample usage with a layout resource file named `sample_fragment`
 * and the generated binding class of `SampleFragmentBinding`.
 *
 * ```
 * class SampleFragment: Fragment(R.layout.sample_fragment) {
 *
 * private val binding by viewBinding(SampleFragmentBinding::bind)
 *
 * override fun onViewCreated(view: View, bundle: Bundle?) {
 *      super.onViewCreated(view, bundle)
 *
 *      binding.sampleButton.setOnClickListener {
 *          showToast("Hello view binding!")
 *      }
 *   }
 *
 * }
 * ```
 * [Source](http://bit.ly/3iz9VKO)
 * */
@JvmName("viewBindingFragment")
fun <T : ViewBinding> Fragment.viewBinding(viewBindingFactory: (View) -> T) =
    FragmentViewBindingProperty(this, viewBindingFactory)
