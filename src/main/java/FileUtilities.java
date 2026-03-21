import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;

class FileUtilities {
    public static String[] leggiLineeFile(String percorsoFile) {
        String[] lineeFile = null;
        try {
            lineeFile = Files.readString(Path.of(percorsoFile)).split("(\\r\\n|\\r|\\n)");
        } catch (IOException e) {
            System.out.println("Problemi leggendo il file " + percorsoFile);
            return new String[0];
        }
        return lineeFile;
    }
}