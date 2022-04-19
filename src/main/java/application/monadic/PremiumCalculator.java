package application.monadic;

import monad.ProvenanceInfo;
import monad.Provenanced;

import java.util.function.Function;

import static monad.Functions.or;

public class PremiumCalculator {

    static Function<? super Client,Provenanced<Boolean>> isYoungDriver =
        client -> Provenanced.of(client.getAge()<=21).addProvenance("rule","PremiumCalculator::isYoungDriver");

    static Function<? super Client,Provenanced<Boolean>> hasDUIConviction =
        client -> Provenanced.of(client.isDUIConviction()).addProvenance("rule","PremiumCalculator::hasDUIConviction");

    static Function<? super Client,Provenanced<Boolean>> isHighRiskDriver =
        or(isYoungDriver,hasDUIConviction);

    static Function<? super Client,Provenanced<Integer>> calculatePremium1 =
        client ->
            Provenanced.of(client)
            .bind(isHighRiskDriver)
            .bind(v -> Provenanced.of(v?300:null))
            .addProvenance("rule","PremiumCalculator::calculatePremium1");

    static Function<? super Client,Provenanced<Integer>> calculatePremium2 =
        client ->
            Provenanced.of(client)
            .bind(isHighRiskDriver)
            .bind(v -> Provenanced.of(v?100:null))
            .addProvenance("rule","PremiumCalculator::calculatePremium1");

    static Function<? super Client,Provenanced<Integer>> calculatePremium =
        or(calculatePremium1,calculatePremium2);


    private static Provenanced<Boolean> isYoungDriver(Client client) {
        return Provenanced.of(client.getAge() <= 21, ProvenanceInfo.with("rule","PremiumCalculator::isYoungDriver"));
    }

    private static Provenanced<Boolean> isHighRiskDriver(Client client) {
        return Provenanced.of(client).bind(isYoungDriver);
    }
}
