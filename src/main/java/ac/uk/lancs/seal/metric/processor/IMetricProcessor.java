package ac.uk.lancs.seal.metric.processor;

import java.util.Map;

public interface IMetricProcessor {
    Map<String, Metric> getResults();
}
