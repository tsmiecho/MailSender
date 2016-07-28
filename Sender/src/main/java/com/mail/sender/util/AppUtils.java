package com.mail.sender.util;

import java.util.Locale;
import java.util.ResourceBundle;

/**
 * Util class
 *
 * @author Tomasz Smiechowicz
 */
public final class AppUtils {

    public static String getBundle(String key) {
        ResourceBundle labels = ResourceBundle.getBundle("configuration", Locale.getDefault());
        return labels.getString(key);
    }
}
