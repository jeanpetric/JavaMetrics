package ac.uk.lancs.seal.metric.calculator;

import ac.uk.lancs.seal.metric.provider.GenericMetric;
import ac.uk.lancs.seal.metric.provider.MapSetStorage;
import ac.uk.lancs.seal.metric.provider.Metric;

public class FanInMetric implements Metric {

    @Override
    public GenericMetric getMetric() {
        return new GenericMetric("pckg:fanIn", new FanInCalculator(), new MapSetStorage());
    }

}
