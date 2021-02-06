package com.kryptkode.cardinfofinder.util.customview

import android.content.Context
import android.util.AttributeSet
import android.view.LayoutInflater
import androidx.constraintlayout.widget.ConstraintLayout
import com.kryptkode.cardinfofinder.databinding.NavigationButtonBinding

class NavigationButton(context: Context, attributeSet: AttributeSet) :
        ConstraintLayout(context, attributeSet) {

    private val binding = NavigationButtonBinding.inflate(LayoutInflater.from(context),this)

    fun setLeftButtonListener(listener: () -> Unit) {
        binding.leftBtn.setOnClickListener {
            listener()
        }
    }

    fun setRightButtonListener(listener: () -> Unit) {
        binding.rightBtn.setOnClickListener {
            listener()
        }
    }
}


