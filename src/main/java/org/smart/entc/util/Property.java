package org.smart.entc.util;

import org.slf4j.LoggerFactory;

import java.util.MissingResourceException;
import java.util.ResourceBundle;

/**
 * Project YIT DIA
 * Created by jaykrish on 5/28/14.
 */
public class Property {
    public static final String BUNDLE_NAME = "org.smart.entc.app";
    private static ResourceBundle resourceBundle = ResourceBundle.getBundle(BUNDLE_NAME);
    private static final org.slf4j.Logger LOGGER = LoggerFactory.getLogger(Property.class);

    /**
     *
     * @param key
     * @return
     */
    public static String getValue(final String key) {
        LOGGER.debug("Property for key:"+key);
        try {
            LOGGER.debug("Property is:"+key);
            String s=resourceBundle.getString(key);
            return s;
        } catch (MissingResourceException e) {
            LOGGER.error("Resource Missing");
            return "Error";
        }
    }
}
