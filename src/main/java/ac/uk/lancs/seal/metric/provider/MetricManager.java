package ac.uk.lancs.seal.metric.provider;

import java.io.File;
import java.io.IOException;
import java.text.MessageFormat;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Queue;
import java.util.logging.Level;
import java.util.logging.Logger;

public abstract class MetricManager implements MetricProvider {
    private static final Logger LOGGER = Logger.getLogger(MetricManager.class.getName());
    private List<File> files;
    private Queue<Metric> metrics;
    private ResultMap results;
    private long metricsCount, filesCount;
    private long currentProgress, totalProgress;
    private int progressStep;
    private int nextProgressReport;

    public MetricManager() {
        metrics = getMetrics();
        metricsCount = metrics.size();
    }

    @Override
    public void setInputFiles(List<File> files) {
        this.files = files;
        filesCount = files.size();
    }

    @Override
    public void setOutputResult(ResultMap results) {
        this.results = results;
    }

    @Override
    public void start() {
        setProgress();
        for (int i = 0; i < metricsCount; i++) {
            GenericMetric metric = metrics.remove().getMetric();
            String metricFqn = metric.getFqn();
            MetricCalculator metricCalculator = metric.getMetricCalculator();
            PreprocessStorage<?> storage = metric.getPreprocessStorage();
            Map<String, String> tmpResult = new HashMap<>();
            for (File file : files) {
                String filePath = "";
                try {
                    filePath = file.getCanonicalPath().toString();
                    metricCalculator.process(file, tmpResult, storage);
                } catch (MetricCalculatorException e) {
                    LOGGER.log(Level.INFO, "cannot process {0} file", filePath);
                } catch (IOException e) {
                    e.printStackTrace();
                }
                updateProgress();
            }
            try {
                metricCalculator.postprocess(tmpResult, storage);
            } catch (MetricCalculatorException e) {
                LOGGER.log(Level.INFO, "postprocessing of {0} failed", metric);
            }
            mergeResults(metricFqn, tmpResult);
        }
    }

    private void mergeResults(String metricFqn, Map<String, String> metricResults) {
        results.put(metricFqn, metricResults);
    }

    private void setProgress() {
        currentProgress = 0;
        totalProgress = metricsCount * filesCount;
        progressStep = 5;
        nextProgressReport = progressStep;
    }

    /*
     * TODO: allow this class to receive and observer which will notify about
     * progress to the outside world
     */
    private void updateProgress() {
        currentProgress++;
        long progress = (long) ((currentProgress / (double) totalProgress) * 100);
        if (progress == nextProgressReport) {
            String output = MessageFormat.format("processed {0} out of {1}", currentProgress, totalProgress);
            LOGGER.log(Level.INFO, output);
            nextProgressReport += progressStep;
        }
    }

    protected abstract Queue<Metric> getMetrics();
}
