package monad;

import org.junit.jupiter.api.Test;

import java.util.function.Function;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class MonadicLawTests {


    @Test
    public void testLeftIdentity() {
        Function<String,Provenanced<String>> prefixWithUnderscore = s -> Provenanced.of("_"+s);
        assertEquals(
            Provenanced.of("foo").bind(prefixWithUnderscore),  // unit x bind
            prefixWithUnderscore.apply("foo")
        );
    }

    @Test
    public void testRightIdentity() {
        assertEquals(
            Provenanced.of("foo").bind(s -> Provenanced.of(s)),  // does not double-wrap
            (Provenanced.of("foo"))
        );
    }

    @Test
    public void testAssociativity() {
        Function<String,Provenanced<String>> prefixWithUnderscore = s -> Provenanced.of("_"+s);  // f
        Function<String,Provenanced<String>> postfixWithUnderscore = s -> Provenanced.of(s+"_"); // g
        Function<String,Provenanced<String>> padWithUnderscore = s -> Provenanced.of(s).bind(prefixWithUnderscore).bind(postfixWithUnderscore);  // fg
        assertEquals(
            Provenanced.of("foo").bind(padWithUnderscore),
            Provenanced.of("foo").bind(prefixWithUnderscore).bind(postfixWithUnderscore)
        );
    }
}
