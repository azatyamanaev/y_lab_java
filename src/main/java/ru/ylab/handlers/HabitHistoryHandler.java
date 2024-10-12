package ru.ylab.handlers;

import java.util.Scanner;

import ru.ylab.App;
import ru.ylab.services.HabitHistoryService;
import ru.ylab.services.HabitService;

/**
 * Class for handling user inputs for {@link Page#HABIT_HISTORY_PAGE} page.
 *
 * @author azatyamanaev
 */
public class HabitHistoryHandler extends AbstractHandler {

    /**
     * Instance of a {@link HabitService}
     */
    private final HabitService habitService;

    /**
     * Instance of a {@link HabitHistoryService}
     */
    private final HabitHistoryService habitHistoryService;

    /**
     * Creates new HabitHistoryHandler
     *
     * @param habitService        HabitService instance
     * @param habitHistoryService HabitHistoryService instance
     */
    public HabitHistoryHandler(HabitService habitService, HabitHistoryService habitHistoryService) {
        this.habitService = habitService;
        this.habitHistoryService = habitHistoryService;
    }

    @Override
    public void handleInput(Scanner scanner) {
        switch (scanner.next()) {
            case "1":
                habitService.getHabits(scanner);
                break;
            case "2":
                habitHistoryService.markHabitCompleted(scanner);
                break;
            case "3":
                habitHistoryService.viewHabitHistory(scanner);
                break;
            case "4":
                App.redirect(Page.AUTHORIZED_USER_PAGE);
                break;
            case "5":
                App.logout();
                break;
            case "6":
                App.shutdown();
                break;
            default:
                System.out.println("Invalid input. Try again.");
        }
    }
}