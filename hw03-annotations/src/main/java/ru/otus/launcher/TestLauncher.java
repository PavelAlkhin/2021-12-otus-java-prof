package ru.otus.launcher;

import ru.otus.annotation.After;
import ru.otus.annotation.Befor;
import ru.otus.annotation.Test;
import ru.otus.test.CatsCreatorTest;

import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.List;

public class TestLauncher {
    public static void startTest(Class<?> clazz){

        Method[] methods = clazz.getDeclaredMethods();
        Method after = null;
        Method befor = null;
        List<Method> test = new ArrayList<>();
        for (Method m: methods) {
            if (m.isAnnotationPresent(After.class)){
                after = m;
            }
            if (m.isAnnotationPresent(Befor.class)){
                befor = m;
            }
            if (m.isAnnotationPresent(Test.class)){
                test.add(m);
            }
        }

        StringBuilder res = new StringBuilder();
        int total = test.size();
        int success = 0;
        for (Method m : test){
            Object obj = null;
            try {
                Class example = Class.forName(clazz.getName());
                obj = example.getConstructor().newInstance();
            }catch (Exception exc){
                System.out.println("ERROR: "+exc);
                continue;
            }
            if (befor != null){
                invokeMethod(befor, res, obj);
            }
            success += invokeMethod(m, res, obj);
            if (after != null){
                invokeMethod(after, res, obj);
            }
        }

        System.out.println(res);
        System.out.println("Total tests - " + total + ", \nsuccecced - " + success + ", \nwith exception - " + (total-success));

    }

    private static int invokeMethod(Method m, StringBuilder res, Object obj) {
        try{
            m.invoke(obj);
            res.append("Success execute " + m.getName() + "\n");
            return 1;
        }catch (Exception ex){
            res.append("Could not execute " + m.getName() + "\n");
            return 0;
        }
    }
}
