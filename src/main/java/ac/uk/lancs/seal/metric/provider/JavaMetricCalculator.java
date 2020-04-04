package ac.uk.lancs.seal.metric.provider;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;
import java.util.Map.Entry;
import java.util.stream.Collectors;

import com.github.javaparser.ParserConfiguration.LanguageLevel;
import com.github.javaparser.StaticJavaParser;
import com.github.javaparser.ast.CompilationUnit;
import com.github.javaparser.ast.ImportDeclaration;
import com.github.javaparser.ast.PackageDeclaration;
import com.github.javaparser.ast.body.ClassOrInterfaceDeclaration;
import com.github.javaparser.ast.visitor.VoidVisitor;
import com.github.javaparser.ast.visitor.VoidVisitorAdapter;

public class JavaMetricCalculator {
    public JavaMetricCalculator() {
        StaticJavaParser.getConfiguration().setLanguageLevel(LanguageLevel.JAVA_8);
    }

    public static void main(String[] args) throws IOException {
        List<Path> files = Files.walk(Paths.get("/home/jean/eclipse-workspace/drummer/src"))
                .filter(Files::isRegularFile).filter(f -> f.getFileName().toString().endsWith("java"))
                .collect(Collectors.toList());
//        files.forEach(f -> System.out.println(f.getFileName().toString()));
//        File file = new File("/home/jean/eclipse-workspace/drummer/src/main/java/gui/StaticModePlayer.java");
        System.out.println(files.size());
        MetricCalculator classCountCalculator = new ClassCountCalculator();
        MetricStorage result = new MetricStorage();
        PreprocessStorage<Integer> storage = new PreprocessStorage<Integer>();
        for (Path file : files) {
            classCountCalculator.process(file.toFile(), result, storage);
        }
        classCountCalculator.postprocess(result, storage);
        for (Entry<String, String> r : result.entrySet()) {
            System.out.println(r.getKey() + " -> " + r.getValue());
        }
    }

    public void processFile(File file) throws FileNotFoundException {
        CompilationUnit cu = StaticJavaParser.parse(file);
        VoidVisitor<Void> visitor = new PackageVisitor();
        visitor.visit(cu, null);
    }

    private class PackageVisitor extends VoidVisitorAdapter<Void> {
        @Override
        public void visit(PackageDeclaration n, Void arg) {
            super.visit(n, arg);
            System.out.println("Package: " + n.getNameAsString());
        }

        @Override
        public void visit(ImportDeclaration n, Void arg) {
            super.visit(n, arg);
            System.out.println("Import: " + n.getNameAsString());
        }

        @Override
        public void visit(ClassOrInterfaceDeclaration n, Void arg) {
            super.visit(n, arg);
            System.out.println("Class: " + n.getNameAsString());
        }
    }
}
