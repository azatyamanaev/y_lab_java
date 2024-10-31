package ru.ylab.mockito.conifg;

import java.time.LocalDate;
import java.util.HashMap;
import java.util.Map;

import ru.ylab.models.Habit;

public class TestDataConfig {


    protected static Map<Long, Habit> habits;
    protected static Map<Long, Habit> users;

    public static void initHabits() {
        habits = new HashMap<Long, Habit>();
        habits.put(-1L, Habit.builder()
                        .name("test_habit_1")
                        .description("d1")
                        .frequency(Habit.Frequency.DAILY)
                        .created(LocalDate.now().minusDays(30))
                        .build());
        habits.put(-2L, Habit.builder()
                             .name("test_habit_2")
                             .description("d2")
                             .frequency(Habit.Frequency.DAILY)
                             .created(LocalDate.now().minusDays(35))
                             .build());

        habits.put(-3L, Habit.builder()
                             .name("test_habit_3")
                             .description("d3")
                             .frequency(Habit.Frequency.WEEKLY)
                             .created(LocalDate.now().minusDays(40))
                             .build());

        habits.put(-4L, Habit.builder()
                             .name("test_habit_4")
                             .description("d4")
                             .frequency(Habit.Frequency.WEEKLY)
                             .created(LocalDate.now().minusDays(60))
                             .build());

        habits.put(-5L, Habit.builder()
                             .name("test_habit_5")
                             .description("d5")
                             .frequency(Habit.Frequency.MONTHLY)
                             .created(LocalDate.now().minusDays(90))
                             .build());

    }
}
