package edu.northwestu.intc3283.datasourcestarter.config.jdbc.converter;

import org.springframework.core.convert.converter.Converter;
import org.springframework.data.convert.WritingConverter;
import java.nio.ByteBuffer;
import java.util.UUID;

@WritingConverter
public class UuidToBytesConverter  implements Converter<UUID, byte[]> {
    @Override
    public byte[] convert(UUID source) {
        final var bb = ByteBuffer.wrap(new byte[16]);
        bb.putLong(source.getMostSignificantBits());
        bb.putLong(source.getLeastSignificantBits());
        return bb.array();
    }
}
