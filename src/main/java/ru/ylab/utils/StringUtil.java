package ru.ylab.utils;

/**
 * Class for working with strings.
 *
 * @author azatayamanaev
 */
public class StringUtil {

    /**
     * Checks whether string is empty.
     *
     * @param value string to check
     * @return whether string is empty
     */
    public static boolean isEmpty(String value) {
        return value == null || value.isBlank();
    }
}
