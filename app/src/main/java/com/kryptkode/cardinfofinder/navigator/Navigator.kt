package com.kryptkode.cardinfofinder.navigator

import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.LifecycleObserver
import androidx.lifecycle.OnLifecycleEvent
import androidx.lifecycle.lifecycleScope
import androidx.navigation.NavController
import com.kryptkode.cardinfofinder.R
import com.kryptkode.cardinfofinder.data.usecase.SeenWalkThroughUseCase
import kotlinx.coroutines.flow.collect
import javax.inject.Inject

class Navigator @Inject constructor(
    private val activity: AppCompatActivity,
    private val navControllerProvider: NavControllerProvider,
    private val seenWalkThroughUseCase: SeenWalkThroughUseCase
) : LifecycleObserver {

    private val navController: NavController
        get() = navControllerProvider.getNavController()

    @OnLifecycleEvent(Lifecycle.Event.ON_CREATE)
    fun setup() {
        activity.lifecycleScope.launchWhenCreated {
            seenWalkThroughUseCase.seenWalkthrough().collect { seenWalkthrough ->
                val graph = navController.navInflater.inflate(R.navigation.main_nav)
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
    }

    fun walkThroughToNext() {
        activity.lifecycleScope.launchWhenCreated {
            seenWalkThroughUseCase.setSeenWalkthrough(true)
            navController.navigate(R.id.action_walkThroughFragment_to_cardInputModeFragment)
        }
    }
}
