package monad;

import java.util.function.Function;

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
        Provenanced result = mapper.apply(this.value);
        result.getProvenanceInfo().addParent(this.provenanceInfo);
        return result;
    }

    public Provenanced<Boolean> bindFirst(Function<? super T,Provenanced<Boolean>>... mappers) {
        if (mappers.length==0) {
            throw new IllegalArgumentException("At least one mapper must be present");
        }
        for (Function<? super T,Provenanced<Boolean>> mapper:mappers) {
            Provenanced<Boolean> result = mapper.apply(this.value);
            if (result.hasValue() && result.getValue()) {
                result.getProvenanceInfo().addParent(this.provenanceInfo);
                return result;
            }
        }
        return Provenanced.of(false);
    }

    public Provenanced<Boolean> orElse(Function<? super T,Provenanced<Boolean>> mapper) {
        // TODO enforce precond that this is Provenanced<Boolean>
        if (!this.hasValue() || this.getValue()==Boolean.FALSE) {
            Provenanced<Boolean> result = mapper.apply(this.value);
            if (result.hasValue() && result.getValue()) {
                result.getProvenanceInfo().addParent(this.provenanceInfo);
                return result;
            }
        }
        return (Provenanced<Boolean>)this;
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

    public Provenanced<T> addProvenance(String key,String value) {
        this.provenanceInfo.put(key,value);
        return this;
    }

    public Provenanced<T> addParent(ProvenanceInfo provenance) {
        this.provenanceInfo.addParent(provenance);
        return this;
    }
}
