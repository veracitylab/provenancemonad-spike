package application.monadic;

import monad.Provenanced;

public class Main {

    public static void main (String[] args) {
        Client client1 = new Client();
        client1.setDUIConviction(true);
        Provenanced<Integer> premium = PremiumCalculator.calculatePremium.apply(client1);
        System.out.println("client1 premium: " + premium.getValue());
        System.out.println("provenance:");
        premium.getProvenanceInfo().printTo(System.out);

    }
}
