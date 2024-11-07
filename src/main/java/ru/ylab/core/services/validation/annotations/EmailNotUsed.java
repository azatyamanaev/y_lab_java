package ru.ylab.core.services.validation.annotations;

import java.lang.annotation.Documented;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

import jakarta.validation.Constraint;
import jakarta.validation.Payload;
import ru.ylab.core.services.validation.validators.EmailNotUsedImpl;
import ru.ylab.core.utils.constants.ErrorConstants;

@Documented
@Constraint(validatedBy = EmailNotUsedImpl.class)
@Target({ElementType.FIELD, ElementType.PARAMETER})
@Retention(RetentionPolicy.RUNTIME)
public @interface EmailNotUsed {

    String message() default ErrorConstants.ALREADY_EXISTS;

    Class<?>[] groups() default {};

    Class<? extends Payload>[] payload() default {};
}
