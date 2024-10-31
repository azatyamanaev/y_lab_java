package ru.ylab.services.validation;

import org.jetbrains.annotations.NotNull;
import org.springframework.stereotype.Component;
import org.springframework.validation.Errors;
import org.springframework.validation.Validator;
import ru.ylab.dto.in.HabitForm;
import ru.ylab.utils.constants.ErrorConstants;

/**
 * Validator for validating {@link HabitForm}.
 *
 * @author azatyamanaev
 */
@Component
public class HabitFormValidator implements Validator, DtoValidator {

    @Override
    public boolean supports(@NotNull Class<?> clazz) {
        return HabitForm.class.equals(clazz);
    }

    @Override
    public void validate(@NotNull Object target, @NotNull Errors errors) {
        HabitForm form = (HabitForm) target;

        if (isEmptyString(form.getName())) {
            errors.rejectValue("name", ErrorConstants.EMPTY_PARAM);
        }

        if (isEmptyString(form.getDescription())) {
            errors.rejectValue("description", ErrorConstants.EMPTY_PARAM);
        }

        if (form.getFrequency() == null) {
            errors.rejectValue("frequency", ErrorConstants.EMPTY_PARAM);
        }
    }
}
