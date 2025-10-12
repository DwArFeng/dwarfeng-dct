# dwarfeng-dct

Dwarfeng（赵扶风）的 DCT 服务，基于 `subgrade` 项目，提供了数据编码与传输的功能。

---

## DCT 能够做什么？

DCT 是数据编码与传输（Data Coding and Transmission）的缩写。总体来说，DCT 服务提供了一种风格统一，易于阅读，
适用于多种数据类型的数据编码与传输的数据处理方式。这种数据处理方式通过 `subgrade` 项目的处理器对外提供。

您可以在以下场景中使用 `dwarfeng-dct` 项目：

1. 在设备侧采集多种类型的数据，将数据编码后发送到消息队列，使用时从消息队列中获取数据，解码后进行处理，且不丢失数据类型信息。
2. 采集的数据类型不固定，使用数据库存储数据，且不丢失数据类型信息。
3. 编码后的数据相对可读，不像 Base64 编码那样难以阅读，且数据编码后长度相对较短。

## 文档

该项目的文档位于 [docs](./docs) 目录下，包括：

### wiki

wiki 为项目的开发人员为本项目编写的详细文档，包含不同语言的版本，主要入口为：

1. [简介](docs/wiki/zh-CN/Introduction.md) - 镜像的 `README.md`，与本文件内容基本相同。
2. [目录](docs/wiki/zh-CN/Contents.md) - 文档目录。

## 关于数据

`dwarfeng-dct` 项目中的数据指的是现实中设备运行过程中产生的数据，例如：

- 计算机的 CPU 温度、使用率等。
- 电梯的运行状态、故障信息等。
- 机床的能耗、运行状态等、报警文本等。

数据具有如下的特点:

1. 每个数据都有一个数据值，数据值的类型可以是任意的，如 `boolean`、`int`、`double`、`String` 等，数据值可以是 `null`。
2. 每个数据都有自己的发生时间。
3. 每个数据都属于一个数据点位，数据点位的类型是 `LongIdKey`，每一个数据点位都有一个固定的意义。

以下是一个示例，某设备采集了一台计算机一小时内的开机情况和 CPU 使用率（为了简化，数据每 10 分钟记录一次）：

| 数据点       | 发生时间  | 值     |
|-----------|-------|-------|
| 1（是否开机）   | 00:00 | false |
| 2（CPU使用率） | 00:00 | null  |
| 1（是否开机）   | 00:10 | true  |
| 2（CPU使用率） | 00:10 | 0.10  |
| 1（是否开机）   | 00:20 | true  |
| 2（CPU使用率） | 00:20 | 0.95  |
| 1（是否开机）   | 00:30 | true  |
| 2（CPU使用率） | 00:30 | 0.70  |
| 1（是否开机）   | 00:40 | true  |
| 2（CPU使用率） | 00:40 | 0.50  |
| 1（是否开机）   | 00:50 | false |
| 2（CPU使用率） | 00:50 | null  |

通过更多更密集的数据记录，可以更加精确的描述设备的运行情况。

设备采集的这些数据，可以通过 `dwarfeng-dct` 项目进行编码，编码后的数据可以存储到数据库中，也可以通过消息队列发送到其他地方。
使用 `dwarfeng-dct` 编码后的数据，解码后可以还原为原始数据，值的类型信息不会丢失。

## 特性

1. Subgrade 架构支持。
2. 数据采用统一接口定义，支持不同实现。
3. 支持编码/解码常见的基本类型。
4. 编码/解码基于 `FastJson`，编码后的数据相对可读，且编码/解码速度快。
5. 所有接口均有默认实现，使用简单，也可以自定义实现。

运行 `src/test` 下的示例以观察全部特性。

| 示例类名               | 说明         |
|--------------------|------------|
| DataCodingExample  | 数据编码器的使用示例 |
| ValueCodingExample | 值编码器的使用示例  |

## 值的类型支持

`dwarfeng-dct` 项目支持的值的类型如下：

| 类型名          | 前缀名          | 优先级 | 值编解码器类型                | 说明                       |
|--------------|--------------|-----|------------------------|--------------------------|
| BigDecimal   | big_decimal  | 正常  | BigDecimalValueCodec   |                          |
| BigInteger   | big_integer  | 正常  | BigIntegerValueCodec   |                          |
| Boolean      | boolean      | 正常  | BooleanValueCodec      |                          |
| byte[]       | byte         | 正常  | ByteValueCodec         | 不支持 Byte[]，使用 Base64 编码  |
| Byte         | byte         | 正常  | ByteValueCodec         |                          |
| Character    | character    | 正常  | CalendarValueCodec     |                          |
| Date         | date         | 正常  | CalendarValueCodec     | 将 Date 序列化为时间戳           |
| Double       | double       | 正常  | DoubleValueCodec       |                          |
| Float        | float        | 正常  | FloatValueCodec        |                          |
| Integer      | integer      | 正常  | IntegerValueCodec      |                          |
| Long         | long         | 正常  | LongValueCodec         |                          |
| Short        | short        | 正常  | ShortValueCodec        |                          |
| String       | string       | 正常  | StringValueCodec       |                          |
| Serializable | serializable | 正常  | SerializableValueCodec | 几乎支持任何可序列化的对象，但不可读，应最后考虑 |

## 安装说明

1. 下载源码。

   - 使用 git 进行源码下载。
      ```
      git clone git@github.com:DwArFeng/dwarfeng-ftp.git
      ```

   - 对于中国用户，可以使用 gitee 进行高速下载。
      ```
      git clone git@gitee.com:dwarfeng/dwarfeng-ftp.git
      ```

2. 项目安装。

   进入项目根目录，执行 maven 命令
   ```
   mvn clean source:jar install
   ```

3. 项目引入。

   在项目的 pom.xml 中添加如下依赖：
   ```xml
   <dependency>
       <groupId>com.dwarfeng</groupId>
       <artifactId>dwarfeng-dct</artifactId>
       <version>${dwarfeng-dct.version}</version>
   </dependency>
   ```

4. enjoy it.

---

## 如何使用

1. 运行 `src/test` 下的 `Example` 以观察全部特性。
2. 观察项目结构，将其中的配置运用到其它的 subgrade 项目中。

### 简单配置

加载 `com.dwarfeng.dct.config.SimpleDctConfig` 类，即可简单地获得单例模式的 `DataCodingHandler` 和 `ValueCodingHandler`。
在项目的 `application-context-scan.xml` 中追加 `com.dwarfeng.dct.configuration` 包中全部 bean 的扫描，示例如下:

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

    <!-- 扫描 configuration 包中的全部 bean。 -->
    <context:component-scan base-package="com.dwarfeng.dct.configuration"/>
</beans>
```

或者只扫描 `com.dwarfeng.dct.configuration` 包中的 `SimpleConfiguration`，示例如下:

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

    <!-- 扫描 configuration 包中的 SimpleConfiguration -->
    <context:component-scan base-package="com.dwarfeng.dct.configuration" use-default-filters="false">
        <context:include-filter
                type="assignable"
                expression="com.dwarfeng.dct.configuration.SimpleConfiguration"
        />
    </context:component-scan>
</beans>
```

### 自定义配置

自定义配置较为灵活，可以在项目中的 `bean-definition.xml` 中追加配置，也可以自定义配置类。
自定义配置适用于以下场景：

1. 非单例模式的 `DataCodingHandler` 和 `ValueCodingHandler`。
2. 需要自定义 `FlatDataCodec` 和 `ValueCodec` 的实现。

由于自定义配置较为灵活，因此在此不做过多介绍，请开发者自行编写配置。

需要注意的是：`DataCodingHandler` 和 `ValueCodingHandler` 在使用之前需要调用 `init()` 方法进行初始化。

---

## 集成/二次开发

### 说明

`dwarfeng-dct` 项目支持通过集成/二次开发的方式扩展其功能，包括：

1. 自定义扁平数据编解码器，将 `Data` 序列化为 `JSON` 以外的其它格式。
2. 自定义值编解码器，扩展 `Data` 的值的支持类型。

开发人员只需要在项目中引用 `dwarfeng-dct` 的依赖，即可进行集成/二次开发，无需额外的配置。

### 自定义扁平数据编解码器

#### 开发

`dwarfeng-dct` 项目中的 `FlatDataCodec` 接口定义了扁平数据编解码器的基本功能，开发人员可以通过实现该接口，
自定义扁平数据编解码器。扩展的编解码器可以将 `Data` 序列化为 `JSON` 以外的其它格式。

建议在实现 `FlatDataCodec` 接口的同时，继承 `AbstractFlatDataCodec` 类，该类对通用的业务逻辑进行了封装，如异常处理等。
继承 `AbstractFlatDataCodec` 类可以使开发人员更加专注于编解码业务的实现。

```java
import com.dwarfeng.dct.handler.fdc.AbstractFlatDataCodec;

@SuppressWarnings("RedundantThrows")
public class CustomFlatDataCodec extends AbstractFlatDataCodec {

    @Override
    protected String doEncode(FlatData target) throws Exception {
        // 实现编码逻辑，而不需要关注异常处理。
        return xxx;
    }

    @Override
    protected FlatData doDecode(String text) throws Exception {
        // 实现解码逻辑，而不需要关注异常处理。
        return xxx;
    }
}
```

#### 使用

在构造 `DataCodingHandlerImpl` 时，将自定义的 `FlatDataCodec` 作为 `DataCodingConfig` 中的配置项传入即可，使用
`DataCodingConfig.Builder` 可以使这一过程更加简单。

```java
import com.dwarfeng.dct.handler.DataCodingHandler;
import com.dwarfeng.dct.handler.DataCodingHandlerImpl;
import com.dwarfeng.dct.handler.ValueCodingHandler;
import com.dwarfeng.dct.struct.DataCodingConfig;
import org.springframework.context.annotation.Configuration;

@SuppressWarnings({"SpringFacetCodeInspection", "RedundantSuppression"})
@Configuration
public class CustomConfiguration {

    @Bean
    public DataCodingHandler dataCodingHandler(ValueCodingHandler valueCodingHandler) {
        // 开发人员自己实现的 FlatDataCodec。
        CustomFlatDataCodec customFlatDataCodec = new CustomFlatDataCodec();
        DataCodingConfig config = new DataCodingConfig.Builder()
                .setFlatDataCodec(customFlatDataCodec)
                .setValueCodingHandler(valueCodingHandler)
                .build();
        return new DataCodingHandlerImpl(config);
    }
}
```

### 自定义值编解码器

#### 开发

`dwarfeng-dct` 项目中的 `ValueCodec` 接口定义了值编解码器的基本功能，开发人员可以通过实现该接口，
自定义值编解码器。扩展的编解码器可以扩展 `Data` 的值的支持类型。

建议在实现 `ValueCodec` 接口的同时，继承 `AbstractValueCodec` 类，该类对通用的业务逻辑进行了封装，如异常处理等。
继承 `AbstractValueCodec` 类可以使开发人员更加专注于编解码业务的实现。

```java
import com.dwarfeng.dct.handler.vc.AbstractValueCodec;

import javax.annotation.Nonnull;

@SuppressWarnings("RedundantThrows")
public class CustomValueCodec extends AbstractValueCodec {

    @Nonnull
    @Override
    protected String doEncode(@Nonnull Object target) throws Exception {
        // 实现解码逻辑，而不需要关注异常处理。
        return xxx;
    }

    @Nonnull
    @Override
    protected Object doDecode(@Nonnull String text) throws Exception {
        // 实现解码逻辑，而不需要关注异常处理。
        return xxx;
    }
}
```

#### 使用

在构造 `ValueCodingHandlerImpl` 时，将自定义的 `ValueCodec` 作为 `ValueCodingConfig` 中的配置项传入即可，使用
`ValueCodingConfig.Builder` 可以使这一过程更加简单。

```java
import com.dwarfeng.dct.handler.ValueCodingHandler;
import com.dwarfeng.dct.handler.ValueCodingHandlerImpl;
import com.dwarfeng.dct.struct.ValueCodingConfig;
import org.springframework.context.annotation.Configuration;

@SuppressWarnings({"SpringFacetCodeInspection", "RedundantSuppression"})
@Configuration
public class CustomConfiguration {

    @Bean
    public ValueCodingHandler valueCodingHandler() {
        // 开发人员自己实现的 ValueCodec。
        CustomValueCodec customValueCodec = new CustomValueCodec();
        ValueCodingConfig config = new ValueCodingConfig.Builder()
                .addCodec(someValueCodec1)
                .addCodec(someValueCodec2)
                .addCodec(someValueCodec3)
                .addCodec(customValueCodec)
                .build();
        return new ValueCodingHandlerImpl(config);
    }
}
```
