package ru.otus.appcontainer;

import org.reflections.Reflections;
import org.reflections.scanners.Scanners;
import org.reflections.util.ClasspathHelper;
import org.reflections.util.ConfigurationBuilder;
import ru.otus.appcontainer.api.AppComponent;
import ru.otus.appcontainer.api.AppComponentsContainer;
import ru.otus.appcontainer.api.AppComponentsContainerConfig;
import ru.otus.exceptions.ReflectionException;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.*;

public class AppComponentsContainerImpl implements AppComponentsContainer {

    private final List<Object> appComponents = new ArrayList<>();
    private final Map<String, Object> appComponentsByName = new HashMap<>();
    private final TreeMap<Integer, List<Method>> sortNamesByOrder = new TreeMap<>();

    public AppComponentsContainerImpl(Class<?> initialConfigClass) {
        processConfig(initialConfigClass);
    }

    public AppComponentsContainerImpl(String packageName) {

        Reflections reflections = new Reflections(new ConfigurationBuilder()
                .setUrls(ClasspathHelper.forPackage(packageName))
                .setScanners(Scanners.TypesAnnotated));

        Set<Class<?>> typesAnnotatedWith = reflections.getTypesAnnotatedWith(AppComponentsContainerConfig.class);

        for (Class aClass : typesAnnotatedWith) {
            processConfig(aClass);
        }

    }

    private void processConfig(Class<?> configClass) {
        checkConfigClass(configClass);
        // You code here...
        Method[] declaredMethods = configClass.getDeclaredMethods();
        Object configObject;

        try {
            configObject = configClass.getDeclaredConstructor().newInstance();
        } catch (InstantiationException | IllegalAccessException | InvocationTargetException |
                 NoSuchMethodException e) {
            throw new ReflectionException("Cannot get Instance of Object " + configClass.getName() + e);
        }

        for (int i = 0; i < declaredMethods.length; i++) {
            var meth = declaredMethods[i];
            if (meth.isAnnotationPresent(AppComponent.class)) {

                AppComponent annotation = meth.getAnnotation(AppComponent.class);
                int order = annotation.order();

                List<Method> stringsFromMap = sortNamesByOrder.get(order);
                if (stringsFromMap == null) {
                    List<Method> methods = new ArrayList<>();
                    methods.add(meth);
                    sortNamesByOrder.put(order, methods);
                } else {
                    stringsFromMap.add(meth);
                }

            }
        }

        for (var map : sortNamesByOrder.entrySet()) {
            List<Method> listMethods = map.getValue();
            for (Method meth : listMethods) {

                try {
                    AppComponent annotation = meth.getAnnotation(AppComponent.class);
                    String name = annotation.name();

                    int paramCount = meth.getParameterCount();
                    Object[] args = new Object[paramCount];
                    if (paramCount > 0) {
                        args = new Object[paramCount];
                        Class<?>[] parameterTypes = meth.getParameterTypes();
                        int i = 0;
                        for (Class p : parameterTypes) {
                            String methName = findMethod(p);
                            Object o = appComponentsByName.get(methName);
                            args[i] = o;
                            i += 1;
                        }
                    }

                    Object invokeObject = invoke(configObject, meth, args);
                    appComponents.add(invokeObject);

                    appComponentsByName.put(name, invokeObject);

                } catch (Throwable e) {
                    throw new ReflectionException("ERROR " + e);
                }
            }
        }

    }

    private String findMethod(Class source) {
        for (var map : sortNamesByOrder.entrySet()) {
            var listMethods = map.getValue();
            for (Method method : listMethods) {
                Class<?> returnType = method.getReturnType();
                if (returnType == source) {
                    return method.getName();
                }
            }
        }
        throw new ReflectionException("Cannt find correct method by source " + source.getName());
    }

    public Object invoke(Object configObject, Method m, Object[] args) throws Throwable {
        if (m != null) {
            System.out.println("executed method:" + m.getName() + ", param:" + args.length);
            return m.invoke(configObject, args);
        }
        throw new ReflectionException("Cannt find invoke method " + m.getName());
    }


    private void checkConfigClass(Class<?> configClass) {
        if (!configClass.isAnnotationPresent(AppComponentsContainerConfig.class)) {
            throw new IllegalArgumentException(String.format("Given class is not config %s", configClass.getName()));
        }
    }

    @Override
    public <C> C getAppComponent(Class<C> componentClass) {
        for (var comp : appComponents) {
            Class<?> aClass = comp.getClass();
            String name = aClass.getName();

            String componentClassName = componentClass.getName();
            if (componentClassName.equals(name)) {
                return (C) comp;
            }
            Class<?>[] interfaces = aClass.getInterfaces();
            for (var i : interfaces) {
                String interfName = i.getName();
                if (componentClassName.equals(interfName)) {
                    return (C) comp;
                }
            }
        }
        throw new ReflectionException("Cannot find  Component in List" + componentClass.getName());
    }

    @Override
    public <C> C getAppComponent(String componentName) {
        Object o = appComponentsByName.get(componentName);
        return (C) o;
    }
}
