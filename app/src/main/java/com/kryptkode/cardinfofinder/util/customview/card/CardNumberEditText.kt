package com.kryptkode.cardinfofinder.util.customview.card

import android.content.Context
import android.graphics.Rect
import android.text.Editable
import android.text.InputFilter
import android.text.InputFilter.LengthFilter
import android.text.InputType
import android.text.Spanned
import android.text.TextUtils
import android.text.TextWatcher
import android.text.method.TransformationMethod
import android.util.AttributeSet
import androidx.core.widget.TextViewCompat
import com.kryptkode.cardinfofinder.R
import com.kryptkode.cardinfofinder.util.customview.card.CardType.Companion.forCardNumber

/**
 * An [android.widget.EditText] that displays Card icons based on the number entered.
 */
class CardNumberEditText : ErrorEditText, TextWatcher {
    interface OnCardTypeChangedListener {
        fun onCardTypeChanged(cardType: CardType?)
    }

    private var mDisplayCardIcon = true
    private var mMask = false

    /**
     * @return The [CardType] currently entered in
     * the [android.widget.EditText]
     */
    var cardType: CardType? = null
        private set
    private var mOnCardTypeChangedListener: OnCardTypeChangedListener? = null
    private var mSavedTranformationMethod: TransformationMethod? = null

    constructor(context: Context?) : super(context) {
        init()
    }

    constructor(context: Context?, attrs: AttributeSet?) : super(context, attrs) {
        init()
    }

    constructor(context: Context?, attrs: AttributeSet?, defStyle: Int) : super(context, attrs, defStyle) {
        init()
    }

    private fun init() {
        inputType = InputType.TYPE_CLASS_NUMBER
        setCardIcon(R.drawable.bt_ic_unknown)
        addTextChangedListener(this)
        updateCardType()
        mSavedTranformationMethod = transformationMethod
    }

    /**
     * Enable or disable showing card type icons as part of the [CardNumberEditText]. Defaults to
     * `true`.
     *
     * @param display `true` to display card type icons, `false` to never display card
     * type icons.
     */
    fun displayCardTypeIcon(display: Boolean) {
        mDisplayCardIcon = display
        if (!mDisplayCardIcon) {
            setCardIcon(-1)
        }
    }

    /**
     * @param mask if `true`, all but the last four digits of the card number will be masked when
     * focus leaves the card field. Uses [CardNumberTransformation], transforming the number from
     * something like "4111111111111111" to "•••• 1111".
     */
    fun setMask(mask: Boolean) {
        mMask = mask
    }

    override fun onFocusChanged(focused: Boolean, direction: Int, previouslyFocusedRect: Rect?) {
        super.onFocusChanged(focused, direction, previouslyFocusedRect)
        if (focused) {
            unmaskNumber()
            if (text.toString().length > 0) {
                setSelection(text.toString().length)
            }
        } else if (mMask && isValid) {
            maskNumber()
        }
    }

    /**
     * Receive a callback when the [CardType] changes
     * @param listener to be called when the [CardType]
     * changes
     */
    fun setOnCardTypeChangedListener(listener: OnCardTypeChangedListener?) {
        mOnCardTypeChangedListener = listener
    }

    override fun afterTextChanged(editable: Editable) {
        val paddingSpans = editable.getSpans(0, editable.length, SpaceSpan::class.java)
        for (span in paddingSpans) {
            editable.removeSpan(span)
        }
        updateCardType()
        setCardIcon(cardType!!.frontResource)
        addSpans(editable, cardType!!.spaceIndices)
        if (cardType!!.maxCardLength == selectionStart) {
            validate()
            if (isValid) {
                focusNextView()
            } else {
                unmaskNumber()
            }
        } else if (!hasFocus()) {
            if (mMask) {
                maskNumber()
            }
        }
    }

    override val isValid: Boolean
        get() = isOptional || cardType!!.validate(text.toString())
    override val errorMessage: String
        get() = if (TextUtils.isEmpty(text)) {
            context.getString(R.string.bt_card_number_required)
        } else {
            context.getString(R.string.bt_card_number_invalid)
        }

    private fun maskNumber() {
        if (transformationMethod !is CardNumberTransformation) {
            mSavedTranformationMethod = transformationMethod
            transformationMethod = CardNumberTransformation()
        }
    }

    private fun unmaskNumber() {
        if (transformationMethod !== mSavedTranformationMethod) {
            transformationMethod = mSavedTranformationMethod
        }
    }

    private fun updateCardType() {
        val type = forCardNumber(text.toString())
        if (cardType !== type) {
            cardType = type
            val filters = arrayOf<InputFilter>(LengthFilter(cardType!!.maxCardLength))
            setFilters(filters)
            invalidate()
            if (mOnCardTypeChangedListener != null) {
                mOnCardTypeChangedListener!!.onCardTypeChanged(cardType)
            }
        }
    }

    private fun addSpans(editable: Editable, spaceIndices: IntArray) {
        val length = editable.length
        for (index in spaceIndices) {
            if (index <= length) {
                editable.setSpan(
                    SpaceSpan(), index - 1, index,
                    Spanned.SPAN_EXCLUSIVE_EXCLUSIVE
                )
            }
        }
    }

    private fun setCardIcon(icon: Int) {
        if (!mDisplayCardIcon || text!!.length == 0) {
            TextViewCompat.setCompoundDrawablesRelativeWithIntrinsicBounds(this, 0, 0, 0, 0)
        } else {
            TextViewCompat.setCompoundDrawablesRelativeWithIntrinsicBounds(this, 0, 0, icon, 0)
        }
    }

    override fun beforeTextChanged(s: CharSequence, start: Int, count: Int, after: Int) {}
}
