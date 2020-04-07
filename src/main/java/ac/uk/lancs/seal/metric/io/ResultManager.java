package ac.uk.lancs.seal.metric.io;

import java.util.Arrays;
import java.util.List;

import ac.uk.lancs.seal.metric.provider.ResultMap;

public class ResultManager {
    private List<OutputProcessor> processors;

    public ResultManager(ResultMap resultMap, OutputProcessor... processors) {
        this.processors = Arrays.asList(processors);
        process(resultMap);
    }

    private void process(ResultMap resultMap) {
        processors.forEach(p -> p.toFormat(resultMap));
    }

    public void export() {
        processors.forEach(p -> p.export());
    }
}
