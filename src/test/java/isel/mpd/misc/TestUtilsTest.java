package isel.mpd.misc;

import junit.framework.AssertionFailedError;
import org.junit.Test;

import static org.junit.Assert.*;

/**
 * Created by lfalcao on 13/05/2015.
 */
public class TestUtilsTest {
    @Test
    public void shouldReturnWithNoErrorWhenAllValuesAreEqual() throws Exception {
        TestUtils.executeAndCheckResult(()-> 1, 1);

    }

    @Test(expected = AssertionFailedError.class)
    public void shouldReturnWithErrorWhenSomeValueIsNotEqual() throws Exception {
        TestUtils.executeAndCheckResult(()-> 1, 2);

    }


}