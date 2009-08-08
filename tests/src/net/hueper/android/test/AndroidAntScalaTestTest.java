package net.hueper.android.test;

import android.test.ActivityInstrumentationTestCase;

/**
 * This is a simple framework for a test of an Application.  See
 * {@link android.test.ApplicationTestCase ApplicationTestCase} for more information on
 * how to write and extend Application tests.
 * <p/>
 * To run this test, you can type:
 * adb shell am instrument -w \
 * -e class net.hueper.android.test.AndroidAntScalaTestTest \
 * net.hueper.android.test.tests/android.test.InstrumentationTestRunner
 */
public class AndroidAntScalaTestTest extends ActivityInstrumentationTestCase<AndroidAntScalaTest> {

    public AndroidAntScalaTestTest() {
        super("net.hueper.android.test", AndroidAntScalaTest.class);
    }

}
