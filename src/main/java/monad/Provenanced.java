package monad;

import java.util.Objects;
import java.util.function.Function;
import java.util.function.Supplier;

public class Provenanced<T> {

    public static Provenanced NULL = new Provenanced(null,null);

    public static <T> Provenanced<T> of(T object, ProvenanceInfo provenanceData) {
        return new Provenanced<>(object,provenanceData);
    }

    public static <T> Provenanced<T> of(T object) {
        return new Provenanced<>(object,new ProvenanceInfo());
    }

    private T value = null;
    private ProvenanceInfo provenanceInfo = null;


    private Provenanced(T value, ProvenanceInfo provenanceInfo) {
        this.value = value;
        this.provenanceInfo = provenanceInfo;
    }


    public <U> Provenanced<U> bind(Function<? super T,Provenanced<U>> mapper) {
        Provenanced<U> result = mapper.apply(this.value);
        result.getProvenanceInfo().addParent(this.provenanceInfo);
        return result;
    }

    // syntactic sugar
    public <Boolean> Provenanced<Boolean> when(Function<? super T,Provenanced<Boolean>> mapper) {
        return bind(mapper);
    }

    public <T> Provenanced<T> then(Supplier<Provenanced<T>> supplier) {
        assert this.value instanceof Boolean;
        if (this.value==Boolean.TRUE) {
            Provenanced<T> result = supplier.get();
            result.getProvenanceInfo().addParent(this.provenanceInfo);
            return result;
        }
        else {
            Provenanced<T> result = Provenanced.of(null);
            result.getProvenanceInfo().addParent(this.provenanceInfo);
            return result;
        }
    }

    public <T> Provenanced<T> orElse(Supplier<Provenanced<T>> supplier) {
        Provenanced<T> result = supplier.get();
        // add parents
        for (ProvenanceInfo pi:this.provenanceInfo.getParents()) {
            result.addParent(pi);
        }
        return result;
    }


    public T getValue() {
        return this.value;
    }

    public boolean hasValue() {
        return this.value!=null;
    }

    public boolean hasProvenanceInfo() {
        return this.value!=null;
    }

    public ProvenanceInfo getProvenanceInfo() {
        return provenanceInfo;
    }

    public Provenanced<T> withProvenance(String key, String value) {
        this.provenanceInfo.put(key,value);
        return this;
    }

    public Provenanced<T> addParent(ProvenanceInfo provenance) {
        this.provenanceInfo.addParent(provenance);
        return this;
    }


    // equality is only based on value, not provenance here
    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Provenanced<?> that = (Provenanced<?>) o;
        return Objects.equals(value, that.value);
    }

    @Override
    public int hashCode() {
        return Objects.hash(value);
    }
}
