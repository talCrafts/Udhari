package org.talcrafts.udhari;

import android.support.test.rule.ActivityTestRule;
import android.support.test.runner.AndroidJUnit4;

import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

import static android.support.test.espresso.Espresso.onView;
import static android.support.test.espresso.action.ViewActions.click;
import static android.support.test.espresso.assertion.ViewAssertions.matches;
import static android.support.test.espresso.matcher.ViewMatchers.isDisplayed;
import static android.support.test.espresso.matcher.ViewMatchers.withId;
import static android.support.test.espresso.matcher.ViewMatchers.withText;
import static org.hamcrest.Matchers.allOf;

/**
 * Created by ashwaghm on 22-Jan-17.
 */


@RunWith(AndroidJUnit4.class)
public class AddCardActivityTest {

    @Rule
    public ActivityTestRule<MainActivity> mActivityRule = new ActivityTestRule<>(
            MainActivity.class);

    @Test
    public void clickSimple() {
        onView(allOf(withId(R.id.fabButton), isDisplayed())).perform(click());
        //New screen has Add button which will be used to verify
        onView(allOf(withId(R.id.button_add),isDisplayed())).check(matches(withText("Add")));
    }


}
