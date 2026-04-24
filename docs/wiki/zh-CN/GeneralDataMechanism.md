# GeneralData 机制

## 概述

`GeneralData`（通用数据）是 `dwarfeng-dct` 项目中 `Data`
接口的通用实现类，用于表示包含点位主键、数据值和发生时间的完整数据对象。
它是项目中最基础的数据结构，为数据编码处理提供了标准化的数据模型。

通用数据是 `dwarfeng-dct` 的核心概念，每个数据都包含四个基本要素：点位主键、数据值、发生时间和毫秒内纳秒偏移。
通过值编解码器机制，支持多种数据类型的编码和解码处理。

## 核心概念

### 什么是通用数据

通用数据是 `dwarfeng-dct` 的核心概念，每个数据都包含四个基本要素：

- **点位主键** (`pointKey`): 用于标识数据所属的点位，不能为 `null`。
- **数据值** (`value`): 数据的实际值，可以为 `null`，支持任意类型。
- **发生时间** (`happenedDate`): 数据发生的时间，不能为 `null`。
- **毫秒内纳秒偏移** (`happenedDateNanoOffset`): 发生时间所在毫秒内的纳秒偏移，取值范围为 `0 ~ 999999`，默认值为 `0`。

### 数据接口实现

`GeneralData` 实现了 `Data` 接口，提供了标准的数据访问方法：

```java
import com.dwarfeng.subgrade.stack.bean.key.LongIdKey;

import javax.annotation.Nonnull;

public interface Data {

    @Nonnull
    LongIdKey getPointKey();

    @Nullable
    Object getValue();

    @Nonnull
    Date getHappenedDate();

    int getHappenedDateNanoOffset();
}
```

## 类结构

```java
import com.dwarfeng.subgrade.stack.bean.dto.Dto;
import com.dwarfeng.subgrade.stack.bean.key.LongIdKey;

@SuppressWarnings("serial")
public class GeneralData implements Dto, Data {
    private LongIdKey pointKey;    // 点位主键（非空）。
    private Object value;          // 数据值（可空）。
    private Date happenedDate;     // 发生时间（非空）。
    private int happenedDateNanoOffset; // 发生时间在毫秒内的纳秒偏移，默认值为 0。
}
```

## 时间精度模型

### 字段语义

`GeneralData` 使用以下组合表达高精度发生时间：

- `happenedDate`：毫秒级基准时间。
- `happenedDateNanoOffset`：该毫秒内的纳秒偏移量，范围为 `0 ~ 999999`。

完整瞬时时间由二者共同决定。推荐通过工具类对 `FlatData` 进行 `Instant` 操作。

## 数据工具类

### Instant 工厂构造

推荐优先使用 `GeneralDataUtil.newInstance(LongIdKey pointKey, Object value, Instant happenedInstant)` 创建对象，
避免手工分拆时间字段导致语义偏差。

```java
import com.dwarfeng.dct.bean.dto.GeneralData;
import com.dwarfeng.dct.util.GeneralDataUtil;
import com.dwarfeng.subgrade.stack.bean.key.LongIdKey;

import java.time.Instant;

public class Example {

    public GeneralData buildGeneralData() {
        LongIdKey pointKey = new LongIdKey(12450L);
        Object value = 42;
        Instant happenedInstant = Instant.parse("2026-04-24T10:30:12.123456789Z");

        return GeneralDataUtil.newInstance(pointKey, value, happenedInstant);
    }
}
```

### Instant 的 Getters 和 Setters

推荐统一通过 `GeneralDataUtil` 进行转换：

- `GeneralDataUtil.setHappenedInstant(...)`：根据 `Instant` 同步设置两个字段。
- `GeneralDataUtil.getHappenedInstant(...)`：由两个字段还原完整 `Instant`。

```java
import com.dwarfeng.dct.bean.dto.GeneralData;
import com.dwarfeng.dct.util.GeneralDataUtil;

import java.time.Instant;

public class Example {

    public Instant getHappenedInstant(GeneralData generalData) {
        return GeneralDataUtil.getHappenedInstant(generalData);
    }

    public void setHappenedInstant(GeneralData generalData, Instant happenedInstant) {
        GeneralDataUtil.setHappenedInstant(generalData, happenedInstant);
    }
}
```

## 数据排序

`CompareUtil` 为 `GeneralData` 提供统一排序语义，推荐优先复用以下比较器：

- `CompareUtil.GENERAL_DATA_DEFAULT_COMPARATOR`：默认比较，先按 `pointKey` 升序，
  再按发生 `Instant` 升序（由 `happenedDate + happenedDateNanoOffset` 组合计算）。
- `CompareUtil.GENERAL_DATA_HAPPENED_INSTANT_ASC_COMPARATOR`：仅按发生 `Instant` 升序，不考虑 `pointKey`。
- `CompareUtil.GENERAL_DATA_HAPPENED_INSTANT_DESC_COMPARATOR`：仅按发生 `Instant` 降序，不考虑 `pointKey`。

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
import com.dwarfeng.dct.bean.dto.GeneralData;
import com.dwarfeng.dct.struct.Data;
import com.dwarfeng.subgrade.stack.bean.key.LongIdKey;

public class Example {

    public void dataCodecExample() {
        // 创建通用数据。
        GeneralData data = new GeneralData(new LongIdKey(12450L), 42, new Date());

        // 编码为文本。
        String encodedText = dataCodingHandler.encode(data);
        System.out.println("Encoded Text: " + encodedText);

        // 从文本解码。
        Data decodedData = dataCodingHandler.decode(encodedText);
        System.out.println("Decoded Data: " + decodedData);
    }
}
```

### 消息队列传输

在消息队列传输场景中，数据经过编码后传输，接收方解码处理：

```java
import com.dwarfeng.dct.struct.Data;

public class Example {

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
}
```

## 值编解码机制

### 配置示例

通过 Spring 配置类可以轻松配置值编解码器：

```java
import com.dwarfeng.dct.handler.ValueCodec;
import com.dwarfeng.dct.handler.ValueCodingHandler;
import com.dwarfeng.dct.handler.ValueCodingHandlerImpl;
import com.dwarfeng.dct.handler.vc.*;
import com.dwarfeng.dct.struct.ValueCodingConfig;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.ArrayList;

@Configuration
public class CustomConfiguration {

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
}
```

## 参阅

- [FlatData 机制](./FlatDataMechanism.md) - 扁平数据机制，介绍 FlatData 的核心概念、使用场景和编解码机制。
