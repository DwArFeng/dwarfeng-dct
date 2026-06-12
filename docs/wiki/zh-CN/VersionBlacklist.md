# Version Blacklist - 版本黑名单

## 说明

本项目的版本黑名单，列出了本项目的版本黑名单，请注意避免使用这些版本。

列入黑名单的版本，可能是因为以下原因：

- 该版本存在严重的 Bug，可能会导致核心功能无法正常使用。
- 该版本存在严重的 Bug，可能会导致数据丢失、数据错误等严重后果。

## 设计目的

该黑名单旨在帮助开发人员快速识别各版本中已知的问题点，明确受影响的模块与典型触发场景，从而做出基于事实的用/避决策与迁移安排，
而不是简单地“一刀切”禁用某一版本。

## 如何使用

黑名单不是绝对禁用清单。遇到被列入黑名单的版本时，应当基于“问题原因—是否命中—影响面”的原则进行研判：

- 是否直接或间接使用了被提及的模块/类。
- 业务中是否存在与“原因”描述相符的顺序敏感或易触发场景。
- 是否已出现与问题相符的日志告警、数据核对失败或线上异常案例。

若未使用黑名单中提及的问题代码部分，或经过验证不命中触发条件，则仍然可以继续使用该版本；若命中或存在较大不确定性，
建议参考对应编号“详细原因”章节末尾的迁移建议执行升级或规避方案，并充分做回归与灰度验证。

请注意：迁移建议仅针对对应编号的问题，所推荐版本可能仍存在其他风险。请继续核查该推荐版本是否仍在黑名单中；
若仍在黑名单中，请持续研判并迭代升级，直至风险可接受或升级至未被列入黑名单的版本。

## 版本黑名单

| 编号                                           | 大版本   | 起始版本    | 结束版本    | 原因                                                                                     |
|----------------------------------------------|-------|---------|---------|----------------------------------------------------------------------------------------|
| [BLACKLIST-20260612.1](#BLACKLIST-202606121) | 3.0.x | 3.0.0.a | 3.0.1.a | 基于 XSD 的 QoS XML 配置存在错误属性定义、缺省值解析与解析逻辑问题，可能导致缺省 QoS 配置报错、QoS 编解码服务配置异常或可用 Handler 不完整。 |

## 详细原因

### BLACKLIST-20260612.1

原因：在基于 XSD 的 XML 配置机制中，`value-coding-qos` 与 `data-coding-qos` 标签的属性定义、缺省值解析和解析逻辑存在错误。
修复前，`META-INF/dwarfeng-dct.xsd` 使用 `service-name` 属性描述 QoS 服务 Bean 名称，并额外暴露 `handler-ref` 属性；
对应的 `ValueCodingQosDefinitionParser` 与 `DataCodingQosDefinitionParser` 也会读取 `service-name`、`handler-ref`，
并尝试通过单个 Handler 引用手工构造 QoS Handler 的 Handler 映射。
同时，解析器未能正确处理 XSD 中声明的缺省属性值，
导致 `<dct:value-coding-qos/>`、`<dct:data-coding-qos/>` 这类完全依赖 XSD 默认值的配置在解析时可能直接报错。
这与 QoS Handler 通过 Spring 构造器自动装配并聚合全部编解码 Handler Bean 的设计不一致，可能导致 QoS 服务 Bean 注册名称不符合预期，
或 QoS Handler 只包含单个默认 Handler，无法完整暴露应用上下文中的编解码能力。

- 受影响模块/类：
   - `com.dwarfeng.dct.node.configuration.ValueCodingQosDefinitionParser`。
   - `com.dwarfeng.dct.node.configuration.DataCodingQosDefinitionParser`。
   - `META-INF/dwarfeng-dct.xsd`。
- 受影响标签：
   - `dct:value-coding-qos`。
   - `dct:data-coding-qos`。
- 典型触发条件：
   - 使用 `dwarfeng-dct` 命名空间的 XSD XML 配置机制。
   - 在 Spring XML 中声明 `dct:value-coding-qos` 或 `dct:data-coding-qos` 标签。
   - 使用 `<dct:value-coding-qos/>` 或 `<dct:data-coding-qos/>` 这类依靠 XSD 缺省值补全属性的配置。
   - 依赖 QoS 服务 Bean 名称自定义，或依赖 QoS Handler 聚合多个值编码/数据编码 Handler。
- 典型症状：
   - 使用 `<dct:value-coding-qos/>` 或 `<dct:data-coding-qos/>` 的缺省配置时，
     旧版本可能无法正确解析 XSD 默认值并在 Spring 上下文加载阶段报错。
   - 使用 `qos-service-name` 属性配置 QoS 服务 Bean 名称时，旧版本无法按预期解析该属性。
   - QoS Handler 可列出的 Handler 名称不完整，只包含 `handler-ref` 指定的单个 Handler。
   - 通过 QoS 服务进行值编码、值解码、数据编码或数据解码时，指定非默认 Handler 名称可能出现找不到 Handler 的异常。
   - 基于 XML Schema 自动补全或校验时，QoS 标签展示了与实际设计不一致的属性。
- 影响范围：
   - 使用 `3.0.0.a` 或 `3.0.1.a`，并启用 XSD XML 配置机制的应用。
   - 直接或间接使用 `ValueCodingQosService`、`DataCodingQosService` 的应用。
   - 对 QoS 服务 Bean 名称、多个编解码 Handler 聚合能力有依赖的应用。

迁移建议：升级至 `3.0.2.a` 及以上版本。

## 注意事项

- 黑名单用于风险提示，并非强制禁用。是否升级或规避，应以是否命中“详细原因”所述的模块与场景为依据。
- 建议遵循生产变更流程：评估 -> 测试 -> 灰度 -> 观测 -> 全量，过程中启用充分的日志与指标监控。
- 如发现新问题或修复版本，请同步更新本黑名单与变更记录，保持信息一致性。
