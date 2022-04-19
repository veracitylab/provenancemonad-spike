package application.monadic;

import java.util.Objects;

public class Premium {

    private int amount = 0;

    public Premium(int amount) {
        this.amount = amount;
    }

    public int getAmount() {
        return amount;
    }

    public void setAmount(int amount) {
        this.amount = amount;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Premium premium = (Premium) o;
        return amount == premium.amount;
    }

    @Override
    public int hashCode() {
        return Objects.hash(amount);
    }
}
