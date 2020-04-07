package ac.uk.lancs.seal.metric.calculator;

import java.util.LinkedList;
import java.util.List;
import java.util.Queue;
import java.util.TreeMap;

import ac.uk.lancs.seal.metric.provider.Metric;

public class JavaMetricFactory {
    private static JavaMetricFactory instance = null;
    private final List<Metric> metrics = new LinkedList<Metric>();
    private final TreeMap<String, Metric> map = new TreeMap<>();

    private JavaMetricFactory() {
        addAllMetrics();
        addAllMetricsWithNames();
    }

    public static JavaMetricFactory getInstance() {
        if (instance == null) {
            instance = new JavaMetricFactory();
        }
        return instance;
    }

    public Queue<Metric> getMetrics(List<String> metricList) {
        Queue<Metric> result = new LinkedList<Metric>();
        metricList.stream().filter(metricName -> map.containsKey(metricName))
                .forEach(existingMetric -> result.add(map.get(existingMetric)));
        return result;
    }

    public Queue<Metric> getMetrics(String prefix) {
        Queue<Metric> result = new LinkedList<Metric>();
        metrics.stream().filter(metric -> metric.getMetricName().startsWith(prefix))
                .forEach(metric -> result.add(metric));
        return result;
    }

    private void addAllMetricsWithNames() {
        metrics.stream().forEach(m -> map.put(m.getMetricName(), m));
    }

    // ALL new Java metrics should be added here
    private void addAllMetrics() {
        metrics.add(new AbstractClassCountMetric());
        metrics.add(new ConcreteClassCountMetric());
        metrics.add(new FanInMetric());
        metrics.add(new FanOutMetric());
    }

}
