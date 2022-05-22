package ru.otus.services.processors;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import ru.otus.lib.SensorDataBufferedWriter;
import ru.otus.api.SensorDataProcessor;
import ru.otus.api.model.SensorData;

import java.util.*;


// Этот класс нужно реализовать
public class SensorDataProcessorBuffered implements SensorDataProcessor {
    private static final Logger log = LoggerFactory.getLogger(SensorDataProcessorBuffered.class);
    private final int bufferSize;
    private final SensorDataBufferedWriter writer;
    private List<SensorData> dataBuffer;

    public SensorDataProcessorBuffered(int bufferSize, SensorDataBufferedWriter writer) {
        this.bufferSize = bufferSize;
        this.writer = writer;
        this.dataBuffer = Collections.synchronizedList(new ArrayList<>());
    }

    @Override
    public void process(SensorData data) {
        dataBuffer.add(data);
        if (dataBuffer.size() >= bufferSize) {
            flush();
        }
    }

    public void flush() {
        try {
            List<SensorData> bufferedData = Collections.synchronizedList(new ArrayList<>());
            for (int i = 0; i < dataBuffer.size(); i++) {
                bufferedData.add(dataBuffer.get(i));
            }
            if (dataBuffer.size() == 0){
                return;
            }
            dataBuffer = Collections.synchronizedList(new ArrayList<>());
            bufferedData.sort(Comparator.comparing(SensorData::getMeasurementTime));

            writer.writeBufferedData(bufferedData);

        } catch (Exception e) {
            log.error("Ошибка в процессе записи буфера", e);
        }
    }

    @Override
    public void onProcessingEnd() {
        flush();
    }
}
