package ru.ylab.core.services.validation.validators;

import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import ru.ylab.core.repositories.UserRepository;
import ru.ylab.core.services.validation.annotations.EmailNotUsed;

@RequiredArgsConstructor
@Component
public class EmailNotUsedImpl implements ConstraintValidator<EmailNotUsed, String> {

    private final UserRepository userRepository;

    @Override
    public boolean isValid(String value, ConstraintValidatorContext context) {
        return !userRepository.existsByEmail(value);
    }
}
