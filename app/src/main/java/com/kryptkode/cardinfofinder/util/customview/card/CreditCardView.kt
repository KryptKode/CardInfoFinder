package com.kryptkode.cardinfofinder.util.customview.card

import android.content.Context
import android.text.SpannableString
import android.text.Spanned
import android.util.AttributeSet
import android.view.LayoutInflater
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
        val cardType = CardType.forCardNumber(cardNumber)
        val textSpan = addSpans(cardNumber, cardType.spaceIndices)
        binding.cardNumber.text = textSpan
        binding.cardTypeLogo.setImageResource(cardType.frontResource)
    }

    private fun addSpans(editable: String, spaceIndices: IntArray): SpannableString {
        val length = editable.length
        val span = SpannableString(editable)
        for (index in spaceIndices) {
            if (index <= length) {
                span.setSpan(
                    SpaceSpan(),
                    index - 1,
                    index,
                    Spanned.SPAN_EXCLUSIVE_EXCLUSIVE
                )
            }
        }

        return span
    }
}
