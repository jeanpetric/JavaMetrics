package ac.uk.lancs.seal.metric.calculator;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

import com.github.javaparser.StaticJavaParser;
import com.github.javaparser.ast.CompilationUnit;
import com.github.javaparser.ast.ImportDeclaration;
import com.github.javaparser.ast.PackageDeclaration;
import com.github.javaparser.ast.visitor.VoidVisitor;
import com.github.javaparser.ast.visitor.VoidVisitorAdapter;

import ac.uk.lancs.seal.metric.provider.MetricCalculator;
import ac.uk.lancs.seal.metric.provider.PreprocessStorage;

public class FanCalculator implements MetricCalculator {
    private String packageName;

    @Override
    public void process(File file, Map<String, Map<String, String>> result, PreprocessStorage<?> storage) {
        packageName = "";
        Set<String> imports = new HashSet<String>();
        Map<String, Set<String>> preStorage = (Map<String, Set<String>>) storage.get();
        try {
            parseAndVisit(file, imports);
            Set<String> prevImports = preStorage.get(packageName);
            if (prevImports != null) {
                imports.addAll(prevImports);
            }
            preStorage.put(packageName, imports);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    public void postprocess(Map<String, Map<String, String>> result, PreprocessStorage<?> storage) {
        Map<String, Set<String>> preStorage = (Map<String, Set<String>>) storage.get();
        fanIn(result, preStorage);
        fanOut(result, preStorage);
    }

    private void fanOut(Map<String, Map<String, String>> result, Map<String, Set<String>> preStorage) {
        // TODO: wrong logic!
        for (String pckgName : preStorage.keySet()) {
            long count = 0;
            for (String subPckgName : preStorage.keySet()) {
                if (!subPckgName.equals(pckgName)) {
                    for (String importName : preStorage.get(subPckgName)) {
                        count += preStorage.get(subPckgName).stream().filter(p -> p.equals(importName)).count();
                    }
                }
            }
            Map<String, String> resultMap = result.get(fqnToPackageName(pckgName));
            resultMap.put("pckg:fout", String.valueOf(count));
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

    private void fanIn(Map<String, Map<String, String>> result, Map<String, Set<String>> preStorage) {
        // TODO: wrong logic!
        for (String pckgName : preStorage.keySet()) {
            Set<String> imports = preStorage.get(pckgName);
            long fanInCount = imports.stream().filter(p -> p.equals(pckgName)).count();
            Map<String, String> resultMap = result.get(pckgName);
            if (resultMap == null) {
                resultMap = new HashMap<String, String>();
            }
            resultMap.put("pckg:fin", String.valueOf(fanInCount));
            result.put(packageName, resultMap);
        }
    }

    private void parseAndVisit(File file, Set<String> imports) throws FileNotFoundException {
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
}
