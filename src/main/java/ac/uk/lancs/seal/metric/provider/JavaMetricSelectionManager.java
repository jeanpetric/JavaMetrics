package ac.uk.lancs.seal.metric.provider;

import java.util.LinkedList;
import java.util.Queue;

public class JavaMetricSelectionManager extends MetricManager {

    private Queue<Metric> selectedMetrics = new LinkedList<Metric>();

    public void add(Metric metric) {
        selectedMetrics.add(metric);
    }

    public void addAll(Queue<Metric> metricCollection) {
        selectedMetrics.addAll(metricCollection);
    }

    @Override
    protected Queue<Metric> getMetrics() {
        return selectedMetrics;
    }

}
