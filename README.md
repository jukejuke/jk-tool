# JK Tool

JK Tool is a Java-based utility library that provides rich functional modules, including geocoding services, coordinate conversion, general utilities, Qiniu Cloud object storage tools, encryption and decryption tools, system utilities, and API response wrappers. This project is suitable for Java applications that need to integrate various tool functions.

## Quick Start

### Requirements

- **JDK 11 or higher**
- **Maven 3.0+**

### Adding Dependencies

#### Maven

Add the following dependency to your project's `pom.xml`:

```xml
<dependency>
    <groupId>io.github.jukejuke</groupId>
    <artifactId>jk-tool</artifactId>
    <version>0.0.4</version>
</dependency>
```

#### Gradle

```gradle
implementation 'io.github.jukejuke:jk-tool:0.0.4'
```

#### Direct Download

Click the link below to download `jk-tool-X.X.X.jar`:

- [Maven Central Repository](https://repo1.maven.org/maven2/io/github/jukejuke/jk-tool/0.0.4/)

## Core Feature Modules

### Geocoding Service

#### Amap Service

##### Reverse Geocoding

Convert latitude and longitude to structured address information:

```java
// Initialize reverse geocoder
AmapRegeoCoder amapRegeoCoder = new AmapRegeoCoder.Builder("your_api_key").build();

// Execute reverse geocoding
AmapResponse response = amapRegeoCoder.reverseGeocode(116.397428, 39.90923);

// Parse response result
if (response.isSuccess()) {
    System.out.println("Address: " + response.getAddress());
    System.out.println("Province: " + response.getProvince());
    System.out.println("City: " + response.getCity());
    System.out.println("District: " + response.getDistrict());
    System.out.println("Street: " + response.getStreet());
    System.out.println("Number: " + response.getNumber());
}
```

##### Geocoding

Convert address information to latitude and longitude:

```java
// Initialize geocoder
AmapGeoCoder amapGeoCoder = new AmapGeoCoder.Builder("your_api_key").build();

// Execute geocoding
AmapGeoResponse response = amapGeoCoder.geoCode("Beijing");

// Parse response result
if (response.isSuccess() && response.getGeocodes() != null && !response.getGeocodes().isEmpty()) {
    AmapGeoResponse.Geocode geocode = response.getGeocodes().get(0);
    System.out.println("Longitude: " + geocode.getLocation().split(",")[0]);
    System.out.println("Latitude: " + geocode.getLocation().split(",")[1]);
    System.out.println("Formatted Address: " + geocode.getFormattedAddress());
}
```

##### District Query

Query district information by keyword:

```java
// Initialize district query
AmapDistrictQuery districtQuery = new AmapDistrictQuery.Builder("your_api_key").build();

// Execute district query
DistrictResponse response = districtQuery.query("Beijing");

// Parse response result
if (response.isSuccess() && response.getDistricts() != null && !response.getDistricts().isEmpty()) {
    for (DistrictResponse.District district : response.getDistricts()) {
        System.out.println("District Name: " + district.getName());
        System.out.println("District Code: " + district.getAdcode());
        System.out.println("Center Point: " + district.getCenter());
    }
}
```

##### Coordinate Conversion

Coordinate conversion via API:

```java
// Initialize coordinate converter
AmapCoordinateConverter converter = new AmapCoordinateConverter.Builder("your_api_key").build();

// Execute coordinate conversion (WGS84 to GCJ02)
CoordinateConverterResponse response = converter.convert(116.397428, 39.90923, "wgs84", "gcj02");

// Parse response result
if (response.isSuccess()) {
    System.out.println("Converted Longitude: " + response.getLocations().get(0).getLongitude());
    System.out.println("Converted Latitude: " + response.getLocations().get(0).getLatitude());
}
```

##### POI Search

Search for points of interest by keywords, categories, regions, and other conditions:

```java
// Initialize POI searcher
AmapPoiSearcher poiSearcher = new AmapPoiSearcher.Builder("your_api_key").build();

// Build search parameters
AmapPoiSearcher.SearchParams params = new AmapPoiSearcher.SearchParams();
params.setKeywords("restaurant");
params.setCity("Beijing");
params.setPageSize(20);
params.setPageNum(1);

// Execute POI search
AmapPoiResponse response = poiSearcher.search(params);

// Parse response result
if (response.isSuccess() && response.getPois() != null && !response.getPois().isEmpty()) {
    for (AmapPoiResponse.Poi poi : response.getPois()) {
        System.out.println("Name: " + poi.getName());
        System.out.println("Address: " + poi.getAddress());
        System.out.println("Coordinates: " + poi.getLocation());
        System.out.println("Phone: " + poi.getTel());
    }
}
```

#### Tianditu Service

##### Geocoding and Reverse Geocoding

```java
// Initialize Tianditu geocoder
TiandituGeocoder tiandituGeocoder = new TiandituGeocoder.Builder("your_api_key").build();

// Execute reverse geocoding
TiandituResponse response = tiandituGeocoder.reverseGeocode(116.397428, 39.90923);

// Parse response result
if (response.isSuccess()) {
    System.out.println("Address: " + response.getFormattedAddress());
    System.out.println("Province: " + response.getProvince());
    System.out.println("City: " + response.getCity());
    System.out.println("District: " + response.getDistrict());
}

// Execute geocoding
TiandituGeoResponse geoResponse = tiandituGeocoder.geoCode("Beijing");

// Parse response result
if (geoResponse.isSuccess() && geoResponse.getLocations() != null && !geoResponse.getLocations().isEmpty()) {
    TiandituGeoResponse.Location location = geoResponse.getLocations().get(0);
    System.out.println("Longitude: " + location.getLongitude());
    System.out.println("Latitude: " + location.getLatitude());
}
```

##### Administrative Region Query

```java
// Initialize administrative region query
TiandituAdministrative administrative = new TiandituAdministrative.Builder("your_api_key").build();

// Execute administrative region query
TiandituAdministrativeResponse response = administrative.queryAdministrative("Beijing");

// Parse response result
if (response.isSuccess() && response.getAdministratives() != null && !response.getAdministratives().isEmpty()) {
    for (TiandituAdministrativeResponse.Administrative admin : response.getAdministratives()) {
        System.out.println("Name: " + admin.getName());
        System.out.println("Level: " + admin.getLevel());
        System.out.println("Code: " + admin.getCode());
    }
}
```

### Coordinate Conversion Utility

Local coordinate conversion, supporting conversion between WGS-84, GCJ-02, and BD-09 coordinate systems:

```java
// Create coordinate point
CoordinateConverter.Point wgs84Point = new CoordinateConverter.Point(116.397428, 39.90923); // Tiananmen, Beijing

// WGS-84 to GCJ-02
CoordinateConverter.Point gcj02Point = CoordinateConverter.wgs84ToGcj02(wgs84Point);
System.out.println("GCJ-02 Coordinates: " + gcj02Point);

// GCJ-02 to BD-09
CoordinateConverter.Point bd09Point = CoordinateConverter.gcj02ToBd09(gcj02Point);
System.out.println("BD-09 Coordinates: " + bd09Point);

// BD-09 to GCJ-02
CoordinateConverter.Point backToGcj02 = CoordinateConverter.bd09ToGcj02(bd09Point);
System.out.println("Back to GCJ-02: " + backToGcj02);

// GCJ-02 to WGS-84
CoordinateConverter.Point backToWgs84 = CoordinateConverter.gcj02ToWgs84(backToGcj02);
System.out.println("Back to WGS-84: " + backToWgs84);
```

### General Utilities

#### Excel Utility

##### Basic Export

```java
// Prepare data
List<User> userList = new ArrayList<>();
userList.add(new User(1, "Zhang San", 25, "zhangsan@example.com"));
userList.add(new User(2, "Li Si", 30, "lisi@example.com"));

// Define headers
Map<String, String> headers = new HashMap<>();
headers.put("id", "ID");
headers.put("name", "Name");
headers.put("age", "Age");
headers.put("email", "Email");

// Export Excel
try (FileOutputStream fos = new FileOutputStream("users.xlsx")) {
    ExcelUtils.export(userList, headers, "User List", fos);
    System.out.println("Excel exported successfully");
} catch (Exception e) {
    e.printStackTrace();
}
```

##### Annotation Configuration Export

First, use the `@ExcelColumn` annotation on your entity class:

```java
public class User {
    @ExcelColumn(name = "ID", order = 1, width = 10)
    private Integer id;
    
    @ExcelColumn(name = "Name", order = 2, width = 20)
    private String name;
    
    @ExcelColumn(name = "Age", order = 3, width = 10, alignment = ExcelAlignment.CENTER)
    private Integer age;
    
    @ExcelColumn(name = "Email", order = 4, width = 30)
    private String email;
    
    // Constructor, getters and setters
}
```

Then, export using annotations:

```java
// Prepare data
List<User> userList = new ArrayList<>();
userList.add(new User(1, "Zhang San", 25, "zhangsan@example.com"));
userList.add(new User(2, "Li Si", 30, "lisi@example.com"));

// Export Excel
try (FileOutputStream fos = new FileOutputStream("users_annotation.xlsx")) {
    ExcelUtils.exportWithAnnotation(userList, "User List", fos);
    System.out.println("Excel exported successfully");
} catch (Exception e) {
    e.printStackTrace();
}
```

##### Streaming Export

Suitable for processing large datasets to avoid memory overflow:

```java
// Prepare large dataset
List<User> userList = new ArrayList<>();
for (int i = 1; i <= 10000; i++) {
    userList.add(new User(i, "User" + i, 20 + i % 30, "user" + i + "@example.com"));
}

// Streaming export Excel
try (FileOutputStream fos = new FileOutputStream("users_streaming.xlsx")) {
    ExcelUtils.exportWithAnnotationStreaming(userList, "User List", fos);
    System.out.println("Excel streaming export successful");
} catch (Exception e) {
    e.printStackTrace();
}
```

##### Streaming Data Acquisition Export

Stream data acquisition and export via Supplier interface:

```java
// Simulate data supplier
AtomicInteger counter = new AtomicInteger(0);
Supplier<User> dataSupplier = () -> {
    int id = counter.incrementAndGet();
    if (id > 10000) {
        return null; // End of data
    }
    return new User(id, "User" + id, 20 + id % 30, "user" + id + "@example.com");
};

// Streaming data acquisition export
try (FileOutputStream fos = new FileOutputStream("users_supplier.xlsx")) {
    ExcelUtils.exportWithStream(fos, dataSupplier);
    System.out.println("Excel streaming data acquisition export successful");
} catch (Exception e) {
    e.printStackTrace();
}
```

##### Import Function

```java
// Import Excel
try (FileInputStream fis = new FileInputStream("users.xlsx")) {
    List<User> userList = ExcelUtils.importFromExcel(fis, User.class);
    System.out.println("Excel imported successfully, total " + userList.size() + " records");
    for (User user : userList) {
        System.out.println(user);
    }
} catch (Exception e) {
    e.printStackTrace();
}
```

##### Streaming Import

Process Excel data via Consumer interface:

```java
// Define data consumer
List<User> importedUsers = new ArrayList<>();
Consumer<User> consumer = user -> {
    importedUsers.add(user);
    System.out.println("Processing user: " + user);
};

// Streaming import Excel
try (FileInputStream fis = new FileInputStream("users.xlsx")) {
    ExcelUtils.importWithStream(fis, User.class, consumer);
    System.out.println("Excel streaming import successful, total " + importedUsers.size() + " records processed");
} catch (Exception e) {
    e.printStackTrace();
}
```

#### HTTP Utility

##### GET Request

```java
// Send GET request (no parameters)
String response = HttpUtil.get("https://api.example.com/users");
System.out.println("Response content: " + response);

// Send GET request (with parameters)
Map<String, Object> params = new HashMap<>();
params.put("page", 1);
params.put("size", 10);
String responseWithParams = HttpUtil.get("https://api.example.com/users", params);
System.out.println("Response with parameters: " + responseWithParams);

// Send GET request (with parameters, custom charset)
String responseWithCharset = HttpUtil.get("https://api.example.com/users", params, "GBK");
System.out.println("Response with custom charset: " + responseWithCharset);
```

##### POST Request

```java
// Send POST request (with parameters)
Map<String, Object> postParams = new HashMap<>();
postParams.put("name", "Zhang San");
postParams.put("age", 25);
String postResponse = HttpUtil.post("https://api.example.com/users", postParams);
System.out.println("POST response content: " + postResponse);
```

##### POST JSON Request

```java
// Send POST JSON request
String json = "{\"name\": \"Zhang San\", \"age\": 25}";
String jsonResponse = HttpUtil.postJson("https://api.example.com/users", json);
System.out.println("POST JSON response content: " + jsonResponse);
```

#### String Utility

```java
// String null check
String str = "test";
boolean isEmpty = StringUtils.isEmpty(str);
boolean isBlank = StringUtils.isBlank(str);

// String split
String[] parts = StringUtils.split("a,b,c", ",");

// String join
String joined = StringUtils.join(Arrays.asList("a", "b", "c"), ",");

// String replace
String replaced = StringUtils.replace("Hello World", "World", "Java");

// String format
String formatted = StringUtils.format("Hello, {}!", "World");
```

#### Date and Time Utility

```java
// Get current time
Date now = DateUtils.now();

// Date formatting
String formattedDate = DateUtils.format(now, "yyyy-MM-dd HH:mm:ss");

// String to date
Date date = DateUtils.parse("2023-01-01 12:00:00", "yyyy-MM-dd HH:mm:ss");

// Date calculation
Date nextDay = DateUtils.addDays(now, 1);
Date lastMonth = DateUtils.addMonths(now, -1);

// Calculate time difference
long days = DateUtils.diffDays(now, nextDay);

// Get internet standard time
Date internetTime = InternetTimeUtils.getInternetTime();
```

#### File Utility

```java
// File operations
File file = new File("test.txt");
FileUtils.write(file, "Hello World");
String content = FileUtils.read(file);

// Directory operations
File dir = new File("test");
FileUtils.mkdirs(dir);
List<File> files = FileUtils.listFiles(dir);

// Download file
String url = "https://example.com/file.zip";
String savePath = "D:/downloads/file.zip";
DownloadUtil.download(url, savePath);
```

#### Annotation Utility

Provides annotation-related utilities for parsing and applying annotations:

```java
// Scan all classes in specified package
Set<Class<?>> classes = AnnotationUtils.scanPackages("com.example");
System.out.println("Number of classes scanned: " + classes.size());

// Get specified annotation on class
MyAnnotation annotation = AnnotationUtils.getAnnotation(MyClass.class, MyAnnotation.class);
if (annotation != null) {
    System.out.println("Annotation value: " + annotation.value());
}

// Get specified annotation on method
Method method = MyClass.class.getMethod("method");
MyAnnotation methodAnnotation = AnnotationUtils.getAnnotation(method, MyAnnotation.class);
if (methodAnnotation != null) {
    System.out.println("Method annotation value: " + methodAnnotation.value());
}
```

#### Bean Conversion Utility

Provides conversion functionality between objects, supporting property mapping between different types of objects:

```java
// Create source object
SourceBean source = new SourceBean();
source.setId(1);
source.setName("Zhang San");
source.setAge(25);

// Convert to target object
TargetBean target = BeanConverter.convert(source, TargetBean.class);
System.out.println("Converted object: " + target);

// Conversion with mapping rules
Map<String, String> mapping = new HashMap<>();
mapping.put("name", "userName"); // Source property name -> Target property name
TargetBean mappedTarget = BeanConverter.convert(source, TargetBean.class, mapping);
System.out.println("Conversion result with mapping: " + mappedTarget);

// Batch conversion
List<SourceBean> sourceList = new ArrayList<>();
sourceList.add(source);
List<TargetBean> targetList = BeanConverter.convertList(sourceList, TargetBean.class);
System.out.println("Number of batch conversion results: " + targetList.size());
```

#### Configuration File Processing

Provides configuration file reading and parsing functionality, supporting formats such as Properties, YAML, and JSON:

```java
// Read Properties configuration file
Properties props = ConfigUtils.readProperties("config.properties");
String value = props.getProperty("key");
System.out.println("Properties configuration value: " + value);

// Read YAML configuration file
Map<String, Object> yamlConfig = ConfigUtils.readYaml("config.yaml");
String yamlValue = (String) yamlConfig.get("key");
System.out.println("YAML configuration value: " + yamlValue);

// Read JSON configuration file
Map<String, Object> jsonConfig = ConfigUtils.readJson("config.json");
String jsonValue = (String) jsonConfig.get("key");
System.out.println("JSON configuration value: " + jsonValue);

// Map configuration to object
AppConfig appConfig = ConfigUtils.mapToObject(yamlConfig, AppConfig.class);
System.out.println("Configuration object: " + appConfig);
```

#### Freemarker Template

Provides Freemarker template rendering functionality for generating dynamic content:

```java
// Get default configuration
Configuration defaultConfig = FreemarkerUtils.getDefaultConfiguration();
System.out.println("Default encoding: " + defaultConfig.getDefaultEncoding());

// Create custom configuration
Properties properties = new Properties();
properties.setProperty("template_update_delay", "1000");
properties.setProperty("default_encoding", "UTF-8");
Configuration customConfig = FreemarkerUtils.createConfiguration(properties);

// Create template data
Map<String, Object> data = new HashMap<>();
data.put("name", "Zhang San");
data.put("age", 30);
data.put("city", "Beijing");

// Render string template
String templateContent = "Hello, ${name}! You are ${age} years old and from ${city}.";
String result = FreemarkerUtils.renderTemplate(templateContent, data);
System.out.println("String template rendering result: " + result);

// Render string template with custom configuration
String customResult = FreemarkerUtils.renderTemplate(templateContent, data, customConfig);
System.out.println("Custom configuration rendering result: " + customResult);

// Render file template
String templatePath = "path/to/template.ftl";
String fileResult = FreemarkerUtils.renderFileTemplate(templatePath, data);
System.out.println("File template rendering result: " + fileResult);

// Render file template with custom configuration
String customFileResult = FreemarkerUtils.renderFileTemplate(templatePath, data, customConfig);
System.out.println("Custom configuration file template rendering result: " + customFileResult);

// Render string template to file
String outputPath = "path/to/output.txt";
FreemarkerUtils.renderTemplateToFile(templateContent, data, outputPath);
System.out.println("String template rendered to file successfully");

// Render file template to file
FreemarkerUtils.renderFileTemplateToFile(templatePath, data, outputPath);
System.out.println("File template rendered to file successfully");

// Render file template to output stream
ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
FreemarkerUtils.renderFileTemplateToStream(templatePath, data, outputStream);
String streamResult = outputStream.toString(FreemarkerUtils.DEFAULT_ENCODING);
System.out.println("File template rendered to output stream result: " + streamResult);
```

#### ID Generation

Provides various ID generation strategies, including UUID, Snowflake algorithm, timestamp, etc.:

```java
// Generate UUID
String uuid = IdGenerator.uuid();
System.out.println("UUID: " + uuid);

// Generate simplified UUID (without hyphens)
String simpleUuid = IdGenerator.simpleUuid();
System.out.println("Simplified UUID: " + simpleUuid);

// Generate ID using Snowflake algorithm
long snowflakeId = IdGenerator.snowflake();
System.out.println("Snowflake ID: " + snowflakeId);

// Generate timestamp-based ID
String timestampId = IdGenerator.timestamp();
System.out.println("Timestamp ID: " + timestampId);

// Generate prefixed ID
String prefixedId = IdGenerator.prefixed("USER");
System.out.println("Prefixed ID: " + prefixedId);
```

#### JWT Token

Provides JWT (JSON Web Token) generation and parsing functionality:

```java
// Generate JWT token
String secretKey = "your_secret_key";
Map<String, Object> claims = new HashMap<>();
claims.put("userId", 123);
claims.put("username", "Zhang San");
claims.put("roles", Arrays.asList("admin", "user"));

String token = JwtUtils.generateToken(claims, secretKey, 3600); // 1 hour validity
System.out.println("Generated JWT token: " + token);

// Parse JWT token
Map<String, Object> parsedClaims = JwtUtils.parseToken(token, secretKey);
System.out.println("Parsed user ID: " + parsedClaims.get("userId"));
System.out.println("Parsed username: " + parsedClaims.get("username"));

// Validate JWT token
boolean valid = JwtUtils.validateToken(token, secretKey);
System.out.println("Is token valid: " + valid);

// Get token expiration time
Date expiration = JwtUtils.getExpiration(token, secretKey);
System.out.println("Token expiration time: " + expiration);
```

#### Logging Utility

Provides logging-related utilities to simplify logging usage:

```java
// Get logger
Logger logger = LogUtils.getLogger(MyClass.class);

// Log different levels
logger.debug("Debug information");
logger.info("Information log");
logger.warn("Warning log");
logger.error("Error log");

// Log with parameters
logger.info("User {} logged in successfully, IP address: {}", "Zhang San", "192.168.1.1");

// Log exception
try {
    // Business logic
} catch (Exception e) {
    logger.error("Operation failed", e);
}

// Use @Slf4j annotation (recommended)
// After adding @Slf4j annotation to the class, you can directly use the log variable
// log.info("Logging with @Slf4j annotation");
```

#### Email Utility

Provides email sending functionality, supporting simple emails, HTML emails, and emails with attachments:

```java
// Initialize email configuration
MailConfig config = new MailConfig();
config.setHost("smtp.example.com");
config.setPort(587);
config.setUsername("your_email@example.com");
config.setPassword("your_password");
config.setFrom("your_email@example.com");

// Create email sender
MailSender sender = new MailSender(config);

// Send simple email
MailMessage message = new MailMessage();
message.setTo(Arrays.asList("recipient@example.com"));
message.setSubject("Test Email");
message.setText("This is a test email");
sender.send(message);
System.out.println("Simple email sent successfully");

// Send HTML email
MailMessage htmlMessage = new MailMessage();
htmlMessage.setTo(Arrays.asList("recipient@example.com"));
htmlMessage.setSubject("HTML Test Email");
htmlMessage.setHtml("<h1>Test Email</h1><p>This is an HTML format test email</p>");
sender.send(htmlMessage);
System.out.println("HTML email sent successfully");

// Send email with attachment
MailMessage attachmentMessage = new MailMessage();
attachmentMessage.setTo(Arrays.asList("recipient@example.com"));
attachmentMessage.setSubject("Test Email with Attachment");
attachmentMessage.setText("This is a test email with an attachment");
attachmentMessage.addAttachment("test.txt", new File("test.txt"));
sender.send(attachmentMessage);
System.out.println("Email with attachment sent successfully");
```

#### URL Processing

Provides URL-related utilities for URL parsing, construction, and processing:

```java
// URL encode
String original = "https://example.com/path?name=Zhang San&age=25";
String encoded = UrlUtils.encode(original);
System.out.println("Encoded URL: " + encoded);

// URL decode
String decoded = UrlUtils.decode(encoded);
System.out.println("Decoded URL: " + decoded);

// Build URL
UrlBuilder builder = new UrlBuilder("https://example.com");
builder.addPath("api")
       .addPath("users")
       .addParam("page", "1")
       .addParam("size", "10");
String builtUrl = builder.build();
System.out.println("Built URL: " + builtUrl);

// Parse URL
UrlParser parser = new UrlParser("https://example.com/api/users?page=1&size=10");
System.out.println("Scheme: " + parser.getScheme());
System.out.println("Host: " + parser.getHost());
System.out.println("Path: " + parser.getPath());
System.out.println("Page parameter: " + parser.getParam("page"));

// Get file extension from URL
String extension = UrlUtils.getFileExtension("https://example.com/image.jpg");
System.out.println("File extension: " + extension);
```

### Qiniu Cloud Object Storage Utility

#### Initialize Configuration

```java
// Initialize Qiniu Cloud configuration
QiniuConfig config = new QiniuConfig();
config.setAccessKey("your_access_key");
config.setSecretKey("your_secret_key");
config.setBucket("your_bucket");
config.setDomain("your_domain");
config.setZone(QiniuConfig.Zone.HUADONG); // Set storage zone

// Initialize Qiniu Cloud utility
QiniuUtils qiniuUtils = new QiniuUtils(config);
```

#### File Upload

```java
// Upload from local file
String localFilePath = "D:/upload/test.jpg";
String key = "test.jpg";
String url = qiniuUtils.upload(localFilePath, key);
System.out.println("Upload successful, access URL: " + url);

// Upload from byte array
byte[] data = "Hello Qiniu".getBytes();
String byteKey = "hello.txt";
String byteUrl = qiniuUtils.upload(data, byteKey);
System.out.println("Byte array upload successful, access URL: " + byteUrl);

// Upload from input stream
try (InputStream inputStream = new FileInputStream("D:/upload/test.jpg")) {
    String streamKey = "stream.jpg";
    String streamUrl = qiniuUtils.upload(inputStream, streamKey);
    System.out.println("Input stream upload successful, access URL: " + streamUrl);
} catch (Exception e) {
    e.printStackTrace();
}
```

#### File Download

```java
// Download file
String key = "test.jpg";
String savePath = "D:/downloads/test.jpg";
qiniuUtils.download(key, savePath);
System.out.println("File download successful");
```

#### File Delete

```java
// Delete file
String key = "test.jpg";
boolean success = qiniuUtils.delete(key);
System.out.println("File deletion " + (success ? "successful" : "failed"));
```

#### File Rename

```java
// Rename file
String oldKey = "test.jpg";
String newKey = "new_test.jpg";
boolean success = qiniuUtils.rename(oldKey, newKey);
System.out.println("File rename " + (success ? "successful" : "failed"));
```

### Encryption and Decryption Utilities

#### AES Encryption and Decryption

```java
// Generate key
String key = AESUtil.generateKey();
System.out.println("Generated key: " + key);

// Encrypt
String plaintext = "Hello AES";
String ciphertext = AESUtil.encrypt(plaintext, key);
System.out.println("Encrypted: " + ciphertext);

// Decrypt
String decrypted = AESUtil.decrypt(ciphertext, key);
System.out.println("Decrypted: " + decrypted);
```

#### RSA Encryption and Decryption

```java
// Generate key pair
RSAUtil.KeyPair keyPair = RSAUtil.generateKeyPair();
System.out.println("Public key: " + keyPair.getPublicKey());
System.out.println("Private key: " + keyPair.getPrivateKey());

// Encrypt
String plaintext = "Hello RSA";
String ciphertext = RSAUtil.encrypt(plaintext, keyPair.getPublicKey());
System.out.println("Encrypted: " + ciphertext);

// Decrypt
String decrypted = RSAUtil.decrypt(ciphertext, keyPair.getPrivateKey());
System.out.println("Decrypted: " + decrypted);

// Sign
String signature = RSAUtil.sign(plaintext, keyPair.getPrivateKey());
System.out.println("Signature: " + signature);

// Verify signature
boolean verified = RSAUtil.verify(plaintext, signature, keyPair.getPublicKey());
System.out.println("Signature verification " + (verified ? "successful" : "failed"));
```

#### Digest Algorithm

```java
// MD5
String md5 = DigestUtil.md5("Hello MD5");
System.out.println("MD5: " + md5);

// SHA-1
String sha1 = DigestUtil.sha1("Hello SHA1");
System.out.println("SHA-1: " + sha1);

// SHA-256
String sha256 = DigestUtil.sha256("Hello SHA256");
System.out.println("SHA-256: " + sha256);

// SHA-512
String sha512 = DigestUtil.sha512("Hello SHA512");
System.out.println("SHA-512: " + sha512);
```

### System Utilities

#### Hosts File Management

```java
// Read Hosts file
List<String> hosts = HostsFileManager.readHosts();
System.out.println("Hosts file content:");
for (String line : hosts) {
    System.out.println(line);
}

// Add Hosts entry
boolean added = HostsFileManager.addHost("127.0.0.1", "localhost");
System.out.println("Hosts entry addition " + (added ? "successful" : "failed"));

// Delete Hosts entry
boolean removed = HostsFileManager.removeHost("127.0.0.1", "localhost");
System.out.println("Hosts entry deletion " + (removed ? "successful" : "failed"));

// Backup Hosts file
HostsFileManager.backupHosts();
System.out.println("Hosts file backup successful");
```

#### DNS Resolution Utility

```java
// DNS resolution
List<String> ips = DnsResolver.resolve("www.example.com");
System.out.println("DNS resolution results:");
for (String ip : ips) {
    System.out.println(ip);
}

// DNS over HTTPS query
List<String> dohIps = DoHQuery.query("www.example.com");
System.out.println("DoH query results:");
for (String ip : dohIps) {
    System.out.println(ip);
}
```

#### IP Address Processing

```java
// Resolve IP address
String ip = "192.168.1.1";
IPAddressResolver resolver = new IPAddressResolver();
IPAddressResolver.IPInfo info = resolver.resolve(ip);
System.out.println("IP address information:");
System.out.println("Country: " + info.getCountry());
System.out.println("Province: " + info.getProvince());
System.out.println("City: " + info.getCity());
```

#### Process Management

```java
// Get process manager
ProcessManager processManager = ProcessManagerFactory.getProcessManager();

// Start process
Process process = processManager.startProcess("notepad.exe");
System.out.println("Process started successfully, PID: " + processManager.getProcessId(process));

// Check if process is running
boolean isRunning = processManager.isProcessRunning(process);
System.out.println("Is process running: " + isRunning);

// Terminate process
processManager.stopProcess(process);
System.out.println("Process terminated successfully");
```

### License Management

#### Hardware Information Acquisition

```java
// Get hardware information
String hardwareId = HardwareUtils.getHardwareId();
System.out.println("Hardware ID: " + hardwareId);

// Get CPU ID
String cpuId = HardwareUtils.getCpuId();
System.out.println("CPU ID: " + cpuId);

// Get disk ID
String diskId = HardwareUtils.getDiskId();
System.out.println("Disk ID: " + diskId);

// Get MAC address
String macAddress = HardwareUtils.getMacAddress();
System.out.println("MAC address: " + macAddress);
```

#### License Generation and Verification

```java
// Generate license
String hardwareId = HardwareUtils.getHardwareId();
String license = LicenseUtils.generateLicense(hardwareId, 365); // 365 days validity
System.out.println("Generated license: " + license);

// Verify license
LicenseInfo licenseInfo = LicenseUtils.verifyLicense(license, hardwareId);
if (licenseInfo.isValid()) {
    System.out.println("License is valid");
    System.out.println("Valid until: " + licenseInfo.getExpiryDate());
} else {
    System.out.println("License is invalid: " + licenseInfo.getErrorMessage());
}
```

### API Response Wrapper

#### Unified Response Result

```java
// Success response
ApiResponse<String> successResponse = ApiResponse.success("Operation successful");
System.out.println("Success response: " + successResponse);

// Failure response
ApiResponse<String> failResponse = ApiResponse.fail(ApiCode.INTERNAL_SERVER_ERROR, "Operation failed");
System.out.println("Failure response: " + failResponse);

// Response with data
User user = new User(1, "Zhang San", 25, "zhangsan@example.com");
ApiResponse<User> dataResponse = ApiResponse.success(user);
System.out.println("Response with data: " + dataResponse);
```

#### Pagination Response

```java
// Pagination response
List<User> userList = new ArrayList<>();
userList.add(new User(1, "Zhang San", 25, "zhangsan@example.com"));
userList.add(new User(2, "Li Si", 30, "lisi@example.com"));

PageResponse<User> pageResponse = PageResponse.success(userList, 1, 10, 2);
System.out.println("Pagination response: " + pageResponse);
System.out.println("Total records: " + pageResponse.getTotal());
System.out.println("Records per page: " + pageResponse.getSize());
System.out.println("Current page: " + pageResponse.getCurrent());
System.out.println("Total pages: " + pageResponse.getPages());
System.out.println("Data list: " + pageResponse.getRecords());
```

## Project Structure

### Core Modules

- **Geocoding Service**: `src/main/java/io/github/jukejuke/map/`
  - Amap: `amap/` directory
  - Tianditu: `tianditu/` directory
  - Coordinate conversion utility: `util/` directory

- **General Utilities**: `src/main/java/io/github/jukejuke/tool/`
  - Annotation utility: `annotation/` directory
  - Bean conversion utility: `bean/` directory
  - Configuration file processing: `config/` directory
  - Encryption and decryption utility: `crypto/` directory
  - Date and time processing: `date/` directory
  - DNS resolution utility: `dns/` directory
  - Excel utility: `excel/` directory
  - Process management: `exec/` directory
  - File operations: `file/` directory
  - Freemarker template: `freemarker/` directory
  - HTTP request: `http/` directory
  - ID generation: `id/` directory
  - IP address processing: `ip/` directory
  - JWT token: `jwt/` directory
  - License management: `license/` directory
  - Logging utility: `log/` directory
  - Email utility: `mail/` directory
  - Response wrapper: `response/` directory
  - String processing: `string/` directory
  - URL processing: `url/` directory

- **Qiniu Cloud Object Storage**: `src/main/java/io/github/jukejuke/qiniu/`

- **API Response Wrapper**: `src/main/java/io/github/jukejuke/api/`

### Test Module

- **Test Code**: `src/test/java/io/github/jukejuke/`
  - Contains test cases and example code for each module

## FAQ and Solutions

### Geocoding Service

**Problem**: Error message returned when calling geocoding service

**Solution**:
1. Check if API key is correct
2. Check if network connection is normal
3. Check if request parameters meet API requirements
4. Refer to API documentation to understand error code meanings

### Excel Utility

**Problem**: Memory overflow when exporting large amounts of data

**Solution**: Use streaming export methods `exportWithAnnotationStreaming` or `exportWithStream`. These methods use SXSSFWorkbook, which writes data to temporary files to avoid memory overflow.

### HTTP Utility

**Problem**: HTTP request timeout

**Solution**:
1. Check if network connection is normal
2. Check if target server is accessible
3. Consider increasing timeout (currently default 15 seconds)

### Qiniu Cloud Utility

**Problem**: File upload failed

**Solution**:
1. Check if Qiniu Cloud configuration is correct (Access Key, Secret Key, Bucket, etc.)
2. Check if network connection is normal
3. Check if file exists and is readable
4. Refer to Qiniu Cloud documentation to understand error code meanings

## Summary

JK Tool is a feature-rich Java utility library that provides geocoding services, coordinate conversion, general utilities, Qiniu Cloud object storage tools, encryption and decryption tools, system utilities, and API response wrappers. This project is designed to be simple and easy to use, suitable for various Java application scenarios.

Through this documentation, you should have an understanding of JK Tool's main features and usage methods. If you encounter any problems during use, please refer to the FAQ and Solutions section, or check the project's test code and example code.

Hope JK Tool can help with your Java project development!

## License

This project follows the Apache 2.0 License. For details, please refer to the [LICENSE](LICENSE) file.

## Contribution

Welcome to contribute code to this project. Please follow these steps:

1. Fork this project
2. Create a new branch
3. Submit a Pull Request

## Contact

If you have any questions or suggestions, please contact us through:

- GitHub: [https://github.com/jukejuke/jk-tool](https://github.com/jukejuke/jk-tool)
