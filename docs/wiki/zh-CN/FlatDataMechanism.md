# FlatData 机制

## 概述

`FlatData`（扁平数据）是 `dwarfeng-dct` 项目中的核心数据结构，用于表示将复杂数据对象转换为文本格式后的数据。
它是数据编码处理过程中的重要中间格式，特别适用于消息队列传输和数据库存储场景。

扁平数据包含四个核心属性：点位主键、数据值、发生时间和毫秒内纳秒偏移。
数据值采用 `prefix:flat_value` 格式，其中前缀标识数据类型，扁平值部分包含实际的文本数据。

## 核心概念

### 什么是扁平数据

扁平数据是指将 `Data.getValue()` 中的数据转换为文本后的数据。扁平数据包含以下四个核心属性：

- **点位主键** (`pointKey`): 标识数据所属的点位。
- **数据值** (`value`): 数据的文本表示，格式为 `prefix:flat_value`。
- **发生时间** (`happenedDate`): 数据发生的时间戳。
- **毫秒内纳秒偏移** (`happenedDateNanoOffset`): 发生时间在当前毫秒内的纳秒偏移，取值范围为 `0 ~ 999999`，默认值为 `0`。

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
    private int happenedDateNanoOffset; // 发生时间在毫秒内的纳秒偏移，默认值为 0。
}
```

## 时间精度模型

### 字段语义

`FlatData` 使用以下组合表达高精度发生时间：

- `happenedDate`：毫秒级基准时间。
- `happenedDateNanoOffset`：该毫秒内的纳秒偏移量，范围为 `0 ~ 999999`。

完整瞬时时间由二者共同决定。推荐通过工具类对 `FlatData` 进行 `Instant` 操作。

## 数据工具类

### Instant 工厂构造

推荐优先使用 `FlatDataUtil.newInstance(LongIdKey pointKey, String value, Instant happenedInstant)` 构造对象，
避免手工分拆时间字段导致语义偏差。

```java
import com.dwarfeng.dct.bean.dto.FlatData;
import com.dwarfeng.dct.util.FlatDataUtil;
import com.dwarfeng.subgrade.stack.bean.key.LongIdKey;

import java.time.Instant;

public class Example {

    public FlatData buildFlatData() {
        LongIdKey pointKey = new LongIdKey(12450L);
        String value = "integer:42";
        Instant happenedInstant = Instant.parse("2026-04-24T10:30:12.123456789Z");

        return FlatDataUtil.newInstance(pointKey, value, happenedInstant);
    }
}
```

### Instant 的 Getters 和 Setters

推荐统一通过 `FlatDataUtil` 进行转换：

- `FlatDataUtil.setHappenedInstant(...)`：根据 `Instant` 同步设置两个字段。
- `FlatDataUtil.getHappenedInstant(...)`：由两个字段还原完整 `Instant`。

```java
import com.dwarfeng.dct.bean.dto.FlatData;
import com.dwarfeng.dct.util.FlatDataUtil;

import java.time.Instant;

public class Example {

    public Instant getHappenedInstant(FlatData flatData) {
        return FlatDataUtil.getHappenedInstant(flatData);
    }

    public void setHappenedInstant(FlatData flatData, Instant happenedInstant) {
        FlatDataUtil.setHappenedInstant(flatData, happenedInstant);
    }
}
```

## 数据排序

`CompareUtil` 为 `FlatData` 提供统一排序语义，推荐优先复用以下比较器：

- `CompareUtil.FLAT_DATA_DEFAULT_COMPARATOR`：默认比较，先按 `pointKey` 升序，
  再按发生 `Instant` 升序（由 `happenedDate + happenedDateNanoOffset` 组合计算）。
- `CompareUtil.FLAT_DATA_HAPPENED_INSTANT_ASC_COMPARATOR`：仅按发生 `Instant` 升序，不考虑 `pointKey`。
- `CompareUtil.FLAT_DATA_HAPPENED_INSTANT_DESC_COMPARATOR`：仅按发生 `Instant` 降序，不考虑 `pointKey`。

说明：本一排序机制仅覆盖 `Data`、`GeneralData`、`FlatData`，不包含 `FastJsonFlatData`,
因为后者主要用于编解码过程中的 JSON 映射，不建议直接在业务逻辑中使用。

## 实际应用

### 消息队列传输

在消息队列传输场景中，`FlatData` 提供了标准化的数据格式。发送方将数据编码为文本格式，接收方解码后进行处理：

发送方代码示例：

```java
import com.dwarfeng.dct.struct.Data;

public class Example {

    public void sendData(Data originalData) {
        // 编码数据。
        String encodedData = dataCodingHandler.encode(originalData);
        // 发送消息。
        messageQueue.send(encodedData);
    }
}
```

接收方代码示例：

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

### 数据存储

在需要将数据存储到文本格式的存储系统中时，`FlatData` 提供了标准化的格式：

```java
import com.dwarfeng.dct.bean.dto.FlatData;
import com.dwarfeng.subgrade.stack.bean.key.LongIdKey;

public class Example {

    public void buildFlatData() {
        // 构造 FlatData 对象。
        FlatData flatData = new FlatData(new LongIdKey(12450L), "integer:42", new Date());
        // 输出 FlatData 信息。
        System.out.println(flatData);
    }
}
```

## 编解码机制

### FastJson 字段映射

默认 `FastJsonFlatDataCodec` 对应的 JSON 字段如下：

- `point_key`: 点位主键。
- `value`: 扁平值，格式为 `prefix:flat_value`。
- `happened_date`: 毫秒时间戳。
- `happened_date_nano_offset`: 发生时间在毫秒内的纳秒偏移。

示例：

```json
{
  "point_key": {
    "long_id": 12450
  },
  "value": "integer:42",
  "happened_date": 724608000000,
  "happened_date_nano_offset": 123456
}
```

兼容性说明：

- 旧 JSON 不包含 `happened_date_nano_offset` 时，解码结果默认按 `0` 处理。
- 完整发生时间由 `happened_date + happened_date_nano_offset` 共同表达。

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

## 参阅

- [GeneralData 机制](./GeneralDataMechanism.md) - 通用数据机制，介绍 GeneralData 的数据结构、支持的数据类型和值编解码机制。
