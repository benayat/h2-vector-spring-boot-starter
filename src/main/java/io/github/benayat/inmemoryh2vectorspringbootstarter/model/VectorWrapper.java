package io.github.benayat.inmemoryh2vectorspringbootstarter.model;

import com.fasterxml.jackson.databind.ObjectMapper;
import jdk.incubator.vector.FloatVector;
import jdk.incubator.vector.VectorOperators;
import jdk.incubator.vector.VectorSpecies;

import java.nio.ByteBuffer;


public record VectorWrapper(float[] data) {
    private static final VectorSpecies<Float> SPECIES = FloatVector.SPECIES_PREFERRED;
    private static final ObjectMapper objectMapper = new ObjectMapper();

    public VectorWrapper(float[] data) {
        this.data = data.clone();
    }

    public FloatVector getVector(float[] data) {
        return FloatVector.fromArray(SPECIES, data, 0);
    }

    public int getLength() {
        return data().length;
    }

    public VectorWrapper add(VectorWrapper other) {
        float[] result = new float[data.length];
        for (int i = 0; i <= SPECIES.loopBound(data.length); i += SPECIES.length()) {
            var m = SPECIES.indexInRange(i, data.length);
            var va = FloatVector.fromArray(SPECIES, data, i, m);
            var vb = FloatVector.fromArray(SPECIES, other.data, i, m);
            var vc = va.add(vb);
            vc.intoArray(result, i);
        }
        return new VectorWrapper(result);
    }

    public VectorWrapper mul(VectorWrapper other) {
        float[] result = new float[data.length];
        for (int i = 0; i <= SPECIES.loopBound(data.length); i += SPECIES.length()) {
            var m = SPECIES.indexInRange(i, data.length);
            var va = FloatVector.fromArray(SPECIES, data, i, m);
            var vb = FloatVector.fromArray(SPECIES, other.data, i, m);
            var vc = va.mul(vb);
            vc.intoArray(result, i);
        }
        return new VectorWrapper(result);
    }

    public float dotProduct(VectorWrapper other) {
        float dotProduct = 0.0f;
        for (int i = 0; i <= SPECIES.loopBound(getLength()); i += SPECIES.length()) {
            var m = SPECIES.indexInRange(i, getLength());
            var va = FloatVector.fromArray(SPECIES, data, i, m);
            var vb = FloatVector.fromArray(SPECIES, other.data, i, m);
            dotProduct += va.mul(vb).reduceLanes(VectorOperators.ADD);
        }
        return dotProduct;
    }

    public float l1Norm() {
        float l1Norm = 0.0f;
        for (int i = 0; i <= SPECIES.loopBound(getLength()); i += SPECIES.length()) {
            var m = SPECIES.indexInRange(i, getLength());
            var va = FloatVector.fromArray(SPECIES, data, i, m);
            l1Norm += va.abs().reduceLanes(VectorOperators.ADD);
        }
        return l1Norm;
    }

    @Override
    public float[] data() {
        return data.clone();
    }

    public float l2Norm() {
        float l2NormSum = 0.0f;
        int length = getLength();
        for (int i = 0; i <= SPECIES.loopBound(length); i += SPECIES.length()) {
            var m = SPECIES.indexInRange(i, length);
            var va = FloatVector.fromArray(SPECIES, data, i, m);
            l2NormSum += va.mul(va).reduceLanes(VectorOperators.ADD);
        }
        return (float) Math.sqrt(l2NormSum);
    }

    public float cosineSimilarity(VectorWrapper other) {
        float dotProduct = this.dotProduct(other);
        float normA = this.l2Norm();
        float normB = other.l2Norm();
        return dotProduct / (normA * normB);
    }

    public float euclideanDistance(VectorWrapper other) {
        float distance = 0.0f;
        for (int i = 0; i <= SPECIES.loopBound(getLength()); i += SPECIES.length()) {
            var m = SPECIES.indexInRange(i, data.length);
            var va = FloatVector.fromArray(SPECIES, data, i, m);
            var vb = FloatVector.fromArray(SPECIES, other.data, i, m);
            var diff = va.sub(vb);
            distance += diff.mul(diff).reduceLanes(VectorOperators.ADD);
        }
        return (float) Math.sqrt(distance);
    }

    public float euclideanSimilarity(VectorWrapper other) {
        float distance = this.euclideanDistance(other);
        return 1 / (1 + distance);
    }

    public String dataArrayToJson() {
        try {
            return objectMapper.writeValueAsString(data());
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    public static VectorWrapper fromJson(String json) {
        try {
            return new VectorWrapper(objectMapper.readValue(json, float[].class));
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    public static VectorWrapper fromByteArray(byte[] bytes) {
        float[] result = new float[bytes.length / Float.BYTES];
        ByteBuffer.wrap(bytes).asFloatBuffer().get(result, 0, result.length);
        return new VectorWrapper(result);
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder("[");
        for (int i = 0; i < data.length; i++) {
            sb.append(data[i]);
            if (i < data.length - 1) sb.append(", ");
        }
        sb.append("]");
        return sb.toString();
    }


}
