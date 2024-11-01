package ru.ylab.junit.utils;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import ru.ylab.utils.RegexMatcher;

public class RegexMatcherTest {

    @DisplayName("Test: match string to regex success")
    @Test
    public void testMatch() {
        Assertions.assertTrue(RegexMatcher.match("^[a-z0-9]+$", "abc1234"));
    }

    @DisplayName("Test: match string to regex fail")
    @Test
    public void testMatchFail() {
        Assertions.assertFalse(RegexMatcher.match("^[a-z0-9]+$", "Abc1234"));
    }

    @DisplayName("Test: match email success")
    @Test
    public void testMatchEmail() {
        Assertions.assertTrue(RegexMatcher.matchEmail("a@mail.ru"));
    }

    @DisplayName("Test: match email fail")
    @Test
    public void testMatchEmailFail() {
        Assertions.assertFalse(RegexMatcher.matchEmail("a123"));
    }
}
