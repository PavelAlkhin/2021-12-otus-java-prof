package ru.otus;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import ru.otus.filler.InitialDataFiller;


@SpringBootApplication
public class JDBCWebServer {
    public static void main(String[] args) {
        var context = SpringApplication.run(JDBCWebServer.class, args);

        context.getBean("initialDataFiller", InitialDataFiller.class).action();
    }
}
