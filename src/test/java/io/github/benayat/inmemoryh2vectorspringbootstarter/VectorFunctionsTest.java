package io.github.benayat.inmemoryh2vectorspringbootstarter;


import io.github.benayat.inmemoryh2vectorspringbootstarter.converter.FloatArrayToByteArrayConverter;
import io.github.benayat.inmemoryh2vectorspringbootstarter.functions.VectorFunctions;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class VectorFunctionsTest {

    FloatArrayToByteArrayConverter floatArrayToByteArrayConverter = new FloatArrayToByteArrayConverter();
    float[] vec1Float = new float[] {1.326f, 2.232f, 3.837f, 4.123f, 5.123f, 6.123f, 7.123f, 8.123f};
    float[] vec2Float = new float[] {5.2345f, 6.3244f, 4.834f, 2.6543f, 1.432f, 9.323f, 3.321f, 19.323f};
    byte[] vec1 = floatArrayToByteArrayConverter.convertToDatabaseColumn(vec1Float);
    byte[] vec2 = floatArrayToByteArrayConverter.convertToDatabaseColumn(vec2Float);
    @Test
    public void testCosineSimilarity() {
        double vec1ToVec1Result = VectorFunctions.cosineSimilarity(vec1, vec1);
        assertEquals(1.0, vec1ToVec1Result, 0.0001);
        double vec1ToVec2Result = VectorFunctions.cosineSimilarity(vec1, vec2);
        assertEquals(0.8358, vec1ToVec2Result, 0.0001);
        double vec2ToVec2Result = VectorFunctions.cosineSimilarity(vec2, vec2);
        assertEquals(1.0, vec2ToVec2Result, 0.0001);
    }
    @Test
    public void testEuclideanSimilarity() {

        double vec1ToVec1Result = VectorFunctions.euclideanSimilarity(vec1, vec1);
        assertEquals(1.0, vec1ToVec1Result, 0.0001);
        double vec1ToVec2Result = VectorFunctions.euclideanSimilarity(vec1, vec2);
        assertEquals(0.0662, vec1ToVec2Result, 0.0001);
        double vec2ToVec2Result = VectorFunctions.euclideanSimilarity(vec2, vec2);
        assertEquals(1.0, vec2ToVec2Result, 0.0001);
    }

}
