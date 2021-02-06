package com.kryptkode.cardinfofinder.ui.cardinfo

import android.os.Bundle
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import com.kryptkode.cardinfofinder.R
import com.kryptkode.cardinfofinder.databinding.FragmentCardInfoBinding
import com.kryptkode.cardinfofinder.util.viewbinding.viewBinding
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class CardInfoFragment : Fragment(R.layout.fragment_card_info) {

    private val viewModel by viewModels<CardInfoViewModel>()
    private val binding by viewBinding(FragmentCardInfoBinding::bind)
    private val cardNumber by lazy { arguments?.getString(CARD_NUMBER_KEY)!! }



    companion object {
        private const val CARD_NUMBER_KEY = "card_number"
        fun makeArguments(cardNumber: String): Bundle {
            val args = Bundle()
            args.putString(CARD_NUMBER_KEY, cardNumber)
            return args
        }
    }
}

