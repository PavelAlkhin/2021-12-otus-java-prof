package ru.otus.dataprocessor;

import ru.otus.model.Measurement;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class ProcessorAggregator implements Processor {

    @Override
    public Map<String, Double> process(List<Measurement> data) {
        //группирует выходящий список по name, при этом суммирует поля value
        Map<String, Double> map = new HashMap<>();
        data.forEach(d->{
            Double value = map.get(d.getName());
            if (value == null) {
                map.put(d.getName(), d.getValue());
            }else {
                value += d.getValue();
                map.put(d.getName(), value);
            }
        });
        return map;
    }
}
