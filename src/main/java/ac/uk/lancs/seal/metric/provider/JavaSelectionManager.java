package ac.uk.lancs.seal.metric.provider;

import java.util.LinkedList;
import java.util.Queue;

public class JavaSelectionManager extends MetricManager {

    Queue<Metric> metrics = new LinkedList<Metric>();

    public void add(Metric metric) {
        metrics.add(metric);
    }

    @Override
    protected Queue<Metric> getMetrics() {
        return metrics;
    }

}
