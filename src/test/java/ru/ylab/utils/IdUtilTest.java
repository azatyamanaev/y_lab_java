package ru.ylab.utils;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

public class IdUtilTest {

    @Test
    public void testGenerateUserId() {
        Assertions.assertEquals(1L, IdUtil.generateUserId());
    }

    @Test
    public void testGenerateHabitId() {
        Assertions.assertEquals(1L, IdUtil.generateHabitId());
    }
}
