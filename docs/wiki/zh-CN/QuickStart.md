# Quick Start - 快速开始

本文档帮助您用最少的步骤体验 `dwarfeng-dct` 的数据编码与解码能力。

## 确认环境

- JDK 1.8 或更高版本。
- Maven 3.x。

如果您的项目无法直接从中央仓库解析本项目依赖，请先参阅 [Install by Source Code](./InstallBySourceCode.md) 安装本项目及其依赖。

## 引入依赖

在项目的 `pom.xml` 中添加如下依赖：

```xml
<dependency>
    <groupId>com.dwarfeng</groupId>
    <artifactId>dwarfeng-dct</artifactId>
    <version>${dwarfeng-dct.version}</version>
</dependency>
```

## 最小化 Spring 配置

最简单的使用方式是扫描 `com.dwarfeng.dct.configuration` 包。该配置会注册默认的 `ValueCodingHandler`、
`DataCodingHandler` 以及内置的值编解码器和 `FastJsonFlatDataCodec`。

```xml
<?xml version="1.0" encoding="UTF-8"?>
<!--suppress SpringFacetInspection -->
<beans
        xmlns:context="http://www.springframework.org/schema/context"
        xmlns:xsi="http://www.w3.org/2001/XMLSchema-instance"
        xmlns="http://www.springframework.org/schema/beans"
        xsi:schemaLocation="http://www.springframework.org/schema/beans
        http://www.springframework.org/schema/beans/spring-beans.xsd
        http://www.springframework.org/schema/context
        http://www.springframework.org/schema/context/spring-context.xsd"
>

    <context:component-scan base-package="com.dwarfeng.dct.configuration"/>
</beans>
```

## 最小示例

完成 Spring 配置后，即可获取 `DataCodingHandler` 对象进行编码与解码。

```java
import com.dwarfeng.dct.bean.dto.GeneralData;
import com.dwarfeng.dct.handler.DataCodingHandler;
import com.dwarfeng.dct.struct.Data;
import com.dwarfeng.subgrade.stack.bean.key.LongIdKey;
import org.springframework.context.support.ClassPathXmlApplicationContext;

import java.util.Date;

@SuppressWarnings("UnnecessaryModifier")
public class QuickStartExample {

    public static void main(String[] args) throws Exception {
        ClassPathXmlApplicationContext ctx = new ClassPathXmlApplicationContext(
                "classpath:spring/application-context*.xml"
        );
        ctx.registerShutdownHook();
        ctx.start();

        DataCodingHandler dataCodingHandler = ctx.getBean(DataCodingHandler.class);

        // 构造一条原始数据。
        GeneralData origin = new GeneralData(new LongIdKey(12450L), 42, new Date());

        // 编码为文本。
        String encoded = dataCodingHandler.encode(origin);
        System.out.println("编码结果: " + encoded);

        // 解码为数据对象。
        Data decoded = dataCodingHandler.decode(encoded);
        System.out.println("解码结果: " + decoded);

        ctx.stop();
        ctx.close();
    }
}
```

运行后，您会看到类似如下风格的文本：

```text
{"point_key":{"long_id":12450},"value":"integer:42","happened_date":...,"happened_date_nano_offset":0}
```

这说明 `dwarfeng-dct` 已经完成了如下工作：

1. 将原始值 `42` 编码为带类型前缀的扁平值 `integer:42`。
2. 将点位、扁平值、发生时间与毫秒内纳秒偏移一起封装为最终文本。
3. 在解码时恢复出原始的数据对象及其值类型，并保留高精度时间语义。

兼容性提示：

- 对于历史报文，若缺少 `happened_date_nano_offset` 字段，解码时会按默认值 `0` 处理。

## 运行项目示例

如果您想直接体验项目中自带的演示程序，可以运行：

- `com.dwarfeng.dct.example.DataCodingExample` - 数据编码器使用示例。
- `com.dwarfeng.dct.example.ValueCodingExample` - 值编码器使用示例。

项目示例位于 `src/test`，会加载 `classpath:spring/application-context*.xml` 对应的测试 Spring 配置。

## 下一步

- 参阅 [FlatData Mechanism](./FlatDataMechanism.md) 了解扁平数据的结构与语义。
- 参阅 [GeneralData Mechanism](./GeneralDataMechanism.md) 了解通用数据的组织方式。
- 如需扩展值类型支持，可参阅 [Extend ValueCodec](./ExtendValueCodec.md)。
