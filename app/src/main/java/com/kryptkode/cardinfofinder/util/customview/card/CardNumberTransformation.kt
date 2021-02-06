package com.kryptkode.cardinfofinder.util.customview.card

import android.graphics.Rect
import android.text.method.TransformationMethod
import android.view.View
import java.util.Arrays

/**
 * A transformation that masks card numbers up to the last 4 digits. For example, it will transform
 * "4111111111111111" to "●●●● 1111". This can be used to mask card numbers in an [android.widget.EditText].
 */
class CardNumberTransformation : TransformationMethod {
    override fun getTransformation(source: CharSequence, view: View): CharSequence {
        if (source.length >= 9) {
            val result = StringBuilder()
                .append(FOUR_DOTS)
                .append(" ")
                .append(source.subSequence(source.length - 4, source.length))
            val padding = CharArray(source.length - result.length)
            Arrays.fill(padding, Character.MIN_VALUE)
            result.insert(0, padding)
            return result.toString()
        }
        return source
    }

    override fun onFocusChanged(
        view: View,
        sourceText: CharSequence,
        focused: Boolean,
        direction: Int,
        previouslyFocusedRect: Rect
    ) {
    }

    companion object {
        private const val FOUR_DOTS = "••••"
    }
}
