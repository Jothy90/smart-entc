package org.smart.entc.util;

import java.util.MissingResourceException;
import java.util.ResourceBundle;

/**
 * Project YIT DIA
 * Created by jaykrish on 5/28/14.
 */
public class Property {
    public static final String BUNDLE_NAME = "org.smart.entc.app";
    private static ResourceBundle resourceBundle = ResourceBundle.getBundle(BUNDLE_NAME);

    /**
     *
     * @param key
     * @return
     */
    public static String getValue(final String key) {
        try {
            return resourceBundle.getString(key);
        } catch (MissingResourceException e) {
            return "Error";
        }
    }
}
