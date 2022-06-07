package ru.otus.services.processors;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import ru.otus.lib.SensorDataBufferedWriter;
import ru.otus.api.SensorDataProcessor;
import ru.otus.api.model.SensorData;

import java.util.*;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.PriorityBlockingQueue;


// Этот класс нужно реализовать
public class SensorDataProcessorBuffered implements SensorDataProcessor {
    private static final Logger log = LoggerFactory.getLogger(SensorDataProcessorBuffered.class);
    private final int bufferSize;
    private final SensorDataBufferedWriter writer;
    //    private List<SensorData> dataBuffer;
    private final PriorityBlockingQueue<SensorData> dataBuffer;

    public SensorDataProcessorBuffered(int bufferSize, SensorDataBufferedWriter writer) {
        this.bufferSize = bufferSize;
        this.writer = writer;
//        this.dataBuffer = Collections.synchronizedList(new ArrayList<>());
        this.dataBuffer = new PriorityBlockingQueue<>(
                bufferSize,
                Comparator.comparing(SensorData::getMeasurementTime)
        );
    }

    @Override
    public synchronized void process(SensorData data) {
        dataBuffer.add(data);
        if (dataBuffer.size() >= bufferSize) {
            flush();
        }
    }

    public synchronized void flush() {
        try {
//            List<SensorData> bufferedData = Collections.synchronizedList(new ArrayList<>());
//            for (int i = 0; i < dataBuffer.size(); i++) {
////                SensorData sensorData = dataBuffer.get(i);
////                bufferedData.add(sensorData);
////                dataBuffer.remove(sensorData);
//                bufferedData.add(dataBuffer.get(i));
//            }
//            if (dataBuffer.size() == 0){
//                return;
//            }
//            dataBuffer = Collections.synchronizedList(new ArrayList<>());
//            bufferedData.sort(Comparator.comparing(SensorData::getMeasurementTime));
//
//            writer.writeBufferedData(bufferedData);

//            synchronized (dataBuffer) {
//                List<SensorData> bufferedData = new ArrayList<>();
//                dataBuffer.drainTo(bufferedData);
//                if (!bufferedData.isEmpty()) {
//                    writer.writeBufferedData(bufferedData);
//                }
//            }
//            synchronized (dataBuffer) {
//                if (!dataBuffer.isEmpty()) {
//                    List<SensorData> sensorDataList = new ArrayList<>();
//                    dataBuffer.drainTo(sensorDataList);
//                    writer.writeBufferedData(sensorDataList);
//            }
//        }
            List<SensorData> bufferedData = new ArrayList<>();
            if (dataBuffer.drainTo(bufferedData) > 0) {
                writer.writeBufferedData(bufferedData);
            }
        } catch (Exception e) {
            log.error("Ошибка в процессе записи буфера", e);
        }
    }

    @Override
    public void onProcessingEnd() {
        flush();
    }
}
