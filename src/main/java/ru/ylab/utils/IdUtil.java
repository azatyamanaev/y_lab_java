package ru.ylab.utils;

import java.util.concurrent.atomic.AtomicLong;

/**
 * Class for generating entity ids.
 *
 * @author azatyamanaev
 */
public class IdUtil {

    /**
     * User id generator.
     */
    private static final AtomicLong userIdSequence = new AtomicLong(1);

    /**
     * Habit id generator.
     */
    private static final AtomicLong habitIdSequence = new AtomicLong(1);

    /**
     * Generates next user id.
     *
     * @return user id
     */
    public static long generateUserId() {
        return userIdSequence.getAndIncrement();
    }

    /**
     * Generates next habit id.
     *
     * @return habit id
     */
    public static long generateHabitId() {
        return habitIdSequence.getAndIncrement();
    }
}
