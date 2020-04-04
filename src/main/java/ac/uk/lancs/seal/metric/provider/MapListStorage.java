package ac.uk.lancs.seal.metric.provider;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class MapListStorage implements PreprocessStorage<Map<String, List<String>>> {
    private Map<String, List<String>> storage = new HashMap<String, List<String>>();

    @Override
    public Map<String, List<String>> get() {
        return storage;
    }

}
