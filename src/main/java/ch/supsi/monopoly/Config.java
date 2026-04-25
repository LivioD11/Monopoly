package ch.supsi.monopoly;

import java.io.InputStream;
import java.util.Properties;

public final class Config {

    private static final Properties fileProps = new Properties();

    static {
        try (InputStream input =
                     Config.class.getClassLoader()
                             .getResourceAsStream("config.properties")) {

            if (input != null) {
                fileProps.load(input);
            }

        } catch (Exception e) {
            throw new RuntimeException("Errore caricamento config", e);
        }
    }

    private Config() {} // impedisce istanziazione

    public static String getString(String key, String defaultValue) {

        String value = System.getProperty(key);

        if (value == null) {
            value = System.getenv(key);
        }

        if (value == null) {
            value = fileProps.getProperty(key);
        }

        return value != null ? value : defaultValue;
    }

    public static int getInt(String key, int defaultValue) {
        try {
            return Integer.parseInt(getString(key, null));
        } catch (Exception e) {
            return defaultValue;
        }
    }

    public static boolean getBoolean(String key, boolean defaultValue) {
        String value = getString(key, null);
        return value != null ? Boolean.parseBoolean(value) : defaultValue;
    }
}