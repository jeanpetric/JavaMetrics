package ac.uk.lancs.seal.metric.calculator;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.HashMap;
import java.util.Map;

import com.github.javaparser.StaticJavaParser;
import com.github.javaparser.ast.CompilationUnit;
import com.github.javaparser.ast.PackageDeclaration;
import com.github.javaparser.ast.body.ClassOrInterfaceDeclaration;
import com.github.javaparser.ast.visitor.VoidVisitor;
import com.github.javaparser.ast.visitor.VoidVisitorAdapter;

import ac.uk.lancs.seal.metric.provider.GenericMetric;
import ac.uk.lancs.seal.metric.provider.MetricCalculator;

public class ClassCountCalculator implements MetricCalculator {
    private String packageName;
    private int classCount;

    @Override
    public void process(File file, Map<String, Map<String, String>> result, GenericMetric metric) {
        packageName = "";
        classCount = 0;
        try {
            parseAndVisit(file);
            recordCount(result, metric);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    public void postprocess(Map<String, Map<String, String>> result, GenericMetric metric) {
        // NOP
    }

    private void recordCount(Map<String, Map<String, String>> result, GenericMetric metric) {
        String metricFqn = metric.getFqn();
        int currentCount = 0;
        if (!result.containsKey(packageName)) {
            Map<String, String> map = new HashMap<>();
            map.put(metricFqn, "0");
            result.put(packageName, map);
        }
        currentCount = Integer.valueOf(result.get(packageName).get(metricFqn));
        result.get(packageName).put(metricFqn, String.valueOf(currentCount + classCount));
        result.put(packageName, result.get(packageName));
    }

    private void parseAndVisit(File file) throws FileNotFoundException {
        CompilationUnit cu = StaticJavaParser.parse(file);
        VoidVisitor<Void> packageNameVisitor = new PackageNameVisitor();
        VoidVisitor<Void> classCountVisitor = new ClassVisitor();
        packageNameVisitor.visit(cu, null);
        classCountVisitor.visit(cu, null);
    }

    private class PackageNameVisitor extends VoidVisitorAdapter<Void> {
        @Override
        public void visit(PackageDeclaration n, Void arg) {
            super.visit(n, arg);
            packageName = n.getNameAsString();
        }
    }

    private class ClassVisitor extends VoidVisitorAdapter<Void> {
        @Override
        public void visit(ClassOrInterfaceDeclaration n, Void arg) {
            super.visit(n, arg);
            classCount++;
        }
    }
}
