package ru.otus.dataprocessor;

import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import ru.otus.model.Measurement;
import ru.otus.model.MyMeasurement;

import java.io.*;
import java.util.ArrayList;
import java.util.List;

public class ResourcesFileLoader implements Loader {

    private final ObjectMapper mapper;
    private final String fileName;

    public ResourcesFileLoader(String fileName) {
        this.mapper = new ObjectMapper();
//        mapper.configure(DeserializationFeature.ACCEPT_SINGLE_VALUE_AS_ARRAY, true);
//        mapper.configure(JsonParser.Feature.ALLOW_COMMENTS, true);
        this.fileName = fileName;
    }

    @Override
    public List<Measurement> load() {
        //читает файл, парсит и возвращает результат
        InputStream inputStreamObject = getClass()
                .getClassLoader().getResourceAsStream(fileName);

        String content = "";

        try (BufferedReader streamReader = new BufferedReader(
                new InputStreamReader(inputStreamObject, "UTF-8"))) {

            StringBuilder responseStrBuilder = new StringBuilder();

            String inputStr;
            while ((inputStr = streamReader.readLine()) != null) {
                responseStrBuilder.append(inputStr);
            }
            content = responseStrBuilder.toString();

        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }

        List<Measurement> measurements = new ArrayList<>();
        try {
            MyMeasurement[] dataLoaded = mapper.readValue(content, MyMeasurement[].class);
            for (MyMeasurement m : dataLoaded){
                measurements.add(new Measurement(m.getName(), m.getValue()));
            }
        } catch (IOException e) {
            throw new FileProcessException("ERROR with read file. " + e);
        }catch (Exception ex){
            throw new FileProcessException("ERROR with read file" + ex);
        }

        return measurements;
    }
}
