package server;


import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

public class WebContext {
    private List<Entity> entities;
    private List<Mapping> mappings;
    private Map<String, String> entityMap = new HashMap<>(); //放入键值对(name)->(class)
    private Map<String, String> mappingMap = new HashMap<>();//放入键值对(url-pattern)->(name)

    public WebContext(List<Entity> entities, List<Mapping> mappings) {
        this.entities = entities;
        this.mappings = mappings;
        for (Entity entity : entities) {
            entityMap.put(entity.getName(), entity.getClz());
        }
        for (Mapping mapping : mappings) {
            Set<String> patterns = mapping.getPatterns();
            String name = mapping.getName();
            for (String pattern : patterns) {
                mappingMap.put(pattern, name);
            }
        }
    }

    //根据url-pattern找到classname
    public String getClassName(String urlPattern) {
        return entityMap.get(mappingMap.get(urlPattern));
    }
}
