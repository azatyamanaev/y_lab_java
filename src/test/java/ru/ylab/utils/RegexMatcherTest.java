package ru.ylab.utils;

import org.junit.Assert;
import org.junit.Test;

public class RegexMatcherTest {

    @Test
    public void testMatch() {
        Assert.assertTrue(RegexMatcher.match("^[a-z0-9]+$", "abc1234"));
    }

    @Test
    public void testMatchFail() {
        Assert.assertFalse(RegexMatcher.match("^[a-z0-9]+$", "Abc1234"));
    }

    @Test
    public void testMatchEmail() {
        Assert.assertTrue(RegexMatcher.matchEmail("a@mail.ru"));
    }

    @Test
    public void testMatchEmailFail() {
        Assert.assertFalse(RegexMatcher.matchEmail("a123"));
    }
}
