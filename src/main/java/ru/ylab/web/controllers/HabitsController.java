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
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestAttribute;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import ru.ylab.aspects.LogRequest;
import ru.ylab.dto.in.HabitForm;
import ru.ylab.dto.in.HabitSearchForm;
import ru.ylab.dto.mappers.HabitMapper;
import ru.ylab.dto.out.HabitDto;
import ru.ylab.models.User;
import ru.ylab.services.entities.HabitService;
import ru.ylab.services.validation.HabitFormValidator;

import static ru.ylab.utils.constants.WebConstants.HABITS_URL;
import static ru.ylab.utils.constants.WebConstants.ID_URL;
import static ru.ylab.utils.constants.WebConstants.SEARCH_URL;
import static ru.ylab.utils.constants.WebConstants.USER_URL;

/**
 * Controller for handling habits HTTP requests.
 *
 * @author azatyamanaev
 */
@LogRequest
@RequiredArgsConstructor
@RestController
@RequestMapping(USER_URL + HABITS_URL)
public class HabitsController {

    private final HabitMapper habitMapper;
    private final HabitService habitService;
    private final HabitFormValidator habitFormValidator;

    @InitBinder
    public void initBinder(WebDataBinder binder) {
        binder.addValidators(habitFormValidator);
    }

    /**
     * Gets habit for user and writes it to response.
     */
    @GetMapping(ID_URL)
    public ResponseEntity<HabitDto> getHabit(@PathVariable("id") Long id, @RequestAttribute("currentUser") User user) {
        HabitDto habit = habitMapper.mapToDto(habitService.getForUser(user.getId(), id));
        return ResponseEntity.ok(habit);
    }

    /**
     * Gets habits for user and writes them to response.
     */
    @GetMapping
    public ResponseEntity<List<HabitDto>> getHabits(@RequestAttribute("currentUser") User user) {
        List<HabitDto> dtos = habitMapper.mapToDto(habitService.getHabitsForUser(user.getId()));
        return ResponseEntity.ok(dtos);
    }

    /**
     * Searches habits for user and writes them to response.
     */
    @GetMapping(SEARCH_URL)
    public ResponseEntity<List<HabitDto>> searchHabits(@RequestAttribute("currentUser") User user,
                                                       HabitSearchForm form) {
        List<HabitDto> dtos = habitMapper.mapToDto(habitService.searchHabitsForUser(user.getId(), form));
        return ResponseEntity.ok(dtos);
    }

    /**
     * Creates habit for user and sets created response status.
     */
    @PostMapping
    public ResponseEntity<Void> createHabit(@RequestAttribute("currentUser") User user,
                                              @Validated @RequestBody HabitForm form) {
        habitService.create(user.getId(), form);
        return ResponseEntity.status(HttpStatus.CREATED).build();
    }

    /**
     * Updates habit for user and sets no content response status.
     */
    @PutMapping(ID_URL)
    public ResponseEntity<Void> updateHabit(@PathVariable("id") Long id,
                                              @RequestAttribute("currentUser") User user,
                                              @Validated @RequestBody HabitForm form) {
        habitService.updateForUser(user.getId(), id, form);
        return ResponseEntity.noContent().build();
    }

    /**
     * Deletes habit for user and sets no content response status.
     */
    @DeleteMapping(ID_URL)
    public ResponseEntity<Void> deleteHabit(@PathVariable("id") Long id,
                                              @RequestAttribute("currentUser") User user) {
        habitService.deleteForUser(user.getId(), id);
        return ResponseEntity.noContent().build();
    }
}
