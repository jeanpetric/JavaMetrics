package ac.uk.lancs.seal.metric.calculator;

import ac.uk.lancs.seal.metric.provider.GenericMetric;
import ac.uk.lancs.seal.metric.provider.Metric;

public class ConcreteClassCountMetric implements Metric {

    private final String metricName = "pckg:concreteClassCount";

    @Override
    public GenericMetric getMetric() {
        return new GenericMetric(metricName, new ConcreteClassCountCalculator(), null);
    }

    @Override
    public String getMetricName() {
        return metricName;
    }
}
