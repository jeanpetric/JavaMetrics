package ac.uk.lancs.seal.metric.io;

import ac.uk.lancs.seal.metric.provider.ResultMap;

public interface OutputProcessor {
    public void toFormat(ResultMap resultMap);

    public void export();
}
