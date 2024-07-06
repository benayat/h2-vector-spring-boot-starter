package io.github.benayat.inmemoryh2vectorspringbootstarter.model;

import lombok.AllArgsConstructor;
import lombok.Getter;

import java.util.HashMap;
import java.util.Map;

@AllArgsConstructor
@Getter
public enum Metric {

    COSINE_SIMILARITY("COSINE_SIMILARITY"),
    L2_SIMILARITY("L2_SIMILARITY");
    private final String value;
    private static final Map<String, Metric> lookup = new HashMap<>();

    static {
        for (Metric metric : Metric.values()) {
            lookup.put(metric.getValue(), metric);
        }
    }

    public static Metric get(String value) {
        return lookup.get(value.toUpperCase());
    }

}
