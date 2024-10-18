package ru.ylab.utils;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Class for matching strings to regex.
 *
 * @author azatyamanaev
 */
public class RegexMatcher {

    /**
     * Email regex.
     */
    private static final String EMAIL_REGEX = "^[A-Za-z0-9]+@(mail.ru)$";

    /**
     * Matches string to regex.
     *
     * @param regex regex to match to
     * @param value string to match
     * @return whether string matches regex
     */
    public static boolean match(String regex, String value) {
        Pattern pattern = Pattern.compile(regex);
        Matcher matcher = pattern.matcher(value);
        return matcher.find();
    }

    /**
     * Matches string to {@link RegexMatcher#EMAIL_REGEX}.
     *
     * @param email email to match
     * @return whether email matches regex.
     */
    public static boolean matchEmail(String email) {
        return match(EMAIL_REGEX, email);
    }
}
