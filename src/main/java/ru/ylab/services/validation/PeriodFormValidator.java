package ru.ylab.services.validation;

import java.time.LocalDate;

import ru.ylab.dto.in.PeriodForm;
import ru.ylab.exception.HttpException;
import ru.ylab.utils.constants.ErrorConstants;

/**
 * Validator for validating {@link PeriodForm}.
 *
 * @author azatyamanaev
 */
public class PeriodFormValidator implements Validator<PeriodForm> {

    @Override
    public void validate(PeriodForm data) {
        isEmpty(data);

        HttpException exception = HttpException.validationError();

        if (data.getFrom() != null && data.getFrom().isAfter(LocalDate.now())) {
            exception.addDetail(ErrorConstants.INVALID_PARAMETER, "from");
        }

        exception.throwIfErrorsNotEmpty();
    }
}
