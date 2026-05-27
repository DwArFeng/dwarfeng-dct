# dwarfeng-dct

数据编码与传输服务。

Data Coding and Transmission for DwArFeng

该项目提供通用的数据编码与传输能力，将带点位、时间和值的数据编码为文本，并支持从文本还原数据，项目基于 `subgrade` 构建。

---

## 特性

1. 标准数据结构：`Data`、`GeneralData`、`FlatData`（毫秒时间基线 + 毫秒内纳秒偏移）。
2. 基于 FastJson 的默认 JSON 数据格式（`point_key`、`value`、`happened_date`、`happened_date_nano_offset`）。
3. 值编解码器体系：`ValueCodingHandler`、`ValueCodec`，支持常见基础类型与可序列化对象。
4. 数据编解码器体系：`DataCodingHandler`、`FlatDataCodec`，支持数据对象与文本的互相转换。
5. Subgrade 架构支持：`DataCodingQosService`、`ValueCodingQosService` 及对应实现。
6. Spring 简单配置：`com.dwarfeng.dct.node.configuration.SimpleConfiguration`。
7. XSD 命名空间装配（`3.0.0.a` 起）。
8. `dwarfeng-dct-api` 模块提供 spring-telqos 集成（`DataCodingCommand`、`ValueCodingCommand`）。

运行下列示例以观察主要特性：

| 所在模块              | 示例类名                                                     | 说明          |
|-------------------|----------------------------------------------------------|-------------|
| dwarfeng-dct-api  | `com.dwarfeng.dct.api.integration.example.TelqosExample` | Telqos 集成示例 |
| dwarfeng-dct-core | `com.dwarfeng.dct.node.example.DataCodingExample`        | 数据编码示例      |
| dwarfeng-dct-core | `com.dwarfeng.dct.node.example.ValueCodingExample`       | 值编码示例       |

## 文档

该项目的文档位于 [docs](./docs) 目录下，包括：

### wiki

wiki 为项目的开发人员为本项目编写的详细文档，包含不同语言的版本，主要入口为：

1. [简介](docs/wiki/zh-CN/Introduction.md) - 镜像的 `README.md`，与本文件内容基本相同。
2. [目录](docs/wiki/zh-CN/Contents.md) - 文档目录。

## 安装说明

1. 下载源码。

   使用 git 进行源码下载。

   ```shell
   git clone git@github.com:DwArFeng/dwarfeng-dct.git
   ```

   对于中国用户，可以使用 gitee 进行高速下载。

   ```shell
   git clone git@gitee.com:dwarfeng/dwarfeng-dct.git
   ```

2. 项目安装。

   进入项目根目录，执行 maven 命令：

   ```shell
   mvn clean source:jar install
   ```

3. 项目引入。

   在项目的 `pom.xml` 中添加如下依赖：

   `dwarfeng-dct-core` 提供核心 DTO、工具类与 Handler 实现，为大多数场景的必选依赖：

   ```xml
   <dependency>
       <groupId>com.dwarfeng</groupId>
       <artifactId>dwarfeng-dct-core</artifactId>
       <version>${dwarfeng-dct.version}</version>
   </dependency>
   ```

   如需使用 spring-telqos 命令行集成，可额外引入 `dwarfeng-dct-api`：

   ```xml
   <dependency>
       <groupId>com.dwarfeng</groupId>
       <artifactId>dwarfeng-dct-api</artifactId>
       <version>${dwarfeng-dct.version}</version>
   </dependency>
   ```

4. enjoy it.

## 如何使用

1. 运行 `dwarfeng-dct-api/src/test` 或 `dwarfeng-dct-core/src/test` 下的示例与测试以观察主要特性。
2. 观察项目结构，将其中的配置运用到其它的 subgrade 项目中。

### 单例模式

加载 `com.dwarfeng.dct.node.configuration.SimpleConfiguration`，即可获得单例模式的 `DataCodingHandler`、
`ValueCodingHandler`、`DataCodingQosHandler`、`ValueCodingQosHandler`、`DataCodingQosService` 与 `ValueCodingQosService`。  
在项目的 `application-context-scan.xml` 中追加 `com.dwarfeng.dct.node.configuration` 包中全部 bean 的扫描，示例如下:

```xml
<?xml version="1.0" encoding="UTF-8"?>
<!-- 以下注释用于抑制 idea 中 .md 的警告，实际并无错误，在使用时可以连同本注释一起删除。 -->
<!--suppress SpringXmlModelInspection -->
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
    <context:component-scan base-package="com.dwarfeng.dct.node.configuration"/>
</beans>
```

或者只扫描 `com.dwarfeng.dct.node.configuration` 包中的 `SimpleConfiguration`，示例如下:

```xml
<?xml version="1.0" encoding="UTF-8"?>
<!-- 以下注释用于抑制 idea 中 .md 的警告，实际并无错误，在使用时可以连同本注释一起删除。 -->
<!--suppress SpringXmlModelInspection -->
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
    <context:component-scan base-package="com.dwarfeng.dct.node.configuration" use-default-filters="false">
        <context:include-filter
                type="assignable"
                expression="com.dwarfeng.dct.node.configuration.SimpleConfiguration"
        />
    </context:component-scan>
</beans>
```

### 多实例模式

不使用简单配置，使用 xml 或者配置类生成多个 `DataCodingHandlerImpl` 与 `ValueCodingHandlerImpl` 实例。  
在项目的 `bean-definition.xml` 中追加配置，示例如下:

```xml
<?xml version="1.0" encoding="UTF-8"?>
<!-- 以下注释用于抑制 idea 中 .md 的警告，实际并无错误，在使用时可以连同本注释一起删除。 -->
<!--suppress SpringBeanConstructorArgInspection, SpringXmlModelInspection -->
<beans
        xmlns:xsi="http://www.w3.org/2001/XMLSchema-instance"
        xmlns:dct="http://dwarfeng.com/schema/dwarfeng-dct"
        xmlns="http://www.springframework.org/schema/beans"
        xsi:schemaLocation="http://www.springframework.org/schema/beans
        http://www.springframework.org/schema/beans/spring-beans.xsd
        http://dwarfeng.com/schema/dwarfeng-dct
        http://dwarfeng.com/schema/dwarfeng-dct/dwarfeng-dct.xsd"
>

    <!-- 扁平数据编解码器。 -->
    <bean name="fastJsonFlatDataCodec" class="com.dwarfeng.dct.impl.handler.fdc.FastJsonFlatDataCodec"/>

    <!-- 值编解码配置与值编码处理器。 -->
    <dct:value-coding-config config-name="valueCodingConfig">
        <dct:value-codec>
            <dct:value-codec-impl package-scan="com.dwarfeng.dct.impl.handler.vc"/>
        </dct:value-codec>
    </dct:value-coding-config>
    <dct:value-coding-handler handler-name="valueCodingHandler1" config-ref="valueCodingConfig"/>
    <dct:value-coding-handler handler-name="valueCodingHandler2" config-ref="valueCodingConfig"/>

    <!-- 数据编解码配置与数据编码处理器。 -->
    <dct:data-coding-config
            config-name="dataCodingConfig1"
            flat-data-codec-ref="fastJsonFlatDataCodec"
            value-coding-handler-ref="valueCodingHandler1"
    />
    <dct:data-coding-config
            config-name="dataCodingConfig2"
            flat-data-codec-ref="fastJsonFlatDataCodec"
            value-coding-handler-ref="valueCodingHandler2"
    />
    <dct:data-coding-handler handler-name="dataCodingHandler1" config-ref="dataCodingConfig1"/>
    <dct:data-coding-handler handler-name="dataCodingHandler2" config-ref="dataCodingConfig2"/>

    <!-- QoS 处理器：通过构造器注入多个 Handler Bean 组成的 Map。 -->
    <bean name="valueCodingQosHandler" class="com.dwarfeng.dct.impl.handler.ValueCodingQosHandlerImpl">
        <constructor-arg>
            <map>
                <entry key="valueCodingHandler1" value-ref="valueCodingHandler1"/>
                <entry key="valueCodingHandler2" value-ref="valueCodingHandler2"/>
            </map>
        </constructor-arg>
    </bean>
    <bean name="dataCodingQosHandler" class="com.dwarfeng.dct.impl.handler.DataCodingQosHandlerImpl">
        <constructor-arg>
            <map>
                <entry key="dataCodingHandler1" value-ref="dataCodingHandler1"/>
                <entry key="dataCodingHandler2" value-ref="dataCodingHandler2"/>
            </map>
        </constructor-arg>
    </bean>

    <!-- QoS 服务。 -->
    <bean name="valueCodingQosService" class="com.dwarfeng.dct.impl.service.ValueCodingQosServiceImpl">
        <constructor-arg ref="valueCodingQosHandler"/>
        <constructor-arg ref="mapServiceExceptionMapper"/>
    </bean>
    <bean name="dataCodingQosService" class="com.dwarfeng.dct.impl.service.DataCodingQosServiceImpl">
        <constructor-arg ref="dataCodingQosHandler"/>
        <constructor-arg ref="mapServiceExceptionMapper"/>
    </bean>
</beans>
```

### XSD 配置

从 `3.0.0.a` 版本开始，可以使用 `dct` 命名空间装配 `DataCodingHandler`、`ValueCodingHandler` 与对应 QoS 服务。  
在项目的 `application-context-dct.xml` 中追加配置，示例如下:

```xml
<?xml version="1.0" encoding="UTF-8"?>
<!-- 以下注释用于抑制 idea 中 .md 的警告，实际并无错误，在使用时可以连同本注释一起删除。, SpringXmlModelInspection -->
<!--suppress SpringPlaceholdersInspection, SpringXmlModelInspection -->
<beans
        xmlns:xsi="http://www.w3.org/2001/XMLSchema-instance"
        xmlns:dct="http://dwarfeng.com/schema/dwarfeng-dct"
        xmlns="http://www.springframework.org/schema/beans"
        xsi:schemaLocation="http://www.springframework.org/schema/beans
        http://www.springframework.org/schema/beans/spring-beans.xsd
        http://dwarfeng.com/schema/dwarfeng-dct
        http://dwarfeng.com/schema/dwarfeng-dct/dwarfeng-dct.xsd"
>

    <bean id="fastJsonFlatDataCodec" class="com.dwarfeng.dct.impl.handler.fdc.FastJsonFlatDataCodec"/>

    <dct:value-coding-config>
        <dct:value-codec>
            <dct:value-codec-impl package-scan="com.dwarfeng.dct.impl.handler.vc"/>
        </dct:value-codec>
    </dct:value-coding-config>
    <dct:value-coding-handler/>
    <dct:value-coding-qos/>
    <dct:data-coding-config flat-data-codec-ref="fastJsonFlatDataCodec"/>
    <dct:data-coding-handler/>
    <dct:data-coding-qos/>
</beans>
```

### 任意数量的实例模式

自行设计 `DataCodingHandler` 与 `ValueCodingHandler` 的工厂类，调用相关工厂方法生成处理器实例，并按需注册到 Spring 容器中。
当前默认实现 `DataCodingHandlerImpl` 与 `ValueCodingHandlerImpl` 均通过配置对象工作，可按需创建不同配置的实例。
