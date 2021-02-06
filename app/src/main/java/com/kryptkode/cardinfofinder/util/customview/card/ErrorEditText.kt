package com.kryptkode.cardinfofinder.util.customview.card

import android.annotation.SuppressLint
import android.content.Context
import android.graphics.Rect
import android.text.TextUtils
import android.util.AttributeSet
import android.view.Gravity
import android.view.View
import android.view.animation.Animation
import android.view.animation.AnimationUtils
import android.view.inputmethod.EditorInfo
import android.view.inputmethod.InputMethodManager
import com.google.android.material.textfield.TextInputEditText
import com.google.android.material.textfield.TextInputLayout
import com.kryptkode.cardinfofinder.R
import com.kryptkode.cardinfofinder.util.VibrationHelper

/**
 * Parent [android.widget.EditText] for storing and displaying error states.
 */
open class ErrorEditText : TextInputEditText {
    private var mErrorAnimator: Animation? = null

    /**
     * @return the current error state of the [android.widget.EditText]
     */
    var isError = false
        private set
    /**
     * @return If this [ErrorEditText] is optional or not. See [.setOptional].
     */

    /**
     * Set this [ErrorEditText] as optional. Optional fields are always valid and show no
     * error message.
     *
     * @param isOptional `true` to set this [ErrorEditText] to optional, `false`
     * to set it to required.
     */
    var isOptional = false

    constructor(context: Context?) : super(context!!) {
        init()
    }

    constructor(context: Context?, attrs: AttributeSet?) : super(context!!, attrs) {
        init()
    }

    constructor(context: Context?, attrs: AttributeSet?, defStyle: Int) : super(context!!, attrs, defStyle) {
        init()
    }

    private fun init() {
        mErrorAnimator = AnimationUtils.loadAnimation(context, R.anim.bt_error_animation)
        isError = false
        setupRTL()
    }

    public override fun onTextChanged(text: CharSequence, start: Int, lengthBefore: Int, lengthAfter: Int) {
        super.onTextChanged(text, start, lengthBefore, lengthAfter)
        if (lengthBefore != lengthAfter) {
            setError(null)
        }
    }

    override fun onFocusChanged(focused: Boolean, direction: Int, previouslyFocusedRect: Rect?) {
        super.onFocusChanged(focused, direction, previouslyFocusedRect)
        if (!focused && !isValid && !TextUtils.isEmpty(text)) {
            setError(errorMessage)
        }
    }

    /**
     * Sets the hint on the [TextInputLayout] if this view is a child of a [TextInputLayout], otherwise
     * sets the hint on this [android.widget.EditText].
     *
     * @param hint The string resource to use as the hint.
     */
    fun setFieldHint(hint: Int) {
        setFieldHint(context.getString(hint))
    }

    /**
     * Sets the hint on the [TextInputLayout] if this view is a child of a [TextInputLayout], otherwise
     * sets the hint on this [android.widget.EditText].
     *
     * @param hint The string value to use as the hint.
     */
    fun setFieldHint(hint: String?) {
        if (textInputLayoutParent != null) {
            textInputLayoutParent!!.hint = hint
        } else {
            setHint(hint)
        }
    }

    /**
     * Request focus for the next view.
     */
    @SuppressLint("WrongConstant")
    fun focusNextView(): View? {
        if (imeActionId == EditorInfo.IME_ACTION_GO) {
            return null
        }
        val next: View? = try {
            focusSearch(FOCUS_FORWARD)
        } catch (e: IllegalArgumentException) {
            // View.FOCUS_FORWARD results in a crash in some versions of Android
            // https://github.com/braintree/braintree_android/issues/20
            focusSearch(FOCUS_DOWN)
        }
        return if (next != null && next.requestFocus()) {
            next
        } else null
    }

    /**
     * Controls the error state of this [ErrorEditText] and sets a visual indication that the
     * [ErrorEditText] contains an error.
     *
     * @param errorMessage the error message to display to the user. `null` will remove any error message displayed.
     */
    fun setError(errorMessage: String?) {
        isError = !TextUtils.isEmpty(errorMessage)
        val textInputLayout = textInputLayoutParent
        if (textInputLayout != null) {
            textInputLayout.isErrorEnabled = !TextUtils.isEmpty(errorMessage)
            textInputLayout.error = errorMessage
        }
        if (mErrorAnimator != null && isError) {
            startAnimation(mErrorAnimator)
            VibrationHelper.vibrate(context, 10)
        }
    }

    /**
     * Override this method validation logic
     *
     * @return `true`
     */
    open val isValid: Boolean
        get() = true

    /**
     * Override this method to display error messages
     *
     * @return [String] error message to display.
     */
    open val errorMessage: String?
        get() = null

    /**
     * Check if the [ErrorEditText] is valid and set the correct error state and visual
     * indication on it.
     */
    fun validate() {
        if (isValid || isOptional) {
            setError(null)
        } else {
            setError(errorMessage)
        }
    }

    /**
     * Attempt to close the soft keyboard. Will have no effect if the keyboard is not open.
     */
    fun closeSoftKeyboard() {
        (context.getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager)
            .hideSoftInputFromWindow(windowToken, 0)
    }

    /**
     * @return the [TextInputLayout] parent if present, otherwise `null`.
     */
    val textInputLayoutParent: TextInputLayout?
        get() = if (parent != null && parent.parent is TextInputLayout) {
            parent.parent as TextInputLayout
        } else null

    private fun setupRTL() {
        if (resources.configuration.layoutDirection == LAYOUT_DIRECTION_RTL) {
            textDirection = TEXT_DIRECTION_LTR
            gravity = Gravity.END
        }
    }
}
