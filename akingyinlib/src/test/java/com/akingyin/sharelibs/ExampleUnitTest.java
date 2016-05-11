package com.akingyin.sharelibs;

import android.text.TextUtils;
import org.junit.Test;

import static org.junit.Assert.*;

/**
 * To work on unit tests, switch the Test Artifact in the Build Variants view.
 */
public class ExampleUnitTest {

    public   static   String  key="124";
    @Test
    public void addition_isCorrect() throws Exception {
        assertEquals(TextUtils.isEmpty(key),false);
        assertEquals(4, 2 + 2);
    }
}