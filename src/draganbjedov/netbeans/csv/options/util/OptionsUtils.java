package draganbjedov.netbeans.csv.options.util;

import draganbjedov.netbeans.csv.options.CSVEditorPanel;
import java.util.ArrayList;
import java.util.List;
import org.openide.util.NbPreferences;

/*
 * OptionsUtils.java
 *
 * Created on Sep 14, 2013, 6:37:26 PM
 *
 * @author Dragan Bjedov
 */
public class OptionsUtils {

    private static final String KEY_DEFAULT_SEPARATOR = "defSeparator";
    private static final String KEY_CUSTOM_SEPARATOR_COUNT = "customSeparatorCount";
    private static final String KEY_CUSTOM_SEPARATOR = "customSeparator";

    private static void saveProperty(String key, String value) {
        NbPreferences.forModule(CSVEditorPanel.class).put(key, value);
    }

    private static String readProperty(String key, String defaultValue) {
        return NbPreferences.forModule(CSVEditorPanel.class).get(key, defaultValue);
    }

    public static String readDefaultSeparator() {
        return readProperty(KEY_DEFAULT_SEPARATOR, ",");
    }

    public static void saveDefaultSeparator(String s) {
        saveProperty(KEY_DEFAULT_SEPARATOR, s);
    }

    public static int readCustomSeparatorCount() {
        int count = 0;
        try {
            count = Integer.parseInt(readProperty(KEY_CUSTOM_SEPARATOR_COUNT, "0"));
        } catch (NumberFormatException nfe) {
        }
        return count;
    }

    public static void saveCustomSeparatorCount(int count) {
        saveProperty(KEY_CUSTOM_SEPARATOR_COUNT, "" + count);
    }

    public static List<String> readCustomSeparators(int count) {
        List<String> s = new ArrayList<String>(count);
        for (int i = 0; i < count; i++) {
            s.add(readProperty(KEY_CUSTOM_SEPARATOR + i, ""));
        }
        return s;
    }

    public static void saveCustomSeparators(String[] s) {
        for (int i = 0; i < s.length; i++) {
            saveProperty(KEY_CUSTOM_SEPARATOR + i, s[i]);
        }
    }
}
