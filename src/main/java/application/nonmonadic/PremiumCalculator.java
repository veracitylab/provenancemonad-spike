package application.nonmonadic;

public class PremiumCalculator {

    public static int calculate (Client client) {
        return isHighRiskDriver(client) ? 300 : 200 ;
    }

    private static boolean isYoungDriver(Client client) {
        return client.getAge() <= 21;
    }

    private static boolean isHighRiskDriver(Client client) {
        return isYoungDriver(client);
    }
}
