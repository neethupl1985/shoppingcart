package com.disco.shoppingCart.utils;

import org.slf4j.MDC;

import java.util.Optional;
import java.util.TreeMap;
import org.apache.commons.lang3.StringUtils;

public class MdcUtil {
    public static String getOrDefault(String key, String defaultValue) {
        return Optional.ofNullable(MDC.get(key)).filter(StringUtils::isNotBlank).orElse(defaultValue);
    }

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
