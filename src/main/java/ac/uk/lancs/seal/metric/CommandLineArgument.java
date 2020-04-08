package ac.uk.lancs.seal.metric;

import java.util.Properties;

import com.cleancoder.args.Args;
import com.cleancoder.args.ArgsException;

public class CommandLineArgument {

    /**
     * d - debug, boolean
     * c - config file, String
     * s - source directory, String
     * i - include paths, String[]
     * e - exclude paths, String[]
     * a - include (allow) files, String[]
     * f - exclude (forbid) files, String[]
     */
    public static void process(Properties properties, String[] args) {
        try {
            Args arg = new Args("d,c*,s*,i[*],e[*],a[*],f[*]", args);
            setDebug(properties, arg.getBoolean('d'));
            setString(properties, "configFile", arg.getString('c'), true);
            setString(properties, "projectRoot", arg.getString('s'), true);
            setStringArray(properties, "includePaths", arg.getStringArray('i'));
            setStringArray(properties, "excludePaths", arg.getStringArray('e'));
            setStringArray(properties, "includeFiles", arg.getStringArray('a'));
            setStringArray(properties, "excludeFiles", arg.getStringArray('f'));
        } catch (ArgsException e) {
            System.out.printf("Argument error: %s\n", e.errorMessage());
        }
    }

    private static void setStringArray(Properties properties, String property, String[] stringArray) {
        String options = "";
        for (String el : stringArray) {
            options += el;
        }
        if (options.length() > 0) {
            options = trimLastComma(options);
            properties.setProperty(property, options);
        }
    }

    private static String trimLastComma(String options) {
        if (options.lastIndexOf(",") > 0) {
            options = options.substring(0, options.lastIndexOf(",") - 1);
        }
        return options;
    }

    private static void setString(Properties properties, String property, String string, boolean mandatory) {
        if (mandatory && string.length() < 1) {
            throw new Error("You must provide this command line argument: " + property);
        } else if (string.length() > 0) {
            properties.setProperty(property, string);
        }
    }

    private static void setDebug(Properties properties, boolean boolean1) {
        properties.setProperty("debug", String.valueOf(boolean1));
    }
}
