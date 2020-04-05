package ac.uk.lancs.seal.metric.io;

import java.util.HashMap;
import java.util.Map;
import java.util.Set;

public class ResultManager {
    private Map<String, Map<String, String>> results;
    
    public ResultManager(Map<String, Map<String, String>> resultMap) {
        results = resultMap;
    }
    
    public void toCsv(String csvFilename) {
    }
    
    private Map<String, Map<String, String>> transpose(Map<String, Map<String, String>> original) {
        Map<String, Map<String, String>> transposed = new HashMap<String, Map<String, String>>();
        Set<String> keys = original.keySet();
        while (keys.iterator().hasNext()) {
            String metric = keys.iterator().next();
            // TODO
        }
        return transposed;
    }

    private boolean process() {
        boolean result = true;
        Set<String> metrics = results.keySet();
        return result;
    }
}
