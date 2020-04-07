package ac.uk.lancs.seal.metric.provider;

import java.io.File;
import java.util.List;

public interface MetricProvider {
    public void setInputFiles(List<File> files);

    public void setOutputResult(ResultMap results);

    public void start();
}
