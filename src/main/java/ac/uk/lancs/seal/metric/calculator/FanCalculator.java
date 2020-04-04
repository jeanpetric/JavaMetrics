package ac.uk.lancs.seal.metric.calculator;

import java.io.File;
import java.util.Map;

import ac.uk.lancs.seal.metric.provider.MetricCalculator;
import ac.uk.lancs.seal.metric.provider.PreprocessStorage;

public class FanCalculator implements MetricCalculator {

    @Override
    public void process(File file, Map<String, Map<String, String>> result, PreprocessStorage<?> storage) {
    }

    @Override
    public void postprocess(Map<String, Map<String, String>> result, PreprocessStorage<?> storage) {
    }
}
