# 使用说明

dwarfeng-dct (Data Coding and Transmission) 是一个用于提供数据编码与传输能力的 Java 库。本文档将详细介绍如何使用
dwarfeng-dct 进行数据编码、传输与解码。

## 概述

dwarfeng-dct 提供了标准化的数据编码格式，使得不同系统之间可以方便地进行数据交换。主要包含以下核心组件：

- **Data/GeneralData**: 标准的数据结构与通用实现
- **FlatData**: 扁平化的数据传输结构
- **FastJsonFlatData/FastJsonFlatDataCodec**: JSON 序列化版本
- **DataCodingHandler/ValueCodingHandler**: 数据编解码、值编解码与时间点转换入口（支持 `Instant` 工具类）
- **CompareUtil**: 统一比较器入口，提供 `LongIdKey`、`Instant`、`Data`、`GeneralData`、`FlatData` 的排序常量

## 快速开始

### 1. 添加依赖

在您的 Maven 项目中添加 dwarfeng-dct 依赖：

```xml
<?xml version="1.0" encoding="UTF-8"?>

<!--suppress MavenModelInspection, MavenModelVersionMissed -->
<project
        xmlns="http://maven.apache.org/POM/4.0.0"
        xmlns:xsi="http://www.w3.org/2001/XMLSchema-instance"
        xsi:schemaLocation="http://maven.apache.org/POM/4.0.0
        http://maven.apache.org/xsd/maven-4.0.0.xsd"
>

    <!-- 省略其他配置 -->
    <dependencies>
        <!-- 省略其他配置 -->
        <dependency>
            <groupId>com.dwarfeng</groupId>
            <artifactId>dwarfeng-dct-core</artifactId>
            <version>${dwarfeng-dct.version}</version>
        </dependency>
       <dependency>
          <groupId>com.dwarfeng</groupId>
          <artifactId>dwarfeng-dct-api</artifactId>
          <version>${dwarfeng-dct.version}</version>
       </dependency>
        <!-- 省略其他配置 -->
    </dependencies>
    <!-- 省略其他配置 -->
</project>
```

### 2. 基本使用

#### 创建 GeneralData 对象

```java
import com.dwarfeng.dct.stack.bean.dto.GeneralData;
import com.dwarfeng.subgrade.stack.bean.key.LongIdKey;

import java.util.Date;

public class Example {

    @SuppressWarnings("UnnecessaryModifier")
    public static void main(String[] args) {
        // 创建通用数据对象。
        GeneralData generalData = new GeneralData(
                new LongIdKey(12345L),     // 数据点主键
                25.6D,                     // 数据值（对象格式）
                new Date(),                // 发生时间（毫秒）
                123456                     // 毫秒内纳秒偏移
        );
        // 输出通用数据。
        System.out.println(generalData);
    }
}
```

#### 使用 newInstance(Instant) 快速构造 Bean

```java
import com.dwarfeng.dct.sdk.util.FlatDataUtil;
import com.dwarfeng.dct.sdk.util.GeneralDataUtil;
import com.dwarfeng.dct.stack.bean.dto.FlatData;
import com.dwarfeng.dct.stack.bean.dto.GeneralData;
import com.dwarfeng.subgrade.stack.bean.key.LongIdKey;

import java.time.Instant;

public class Example {

    @SuppressWarnings("UnnecessaryModifier")
    public static void main(String[] args) {
        Instant happenedInstant = Instant.parse("2024-01-15T02:30:00.123456789Z");

        // 一步完成对象创建与时间语义拆分（happenedDate + happenedDateNanoOffset）。
        GeneralData generalData = GeneralDataUtil.newInstance(new LongIdKey(12345L), 25.6D, happenedInstant);
        FlatData flatData = FlatDataUtil.newInstance(new LongIdKey(12345L), "double:25.6", happenedInstant);

        // 可通过 getHappenedInstant 还原为同一 Instant。
        System.out.println(GeneralDataUtil.getHappenedInstant(generalData));
        System.out.println(FlatDataUtil.getHappenedInstant(flatData));
    }
}
```

推荐优先使用 `newInstance(..., Instant)`，避免手动拼装 `Date` 与纳秒偏移导致的精度误差或语义不一致。

#### JSON 序列化和反序列化

```java
import com.dwarfeng.dct.impl.handler.DataCodingHandlerImpl;
import com.dwarfeng.dct.impl.handler.ValueCodingHandlerImpl;
import com.dwarfeng.dct.impl.handler.fdc.FastJsonFlatDataCodec;
import com.dwarfeng.dct.impl.handler.vc.DoubleValueCodec;
import com.dwarfeng.dct.stack.bean.dto.GeneralData;
import com.dwarfeng.dct.stack.handler.DataCodingHandler;
import com.dwarfeng.dct.stack.handler.ValueCodec;
import com.dwarfeng.dct.stack.handler.ValueCodingHandler;
import com.dwarfeng.dct.stack.struct.Data;
import com.dwarfeng.dct.stack.struct.DataCodingConfig;
import com.dwarfeng.dct.stack.struct.ValueCodingConfig;
import com.dwarfeng.subgrade.stack.bean.key.LongIdKey;

import java.util.Collections;
import java.util.Date;
import java.util.List;

public class Example {

    @SuppressWarnings("UnnecessaryModifier")
    public static void main(String[] args) throws Exception {
        List<ValueCodec> valueCodecs = Collections.singletonList(new DoubleValueCodec());
        ValueCodingConfig valueCodingConfig = new ValueCodingConfig.Builder()
                .addCodecs(valueCodecs)
                .addPreCacheClass(Double.class)
                .addPreCachePrefix("double")
                .build();
        ValueCodingHandler valueCodingHandler = new ValueCodingHandlerImpl(valueCodingConfig);

        DataCodingConfig dataCodingConfig = new DataCodingConfig.Builder()
                .setFlatDataCodec(new FastJsonFlatDataCodec())
                .setValueCodingHandler(valueCodingHandler)
                .build();
        DataCodingHandler dataCodingHandler = new DataCodingHandlerImpl(dataCodingConfig);

        GeneralData generalData = new GeneralData(new LongIdKey(12345L), 25.6D, new Date(), 456789);
        // 将 GeneralData 转换为 JSON 字符串.
        String jsonMessage = dataCodingHandler.encode(generalData);
        // 输出: {"point_key":{"long_id":12345},"value":"double:25.6","happened_date":1705285800123,
        // "happened_date_nano_offset":456789}。
        System.out.println(jsonMessage);
        // 将 JSON 字符串转换回 Data 对象。
        Data parsedData = dataCodingHandler.decode(jsonMessage);
        // 输出: GeneralData{pointKey=LongIdKey{longId=12345}, value=25.6, ...}。
        System.out.println(parsedData);
    }
}
```

`happened_date_nano_offset` 为新增字段，旧消息未携带该字段时会按 `0` 处理，兼容历史格式。

## 实际应用场景

### Kafka 消息处理

以下是一个真实的 Kafka 消费者示例，展示了如何在消息队列中使用 dwarfeng-dct：

```java
import com.dwarfeng.dct.stack.handler.DataCodingHandler;
import com.dwarfeng.dct.stack.struct.Data;

import java.util.List;
import java.util.function.Consumer;

@KafkaListener(
        id = "${kafka.listener.id}",
        containerFactory = "kafkaListenerContainerFactory",
        topics = "${kafka.topic.data}"
)
public void onMessage(List<String> messages) {
    Consumer<Data> processor = this::processData;

    for (String message : messages) {
        try {
            Data data = dataCodingHandler.decode(message);
            processor.accept(data);
        } catch (Exception e) {
            LOGGER.error("解析数据失败: {}", message, e);
        }
    }
}
```

### 数据收集和传输

```java
import com.dwarfeng.dct.stack.bean.dto.GeneralData;
import com.dwarfeng.subgrade.stack.bean.key.LongIdKey;

import java.util.Date;

public class DataCollector {

    public void collectAndSend(long pointLongId, Object value) throws Exception {
        // 收集数据。
        GeneralData generalData = new GeneralData(
                new LongIdKey(pointLongId),
                value,
                new Date()
        );

        // 编码数据。
        String message = dataCodingHandler.encode(generalData);

        // 发送到消息队列或其他传输通道。
        messageSender.send(message);
    }
}
```

### 数据排序和比较

```java
import com.dwarfeng.dct.sdk.util.CompareUtil;
import com.dwarfeng.dct.stack.struct.Data;

import java.util.Collections;
import java.util.List;

public class DataSorter {

    @SuppressWarnings("Java8ListSort")
    public void sortData(List<Data> dataList) {
        // 按默认顺序排序（点位主键 + 发生时间）。
        Collections.sort(dataList, CompareUtil.DATA_DEFAULT_COMPARATOR);

        // 按发生时间升序排序。
        Collections.sort(dataList, CompareUtil.DATA_HAPPENED_INSTANT_ASC_COMPARATOR);
    }
}
```

## 高级用法

### 批量数据处理

```java
import com.dwarfeng.dct.sdk.util.CompareUtil;
import com.dwarfeng.dct.stack.struct.Data;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class BatchDataProcessor {

    public void processBatchData(List<String> jsonMessages) {
        List<Data> dataList = new ArrayList<>();

        for (String message : jsonMessages) {
            try {
                Data data = dataCodingHandler.decode(message);
                dataList.add(data);
            } catch (Exception e) {
                LOGGER.error("解析消息失败: {}", message, e);
            }
        }

        // 按发生时间升序（日期 + 毫秒内纳秒偏移）。
        dataList.sort(CompareUtil.DATA_HAPPENED_INSTANT_ASC_COMPARATOR);

        // 批量处理
        processDataList(dataList);
    }
}
```

### 使用 FlatData

```java
import com.dwarfeng.dct.impl.handler.fdc.FastJsonFlatDataCodec;
import com.dwarfeng.dct.sdk.util.CompareUtil;
import com.dwarfeng.dct.sdk.util.FlatDataUtil;
import com.dwarfeng.dct.stack.bean.dto.FlatData;
import com.dwarfeng.subgrade.stack.bean.key.LongIdKey;

import java.time.Instant;
import java.util.Arrays;
import java.util.Collections;
import java.util.Date;
import java.util.List;

public class Example {

    @SuppressWarnings({"UnnecessaryModifier", "ArraysAsListWithZeroOrOneArgument", "Java8ListSort"})
    public static void main(String[] args) throws Exception {
        // 创建 FlatData 对象（值已经是编码后的字符串）。
        FlatData flatData = new FlatData(new LongIdKey(12345L), "integer:42", new Date(), 123456);
        // 通过工具类将时间设置为完整 Instant。
        FlatDataUtil.setHappenedInstant(flatData, Instant.parse("2024-01-15T02:30:00.123456789Z"));
        // FlatData 默认排序就是按点位主键与发生时间升序。
        List<FlatData> flatDataList = Arrays.asList(flatData);
        Collections.sort(flatDataList, CompareUtil.FLAT_DATA_DEFAULT_COMPARATOR);

        // 也可以直接使用 FastJsonFlatDataCodec 进行扁平数据编解码。
        FastJsonFlatDataCodec codec = new FastJsonFlatDataCodec();
        System.out.println(codec.encode(flatData));
    }
}
```

### 自定义数据转换

```java
import com.dwarfeng.dct.stack.bean.dto.GeneralData;
import com.dwarfeng.subgrade.stack.bean.key.LongIdKey;

public class CustomDataConverter {

    public GeneralData convertFromBusinessObject(BusinessData businessData) {
        return new GeneralData(
                new LongIdKey(businessData.getPointId()),
                businessData.getValue(),
                businessData.getTimestamp(),
                businessData.getTimestampNanoOffset()
        );
    }

    public BusinessData convertToBusinessObject(GeneralData generalData) {
        // ... 业务对象转换逻辑 ...
        return new BusinessData(
                generalData.getPointKey().getLongId(),
                generalData.getValue(),
                generalData.getHappenedDate(),
                generalData.getHappenedDateNanoOffset()
        );
    }
}
```

## 最佳实践

### 1. 错误处理

```java
import com.dwarfeng.dct.stack.struct.Data;

public class Example {

    public Data safeParseMessage(String message) {
        try {
            return dataCodingHandler.decode(message);
        } catch (Exception e) {
            LOGGER.error("解析 Data 失败: {}", message, e);
            return null; // 或者抛出业务异常
        }
    }
}
```

### 2. 性能优化

对于大量数据的处理，考虑使用批量操作：

```java
import com.dwarfeng.dct.stack.struct.Data;

import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

public class Example {

    public void processLargeDataset(List<String> messages) {
        List<Data> validData = messages.parallelStream()
                .map(this::safeParseMessage)
                .filter(Objects::nonNull)
                .collect(Collectors.toList());

        // 批量处理有效数据
        batchProcessor.process(validData);
    }
}
```

### 3. 数据验证

```java
import com.dwarfeng.dct.stack.struct.Data;
import com.dwarfeng.dutil.basic.time.TimeUtil;

public class Example {

    public boolean isValidData(Data data) {
        return data != null
                && data.getPointKey() != null
                && data.getPointKey().getLongId() > 0
                && data.getHappenedDate() != null
                && isNanoOffsetValid(data.getHappenedDateNanoOffset());
    }

    private boolean isNanoOffsetValid(int nanoOffset) {
        try {
            TimeUtil.checkNanoOffset(nanoOffset);
            return true;
        } catch (IllegalArgumentException ignored) {
            return false;
        }
    }
}
```

## 注意事项

1. **数据格式**: `GeneralData` 中的 value 字段为对象类型，传输时会通过 `ValueCodingHandler` 编码为带前缀的字符串
2. **时间处理**: `happenedDate` 与 `happenedDateNanoOffset` 共同组成完整时间点，
   跨系统建议统一使用 `DataUtil/GeneralDataUtil/FlatDataUtil` 与 `Instant` 进行转换
3. **值编解码策略**: 优先使用内置 `ValueCodec` 与预缓存配置；自定义值类型可通过扩展 `ValueCodec` 支持
4. **排序策略**: 优先使用 `CompareUtil` 的比较器常量（如 `DATA_DEFAULT_COMPARATOR`、
   `DATA_HAPPENED_INSTANT_ASC_COMPARATOR`、`FLAT_DATA_DEFAULT_COMPARATOR`）
5. **性能考虑**: 对于大量数据，考虑使用批量处理和异步处理
