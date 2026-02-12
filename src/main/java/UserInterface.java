import java.util.List;

public class UserInterface {

    public void validArgument(String[] arguments) {
        try {
            // if no arguments are passed, a function displaying the list of files will be printed
            if (arguments.length == 0) {
                displayList();
                //if one argument is passed, the corresponding file will be printed and decrypted with the default key
            } else if (arguments.length == 1) {
                int fileNum = isValidFile(arguments[0]);
                displayFile(fileNum, "default");
                //if two argument is passed, the file corresponding to the first argument will be printed and decrypted with the key corresponding to the second argument
            } else if (arguments.length == 2) {
                int fileNum = isValidFile(arguments[0]);
                String keyType = isValidKeyType(arguments[1]);
                displayFile(fileNum, keyType);
                //if more than two arguments are passed, the following message is printed
            } else {
                System.out.println("Invalid number of arguments. First argument must be a file name and the second argument must be a key type");
            }
        } catch (IllegalArgumentException e) {
            System.out.println("Invalid arguments entered. First argument must be a file name and the second argument must be a key type");
        }
    }


    // this function checks to ensure the file number is valid
    private int isValidFile(String fileName) {
        int fileNum;

        try {
            fileNum = Integer.parseInt(fileName);
        } catch (NumberFormatException e) {
            throw new IllegalArgumentException("File number must be an integer");
        }

        ProgramControl pc = new ProgramControl(new DefaultFileHandler());
        List<String> files = pc.getFileHandler().listFiles();

        //check to see if the file number is valid
        if (fileNum < 1 || fileNum > files.size()) {
            throw new IllegalArgumentException("File doesn't exist");
        }
        return fileNum;
    }

    // this function checks to ensure the decryption key type is valid
    private String isValidKeyType(String keyType) {
        String[] keys = {"default", "cipher"};
        for (String key : keys) {
            if (key.equals(keyType)) {
                return keyType;
            }
        }
        throw new IllegalArgumentException("Invalid key type");
    }

    // this function displays the list of files in the folder
    private void displayList() {
        ProgramControl pc = new ProgramControl(new DefaultFileHandler());
        List<String> files = pc.getFileHandler().listFiles();

        int index = 1;
        for(String file: files){
            System.out.printf("%02d %s%n", index, file);
            index++;
        }
    }


    // this function displays the file decrypted
    private void displayFile(int fileNum, String keyType) {
        ProgramControl pc = new ProgramControl(new DefaultFileHandler());
        List<String> files = pc.getFileHandler().listFiles();

        String fileName = files.get(fileNum -1);
        String content;

        try{
            content = pc.requestControl(new String[]{fileName});
        }catch(FileAccessException exception){
            System.out.println("Failed to read the file: " + exception.getMessage());
            return;
        }
        //if the keyType is cipher then decipher the content
        if("cipher".equals(keyType)){
            Cipher cipher = new Cipher();
            content = cipher.decipher(content);
        }
        System.out.println(content);
    }


}
