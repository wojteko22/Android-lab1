package pl.edu.pwr.wojciech.okonski.lab1.lab1

import android.support.test.espresso.Espresso.onData
import android.support.test.espresso.Espresso.onView
import android.support.test.espresso.action.ViewActions.click
import android.support.test.espresso.action.ViewActions.typeText
import android.support.test.espresso.assertion.ViewAssertions.matches
import android.support.test.espresso.matcher.ViewMatchers.withId
import android.support.test.espresso.matcher.ViewMatchers.withText
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
        onView(withId(R.id.etMass)).perform(typeText("67.5"))
        onView(withId(R.id.etHeight)).perform(typeText("1.85"))
        onView(withId(R.id.btnCount)).perform(click())
        onView(withId(R.id.tvBmiResult)).check(matches(withText(containsString("19.7"))))
        onView(withId(R.id.tvDescription)).check(matches(withText(R.string.normalBmi)))
    }

    @Test
    fun displayPromptWhenSomeFieldIsEmpty() {
        onView(withId(R.id.etMass)).perform(typeText("60"))
        onView(withId(R.id.btnCount)).perform(click())
        onView(withId(R.id.tvDescription)).check(matches(withText(R.string.invalidInput)))
    }

    @Test
    fun displayPromptForInvalidInputForLbIn() {
        onView(withId(R.id.spinner)).perform(click())
        onData(allOf(`is`(instanceOf(String::class.java)), `is`("lb/in"))).perform(click())
        onView(withId(R.id.etMass)).perform(typeText("67.5"))
        onView(withId(R.id.etHeight)).perform(typeText("1.85"))
        onView(withId(R.id.btnCount)).perform(click())
        onView(withId(R.id.tvDescription)).check(matches(withText(R.string.invalidInput)))
    }
}