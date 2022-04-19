package application.nonmonadic;

public class PremiumCalculator {

    public static Premium calculate (Client client) {
        if (isHighRiskDriver(client)) {
            return new Premium(300);
        }
        else {
            return new Premium(200);
        }
    }

    private static boolean isYoungDriver(Client client) {
        return client.getAge() <= 21;
    }

    private static boolean isHighRiskDriver(Client client) {
        return isYoungDriver(client) || client.hasDUIConviction();
    }
}
