package application.monadic;

import monad.Provenanced;

public class Main {

    public static void main (String[] args) {
        System.out.println("calculating discount for a young driver");
        Client client1 = new Client();
        client1.setAge(18);
        Provenanced<Integer> premium1 = PremiumCalculator.calculatePremium.apply(client1);
        System.out.println("client1 premium: " + premium1.getValue());
        System.out.println("provenance:");
        premium1.getProvenanceInfo().printTo(System.out);

        System.out.println();

        System.out.println("calculating discount for an older driver");
        Client client2 = new Client();
        client2.setAge(25);
        Provenanced<Integer> premium2 = PremiumCalculator.calculatePremium.apply(client2);
        System.out.println("client2 premium: " + premium2.getValue());
        System.out.println("provenance:");
        premium2.getProvenanceInfo().printTo(System.out);
    }
}
