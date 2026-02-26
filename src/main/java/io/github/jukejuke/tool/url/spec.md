# URL 编解码工具规范

## 1. 功能概述

URL 编解码工具类 `UrlUtils` 提供了 URL 编码和解码的静态方法，用于处理 URL 中的特殊字符，确保 URL 符合 RFC 3986 标准。

### 主要功能：

- URL 编码：将字符串转换为符合 URL 规范的格式，对特殊字符进行编码
- URL 解码：将编码后的 URL 字符串转换回原始字符串
- 支持自定义字符编码，默认使用 UTF-8
- 空值处理：对 null 输入返回 null
- 异常处理：将 UnsupportedEncodingException 转换为 RuntimeException

## 2. 接口定义

### 2.1 编码方法

```java
/**
 * URL 编码
 * 
 * @param value 需要编码的字符串
 * @return 编码后的字符串
 */
public static String encode(String value);

/**
 * URL 编码
 * 
 * @param value   需要编码的字符串
 * @param charset 字符编码
 * @return 编码后的字符串
 */
public static String encode(String value, String charset);
```

### 2.2 解码方法

```java
/**
 * URL 解码
 * 
 * @param value 需要解码的字符串
 * @return 解码后的字符串
 */
public static String decode(String value);

/**
 * URL 解码
 * 
 * @param value   需要解码的字符串
 * @param charset 字符编码
 * @return 解码后的字符串
 */
public static String decode(String value, String charset);
```

## 3. 实现细节

### 3.1 核心实现

- 使用 Java 标准库 `java.net.URLEncoder` 和 `java.net.URLDecoder` 进行编解码
- 默认字符编码为 UTF-8
- 对 null 输入进行特殊处理，直接返回 null
- 捕获 UnsupportedEncodingException 并转换为 RuntimeException，简化调用方的异常处理

### 3.2 关键代码

```java
// 默认字符编码
private static final String DEFAULT_CHARSET = "UTF-8";

// URL 编码实现
public static String encode(String value, String charset) {
    if (value == null) {
        return null;
    }
    try {
        return URLEncoder.encode(value, charset);
    } catch (UnsupportedEncodingException e) {
        throw new RuntimeException("URL encode failed: " + e.getMessage(), e);
    }
}

// URL 解码实现
public static String decode(String value, String charset) {
    if (value == null) {
        return null;
    }
    try {
        return URLDecoder.decode(value, charset);
    } catch (UnsupportedEncodingException e) {
        throw new RuntimeException("URL decode failed: " + e.getMessage(), e);
    }
}
```

## 4. 使用示例

### 4.1 基本用法

```java
// 编码普通字符串
String encoded = UrlUtils.encode("hello world");
// 结果: hello%20world

// 编码包含特殊字符的字符串
String encodedSpecial = UrlUtils.encode("hello+world&test=123");
// 结果: hello%2Bworld%26test%3D123

// 编码中文字符
String encodedChinese = UrlUtils.encode("中文测试");
// 结果: %E4%B8%AD%E6%96%87%E6%B5%8B%E8%AF%95

// 解码字符串
String decoded = UrlUtils.decode("hello%20world");
// 结果: hello world

// 使用指定编码
String encodedGBK = UrlUtils.encode("中文测试", "GBK");
String decodedGBK = UrlUtils.decode(encodedGBK, "GBK");
```

### 4.2 实际应用场景

#### 4.2.1 构建 URL 查询参数

```java
String baseUrl = "https://example.com/api";
String param1 = UrlUtils.encode("value with spaces");
String param2 = UrlUtils.encode("中文参数");
String url = baseUrl + "?param1=" + param1 + "&param2=" + param2;
// 结果: https://example.com/api?param1=value%20with%20spaces&param2=%E4%B8%AD%E6%96%87%E5%8F%82%E6%95%B0
```

#### 4.2.2 处理 URL 路径

```java
String basePath = "https://example.com/files";
String fileName = UrlUtils.encode("文档 (1).pdf");
String fullPath = basePath + "/" + fileName;
// 结果: https://example.com/files/%E6%96%87%E6%A1%A3%20%281%29.pdf
```

## 5. 测试方法

### 5.1 JUnit 测试

使用 `UrlUtilsTest.java` 进行单元测试，测试以下场景：

- 普通字符串的编码和解码
- 包含特殊字符的字符串的编码和解码
- 中文字符的编码和解码
- 空值处理
- 异常处理（无效编码）

### 5.2 手动测试

使用 `UrlUtilsTestMain.java` 进行手动测试，运行 main 方法查看测试结果：

```bash
java -cp "target/classes:target/test-classes" io.github.jukejuke.tool.url.UrlUtilsTestMain
```

## 6. 性能考虑

- 对于频繁的 URL 编解码操作，建议缓存编码结果以提高性能
- 对于大字符串的编解码，可能会占用较多内存，建议合理使用

## 7. 注意事项

- URL 编码仅适用于 URL 的查询参数和路径部分，不适用于整个 URL
- 不同的字符编码会产生不同的编码结果，解码时需要使用相同的编码
- 某些特殊字符（如 `+`）在 URL 中有特殊含义，编码时会被转换为 `%2B`

## 8. 兼容性

- 支持 Java 8 及以上版本
- 与标准 URL 编码规范 RFC 3986 兼容
- 与主流浏览器和服务器的 URL 处理方式兼容
