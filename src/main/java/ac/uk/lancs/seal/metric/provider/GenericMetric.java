package ac.uk.lancs.seal.metric.provider;

public class GenericMetric {
    protected final PreprocessStorage<?> preprocessStorage;
    protected final MetricCalculator metricCalculator;
    protected final String fqn;

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
