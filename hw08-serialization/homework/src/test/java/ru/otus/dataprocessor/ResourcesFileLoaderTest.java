package ru.otus.dataprocessor;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Test;
import ru.otus.model.Measurement;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

class ResourcesFileLoaderTest {

    private final String FILENAME = "test/inputDataTestOne.json";
    private final ObjectMapper mapper = new ObjectMapper();

    @Test
    void load() throws JsonProcessingException {
        var loader = new ResourcesFileLoader(FILENAME);
        List<Measurement> load = loader.load();
        assertThat(load.size()).isEqualTo(1);
    }
}