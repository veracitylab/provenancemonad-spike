# Provenance Monad Spike

Exploration of the notion of a provenance monad. See example code in the `application.monadic` package. A `Provenanced` monad wraps computed values and adds 
`ProvenanceInfo` -- a rather general data structure consisting of a flat key-value map (i.e. both keys and values are strings) and a reference to parent 
instances of `ProvenanceInfo`. `Provenanced::bind` (aka flatMap function) composes provenance by transparently adding a parent reference.  

## Notes

1. some provenance info now set with `withProvenance` could be injected by an agent, reducing boilerplate
2. this mechanism could also pick up annotations on functions, system properties etc
3. info captured is fine-grained (methods invoked), but this could be changed, e.g. it could just detect modules used, whether outgoing network calls are made to certain URLs, whether some AI packages with known bias are used etc, perhaps referencing some central database (just like CVE for security checks) 
4. an interesting feature is `Functions::or` -- some sort of backtracking -- here we seem to cross over from functional into logic programming - to be discussed