# JK-Tool 项目 Wiki

## 目录

- [项目概述](#项目概述)
- [技术架构](#技术架构)
- [核心功能模块](#核心功能模块)
- [开发环境配置](#开发环境配置)
- [常见问题与解决方案](#常见问题与解决方案)
- [版本历史](#版本历史)

---

## 项目概述

### 项目简介

JK-Tool 是一个功能丰富的 Java 工具类库，提供了地理编码服务、坐标转换、通用工具类、七牛云对象存储工具、加密解密工具、系统工具类和 API 响应封装等功能。该项目设计简洁，使用方便，适用于各种 Java 应用场景。

### 项目信息

- **项目名称**: jk-tool
- **Group ID**: io.github.jukejuke
- **Artifact ID**: jk-tool
- **版本**: 0.0.5
- **许可证**: Apache 2.0
- **作者**: jukejuke (7385697@qq.com)
- **代码仓库**: https://github.com/jukejuke/jk-tool

### 项目目标

1. 提供丰富的工具类，覆盖常用开发场景
2. 提供地理编码服务和坐标转换功能
3. 封装常用的第三方服务（如七牛云存储）
4. 提供统一的 API 响应封装
5. 提供安全的加密解密工具

---

## 技术架构

### 技术栈

| 技术 | 版本 | 用途 |
|------|------|------|
| Java | 11+ | 开发语言 |
| Maven | 3.0+ | 项目管理工具 |
| Lombok | 1.18.42 | 简化代码 |
| Apache POI | 5.3.0 | Excel 处理 |
| Fastjson2 | 2.0.61 | JSON 序列化 |
| OkHttp | 4.12.0 | HTTP 客户端 |
| dnsjava | 3.6.3 | DNS 解析 |
| Nimbus JOSE JWT | 10.6 | JWT 处理 |
| Freemarker | 2.3.34 | 模板引擎 |
| JavaMail | 1.6.2 | 邮件处理 |
| Qiniu Java SDK | 7.15.0 | 七牛云存储 |
| JUnit 5 | 5.9.2 | 测试框架 |
| Mockito | 4.11.0 | 测试模拟框架 |

### 项目结构

```
jk-tool/
├── pom.xml                                  # Maven 配置文件
├── README.md                                # 英文文档
├── README.cn.md                             # 中文文档
├── LICENSE                                  # 许可证文件
├── 使用文档.md                              # 使用文档
├── src/
│   ├── main/
│   │   └── java/
│   │       └── io/
│   │           └── github/
│   │               └── jukejuke/
│   │                   ├── api/             # API 响应封装
│   │                   ├── map/             # 地理编码服务
│   │                   │   ├── amap/        # 高德地图服务
│   │                   │   ├── tianditu/    # 天地图服务
│   │                   │   └── util/        # 坐标转换工具
│   │                   ├── qiniu/           # 七牛云存储工具
│   │                   └── tool/            # 通用工具类
│   │                       ├── annotation/  # 注解工具
│   │                       ├── bean/        # Bean 转换工具
│   │                       ├── config/      # 配置处理工具
│   │                       ├── crypto/      # 加密解密工具
│   │                       ├── date/        # 日期时间工具
│   │                       ├── dns/         # DNS 解析工具
│   │                       ├── excel/       # Excel 处理工具
│   │                       ├── exec/        # 进程管理工具
│   │                       ├── file/        # 文件处理工具
│   │                       ├── freemarker/  # Freemarker 模板工具
│   │                       ├── http/        # HTTP 请求工具
│   │                       ├── id/          # ID 生成工具
│   │                       ├── image/       # 图片处理工具
│   │                       ├── ip/          # IP 地址处理工具
│   │                       ├── jwt/         # JWT 令牌工具
│   │                       ├── license/     # 许可证管理工具
│   │                       ├── log/         # 日志工具
│   │                       ├── mail/        # 邮件工具
│   │                       ├── math/        # 数学计算工具
│   │                       ├── response/    # 响应封装工具
│   │                       ├── string/      # 字符串工具
│   │                       ├── url/         # URL 处理工具
│   │                       └── zip/         # ZIP 压缩工具
│   └── test/
│       └── java/
│           └── io/
│               └── github/
│                   └── jukejuke/           # 测试代码
```

### 设计原则

1. **简洁易用**: 提供简单易用的 API，降低学习成本
2. **功能完整**: 覆盖常用开发场景，提供丰富的功能
3. **可扩展性**: 良好的代码结构，便于扩展新功能
4. **稳定性**: 充分的测试用例，确保代码质量
5. **文档完善**: 提供详细的使用文档和示例代码

---

## 核心功能模块

### 1. 地理编码服务

#### 高德地图服务 (Amap)

提供高德地图的地理编码、反地理编码、区域查询、坐标转换和 POI 搜索功能。

**核心类**:
- `AmapGeoCoder`: 地理编码（地址转经纬度）
- `AmapRegeoCoder`: 反地理编码（经纬度转地址）
- `AmapDistrictQuery`: 区域查询
- `AmapCoordinateConverter`: 坐标转换
- `AmapPoiSearcher`: POI 兴趣点搜索

**使用示例**:
```java
// 初始化地理编码器
AmapGeoCoder geocoder = new AmapGeoCoder.Builder("your_api_key").build();
// 执行地理编码
AmapGeoResponse response = geocoder.geoCode("北京市");
// 解析结果
if (response.isSuccess() && response.getGeocodes() != null) {
    AmapGeoResponse.Geocode geocode = response.getGeocodes().get(0);
    System.out.println("经纬度: " + geocode.getLocation());
}
```

#### 天地图服务 (Tianditu)

提供天地图的地理编码、反地理编码和行政区域查询功能。

**核心类**:
- `TiandituGeocoder`: 地理编码与反地理编码
- `TiandituAdministrative`: 行政区域查询

### 2. 坐标转换工具

提供 WGS-84、GCJ-02、BD-09 坐标系之间的本地坐标转换功能。

**坐标系说明**:
- **WGS-84**: GPS 原始坐标，地球坐标系
- **GCJ-02**: 火星坐标系，中国境内使用的加密坐标
- **BD-09**: 百度坐标系，在 GCJ-02 基础上进一步加密
- **CGCS2000**: 中国国家测绘局 2000 年发布的大地坐标系

**核心类**: `CoordinateConverter`

**使用示例**:
```java
// 创建 WGS-84 坐标点
CoordinateConverter.Point wgs84Point = new CoordinateConverter.Point(116.397428, 39.90923);
// WGS-84 转 GCJ-02
CoordinateConverter.Point gcj02Point = CoordinateConverter.wgs84ToGcj02(wgs84Point);
// GCJ-02 转 BD-09
CoordinateConverter.Point bd09Point = CoordinateConverter.gcj02ToBd09(gcj02Point);
```

### 3. 通用工具类

#### Excel 处理工具

提供 Excel 文件的创建、数据填充和导出功能，支持注解配置、流式处理和大数据量导出。

**核心类**: `ExcelUtils`

**核心注解**: `@ExcelColumn` - 用于配置 Excel 导出的字段信息

**功能特性**:
- 基本导出
- 注解配置导出
- 流式导出（处理大数据集）
- 流式数据获取导出（通过 Supplier 接口）
- 导入功能
- 流式导入（通过 Consumer 接口）

**使用示例**:
```java
// 注解配置导出
List<User> userList = new ArrayList<>();
// 填充数据...
try (FileOutputStream fos = new FileOutputStream("users.xlsx")) {
    ExcelUtils.exportWithAnnotation(userList, "用户列表", fos);
}
```

#### HTTP 请求工具

提供简单易用的 HTTP 请求功能，支持 GET、POST 和 POST JSON 请求。

**核心类**: `HttpUtil`

**使用示例**:
```java
// GET 请求
String response = HttpUtil.get("https://api.example.com/users");
// POST JSON 请求
String json = "{\"name\": \"张三\", \"age\": 25}";
String jsonResponse = HttpUtil.postJson("https://api.example.com/users", json);
```

#### 字符串工具

提供常用的字符串处理功能，包括空值判断、分割、连接、替换、格式化等。

**核心类**: `StringUtils`

#### 日期时间工具

提供日期时间处理功能，包括获取当前时间、格式化、解析、计算、时间差等。

**核心类**: `DateUtils`, `InternetTimeUtils`

#### 文件工具

提供文件和目录操作功能，包括读写、创建、删除、下载等。

**核心类**: `FileUtils`, `DownloadUtil`, `SimpleDownloadUtil`

#### 注解工具

提供注解相关的工具类，用于处理注解的解析和应用。

**核心类**: `AnnotationUtils`

#### Bean 转换工具

提供对象之间的转换功能，支持不同类型对象之间的属性映射。

**核心类**: `BeanConvertUtils`, `BeanUtils`

#### Freemarker 模板工具

提供 Freemarker 模板的渲染功能，用于生成动态内容。

**核心类**: `FreemarkerUtils`

#### ID 生成工具

提供多种 ID 生成策略，包括 UUID、雪花算法、时间戳等。

**核心类**: `IdGenerator`, `DateIdTool`

#### JWT 令牌工具

提供 JWT (JSON Web Token) 的生成和解析功能。

**核心类**: `JwtUtils`

#### 日志工具

提供日志相关的工具类，简化日志的使用。

**核心类**: `LogUtil`

#### 邮件工具

提供邮件发送功能，支持简单邮件、HTML 邮件和带附件的邮件。

**核心类**: `MailUtils`, `MailReaderUtils`, `MailDeleterUtils`

#### URL 处理工具

提供 URL 相关的工具类，用于 URL 的解析、构建和处理。

**核心类**: `UrlUtils`

### 4. 七牛云对象存储工具

提供七牛云对象存储的文件上传、下载、删除、重命名等功能。

**核心类**: `QiniuUtils`, `QiniuConfig`

**使用示例**:
```java
// 初始化配置
QiniuConfig config = new QiniuConfig();
config.setAccessKey("your_access_key");
config.setSecretKey("your_secret_key");
config.setBucket("your_bucket");
config.setDomain("your_domain");
QiniuUtils qiniuUtils = new QiniuUtils(config);
// 上传文件
String url = qiniuUtils.upload("local_file_path", "key");
```

### 5. 加密解密工具

#### AES 加解密

提供 AES 加密和解密功能。

**核心类**: `AESUtil`

#### RSA 加解密

提供 RSA 加密、解密、签名和验签功能。

**核心类**: `RSAUtil`

#### 摘要算法

提供常用的摘要算法，如 MD5、SHA-1、SHA-256、SHA-512。

**核心类**: `DigestUtil`

### 6. 系统工具类

#### Hosts 文件管理

提供 Hosts 文件的读取、修改和备份功能。

**核心类**: `HostsFileManager`

#### DNS 解析工具

提供 DNS 解析和 DNS over HTTPS 查询功能。

**核心类**: `DnsResolver`, `DoHQuery`, `DoHWithHttpProxy`, `ProxyDnsResolver`

#### IP 地址处理

提供 IP 地址解析功能。

**核心类**: `IPAddressResolver`, `ProxyIPAddressResolver`

#### 进程管理

提供进程的启动、管理和监控功能，支持 Windows 和 Linux 平台。

**核心类**: `ProcessManager`, `ProcessManagerFactory`, `WindowExeProcessManager`, `LinuxExeProcessManager`

### 7. 许可证管理

#### 硬件信息获取

提供硬件信息获取功能，包括 CPU ID、硬盘 ID、MAC 地址等。

**核心类**: `HardwareUtils`

#### 许可证生成与验证

提供许可证的生成和验证功能。

**核心类**: `LicenseUtils`, `LicenseInfo`

### 8. API 响应封装

提供统一的 API 响应封装，包含状态码、消息和数据。

**核心类**: `ApiResponse`, `ApiCode`, `PageResponse`, `PageRequest`

**使用示例**:
```java
// 成功响应
ApiResponse<User> response = ApiResponse.success(user);
// 分页响应
PageResponse<User> pageResponse = PageResponse.success(userList, 1, 10, total);
// 失败响应
ApiResponse<Void> failResponse = ApiResponse.fail(ApiCode.INTERNAL_SERVER_ERROR, "操作失败");
```

---

## 开发环境配置

### 环境要求

- **JDK 版本**: 11 或更高版本
- **Maven 版本**: 3.0 或更高版本
- **IDE**: IntelliJ IDEA / Eclipse (建议使用 IntelliJ IDEA)

### 项目导入

#### Maven 导入

在项目的 `pom.xml` 文件中添加以下依赖：

```xml
<dependency>
    <groupId>io.github.jukejuke</groupId>
    <artifactId>jk-tool</artifactId>
    <version>0.0.5</version>
</dependency>
```

#### Gradle 导入

在项目的 `build.gradle` 文件中添加以下依赖：

```gradle
implementation 'io.github.jukejuke:jk-tool:0.0.5'
```

### 从源码构建

1. 克隆仓库
```bash
git clone https://github.com/jukejuke/jk-tool.git
```

2. 进入项目目录
```bash
cd jk-tool
```

3. 使用 Maven 构建
```bash
mvn clean install
```

### 开发规范

1. **Java 版本**: 使用 Java 11 语法
2. **日志**: 使用 Lombok 的 `@Slf4j` 注解
3. **异常信息**: 使用中文
4. **注释**: 添加必要的函数级注释
5. **代码风格**: 遵循阿里巴巴 Java 开发手册

---

## 常见问题与解决方案

### 地理编码服务

**问题**: 调用地理编码服务时返回错误信息

**解决方案**:
1. 检查 API 密钥是否正确
2. 检查网络连接是否正常
3. 检查请求参数是否符合 API 要求
4. 查看 API 文档，了解错误码含义

### Excel 工具

**问题**: 导出大量数据时内存溢出

**解决方案**: 使用流式导出方法 `exportWithAnnotationStreaming` 或 `exportWithStream`，这些方法使用 SXSSFWorkbook，会将数据写入临时文件，避免内存溢出。

### HTTP 工具

**问题**: HTTP 请求超时

**解决方案**:
1. 检查网络连接是否正常
2. 检查目标服务器是否可访问
3. 考虑增加超时时间（目前默认 15 秒）

### 七牛云工具

**问题**: 文件上传失败

**解决方案**:
1. 检查七牛云配置是否正确（Access Key、Secret Key、Bucket 等）
2. 检查网络连接是否正常
3. 检查文件是否存在且可读取
4. 查看七牛云文档，了解错误码含义

---

## 版本历史

### 0.0.5
- 当前版本
- 修复了若干已知问题
- 优化了部分功能实现

### 0.0.4
- 优化了代码结构
- 修复了若干 Bug

### 0.0.3
- 添加了新的工具类
- 增强了现有功能

### 0.0.2
- 优化了性能
- 完善了文档

### 0.0.1
- 初始版本
- 提供了基本的工具类功能

---

## 联系方式

- **项目地址**: https://github.com/jukejuke/jk-tool
- **作者**: jukejuke
- **邮箱**: 7385697@qq.com

---

## 贡献

欢迎为本项目贡献代码。请遵循以下步骤：

1. Fork 本项目
2. 创建新分支 (`git checkout -b feature/AmazingFeature`)
3. 提交更改 (`git commit -m 'Add some AmazingFeature'`)
4. 推送到分支 (`git push origin feature/AmazingFeature`)
5. 创建新的 Pull Request

---

## 许可证

本项目遵循 Apache 2.0 许可证。有关详细信息，请参阅 LICENSE 文件。
