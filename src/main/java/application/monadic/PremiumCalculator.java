package application.monadic;

import application.Client;
import monad.Provenanced;
import java.util.function.Function;
import static monad.Functions.or;

public class PremiumCalculator {

    static Function<? super Client,Provenanced<Boolean>> isYoungDriver =
        client -> Provenanced.of(client.getAge()<=21).withProvenance("rule","PremiumCalculator::isYoungDriver");

    static Function<? super Client,Provenanced<Integer>> defaultPremium =
        client -> Provenanced.of(200).withProvenance("rule","PremiumCalculator::defaultPremium");

    static Function<? super Client,Provenanced<Integer>> premiumForYoungDrivers =
        client ->
            Provenanced.of(client)
            .bind(isYoungDriver)
            .bind(v -> Provenanced.of(v?300:null))
            .withProvenance("rule","PremiumCalculator::premiumForYoungDrivers");

    // construct necessary to apply multiple alternative rules , basically some form of backtracking
    // if premiumForYoungDrivers cannot be applied, i.e. yields a value Provenanced.NULL , then defaultPremium will be applied
    static Function<? super Client,Provenanced<Integer>> calculatePremium = or(premiumForYoungDrivers,defaultPremium);


}
