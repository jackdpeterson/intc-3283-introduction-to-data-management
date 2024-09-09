package edu.northwestu.intc3283.datasourcestarter.config.jdbc.converter;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.core.convert.converter.Converter;
import org.springframework.data.convert.ReadingConverter;
import org.springframework.data.convert.WritingConverter;
import java.io.IOException;
import java.util.Map;

public class MapJsonConverters {

    private static final ObjectMapper objectMapper = new ObjectMapper();

    @ReadingConverter
    public static class MapReadingConverter implements Converter<String, Map<String, String>> {
        @Override
        public Map<String, String> convert(String source) {
            try {
                return objectMapper.readValue(source, new TypeReference<Map<String, String>>() {});
            } catch (IOException e) {
                throw new IllegalArgumentException("Error reading JSON string to Map", e);
            }
        }
    }

    @WritingConverter
    public static class MapWritingConverter implements Converter<Map<String, String>, String> {
        @Override
        public String convert(Map<String, String> source) {
            try {
                return objectMapper.writeValueAsString(source);
            } catch (JsonProcessingException e) {
                throw new IllegalArgumentException("Error writing Map to JSON string", e);
            }
        }
    }
}
