package ru.otus.processor;

import ru.otus.model.Message;

public class ProcessorChangeFiled11And12 implements Processor{

    @Override
    public Message process(Message message) {
        String field11 = message.getField11();
        message.toBuilder().field11(message.getField12()).build();
        message.toBuilder().field12(field11).build();
        return message;
    }
}
