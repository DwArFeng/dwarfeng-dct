# ChangeLog

## Release_3.0.0_20260517_build_A

### 功能构建

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
