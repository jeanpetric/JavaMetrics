package ac.uk.lancs.seal.metric.calculator;

import ac.uk.lancs.seal.metric.provider.GenericMetric;
import ac.uk.lancs.seal.metric.provider.Metric;

public class AbstractClassCountMetric implements Metric {
    private GenericMetric classCountMetric;

    public AbstractClassCountMetric() {
        classCountMetric = new GenericMetric("pckg:abstractClassCount", new AbstractClassCountCalculator(), null);
    }

    @Override
    public GenericMetric getMetric() {
        return classCountMetric;
    }
}
