package ru.ylab.core.mockito.config;

import java.time.LocalDate;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import ru.ylab.core.dto.in.PeriodForm;
import ru.ylab.core.dto.out.HabitCompletionPercent;
import ru.ylab.core.dto.out.HabitCompletionStreak;
import ru.ylab.core.dto.out.HabitHistoryProjection;
import ru.ylab.core.models.Habit;
import ru.ylab.core.models.User;

public class TestConfigurer {

    public static User getTestAdmin() {
        return User.builder()
                   .id(0L)
                   .name("admin_test")
                   .email("admin_test@mail.ru")
                   .password("admin")
                   .role(User.Role.ADMIN)
                   .build();
    }

    public static User getTestUser() {
        return User.builder()
                   .id(1L)
                   .name("user1")
                   .email("a_test@mail.ru")
                   .password("123")
                   .role(User.Role.USER)
                   .build();
    }

    public static List<User> getUsers() {
        return List.of(
                getTestAdmin(),
                getTestUser(),
                User.builder()
                    .id(2L)
                    .name("user2")
                    .email("b_test@mail.ru")
                    .password("456")
                    .role(User.Role.USER)
                    .build());
    }

    public static Habit getOneHabit() {
        return Habit.builder()
                    .id(1L)
                    .name("h1_test")
                    .description("desc1")
                    .frequency(Habit.Frequency.DAILY)
                    .created(LocalDate.now().minusDays(30))
                    .userId(1L)
                    .build();
    }

    public static List<Habit> getHabits() {
        return List.of(
                getOneHabit(),
                Habit.builder()
                     .id(2L)
                     .name("h2_test")
                     .description("desc2")
                     .frequency(Habit.Frequency.DAILY)
                     .created(LocalDate.now().minusDays(35))
                     .userId(1L)
                     .build(),
                Habit.builder()
                     .id(3L)
                     .name("h3_test")
                     .description("desc3")
                     .frequency(Habit.Frequency.WEEKLY)
                     .created(LocalDate.now().minusDays(40))
                     .userId(1L)
                     .build(),
                Habit.builder()
                     .id(4L)
                     .name("hb1_test")
                     .description("ds1")
                     .frequency(Habit.Frequency.WEEKLY)
                     .created(LocalDate.now().minusDays(25))
                     .userId(2L)
                     .build(),
                Habit.builder()
                     .id(5L)
                     .name("hb2_test")
                     .description("ds2")
                     .frequency(Habit.Frequency.WEEKLY)
                     .created(LocalDate.now().minusDays(40))
                     .userId(2L)
                     .build(),
                Habit.builder()
                     .id(6L)
                     .name("hb3_test")
                     .description("ds3")
                     .frequency(Habit.Frequency.MONTHLY)
                     .created(LocalDate.now().minusDays(55))
                     .userId(2L)
                     .build());
    }

    public static HabitHistoryProjection getHabitHistoryProjection() {
        return HabitHistoryProjection.builder()
                                     .habitName("h1_test")
                                     .days(Set.of(
                                             LocalDate.parse("2024-10-02"),
                                             LocalDate.parse("2024-10-05"),
                                             LocalDate.parse("2024-10-10")))
                                     .build();
    }

    public static List<HabitCompletionStreak> habitCompletionStreak() {
        return List.of(
                new HabitCompletionStreak("h1_test", 1),
                new HabitCompletionStreak("h2_test", 0),
                new HabitCompletionStreak("h3_test", 0)
        );
    }

    public static List<HabitCompletionPercent> habitCompletionPercent(PeriodForm form) {
        return List.of(
                new HabitCompletionPercent("h1_test", "0%", form.getFrom(), form.getTo()),
                new HabitCompletionPercent("h2_test", "0%", form.getFrom(), form.getTo()),
                new HabitCompletionPercent("h3_test", "0%", form.getFrom(), form.getTo())
        );
    }


    public static List<HabitHistoryProjection> habitCompletionReport() {
        return List.of(
                getHabitHistoryProjection(),
                HabitHistoryProjection.builder()
                                      .habitName("h2_test")
                                      .days(new HashSet<>())
                                      .build(),
                HabitHistoryProjection.builder()
                                      .habitName("h3_test")
                                      .days(new HashSet<>())
                                      .build());
    }
}
