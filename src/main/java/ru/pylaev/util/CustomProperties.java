package ru.pylaev.util;

import java.util.PropertyResourceBundle;

public record CustomProperties(String fileName) {

    public String getPropertyContent(String property) {
        PropertyResourceBundle properties = (PropertyResourceBundle) PropertyResourceBundle.getBundle(fileName);
        return properties.getString(property);
    }
}
