# ChangeLog

## Release_1.0.5_20261231_build_A

### 功能构建

- 依赖升级。
  - 升级 `log4j2` 依赖版本为 `2.25.3` 以规避漏洞。
  - 升级 `dutil` 依赖版本为 `0.4.0.a-beta` 以规避漏洞。
  - 升级 `subgrade` 依赖版本为 `1.6.2.a` 以规避漏洞。

- 优化开发环境支持。
  - 在 .gitignore 中添加 VSCode 相关文件的忽略规则。
  - 在 .gitignore 中添加 Cursor IDE 相关文件的忽略规则。

### Bug 修复

- (无)

### 功能移除

- (无)

---

## Release_1.0.4_20251012_build_A

### 功能构建

- 优化 `docs/wiki` 目录结构。
  - 将 `docs/wiki/en_US` 目录重命名为 `en-US`，以符合 rfc5646 规范。
  - 将 `docs/wiki/zh_CN` 目录重命名为 `zh-CN`，以符合 rfc5646 规范。
  - 更新 `docs/wiki/README.md` 中的链接指向。
  - 更新 `README.md` 中的链接指向。

- 依赖升级。
  - 升级 `subgrade` 依赖版本为 `1.6.0.a` 以规避漏洞。

### Bug 修复

- (无)

### 功能移除

- (无)

---

## Release_1.0.3_20250805_build_A

### 功能构建

- Wiki 编写。
  - docs/wiki/zh_CN/InstallBySourceCode.md。

- 优化部分文件的格式。
  - CHANGELOG.md。

- 依赖升级。
  - 升级 `commons-lang3` 依赖版本为 `3.18.0` 以规避漏洞。
  - 升级 `subgrade` 依赖版本为 `1.5.11.a` 以规避漏洞。

### Bug 修复

- (无)

### 功能移除

- (无)

---

## Release_1.0.2_20250531_build_A

### 功能构建

- Wiki 编写。
  - docs/wiki/zh_CN/VersionBlacklist.md。

- 依赖升级。
  - 升级 `subgrade` 依赖版本为 `1.5.10.a` 以规避漏洞。

### Bug 修复

- (无)

### 功能移除

- (无)

---

## Release_1.0.1_20250325_build_A

### 功能构建

- 更新 README.md。

- Wiki 编写。
  - 构建 wiki 目录结构。
  - docs/wiki/en_US/Contents.md。
  - docs/wiki/en_US/Introduction.md。
  - docs/wiki/zh_CN/Contents.md。
  - docs/wiki/zh_CN/Introduction.md。

- 日志功能优化。
  - 优化默认日志配置，默认配置仅向控制台输出 `INFO` 级别的日志。
  - 优化日志配置结构，提供 `conf/logging/settings.xml` 配置文件及其不同平台的参考配置文件，以供用户自定义日志配置。

- 依赖升级。
  - 升级 `spring` 依赖版本为 `5.3.39` 以规避漏洞。
  - 升级 `slf4j` 依赖版本为 `1.7.36` 以规避漏洞。
  - 升级 `subgrade` 依赖版本为 `1.5.8.a` 以规避漏洞。

### Bug 修复

- 修正 test 目录下错误的项目结构。

### 功能移除

- (无)

---

## Release_1.0.0_20230609_build_A

### 功能构建

- 项目结构建立，程序清理测试通过。

- 核心机制实现。

- 编写示例代码，运行测试通过。

- 编写 README.md。

### Bug 修复

- (无)

### 功能移除

- (无)
