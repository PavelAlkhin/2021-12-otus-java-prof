package ru.otus.processor;

import ru.otus.model.Message;

import java.time.LocalDateTime;

public class ProcessorEvenSecondToThrow implements Processor {

    private LocalDateTime localDateTime;

    @Override
    public Message process(Message message) {
        if (localDateTime == null){
            localDateTime = LocalDateTime.now();
        }
        if (localDateTime.getSecond() % 2 == 0){
            throw new RuntimeException("Even second Exception");
        }
        return message;
    }

    public void setLocalDateTime(LocalDateTime localDateTime) {
        this.localDateTime = localDateTime;
    }
}
