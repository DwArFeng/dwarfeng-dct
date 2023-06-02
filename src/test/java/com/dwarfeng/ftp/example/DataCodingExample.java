package com.dwarfeng.ftp.example;

import com.dwarfeng.dct.bean.dto.GeneralData;
import com.dwarfeng.dct.handler.DataCodingHandler;
import com.dwarfeng.subgrade.stack.bean.key.LongIdKey;
import org.springframework.context.support.ClassPathXmlApplicationContext;

import java.math.BigDecimal;
import java.math.BigInteger;
import java.util.*;
import java.util.stream.Collectors;

/**
 * 数据编码器的使用示例。
 *
 * @author DwArFeng
 * @since 1.0.0
 */
public final class DataCodingExample {

    @SuppressWarnings("SpellCheckingInspection")
    public static void main(String[] args) throws Exception {
        ClassPathXmlApplicationContext ctx = new ClassPathXmlApplicationContext(
                "classpath:spring/application-context*.xml"
        );
        ctx.registerShutdownHook();
        ctx.start();

        DataCodingHandler dataCodingHandler = ctx.getBean(DataCodingHandler.class);

        Scanner scanner = new Scanner(System.in);

        // 显示欢迎信息。
        System.out.println("开发者您好!");
        System.out.println("这是一个示例, 用于演示 dwarfeng-dct 数据编码器的使用");
        System.out.println("该示例将会演示如何使用数据编码器");
        System.out.print("按回车键继续...");
        scanner.nextLine();

        // 1. 演示数据编码过程。
        System.out.println();
        System.out.println("1. 演示数据编码过程...");
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
        List<GeneralData> generalDatas = objects.stream().map(
                obj -> new GeneralData(new LongIdKey(12450L), obj, new Date(724608000000L))
        ).collect(Collectors.toList());
        for (GeneralData generalData : generalDatas) {
            System.out.println(generalData + " -> " + dataCodingHandler.encode(generalData));
        }
        System.out.println();
        System.out.print("按回车键继续...");
        scanner.nextLine();

        // 2. 演示数据解码过程。
        System.out.println();
        System.out.println("2. 演示数据解码过程...");
        System.out.println("接下来程序将会生成几个文本，并将其解码成对象");
        System.out.println();
        List<String> strings = Arrays.asList(
                "{\"point_key\":{\"long_id\":12450},\"value\":\"integer:12\",\"happened_date\":724608000000}",
                "{\"point_key\":{\"long_id\":12450},\"value\":\"long:34\",\"happened_date\":724608000000}",
                "{\"point_key\":{\"long_id\":12450},\"value\":\"float:56.78\",\"happened_date\":724608000000}",
                "{\"point_key\":{\"long_id\":12450},\"value\":\"double:90.12\",\"happened_date\":724608000000}",
                "{\"point_key\":{\"long_id\":12450},\"value\":\"string:foobar\",\"happened_date\":724608000000}",
                "{\"point_key\":{\"long_id\":12450},\"value\":\"date:724608000000\",\"happened_date\":724608000000}",
                "{\"point_key\":{\"long_id\":12450},\"value\":\"byte_array:AQIDBAU=\",\"happened_date\":724608000000}",
                "{\"point_key\":{\"long_id\":12450},\"value\":\"big_integer:12450\",\"happened_date\":724608000000}",
                "{\"point_key\":{\"long_id\":12450},\"value\":\"big_decimal:3.14159\",\"happened_date\":724608000000}",
                "{\"point_key\":{\"long_id\":12450},\"value\":\"serializable:rO0ABXNyABNqYXZhLnV0aWwuQXJyYXlMaXN0eIH" +
                        "SHZnHYZ0DAAFJAARzaXpleHAAAAAFdwQAAAAFc3IAEWphdmEubGFuZy5JbnRlZ2VyEuKgpPeBhzgCAAFJAAV2YWx1ZX" +
                        "hyABBqYXZhLmxhbmcuTnVtYmVyhqyVHQuU4IsCAAB4cAAAAAFzcQB+AAIAAAACc3EAfgACAAAAA3NxAH4AAgAAAARzc" +
                        "QB+AAIAAAAFeA==\",\"happened_date\":724608000000}"
        );
        for (String string : strings) {
            System.out.println(string + " -> " + dataCodingHandler.decode(string));
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
