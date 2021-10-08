package com.disco.shoppingcart.utils;

import org.apache.commons.lang3.StringUtils;
import org.slf4j.MDC;

import java.util.Optional;
import java.util.TreeMap;

public class MdcUtil {

    public static void put(String key, String value) {
        Optional.ofNullable(value)
                .filter((val) -> !StringUtils.isEmpty(val))
                .filter((val) -> !val.equals("null"))
                .ifPresent((val) -> MDC.put(key, val));
    }

    public static void clear() {
        MDC.clear();
    }

    public static Object toJson() {
        return ObjectToJson.toJson(new TreeMap<>(MDC.getCopyOfContextMap()), false);
    }
}
