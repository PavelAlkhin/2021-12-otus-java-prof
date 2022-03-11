package ru.otus.processor;

import ru.otus.model.Message;

import java.time.LocalDateTime;

public class ProcessorEvenSecondToThrow implements Processor {
    @Override
    public Message process(Message message) {
        if (LocalDateTime.now().getSecond() % 2 == 0){
            throw new RuntimeException("Even second Exception");
        }
        return message;
    }
}
