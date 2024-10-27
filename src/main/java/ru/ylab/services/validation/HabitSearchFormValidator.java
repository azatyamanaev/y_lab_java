package ru.ylab.services.validation;

import java.time.LocalDate;
import java.util.List;

import ru.ylab.dto.in.HabitSearchForm;
import ru.ylab.exception.HttpException;
import ru.ylab.models.Habit;
import ru.ylab.utils.constants.ErrorConstants;

/**
 * Validator for validating {@link HabitSearchForm}.
 *
 * @author azatyamanaev
 */
public class HabitSearchFormValidator implements Validator<HabitSearchForm> {

    @Override
    public void validate(HabitSearchForm data) {
        isEmpty(data);

        HttpException exception = HttpException.validationError();

        if (data.getName() != null && !data.getName().isBlank()) {
            exception.addDetail(ErrorConstants.EMPTY_PARAM, "name");
        }
        if (data.getFrequency() != null && !data.getFrequency().isBlank()) {
            exception.addDetail(ErrorConstants.EMPTY_PARAM, "frequency");
        } else if (data.getFrequency() != null
                && !List.of(Habit.Frequency.DAILY.name(), Habit.Frequency.WEEKLY.name(), Habit.Frequency.MONTHLY.name())
                        .contains(data.getFrequency())) {
            exception.addDetail(ErrorConstants.INVALID_PARAMETER, "frequency");
        }
        if (data.getFrom() != null && data.getFrom().isAfter(LocalDate.now())) {
            exception.addDetail(ErrorConstants.INVALID_PARAMETER, "from");
        }

        exception.throwIfErrorsNotEmpty();
    }
}
