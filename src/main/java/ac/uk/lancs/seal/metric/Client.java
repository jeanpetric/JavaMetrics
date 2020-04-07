package ac.uk.lancs.seal.metric;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Arrays;
import java.util.LinkedList;
import java.util.List;
import java.util.Properties;
import java.util.stream.Collectors;

import com.github.javaparser.ParserConfiguration.LanguageLevel;
import com.github.javaparser.StaticJavaParser;

import ac.uk.lancs.seal.metric.io.CsvOutputProcessor;
import ac.uk.lancs.seal.metric.io.DefaultPathUtil;
import ac.uk.lancs.seal.metric.io.PathUtil;
import ac.uk.lancs.seal.metric.io.ResultManager;
import ac.uk.lancs.seal.metric.provider.JavaPackageManager;
import ac.uk.lancs.seal.metric.provider.MetricManager;
import ac.uk.lancs.seal.metric.provider.ResultMap;

public class Client {

    public Client() {
        StaticJavaParser.getConfiguration().setLanguageLevel(LanguageLevel.CURRENT);
        StaticJavaParser.getConfiguration().setPreprocessUnicodeEscapes(true);
    }

    public static void main(String[] args) throws IOException {
//        String path = "/home/jean/eclipse-workspace/camel";
//        String path = "/home/jean/eclipse-workspace/drummer/src";
        Properties properties = loadConfig(args);
        String path = properties.getProperty("projectRoot");
        List<Path> includePaths = stringsToPaths(Arrays.asList(properties.getProperty("includePaths").split(",")));
        List<Path> excludePaths = stringsToPaths(Arrays.asList(properties.getProperty("excludePaths").split(",")));
        List<String> includeFiles = Arrays.asList(properties.getProperty("includeFiles").split(","));
        List<String> excludeFiles = Arrays.asList(properties.getProperty("excludeFiles").split(","));
        String resultOutput = properties.getProperty("resultOutput");

        ResultMap results = new ResultMap();
        PathUtil pathUtil = new DefaultPathUtil();
        List<Path> pathsList = pathUtil.getFilePathsRecursively(path);
        pathsList = pathUtil.filterIncludeFilesThatMatch(pathsList, includeFiles);
        pathsList = pathUtil.filterExcludeFilesThatMatch(pathsList, excludeFiles);
        pathsList = pathUtil.filterIncludeAbsolutePaths(pathsList, includePaths);
        List<File> filesList = pathUtil.pathToFiles(pathsList);
        MetricManager metricManager = new JavaPackageManager();
        metricManager.setInputFiles(filesList);
        metricManager.setOutputResult(results);
        metricManager.start();
        ResultManager resultManager = new ResultManager(results, new CsvOutputProcessor(resultOutput));
        resultManager.export();
    }

    private static List<Path> stringsToPaths(List<String> paths) {
        List<Path> result = new LinkedList<Path>();
        result = paths.stream().map(p -> Paths.get(p)).collect(Collectors.toList());
        return result;
    }

    private static Properties loadConfig(String[] args) {
        Properties properties = new Properties();
        String configFile = "config.properties";
        if (args.length >= 2) {
            configFile = args[1];
        }
        InputStream inStream;
        try {
            inStream = new FileInputStream(configFile);
            properties.load(inStream);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return properties;
    }
}
