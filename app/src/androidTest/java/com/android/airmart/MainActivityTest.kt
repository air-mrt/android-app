package com.android.airmart

import android.view.Gravity
import androidx.test.espresso.Espresso.onView
import androidx.test.espresso.action.ViewActions.click
import androidx.test.espresso.assertion.ViewAssertions
import androidx.test.espresso.contrib.DrawerMatchers
import androidx.test.espresso.contrib.NavigationViewActions.navigateTo
import androidx.test.espresso.matcher.ViewMatchers
import androidx.test.espresso.matcher.ViewMatchers.withContentDescription
import androidx.test.espresso.matcher.ViewMatchers.withId
import androidx.test.rule.ActivityTestRule
import com.android.airmart.ui.MainActivity
import com.android.airmart.utilities.getToolbarNavigationContentDescription
import kotlinx.coroutines.withContext
import org.junit.Rule
import org.junit.Test

class MainActivityTest {
    @Rule
    @JvmField
    var activityTestRule = ActivityTestRule(MainActivity::class.java)

    @Test
    fun clickOnAndroidHomeIcon_OpensAndClosesNavigation() {
        onchecked()
        // Check that drawer is closed at startup
        onView(withId(R.id.drawer_layout))
            .check(ViewAssertions.matches(DrawerMatchers.isClosed(Gravity.START)))

        checkDrawerIsOpen()
    }


    private fun checkDrawerIsOpen() {
        onView(withId(R.id.drawer_layout))
            .check(ViewAssertions.matches(DrawerMatchers.isOpen(Gravity.START)))
            .perform()

    }
    private fun onchecked(){
        onView(withContentDescription(getToolbarNavigationContentDescription(
            activityTestRule.activity,R.id.toolbar_layout))).perform(click())
    }



}