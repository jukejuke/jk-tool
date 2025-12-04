该项目目前没有 README.md 文件。建议创建一个以帮助用户理解项目结构、功能及使用方法。以下是一个适用于当前项目的 README.md 内容示例：

---

# JK Tool

JK Tool 是一个基于 Java 的工具库，主要用于通过高德地图（Amap）和天地图（Tianditu）的反地理编码服务，将经纬度转换为可读地址信息。该项目适用于需要集成地理编码服务的 Java 应用程序。

## 功能特点

- **高德地图反地理编码**：通过 `AmapRegeoCoder` 类实现，支持将经纬度转换为结构化地址信息。
- **天地图反地理编码**：通过 `TiandituGeocoder` 类实现，支持将经纬度转换为结构化地址信息。
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

### 解析响应数据

根据返回的 `AmapResponse` 或 `TiandituResponse` 对象，您可以轻松获取结构化地址信息。

## 项目结构

- `src/main/java/io/github/jukejuke/map/amap/AmapRegeoCoder.java`：高德地图反地理编码实现。
- `src/main/java/io/github/jukejuke/geocoder/tianditu/TiandituGeocoder.java`：天地图反地理编码实现。
- `src/test/java/io/github/jukejuke/map/amap/AmapRegeoCoderTest.java`：高德地图反地理编码的测试用例。
- `src/test/java/io/github/jukejuke/geocoder/tianditu/TiandituGeocoderTest.java`：天地图反地理编码的测试用例。

## 许可证

本项目遵循 MIT 许可证。有关详细信息，请参阅 [LICENSE](LICENSE) 文件。

## 贡献

欢迎为本项目贡献代码。请遵循以下步骤：

1. Fork 本项目。
2. 创建新分支。
3. 提交 Pull Request。

---

通过这个 README.md 文件，用户可以快速了解项目的用途、功能和使用方法。您可以根据需要进一步扩展和优化内容。