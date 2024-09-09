package edu.northwestu.intc3283.datasourcestarter.config.jdbc.converter;

import org.springframework.core.convert.converter.Converter;
import org.springframework.data.convert.ReadingConverter;
import java.util.UUID;

@ReadingConverter
public class ObjectToUuidConverter implements Converter<byte[], UUID> {
    @Override
    public UUID convert(byte[] source) {
        // Ensure that the byte array length is 16 (UUID size)
        if (source.length != 16) {
            throw new IllegalArgumentException("Invalid byte array length for UUID conversion");
        }

        // Construct UUID from byte array
        return new UUID(
                bytesToLong(source, 0),
                bytesToLong(source, 8)
        );
    }


    private long bytesToLong(byte[] bytes, int startIndex) {
        long value = 0;
        for (int i = startIndex; i < startIndex + 8; i++) {
            value = (value << 8) + (bytes[i] & 0xff);
        }
        return value;
    }
}
