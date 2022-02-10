package proxy;

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
        private final CatsCreator myClass;

        DemoInvocationHandler(CatsCreatorImpl myClass) {
            this.myClass = myClass;
        }

        @Override
        public Object invoke(Object proxy, Method method, Object[] args) throws Throwable {

            System.out.println("invoking method:" + method);
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
