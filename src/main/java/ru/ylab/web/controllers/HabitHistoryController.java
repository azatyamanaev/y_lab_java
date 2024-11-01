package ru.ylab.web.controllers;

import java.time.LocalDate;
import java.util.List;

import lombok.RequiredArgsConstructor;
import org.springdoc.core.annotations.ParameterObject;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import ru.ylab.aspects.LogRequest;
import ru.ylab.dto.in.PeriodForm;
import ru.ylab.dto.out.HabitCompletionPercent;
import ru.ylab.dto.out.HabitCompletionStreak;
import ru.ylab.dto.out.HabitHistoryProjection;
import ru.ylab.models.User;
import ru.ylab.services.entities.HabitHistoryService;

import static ru.ylab.utils.constants.WebConstants.HABIT_HISTORY_URL;
import static ru.ylab.utils.constants.WebConstants.HABIT_PERCENTAGE_URL;
import static ru.ylab.utils.constants.WebConstants.HABIT_REPORT_URL;
import static ru.ylab.utils.constants.WebConstants.HABIT_STREAK_URL;
import static ru.ylab.utils.constants.WebConstants.ID_URL;
import static ru.ylab.utils.constants.WebConstants.USER_URL;

/**
 * Controller for handling habit history HTTP requests.
 *
 * @author azatyamanaev
 */
@LogRequest
@RequiredArgsConstructor
@RestController
@RequestMapping(USER_URL + HABIT_HISTORY_URL)
public class HabitHistoryController {

    /**
     * Instance of a {@link HabitHistoryService}.
     */
    private final HabitHistoryService habitHistoryService;

    /**
     * Gets habit history for user and writes it to response.
     */
    @GetMapping(ID_URL)
    public ResponseEntity<HabitHistoryProjection> getHabitHistory(@PathVariable("id") Long id,
                                                                  @RequestAttribute("currentUser") User user) {
        HabitHistoryProjection projection = habitHistoryService.getHabitHistory(user.getId(), id);
        return ResponseEntity.ok(projection);
    }

    /**
     * Marks habit completed for user.
     */
    @PutMapping(ID_URL)
    public ResponseEntity<String> markHabitCompleted(@PathVariable("id") Long id, @RequestAttribute("currentUser") User user,
                                                     @RequestParam("completed_on") LocalDate completedOn) {
        habitHistoryService.markHabitCompleted(user.getId(), id, completedOn);
        return new ResponseEntity<>("", HttpStatus.NO_CONTENT);
    }

    /**
     * Gets habits completion streaks for user and writes them to response.
     */
    @GetMapping(HABIT_STREAK_URL)
    public ResponseEntity<List<HabitCompletionStreak>> getHabitStreak(@RequestAttribute("currentUser") User user) {
        List<HabitCompletionStreak> streaks = habitHistoryService.habitCompletionStreak(user.getId());
        return ResponseEntity.ok(streaks);
    }

    /**
     * Gets habits completion percentage for user and writes it to response.
     */
    @GetMapping(HABIT_PERCENTAGE_URL)
    public ResponseEntity<List<HabitCompletionPercent>> getHabitPercentage(@RequestAttribute("currentUser") User user,
                                                                           @ParameterObject PeriodForm form) {
        List<HabitCompletionPercent> percentage = habitHistoryService.habitCompletionPercent(user.getId(), form);
        return ResponseEntity.ok(percentage);
    }

    /**
     * Gets habits completion report for user and writes it to response.
     */
    @GetMapping(HABIT_REPORT_URL)
    public ResponseEntity<List<HabitHistoryProjection>> getHabitReport(@RequestAttribute("currentUser") User user) {
        return ResponseEntity.ok(habitHistoryService.habitCompletionReport(user.getId()));
    }
}
