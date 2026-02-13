import org.junit.jupiter.api.Test;

import java.util.Collections;
import java.util.List;
import java.util.Map;
import static org.junit.jupiter.api.Assertions.*;

public class ProgramControlTest { //this is here to control the behavior
    static class PCFakeFileHandler implements FileHandler{
        private final List<String> files;
        private final Map<String, String> contents;

        PCFakeFileHandler (List<String> files, Map<String, String> contents) {
            super();
            this.files = files;
            this.contents = contents;
        }

        @Override
        public List<String> listFiles() {
            return files;
        }

        @Override
        public String readFile(String filename) {
            return contents.get(filename);
        }
    }

    @Test
    void noFiles() { //test for if no files are in directory at all
        PCFakeFileHandler fileHandler = new PCFakeFileHandler(Collections.emptyList(), Map.of());
        ProgramControl pc1 = new ProgramControl(fileHandler);
        String result = pc1.requestControl(new String[]{});
        assertEquals("No files in directory", result);
    }

    @Test
    void noArguments() { //test for if no argument is entered
        List<String> files = List.of("file1.txt", "file2.txt");

        PCFakeFileHandler fileHandler = new PCFakeFileHandler(files, Map.of());
        ProgramControl pc2 = new ProgramControl(fileHandler);
        String result = pc2.requestControl(new String[]{});
        String expected = "Files in directory: " + "01 file1.txt\n" + "02 file2.txt\n";
        assertEquals(expected, result);
    }

    @Test
    void validNumberArguments() { //test for if a argument entered is a valid number
        List<String> files = List.of("file1.txt", "file2.txt");
        Map<String, String> contents = Map.of("file1.txt", "file 1 content",  "file2.txt", "file 2 content");
        PCFakeFileHandler fileHandler = new PCFakeFileHandler(files, contents);
        ProgramControl pc3 = new ProgramControl(fileHandler);
        String result = pc3.requestControl(new String[]{"1"});
        assertEquals("file 1 content", result);
    }

    @Test
    void numberThatDoesNotExist() { //test for if an invalid number is entered as an argument
        List<String> files = List.of("file1.txt", "file2.txt");
        Map<String, String> contents = Map.of("file1.txt", "file 1 content",  "file2.txt", "file 2 content");
        PCFakeFileHandler fileHandler = new PCFakeFileHandler(files, contents);
        ProgramControl pc4 = new ProgramControl(fileHandler);
        String result = pc4.requestControl(new String[]{"4"});
        assertEquals("Invalid argument There isn't a file with this number", result);
    }

    @Test
    void validFileName() { //test for if a valid filename is entered for the argument
        List<String> files = List.of("file1.txt", "file2.txt");
        Map<String, String> contents = Map.of("file1.txt", "file 1 content",  "file2.txt", "file 2 content");
        PCFakeFileHandler fileHandler = new PCFakeFileHandler(files, contents);
        ProgramControl pc5 = new ProgramControl(fileHandler);
        String result = pc5.requestControl(new String[]{"file1.txt"});
        assertEquals("file 1 content", result);
    }
    @Test
    void invalidFileName() { //test for if an invalid filename is entered for the argument
        List<String> files = List.of("file1.txt", "file2.txt");
        Map<String, String> contents = Map.of("file1.txt", "file 1 content",  "file2.txt", "file 2 content");
        PCFakeFileHandler fileHandler = new PCFakeFileHandler(files, contents);
        ProgramControl pc5 = new ProgramControl(fileHandler);
        String result = pc5.requestControl(new String[]{"randomFile.txt"});
        assertEquals("File does not exist. Please enter a valid file name.", result);
    }
}

