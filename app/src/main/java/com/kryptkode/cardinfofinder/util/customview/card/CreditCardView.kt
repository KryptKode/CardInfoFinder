package com.kryptkode.cardinfofinder.util.customview.card

import android.content.Context
import android.util.AttributeSet
import android.view.LayoutInflater
import androidx.annotation.DrawableRes
import androidx.constraintlayout.widget.ConstraintLayout
import com.kryptkode.cardinfofinder.R
import com.kryptkode.cardinfofinder.databinding.CreditCardViewBinding

class CreditCardView(context: Context, attrs: AttributeSet) : ConstraintLayout(context, attrs) {

    private val binding = CreditCardViewBinding.inflate(LayoutInflater.from(context), this)

    init {
        val a = context.obtainStyledAttributes(attrs, R.styleable.CreditCardView, 0, 0)

        val cardNumber = a.getString(R.styleable.CreditCardView_card_number) ?: ""
        binding.cardNumber.text = cardNumber

        a.recycle()
    }

    fun setCardNumber(cardNumber: String) {
        binding.cardNumber.text = cardNumber
        val cardType = CardType.forCardNumber(cardNumber)
        setCardTypeIcon(cardType.frontResource)
    }

    fun setCardTypeIcon(@DrawableRes resId: Int) {
        binding.cardTypeLogo.setImageResource(resId)
    }

}
