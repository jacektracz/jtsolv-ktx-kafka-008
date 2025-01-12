package com.github.jtsolvtransactions.model;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

import org.apache.kafka.common.serialization.Deserializer;
import org.apache.kafka.common.serialization.Serde;
import org.apache.kafka.common.serialization.Serializer;

import java.io.IOException;

public class JTSolvJsonSerde<T> implements Serde<T> {

    public static final ObjectMapper OBJECT_MAPPER = new ObjectMapper();
    private final Class<T> type;

    public JTSolvJsonSerde(Class<T> type) {
        this.type = type;
    }

    @Override
    public Serializer<T> serializer() {
        return (topic, data) -> serialize(data);
    }


    private byte[] serialize(T data) {
        try {
            return OBJECT_MAPPER.writeValueAsBytes(data);
        } catch (JsonProcessingException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public Deserializer<T> deserializer() {
        return (topic, bytes) -> deserialize(bytes);
    }

    private T deserialize(byte[] bytes) {
        try {
            return OBJECT_MAPPER.readValue(bytes, type);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
}
