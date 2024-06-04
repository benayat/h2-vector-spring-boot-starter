package io.github.benayat.inmemoryh2vectorspringbootstarter.converter;

import jakarta.persistence.AttributeConverter;

import java.nio.ByteBuffer;

public class FloatArrayToByteArrayConverter implements AttributeConverter<float[], byte[]> {
    @Override
    public byte[] convertToDatabaseColumn(float[] attribute) {
        ByteBuffer buffer = ByteBuffer.allocate(attribute.length * Float.BYTES);
        buffer.asFloatBuffer().put(attribute);
        return buffer.array();
    }

    @Override
    public float[] convertToEntityAttribute(byte[] dbData) {
        float[] result = new float[dbData.length / Float.BYTES];
        ByteBuffer.wrap(dbData).asFloatBuffer().get(result, 0, result.length);
        return result;
    }
}
