package ru.otus;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class Main {
    private static final Logger logger = LoggerFactory.getLogger(Main.class);
    private static final int MAX_VALUE = 10;

    public static void main(String[] args) {
        Main calculator = new Main();
        new Thread(() -> calculator.calc()).start();
        new Thread(() -> calculator.calc()).start();
    }

    private void calc() {
        int value_ = 1;
        while (value_ != MAX_VALUE) {
            value_ = increment(value_);
        }
        while (value_ != 0) {
            value_ = decrement(value_);
        }
    }
    private synchronized int increment(int value) {
        logger.info(String.valueOf(value));
        value += 1;
        notifyAll();
        try {
            wait();
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }
        return value;
    }

    private synchronized int decrement(int value) {
        logger.info(String.valueOf(value));
        value -= 1;
        notifyAll();
        try {
            wait();
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }
        return value;
    }

    private static void sleep() {
        try {
            Thread.sleep(100);
        } catch (InterruptedException e) {
            logger.error(e.getMessage());
            Thread.currentThread().interrupt();
        }
    }


}