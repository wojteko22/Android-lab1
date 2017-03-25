package pl.edu.pwr.wojciech.okonski.lab1.lab1;

import android.support.test.filters.LargeTest;
import android.support.test.rule.ActivityTestRule;
import android.support.test.runner.AndroidJUnit4;

import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

import static android.support.test.espresso.Espresso.onData;
import static android.support.test.espresso.Espresso.onView;
import static android.support.test.espresso.action.ViewActions.click;
import static android.support.test.espresso.action.ViewActions.typeText;
import static android.support.test.espresso.assertion.ViewAssertions.matches;
import static android.support.test.espresso.matcher.ViewMatchers.withId;
import static android.support.test.espresso.matcher.ViewMatchers.withText;
import static org.hamcrest.Matchers.allOf;
import static org.hamcrest.Matchers.containsString;
import static org.hamcrest.Matchers.instanceOf;
import static org.hamcrest.Matchers.is;

@RunWith(AndroidJUnit4.class)
@LargeTest
public class MainActivityInstrumentationTest {

    @Rule
    public ActivityTestRule mActivityRule = new ActivityTestRule<>(
            MainActivity.class);

    @Test
    public void displayProperResultsWhenDataIsCorrect() {
        onView(withId(R.id.etMass)).perform(typeText("67.5"));
        onView(withId(R.id.etHeight)).perform(typeText("1.85"));
        onView(withId(R.id.btnCount)).perform(click());
        onView(withId(R.id.tvBmiResult)).check(matches((withText(containsString("19.7")))));
        onView(withId(R.id.tvDescription)).check(matches(withText(R.string.normalBmi)));
    }

    @Test
    public void displayPromptWhenSomeFieldIsEmpty() {
        onView(withId(R.id.etMass)).perform(typeText("60"));
        onView(withId(R.id.btnCount)).perform(click());
        onView(withId(R.id.tvDescription)).check(matches(withText(R.string.invalidInput)));
    }

    @Test
    public void displayPromptForInvalidInputForLbIn() {
        onView(withId(R.id.spinner)).perform(click());
        onData(allOf(is(instanceOf(String.class)), is("lb/in"))).perform(click());
        onView(withId(R.id.etMass)).perform(typeText("67.5"));
        onView(withId(R.id.etHeight)).perform(typeText("1.85"));
        onView(withId(R.id.btnCount)).perform(click());
        onView(withId(R.id.tvDescription)).check(matches(withText(R.string.invalidInput)));
    }
}