package ru.otus.listener.homework;

import ru.otus.listener.Listener;
import ru.otus.model.Message;
import ru.otus.processor.homework.DateTimeProvider;

import java.util.ArrayDeque;
import java.util.Deque;
import java.util.Optional;

public class HistoryListener implements Listener, HistoryReader {

    private final Deque<Message> stack = new ArrayDeque<>();
    private DateTimeProvider dateTimeProvider;

    @Override
    public void onUpdated(Message msg) {
//        throw new UnsupportedOperationException();
        stack.push(msg);
        dateTimeProvider.getDate();
    }

    @Override
    public Optional<Message> findMessageById(long id) {
        throw new UnsupportedOperationException();
    }
}
