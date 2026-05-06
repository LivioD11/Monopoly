package ch.supsi.monopoly;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

class ConfigTest {

    @Test
    void testConfigFileIsLoaded() {
        // Verifica che il file esista e venga letto.
        // Sostituisci "chiave.di.esempio" con una chiave che hai realmente nel file config.properties
        String value = Config.getString("chiave.di.esempio", "default");

        assertNotEquals("default", value, "Il valore dovrebbe essere letto dal file, non restituire il default");
    }

    @Test
    void testPrioritySystemProperty() {
        // Testiamo la gerarchia: System Property deve vincere su tutto
        String key = "test.priority.key";
        System.setProperty(key, "system-value");

        assertEquals("system-value", Config.getString(key, "default"));

        System.clearProperty(key);
    }

    @Test
    void testGetInt() {
        // Test con valore valido (se presente nel file o iniettato)
        assertEquals(100, Config.getInt("test.int", 0));

        // Test con valore non numerico (deve restituire il default)
        System.setProperty("test.notint", "abc");

        System.clearProperty("test.int");
        System.clearProperty("test.notint");
    }

    @Test
    void testGetBoolean() {
        assertTrue(Config.getBoolean("test.bool", false));

        System.setProperty("test.bool", "false");
        assertFalse(Config.getBoolean("test.bool", true));

        assertTrue(Config.getBoolean("non.existent", true));

        System.clearProperty("test.bool");
    }

    @Test
    void testDefaultValues() {
        // Verifica che se la chiave non esiste da nessuna parte, ritorni il default
        String randomKey = "key.che.non.esiste." + System.currentTimeMillis();
        assertEquals("fallback", Config.getString(randomKey, "fallback"));
    }
}
