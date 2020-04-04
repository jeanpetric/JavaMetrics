package ac.uk.lancs.seal.metric.processor;

import java.util.LinkedList;
import java.util.List;
import java.util.concurrent.ConcurrentHashMap;

public class ResultStorage {
    private ConcurrentHashMap<String, List<Metric>> results = new ConcurrentHashMap<>();
    private static ResultStorage instance = new ResultStorage();

    private ResultStorage() {
    }

    public static ResultStorage getInstance() {
        return instance;
    }

    public void add(String key, Metric value) {
        List<Metric> metrics = results.get(key);
        if (metrics == null) {
            metrics = new LinkedList<>();
            results.put(key, metrics);
        }
        metrics.add(value);
    }
}
