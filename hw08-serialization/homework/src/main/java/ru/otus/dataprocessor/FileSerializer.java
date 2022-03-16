package ru.otus.dataprocessor;

import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.util.Map;
import java.util.TreeMap;

public class FileSerializer implements Serializer {

    private final String fileName;

    public FileSerializer(String fileName) {
        this.fileName = fileName;
    }

    @Override
    public void serialize(Map<String, Double> data) {
        //формирует результирующий json и сохраняет его в файл
        Map<String, Double> sortedMap = new TreeMap<>(data);

        try (var bufferedWriter = new BufferedWriter(new FileWriter(fileName))) {
            var context = new StringBuilder();
            context.append("{");
            int size = sortedMap.size();
            int count = 1;
            for(var keyValue : sortedMap.entrySet()) {
                context.append(String.format("\"%s\":%s",keyValue.getKey(),keyValue.getValue()));
                if (count != size) {
                    context.append(",");
                }
                count ++;
            }
            context.append("}");
            bufferedWriter.write(context.toString());
        } catch (IOException e) {
            throw new FileProcessException("ERROR with file write: " + e);
        }
    }
}
