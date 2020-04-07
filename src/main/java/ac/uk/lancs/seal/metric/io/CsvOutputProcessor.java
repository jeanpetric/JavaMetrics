package ac.uk.lancs.seal.metric.io;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.io.PrintStream;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Set;

import ac.uk.lancs.seal.metric.provider.ResultMap;

public class CsvOutputProcessor implements OutputProcessor {
    private Set<String> metrics;
    private ResultMap csvFriendlyResultMap;
    private String exportPath;
    private String delimiter;

    public CsvOutputProcessor(String exportPath) {
        this.delimiter = ",";
        this.exportPath = exportPath;
    }

    public CsvOutputProcessor(String delimiter, String exportPath) {
        this.delimiter = delimiter;
        this.exportPath = exportPath;
    }

    @Override
    public void toFormat(ResultMap resultMap) {
        csvFriendlyResultMap = resultMap;
        extractMetrics(resultMap);
        process(resultMap);
    }

    @Override
    public void export() {
        OutputStream stream = getExportStream();
        try {
            stream.write(createCsvHeader(delimiter).getBytes());
            stream.write(createCsvRows(delimiter).getBytes());
            stream.flush();
            stream.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private OutputStream getExportStream() {
        OutputStream stream = new PrintStream(System.out);
        try {
            stream = new FileOutputStream(new File(exportPath));
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
        return stream;
    }

    public String toCsv(String delimiter) {
        StringBuffer resultBuffer = new StringBuffer();
        resultBuffer.append(createCsvHeader(delimiter));
        resultBuffer.append(createCsvRows(delimiter));
        return resultBuffer.toString();
    }

    private String createCsvRows(String delimiter) {
        StringBuffer resultBuffer = new StringBuffer();
        csvFriendlyResultMap.entrySet().stream().forEach(kv -> {
            resultBuffer.append(kv.getKey() + delimiter);
            kv.getValue().entrySet().forEach(m -> resultBuffer.append(m.getValue() + delimiter));
            resultBuffer.append("\n");
        });
        return resultBuffer.toString();
    }

    private String createCsvHeader(String delimiter) {
        String result = "name,";
        for (String metric : metrics) {
            result += metric + delimiter;
        }
        result += "\n";
        return result;
    }

    private ResultMap transpose(ResultMap original) {
        ResultMap transposed = new ResultMap();
        String key = original.keySet().iterator().next();
        if (key != null) {
            Set<String> modules = original.get(key).keySet();
            modules.stream().forEach(m -> transposed.put(m, new HashMap<>()));
        }
        for (String method : transposed.keySet()) {
            for (String metric : original.keySet()) {
                String value = original.get(metric).get(method);
                transposed.get(method).put(metric, value);
            }
        }
        return transposed;
    }

    private void extractMetrics(ResultMap resultMap) {
        metrics = new HashSet<String>();
        metrics.addAll(resultMap.keySet());
    }

    private void process(ResultMap resultMap) {
        csvFriendlyResultMap = transpose(resultMap);
    }
}
