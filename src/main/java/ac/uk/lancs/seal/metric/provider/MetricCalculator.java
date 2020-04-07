package ac.uk.lancs.seal.metric.provider;

import java.io.File;
import java.util.Map;

public interface MetricCalculator {
    public void process(File file, Map<String, String> result, PreprocessStorage<?> storage);
//            throws MetricCalculatorException;

    public void postprocess(Map<String, String> result, PreprocessStorage<?> storage)
            throws MetricCalculatorException;
}
