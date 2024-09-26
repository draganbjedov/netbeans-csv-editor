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

    private static final String DEFAULT_SEPARATOR = ",";
    private static final String KEY_DEFAULT_SEPARATOR = "defSeparator";
    private static final String KEY_CUSTOM_SEPARATOR_COUNT = "customSeparatorCount";
    private static final String KEY_CUSTOM_SEPARATOR = "customSeparator";

    private static final String DEFAULT_ESCAPE_CHAR = "\"";
    private static final String KEY_DEFAULT_ESCAPE_CHAR = "defEscapeChar";
    private static final String KEY_CUSTOM_ESCAPE_CHAR_COUNT = "customEscapeCharCount";
    private static final String KEY_CUSTOM_ESCAPE_CHAR = "customEscapeChar";

    private static void saveProperty(String key, String value) {
        NbPreferences.forModule(CSVEditorPanel.class).put(key, value);
    }

    private static String readProperty(String key, String defaultValue) {
        return NbPreferences.forModule(CSVEditorPanel.class).get(key, defaultValue);
    }

    public static char readDefaultSeparator() {
        return readProperty(KEY_DEFAULT_SEPARATOR, DEFAULT_SEPARATOR).charAt(0);
    }

    public static void saveDefaultSeparator(Character c) {
        saveProperty(KEY_DEFAULT_SEPARATOR, c.toString());
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

    public static List<Character> readCustomSeparators(int count) {
        List<Character> chars = new ArrayList<>(count);
        for (int i = 0; i < count; i++) {
            chars.add(readProperty(KEY_CUSTOM_SEPARATOR + i, "").charAt(0));
        }
        return chars;
    }

    public static void saveCustomSeparators(Character[] chars) {
        for (int i = 0; i < chars.length; i++) {
            saveProperty(KEY_CUSTOM_SEPARATOR + i, chars[i].toString());
        }
    }

    public static char readDefaultEscapeChar() {
        return readProperty(KEY_DEFAULT_ESCAPE_CHAR, DEFAULT_ESCAPE_CHAR).charAt(0);
    }

    public static void saveDefaultEscapeChar(Character c) {
        saveProperty(KEY_DEFAULT_ESCAPE_CHAR, c.toString());
    }

    public static int readCustomEscapeCharCount() {
        int count = 0;
        try {
            count = Integer.parseInt(readProperty(KEY_CUSTOM_ESCAPE_CHAR_COUNT, "0"));
        } catch (NumberFormatException nfe) {
        }
        return count;
    }

    public static void saveCustomEscapeCharCount(int count) {
        saveProperty(KEY_CUSTOM_ESCAPE_CHAR_COUNT, "" + count);
    }

    public static List<Character> readCustomEscapeChars(int count) {
        List<Character> chars = new ArrayList<>(count);
        for (int i = 0; i < count; i++) {
            chars.add(readProperty(KEY_CUSTOM_ESCAPE_CHAR + i, "").charAt(0));
        }
        return chars;
    }

    public static void saveCustomEscapeChars(Character[] chars) {
        for (int i = 0; i < chars.length; i++) {
            saveProperty(KEY_CUSTOM_ESCAPE_CHAR + i, chars[i].toString());
        }
    }
}
