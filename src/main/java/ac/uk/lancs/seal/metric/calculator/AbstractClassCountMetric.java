package ac.uk.lancs.seal.metric.calculator;

import ac.uk.lancs.seal.metric.provider.GenericMetric;
import ac.uk.lancs.seal.metric.provider.Metric;

public class AbstractClassCountMetric implements Metric {

    private final String metricName = "pckg:abstractClassCount";

    @Override
    public GenericMetric getMetric() {
        return new GenericMetric(metricName, new AbstractClassCountCalculator(), null);
    }

    @Override
    public String getMetricName() {
        return metricName;
    }
}
