package ru.otus.dataprocessor;

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
        this.fileName = fileName;
    }

    @Override
    public List<Measurement> load() {
        InputStream inputStreamObject = getClass().getClassLoader().getResourceAsStream(fileName);
        String content = getStringContent(inputStreamObject);
        return getMeasurements(content);
    }

    private List<Measurement> getMeasurements(String content) {
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

    private String getStringContent(InputStream inputStreamObject) {
        String content;
        try (BufferedReader streamReader = new BufferedReader(
                new InputStreamReader(inputStreamObject, "UTF-8"))) {

            StringBuilder responseStrBuilder = new StringBuilder();

            String inputStr;
            while ((inputStr = streamReader.readLine()) != null) {
                responseStrBuilder.append(inputStr);
            }
            content = responseStrBuilder.toString();

        } catch (UnsupportedEncodingException e) {
            throw new FileProcessException("ERROR with read file. " + e);
        } catch (IOException e) {
            throw new FileProcessException("ERROR with read file. " + e);
        }
        return content;
    }
}
