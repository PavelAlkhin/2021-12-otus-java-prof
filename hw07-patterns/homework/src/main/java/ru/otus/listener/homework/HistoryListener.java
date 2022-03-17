package ru.otus.listener.homework;

import ru.otus.listener.Listener;
import ru.otus.model.Message;
import ru.otus.model.ObjectForMessage;

import java.util.*;

public class HistoryListener implements Listener, HistoryReader {

    private HashMap<Long, Message> messages = new HashMap<>();

    @Override
    public void onUpdated(Message msg) {
        List<String> newData = new ArrayList<>();
        if(msg.getField13() != null) {
            for (String str : msg.getField13().getData()) {
                newData.add(str);
            }
        }
        ObjectForMessage obj = new ObjectForMessage();
        obj.setData(newData);

        var newMess = new Message.Builder(msg.getId())
                .field1(msg.getField1())
                .field2(msg.getField2())
                .field3(msg.getField3())
                .field4(msg.getField4())
                .field5(msg.getField5())
                .field6(msg.getField6())
                .field7(msg.getField7())
                .field8(msg.getField8())
                .field9(msg.getField9())
                .field10(msg.getField10())
                .field11(msg.getField11())
                .field12(msg.getField12())
                .field13(obj)
                .build();
        messages.put(msg.getId(), newMess);
    }

    @Override
    public Optional<Message> findMessageById(long id) {
        return Optional.of(messages.get(id));
    }
}
