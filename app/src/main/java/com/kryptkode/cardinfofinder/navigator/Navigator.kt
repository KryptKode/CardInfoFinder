package com.kryptkode.cardinfofinder.navigator

import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.lifecycleScope
import androidx.navigation.NavController
import com.kryptkode.cardinfofinder.R
import com.kryptkode.cardinfofinder.data.usecase.SeenWalkThroughUseCase
import com.kryptkode.cardinfofinder.ui.cardinfo.CardInfoFragment
import javax.inject.Inject
import kotlinx.coroutines.flow.first

class Navigator @Inject constructor(
    private val activity: AppCompatActivity,
    private val navControllerProvider: NavControllerProvider,
    private val seenWalkThroughUseCase: SeenWalkThroughUseCase
) {

    private val navController: NavController
        get() = navControllerProvider.getNavController()

    fun setup() {
        activity.lifecycleScope.launchWhenCreated {
            val graph = navController.navInflater.inflate(R.navigation.main_nav)
            val seenWalkthrough = seenWalkThroughUseCase.seenWalkthrough().first()
            graph.startDestination = when {
                seenWalkthrough -> {
                    R.id.cardInputModeFragment
                }
                else -> {
                    R.id.walkThroughFragment
                }
            }
            navController.graph = graph
        }
    }

    fun navigateUp() {
        navController.navigateUp()
    }

    fun walkThroughToNext() {
        activity.lifecycleScope.launchWhenCreated {
            seenWalkThroughUseCase.setSeenWalkthrough(true)
            navController.navigate(R.id.action_walkThroughFragment_to_cardInputModeFragment)
        }
    }

    fun inputModeToManualInput() {
        navController.navigate(R.id.action_cardInputModeFragment_to_manualInputFragment)
    }

    fun inputModeToOcrInput() {
        navController.navigate(R.id.action_cardInputModeFragment_to_ocrInputFragment)
    }

    fun toCardInfo(cardNumber: String) {
        navController.navigate(
            R.id.action_to_cardInfoFragment,
            CardInfoFragment.makeArguments(cardNumber)
        )
    }
}
