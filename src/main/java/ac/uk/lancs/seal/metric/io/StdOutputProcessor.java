package ac.uk.lancs.seal.metric.io;

import ac.uk.lancs.seal.metric.provider.ResultMap;

public class StdOutputProcessor implements OutputProcessor {
    private ResultMap result;

    @Override
    public void toFormat(ResultMap resultMap) {
        result = resultMap;
    }

    @Override
    public void export() {
        result.entrySet().stream().forEach(entry -> {
            System.out.println(entry.getKey());
            entry.getValue().entrySet().forEach(kv -> {
                System.out.println("\t" + kv.getKey() + " -> " + kv.getValue());
            });
        });
    }
}
