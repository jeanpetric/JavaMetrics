package ac.uk.lancs.seal.metric.calculator;

import java.io.File;
import java.io.IOException;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

import com.github.javaparser.StaticJavaParser;
import com.github.javaparser.ast.CompilationUnit;
import com.github.javaparser.ast.ImportDeclaration;
import com.github.javaparser.ast.PackageDeclaration;
import com.github.javaparser.ast.visitor.VoidVisitor;
import com.github.javaparser.ast.visitor.VoidVisitorAdapter;

import ac.uk.lancs.seal.metric.provider.MapSetStorage;
import ac.uk.lancs.seal.metric.provider.MetricCalculator;
import ac.uk.lancs.seal.metric.provider.PreprocessStorage;

public class FanInCalculator implements MetricCalculator {
    private String packageName = "";

    @Override
    public void process(File file, Map<String, String> result, PreprocessStorage<?> storage) {
        Set<String> importedPackages = new HashSet<String>();
        MapSetStorage preStorage = (MapSetStorage) storage;
        try {
            parseAndVisit(file, importedPackages);
            updatePrestorage(importedPackages, preStorage);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void postprocess(Map<String, String> result, PreprocessStorage<?> storage) {
        MapSetStorage packages = (MapSetStorage) storage;

        packages.get().keySet().forEach(pckg -> {
            long cnt = packages.get().entrySet().stream()
                    .filter(k -> !k.getKey().equals(pckg) && k.getValue().contains(pckg)).count();
            result.put(pckg, String.valueOf(cnt));
        });
    }

    private void updatePrestorage(Set<String> importedPackages, MapSetStorage preStorage) {
        Set<String> currentPackages = preStorage.get().get(packageName);
        if (currentPackages != null) {
            importedPackages.addAll(currentPackages);
        }
        preStorage.get().put(packageName, importedPackages);
    }

    private void parseAndVisit(File file, Set<String> imports) throws IOException {
        CompilationUnit cu = StaticJavaParser.parse(file);
        VoidVisitor<Void> packageNameVisitor = new PackageAndClassNameVisitor();
        VoidVisitor<Set<String>> importVisitor = new ImportVisitor();
        packageNameVisitor.visit(cu, null);
        importVisitor.visit(cu, imports);
    }

    private class PackageAndClassNameVisitor extends VoidVisitorAdapter<Void> {
        @Override
        public void visit(PackageDeclaration n, Void arg) {
            super.visit(n, arg);
            packageName = n.getNameAsString();
        }
    }

    private class ImportVisitor extends VoidVisitorAdapter<Set<String>> {
        @Override
        public void visit(ImportDeclaration n, Set<String> arg) {
            super.visit(n, arg);
            arg.add(fqnToPackageName(n.getNameAsString()));
        }
    }

    private String fqnToPackageName(String fqn) {
        String result = fqn;
        int lastIndex = fqn.lastIndexOf(".");
        if (lastIndex > 0) {
            result = fqn.substring(0, fqn.lastIndexOf("."));
        }
        return result;
    }
}
