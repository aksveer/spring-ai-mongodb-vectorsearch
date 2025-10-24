package com.example.vectorsearch.utils;

import java.util.ArrayList;
import java.util.List;


public class EmbeddingUtils {
    public static List<Double> getDoubleList(float[] embedding) {
        List<Double> doubles = new ArrayList<>(embedding.length);
        for (float f : embedding) {
            doubles.add((double) f);
        }
        return doubles;
    }
}
