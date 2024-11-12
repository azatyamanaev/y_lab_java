package ru.ylab.dto.in;

import java.time.LocalDate;

import io.swagger.v3.oas.annotations.Parameter;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;
import org.springdoc.core.annotations.ParameterObject;

/**
 * Habit completion percentage request form.
 *
 * @author azatyamanaev
 */
@Setter
@Getter
@ParameterObject
@EqualsAndHashCode
public class PeriodForm {

    /**
     * From which date to count habit completion percent.
     */
    @Parameter(description = "from which date to show habit completion percent")
    private LocalDate from;

    /**
     * Until which date to count habit completion percent.
     */
    @Parameter(description = "until which date to show habit completion percent")
    private LocalDate to;
}
