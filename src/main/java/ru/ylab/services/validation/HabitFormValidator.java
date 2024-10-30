package ru.ylab.services.validation;

import java.util.List;

import ru.ylab.dto.in.HabitForm;
import ru.ylab.exception.HttpException;
import ru.ylab.models.Habit;
import ru.ylab.utils.constants.ErrorConstants;

/**
 * Validator for validating {@link HabitForm}.
 *
 * @author azatyamanaev
 */
public class HabitFormValidator implements Validator<HabitForm>{

    @Override
    public void validate(HabitForm data) {
        isEmpty(data);

        HttpException exception = HttpException.validationError();
        isEmptyString(data.getName(), "name", exception);
        isEmptyString(data.getDescription(), "description", exception);
        if (!isEmptyString(data.getFrequency(), "frequency", exception)
                && !List.of(Habit.Frequency.DAILY.name(), Habit.Frequency.WEEKLY.name(), Habit.Frequency.MONTHLY.name())
                        .contains(data.getFrequency())) {
            exception.addDetail(ErrorConstants.INVALID_PARAMETER, "frequency");
        }

        exception.throwIfErrorsNotEmpty();
    }
}
