package com.kryptkode.cardinfofinder.ui.cardinputmode

import android.os.Bundle
import android.view.View
import androidx.fragment.app.Fragment
import com.kryptkode.cardinfofinder.R
import com.kryptkode.cardinfofinder.databinding.FragmentCardInputModeBinding
import com.kryptkode.cardinfofinder.navigator.Navigator
import com.kryptkode.cardinfofinder.util.viewbinding.viewBinding
import dagger.hilt.android.AndroidEntryPoint
import javax.inject.Inject

@AndroidEntryPoint
class CardInputModeFragment : Fragment(R.layout.fragment_card_input_mode) {

    @Inject
    lateinit var navigator: Navigator

    private val binding by viewBinding(FragmentCardInputModeBinding::bind)

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.manualMode.setOnClickListener {
            navigator.inputModeToManualInput()
        }

        binding.ocrMode.setOnClickListener {
            navigator.inputModeToOcrInput()
        }
    }
}
