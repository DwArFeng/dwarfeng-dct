# FlatData 机制

## 概述

`FlatData`（扁平数据）是 `dwarfeng-dct` 项目中的核心数据结构，用于表示将复杂数据对象转换为文本格式后的数据。
它是数据编码处理过程中的重要中间格式，特别适用于消息队列传输和数据库存储场景。

扁平数据包含三个核心属性：点位主键、数据值和发生时间。
数据值采用 `prefix:flat_value` 格式，其中前缀标识数据类型，扁平值部分包含实际的文本数据。

## 核心概念

### 什么是扁平数据

扁平数据是指将 `Data.getValue()` 中的数据转换为文本后的数据。扁平数据包含以下三个核心属性：

- **点位主键** (`pointKey`): 标识数据所属的点位。
- **数据值** (`value`): 数据的文本表示，格式为 `prefix:flat_value`。
- **发生时间** (`happenedDate`): 数据发生的时间戳。

### 数据值格式

扁平数据的值采用特定的格式：`prefix:flat_value`。

- **前缀部分** (`prefix`): 第一个冒号之前的部分，标识数据类型。
- **扁平值部分** (`flat_value`): 第一个冒号之后的部分，实际的文本数据。

例如：

- `integer:123` - 整数类型，值为 123。
- `string:hello` - 字符串类型，值为 hello。
- `date:724608000000` - 日期类型，值为时间戳。

## 类结构

```java
import com.dwarfeng.subgrade.stack.bean.dto.Dto;
import com.dwarfeng.subgrade.stack.bean.key.LongIdKey;

@SuppressWarnings("serial")
public class FlatData implements Dto {
    private LongIdKey pointKey;     // 点位主键。
    private String value;           // 扁平化的数据值。
    private Date happenedDate;      // 发生时间。
}
```

## 实际应用

### 消息队列传输

在消息队列传输场景中，`FlatData` 提供了标准化的数据格式。发送方将数据编码为文本格式，接收方解码后进行处理：

发送方代码示例：

```java
public void sendData(Data originalData) {
    // 编码数据。
    String encodedData = dataCodingHandler.encode(originalData);
    // 发送消息。
    messageQueue.send(encodedData);
}
```

接收方代码示例：

```java
public void onMessageReceived(String message) {
    // 接收方：解码数据。
    Data dctData = dataCodingHandler.decode(message);
    // 假设 RecordInfo 是另一个系统使用的数据结构。
    RecordInfo recordInfo = new RecordInfo(
            dctData.getPointKey(),
            dctData.getValue(),
            dctData.getHappenedDate()
    );
    // 消费 recordInfo 进行后续处理。
    recordInfoConsumer.consume(recordInfo);
}
```

### 数据存储

在需要将数据存储到文本格式的存储系统中时，`FlatData` 提供了标准化的格式：

```java
FlatData flatData = new FlatData(
        new LongIdKey(12450L),
        "integer:42",
        new Date()
);
```

## 编解码机制

### 配置示例

通过 Spring 配置类可以轻松配置扁平数据编解码器：

```java
import com.dwarfeng.dct.handler.DataCodingHandler;
import com.dwarfeng.dct.handler.DataCodingHandlerImpl;
import com.dwarfeng.dct.handler.FlatDataCodec;
import com.dwarfeng.dct.handler.ValueCodingHandler;
import com.dwarfeng.dct.handler.fdc.FastJsonFlatDataCodec;
import com.dwarfeng.dct.struct.DataCodingConfig;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class CustomConfiguration {

    @Bean
    public FlatDataCodec flatDataCodec() {
        return new FastJsonFlatDataCodec();
    }

    // 在其它配置中定义 valueCodingHandler。
    @SuppressWarnings("SpringJavaInjectionPointsAutowiringInspection")
    @Bean
    public DataCodingHandler dataCodingHandler(
            FlatDataCodec flatDataCodec,
            ValueCodingHandler valueCodingHandler
    ) {
        DataCodingConfig config = new DataCodingConfig.Builder()
                .setFlatDataCodec(flatDataCodec)
                .setValueCodingHandler(valueCodingHandler)
                .build();
        return new DataCodingHandlerImpl(config);
    }
}
```

## 参考

- [GeneralData 机制](./GeneralDataMechanism.md) - 通用数据机制，介绍 GeneralData 的数据结构、支持的数据类型和值编解码机制。
