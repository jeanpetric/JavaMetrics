package ac.uk.lancs.seal.metric.provider;

import java.util.HashMap;
import java.util.Map;

public class GenericMetric {
    protected final Map<String, String> results = new HashMap<>();
    protected final PreprocessStorage<?> preprocessStorage;
    protected final MetricCalculator metricCalculator;
    protected final String fqn;

    public GenericMetric(MetricCalculator metricCalculator, String fqn, PreprocessStorage<?> storage) {
        this.metricCalculator = metricCalculator;
        this.fqn = fqn;
        this.preprocessStorage = storage;
    }

    public void addResult(String key, String value) {
        results.put(fqn + ":" + key, value);
    }

    public PreprocessStorage<?> getPreprocessStorage() {
        return preprocessStorage;
    }

    public Map<String, String> getMetricResult() {
        return results;
    }

    public MetricCalculator getMetricCalculator() {
        return metricCalculator;
    }
}
