package com.kryptkode.cardinfofinder.ui.manualinput

import androidx.test.espresso.Espresso.onView
import androidx.test.espresso.action.ViewActions.clearText
import androidx.test.espresso.action.ViewActions.click
import androidx.test.espresso.action.ViewActions.replaceText
import androidx.test.espresso.assertion.ViewAssertions.matches
import androidx.test.espresso.matcher.ViewMatchers.withId
import androidx.test.ext.junit.runners.AndroidJUnit4
import androidx.test.filters.SmallTest
import com.kryptkode.cardinfofinder.R
import com.kryptkode.cardinfofinder.navigator.Navigator
import com.kryptkode.cardinfofinder.utils.getString
import com.kryptkode.cardinfofinder.utils.hasTextInputLayoutErrorText
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
class ManualInputFragmentTest {

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
    fun clickNavLeftButtonNavigatesUp() {
        stubNavigateUp()

        launchFragmentInHiltContainer<ManualInputFragment>()

        onView(withId(R.id.left_btn)).perform(click())

        verify {
            navigator.navigateUp()
        }
    }

    @Test
    fun clickNavRightButtonWithTextNavigatesToCardInfo() {
        stubNavigatorToCardInfo()

        launchFragmentInHiltContainer<ManualInputFragment>()

        onView(withId(R.id.card_number_edit_text)).perform(replaceText("4242424242424242"))

        onView(withId(R.id.right_btn)).perform(click())

        verify {
            navigator.toCardInfo("4242424242424242")
        }
    }

    @Test
    fun clickNavRightButtonWithoutTextShowsError() {
        launchFragmentInHiltContainer<ManualInputFragment>()

        onView(withId(R.id.card_number_edit_text)).perform(clearText())

        onView(withId(R.id.right_btn)).perform(click())

        onView(withId(R.id.card_number_input))
            .check(
                matches(
                    hasTextInputLayoutErrorText(
                        getString(R.string.card_number_invalid_message)
                    )
                )
            )
    }

    @After
    fun tearDown() {
        unmockkAll()
    }

    private fun stubNavigatorToCardInfo() {
        every {
            navigator.toCardInfo(any())
        } returns Unit
    }

    private fun stubNavigateUp() {
        every {
            navigator.navigateUp()
        } returns Unit
    }
}
