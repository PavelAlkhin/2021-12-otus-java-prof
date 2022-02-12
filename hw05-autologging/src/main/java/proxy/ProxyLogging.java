package proxy;

import annotation.Log;
import service.CatsCreator;
import service.CatsCreatorImpl;

import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;
import java.lang.reflect.Proxy;

public class ProxyLogging {
    public static CatsCreator createMyClass() {
        InvocationHandler handler = new DemoInvocationHandler(new CatsCreatorImpl());
        return (CatsCreator) Proxy.newProxyInstance(ProxyLogging.class.getClassLoader(),
                new Class<?>[]{CatsCreator.class}, handler);
    }

    static class DemoInvocationHandler implements InvocationHandler {
        private final CatsCreatorImpl myClass;
        private final Method[] declaredMethods;

        DemoInvocationHandler(CatsCreatorImpl myClass) {
            this.myClass = myClass;
            this.declaredMethods = myClass.getClass().getDeclaredMethods();
        }

        @Override
        public Object invoke(Object proxy, Method method, Object[] args) throws Throwable {

            boolean logging = isLogging(method, args, false);

            if (logging) {
                System.out.println("executed method:" + method.getName() + ", param:" + args.length);
            }

            return method.invoke(myClass, args);
        }

        private boolean isLogging(Method method, Object[] args, boolean logging) {
            for (Method m : declaredMethods) {
                if (m.getName().equals(method.getName())) {
                    if (m.isAnnotationPresent(Log.class)) {

                        boolean ok = true;
                        for (int i = 0; i < args.length; i++) {
                            Object argObj = args[i];

                            Class<?> aClassImpl = argObj.getClass();
                            if (!compareMethodParam(m, i, aClassImpl)) {
                                ok = false;
                            }
                        }
                        if (ok) {
                            logging = true;
                        }
                    }
                }
            }
            return logging;
        }

        private boolean compareMethodParam(Method m, int i, Class<?> aClassImpl) {
            try {
                Class<?>[] aClassImplMethod = m.getParameterTypes();
                if (aClassImpl.getCanonicalName().equals(aClassImplMethod[i].getCanonicalName())) {
                    return true;
                }
            } catch (Exception e) {
                return false;
            }
            return false;
        }

        @Override
        public String toString() {
            return "DemoInvocationHandler{" +
                    "myClass=" + myClass +
                    '}';
        }
    }
}
