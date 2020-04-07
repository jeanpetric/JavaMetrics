package ac.uk.lancs.seal.metric.calculator;

import java.util.Queue;

import ac.uk.lancs.seal.metric.provider.Metric;

public class JavaPackageMetricFactory {

    public static Queue<Metric> getMetrics() {
        return JavaMetricFactory.getInstance().getMetrics("pckg:");
    }
}
