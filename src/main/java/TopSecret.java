public class TopSecret {

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

        if (fileNum < 1) {
            throw new IllegalArgumentException("File number must be positive");
        } else if (fileNum > 2) {
            throw new IllegalArgumentException("File does not exist");
        }

        return fileNum;
    }

    // this function checks to ensure the decryption key type is valid
    private String isValidKeyType(String keyType) {
        // add other key types
        String[] keys = {"default"};
        for (String key : keys) {
            if (key.equals(keyType)) {
                return keyType;
            }
        }
        throw new IllegalArgumentException("Invalid key type");
    }

        // this function displays the list of files in the folder
        private void displayList() {
        // replace with actual file names
            System.out.println("01 filea.txt");
            System.out.println("02 fileb.txt");
            System.out.println("03 filec.txt");
        }


        // this function displays the file decrypted
        private void displayFile(int fileNum, String keyType) {
            System.out.println(fileNum + ": " + keyType);

            // decrypt and print file here
        }
}
