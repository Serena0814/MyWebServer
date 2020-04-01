package server;


import java.util.HashSet;
import java.util.Set;

public class Mapping {
    private String name;
    private Set<String> patterns;

    public Mapping() {
        patterns = new HashSet<>();
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Set<String> getPatterns() {
        return patterns;
    }

    public void addPattern(String pattern) {
        this.patterns.add(pattern);
    }
}
