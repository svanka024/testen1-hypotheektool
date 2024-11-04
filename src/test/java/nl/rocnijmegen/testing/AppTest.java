package nl.rocnijmegen.testing;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;


public class MainTest {


    @Test
    public void testFullHypotheekBerekeningMetCorrecteInvoer() {
        String input = "60000\n30\nNee\nJa\n1234";
        InputStream userinput = new ByteArrayInputStream(input.getBytes());
        System.setIn(userinput);

        ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
        PrintStream originalOut = System.out;
        System.setOut(new PrintStream(outputStream));

        Main.main(new String[0]);

        System.setOut(originalOut);

        String actualOutput = outputStream.toString();
        String expectedOutput = "Totaal betaald na 30 jaar: 892500.00";
        assertTrue(actualOutput.contains(expectedOutput));
    }
}
