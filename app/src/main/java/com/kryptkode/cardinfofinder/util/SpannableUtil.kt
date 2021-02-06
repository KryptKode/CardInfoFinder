package com.kryptkode.cardinfofinder.util

import android.graphics.Typeface
import android.text.Spannable
import android.text.SpannableStringBuilder
import android.text.style.ClickableSpan
import android.text.style.ForegroundColorSpan
import android.text.style.StyleSpan
import android.view.View
import androidx.annotation.ColorInt

object SpannableUtil {

    fun getSpannableBoldText(fullText: String, boldText: String): SpannableStringBuilder {
        if (!fullText.contains(boldText)) {
            throw IllegalStateException("The full text '$fullText' should contain the bold text '$boldText'")
        }

        val boldTextStartIndex = fullText.indexOf(boldText)
        val boldTextEndIndex = boldTextStartIndex + boldText.length
        val spannable = SpannableStringBuilder(fullText)
        spannable.setSpan(
            StyleSpan(Typeface.BOLD),
            boldTextStartIndex,
            boldTextEndIndex,
            Spannable.SPAN_EXCLUSIVE_EXCLUSIVE
        )
        return spannable
    }

    fun getSpannableForegroundColorText(
        fullText: String,
        coloredText: String,
        @ColorInt color: Int
    ): SpannableStringBuilder {
        if (!fullText.contains(coloredText)) {
            throw IllegalStateException("The full text '$fullText' should contain the colorText text '$coloredText'")
        }

        val boldTextStartIndex = fullText.indexOf(coloredText)
        val boldTextEndIndex = boldTextStartIndex + coloredText.length
        val spannable = SpannableStringBuilder(fullText)
        spannable.setSpan(
            ForegroundColorSpan(color),
            boldTextStartIndex,
            boldTextEndIndex,
            Spannable.SPAN_EXCLUSIVE_EXCLUSIVE
        )
        return spannable
    }

    fun getClickableSpan(fullText: String, clickableText: String, @ColorInt color: Int, callback: ()->Unit): SpannableStringBuilder {
        if (!fullText.contains(clickableText)) {
            throw IllegalStateException("The full text '$fullText' should contain the clickableText text '$clickableText'")
        }

        val boldTextStartIndex = fullText.indexOf(clickableText)
        val boldTextEndIndex = boldTextStartIndex + clickableText.length
        val spannable = SpannableStringBuilder(fullText)

        spannable.setSpan(
            ForegroundColorSpan(color),
            boldTextStartIndex,
            boldTextEndIndex,
            Spannable.SPAN_EXCLUSIVE_EXCLUSIVE
        )

        spannable.setSpan(
            object : ClickableSpan() {
                override fun onClick(p0: View) {
                    callback()
                }
            },
            boldTextStartIndex,
            boldTextEndIndex,
            Spannable.SPAN_EXCLUSIVE_EXCLUSIVE
        )
        return spannable
    }
}
