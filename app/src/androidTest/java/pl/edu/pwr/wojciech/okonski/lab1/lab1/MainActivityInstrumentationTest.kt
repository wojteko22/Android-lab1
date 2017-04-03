package pl.edu.pwr.wojciech.okonski.lab1.lab1

import android.support.test.espresso.Espresso.onData
import android.support.test.espresso.Espresso.onView
import android.support.test.espresso.action.ViewActions.*
import android.support.test.espresso.assertion.ViewAssertions.matches
import android.support.test.espresso.matcher.ViewMatchers.*
import android.support.test.filters.LargeTest
import android.support.test.rule.ActivityTestRule
import android.support.test.runner.AndroidJUnit4
import org.hamcrest.Matchers.*
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith

@RunWith(AndroidJUnit4::class)
@LargeTest
class MainActivityInstrumentationTest {

    @Rule @JvmField
    val activity = ActivityTestRule(MainActivity::class.java)

    @Test
    fun displayProperResultsWhenDataIsCorrect() {
        clearInputFields()
        onView(withId(R.id.etMass)).perform(typeText("67.5"))
        onView(withId(R.id.etHeight)).perform(typeText("1.85"))
        onView(withId(R.id.btnCount)).perform(click())
        onView(withId(R.id.tvBmiResult)).check(matches(withText(containsString("19.7"))))
        onView(withId(R.id.tvDescription)).check(matches(withText(R.string.normalBmi)))
    }

    @Test
    fun disableButtonsWhenSomeInputIsInvalid() {
        clearInputFields()
        onView(withId(R.id.etMass)).perform(typeText("60"))
        onView(withId(R.id.etHeight)).perform(typeText("60"))
        onView(withId(R.id.btnSave)).check(matches(not(isEnabled())))
        onView(withId(R.id.btnCount)).check(matches(not(isEnabled())))
    }

    @Test
    fun disableButtonsWhenSomeInputIsInvalidInNotDefaultUnits() {
        clearInputFields()
        onView(withId(R.id.spinner)).perform(click())
        onData(allOf(`is`(instanceOf(String::class.java)), `is`("lb/in"))).perform(click())
        onView(withId(R.id.etMass)).perform(typeText("67.5"))
        onView(withId(R.id.etHeight)).perform(typeText("1.85"))
        onView(withId(R.id.btnSave)).check(matches(not(isEnabled())))
        onView(withId(R.id.btnCount)).check(matches(not(isEnabled())))
    }

    private fun clearInputFields() {
        onView(withId(R.id.etMass)).perform(clearText())
        onView(withId(R.id.etHeight)).perform(clearText())
    }
}