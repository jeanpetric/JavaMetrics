package ac.uk.lancs.seal.metric.calculator;

import ac.uk.lancs.seal.metric.provider.GenericMetric;
import ac.uk.lancs.seal.metric.provider.MapSetStorage;
import ac.uk.lancs.seal.metric.provider.Metric;

public class FanInMetric implements Metric {

    private final String metricName = "pckg:fanIn";

    @Override
    public GenericMetric getMetric() {
        return new GenericMetric(metricName, new FanInCalculator(), new MapSetStorage());
    }

    @Override
    public String getMetricName() {
        return metricName;
    }

}
