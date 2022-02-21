package proxy;

import annotation.Log;
import service.CatsCreator;
import service.CatsCreatorImpl;

import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;
import java.lang.reflect.Proxy;
import java.util.HashMap;
import java.util.Map;

public class ProxyLogging {
    public static CatsCreator createMyClass() {
        Class<?>[] classes = {CatsCreator.class};
        InvocationHandler handler = new DemoInvocationHandler(new CatsCreatorImpl(), classes[0]);
        return (CatsCreator) Proxy.newProxyInstance(ProxyLogging.class.getClassLoader(),
                classes, handler);
    }

    static class DemoInvocationHandler implements InvocationHandler {
        private CatsCreatorImpl myClass;
        private Map<String, Method> methods;

        DemoInvocationHandler(CatsCreatorImpl myClass, Class<?> classInterface) {
            this.myClass = myClass;
            this.methods = new HashMap<>();

            Method[] declaredMethods = myClass.getClass().getDeclaredMethods();
            Method[] intefMethods = classInterface.getDeclaredMethods();
            for (Method mImpl : declaredMethods) {
                if (mImpl.isAnnotationPresent(Log.class)) {

                    Class<?>[] mParameterTypes = mImpl.getParameterTypes();
                    int length = mParameterTypes.length;
                    boolean ok = true;
                    boolean isFoundMethod = false;

                    for (Method mIntrf : intefMethods) {

                        if (mIntrf.getName() == mImpl.getName()) {
                            Class[] intefParameterTypes = mIntrf.getParameterTypes();
                            for (int i = 0; i < length; i++) {
                                try {
                                    Class aClassImpl = mParameterTypes[i];
                                    Class aClassIntrf = intefParameterTypes[i];
                                    if (!aClassImpl.getCanonicalName().equals(aClassIntrf.getCanonicalName())) {
                                        ok = false;
                                        continue;
                                    }
                                    isFoundMethod = true;
                                } catch (Exception ex) {
                                    ok = false;
                                    continue;
                                }
                            }
                            if (isFoundMethod && ok) {
                                this.methods.put(generateClasssName(mIntrf), mIntrf);
                            }
                        }

                    }

                }
            }
        }

        private String generateClasssName(Method m) {
            StringBuilder stringBuilder = new StringBuilder();
            stringBuilder.append(m.getName());
            stringBuilder.append("_");
            for (Class cls : m.getParameterTypes()) {
                stringBuilder.append(cls.getCanonicalName());
                stringBuilder.append("_");
            }
            return stringBuilder.toString();
        }

        @Override
        public Object invoke(Object proxy, Method method, Object[] args) throws Throwable {

            Method m = methods.get(generateClasssName(method));
            if (m != null) {
                System.out.println("executed method:" + method.getName() + ", param:" + args.length);
                return m.invoke(myClass, args);
            }
            return method.invoke(myClass, args);
        }

        @Override
        public String toString() {
            return "DemoInvocationHandler{" +
                    "myClass=" + myClass +
                    '}';
        }
    }
}
