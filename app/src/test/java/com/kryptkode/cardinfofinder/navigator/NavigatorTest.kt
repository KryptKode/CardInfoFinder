package com.kryptkode.cardinfofinder.navigator

import androidx.lifecycle.LifecycleCoroutineScope
import androidx.navigation.NavController
import com.kryptkode.cardinfofinder.R
import com.kryptkode.cardinfofinder.data.usecase.SeenWalkThroughUseCase
import io.mockk.coEvery
import io.mockk.every
import io.mockk.mockk
import io.mockk.verify
import kotlinx.coroutines.Job
import org.junit.Before
import org.junit.Test

class NavigatorTest {

    private lateinit var seenWalkThroughUseCase: SeenWalkThroughUseCase
    private lateinit var navControllerProvider: NavControllerProvider
    private lateinit var lifecycleCoroutineScope: LifecycleCoroutineScope
    private lateinit var navController: NavController
    private lateinit var sut: Navigator

    @Before
    fun setUp() {
        lifecycleCoroutineScope = mockk()
        seenWalkThroughUseCase = mockk()
        navControllerProvider = mockk()
        navController = mockk()
        sut = Navigator(lifecycleCoroutineScope, navControllerProvider, seenWalkThroughUseCase)

        stubNavController()
        stubNavControllerNavigateUp()
        stubNavControllerNavigate()
    }

    @Test
    fun `navigateUp calls nav controller`() {
        sut.navigateUp()

        verify {
            navController.navigateUp()
        }
    }

    @Test
    fun `inputModeToManualInput calls nav controller`() {
        sut.inputModeToManualInput()

        verify {
            navController.navigate(R.id.action_cardInputModeFragment_to_manualInputFragment)
        }
    }

    @Test
    fun `inputModeToOcrInput calls nav controller`() {
        sut.inputModeToOcrInput()

        verify {
            navController.navigate(R.id.action_cardInputModeFragment_to_ocrInputFragment)
        }
    }

    private fun stubNavController() {
        every {
            navControllerProvider.getNavController()
        } returns navController
    }

    private fun stubNavControllerNavigateUp() {
        every {
            navController.navigateUp()
        } returns true
    }

    private fun stubNavControllerNavigateWithArgs() {
        every {
            navController.navigate((any() as Int), any())
        } returns Unit
    }

    private fun stubNavControllerNavigate() {
        every {
            navController.navigate((any() as Int))
        } returns Unit
    }

    private fun stubLifecycleCoroutine() {
        every {
            lifecycleCoroutineScope.launchWhenCreated(any())
        } returns Job()
    }

    private fun stubSeenWalkthrough() {
        coEvery {
            seenWalkThroughUseCase.setSeenWalkthrough(any())
        } returns Unit
    }
}
