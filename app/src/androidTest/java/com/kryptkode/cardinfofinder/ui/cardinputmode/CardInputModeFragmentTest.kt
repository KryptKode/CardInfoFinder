package com.kryptkode.cardinfofinder.ui.cardinputmode

import androidx.test.espresso.Espresso.onView
import androidx.test.espresso.action.ViewActions.click
import androidx.test.espresso.matcher.ViewMatchers.withId
import androidx.test.ext.junit.runners.AndroidJUnit4
import androidx.test.filters.SmallTest
import com.kryptkode.cardinfofinder.R
import com.kryptkode.cardinfofinder.navigator.Navigator
import com.kryptkode.cardinfofinder.utils.launchFragmentInHiltContainer
import dagger.hilt.android.testing.BindValue
import dagger.hilt.android.testing.HiltAndroidRule
import dagger.hilt.android.testing.HiltAndroidTest
import io.mockk.MockKAnnotations
import io.mockk.every
import io.mockk.impl.annotations.MockK
import io.mockk.unmockkAll
import io.mockk.verify
import org.junit.After
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith

@SmallTest
@RunWith(AndroidJUnit4::class)
@HiltAndroidTest
class CardInputModeFragmentTest {

    @get:Rule(order = 0)
    val hiltRule = HiltAndroidRule(this)

    @BindValue
    @MockK
    lateinit var navigator: Navigator

    @Before
    fun init() {
        MockKAnnotations.init(this)
        hiltRule.inject()
    }

    @Test
    fun clickManualInputNavigatesToManualInputFragment() {
        stubNavigatorToManual()

        launchFragmentInHiltContainer<CardInputModeFragment>()

        onView(withId(R.id.manual_mode)).perform(click())

        verify {
            navigator.inputModeToManualInput()
        }
    }

    @After
    fun tearDown() {
        unmockkAll()
    }

    private fun stubNavigatorToManual() {
        every {
            navigator.inputModeToManualInput()
        } returns Unit
    }
}
