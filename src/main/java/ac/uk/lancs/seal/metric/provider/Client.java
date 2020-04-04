package ac.uk.lancs.seal.metric.provider;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.stream.Collectors;

import com.github.javaparser.ParserConfiguration.LanguageLevel;
import com.github.javaparser.StaticJavaParser;

public class Client {
    public Client() {
        StaticJavaParser.getConfiguration().setLanguageLevel(LanguageLevel.JAVA_8);
    }

    public static void main(String[] args) throws IOException {
        List<Path> files = Files.walk(Paths.get("/home/jean/eclipse-workspace/drummer/src/main/java"))
                .filter(Files::isRegularFile).filter(f -> f.getFileName().toString().endsWith("java"))
                .collect(Collectors.toList());
        files.forEach(f -> System.out.println(f.getFileName()));
        List<File> filesList = files.stream().map(f -> f.toFile()).collect(Collectors.toList());
        Map<String, Map<String, String>> results = new HashMap<>();
        MetricManager metricManager = new JavaPackageManager();
        metricManager.setInputFiles(filesList);
        metricManager.setOutputResult(results);
        metricManager.start();
        for (String p : results.keySet()) {
            System.out.println("package: " + p);
            for (Entry<String, String> m : results.get(p).entrySet()) {
                System.out.println(m.getKey() + " -> " + m.getValue());
            }
        }
    }
}
