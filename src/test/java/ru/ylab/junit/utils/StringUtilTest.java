package ru.ylab.junit.utils;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import ru.ylab.utils.StringUtil;

import static ru.ylab.utils.constants.WebConstants.APP_CONTEXT_PATH;
import static ru.ylab.utils.constants.WebConstants.HABITS_URL;
import static ru.ylab.utils.constants.WebConstants.USER_URL;

public class StringUtilTest {

    @Test
    public void testIsEmptyNullString() {
        Assertions.assertTrue(StringUtil.isEmpty(null));
    }

    @Test
    public void testIsEmptyBlankString() {
        Assertions.assertTrue(StringUtil.isEmpty("   "));
    }

    @Test
    public void testNotEmptyString() {
        Assertions.assertFalse(StringUtil.isEmpty("string"));
    }

    @Test
    public void testParseReqUri() {
        Assertions.assertEquals("/user/habits",
                StringUtil.parseReqUri(APP_CONTEXT_PATH + USER_URL + HABITS_URL));
    }

    @Test
    public void testParseReqUriFail() {
        Assertions.assertNotEquals("/user/habits",
                StringUtil.parseReqUri("/app/" + USER_URL + HABITS_URL));
    }
}
