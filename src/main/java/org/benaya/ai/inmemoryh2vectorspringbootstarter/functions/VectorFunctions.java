package org.benaya.ai.inmemoryh2vectorspringbootstarter.functions;

import org.benaya.ai.inmemoryh2vectorspringbootstarter.converter.FloatArrayToByteArrayConverter;
import org.benaya.ai.inmemoryh2vectorspringbootstarter.model.VectorWrapper;

public class VectorFunctions {
//    public static double cosineSimilarity(float[] vec1, float[] vec2) {
//        VectorWrapper vector1 = new VectorWrapper(vec1);
//        VectorWrapper vector2 = new VectorWrapper(vec2);
//        return vector1.cosineSimilarity(vector2);
//    }
//    public static double cosineSimilarity(String vec1, String vec2) {
//        VectorWrapper vector1 = VectorWrapper.fromJson(vec1);
//        VectorWrapper vector2 = VectorWrapper.fromJson(vec2);
//        return vector1.cosineSimilarity(vector2);
//    }
//    public static FloatArrayToByteArrayConverter converter = new FloatArrayToByteArrayConverter();
    public static double cosineSimilarity(byte[] vec1, byte[] vec2) {
        VectorWrapper vector1 = VectorWrapper.fromByteArray(vec1);
        VectorWrapper vector2 = VectorWrapper.fromByteArray(vec2);
        return vector1.cosineSimilarity(vector2);
    }

    public static double euclideanSimilarity(float[] vec1, float[] vec2) {
        VectorWrapper vector1 = new VectorWrapper(vec1);
        VectorWrapper vector2 = new VectorWrapper(vec2);
        return vector1.euclideanSimilarity(vector2);
    }


}
