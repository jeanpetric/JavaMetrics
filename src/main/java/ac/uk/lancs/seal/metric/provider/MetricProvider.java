package ac.uk.lancs.seal.metric.provider;

import java.io.File;
import java.util.List;
import java.util.Map;

public interface MetricProvider {
    public void setInputFiles(List<File> files);

    public void setOutputResult(Map<String, Map<String, String>> results);

    public void start();
}
