package ru.ylab.utils.constants;

import org.intellij.lang.annotations.Language;

/**
 * Class containing global app constants
 *
 * @author azatyamanaev
 */
public interface AppConstants {

    String TEST_PROFILE = "test";
    String DEV_PROFILE = "dev";

    @Language("RegExp")
    String EMAIL_REGEX = "^[A-Za-z0-9_]+@(mail.ru)$";
}
