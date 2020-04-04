package ac.uk.lancs.seal.metric.provider;

public class GenericMetric {
    private final PreprocessStorage<?> preprocessStorage;
    private final MetricCalculator metricCalculator;

    public GenericMetric(MetricCalculator metricCalculator, PreprocessStorage<?> storage) {
        this.metricCalculator = metricCalculator;
        this.preprocessStorage = storage;
    }

    public PreprocessStorage<?> getPreprocessStorage() {
        return preprocessStorage;
    }

    public MetricCalculator getMetricCalculator() {
        return metricCalculator;
    }
}
