# ChangeLog

## Release_2.0.1_20260424_build_A

### 功能构建

- (无)

### Bug 修复

- (无)

### 功能移除

- (无)

---

## Release_2.0.0_20260424_build_A

### 功能构建

- Wiki 更新。
  - docs/wiki/zh-CN/FlatDataMechanism.md。
  - docs/wiki/zh-CN/GeneralDataMechanism.md。
  - docs/wiki/zh-CN/QuickStart.md。

- 新增工具类。
  - com.dwarfeng.dct.util.CompareUtil。

- 增强数据时间精度支持。
  - 增加 `com.dwarfeng.dct.bean.dto.FastJsonFlatData.happenedDateNanoOffset` 字段。
  - 增加 `com.dwarfeng.dct.bean.dto.FlatData.happenedDateNanoOffset` 字段。
  - 增加 `com.dwarfeng.dct.bean.dto.GeneralData.happenedDateNanoOffset` 字段。
  - 增加 `com.dwarfeng.dct.struct.Data.getHappenedDateNanoOffset()` 接口方法。
  - 新增 `com.dwarfeng.dct.util.DataUtil` 工具类，为相应的对象提供时间操作工具。
  - 新增 `com.dwarfeng.dct.util.FlatDataUtil` 工具类，为相应的对象提供时间操作工具。
  - 新增 `com.dwarfeng.dct.util.GeneralDataUtil` 工具类，为相应的对象提供时间操作工具。
  - 调整相关处理器的处理逻辑，以支持纳秒偏移量的传递。

- 依赖升级。
  - 升级 `log4j2` 依赖版本为 `2.25.4` 以规避漏洞。
  - 升级 `dutil` 依赖版本为 `0.4.2.a-beta` 以规避漏洞。
  - 升级 `subgrade` 依赖版本为 `1.8.2.a` 以规避漏洞。

- 优化开发环境支持。
  - 在 .gitignore 中添加 Vibe Coding 相关文件的忽略规则。

### Bug 修复

- (无)

### 功能移除

- (无)

---

## 更早的版本

[View all changelogs](./changelogs)
