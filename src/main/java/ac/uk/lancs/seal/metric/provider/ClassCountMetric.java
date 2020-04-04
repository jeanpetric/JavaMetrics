package ac.uk.lancs.seal.metric.provider;

public class ClassCountMetric implements Metric {
    private GenericMetric classCountMetric;

    public ClassCountMetric() {
        PreprocessStorage<String> storage = new PreprocessStorage<String>();
        classCountMetric = new GenericMetric(new ClassCountCalculator(), "pckg", storage);
    }

    @Override
    public GenericMetric getMetric() {
        return classCountMetric;
    }
}
