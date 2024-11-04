package nl.rocnijmegen.testing;

import java.util.Scanner;


public class App {
    private static final double[] RENTE_PERC = {2.0, 3.0, 3.5, 4.5, 5.0}; // in %

    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);

        System.out.print("Voer uw jaarinkomen in: €");
        double inkomen = scanner.nextDouble();

        System.out.print("Heeft u een partner? (ja/nee): ");
        boolean heeftPartner = scanner.next().equalsIgnoreCase("ja");
        double partnerInkomen = 0;
        if (heeftPartner) {
            System.out.print("Voer het jaarinkomen van uw partner in: €");
            partnerInkomen = scanner.nextDouble();
        }

        System.out.print("Heeft u een studieschuld? (ja/nee): ");
        boolean heeftStudieschuld = scanner.next().equalsIgnoreCase("ja");

        System.out.print("Voer uw postcode in: ");
        int postcode = scanner.nextInt();
        if (isPostcodeUitgesloten(postcode)) {
            System.out.println("Hypotheken zijn niet beschikbaar in uw postcodegebied.");
            return;
        }

        System.out.print("Kies een rentevaste periode (1, 5, 10, 20, 30 jaar): ");
        int rentevastePeriode = scanner.nextInt();
        if (rentevastePeriode != 1 && rentevastePeriode != 5 && rentevastePeriode != 10 && rentevastePeriode != 20 && rentevastePeriode != 30) {
            System.out.println("Ongeldige rentevaste periode ingevoerd.");
            return;
        }

        double maxTeLenen = berekenMaxTeLenen(inkomen, partnerInkomen, heeftStudieschuld);
        double maandlasten = berekenMaandlasten(maxTeLenen, rentevastePeriode);

        System.out.printf("Maximaal te lenen bedrag: €%.2f%n", maxTeLenen);
        System.out.printf("Maandlasten voor hypotheek: €%.2f%n", maandlasten);
        System.out.printf("Totale bedrag betaald na 30 jaar: €%.2f%n", maandlasten * 12 * 30);
    }

    public static double berekenMaxTeLenen(double inkomen, double partnerInkomen, boolean heeftStudieschuld) {
        double totaalInkomen = (inkomen + partnerInkomen) * 4.25;
        if (heeftStudieschuld) {
            totaalInkomen *= 0.75; // 25%
        }
        return totaalInkomen;
    }

    public static double berekenMaandlasten(double lening, int rentevastePeriode) {
        double rentePercentage = getRentePercentage(rentevastePeriode);
        double renteMaand = (rentePercentage / 100) / 12;
        double renteBedrag = lening * renteMaand;
        double aflossing = lening / (30 * 12); // 30 jaar
        return renteBedrag + aflossing;
    }

    public static double getRentePercentage(int rentevastePeriode) {
        switch (rentevastePeriode) {
            case 1: return RENTE_PERC[0];
            case 5: return RENTE_PERC[1];
            case 10: return RENTE_PERC[2];
            case 20: return RENTE_PERC[3];
            case 30: return RENTE_PERC[4];
            default: throw new IllegalArgumentException("Ongeldige rentevaste periode.");
        }
    }

    public static boolean isPostcodeUitgesloten(int postcode) {
        return postcode == 9679 || postcode == 9681 || postcode == 9682;
    }
}
