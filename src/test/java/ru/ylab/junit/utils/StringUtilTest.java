package ru.ylab.junit.utils;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import ru.ylab.utils.StringUtil;

import static org.assertj.core.api.Assertions.assertThat;
import static ru.ylab.utils.constants.WebConstants.APP_CONTEXT_PATH;
import static ru.ylab.utils.constants.WebConstants.HABITS_URL;
import static ru.ylab.utils.constants.WebConstants.USER_URL;

public class StringUtilTest {

    @DisplayName("Test: string is empty on null string")
    @Test
    public void testIsEmptyNullString() {
        assertThat(StringUtil.isEmpty(null)).isTrue();
    }

    @DisplayName("Test: string is empty on blank string")
    @Test
    public void testIsEmptyBlankString() {
        assertThat(StringUtil.isEmpty("   ")).isTrue();
    }

    @DisplayName("Test: string is not empty")
    @Test
    public void testNotEmptyString() {
        assertThat(StringUtil.isEmpty("string")).isFalse();
    }

    @DisplayName("Test: parse req uri success")
    @Test
    public void testParseReqUri() {
        assertThat(StringUtil.parseReqUri(APP_CONTEXT_PATH + USER_URL + HABITS_URL))
                .isEqualTo("/user/habits");
    }

    @DisplayName("Test: parse req uri fail")
    @Test
    public void testParseReqUriFail() {
        assertThat(StringUtil.parseReqUri("/app/" + USER_URL + HABITS_URL))
                .isNotEqualTo("/user/habits");
    }
}
