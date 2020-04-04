package ac.uk.lancs.seal.metric.provider;

import java.util.LinkedList;
import java.util.List;

public class ListStorage implements PreprocessStorage<List<String>> {
    private List<String> storage = new LinkedList<>();

    @Override
    public List<String> get() {
        return storage;
    }
}
