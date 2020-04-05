package ac.uk.lancs.seal.metric.calculator;

import ac.uk.lancs.seal.metric.provider.GenericMetric;
import ac.uk.lancs.seal.metric.provider.MapSetStorage;
import ac.uk.lancs.seal.metric.provider.Metric;

public class FanOutMetric implements Metric {

    @Override
    public GenericMetric getMetric() {
        return new GenericMetric("pckg:fanOut", new FanOutCalculator(), new MapSetStorage());
    }

}
