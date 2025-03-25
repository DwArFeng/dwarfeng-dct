# ChangeLog

### Release_1.0.1_20250325_build_A

#### 功能构建

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

#### Bug修复

- 修正 test 目录下错误的项目结构。

#### 功能移除

- (无)

---

### Release_1.0.0_20230609_build_A

#### 功能构建

- 项目结构建立，程序清理测试通过。

- 核心机制实现。

- 编写示例代码，运行测试通过。

- 编写 README.md。

#### Bug修复

- (无)

#### 功能移除

- (无)
