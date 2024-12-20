package ru.ylab.web.controllers;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestAttribute;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import ru.spring.auditstarter.annotations.AuditRequest;
import ru.ylab.dto.in.SignUpForm;
import ru.ylab.dto.mappers.UserMapper;
import ru.ylab.dto.out.UserDto;
import ru.ylab.models.User;
import ru.ylab.services.entities.UserService;

import static ru.ylab.utils.constants.WebConstants.SELF_URL;
import static ru.ylab.utils.constants.WebConstants.USER_URL;

/**
 * Controller for handling user profile HTTP requests for user.
 *
 * @author azatyamanaev
 */
@AuditRequest
@RequiredArgsConstructor
@RestController
@RequestMapping(USER_URL + SELF_URL)
public class UserProfileController {

    private final UserMapper userMapper;
    private final UserService userService;

    /**
     * Gets user data for user and writes it to response.
     */
    @GetMapping
    public ResponseEntity<UserDto> getProfile(@RequestAttribute("currentUser") User user) {
        UserDto dto = userMapper.mapToDto(userService.get(user.getId()));
        return ResponseEntity.ok(dto);
    }

    /**
     * Updates user profile for user and sets no content response status.
     */
    @PutMapping
    public ResponseEntity<Void> updateProfile(@RequestAttribute("currentUser") User user,
                                                @Valid @RequestBody SignUpForm form) {
        userService.update(user.getId(), form);
        return ResponseEntity.noContent().build();
    }

    /**
     * Deletes user profile for user and sets no content response status.
     */
    @DeleteMapping
    public ResponseEntity<Void> deleteProfile(@RequestAttribute("currentUser") User user) {
        userService.delete(user.getId());
        return ResponseEntity.noContent().build();

    }
}
