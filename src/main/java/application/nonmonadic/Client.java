package application.nonmonadic;

import java.util.Objects;

public class Client {
    private int age = 18;
    private boolean duiConviction = false;

    public int getAge() {
        return age;
    }

    public void setAge(int age) {
        this.age = age;
    }

    public boolean hasDUIConviction() {
        return duiConviction;
    }

    public void setDUIConviction(boolean duiConviction) {
        this.duiConviction = duiConviction;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Client client = (Client) o;
        return age == client.age && duiConviction == client.duiConviction;
    }

    @Override
    public int hashCode() {
        return Objects.hash(age, duiConviction);
    }
}
