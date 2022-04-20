package monad;

import java.io.PrintStream;
import java.util.*;

import static java.util.Collections.unmodifiableList;
import static java.util.Collections.unmodifiableSet;

public class ProvenanceInfo {
    private Map<String,String> data = new HashMap<>();
    private List<ProvenanceInfo> parents = new ArrayList<>();

    public ProvenanceInfo(ProvenanceInfo... parents) {
        this(new HashMap<>(),parents);
    }

    public ProvenanceInfo(Map<String,String> properties, ProvenanceInfo... parents) {
        for (ProvenanceInfo parent:parents) {
            this.parents.add(parent);
        }
        this.data.putAll(properties);
        for (ProvenanceInfo parent:parents) {
            this.parents.add(parent);
        }
    }

    public static ProvenanceInfo with (String key, String value, ProvenanceInfo... parents) {
        Map map = new HashMap();
        map.put(key,value);
        return new ProvenanceInfo(map,parents);
    }

    public Set<String> getKeys() {return unmodifiableSet(data.keySet());}

    public boolean containsKey(Object key) {
        return data.containsKey(key);
    }

    public String get(Object key) {
        return data.get(key);
    }

    public String put(String key, String value) {
        return data.put(key, value);
    }

    public List<ProvenanceInfo> getParents() {
        return unmodifiableList(parents);
    }

    public boolean addParent(ProvenanceInfo provenance) {
        return this.parents.add(provenance);
    }

    public void printTo(PrintStream out) {
        printTo(System.out,0);
    }

    private static final String OFFSET = "\t";
    private void printOffset(PrintStream out,int level) {
        for (int i=0;i<level;i++) {
            out.print(OFFSET);
        }
    }

    private void printTo(PrintStream out,int level) {
        for (String key:this.data.keySet()) {
            printOffset(out,level);
            out.print(key);
            out.print(" -> ");
            out.println(this.data.get(key));
        }
        printOffset(out,level);
        if (!this.parents.isEmpty()) {
            out.println("parent(s):");
            for (ProvenanceInfo parent : this.parents) {
                parent.printTo(out, level + 1);
            }
        }
    }
}
