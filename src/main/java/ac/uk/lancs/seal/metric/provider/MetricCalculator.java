package ac.uk.lancs.seal.metric.provider;

import java.io.File;
import java.util.Map;

public interface MetricCalculator {
    public void process(File file, Map<String, Map<String, String>> result, GenericMetric metric);

    public void postprocess(Map<String, Map<String, String>> result, GenericMetric metric);
}
