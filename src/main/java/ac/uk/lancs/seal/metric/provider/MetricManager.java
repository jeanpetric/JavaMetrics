package ac.uk.lancs.seal.metric.provider;

import java.io.File;
import java.util.List;
import java.util.Map;
import java.util.Queue;

public abstract class MetricManager implements MetricProvider {
    private List<File> files;
    private Map<String, Map<String, String>> results;

    @Override
    public void setInputFiles(List<File> files) {
        this.files = files;
    }

    @Override
    public void setOutputResult(Map<String, Map<String, String>> results) {
        this.results = results;
    }

    @Override
    public void start() {
        Queue<Metric> metrics = getMetrics();
        int queueSize = metrics.size();

        for (int i = 0; i < queueSize; i++) {
            GenericMetric metric = metrics.remove().getMetric();
            MetricCalculator metricCalculator = metric.getMetricCalculator();
            PreprocessStorage<?> storage = metric.getPreprocessStorage();
            for (File file : files) {
                // SHOULD PASS SIMPLE RESULTS AND THEN METRICMANAGER CAN ADD THIS SIMPLE RESULT
                // INTO A PROPER RESULT MAP. OTHERWISE METRICS CAN OVERWRITE RESULTS OF OTHER
                // METRICS!!! metric -> packageName : value
                metricCalculator.process(file, results, storage);
            }
            metricCalculator.postprocess(results, storage);
        }
    }

    private void mergeResults(Object tempResults) {
    }

    protected abstract Queue<Metric> getMetrics();
}
