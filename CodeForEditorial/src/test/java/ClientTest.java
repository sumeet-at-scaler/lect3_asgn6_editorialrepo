import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.io.ByteArrayOutputStream;
import java.io.PrintStream;
import java.lang.reflect.*;
import java.util.*;

import static org.junit.jupiter.api.Assertions.*;

public class ClientTest {

    @Test
    public void testDisplayMethodExists() {
        Method[] methods = Client.class.getDeclaredMethods();
        for (Method method : methods) {
            if (method.getName().equals("display")) {
                return;
            }
        }
        fail("display method not found in Client class");
    }

    @Test
    public void testDisplayMethodIsStatic() {
        Method[] methods = Client.class.getDeclaredMethods();
        for (Method method : methods) {
            if (method.getName().equals("display")) {
                assertTrue(Modifier.isStatic(method.getModifiers()), "display method should be static");
                return;
            }
        }
        fail("display method not found in Client class");
    }

    @Test
    public void testDisplayMethodReturnType() {
        Method[] methods = Client.class.getDeclaredMethods();
        for (Method method : methods) {
            if (method.getName().equals("display")) {
                assertEquals(void.class, method.getReturnType(), "display method's return type should be void");
                return;
            }
        }
        fail("display method not found in Client class");
    }

    @Test
    public void testDisplayMethodParameterType() {
        Method[] methods = Client.class.getDeclaredMethods();
        for (Method method : methods) {
            if (method.getName().equals("display")) {
                Class<?>[] parameterTypes = method.getParameterTypes();
                assertEquals(1, parameterTypes.length, "display method should take one parameter");
                assertTrue(Iterable.class.isAssignableFrom(parameterTypes[0]), "display method parameter should be of appropriate type that allows it to loop over data");
                return;
            }
        }
        fail("display method not found in Client class");
    }

    private Method getDisplayMethod(){
        Method[] methods = Client.class.getDeclaredMethods();
        for (Method method : methods) {
            if (method.getName().equals("display")) {
                assertTrue(Modifier.isStatic(method.getModifiers()), "display method should be static");
                return method;
            }
        }
        return null;
    }

    @Test
    public void testDisplayList() throws Exception {
        Method displayMethod = getDisplayMethod();
        if(displayMethod == null){
            fail("display method not found in Client class");
        }

        ByteArrayOutputStream outContent = new ByteArrayOutputStream();
        System.setOut(new PrintStream(outContent));

        List<String> list = new ArrayList<>();
        list.add("apple");
        list.add("banana");
        list.add("cherry");

        displayMethod.invoke(null, list);

        String expectedOutput = "apple\nbanana\ncherry\n";
        assertEquals(expectedOutput, outContent.toString());
    }

    @Test
    public void testDisplaySet() throws Exception{
        Method displayMethod = getDisplayMethod();
        if(displayMethod == null){
            fail("display method not found in Client class");
        }

        ByteArrayOutputStream outContent = new ByteArrayOutputStream();
        System.setOut(new PrintStream(outContent));

        Set<Integer> set = new HashSet<>();
        set.add(1);
        set.add(2);
        set.add(3);

        displayMethod.invoke(null, set);

        String expectedOutput = "1\n2\n3\n";
        assertEquals(expectedOutput, outContent.toString());
    }

    @Test
    public void testDisplayCustomIterable() throws Exception {
        Method displayMethod = getDisplayMethod();
        if(displayMethod == null){
            fail("display method not found in Client class");
        }

        ByteArrayOutputStream outContent = new ByteArrayOutputStream();
        System.setOut(new PrintStream(outContent));

        CustomIterable<String> iterable = new CustomIterable<>();
        iterable.add("cat");
        iterable.add("dog");

        displayMethod.invoke(null, iterable);

        String expectedOutput = "cat\ndog\n";
        assertEquals(expectedOutput, outContent.toString());
    }

    private static class CustomIterable<T> implements Iterable<T> {
        private List<T> list = new ArrayList<>();

        public void add(T item) {
            list.add(item);
        }

        @Override
        public Iterator<T> iterator() {
            return list.iterator();
        }
    }

}
