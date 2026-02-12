import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.AfterEach;


import static org.junit.jupiter.api.Assertions.*;

import java.io.ByteArrayOutputStream;
import java.io.PrintStream;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

class UserInterfaceTest {

    private UserInterface test;
    private final ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
    private final PrintStream originalOut = System.out;


    @BeforeEach
    void setUp() throws IOException {
        test = new UserInterface();
        System.setOut(new PrintStream(outputStream));

        // Create real test files in actual data directory
        Path dataDir = Paths.get("data");
        Files.createDirectories(dataDir);
        Files.writeString(dataDir.resolve("file1.txt"), "testing num 1");
        Files.writeString(dataDir.resolve("file2.txt"), "hello my name is christy");
        Files.writeString(dataDir.resolve("file3.txt"), "this is class sde");

        // Create cipher key file in actual ciphers directory
        Path cipherDir = Paths.get("ciphers");
        Files.createDirectories(cipherDir);
        Files.writeString(cipherDir.resolve("key.txt"),
                "abcdefghijklmnopqrstuvwxyz\nzyxwvutsrqponmlkjihgfedcba");
    }

    @AfterEach
    void tearDown() throws IOException {
        System.setOut(originalOut);

        // Clean up test files
        Files.deleteIfExists(Paths.get("data/file1.txt"));
        Files.deleteIfExists(Paths.get("data/file2.txt"));
        Files.deleteIfExists(Paths.get("data/file3.txt"));
    }

    // Tests for validArgument() method

    @Test
    void testValidArgumentNoArguments() {
        test.validArgument(new String[]{});

        String output = outputStream.toString();
        assertTrue(output.contains("01") && output.contains("02") && output.contains("03"),
                "should display all files");
    }

    @Test
    void testValidArgumentOneArgument1() {
        test.validArgument(new String[]{"1"});
        String output = outputStream.toString();
        assertTrue(output.contains("testing num 1"),
                "Should display file content or error message");

    }

    @Test
    void testValidArgumentOneArgument2 (){
        test.validArgument(new String[]{"2"});
        String output = outputStream.toString();
        assertTrue(output.contains("christy"),
                "Should display file content or error message");
    }

    @Test
    void testValidArgumentOneArgument3 (){
        test.validArgument(new String[]{"3"});
        String output = outputStream.toString();
        assertTrue(output.contains("sde"),
                "Should display file content or error message");
    }

    @Test
    void testValidArgumentOneArgumentTooHigh () {
        test.validArgument(new String[]{"5"});
        String output = outputStream.toString();
        assertTrue(output.contains("testing num 1") || output.contains("Invalid arguments entered"),
                "Should display file content or error message");
    }

    @Test
    void testValidArgumentOneArgumentTooLow (){
        test.validArgument(new String[]{"-1"});
        String output = outputStream.toString();
        assertTrue(output.contains("Invalid arguments entered"),
                "Should display file content or error message");
    }

    @Test
    void testValidArgumentStringArgument () {
        test.validArgument(new String[]{"sldkfja"});
        String output = outputStream.toString();
        assertTrue(output.contains("Invalid arguments entered"),
                "Should display file content or error message");
    }





    @Test
    void testValidArgumentTwoArgumentsSpecialKey() {
        test.validArgument(new String[]{"1", "cipher"});
        String output = outputStream.toString();
        assertFalse(output.isEmpty(), "Should display output");
    }

    @Test
    void testValidArgumentTwoArgumentsDefault() {
        test.validArgument(new String[]{"1", "default"});

        String output = outputStream.toString();
        assertFalse(output.isEmpty(), "Should display output");
    }

    @Test
    void testValidArgumentTooManyArguments() {
        test.validArgument(new String[]{"1", "cipher", "lskfajn"});

        String output = outputStream.toString();
        assertEquals("Invalid number of arguments. First argument must be a file name and the second argument must be a key type\n",
                output);
    }


    @Test
    void testValidArgumentInvalidKey() {
        test.validArgument(new String[]{"1", "lsdjf;adlkfja;"});

        String output = outputStream.toString();
        assertEquals("Invalid arguments entered. First argument must be a file name and the second argument must be a key type\n",
                output);
    }


    @Test
    void testIsValidFileEmptyString() {
        test.validArgument(new String[]{""});

        String output = outputStream.toString();
        assertTrue(output.contains("Invalid arguments"), "Empty string should be invalid");
    }


    @Test
    void testIsValidKeyTypeDefaultKey() {
        test.validArgument(new String[]{"1", "default"});

        String output = outputStream.toString();
        assertFalse(output.contains("Invalid arguments"), "'default' should be valid key type");
    }

    @Test
    void testIsValidKeyType_CipherKey() {
        test.validArgument(new String[]{"1", "cipher"});

        String output = outputStream.toString();
        assertFalse(output.contains("Invalid arguments"), "'cipher' should be valid key");
    }

    @Test
    void testIsValidKeyTypeInvalidKey() {
        test.validArgument(new String[]{"1", "invalid"});

        String output = outputStream.toString();
        assertTrue(output.contains("Invalid arguments"), "should not be a valid key type");
    }

    @Test
    void testIsValidKeyTypeCaseSensitive() {
        test.validArgument(new String[]{"1", "DEFAULT"});

        String output = outputStream.toString();
        assertFalse(output.contains("Invalid arguments"), "'DEFAULT' should be valid");
    }

    @Test
    void testIsValidKeyTypeCaseSensitiveValid() {
        test.validArgument(new String[]{"1", "CIPHER"});

        String output = outputStream.toString();
        assertFalse(output.contains("Invalid arguments"), "'CIPHER' should be valid");
    }

    @Test
    void testIsValidKeyType_EmptyString() {
        test.validArgument(new String[]{"1", ""});

        String output = outputStream.toString();
        assertTrue(output.contains("Invalid arguments"), "Empty key type should be invalid");
    }


    @Test
    void testDisplayListFormat() {
        test.validArgument(new String[]{});
        String output = outputStream.toString();
        String[] lines = output.split("\n");
        assertTrue(lines.length > 0, "Should have at least one line");
        assertTrue(lines[0].matches("\\d{2} .+"), "First line should start with two digits and a space");
    }

    @Test
    void testDisplayListMultipleFiles() {
        test.validArgument(new String[]{});

        String output = outputStream.toString();
        String[] lines = output.split("\n");
        assertTrue(lines.length > 0, "Should display at least one file");
    }


    @Test
    void testDisplayFileWithDefaultKey() {
        test.validArgument(new String[]{"1", "default"});

        String output = outputStream.toString();
        assertFalse(output.isEmpty(), "Should display file content");
    }

    @Test
    void testDisplayFileWithCipherKey() {
        test.validArgument(new String[]{"1", "cipher"});

        String output = outputStream.toString();
        assertFalse(output.isEmpty(), "Should display deciphered content");
    }

    @Test
    void testDisplayFileOneArgumentDefaultKey() {
        test.validArgument(new String[]{"1"});

        String output = outputStream.toString();
        assertFalse(output.isEmpty(), "Should display file with default key");
    }

    @Test
    void testValidArgumentNullArray() {
        assertThrows(NullPointerException.class, () -> {
            test.validArgument(null);
        }, "Should throw exception for null arguments array");
    }

    @Test
    void testValidArgumentArrayWithNullElement() {
        test.validArgument(new String[]{null});

        String output = outputStream.toString();
        assertTrue(output.contains("Invalid arguments"), "Should handle null element");
    }

}