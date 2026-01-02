# 扩展 dwarfeng-dct 支持的对象类型

## 概述

`dwarfeng-dct` 项目通过值编解码器（ValueCodec）机制支持多种数据类型的编码和解码。
虽然项目内置了常见的数据类型支持（如 `Integer`、`String`、`BigDecimal` 等），但在实际应用中，您可能需要支持自定义的数据类型。

通过实现 `ValueCodec` 接口，您可以扩展 `dwarfeng-dct` 支持的对象类型，使其能够编码和解码您自定义的数据类型。
这种扩展机制使得 `dwarfeng-dct` 具有高度的灵活性和可扩展性。

本文档将详细介绍如何扩展 `dwarfeng-dct` 支持的对象类型，包括核心概念、实现步骤、完整示例和最佳实践。

## 核心概念

### ValueCodec 接口

`ValueCodec` 接口定义了值编解码器的基本功能，是扩展对象类型的核心接口。该接口包含以下方法：

- **`getTargetClass()`**: 返回该编解码器支持的目标类型。该方法每次被调用时，返回的值必须是一样的。
- **`getValuePrefix()`**: 返回值前缀，用于标识编码后的文本格式。该方法每次被调用时，返回的值必须是一样的。
- **`getPriority()`**: 返回优先级，值越小优先级越高。当多个编解码器支持同一类型时，会选择优先级最高的。
- **`encode(Object target)`**: 将指定的目标对象编码为文本。传入的目标对象不会为 `null`。
- **`decode(String text)`**: 将指定的值文本解码为对象。返回的解码后的对象不会为 `null`。

### AbstractValueCodec 抽象类

`AbstractValueCodec` 是 `ValueCodec` 接口的抽象实现类，它封装了通用的业务逻辑，如异常处理等。
继承该类可以使开发人员更加专注于编解码业务的实现。

该类提供了以下抽象方法供子类实现：

- **`doEncode(Object target)`**: 编码方法的具体实现，不需要关注异常处理。
- **`doDecode(String text)`**: 解码方法的具体实现，不需要关注异常处理。

### 优先级机制

优先级机制用于解决多个编解码器支持同一类型时的选择问题。优先级值越小，优先级越高。
`dwarfeng-dct` 在 `Constants` 类中定义了以下优先级常量：

- `VALUE_CODEC_PRIORITY_MAX`: 最高优先级（`Integer.MIN_VALUE`）
- `VALUE_CODEC_PRIORITY_VERY_HIGH`: 非常高优先级（`Integer.MIN_VALUE + 1000`）
- `VALUE_CODEC_PRIORITY_HIGH`: 高优先级（`Integer.MIN_VALUE + 2000`）
- `VALUE_CODEC_PRIORITY_NORMAL`: 正常优先级（`0`）
- `VALUE_CODEC_PRIORITY_LOW`: 低优先级（`Integer.MAX_VALUE - 2000`）
- `VALUE_CODEC_PRIORITY_VERY_LOW`: 非常低优先级（`Integer.MAX_VALUE - 1000`）
- `VALUE_CODEC_PRIORITY_MIN`: 最低优先级（`Integer.MAX_VALUE`）

### 值前缀机制

值前缀用于标识编码后的文本格式。
编码后的文本采用 `prefix:value` 格式，其中 `prefix` 是值前缀，`value` 是编码后的实际值。例如：

- `integer:123` - 整数类型，前缀为 `integer`，值为 `123`。
- `string:hello` - 字符串类型，前缀为 `string`，值为 `hello`。
- `big_decimal:3.14159` - 大数类型，前缀为 `big_decimal`，值为 `3.14159`。

值前缀应该具有唯一性，避免与其他编解码器的前缀冲突。

**重要约束**：自定义的 `ValueCodec`，`getValuePrefix()` 提供的值前缀中，不得包含冒号 `:`。
由于编码后的文本采用 `prefix:value` 格式，冒号 `:` 作为分隔符使用，因此值前缀中不能包含冒号，否则会导致解析错误。

## 实现步骤

### 步骤 1：创建自定义 ValueCodec 类

创建一个新的类，继承 `AbstractValueCodec` 抽象类。

### 步骤 2：实现构造函数

在构造函数中调用父类构造函数，传入目标类、值前缀和优先级。

**重要约束**: 调用父类构造函数时，传入的值前缀中不得包含冒号 `:`。
由于编码后的文本采用 `prefix:value` 格式，冒号 `:` 作为分隔符使用，因此值前缀中不能包含冒号，否则会导致解析错误。

### 步骤 3：实现 doEncode 方法

实现 `doEncode` 方法，将目标对象编码为文本字符串。该方法接收一个非 `null` 的目标对象，返回编码后的文本。

### 步骤 4：实现 doDecode 方法

实现 `doDecode` 方法，将文本字符串解码为目标对象。该方法接收一个非 `null` 的文本字符串，返回解码后的对象。

### 步骤 5：配置和使用

在 Spring 配置中注册自定义的 `ValueCodec`，并将其添加到 `ValueCodingConfig` 中。

## 完整示例

### 示例 1：UUID 类型扩展

以下示例展示了如何为 `UUID` 类型创建自定义的 `ValueCodec`：

```java
import com.dwarfeng.dct.handler.vc.AbstractValueCodec;
import com.dwarfeng.dct.util.Constants;

import javax.annotation.Nonnull;
import java.util.UUID;

public class UuidValueCodec extends AbstractValueCodec {

    public UuidValueCodec() {
        super(UUID.class, "uuid", Constants.VALUE_CODEC_PRIORITY_NORMAL);
    }

    @Nonnull
    @Override
    protected String doEncode(@Nonnull Object target) {
        // 将 UUID 对象转换为字符串。
        UUID uuid = (UUID) target;
        return uuid.toString();
    }

    @Nonnull
    @Override
    protected Object doDecode(@Nonnull String text) {
        // 从字符串解析 UUID 对象。
        return UUID.fromString(text);
    }
}
```

### 示例 2：自定义 POJO 类型扩展

以下示例展示了如何为自定义 POJO 类型创建 `ValueCodec`。假设我们有一个 `UserInfo` 类：

```java
import com.alibaba.fastjson2.JSON;
import com.dwarfeng.dct.handler.vc.AbstractValueCodec;
import com.dwarfeng.dct.util.Constants;
import com.dwarfeng.subgrade.stack.bean.Bean;

import javax.annotation.Nonnull;

public class UserInfoValueCodec extends AbstractValueCodec {

    public UserInfoValueCodec() {
        super(UserInfo.class, "user_info", Constants.VALUE_CODEC_PRIORITY_NORMAL);
    }

    @Nonnull
    @Override
    protected String doEncode(@Nonnull Object target) {
        // 使用 FastJSON 将对象序列化为 JSON 字符串。
        UserInfo userInfo = (UserInfo) target;
        return JSON.toJSONString(userInfo);
    }

    @Nonnull
    @Override
    protected Object doDecode(@Nonnull String text) {
        // 使用 FastJSON 将 JSON 字符串反序列化为对象。
        return JSON.parseObject(text, UserInfo.class);
    }
}

// UserInfo 类定义示例。
class UserInfo implements Bean {

    @SuppressWarnings("MissingSerialAnnotation")
    private static final long serialVersionUID = 1L;

    private String username;
    private Integer age;
    private String email;

    // 省略构造函数、getter 和 setter 方法。
}
```

### 示例 3：使用自定义 ValueCodec

以下示例展示了如何在 Spring 配置中使用自定义的 `ValueCodec`：

```java
import com.dwarfeng.dct.handler.ValueCodec;
import com.dwarfeng.dct.handler.ValueCodingHandler;
import com.dwarfeng.dct.handler.ValueCodingHandlerImpl;
import com.dwarfeng.dct.handler.vc.*;
import com.dwarfeng.dct.struct.ValueCodingConfig;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Configuration
public class CustomValueCodecConfiguration {

    @Bean
    public List<ValueCodec> valueCodecs() {
        List<ValueCodec> codecs = new ArrayList<>();
        // 添加内置的编解码器。
        codecs.add(new BooleanValueCodec());
        codecs.add(new IntegerValueCodec());
        codecs.add(new LongValueCodec());
        codecs.add(new DoubleValueCodec());
        codecs.add(new StringValueCodec());
        // 添加自定义的编解码器。
        codecs.add(new UuidValueCodec());
        codecs.add(new UserInfoValueCodec());
        return codecs;
    }

    @Bean
    public ValueCodingHandler valueCodingHandler(List<ValueCodec> valueCodecs) {
        ValueCodingConfig config = new ValueCodingConfig.Builder()
                .addCodecs(valueCodecs)
                // 预缓存常用类型，提高性能。
                .addPreCacheClasses(
                        valueCodecs.stream()
                                .map(ValueCodec::getTargetClass)
                                .collect(Collectors.toList())
                )
                .addPreCachePrefixes(
                        valueCodecs.stream()
                                .map(ValueCodec::getValuePrefix)
                                .collect(Collectors.toList())
                )
                .build();
        return new ValueCodingHandlerImpl(config);
    }
}
```

### 示例 4：实际应用场景

以下示例展示了如何在应用中使用自定义的 `ValueCodec`：

```java
import com.dwarfeng.dct.bean.dto.GeneralData;
import com.dwarfeng.dct.handler.DataCodingHandler;
import com.dwarfeng.dct.struct.Data;
import com.dwarfeng.subgrade.stack.bean.key.LongIdKey;

import java.util.Date;
import java.util.UUID;

public class CustomValueCodecExample {

    private final DataCodingHandler dataCodingHandler;

    public CustomValueCodecExample(DataCodingHandler dataCodingHandler) {
        this.dataCodingHandler = dataCodingHandler;
    }

    public void encodeUuidExample() {
        // 创建包含 UUID 值的通用数据。
        UUID uuid = UUID.randomUUID();
        GeneralData data = new GeneralData(new LongIdKey(12450L), uuid, new Date());

        // 编码为文本。
        String encodedText = dataCodingHandler.encode(data);
        // 输出示例：Encoded Text: {"pointKey":{"longId":12450},"value":"uuid:550e8400-e29b-41d4-a716-446655440000","...
        System.out.println("Encoded Text: " + encodedText);
    }

    public void decodeUuidExample(String encodedText) {
        // 从文本解码。
        Data decodedData = dataCodingHandler.decode(encodedText);
        UUID uuid = (UUID) decodedData.getValue();
        System.out.println("Decoded UUID: " + uuid);
    }

    public void encodeUserInfoExample() {
        // 创建包含 UserInfo 值的通用数据。
        UserInfo userInfo = new UserInfo("张三", 25, "zhangsan@example.com");
        GeneralData data = new GeneralData(new LongIdKey(12451L), userInfo, new Date());

        // 编码为文本。
        String encodedText = dataCodingHandler.encode(data);
        System.out.println("Encoded Text: " + encodedText);
    }

    public void decodeUserInfoExample(String encodedText) {
        // 从文本解码。
        Data decodedData = dataCodingHandler.decode(encodedText);
        UserInfo userInfo = (UserInfo) decodedData.getValue();
        System.out.println("Decoded UserInfo: " + userInfo);
    }
}
```

## 配置方式

### Spring 配置

在 Spring 应用中，您可以通过 `@Configuration` 类来配置自定义的 `ValueCodec`。配置步骤如下：

1. 创建一个 `@Configuration` 类。
2. 创建一个 `@Bean` 方法，返回 `List<ValueCodec>`，包含所有需要使用的编解码器。
3. 创建一个 `@Bean` 方法，返回 `ValueCodingHandler`，使用 `ValueCodingConfig.Builder` 构建配置。

### 预缓存配置

为了提高性能，您可以在配置中指定预缓存的类和前缀。预缓存可以在初始化时将常用的编解码器提前加载到缓存中，避免运行时查找。

使用 `ValueCodingConfig.Builder` 的 `addPreCacheClasses` 和 `addPreCachePrefixes` 方法可以配置预缓存。

## 最佳实践

### 优先级选择建议

- **特定类型优先**：为特定类型创建的编解码器应该使用较高的优先级（如 `VALUE_CODEC_PRIORITY_HIGH`），
  以确保它们优先于通用编解码器（如 `SerializableValueCodec`）被使用。
- **通用类型靠后**：通用的编解码器（如 `SerializableValueCodec`）应该使用最低优先级（`VALUE_CODEC_PRIORITY_MIN`），
  作为保底方案。
- **默认使用正常优先级**：对于大多数自定义类型，使用 `VALUE_CODEC_PRIORITY_NORMAL` 即可。

### 前缀命名规范

- **使用小写字母和下划线**：前缀应该使用小写字母和下划线，例如 `user_info`、`big_decimal`。
- **保持唯一性**：确保前缀不与现有的编解码器冲突。
- **具有描述性**：前缀应该能够清楚地描述数据类型，例如 `uuid`、`user_info`。
- **不得包含冒号**：值前缀中不得包含冒号 `:`，因为冒号是编码格式中的分隔符。

### 错误处理建议

- **继承 AbstractValueCodec**：继承 `AbstractValueCodec` 可以让框架自动处理异常，您只需要在 `doEncode` 和 `doDecode`
  方法中抛出异常即可。
- **提供清晰的错误信息**：在编码或解码失败时，应该抛出包含清晰错误信息的异常。

### 性能优化建议

- **使用预缓存**：对于常用的类型，使用预缓存可以提高性能。
- **避免复杂的序列化**：如果可能，使用简单的字符串转换而不是复杂的序列化机制。
- **考虑使用现有的序列化库**：对于复杂对象，可以使用 FastJSON、Jackson 等成熟的序列化库。

## 参考

- [GeneralData 机制](./GeneralDataMechanism.md) - 通用数据机制，介绍 GeneralData 的数据结构、支持的数据类型和值编解码机制。
- [FlatData 机制](./FlatDataMechanism.md) - 扁平数据机制，介绍 FlatData 的核心概念、使用场景和编解码机制。
