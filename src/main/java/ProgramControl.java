import java.nio.file.Path;
import java.util.List;

public class ProgramControl {
    private FileHandler fileHandler;

    public ProgramControl(FileHandler fileHandler) {
        this.fileHandler = fileHandler;
    }
    public FileHandler getFileHandler(){
        return this.fileHandler;
    }
    public String requestControl(String[] arguments){
        List<String> files = fileHandler.listFiles();
        if (files == null || files.isEmpty()){
            return "No files in directory";
        }

        if (arguments.length == 0) { //this is default, no argument, prints all the files in the directory
            String result = "Files in directory: ";
            int indexOfFiles = 1;
            for (String file : files) {
                result += String.format("%02d %s\n", indexOfFiles, file);
                indexOfFiles++;
            }
            return result;
        }

        String requested = arguments[0];

            try{ //this sees if the argument entered is a number, if so, it pulls content through the file number
                int fileNumReq = Integer.parseInt(requested);
                if (fileNumReq < 1 || fileNumReq > files.size()) {
                    return ("Invalid argument There isn't a file with this number");
                }
                String fileSelected = files.get(fileNumReq - 1); //-1 for how things are indexed in java
                String content = fileHandler.readFile(fileSelected);
                return (content);
            } catch(NumberFormatException e) {} //goes to file name version because it wasn't a number

            String content = "";
            if (files.contains(requested)) { //this is the file name version
                content = fileHandler.readFile(requested);
                return (content);
            }
            else {
                return ("File does not exist. Please enter a valid file name.");
            }
    }

}
