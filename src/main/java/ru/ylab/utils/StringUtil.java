package ru.ylab.utils;

import ru.ylab.utils.constants.WebConstants;

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

    /**
     * Cuts context path from servlet request.
     *
     * @param uri http request uri
     * @return uri for concrete servlet
     */
    public static String parseReqUri(String uri) {
        return uri.substring(uri.lastIndexOf(WebConstants.APP_CONTEXT_PATH) + WebConstants.APP_CONTEXT_PATH.length());
    }
}
