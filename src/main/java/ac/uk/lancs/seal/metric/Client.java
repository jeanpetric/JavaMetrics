package ac.uk.lancs.seal.metric;

import java.io.File;
import java.io.IOException;
import java.nio.file.Path;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

import com.github.javaparser.ParserConfiguration.LanguageLevel;
import com.github.javaparser.StaticJavaParser;

import ac.uk.lancs.seal.metric.io.DefaultPathUtil;
import ac.uk.lancs.seal.metric.io.PathUtil;
import ac.uk.lancs.seal.metric.provider.JavaPackageManager;
import ac.uk.lancs.seal.metric.provider.MetricManager;

public class Client {

    public Client() {
        StaticJavaParser.getConfiguration().setLanguageLevel(LanguageLevel.CURRENT);
        StaticJavaParser.getConfiguration().setPreprocessUnicodeEscapes(true);
    }

    public static void main(String[] args) throws IOException {
        String path = "/home/jean/eclipse-workspace/drummer/src";
//        String path = "/home/jean/eclipse-workspace/camel";
        Map<String, Map<String, String>> results = new HashMap<>();
        PathUtil pathUtil = new DefaultPathUtil();
        List<Path> pathsList = pathUtil.getFilePathsRecursively(path);
        List<String> include = new LinkedList<String>();
        include.add(".*\\.java");
        pathsList = pathUtil.filterIncludeFilesThatMatch(pathsList, include);
        List<String> exclude = new LinkedList<String>();
        exclude.add(".*Test\\.java");
        pathsList = pathUtil.filterExcludeFilesThatMatch(pathsList, exclude);
        List<File> filesList = pathUtil.pathToFiles(pathsList);
        MetricManager metricManager = new JavaPackageManager();
        metricManager.setInputFiles(filesList);
        metricManager.setOutputResult(results);
        metricManager.start();
        for (String p : results.keySet()) {
            System.out.println("metric: " + p);
            for (Entry<String, String> m : results.get(p).entrySet()) {
                System.out.println("\t" + m.getKey() + " -> " + m.getValue());
            }
        }
    }
}
