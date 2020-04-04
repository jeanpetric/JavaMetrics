package ac.uk.lancs.seal.metric.provider;

import java.util.LinkedList;
import java.util.Queue;

import ac.uk.lancs.seal.metric.calculator.ClassCountCalculator;
import ac.uk.lancs.seal.metric.calculator.FanCalculator;

public class JavaPackageManager extends MetricManager {

    @Override
    protected Queue<GenericMetric> getMetrics() {
        Queue<GenericMetric> metrics = new LinkedList<>();
        metrics.add(new GenericMetric(new ClassCountCalculator(), null));
        Object fanStorage = null;
        metrics.add(new GenericMetric(new FanCalculator(), null));
        return metrics;
    }
}
