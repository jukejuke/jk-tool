# JK Tool

JK Tool 是一个基于 Java 的工具库，主要用于通过高德地图（Amap）和天地图（Tianditu）的地理编码和反地理编码服务，将地址信息与经纬度之间进行转换。该项目适用于需要集成地理编码服务的 Java 应用程序。

## 功能特点

### 地理编码服务
- **高德地图反地理编码**：通过 `AmapRegeoCoder` 类实现，支持将经纬度转换为结构化地址信息。
- **高德地图地理编码**：通过 `AmapGeoCoder` 类实现，支持将地址信息转换为经纬度。
- **高德地图区域查询**：通过 `AmapDistrictQuery` 类实现，支持根据关键字查询区域信息。
- **天地图反地理编码**：通过 `TiandituGeocoder` 类实现，支持将经纬度转换为结构化地址信息。
- **天地图行政区域查询**：通过 `TiandituAdministrative` 类实现，支持查询行政区域信息。

### 通用工具类
- **HTTP请求工具**：通过 `HttpUtil` 类实现，支持GET、POST、POST JSON等常用HTTP请求方法。
- **字符串处理**：通过 `StringUtils` 类实现，提供字符串判空、分割、连接、替换、格式化等常用操作。
- **文件操作**：通过 `FileUtils` 类实现，支持文件的创建、读取、写入、删除以及目录的创建和遍历等操作。
- **配置文件处理**：通过 `PropertiesUtils` 类实现，支持从类路径或文件系统读取、修改和保存properties配置文件。
- **Bean字段过滤**：通过 `BeanFieldFilter` 类实现，支持灵活过滤Java Bean中的字段。
- **Bean属性操作**：通过 `BeanPropertyUtils` 类实现，提供Bean属性的读取和设置工具。
- **日期时间处理**：通过 `DateUtils` 类实现，支持日期格式化、转换和计算功能。
- **网络时间同步**：通过 `InternetTimeUtils` 类实现，支持从互联网时间服务器获取标准时间。
- **Freemarker模板**：通过 `FreemarkerUtils` 类实现，简化模板渲染和数据绑定操作。
- **JWT令牌处理**：通过 `JwtUtils` 类实现，支持JWT令牌的生成、解析和验证功能。

### 加密解密工具
- **安全工具类**：通过 `SecureUtil` 类实现，提供AES/DES对称加密、MD5/SHA哈希算法、HMAC消息认证码、UUID生成等安全相关功能。
- **AES加解密**：通过 `AESUtil` 类实现，提供AES对称加密算法的完整实现，支持多种模式和填充方式。
- **RSA加解密**：通过 `RSAUtil` 类实现，提供RSA非对称加密算法的密钥生成、加密、解密、签名和验证功能。
- **DSA数字签名**：通过 `DSAUtil` 和 `DSAKeyExportImport` 类实现，提供DSA数字签名算法的密钥生成、签名和验证功能。
- **摘要算法**：通过 `DigestUtil` 类实现，支持MD5、SHA-1、SHA-256、SHA-512等常用摘要算法。

### 系统工具类
- **Hosts文件管理**：通过 `HostsFileManager` 类实现，支持Hosts文件的读取、编辑和备份功能。
- **DNS解析工具**：通过 `DnsResolver`、`ProxyDnsResolver`、`DoHQuery`、`DoHWithHttpProxy` 类实现，支持DNS查询和DNS over HTTPS功能。
- **IP地址处理**：通过 `IPAddressResolver`、`ProxyIPAddressResolver` 类实现，支持IP地址解析和代理IP识别。
- **进程管理**：通过 `WindowExeProcessManager` 类实现，支持Windows系统下的进程启动、查询和管理。
- **日志工具**：通过 `LogUtil` 类实现，提供简洁的日志记录功能。

### 许可证管理
- **硬件信息获取**：通过 `HardwareUtils` 类实现，支持获取计算机硬件标识信息。
- **许可证生成**：通过 `LicenseUtils` 类实现，支持生成基于硬件信息的软件许可证。
- **许可证验证**：通过 `LicenseInfo` 类实现，支持验证和解析许可证信息。

## 使用方法

### 添加依赖

请确保 `pom.xml` 文件中包含所需的依赖项，如 `OkHttpClient` 和其他必要的库。

### 初始化反地理编码器

#### 高德地图

```java
AmapRegeoCoder amapRegeoCoder = new AmapRegeoCoder.Builder("your_api_key").build();
AmapResponse response = amapRegeoCoder.reverseGeocode(116.397428, 39.90923);
```

#### 天地图

```java
TiandituGeocoder tiandituGeocoder = new TiandituGeocoder.Builder("your_api_key").build();
TiandituResponse response = tiandituGeocoder.reverseGeocode(116.397428, 39.90923);
```

### 初始化地理编码器（高德地图）

```java
AmapGeoCoder amapGeoCoder = new AmapGeoCoder.Builder("your_api_key").build();
AmapGeoResponse response = amapGeoCoder.geoCode("北京市");
```

### 初始化区域查询器（高德地图）

```java
AmapDistrictQuery districtQuery = new AmapDistrictQuery.Builder("your_api_key").build();
DistrictResponse response = districtQuery.query("北京市");
```

### 初始化行政区域查询器（天地图）

```java
TiandituAdministrative administrative = new TiandituAdministrative.Builder("your_api_key").build();
TiandituAdministrativeResponse response = administrative.queryAdministrative("北京市");
```

### 解析响应数据

根据返回的 `AmapResponse`、`AmapGeoResponse`、`DistrictResponse`、`TiandituResponse` 或 `TiandituAdministrativeResponse` 对象，您可以轻松获取结构化地址或区域信息。

## 项目结构

### 地理编码服务
- `src/main/java/io/github/jukejuke/map/amap/AmapRegeoCoder.java`：高德地图反地理编码实现。
- `src/main/java/io/github/jukejuke/map/amap/AmapGeoCoder.java`：高德地图地理编码实现。
- `src/main/java/io/github/jukejuke/map/amap/AmapDistrictQuery.java`：高德地图区域查询实现。
- `src/main/java/io/github/jukejuke/map/tianditu/TiandituGeocoder.java`：天地图反地理编码实现。
- `src/main/java/io/github/jukejuke/map/tianditu/TiandituAdministrative.java`：天地图行政区域查询实现。

### 通用工具类
- `src/main/java/io/github/jukejuke/tool/bean/BeanFieldFilter.java`：Bean字段过滤工具类。
- `src/main/java/io/github/jukejuke/tool/bean/BeanPropertyUtils.java`：Bean属性操作工具类。
- `src/main/java/io/github/jukejuke/tool/date/DateUtils.java`：日期时间处理工具类。
- `src/main/java/io/github/jukejuke/tool/freemarker/FreemarkerUtils.java`：Freemarker模板引擎工具类。
- `src/main/java/io/github/jukejuke/tool/jwt/JwtUtils.java`：JWT令牌处理工具类。

### 测试用例
- `src/test/java/io/github/jukejuke/map/amap/AmapRegeoCoderTest.java`：高德地图反地理编码的测试用例。
- `src/test/java/io/github/jukejuke/map/amap/AmapGeoCoderTest.java`：高德地图地理编码的测试用例。
- `src/test/java/io/github/jukejuke/map/amap/AmapDistrictQueryTest.java`：高德地图区域查询的测试用例。
- `src/test/java/io/github/jukejuke/map/tianditu/TiandituGeocoderTest.java`：天地图反地理编码的测试用例。
- `src/test/java/io/github/jukejuke/map/tianditu/TiandituAdministrativeTest.java`：天地图行政区域查询的测试用例。
- `src/test/java/io/github/jukejuke/tool/bean/BeanFieldFilterTest.java`：Bean字段过滤工具类测试用例。
- `src/test/java/io/github/jukejuke/tool/bean/BeanPropertyUtilsTest.java`：Bean属性操作工具类测试用例。
- `src/test/java/io/github/jukejuke/tool/freemarker/FreemarkerUtilsTest.java`：Freemarker模板引擎工具类测试用例。
- `src/test/java/io/github/jukejuke/tool/jwt/JwtUtilsTest.java`：JWT令牌处理工具类测试用例。
- `src/test/java/io/github/jukejuke/util/DateUtilsTest.java`：日期时间处理工具类测试用例。

### Maven
在项目的pom.xml的dependencies中加入以下内容:

```xml
<dependency>
    <groupId>io.github.jukejuke</groupId>
    <artifactId>jk-tool</artifactId>
    <version>0.0.2</version>
</dependency>
```

### Gradle
```
implementation 'io.github.jukejuke:jk-tool:0.0.2'
```

### 下载jar

点击以下链接，下载`jk-tool-X.X.X.jar`即可：

- [Maven中央库](https://repo1.maven.org/maven2/io/github/jukejuke/jk-tool/0.0.2/)

## 许可证

本项目遵循 MIT 许可证。有关详细信息，请参阅 [LICENSE](LICENSE) 文件。

## 贡献

欢迎为本项目贡献代码。请遵循以下步骤：

1. Fork 本项目。
2. 创建新分支。
3. 提交 Pull Request。