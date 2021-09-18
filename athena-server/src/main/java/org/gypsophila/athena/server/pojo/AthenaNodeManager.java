package org.gypsophila.athena.server.pojo;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

public class AthenaNodeManager {
    
    private final Map<String, DataNode> nodes;
    
    public AthenaNodeManager() {
        nodes = new ConcurrentHashMap<>();
    }
    
    public DataNode put(String path, DataNode node) {
        DataNode oldNode = nodes.put(path, node);
        return oldNode;
    }
    

}