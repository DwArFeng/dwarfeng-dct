# Quick Start - 快速开始

本文档帮助您用最少的步骤体验 `dwarfeng-dct` 的数据编码与解码能力。

## 确认环境

- JDK 1.8 或更高版本。
- Maven 3.x。

如果您的项目无法直接从中央仓库解析本项目依赖，请先参阅 [Install by Source Code](./InstallBySourceCode.md) 安装本项目及其依赖。

## 引入依赖

在项目的 `pom.xml` 中添加如下依赖：

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

## 最小示例

完成依赖引入后，即可使用 `GeneralData` 与 `DataCodingHandler` 完成最小数据编解码链路。

```java
import com.dwarfeng.dct.impl.handler.DataCodingHandlerImpl;
import com.dwarfeng.dct.impl.handler.ValueCodingHandlerImpl;
import com.dwarfeng.dct.impl.handler.fdc.FastJsonFlatDataCodec;
import com.dwarfeng.dct.impl.handler.vc.IntegerValueCodec;
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

public class QuickStartExample {

    @SuppressWarnings("UnnecessaryModifier")
    public static void main(String[] args) throws Exception {
        // 构造值编解码处理器。
        List<ValueCodec> valueCodecs = Collections.singletonList(new IntegerValueCodec());
        ValueCodingConfig valueCodingConfig = new ValueCodingConfig.Builder()
                .addCodecs(valueCodecs)
                .addPreCacheClass(Integer.class)
                .addPreCachePrefix("integer")
                .build();
        ValueCodingHandler valueCodingHandler = new ValueCodingHandlerImpl(valueCodingConfig);

        // 构造数据编解码处理器。
        DataCodingConfig dataCodingConfig = new DataCodingConfig.Builder()
                .setFlatDataCodec(new FastJsonFlatDataCodec())
                .setValueCodingHandler(valueCodingHandler)
                .build();
        DataCodingHandler dataCodingHandler = new DataCodingHandlerImpl(dataCodingConfig);

        // 构造一条原始数据。
        GeneralData origin = new GeneralData(new LongIdKey(12450L), 42, new Date());

        // 编码为文本。
        String encoded = dataCodingHandler.encode(origin);
        System.out.println("编码结果: " + encoded);

        // 从文本解码为数据对象。
        Data decoded = dataCodingHandler.decode(encoded);
        System.out.println("解码结果: " + decoded);
        System.out.println("解码 pointLongId: " + decoded.getPointKey().getLongId());
    }
}
```

运行后，您会看到类似如下风格的文本：

```text
编码结果: {"point_key":{"long_id":12450},"value":"integer:42","happened_date": ...
解码结果: GeneralData{pointKey=LongIdKey{longId=12450}, value=42, happenedDate= ...
解码 pointLongId: 12450
```

这说明 `dwarfeng-dct` 已经完成了如下工作：

1. 将 `GeneralData` 按默认规则编码为 JSON 文本。
2. 使用 `point_key`、`value`、`happened_date`、`happened_date_nano_offset` 作为消息字段名。
3. 在解码时将文本恢复为 `Data` 对象，并将 `integer:42` 恢复为 `Integer` 值。

## 下一步

- [FlatData Mechanism](./FlatDataMechanism.md) - dwarfeng-dct 的扁平数据机制说明，介绍 JSON 字段结构和编解码语义。
- [GeneralData Mechanism](./GeneralDataMechanism.md) - dwarfeng-dct 的通用数据机制说明，介绍数据对象结构和时间精度模型。
- [Extend ValueCodec](./ExtendValueCodec.md) - dwarfeng-dct 的值编解码扩展说明，介绍如何支持自定义对象类型。
