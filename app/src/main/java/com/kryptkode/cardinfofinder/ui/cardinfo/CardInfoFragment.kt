package com.kryptkode.cardinfofinder.ui.cardinfo

import android.annotation.SuppressLint
import android.os.Bundle
import android.view.View
import androidx.core.view.isVisible
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import com.kryptkode.cardinfofinder.R
import com.kryptkode.cardinfofinder.data.model.CardInfo
import com.kryptkode.cardinfofinder.databinding.FragmentCardInfoBinding
import com.kryptkode.cardinfofinder.navigator.Navigator
import com.kryptkode.cardinfofinder.util.viewbinding.viewBinding
import dagger.hilt.android.AndroidEntryPoint
import javax.inject.Inject
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach

@AndroidEntryPoint
class CardInfoFragment : Fragment(R.layout.fragment_card_info) {

    @Inject
    lateinit var navigator: Navigator

    private val viewModel by viewModels<CardInfoViewModel>()
    private val binding by viewBinding(FragmentCardInfoBinding::bind)
    private val cardNumber by lazy { arguments?.getString(CARD_NUMBER_KEY)!! }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.errorGroup.isVisible = true
        binding.cardDetailsGroup.isVisible = false
        binding.swipeRefresh.isEnabled = false
        binding.swipeRefresh.isRefreshing = false

        binding.cardView.setCardNumber(cardNumber)

        binding.retryButton.setOnClickListener {
            getCardInfo(refresh = true)
        }

        binding.imgBack.setOnClickListener {
            navigator.navigateUp()
        }

        getCardInfo()

        viewModel.viewState
            .onEach {
                binding.swipeRefresh.isEnabled = it.loading
                binding.swipeRefresh.isRefreshing = it.loading
                binding.cardDetailsGroup.isVisible = it.cardInfo.brand.isNotEmpty()
                bindCardInfo(it.cardInfo)
                binding.errorGroup.isVisible = it.error || it.loading
                binding.retryButton.isVisible = it.error
                binding.errorImage.setImageResource(if (it.error) R.drawable.ic_cloud else R.drawable.ic_card)
                binding.errorTextView.text =
                    if (it.error) it.errorMessage else getString(R.string.card_info_loading_message)
            }
            .launchIn(viewLifecycleOwner.lifecycleScope)
    }

    private fun getCardInfo(refresh: Boolean = false) {
        viewLifecycleOwner.lifecycleScope.launchWhenStarted {
            viewModel.getCardInfo(cardNumber, refresh)
        }
    }

    @SuppressLint("DefaultLocale")
    private fun bindCardInfo(cardInfo: CardInfo) {

        binding.cardDetails.tvScheme.text = cardInfo.scheme.capitalize()
        binding.cardDetails.tvBrand.text = cardInfo.brand.capitalize()
        binding.cardDetails.tvType.text = cardInfo.type.capitalize()

        binding.cardCountry.tvName.text = cardInfo.country.name.capitalize()
        binding.cardCountry.tvCurrency.text = getString(R.string.currency_label, cardInfo.country.currency)
        binding.cardCountry.tvEmoji.text = cardInfo.country.emoji

        binding.cardBank.tvName.text = cardInfo.bank.name.capitalize()

        binding.cardBank.tvPhone.text = cardInfo.bank.phone
        binding.cardBank.tvWebsite.text = cardInfo.bank.url
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
