package com.konka.kkstar.util;

import java.util.Collection;
import java.util.Iterator;

/**
 * Created by Zhou Weilin on 2018-5-18.
 */

public class CommUtils {
    public static String join(Collection strings, String sep) {
        return join(strings.iterator(), sep);
    }

    public static String join(Iterator strings, String sep) {
        if(!strings.hasNext()) {
            return "";
        } else {
            String start = strings.next().toString();
            if(!strings.hasNext()) {
                return start;
            } else {
                StringBuilder sb = (new StringBuilder(64)).append(start);

                while(strings.hasNext()) {
                    sb.append(sep);
                    sb.append(strings.next());
                }

                return sb.toString();
            }
        }
    }
}
