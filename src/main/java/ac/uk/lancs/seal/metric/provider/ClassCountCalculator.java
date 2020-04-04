package ac.uk.lancs.seal.metric.provider;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

import com.github.javaparser.StaticJavaParser;
import com.github.javaparser.ast.CompilationUnit;
import com.github.javaparser.ast.PackageDeclaration;
import com.github.javaparser.ast.body.ClassOrInterfaceDeclaration;
import com.github.javaparser.ast.visitor.VoidVisitor;
import com.github.javaparser.ast.visitor.VoidVisitorAdapter;

public class ClassCountCalculator implements MetricCalculator {

    @Override
    public void process(File file, MetricStorage result, PreprocessStorage<?> storage) {
        PreprocessStorage<Integer> preStorage = (PreprocessStorage<Integer>) storage;
        List<String> classCountList = new LinkedList<String>();
        try {
            CompilationUnit cu = StaticJavaParser.parse(file);
            VoidVisitor<List<String>> packageNameVisitor = new PackageNameVisitor();
            VoidVisitor<List<String>> classCountVisitor = new ClassVisitor();
            List<String> packageNameList = new LinkedList<>();
            packageNameVisitor.visit(cu, packageNameList);
            classCountVisitor.visit(cu, classCountList);
            String packageName = packageNameList.get(0);
            int classCount = classCountList.size();
            if (preStorage.containsKey(packageName)) {
                classCount += preStorage.get(packageName);
            }
            preStorage.put(packageName, classCount);
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void postprocess(MetricStorage result, PreprocessStorage<?> storage) {
        PreprocessStorage<Integer> preStorage = (PreprocessStorage<Integer>) storage;
        for (Map.Entry<String, Integer> kv : preStorage.entrySet()) {
            result.put(kv.getKey(), String.valueOf(kv.getValue()));
        }
    }

    private class PackageNameVisitor extends VoidVisitorAdapter<List<String>> {
        @Override
        public void visit(PackageDeclaration n, List<String> arg) {
            super.visit(n, arg);
            arg.add(n.getNameAsString());
        }
    }

    private class ClassVisitor extends VoidVisitorAdapter<List<String>> {
        @Override
        public void visit(ClassOrInterfaceDeclaration n, List<String> arg) {
            super.visit(n, arg);
            arg.add("");
        }
    }
}
