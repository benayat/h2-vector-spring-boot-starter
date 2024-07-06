package io.github.benayat.inmemoryh2vectorspringbootstarter.functions;

import io.github.benayat.inmemoryh2vectorspringbootstarter.model.VectorWrapper;

public class VectorFunctions {
    public static double cosineSimilarity(byte[] vec1, byte[] vec2) {
        VectorWrapper vector1 = VectorWrapper.fromByteArray(vec1);
        VectorWrapper vector2 = VectorWrapper.fromByteArray(vec2);
        return vector1.cosineSimilarity(vector2);
    }

    public static double euclideanSimilarity(byte[] vec1, byte[] vec2) {
        VectorWrapper vector1 = VectorWrapper.fromByteArray(vec1);
        VectorWrapper vector2 = VectorWrapper.fromByteArray(vec2);
        return vector1.euclideanSimilarity(vector2);
    }
}
