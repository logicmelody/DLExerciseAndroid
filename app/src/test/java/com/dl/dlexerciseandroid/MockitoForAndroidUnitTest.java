package com.dl.dlexerciseandroid;

import android.content.Context;

import org.hamcrest.MatcherAssert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;

import static org.hamcrest.core.Is.is;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

/**
 * Created by logicmelody on 2017/5/22.
 */

@RunWith(MockitoJUnitRunner.class)
public class MockitoForAndroidUnitTest {

    private static final String FAKE_APP_NAME = "DLExercise";

    @Mock
    private Context mMockContext;


    public MockitoForAndroidUnitTest() {
        // 產生mocking context object
        mMockContext = mock(Context.class);
    }

    @Test
    public void readStringFromContext() {
        // Given a mocked Context injected into the object under test...
        when(mMockContext.getString(R.string.app_name)).thenReturn(FAKE_APP_NAME);

        // ...then the result should be the expected one.
        MatcherAssert.assertThat(mMockContext.getString(R.string.app_name), is(FAKE_APP_NAME));
    }
}
