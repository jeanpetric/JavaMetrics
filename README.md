# JavaMetrics

Extensible tool for gathering static code metrics for Java. At the moment, this is the only JavaMetrics tool which has all of the following benefits:

- opensource and free to use for commercial or private use (GNU GPL v3 Licence)
- easily extensible
- allows selection of specific static code metrics
- runs on source code (rather than byte code)
- can be used as a library in any Java code

# Licence

Licence is GNU GPL v3.

Please note that this package contains Uncle Bob's `javaargs` code which is included in this project with his generous permission. His code is available [here](https://github.com/unclebob/javaargs).

# Running the tool

## Installation

Download a copy from `releases` tab and then (unix instructions):

```
unzip JavaMetrics-<version>.zip
cd JavaMetrics-<version>
mvn package
```

## Run
Once done, the executable file will be located in the `target` directory and can be executed as follows:

`java -jar JavaMetrics-<version>-release-jar-with-dependencies.jar <options>`

Read for the list of possible options below.

# Options

This options are available since version `0.4.1`:

- `-c confFile.properties` [mandatory]: configuration file, see for an example below
- `-s dir` [mandatory]: absolute path to the root source directory, e.g. /home/user/workspace/my_java_project
- `-o output` [mandatory]: where the output file is to be stored, e.g. /home/user/workspace/my_java_project/metrics.csv
- `-i path`: relative path within the root source directory to scan; to add multiple include paths type `-i path1 -i path2` etc.
- `-e path`: relative path within the root source directory to exclude from scanning; same as above to include multiple
- `-a pattern`: regular expression for files to include, e.g. `.*\\.java`; same as above to include multiple
- `-f pattern`: regular expression for files to exclude, e.g. `.*Test\\.java`; same as above to include multiple
- `-m metric`: full qualified name of the metric of interest, e.g. `pckg:fanIn`; same as above to include multiple

NB: All non mandatory options can be specified within a config file. If options specified on both, command line and config file, the priority is given to the command line arguments.

# Metrics

The following are fully qualified names of the metrics that exist in version `0.4.1`:

- `pckg:fanIn`
- `pckg:fanOut`
- `pckg:abstractClassCount`
- `pckg:concreteClassCount`

Feel free to contribute more metrics via pull requests. Find out more about how to contribute below.

# Example of a config.properties file

```
includePaths=
excludePaths=archetypes/
includeFiles=.*\\.java
excludeFiles=.*Test\\.java,module-info.java
selectedMetrics=pckg:fanIn,pckg:fanOut,pckg:concreteClassCount,pckg:abstractClassCount
```
# Troubleshooting

## Error parsing a file
JavaMetrics is built on top of JavaParser (though, JavaMetrics can be easily extended to support other parsers and/or languages). In some cases JavaParser cannot properly parse a `java` file. For example, as of version `3.15.17` of JavaParser, parsing Java `modules` is not possible. If there is a file on path which cannot be parsed, either because the file has content that is not supported by JavaParser or is malformed, JavaParser will throw a fatal `Error`. This will also cause JavaMetrics to crash. The only workaround is to identify which file(s) cause this crash and exclude them (either individually, or all files on a specific path). To identify which file(s) cause issues run JavaMetrics in `debug` mode (which lists each file that is currently being processed) and wait for the `Error` to show up (the issues is most often in the file just above the error message).

# How to contribute

Todo
