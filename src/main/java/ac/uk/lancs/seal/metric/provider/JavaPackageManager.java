package ac.uk.lancs.seal.metric.provider;

import java.util.LinkedList;
import java.util.List;

import ac.uk.lancs.seal.metric.calculator.ClassCountCalculator;

public class JavaPackageManager extends MetricManager {

    @Override
    protected List<GenericMetric> getMetrics() {
        List<GenericMetric> metrics = new LinkedList<>();
        metrics.add(new GenericMetric(new ClassCountCalculator(), null));
        return metrics;
    }
}
