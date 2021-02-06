package com.kryptkode.cardinfofinder.ui.cardinfo

import android.os.Bundle
import android.view.View
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import com.kryptkode.cardinfofinder.R
import com.kryptkode.cardinfofinder.databinding.FragmentCardInfoBinding
import com.kryptkode.cardinfofinder.util.viewbinding.viewBinding
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach

@AndroidEntryPoint
class CardInfoFragment : Fragment(R.layout.fragment_card_info) {

    private val viewModel by viewModels<CardInfoViewModel>()
    private val binding by viewBinding(FragmentCardInfoBinding::bind)
    private val cardNumber by lazy { arguments?.getString(CARD_NUMBER_KEY)!! }


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        viewLifecycleOwner.lifecycleScope.launchWhenStarted {
            viewModel.getCardInfo(cardNumber)
        }

        viewModel.viewState
            .onEach {

            }
            .launchIn(viewLifecycleOwner.lifecycleScope)
    }

    companion object {
        private const val CARD_NUMBER_KEY = "card_number"
        fun makeArguments(cardNumber: String): Bundle {
            val args = Bundle()
            args.putString(CARD_NUMBER_KEY, cardNumber)
            return args
        }
    }
}
