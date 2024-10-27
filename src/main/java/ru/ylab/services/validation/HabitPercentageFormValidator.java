package ru.ylab.services.validation;

import java.time.LocalDate;

import ru.ylab.dto.in.HabitPercentageForm;
import ru.ylab.exception.HttpException;
import ru.ylab.utils.constants.ErrorConstants;

/**
 * Validator for validating {@link HabitPercentageForm}.
 *
 * @author azatyamanaev
 */
public class HabitPercentageFormValidator implements Validator<HabitPercentageForm> {

    @Override
    public void validate(HabitPercentageForm data) {
        isEmpty(data);

        HttpException exception = HttpException.validationError();

        if (data.getFrom() != null && data.getFrom().isAfter(LocalDate.now())) {
            exception.addDetail(ErrorConstants.INVALID_PARAMETER, "from");
        }

        exception.throwIfErrorsNotEmpty();
    }
}
