package ac.uk.lancs.seal.metric.calculator;

import ac.uk.lancs.seal.metric.provider.GenericMetric;
import ac.uk.lancs.seal.metric.provider.MapSetStorage;
import ac.uk.lancs.seal.metric.provider.Metric;

public class FanOutMetric implements Metric {

    private final String metricName = "pckg:fanOut";

    @Override
    public GenericMetric getMetric() {
        return new GenericMetric(metricName, new FanOutCalculator(), new MapSetStorage());
    }

    @Override
    public String getMetricName() {
        return metricName;
    }

}
