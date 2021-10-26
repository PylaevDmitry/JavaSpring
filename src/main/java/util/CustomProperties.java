package util;

import java.util.PropertyResourceBundle;

public class CustomProperties {
    private final String fileName;

    public CustomProperties (String fileName) {
        this.fileName = fileName;
    }

    public String getPropertyContent (String property ) {
        PropertyResourceBundle properties = (PropertyResourceBundle) PropertyResourceBundle.getBundle(fileName);
        return properties.getString(property);
    }

}
