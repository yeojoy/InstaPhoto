package me.yeojoy.instaapp;

import android.content.Context;
import android.support.test.InstrumentationRegistry;
import android.support.test.runner.AndroidJUnit4;

import org.junit.Test;
import org.junit.runner.RunWith;

import me.yeojoy.instaapp.utils.Validator;

import static org.junit.Assert.*;

/**
 * Instrumentation test, which will execute on an Android device.
 *
 * @see <a href="http://d.android.com/tools/testing">Testing documentation</a>
 */
@RunWith(AndroidJUnit4.class)
public class ExampleInstrumentedTest {

    @Test
    public void useAppContext() throws Exception {
        // Context of the app under test.
        Context appContext = InstrumentationRegistry.getTargetContext();

        assertEquals("me.yeojoy.instaapp", appContext.getPackageName());
    }

    /**
     * Validator TestCode 추가
     *
     */
    @Test
    public void check_Validator_insta_username() {
        // valid
        String userName = "yeojoy";
        assertTrue(Validator.isQueryValidated(userName));

        // valid
        userName = "yeojoy_";
        assertTrue(Validator.isQueryValidated(userName));

        // valid
        userName = "yeojoy.";
        assertTrue(Validator.isQueryValidated(userName));

        // valid
        userName = "yeojoy23";
        assertTrue(Validator.isQueryValidated(userName));

        // invalid
        userName = "yeojoy-";
        assertTrue(!Validator.isQueryValidated(userName));

    }
}
