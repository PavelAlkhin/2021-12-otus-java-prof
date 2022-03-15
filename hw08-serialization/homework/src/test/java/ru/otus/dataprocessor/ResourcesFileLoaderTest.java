package ru.otus.dataprocessor;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Test;
import ru.otus.model.Measurement;
import ru.otus.model.MyMeasurement;

import java.util.List;

class ResourcesFileLoaderTest {

    private final String FILENAME = "test/inputDataTest.json";
    private final ObjectMapper mapper = new ObjectMapper();

    @Test
    void load() throws JsonProcessingException {
//        MyMeasurement mis = mapper.reader().forType(MyMeasurement.class).readValue("{\"name\":\"val1\",\"value\":0.0}");
//        System.out.println(mis);


        var loader = new ResourcesFileLoader(FILENAME);
        List<Measurement> load = loader.load();
    }
}