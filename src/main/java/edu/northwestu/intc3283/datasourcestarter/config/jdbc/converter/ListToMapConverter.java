package edu.northwestu.intc3283.datasourcestarter.config.jdbc.converter;

import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

public class ListToMapConverter {
    public static Map<Long, String> convertListToMap(List<String> list) {
        return IntStream.range(0, list.size())
                .boxed()
                .collect(Collectors.toMap(
                        i -> (long) i + 1,  // Key: Long starting from 1
                        list::get           // Value: corresponding String from the list
                ));
    }

}
