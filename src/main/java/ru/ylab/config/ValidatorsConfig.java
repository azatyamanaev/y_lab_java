package ru.ylab.config;

import lombok.Getter;
import ru.ylab.dto.in.HabitForm;
import ru.ylab.dto.in.PeriodForm;
import ru.ylab.dto.in.HabitSearchForm;
import ru.ylab.dto.in.SignInForm;
import ru.ylab.dto.in.SignUpForm;
import ru.ylab.dto.in.UserSearchForm;
import ru.ylab.services.validation.HabitFormValidator;
import ru.ylab.services.validation.PeriodFormValidator;
import ru.ylab.services.validation.HabitSearchFormValidator;
import ru.ylab.services.validation.SignInFormValidator;
import ru.ylab.services.validation.UserFormValidator;
import ru.ylab.services.validation.UserSearchFormValidator;
import ru.ylab.services.validation.Validator;

/**
 * Validators configuration class.
 *
 * @author azatyamanaev
 */
@Getter
public class ValidatorsConfig {

    /**
     * Instance of a {@link Validator<SignInForm>}.
     */
    private final Validator<SignInForm> signInValidator;

    /**
     * Instance of a {@link Validator<SignUpForm>}.
     */
    private final Validator<SignUpForm> userFormValidator;

    /**
     * Instance of a {@link Validator<UserSearchForm>}.
     */
    private final Validator<UserSearchForm> userSearchFormValidator;

    /**
     * Instance of a {@link Validator<HabitForm>}.
     */
    private final Validator<HabitForm> habitFormValidator;

    /**
     * Instance of a {@link Validator<HabitSearchForm>}.
     */
    private final Validator<HabitSearchForm> habitSearchFormValidator;

    /**
     * Instance of a {@link Validator< PeriodForm >}.
     */
    private final Validator<PeriodForm> habitPercentageFormValidator;

    /**
     * Creates new ValidatorsConfig.
     *
     * @param config repositories
     */
    public ValidatorsConfig(RepositoriesConfig config) {
        this.signInValidator = new SignInFormValidator();
        this.userFormValidator = new UserFormValidator(config.getUserRepository());
        this.userSearchFormValidator = new UserSearchFormValidator();
        this.habitFormValidator = new HabitFormValidator();
        this.habitSearchFormValidator = new HabitSearchFormValidator();
        this.habitPercentageFormValidator = new PeriodFormValidator();
    }
}
