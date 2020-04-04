package ac.uk.lancs.seal.metric.provider;

import java.util.LinkedList;
import java.util.Queue;

import ac.uk.lancs.seal.metric.calculator.ClassCountMetric;

public class JavaPackageManager extends MetricManager {

    @Override
    protected Queue<Metric> getMetrics() {
        Queue<Metric> metrics = new LinkedList<>();
        metrics.add(new ClassCountMetric());
//        metrics.add(new FanMetric());
        return metrics;
    }
}
