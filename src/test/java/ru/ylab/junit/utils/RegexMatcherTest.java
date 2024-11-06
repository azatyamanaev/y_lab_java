package ru.ylab.junit.utils;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import ru.ylab.utils.RegexMatcher;

import static org.assertj.core.api.Assertions.assertThat;

public class RegexMatcherTest {

    @DisplayName("Test: match string to regex success")
    @Test
    public void testMatch() {
        assertThat(RegexMatcher.match("^[a-z0-9]+$", "abc1234")).isTrue();
    }

    @DisplayName("Test: match string to regex fail")
    @Test
    public void testMatchFail() {
        assertThat(RegexMatcher.match("^[a-z0-9]+$", "Abc1234")).isFalse();
    }

    @DisplayName("Test: match email success")
    @Test
    public void testMatchEmail() {
        assertThat(RegexMatcher.matchEmail("a@mail.ru")).isTrue();
    }

    @DisplayName("Test: match email fail")
    @Test
    public void testMatchEmailFail() {
        assertThat(RegexMatcher.matchEmail("a123")).isFalse();
    }
}
