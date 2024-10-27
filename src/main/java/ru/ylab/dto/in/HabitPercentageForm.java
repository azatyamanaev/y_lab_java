package ru.ylab.dto.in;

import java.time.LocalDate;

import lombok.Getter;
import lombok.Setter;

/**
 * Habit completion percentage request form.
 *
 * @author azatyamanaev
 */
@Setter
@Getter
public class HabitPercentageForm {

    /**
     * From which date to count habit completion percent.
     */
    private LocalDate from;

    /**
     * Until which date to count habit completion percent.
     */
    private LocalDate to;
}
