package ru.ylab.repositories;

import java.util.HashMap;
import java.util.Map;

import lombok.Getter;
import ru.ylab.models.Habit;
import ru.ylab.models.HabitHistory;
import ru.ylab.models.User;

/**
 * Class for storing entities data.
 *
 * @author azatyamanaev
 */
@Getter
public class Storage {

    /**
     * Map for storing users data.
     */
    private final Map<Long, User> users = new HashMap<>();

    /**
     * Map for storing habits data.
     */
    private final Map<Long, Habit> habits = new HashMap<>();

    /**
     * Map for storing habit history data.
     */
    private final Map<Long, HabitHistory> habitHistory = new HashMap<>();
}
