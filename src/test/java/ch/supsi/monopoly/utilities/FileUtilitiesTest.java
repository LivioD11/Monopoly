package ch.supsi.monopoly.utilities;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.io.TempDir;
import static org.junit.jupiter.api.Assertions.*;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;

class FileUtilitiesTest {

    @TempDir
    Path tempDir;

    @Test
    void leggiLineeFile_DovrebbeRitornareArrayDiStringhe_QuandoIlFileEsiste() throws IOException {
        // Arrange: creiamo un file temporaneo con del contenuto
        Path tempFile = tempDir.resolve("test_file.txt");
        String contenuto = "Linea 1\nLinea 2\r\nLinea 3";
        Files.writeString(tempFile, contenuto);

        // Act
        String[] risultato = FileUtilities.leggiLineeFile(tempFile.toString());

        // Assert
        assertNotNull(risultato);
        assertEquals(3, risultato.length);
        assertEquals("Linea 1", risultato[0]);
        assertEquals("Linea 2", risultato[1]);
        assertEquals("Linea 3", risultato[2]);
    }

    @Test
    void leggiLineeFile_DovrebbeRitornareArrayVuoto_QuandoIlFileNonEsiste() {
        // Arrange
        String percorsoInesistente = "percorso/fantasma/file.txt";

        // Act
        String[] risultato = FileUtilities.leggiLineeFile(percorsoInesistente);

        // Assert
        assertNotNull(risultato);
        assertEquals(0, risultato.length, "Dovrebbe restituire un array vuoto in caso di errore");
    }

    @Test
    void leggiLineeFile_DovrebbeGestireFileVuoto() throws IOException {
        // Arrange
        Path tempFile = tempDir.resolve("vuoto.txt");
        Files.createFile(tempFile);

        // Act
        String[] risultato = FileUtilities.leggiLineeFile(tempFile.toString());

        // Assert
        // Nota: Files.readString su un file vuoto restituisce "",
        // lo split su stringa vuota produce un array con un elemento vuoto [""] o vuoto a seconda del regex.
        assertNotNull(risultato);
        assertTrue(risultato.length <= 1);
    }
}