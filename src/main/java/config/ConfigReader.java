package config;

import java.io.InputStream;
import java.util.Properties;

public class ConfigReader {

    private Properties prop;

    public ConfigReader() {

        try {
            prop = new Properties();

            InputStream input = getClass()
                    .getClassLoader()
                    .getResourceAsStream("config.properties");

            if (input == null) {
                System.out.println("config.properties file NOT found!");
                return;
            }

            prop.load(input);

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public String get(String key) {
        return prop.getProperty(key);
    }
}
