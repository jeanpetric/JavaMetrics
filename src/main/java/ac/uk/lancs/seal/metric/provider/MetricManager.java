package ac.uk.lancs.seal.metric.provider;

import java.io.File;
import java.util.List;
import java.util.Map;

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
        List<GenericMetric> metrics = getMetrics();

        for (GenericMetric metric : metrics) {
            MetricCalculator metricCalculator = metric.getMetricCalculator();
            PreprocessStorage<?> storage = metric.getPreprocessStorage();
            for (File file : files) {
                metricCalculator.process(file, results, storage);
            }
            metricCalculator.postprocess(results, storage);
        }
    }

    protected abstract List<GenericMetric> getMetrics();
}
