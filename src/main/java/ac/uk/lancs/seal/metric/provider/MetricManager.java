package ac.uk.lancs.seal.metric.provider;

public abstract class MetricManager {
    protected String version;
    protected String level;

    public MetricManager(String version, String level) {
        this.version = version;
        this.level = level;
    }
}
