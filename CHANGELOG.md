# ChangeLog

## Release_3.0.2_20260612_build_A

### 功能构建

- 更新 README.md。

- Wiki 更新。
  - docs/wiki/zh-CN/VersionBlacklist.md。
  - docs/wiki/zh-CN/Introduction.md。

- 优化单元测试。
  - com.dwarfeng.dct.impl.handler.DataCodingQosHandlerImplTest。
  - com.dwarfeng.dct.impl.handler.ValueCodingQosHandlerImplTest。

- `dwarfeng-dct-api` 子模块类优化注释、文档注释格式、代码换行格式。
  - com.dwarfeng.dct.api.integration.configuration.ServiceExceptionMapperConfiguration。

- `dwarfeng-dct-core` 子模块类优化注释、文档注释格式、代码换行格式。
  - com.dwarfeng.dct.node.configuration.DataCodingConfigDefinitionParser。
  - com.dwarfeng.dct.node.configuration.DataCodingQosDefinitionParser。
  - com.dwarfeng.dct.node.configuration.ValueCodingQosDefinitionParser。
  - com.dwarfeng.dct.node.configuration.ServiceExceptionMapperConfiguration。
  - com.dwarfeng.dct.sdk.util.ServiceExceptionHelperTest。

- 优化文件格式。
  - 优化 `application-context-*.xml` 文件的格式。
  - 优化 `pom.xml` 文件的格式。

### Bug 修复

- 基于 XSD 的 XML 配置修复。
  - 修复 `DataCodingQosDefinitionParser` 中错误的 XML 解析逻辑。
  - 修复 `ValueCodingQosDefinitionParser` 中错误的 XML 解析逻辑。
  - 修复 `META-INF/dwarfeng-dct.xsd` 中错误的标签属性定义。

### 功能移除

- (无)

---

## Release_3.0.1_20260527_build_A

### 功能构建

- Wiki 编写。
  - docs/wiki/zh-CN/UseWithMaven.md。

- 依赖升级。
  - 升级 `subgrade` 依赖版本为 `1.8.3.a` 以规避漏洞。
  - 升级 `spring-telqos` 依赖版本为 `2.0.2.a` 以规避漏洞。

### Bug 修复

- (无)

### 功能移除

- (无)

---

## Release_3.0.0_20260527_build_A

### 功能构建

- Wiki 编写。
  - docs/wiki/zh-CN/UsageGuide.md。

- 更新 README.md。

- Wiki 更新。
  - docs/wiki/zh-CN/QuickStart.md。
  - docs/wiki/zh-CN/Introduction.md。

- 新增 spring-telqos 框架集成指令。
  - com.dwarfeng.dct.api.integration.springtelqos.DataCodingCommand。
  - com.dwarfeng.dct.api.integration.springtelqos.ValueCodingCommand。

- 增加依赖。
  - 增加依赖 `spring-telqos` 以应用其新功能，版本为 `2.0.1.a`。

- 为项目增加 xsd 配置机制。
  - 增加 `META-INF/dwarfeng-dct.xsd` 文件。
  - 增加 `com.dwarfeng.dct.node.configuration.DctNamespaceHandler` 及对应的定义解析器。
  - 调整测试目录的相关配置文件，以使用新的 xsd 配置机制。

- 重构配置机制。
  - 调整 `com.dwarfeng.dct.stack.struct.DataCodingConfig` 的构建期校验策略。
  - 调整 `com.dwarfeng.dct.stack.struct.ValueCodingConfig` 的构建期校验策略。
  - 新增 `com.dwarfeng.dct.stack.util.DataCodingConfigUtil` 校验工具类。
  - 新增 `com.dwarfeng.dct.stack.util.ValueCodingConfigUtil` 校验工具类。
  - 补充配置机制相关单元测试。

- 新增 QoS 服务。
  - com.dwarfeng.dct.stack.service.DataCodingQosService。
  - com.dwarfeng.dct.stack.service.ValueCodingQosService。

- 重构异常机制。
  - 引入数据编码、值编码分层异常体系，并调整扁平数据/值编解码相关异常的继承关系。
  - 新增 `DataCodingExceptionHelper`，统一 Handler 与抽象编解码器中的异常解析与抛出逻辑。
  - 新增 `ValueCodingExceptionHelper`，统一 Handler 与抽象编解码器中的异常解析与抛出逻辑。
  - 调整 `ServiceExceptionCodes` 的服务异常码。
  - 调整 `ServiceExceptionHelper` 的服务异常映射。
  - 补充异常机制相关单元测试。

- 重构项目模块。
  - 新增 `dwarfeng-dct-core` 子模块，并迁移原有代码至该模块。
  - 新增 `dwarfeng-dct-api` 子模块。

- 重构项目结构。
  - 将项目构型更改为 subgrade 稳健式标准构型。

- 优化文件格式。
  - 优化 `application-context-*.xml` 文件的格式。

### Bug 修复

- (无)

### 功能移除

- 移除部分配置检查工具类。
  - com.dwarfeng.dct.sdk.util.FlatDataCodecUtil。
  - com.dwarfeng.dct.sdk.util.ValueCodecUtil。

- 移除 `DctException` 根异常及 `DCT_FAILED` 服务异常码。

---

## 更早的版本

[View all changelogs](./changelogs)
