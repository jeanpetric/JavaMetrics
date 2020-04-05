package ac.uk.lancs.seal.metric.calculator;

import ac.uk.lancs.seal.metric.provider.GenericMetric;
import ac.uk.lancs.seal.metric.provider.Metric;

public class ConcreteClassCountMetric implements Metric {
    private GenericMetric classCountMetric;

    public ConcreteClassCountMetric() {
        classCountMetric = new GenericMetric("pckg:concreteClassCount", new ConcreteClassCountCalculator(), null);
    }

    @Override
    public GenericMetric getMetric() {
        return classCountMetric;
    }
}
