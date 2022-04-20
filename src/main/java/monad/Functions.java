package monad;

import java.util.function.Function;

public class Functions {

    public static <T,U>  Function<? super T,Provenanced<U>> or(Function<? super T,Provenanced<U>>... functions) {
        return t -> {
            for (Function<? super T,Provenanced<U>> function:functions) {
                Provenanced<U> result = function.apply(t);
                if (result.hasValue()) {
                    // wrap again as this is application of a rule
                    return Provenanced.of(result.getValue())
                        .withProvenance("rule","Functions::or")
                        .addParent(result.getProvenanceInfo());
                }
            }
            return Provenanced.NULL;
        };
    }
}
