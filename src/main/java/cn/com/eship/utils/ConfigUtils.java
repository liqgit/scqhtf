package cn.com.eship.utils;

import java.util.Properties;

/**
 * Created by simon on 16/9/13.
 */
public class ConfigUtils {
    public static String readValue(String configPath, String key) throws Exception {
        Properties properties = new Properties();
        properties.load(ConfigUtils.class.getClassLoader().getResourceAsStream(configPath));
        return properties.getProperty(key);
    }

    public static Properties readProperties(String configPath) throws Exception {
        Properties properties = new Properties();
        properties.load(ConfigUtils.class.getClassLoader().getResourceAsStream(configPath));
        return properties;
    }

}
