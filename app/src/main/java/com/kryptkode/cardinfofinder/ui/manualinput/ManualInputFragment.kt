package com.kryptkode.cardinfofinder.ui.manualinput

import android.os.Bundle
import android.view.View
import androidx.fragment.app.Fragment
import com.kryptkode.cardinfofinder.R
import com.kryptkode.cardinfofinder.databinding.FragmentManualInputBinding
import com.kryptkode.cardinfofinder.navigator.Navigator
import com.kryptkode.cardinfofinder.util.ToastHelper
import com.kryptkode.cardinfofinder.util.viewbinding.viewBinding
import dagger.hilt.android.AndroidEntryPoint
import javax.inject.Inject

@AndroidEntryPoint
class ManualInputFragment : Fragment(R.layout.fragment_manual_input) {

    @Inject
    lateinit var navigator: Navigator

    @Inject
    lateinit var toastHelper: ToastHelper

    private val binding by viewBinding(FragmentManualInputBinding::bind)

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.navBtn.setLeftButtonListener {
            navigator.navigateUp()
        }

        binding.navBtn.setRightButtonListener {
            if (binding.cardNumberEditText.isValid) {
                navigator.toCardInfo(binding.cardNumberEditText.text.toString())
            } else {
                toastHelper.showMessage(getString(R.string.card_number_invalid_message))
            }
        }
    }
}
