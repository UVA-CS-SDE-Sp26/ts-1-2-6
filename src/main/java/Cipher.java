import java.io.IOException;
import java.nio.file.*;
import java.util.List;
import java.util.HashMap;
import java.util.Map;

//Cipher class reads a cipher key file and decodes the ciphered string
public class Cipher {

    //initialize a map to store/compare the actual letters and ciphered text
    private final Map<Character, Character> cipherMap;

    //constructor
    public Cipher() {
        this.cipherMap = buildCipherMap();
    }

    //reads the cipher key file from the path "ciphers/key.txt" and validates the keyType
    private List<String> readCipherKeyFile(){
        try{
            //Provides the path to the cipher key file and reads all lines from the file
            Path path = Path.of("ciphers/key.txt");
            List<String> lines = Files.readAllLines(path);

            //file must have exactly two lines: actual letters and ciphered text
            if(lines.size() != 2){
                throw new FileAccessException("Cipher key must have exactly 2 lines");
            }
            //both lines must be the same length
            if(lines.get(0).length() != lines.get(1).length()){
                throw new FileAccessException("The two cipher key lines must be the same length");
            }
            return lines;
        }
        catch(IOException exception){
            throw new FileAccessException("Failed to read cipher key file", exception);
        }
    }

    //Builds a cipher map from the ciphered letters to the actual letters
    private Map<Character, Character> buildCipherMap(){
        List<String> lines = readCipherKeyFile();
        String actualLetters = lines.get(0); //first line = actual letters
        String cipherMatch = lines.get(1); //second line = cipher match

        Map<Character, Character> map = new HashMap<>();
        //for each character in the cipher match line, map it to the actual letter
        for(int i = 0; i < actualLetters.length(); i++){
            map.put(cipherMatch.charAt(i), actualLetters.charAt(i));
        }
        return map;
    }

    //deciphers the given ciphered text
    public String decipher(String cipheredString){
        StringBuilder decipheredString = new StringBuilder();
        //converts the string to a character array
        char[] characters = cipheredString.toCharArray();

        //loops through each character in the string
        for (char letter : characters) {
            if (cipherMap.containsKey(letter)) { //if the character exists in the cipher line then replace it with the actual letter
                decipheredString.append(cipherMap.get(letter));
            } else { //otherwise leave it as is
                decipheredString.append(letter);
            }
        }
        return decipheredString.toString();
    }


}
