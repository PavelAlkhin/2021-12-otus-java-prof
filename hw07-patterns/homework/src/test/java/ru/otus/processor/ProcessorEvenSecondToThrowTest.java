package ru.otus.processor;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import ru.otus.model.Message;

import java.time.LocalDateTime;

import static org.junit.jupiter.api.Assertions.assertTrue;

class ProcessorEvenSecondToThrowTest {

    @Test
    @DisplayName("Testing do not throw even second")
    void processNotThrow() {
        ProcessorEvenSecondToThrow processor = new ProcessorEvenSecondToThrow();
        LocalDateTime notTrowDT = LocalDateTime.of(2022, 03, 14, 2, 3, 1);

        processor.setLocalDateTime(notTrowDT);
        var message = new Message.Builder(1L)
                .build();

        try{
            processor.process(message);
        } catch( RuntimeException ex){
            assertTrue(false);
        }

    }

    @Test
    @DisplayName("Testing throw even second")
    void processThrow() {
        ProcessorEvenSecondToThrow processor = new ProcessorEvenSecondToThrow();
        LocalDateTime notTrowDT = LocalDateTime.of(2022, 03, 14, 2, 3, 2);

        processor.setLocalDateTime(notTrowDT);
        var message = new Message.Builder(1L)
                .build();
        try{
            processor.process(message);
        } catch( RuntimeException ex){
            assertTrue(true);
        }
    }
}