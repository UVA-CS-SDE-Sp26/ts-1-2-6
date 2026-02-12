import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.BeforeEach;
import static org.junit.jupiter.api.Assertions.*;

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

    //Tests if the decipher method works properly with known characters in the key file
    @Test
    void testDecipher(){
        String ciphered = "cpep";
        String deciphered = "bodo";
        assertEquals(deciphered, cipher.decipher(ciphered));
    }

    //Tests if the decipher method properly deals with characters not in the key file
    @Test
    void testDecipherWithUnknownCharacters(){
        String ciphered = "cpep!!";
        String deciphered = "bodo!!";
        assertEquals(deciphered, cipher.decipher(ciphered));
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
