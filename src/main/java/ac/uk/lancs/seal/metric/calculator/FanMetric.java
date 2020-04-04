package ac.uk.lancs.seal.metric.calculator;

import ac.uk.lancs.seal.metric.provider.GenericMetric;
import ac.uk.lancs.seal.metric.provider.MapSetStorage;
import ac.uk.lancs.seal.metric.provider.Metric;

public class FanMetric implements Metric {

    @Override
    public GenericMetric getMetric() {
        return new GenericMetric(new FanCalculator(), new MapSetStorage());
    }
}
