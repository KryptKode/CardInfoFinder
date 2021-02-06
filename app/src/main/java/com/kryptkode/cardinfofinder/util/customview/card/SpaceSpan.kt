package com.kryptkode.cardinfofinder.util.customview.card

import android.graphics.Canvas
import android.graphics.Paint
import android.graphics.Paint.FontMetricsInt
import android.text.style.ReplacementSpan

/**
 * A [ReplacementSpan] used for spacing in [android.widget.EditText]
 * to space things out. Adds ' 's
 */
class SpaceSpan : ReplacementSpan() {
    override fun getSize(paint: Paint, text: CharSequence, start: Int, end: Int, fm: FontMetricsInt?): Int {
        val padding = paint.measureText(" ", 0, 1)
        val textSize = paint.measureText(text, start, end)
        return (padding + textSize).toInt()
    }

    override fun draw(
        canvas: Canvas, text: CharSequence, start: Int, end: Int, x: Float, top: Int, y: Int,
        bottom: Int, paint: Paint
    ) {
        canvas.drawText(text.subSequence(start, end).toString() + " ", x, y.toFloat(), paint)
    }
}
