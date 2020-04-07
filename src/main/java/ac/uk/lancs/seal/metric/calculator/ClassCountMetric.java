package ac.uk.lancs.seal.metric.calculator;

import ac.uk.lancs.seal.metric.provider.GenericMetric;
import ac.uk.lancs.seal.metric.provider.Metric;

public class ClassCountMetric implements Metric {

    private final String metricName = "pckg:classCount";

    @Override
    public GenericMetric getMetric() {
        return new GenericMetric(metricName, new ClassCountCalculator(), null);
    }

    @Override
    public String getMetricName() {
        return metricName;
    }
}
