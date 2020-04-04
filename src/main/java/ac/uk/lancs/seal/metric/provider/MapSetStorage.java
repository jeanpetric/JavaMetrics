package ac.uk.lancs.seal.metric.provider;

import java.util.HashMap;
import java.util.Map;
import java.util.Set;

public class MapSetStorage implements PreprocessStorage<Map<String, Set<String>>> {
    private Map<String, Set<String>> storage = new HashMap<String, Set<String>>();

    @Override
    public Map<String, Set<String>> get() {
        return storage;
    }

}
