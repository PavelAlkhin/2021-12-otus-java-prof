package ru.otus;

import ru.otus.launcher.TestLauncher;
import ru.otus.test.CatsCreatorTest;

import java.lang.reflect.InvocationTargetException;

public class Main {
    public static void main(String[] args) throws InvocationTargetException, IllegalAccessException {
        TestLauncher.startTest(CatsCreatorTest.class);
    }
}
