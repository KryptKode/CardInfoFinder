package com.kryptkode.cardinfofinder.util.viewbinding

import android.os.Handler
import android.os.Looper
import android.view.View
import androidx.annotation.MainThread
import androidx.fragment.app.DialogFragment
import androidx.fragment.app.Fragment
import androidx.lifecycle.DefaultLifecycleObserver
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.LifecycleOwner
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
abstract class ViewBindingProperty<in R : Fragment, T : ViewBinding>(
    private val viewBinder: (View) -> T
) : ReadOnlyProperty<R, T> {

    private var binding: T? = null

    protected abstract fun getLifecycleOwner(thisRef: R): LifecycleOwner

    @MainThread
    override fun getValue(thisRef: R, property: KProperty<*>): T {
        val binding = binding
        if (binding != null && thisRef.view === binding.root) {
            return binding
        }

        if (!getLifecycleOwner(thisRef).lifecycle
            .currentState.isAtLeast(Lifecycle.State.INITIALIZED)
        ) {
            throw IllegalStateException(
                "Should not attempt to get bindings when Fragment views are destroyed."
            )
        }

        getLifecycleOwner(thisRef).lifecycle.addObserver(ViewBindingCleanupLifecycleObserver())

        return viewBinder(thisRef.requireView()).also { this.binding = it }
    }

    private inner class ViewBindingCleanupLifecycleObserver : DefaultLifecycleObserver {
        private val mainHandler = Handler(Looper.getMainLooper())

        @MainThread
        override fun onDestroy(owner: LifecycleOwner) {
            owner.lifecycle.removeObserver(this)
            mainHandler.post {
                binding = null
            }
        }
    }
}

private class FragmentViewBindingProperty<F : Fragment, T : ViewBinding>(
    viewBinder: (View) -> T
) : ViewBindingProperty<F, T>(viewBinder) {

    override fun getLifecycleOwner(thisRef: F) = thisRef.viewLifecycleOwner
}

private class DialogFragmentViewBindingProperty<F : DialogFragment, T : ViewBinding>(
    viewBinder: (View) -> T
) : ViewBindingProperty<F, T>(viewBinder) {

    override fun getLifecycleOwner(thisRef: F): LifecycleOwner {
        return if (thisRef.view == null) thisRef.viewLifecycleOwner else thisRef
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
@Suppress("unused")
@JvmName("viewBindingFragment")
fun <F : Fragment, T : ViewBinding> F.viewBinding(viewBinder: (View) -> T):
    ViewBindingProperty<F, T> {
        return FragmentViewBindingProperty(viewBinder)
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
@Suppress("unused")
@JvmName("viewBindingDialogFragment")
fun <F : DialogFragment, T : ViewBinding> F.dialogViewBinding(viewBinder: (View) -> T):
    ViewBindingProperty<F, T> {
        return DialogFragmentViewBindingProperty(viewBinder)
    }
