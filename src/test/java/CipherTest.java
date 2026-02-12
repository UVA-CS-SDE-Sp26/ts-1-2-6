import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.BeforeEach;
import static org.junit.jupiter.api.Assertions.*;

import java.nio.file.*;
import java.util.List;
import java.util.Map;
import java.io.IOException;

public class CipherTest {

    private Cipher cipher;
    @BeforeEach
    void setUp(){
        cipher = new Cipher();
    }

    //Tests to see if the cipher object is initialized correctly
    //Indirectly confirms that the file exists/is readable, key is valid, and the cipher map was built
    @Test
    void testCipherConstructor(){
        assertNotNull(cipher);
    }

    //Tests that each character in the cipher key is correctly mapped to its actual character
    //The test reads the given key file so it will still work if the cipher changes
    @Test
    void testDecipher() throws IOException {
        Map<Character, Character> map = cipher.getCipherMap();
        List<String> lines = Files.readAllLines(Path.of("ciphers/key.txt"));
        String actualLetters = lines.get(0);
        String cipheredLetters = lines.get(1);

        for(int i = 0; i < cipheredLetters.length(); i++){
            char cipheredCharacter = cipheredLetters.charAt(i);
            char actualCharacter = actualLetters.charAt(i);
            assertEquals(actualCharacter, map.get(cipheredCharacter), "Decipher was incorrect for character: " + cipheredCharacter);
        }
    }

    //Tests if the decipher method properly deals with an empty string
    @Test
    void testDecipherWithEmptyString(){
        assertEquals("", cipher.decipher(""));
    }

    //Tests if the decipher method properly handles a null string
    @Test
    void testDecipherWithNull(){
        assertThrows(IllegalArgumentException.class, ()-> cipher.decipher(null));
    }

    //Tests if the ciphered string contains no valid characters that it returns as is
    @Test
    void testDecipherWithNoValidCharacters(){
        String ciphered = "^%$";
        String deciphered = "^%$";
        assertEquals(deciphered, cipher.decipher(ciphered));
    }
}
