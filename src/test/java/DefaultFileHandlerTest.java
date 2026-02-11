import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.io.TempDir;

import java.nio.file.Files;
import java.nio.file.Path;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

public class DefaultFileHandlerTest {

    @TempDir
    Path tempDir;

    @Test
    void listFiles_returnsSortedNames() throws Exception {
        Path data = tempDir.resolve("data");
        Files.createDirectory(data);

        Files.writeString(data.resolve("b.txt"), "B");
        Files.writeString(data.resolve("a.txt"), "A");

        DefaultFileHandler fh = new DefaultFileHandler(data);
        assertEquals(List.of("a.txt", "b.txt"), fh.listFiles());
    }

    @Test
    void readFile_returnsContents() throws Exception {
        Path data = tempDir.resolve("data");
        Files.createDirectory(data);

        Files.writeString(data.resolve("filea.txt"), "hello\n");

        DefaultFileHandler fh = new DefaultFileHandler(data);
        assertEquals("hello\n", fh.readFile("filea.txt"));
    }

    @Test
    void missingDataDirectory_throws() {
        Path missing = tempDir.resolve("data");
        DefaultFileHandler fh = new DefaultFileHandler(missing);
        assertThrows(FileAccessException.class, fh::listFiles);
    }

    @Test
    void missingFile_throws() throws Exception {
        Path data = tempDir.resolve("data");
        Files.createDirectory(data);

        DefaultFileHandler fh = new DefaultFileHandler(data);
        assertThrows(FileAccessException.class, () -> fh.readFile("nope.txt"));
    }

    @Test
    void blocksPathTraversal() throws Exception {
        Path data = tempDir.resolve("data");
        Files.createDirectory(data);

        DefaultFileHandler fh = new DefaultFileHandler(data);
        assertThrows(FileAccessException.class, () -> fh.readFile("../secret.txt"));
        assertThrows(FileAccessException.class, () -> fh.readFile("..\\secret.txt"));
    }
}
