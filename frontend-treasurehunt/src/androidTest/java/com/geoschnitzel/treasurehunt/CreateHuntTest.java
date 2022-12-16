package com.geoschnitzel.treasurehunt;

import android.support.test.espresso.Espresso;
import android.support.test.rule.ActivityTestRule;


import com.geoschnitzel.treasurehunt.createhunt.CreateHuntActivity;

import org.junit.Rule;
import org.junit.Test;

import static android.support.test.espresso.Espresso.onView;
import static android.support.test.espresso.assertion.ViewAssertions.matches;
import static android.support.test.espresso.matcher.ViewMatchers.isDisplayed;
import static android.support.test.espresso.matcher.ViewMatchers.withId;

public class CreateHuntTest {
    @Rule
    public ActivityTestRule<CreateHuntActivity> mCreateHuntActivityTestRule =
            new ActivityTestRule<CreateHuntActivity>(CreateHuntActivity.class) {
            };

    @Test
    public void getsDisplayed() {
        onView(withId(R.id.create_hunt_name)).check(matches(isDisplayed()));
    }
}
