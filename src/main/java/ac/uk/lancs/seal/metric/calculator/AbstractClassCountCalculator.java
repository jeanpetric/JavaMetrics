package ac.uk.lancs.seal.metric.calculator;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.Map;

import com.github.javaparser.StaticJavaParser;
import com.github.javaparser.ast.CompilationUnit;
import com.github.javaparser.ast.PackageDeclaration;
import com.github.javaparser.ast.body.ClassOrInterfaceDeclaration;
import com.github.javaparser.ast.visitor.VoidVisitor;
import com.github.javaparser.ast.visitor.VoidVisitorAdapter;

import ac.uk.lancs.seal.metric.provider.MetricCalculator;
import ac.uk.lancs.seal.metric.provider.PreprocessStorage;

public class AbstractClassCountCalculator implements MetricCalculator {
    private String packageName = "";
    private int classCount = 0;

    @Override
    public void process(File file, Map<String, String> result, PreprocessStorage<?> storage) {
        packageName = "";
        classCount = 0;
        try {
            parseAndVisit(file);
            recordCount(result);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    public void postprocess(Map<String, String> result, PreprocessStorage<?> storage) {
        // NOP
    }

    private void recordCount(Map<String, String> result) {
        int currentCount = 0;
        if (!result.containsKey(packageName)) {
            result.put(packageName, "1");
        } else {
            currentCount = Integer.valueOf(result.get(packageName));
            result.put(packageName, String.valueOf(currentCount + classCount));
        }
    }

    private void parseAndVisit(File file) throws FileNotFoundException {
        CompilationUnit cu = null;
        String filePath = null;
        try {
            filePath = file.getCanonicalPath();
            cu = StaticJavaParser.parse(file);
        } catch (Exception e) {
            System.out.println(filePath);
        }
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
            if (n.isAbstract() || n.isInterface()) {
                classCount++;
            }
        }
    }
}
