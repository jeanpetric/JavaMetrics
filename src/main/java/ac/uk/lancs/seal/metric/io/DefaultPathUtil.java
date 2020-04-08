package ac.uk.lancs.seal.metric.io;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.LinkedList;
import java.util.List;
import java.util.stream.Collectors;

public class DefaultPathUtil implements PathUtil {

    @Override
    public List<Path> getFilePathsRecursively(String root) {
        List<Path> result = new LinkedList<Path>();
        try {
            result = Files.walk(Paths.get(root)).filter(Files::isRegularFile).collect(Collectors.toList());
        } catch (IOException e) {
            e.printStackTrace();
        }
        return result;
    }

    @Override
    public List<Path> filterIncludeAbsolutePaths(List<Path> paths, List<Path> filters) {
        return paths.stream().filter(p -> {
            return filters.stream().filter(f -> p.toString().contains(f.toString())).count() > 0 ? true : false;
        }).collect(Collectors.toList());
    }

    @Override
    public List<Path> filterExcludeAbsolutePaths(List<Path> paths, List<Path> filters) {
        return paths.stream().filter(p -> {
            return filters.stream().filter(f -> p.toString().contains(f.toString())).count() > 0 ? false : true;
        }).collect(Collectors.toList());
    }

    @Override
    public List<Path> filterIncludeFilesThatMatch(List<Path> paths, List<String> filters) {
        return paths.stream().filter(p -> {
            return filters.stream().anyMatch(f -> {
                return p.getFileName().toString().matches(f);
            });
        }).collect(Collectors.toList());
    }

    @Override
    public List<Path> filterExcludeFilesThatMatch(List<Path> paths, List<String> filters) {
        return paths.stream().filter(p -> {
            return !filters.stream().anyMatch(f -> {
                return p.getFileName().toString().matches(f);
            });
        }).collect(Collectors.toList());
    }

    @Override
    public List<File> pathToFiles(List<Path> paths) {
        return paths.stream().map(p -> p.toFile()).collect(Collectors.toList());
    }
}
