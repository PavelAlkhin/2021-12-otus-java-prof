package ru.otus.processor;

import ru.otus.model.Message;
import ru.otus.processor.homework.DateTimeProvider;

public class LoggerProcessor implements Processor {

    private final Processor processor;
    private final DateTimeProvider dateTimeProvider;

    public LoggerProcessor(Processor processor, DateTimeProvider dateTimeProvider) {
        this.processor = processor;
        this.dateTimeProvider = dateTimeProvider;
    }

    @Override
    public Message process(Message message) {
        System.out.println("log processing message:" + message);
        return processor.process(message);
    }

    @Override
    public Message changePlace11to12(Message message) {
        return null;
    }

    @Override
    public Message evenSecondToThrow(Message message) {
        return null;
    }
}
