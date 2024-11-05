package ru.ylab.web.controllers;

import java.util.List;

import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.InitBinder;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import ru.ylab.aspects.LogRequest;
import ru.ylab.dto.in.UserForm;
import ru.ylab.dto.in.UserSearchForm;
import ru.ylab.dto.mappers.UserMapper;
import ru.ylab.dto.out.UserDto;
import ru.ylab.services.entities.UserService;
import ru.ylab.services.validation.UserFormValidator;

import static ru.ylab.utils.constants.WebConstants.ADMIN_URL;
import static ru.ylab.utils.constants.WebConstants.ID_URL;
import static ru.ylab.utils.constants.WebConstants.SEARCH_URL;
import static ru.ylab.utils.constants.WebConstants.USERS_URL;

/**
 * Controller for handling users HTTP requests for admin.
 *
 * @author azatyamanaev
 */
@LogRequest
@RequiredArgsConstructor
@RestController
@RequestMapping(ADMIN_URL + USERS_URL)
public class AdminUsersController {

    private final UserMapper userMapper;
    private final UserService userService;
    private final UserFormValidator userFormValidator;

    @InitBinder
    public void initBinder(WebDataBinder binder) {
        binder.addValidators(userFormValidator);
    }

    /**
     * Gets user for admin and writes it to response.
     */
    @GetMapping(ID_URL)
    public ResponseEntity<UserDto> getUser(@PathVariable("id") Long id) {
        UserDto dto = userMapper.mapToDto(userService.get(id));
        return ResponseEntity.ok(dto);
    }

    /**
     * Gets users for admin and writes them to response.
     */
    @GetMapping
    public ResponseEntity<List<UserDto>> getUsers() {
        List<UserDto> dtos = userMapper.mapToDto(userService.getAll());
        return ResponseEntity.ok(dtos);
    }

    /**
     * Searches users for admin and writes them to response.
     */
    @GetMapping(SEARCH_URL)
    public ResponseEntity<List<UserDto>> searchUsers(UserSearchForm form) {
        List<UserDto> dtos = userMapper.mapToDto(userService.searchUsers(form));
        return ResponseEntity.ok(dtos);
    }

    /**
     * Creates user for admin and sets created response status.
     */
    @PostMapping
    public ResponseEntity<String> createUser(@Validated @RequestBody UserForm form) {
        userService.createByAdmin(form);
        return new ResponseEntity<>("", HttpStatus.CREATED);
    }

    /**
     * Deletes user for admin and sets no content response status.
     */
    @DeleteMapping(ID_URL)
    public ResponseEntity<String> deleteUser(@PathVariable("id") Long id) {
        userService.delete(id);
        return new ResponseEntity<>("", HttpStatus.NO_CONTENT);
    }
}
