# JK Tool

JK Tool 是一个基于 Java 的工具库，主要用于通过高德地图（Amap）和天地图（Tianditu）的地理编码和反地理编码服务，将地址信息与经纬度之间进行转换。该项目适用于需要集成地理编码服务的 Java 应用程序。

## 功能特点

- **高德地图反地理编码**：通过 `AmapRegeoCoder` 类实现，支持将经纬度转换为结构化地址信息。
- **高德地图地理编码**：通过 `AmapGeoCoder` 类实现，支持将地址信息转换为经纬度。
- **高德地图区域查询**：通过 `AmapDistrictQuery` 类实现，支持根据关键字查询区域信息。
- **天地图反地理编码**：通过 `TiandituGeocoder` 类实现，支持将经纬度转换为结构化地址信息。
- **天地图行政区域查询**：通过 `TiandituAdministrative` 类实现，支持查询行政区域信息。
- **可扩展性**：可轻松扩展以支持其他地理编码服务。
- **测试用例**：提供针对 Amap 和 Tianditu 的完整测试用例，确保功能的可靠性。

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

- `src/main/java/io/github/jukejuke/map/amap/AmapRegeoCoder.java`：高德地图反地理编码实现。
- `src/main/java/io/github/jukejuke/map/amap/AmapGeoCoder.java`：高德地图地理编码实现。
- `src/main/java/io/github/jukejuke/map/amap/AmapDistrictQuery.java`：高德地图区域查询实现。
- `src/main/java/io/github/jukejuke/map/tianditu/TiandituGeocoder.java`：天地图反地理编码实现。
- `src/main/java/io/github/jukejuke/map/tianditu/TiandituAdministrative.java`：天地图行政区域查询实现。
- `src/test/java/io/github/jukejuke/map/amap/AmapRegeoCoderTest.java`：高德地图反地理编码的测试用例。
- `src/test/java/io/github/jukejuke/map/amap/AmapGeoCoderTest.java`：高德地图地理编码的测试用例。
- `src/test/java/io/github/jukejuke/map/amap/AmapDistrictQueryTest.java`：高德地图区域查询的测试用例。
- `src/test/java/io/github/jukejuke/map/tianditu/TiandituGeocoderTest.java`：天地图反地理编码的测试用例。
- `src/test/java/io/github/jukejuke/map/tianditu/TiandituAdministrativeTest.java`：天地图行政区域查询的测试用例。

## 安装
### Maven
在项目的pom.xml的dependencies中加入以下内容:

```xml
<dependency>
    <groupId>io.github.jukejuke</groupId>
    <artifactId>jk-tool</artifactId>
    <version>0.0.1</version>
</dependency>
```

### Gradle
```
implementation 'io.github.jukejuke:jk-tool:0.0.1'
```

### 下载jar

点击以下链接，下载`jk-tool-X.X.X.jar`即可：

- [Maven中央库](https://repo1.maven.org/maven2/io/github/jukejuke/jk-tool/0.0.1/)

## 许可证

本项目遵循 MIT 许可证。有关详细信息，请参阅 [LICENSE](LICENSE) 文件。

## 贡献

欢迎为本项目贡献代码。请遵循以下步骤：

1. Fork 本项目。
2. 创建新分支。
3. 提交 Pull Request。