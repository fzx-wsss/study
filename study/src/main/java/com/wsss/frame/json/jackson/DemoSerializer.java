package com.wsss.frame.json.jackson;

import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonSerializer;
import com.fasterxml.jackson.databind.SerializerProvider;

import java.io.IOException;

public class DemoSerializer extends JsonSerializer<Object> {
    @Override
    public void serialize(Object demo, JsonGenerator jsonGenerator, SerializerProvider serializerProvider) throws IOException, JsonProcessingException {
        System.out.println("serialize");
        jsonGenerator.writeStartObject();
        jsonGenerator.writeStringField("test","222");
        jsonGenerator.writeEndObject();
    }
}
