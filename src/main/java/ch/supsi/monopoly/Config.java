package ch.supsi.monopoly;

import java.io.InputStream;
import java.util.Properties;

/**
 * Gestore centralizzato della configurazione del gioco Monopoly.
 * <p>
 * Questa classe carica i parametri dal file {@code config.properties} e fornisce
 * metodi statici per accedere a costanti di gioco, impostazioni economiche e limiti.
 * La ricerca di una chiave segue una gerarchia di priorità:
 * <ol>
 * <li>Proprietà di sistema Java ({@code System.getProperty})</li>
 * <li>Variabili d'ambiente ({@code System.getenv})</li>
 * <li>File di configurazione locale ({@code config.properties})</li>
 * </ol>
 * </p>
 */
public final class Config {

    /** Contenitore delle proprietà caricate dal file di risorse. */
    private static final Properties fileProps = new Properties();

    static {
        try (InputStream input =
                     Config.class.getClassLoader()
                             .getResourceAsStream("config.properties")) {

            if (input != null) {
                fileProps.load(input);
            }

        } catch (Exception e) {
            throw new RuntimeException("Errore critico nel caricamento del file di configurazione", e);
        }
    }

    /**
     * Costruttore privato per impedire l'istanziazione di questa classe utility.
     */
    private Config() {}

    /**
     * Recupera un valore di tipo String associato alla chiave specificata.
     * * @param key La chiave della proprietà da cercare.
     * @param defaultValue Il valore da restituire se la chiave non viene trovata in nessuna sorgente.
     * @return Il valore trovato o il valore predefinito fornito.
     */
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

    /**
     * Recupera un valore intero associato alla chiave specificata.
     * * @param key La chiave della proprietà da cercare.
     * @param defaultValue Il valore da restituire se la chiave non esiste o non è un numero valido.
     * @return Il valore intero trovato o il valore predefinito.
     */
    public static int getInt(String key, int defaultValue) {
        try {
            String value = getString(key, null);
            return value != null ? Integer.parseInt(value) : defaultValue;
        } catch (Exception e) {
            return defaultValue;
        }
    }

    /**
     * Recupera un valore booleano associato alla chiave specificata.
     * * @param key La chiave della proprietà da cercare.
     * @param defaultValue Il valore da restituire se la chiave non esiste.
     * @return {@code true} o {@code false} in base al valore trovato, altrimenti il valore predefinito.
     */
    public static boolean getBoolean(String key, boolean defaultValue) {
        String value = getString(key, null);
        return value != null ? Boolean.parseBoolean(value) : defaultValue;
    }
}