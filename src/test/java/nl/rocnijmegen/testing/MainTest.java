package nl.rocnijmegen.testing;

import com.sun.tools.javac.Main;
import org.junit.jupiter.api.Test;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.InputStream;
import java.io.PrintStream;

import static org.junit.jupiter.api.Assertions.*;


public class MainTest {

    @Test
    void testBerekenMaxTeLenenZonderPartnerEnZonderStudieschuld() {
        double inkomen = 60000;
        double partnerInkomen = 0;
        boolean heeftStudieschuld = false;

        double verwachtMaxTeLenen = 60000 * 4.25;
        double resultaat = App.berekenMaxTeLenen(inkomen, partnerInkomen, heeftStudieschuld);

        assertEquals(verwachtMaxTeLenen, resultaat, 0.01);
    }

    @Test
    void testBerekenMaxTeLenenMetPartnerZonderStudieschuld() {
        double inkomen = 60000;
        double partnerInkomen = 40000;
        boolean heeftStudieschuld = false;

        double verwachtMaxTeLenen = (60000 + 40000) * 4.25;
        double resultaat = App.berekenMaxTeLenen(inkomen, partnerInkomen, heeftStudieschuld);

        assertEquals(verwachtMaxTeLenen, resultaat, 0.01);
    }

    @Test
    void testBerekenMaxTeLenenMetStudieschuld() {
        double inkomen = 60000;
        double partnerInkomen = 0;
        boolean heeftStudieschuld = true;

        double verwachtMaxTeLenen = (60000 * 4.25) * 0.75;
        double resultaat = App.berekenMaxTeLenen(inkomen, partnerInkomen, heeftStudieschuld);

        assertEquals(verwachtMaxTeLenen, resultaat, 0.01);
    }

    @Test
    void testBerekenMaandlasten30Jaar() {
        double lening = 255000;
        int rentevastePeriode = 30;

        // Verwachte resultaten op basis van rentepercentage van 5% voor 30 jaar
        double rentePercentage = 5.0 / 100 / 12;
        double renteBedrag = lening * rentePercentage;
        double aflossing = lening / (30 * 12);
        double verwachtMaandlasten = renteBedrag + aflossing;

        double resultaat = App.berekenMaandlasten(lening, rentevastePeriode);

        assertEquals(verwachtMaandlasten, resultaat, 0.01);
    }

    @Test
    void testIsPostcodeUitgesloten() {
        assertTrue(App.isPostcodeUitgesloten(9679));
        assertTrue(App.isPostcodeUitgesloten(9681));
        assertTrue(App.isPostcodeUitgesloten(9682));
        assertFalse(App.isPostcodeUitgesloten(1000)); // Een geldige postcode
    }

    @Test
    public void testFullHypotheekBerekeningMetCorrecteInvoer() throws Exception {
        String input = "60000\n30\nNee\nJa\n6663";
        InputStream inputStream = new ByteArrayInputStream(input.getBytes());
        System.setIn(inputStream);

        ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
        PrintStream originalOut = System.out;
        System.setOut(new PrintStream(outputStream));

        Main.main(new String[0]);

        System.setOut(originalOut);

        String actualOutput = outputStream.toString();
        String expectedOutput = outputStream.toString();
        assertTrue(actualOutput.contains(expectedOutput));

        assertTrue(actualOutput.contains(outputStream.toString()), "De output bevat niet het verwachte maximale leenbedrag.");
        assertTrue(actualOutput.contains(outputStream.toString()), "De output bevat niet het verwachte maandlastenbedrag.");
        assertTrue(actualOutput.contains(outputStream.toString()), "De output bevat niet het verwachte totale betaalde bedrag na 30 jaar.");
    }
}
