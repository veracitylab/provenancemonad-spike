package application.monadic2;

import application.Client;
import monad.Provenanced;

import java.util.function.Function;

public class PremiumCalculator {

    static Provenanced<Boolean> isYoungDriver (Client client) {
        return Provenanced.of(client.getAge() <= 21)
            .withProvenance("rule", "PremiumCalculator::isYoungDriver");
    }

    static Provenanced<Integer> defaultPremium () {
        return Provenanced.of(200)
            .withProvenance("rule", "PremiumCalculator::defaultPremium");
    }

    static Provenanced<Integer> youngDriverPremium () {
        return Provenanced.of(300)
            .withProvenance("rule", "PremiumCalculator::youngDriverPremium");
    }

    static Function<? super Client,Provenanced<Integer>> calculatePremium =
        client ->
            Provenanced.of(client)
            .when(PremiumCalculator::isYoungDriver)
            .then(PremiumCalculator::youngDriverPremium)
            .orElse(PremiumCalculator::defaultPremium);


}
