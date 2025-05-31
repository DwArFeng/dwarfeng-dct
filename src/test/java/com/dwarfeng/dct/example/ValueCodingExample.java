package com.dwarfeng.dct.example;

import com.dwarfeng.dct.handler.ValueCodingHandler;
import org.springframework.context.support.ClassPathXmlApplicationContext;

import java.math.BigDecimal;
import java.math.BigInteger;
import java.util.*;

/**
 * 值编码器的使用示例。
 *
 * @author DwArFeng
 * @since 1.0.0
 */
public final class ValueCodingExample {

    // 为了保证代码的可读性，此处代码不做简化。
    @SuppressWarnings({"SpellCheckingInspection", "ExtractMethodRecommender"})
    public static void main(String[] args) throws Exception {
        ClassPathXmlApplicationContext ctx = new ClassPathXmlApplicationContext(
                "classpath:spring/application-context*.xml"
        );
        ctx.registerShutdownHook();
        ctx.start();

        ValueCodingHandler valueCodingHandler = ctx.getBean(ValueCodingHandler.class);

        Scanner scanner = new Scanner(System.in);

        // 显示欢迎信息。
        System.out.println("开发者您好!");
        System.out.println("这是一个示例, 用于演示 dwarfeng-dct 值编码器的使用");
        System.out.println("该示例将会演示如何使用值编码器");
        System.out.print("按回车键继续...");
        scanner.nextLine();

        // 1. 演示值编码过程。
        System.out.println();
        System.out.println("1. 演示值编码过程...");
        System.out.println("接下来程序将会生成几个对象，并将其编码成文本");
        System.out.println();
        ArrayList<Integer> exampleArrayList = new ArrayList<>();
        exampleArrayList.add(1);
        exampleArrayList.add(2);
        exampleArrayList.add(3);
        exampleArrayList.add(4);
        exampleArrayList.add(5);
        List<Object> objects = Arrays.asList(
                12,
                34L,
                56.78f,
                90.12d,
                "foobar",
                new Date(724608000000L),
                new byte[]{1, 2, 3, 4, 5},
                new BigInteger("12450"),
                new BigDecimal("3.14159"),
                exampleArrayList
        );
        for (Object object : objects) {
            System.out.println(object.toString() + " -> " + valueCodingHandler.encode(object));
        }
        System.out.println();
        System.out.print("按回车键继续...");
        scanner.nextLine();

        // 2. 演示值解码过程。
        System.out.println();
        System.out.println("2. 演示值解码过程...");
        System.out.println("接下来程序将会生成几个文本，并将其解码成对象");
        System.out.println();
        List<String> strings = Arrays.asList(
                "integer:12",
                "long:34",
                "float:56.78",
                "double:90.12",
                "string:foobar",
                "date:724608000000",
                "byte_array:AQIDBAU=",
                "big_integer:12450",
                "big_decimal:3.14159",
                "serializable:rO0ABXNyABNqYXZhLnV0aWwuQXJyYXlMaXN0eIHSHZnHYZ0DAAFJAARzaXpleHAAAAAFdwQAAAAFc3IAEWphdm" +
                        "EubGFuZy5JbnRlZ2VyEuKgpPeBhzgCAAFJAAV2YWx1ZXhyABBqYXZhLmxhbmcuTnVtYmVyhqyVHQuU4IsCAAB4cAAAA" +
                        "AFzcQB+AAIAAAACc3EAfgACAAAAA3NxAH4AAgAAAARzcQB+AAIAAAAFeA=="
        );
        for (String string : strings) {
            System.out.println(string + " -> " + valueCodingHandler.decode(string));
        }
        System.out.println();
        System.out.print("按回车键继续...");
        scanner.nextLine();

        // 感谢使用。
        System.out.println("示例演示完毕, 感谢您测试与使用!");

        ctx.stop();
        ctx.close();
        System.exit(0);
    }
}
