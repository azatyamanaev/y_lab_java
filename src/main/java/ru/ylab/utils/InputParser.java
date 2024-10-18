package ru.ylab.utils;

import java.time.LocalDate;
import java.time.format.DateTimeParseException;
import java.util.Scanner;

import ru.ylab.models.Habit;
import ru.ylab.models.User;

/**
 * Class for parsing user inputs.
 *
 * @author azatyamanaev
 */
public class InputParser {

    /**
     * Parses user role from user input.
     *
     * @param scanner scanner for reading user input
     * @return user role in string format
     */
    public static String parseRole(Scanner scanner) {
        String input;
        String res;

        do
        {
            input = scanner.next();
            res = switch (input) {
                case "1" ->
                        User.Role.USER.toString();
                case "2" ->
                        User.Role.ADMIN.name();
                default ->
                        null;
            };
            if (res == null) {
                System.out.println("Invalid input. Enter role(1 - USER, 2 - ADMIN): ");
            }
        } while (res == null);

        return res;
    }

    /**
     * Parses habit frequency from user input.
     *
     * @param scanner scanner for reading user input
     * @return habit frequency in string format
     */
    public static String parseFrequency(Scanner scanner) {
        String input;
        String res;

        do
        {
            input = scanner.next();
            res = switch (input) {
                case "1" ->
                        Habit.Frequency.DAILY.name();
                case "2" ->
                        Habit.Frequency.WEEKLY.name();
                case "3" ->
                        Habit.Frequency.MONTHLY.name();
                default ->
                        null;
            };
            if (res == null) {
                System.out.println("Invalid input. Enter frequency(1 - daily, 2 - weekly, 3 - monthly): ");
            }
        } while (res == null);

        return res;
    }

    /**
     * Parses date from user input.
     *
     * @param scanner scanner for reading user input
     * @return parsed date
     */
    public static LocalDate parseDate(Scanner scanner) {
        String input;
        LocalDate res;

        do
        {
            input = scanner.next();
            try {
                res = LocalDate.parse(input);
            } catch (DateTimeParseException e) {
                System.out.println("Invalid input. enter date in format yyyy-MM-dd: ");
                res = null;
            }
        } while (res == null);

        return res;
    }

    /**
     * Parses 'c' key from user input.
     *
     * @param scanner scanner for reading user input
     */
    public static void parseCKey(Scanner scanner) {
        String input;
        do
        {
            System.out.println("Enter 'c' to continue");
            input = scanner.next();
        } while (!"c".equals(input));
    }
}
