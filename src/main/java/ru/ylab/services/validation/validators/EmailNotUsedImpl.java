package ru.ylab.services.validation.validators;

import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import ru.ylab.repositories.UserRepository;
import ru.ylab.services.validation.annotations.EmailNotUsed;

@RequiredArgsConstructor
@Component
public class EmailNotUsedImpl implements ConstraintValidator<EmailNotUsed, String> {

    private final UserRepository userRepository;

    @Override
    public boolean isValid(String value, ConstraintValidatorContext context) {
        return !userRepository.existsByEmail(value);
    }
}
