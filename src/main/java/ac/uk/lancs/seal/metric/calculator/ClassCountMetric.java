package ac.uk.lancs.seal.metric.calculator;

import ac.uk.lancs.seal.metric.provider.GenericMetric;
import ac.uk.lancs.seal.metric.provider.Metric;

public class ClassCountMetric implements Metric {
    private GenericMetric classCountMetric;

    public ClassCountMetric() {
        classCountMetric = new GenericMetric(new ClassCountCalculator(), null);
    }

    @Override
    public GenericMetric getMetric() {
        return classCountMetric;
    }
}
