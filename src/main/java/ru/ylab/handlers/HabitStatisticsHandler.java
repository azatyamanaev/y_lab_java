package ru.ylab.handlers;

import java.util.Scanner;

import ru.ylab.App;
import ru.ylab.services.HabitHistoryService;

/**
 * Class for handling user inputs for {@link Page#HABIT_STATISTICS_PAGE} page.
 *
 * @author azatyamanaev
 */
public class HabitStatisticsHandler extends AbstractHandler {

    /**
     * Instance of a {@link HabitHistoryService}
     */
    private final HabitHistoryService habitHistoryService;

    /**
     * Creates new HabitStatisticsHandler
     *
     * @param scanner             scanner for reading user input
     * @param habitHistoryService HabitHistoryService instance
     */
    public HabitStatisticsHandler(Scanner scanner, HabitHistoryService habitHistoryService) {
        super(scanner);
        this.habitHistoryService = habitHistoryService;
    }

    @Override
    public void handleInput() {
        switch (scanner.next()) {
            case "1":
                habitHistoryService.habitCompletionStreak();
                break;
            case "2":
                habitHistoryService.habitCompletionPercent();
                break;
            case "3":
                habitHistoryService.habitCompletionReport();
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
