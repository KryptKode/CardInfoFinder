package com.kryptkode.cardinfofinder.ui.walkthrough

import android.os.Bundle
import android.view.View
import androidx.fragment.app.Fragment
import androidx.viewpager2.widget.ViewPager2
import com.kryptkode.cardinfofinder.R
import com.kryptkode.cardinfofinder.databinding.FragmentWalkthroughBinding
import com.kryptkode.cardinfofinder.navigator.Navigator
import com.kryptkode.cardinfofinder.util.viewbinding.viewBinding
import dagger.hilt.android.AndroidEntryPoint
import javax.inject.Inject

@AndroidEntryPoint
class WalkThroughFragment : Fragment(R.layout.fragment_walkthrough) {

    @Inject
    lateinit var itemProvider: WalkThroughItemProvider

    @Inject
    lateinit var navigator: Navigator

    private val binding by viewBinding(FragmentWalkthroughBinding::bind)

    private lateinit var adapter: WalkThroughAdapter

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        adapter = WalkThroughAdapter()
        binding.viewPager.adapter = adapter
        binding.viewPager.registerOnPageChangeCallback(
            object : ViewPager2.OnPageChangeCallback() {
                override fun onPageSelected(position: Int) {
                    handlePageChange(position)
                }
            }
        )

        binding.skipButton.setOnClickListener {
            navigator.walkThroughToNext()
        }

        binding.nextButton.setOnClickListener {
            handleNextClick()
        }

        adapter.submitList(itemProvider.getItems())
        binding.pageIndicator.count = adapter.itemCount
    }

    private fun handlePageChange(position: Int) {
        binding.pageIndicator.setSelected(position)
        if (position == adapter.itemCount.minus(1)) {
            binding.nextButton.text = getString(R.string.walkthrough_done)
        } else {
            binding.nextButton.text = getString(R.string.walkthrough_next)
        }
    }

    private fun handleNextClick() {
        val currentPosition = binding.viewPager.currentItem
        if (currentPosition >= adapter.itemCount.minus(1)) {
            navigator.walkThroughToNext()
        } else {
            binding.viewPager.currentItem = currentPosition + 1
        }
    }
}
