package ac.uk.lancs.seal.metric.provider;

import java.io.File;

public interface MetricCalculator {
    public void process(File file, MetricStorage result, PreprocessStorage<?> storage);

    public void postprocess(MetricStorage result, PreprocessStorage<?> storage);
}
