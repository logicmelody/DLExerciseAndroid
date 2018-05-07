package com.dl.dlexerciseandroid;

import org.junit.Test;

import static org.junit.Assert.*;

/**
 * To work on unit tests, switch the Test Artifact in the Build Variants view.
 *
 * Local Unit Test要將file放在這個package底下
 */
public class ExampleUnitTest {

    @Test
    public void addition_isCorrect() throws Exception {
        // 初始setup
        int first = 1;
        int second = 2;
        int expect = 3;

        // 執行
        int actual = first + second;

        // 驗證
        assertEquals(expect, actual);
    }
}