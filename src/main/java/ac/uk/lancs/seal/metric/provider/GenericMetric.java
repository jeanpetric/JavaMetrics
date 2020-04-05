package ac.uk.lancs.seal.metric.provider;

public class GenericMetric {
    private final PreprocessStorage<?> preprocessStorage;
    private final MetricCalculator metricCalculator;
    private final String fqn;

    public GenericMetric(String fqn, MetricCalculator metricCalculator, PreprocessStorage<?> storage) {
        this.fqn = fqn;
        this.metricCalculator = metricCalculator;
        this.preprocessStorage = storage;
    }

    public PreprocessStorage<?> getPreprocessStorage() {
        return preprocessStorage;
    }

    public MetricCalculator getMetricCalculator() {
        return metricCalculator;
    }

    public String getFqn() {
        return fqn;
    }
}
