# GeneralData 机制

## 概述

`GeneralData`（通用数据）是 `dwarfeng-dct` 项目中 `Data`
接口的通用实现类，用于表示包含点位主键、数据值和发生时间的完整数据对象。它是项目中最基础的数据结构，为数据编码处理提供了标准化的数据模型。

通用数据是 `dwarfeng-dct` 的核心概念，每个数据都包含三个基本要素：点位主键、数据值和发生时间。通过值编解码器机制，支持多种数据类型的编码和解码处理。

## 核心概念

### 什么是通用数据

通用数据是 `dwarfeng-dct` 的核心概念，每个数据都包含三个基本要素：

- **点位主键** (`pointKey`): 用于标识数据所属的点位，不能为 `null`。
- **数据值** (`value`): 数据的实际值，可以为 `null`，支持任意类型。
- **发生时间** (`happenedDate`): 数据发生的时间，不能为 `null`。

### 数据接口实现

`GeneralData` 实现了 `Data` 接口，提供了标准的数据访问方法：

```java
public interface Data { 
    @Nonnull
    LongIdKey getPointKey();

    @Nullable
    Object getValue();

    @Nonnull
    Date getHappenedDate();
}
```

## 类结构

```java
public class GeneralData implements Dto, Data { 
    private LongIdKey pointKey;    // 点位主键（非空）。
    private Object value;          // 数据值（可空）。
    private Date happenedDate;     // 发生时间（非空）。
}
```

## 支持的数据类型

`GeneralData` 的 `value` 字段支持多种数据类型，通过值编解码器（ValueCodec）进行处理：

### 常用数据类型

- **基本类型**: `Integer`, `Long`, `Float`, `Double`, `Boolean`, `Character`, `Byte`。
- **字符串类型**: `String`。
- **日期时间类型**: `Date`。
- **数值类型**: `BigInteger`, `BigDecimal`。
- **数组类型**: `byte[]`。
- **复杂对象**: 任何实现 `Serializable` 接口的对象。

### 编码格式示例

- `12` → `"integer:12"`。
- `"hello"` → `"string:hello"`。
- `new Date(724608000000L)` → `"date:724608000000"`。
- `new BigDecimal("3.14159")` → `"big_decimal:3.14159"`。

## 实际应用

### 数据编码处理

在实际应用中，`GeneralData` 通过数据编码处理器进行编码和解码：

```java
// 创建通用数据。
GeneralData data = new GeneralData(
        new LongIdKey(12450L),
        42,
        new Date()
);

// 编码为文本。
String encodedText = dataCodingHandler.encode(data);

// 从文本解码。
Data decodedData = dataCodingHandler.decode(encodedText);
```

### 消息队列传输

在消息队列传输场景中，数据经过编码后传输，接收方解码处理：

```java
// 接收方：解码数据
Data dctData = dataCodingHandler.decode(message);
// RecordInfo 是另一个系统使用的数据结构。
RecordInfo recordInfo = new RecordInfo(
        dctData.getPointKey(), 
        dctData.getValue(), 
        dctData.getHappenedDate()
);
```

## 值编解码机制

### 配置示例

通过 Spring 配置类可以轻松配置值编解码器：

```java

@Bean
public List<ValueCodec> valueCodecs() {
    List<ValueCodec> codecs = new ArrayList<>();
    codecs.add(new BooleanValueCodec());
    codecs.add(new IntegerValueCodec());
    codecs.add(new LongValueCodec());
    codecs.add(new DoubleValueCodec());
    codecs.add(new StringValueCodec());
    return codecs;
}

@Bean
public ValueCodingHandler valueCodingHandler(List<ValueCodec> valueCodecs) {
    ValueCodingConfig config = new ValueCodingConfig.Builder()
            .addCodecs(valueCodecs)
            .build();
    return new ValueCodingHandlerImpl(config);
}
```

## 参考

- [FlatData 机制](./FlatDataMechanism.md) - 扁平数据机制，介绍 FlatData 的核心概念、使用场景和编解码机制。
