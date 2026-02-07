import java.util.List;

public interface FileHandler {
    List<String> listFiles();
    String readFile(String filename);
}
