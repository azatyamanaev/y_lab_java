package ru.ylab.unit.junit.utils;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import ru.ylab.utils.RegexMatcher;

public class RegexMatcherTest {

    @Test
    public void testMatch() {
        Assertions.assertTrue(RegexMatcher.match("^[a-z0-9]+$", "abc1234"));
    }

    @Test
    public void testMatchFail() {
        Assertions.assertFalse(RegexMatcher.match("^[a-z0-9]+$", "Abc1234"));
    }

    @Test
    public void testMatchEmail() {
        Assertions.assertTrue(RegexMatcher.matchEmail("a@mail.ru"));
    }

    @Test
    public void testMatchEmailFail() {
        Assertions.assertFalse(RegexMatcher.matchEmail("a123"));
    }
}
