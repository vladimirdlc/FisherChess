package Game.util;


import java.util.Collection;
import java.util.Iterator;


/**
 * Generic Functions
 * @author Vladimir de la Cruz
 */
public class Functions {

    public static String join(Collection s, String delimiter) {
        StringBuffer buffer = new StringBuffer();
        Iterator iter = s.iterator();
        while (iter.hasNext()) {
            buffer.append(iter.next());
            if (iter.hasNext()) {
                buffer.append(delimiter);
            }
        }
        return buffer.toString();
    }

    public static String join(String[] s, String glue) {
        int k = s.length;
        if (k == 0) {
            return null;
        }
        StringBuilder out = new StringBuilder();
        out.append(s[0]);
        for (int x = 1; x < k; ++x) {
            out.append(glue).append(s[x]);
        }
        return out.toString();
    }
}
