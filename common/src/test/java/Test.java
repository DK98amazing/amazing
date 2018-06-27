import com.google.common.collect.Lists;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.function.Consumer;
import java.util.function.Function;
import java.util.function.Predicate;
import java.util.function.Supplier;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

//java8函数式编程
public class Test {
    public static void main(String args[]) {
//        Function<String, Integer> function = (String str) -> {return str.length();};
//        Supplier<String> supplier = () -> {return "sd";};
//        Predicate<String> predicate = (String str) -> {return str.length() == 5;};
//        Predicate<String> predicate1 = (String str) -> {return str instanceof String;};
//        Consumer<String> consumer = (String str) -> {System.out.println(str.length());};
//        System.out.println(function.apply("sadadada"));
//        System.out.println(supplier.get());
//        System.out.println(predicate.test("12345"));
//        System.out.println(predicate.and(predicate1).test("1234"));
//        consumer.accept("ssssss");
//        Collection<String> collection = new ArrayList<>();
//        collection.add("123");collection.add("12345");
//        collection.removeIf(predicate);
//        System.out.println(collection.toArray().length);
//
//        String pattern = "^vc4=1|4|16$";
//        Pattern r = Pattern.compile(pattern);
//        Matcher m = r.matcher("vc4=1");
//        System.out.println(m.matches());
//
//        String content = "vc4=16\\vc12=1";
//        String content1 = "vc4=16";
//        String content2 = "vc12=9";
//        String pattern4 = "vc4=(1|4|16|64)\\\\vc12=(([1-5][0-9]?)|(6[0-3]?)|7|8|9)";
//        String pattern1 = "vc4=(1|4|16|64)";
//        String pattern2 = "vc12=(([1-5][0-9]?)|(6[0-3]?)|7|8|9)";
//        boolean isMatch = Pattern.matches(pattern4, content);
//        System.out.println(isMatch);
//
//        List<Integer> list = Lists.newArrayList();
//        list.add(1);list.add(1);
//        list.remove(new Integer(1));
//        System.out.println(list.size());
//
//        System.out.println(0xe0040000);
//        StringBuffer stringBuffer = new StringBuffer();
//        stringBuffer.append("1").append("2");
//        System.out.println(stringBuffer.toString());
        try {
            throwEx();
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
        System.out.println("2");


    }

    private static void throwEx() throws Exception {
        System.out.println("1");

        int ret  =1;
        if (ret == 1) {
            throw new Exception("error");
        }


    }

}
