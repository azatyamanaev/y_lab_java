package ru.ylab.utils;

import org.junit.Assert;
import org.junit.Test;

public class IdUtilTest {

    @Test
    public void testGenerateUserId() {
        Assert.assertEquals(1L, IdUtil.generateUserId());
    }

    @Test
    public void testGenerateHabitId() {
        Assert.assertEquals(1L, IdUtil.generateHabitId());
    }
}
