package ac.uk.lancs.seal.metric.provider;

import java.io.IOException;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

import com.github.javaparser.ParserConfiguration.LanguageLevel;
import com.github.javaparser.StaticJavaParser;

import ac.uk.lancs.seal.metric.io.DefaultPathUtil;
import ac.uk.lancs.seal.metric.io.PathUtil;

public class Client {
    public Client() {
        StaticJavaParser.getConfiguration().setLanguageLevel(LanguageLevel.CURRENT);
        StaticJavaParser.getConfiguration().setPreprocessUnicodeEscapes(true);
    }

    public static void main(String[] args) throws IOException {
        String path = "/home/jean/eclipse-workspace/drummer/src";
//        String path = "/home/jean/eclipse-workspace/camel";
//        List<Path> files = Files.walk(Paths.get(path))
//                .filter(Files::isRegularFile).filter(f -> f.getFileName().toString().endsWith("java"))
//                .collect(Collectors.toList());
//        files.forEach(f -> System.out.println(f.getFileName()));
//        List<File> filesList = files.stream().map(f -> f.toFile()).collect(Collectors.toList());
        Map<String, Map<String, String>> results = new HashMap<>();
        PathUtil pathUtil = new DefaultPathUtil();
        List<Path> filesList = pathUtil.getFilePathsRecursively(path);
        List<String> include = new LinkedList<String>();
        include.add(".*\\.java");
        filesList = pathUtil.filterIncludeFilesThatMatch(filesList, include);
        List<String> exclude = new LinkedList<String>();
        exclude.add(".*Test\\.java");
        filesList = pathUtil.filterExcludeFilesThatMatch(filesList, exclude);
        List<Path> excludePath = new LinkedList<>();
        excludePath.add(Paths.get("/home/jean/eclipse-workspace/drummer/src/main/java/engine"));
        filesList = pathUtil.filterExcludeAbsolutePaths(filesList, excludePath);
        filesList.stream().forEach(p -> System.out.println(p.getFileName().toString()));
//        MetricManager metricManager = new JavaPackageManager();
//        metricManager.setInputFiles(filesList);
//        metricManager.setOutputResult(results);
//        metricManager.start();
//        for (String p : results.keySet()) {
//            System.out.println("metric: " + p);
//            for (Entry<String, String> m : results.get(p).entrySet()) {
//                System.out.println("\t" + m.getKey() + " -> " + m.getValue());
//            }
//        }
    }
}
