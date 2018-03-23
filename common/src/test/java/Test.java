import java.nio.file.attribute.UserDefinedFileAttributeView;
import java.util.ArrayList;
import java.util.Collection;
import java.util.function.Consumer;
import java.util.function.Function;
import java.util.function.Predicate;
import java.util.function.Supplier;

//java8函数式编程
public class Test {
    public static void main(String args[]) {
        Function<String, Integer> function = (String str) -> {return str.length();};
        Supplier<String> supplier = () -> {return "sd";};
        Predicate<String> predicate = (String str) -> {return str.length() == 5;};
        Predicate<String> predicate1 = (String str) -> {return str instanceof String;};
        Consumer<String> consumer = (String str) -> {System.out.println(str.length());};
        System.out.println(function.apply("sadadada"));
        System.out.println(supplier.get());
        System.out.println(predicate.test("12345"));
        System.out.println(predicate.and(predicate1).test("1234"));
        consumer.accept("ssssss");
        Collection<String> collection = new ArrayList<>();
        collection.add("123");collection.add("12345");
        collection.removeIf(predicate);
        System.out.println(collection.toArray().length);

    }

}