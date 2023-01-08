package com.drosa.heytrade.configuration;

import java.io.IOException;

import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.databind.SerializerProvider;
import com.fasterxml.jackson.databind.ser.std.StdSerializer;
import org.springframework.data.domain.PageImpl;

public class PageSerializer extends StdSerializer<PageImpl> {

  public PageSerializer() {
    super(PageImpl.class);
  }

  @Override
  public void serialize(PageImpl value, JsonGenerator gen, SerializerProvider provider) throws IOException {
    gen.writeStartObject();
    gen.writeNumberField("number", value.getNumber());
    gen.writeNumberField("numberOfElements", value.getNumberOfElements());
    gen.writeNumberField("totalElements", value.getTotalElements());
    gen.writeNumberField("totalPages", value.getTotalPages());
    gen.writeNumberField("size", value.getSize());
    gen.writeFieldName("content");
    provider.defaultSerializeValue(value.getContent(), gen);
    gen.writeEndObject();
  }
}
