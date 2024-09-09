package edu.northwestu.intc3283.datasourcestarter.config.jdbc;

import edu.northwestu.intc3283.datasourcestarter.config.jdbc.converter.ListLikeMapJsonConverters;
import edu.northwestu.intc3283.datasourcestarter.config.jdbc.converter.MapJsonConverters;
import edu.northwestu.intc3283.datasourcestarter.config.jdbc.converter.ObjectToUuidConverter;
import edu.northwestu.intc3283.datasourcestarter.config.jdbc.converter.UuidToBytesConverter;
import jakarta.validation.constraints.NotNull;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.jdbc.repository.config.AbstractJdbcConfiguration;
import org.springframework.data.jdbc.repository.config.EnableJdbcAuditing;
import org.springframework.data.jdbc.repository.config.EnableJdbcRepositories;
import java.util.Arrays;
import java.util.List;

@Configuration
@EnableJdbcRepositories("edu.northwestu.intc3283.datasourcestarter.repository")
@EnableJdbcAuditing
public class CustomJdbcConfiguration extends AbstractJdbcConfiguration {

    @Override
    protected @NotNull List<?> userConverters() {
        return Arrays.asList(
                new MapJsonConverters.MapReadingConverter(),
                new MapJsonConverters.MapWritingConverter(),
                new ListLikeMapJsonConverters.MapReadingConverter(),
                new ListLikeMapJsonConverters.MapWritingConverter(),
                new UuidToBytesConverter(),
                new ObjectToUuidConverter()
        );
    }
}