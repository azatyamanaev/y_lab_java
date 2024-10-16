package ru.ylab.utils;

/**
 * Class for generating entity ids.
 *
 * @author azatyamanaev
 */
public class IdUtil {

    /**
     * User id generator.
     */
    private static Long userIdSequence = 0L;

    /**
     * Habit id generator.
     */
    private static Long habitIdSequence = 0L;

    /**
     * Generates next user id.
     *
     * @return user id
     */
    public static long generateUserId() {
        return ++userIdSequence;
    }

    /**
     * Generates next habit id.
     *
     * @return habit id
     */
    public static long generateHabitId() {
        return ++habitIdSequence;
    }
}
