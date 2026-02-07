import java.io.IOException;
import java.nio.file.*;
import java.util.Comparator;
import java.util.List;
import java.util.stream.Stream;

public class DefaultFileHandler implements FileHandler {
    private final Path dataDir;

    public DefaultFileHandler() {
        this(Paths.get("data"));
    }

    // makes testing easy
    public DefaultFileHandler(Path dataDir) {
        this.dataDir = dataDir.toAbsolutePath().normalize();
    }

    @Override
    public List<String> listFiles() {
        if (!Files.exists(dataDir) || !Files.isDirectory(dataDir)) {
            throw new FileAccessException("Data directory not found: " + dataDir);
        }

        try (Stream<Path> stream = Files.list(dataDir)) {
            return stream
                    .filter(Files::isRegularFile)
                    .map(p -> p.getFileName().toString())
                    .sorted(Comparator.naturalOrder())
                    .toList();
        } catch (IOException e) {
            throw new FileAccessException("Failed to list files in data directory: " + dataDir, e);
        }
    }

    @Override
    public String readFile(String filename) {
        if (filename == null || filename.isBlank()) {
            throw new FileAccessException("Filename cannot be blank.");
        }

        if (!Files.exists(dataDir) || !Files.isDirectory(dataDir)) {
            throw new FileAccessException("Data directory not found: " + dataDir);
        }

        Path filePath = dataDir.resolve(filename).normalize();
        if (!filePath.startsWith(dataDir)) {
            throw new FileAccessException("Illegal filename/path: " + filename);
        }

        if (!Files.exists(filePath) || !Files.isRegularFile(filePath)) {
            throw new FileAccessException("File not found: " + filename);
        }

        try {
            return Files.readString(filePath);
        } catch (IOException e) {
            throw new FileAccessException("Failed to read file: " + filename, e);
        }
    }
}
