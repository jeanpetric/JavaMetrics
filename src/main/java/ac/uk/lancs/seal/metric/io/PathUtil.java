package ac.uk.lancs.seal.metric.io;

import java.io.File;
import java.nio.file.Path;
import java.util.List;

public interface PathUtil {
    public List<Path> getFilePathsRecursively(String root);

    public List<Path> filterIncludeAbsolutePaths(List<Path> paths, List<Path> filters);

    public List<Path> filterExcludeAbsolutePaths(List<Path> paths, List<Path> filters);

    public List<Path> filterIncludeFilesThatMatch(List<Path> paths, List<String> filters);

    public List<Path> filterExcludeFilesThatMatch(List<Path> paths, List<String> filters);

    public List<File> pathToFiles(List<Path> paths);
}
