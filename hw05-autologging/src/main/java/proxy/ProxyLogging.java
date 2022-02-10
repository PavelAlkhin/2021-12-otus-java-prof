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

        DemoInvocationHandler(CatsCreatorImpl myClass) {
            this.myClass = myClass;
        }

        @Override
        public Object invoke(Object proxy, Method method, Object[] args) throws Throwable {
            Class aClass = myClass.getClass();
            Method[] declaredMethods = aClass.getDeclaredMethods();
            boolean logging = false;
            if (args == null) {
                int length = 0;
            } else {
                int length = args.length;
            }
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
            if (logging) {
                System.out.println("executed method:" + method.getName() + ", param:" + args.length);
            }
            return method.invoke(myClass, args);
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
