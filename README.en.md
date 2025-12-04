# JK Tool

JK Tool is a Java-based utility library primarily designed to convert latitude and longitude coordinates into human-readable address information using the reverse geocoding services of Amap (Gaode Map) and Tianditu. This project is suitable for Java applications requiring integration with geocoding services.

## Features

- **Amap Reverse Geocoding**: Implemented via the `AmapRegeoCoder` class, supporting conversion of coordinates into structured address information.
- **Tianditu Reverse Geocoding**: Implemented via the `TiandituGeocoder` class, supporting conversion of coordinates into structured address information.
- **Extensibility**: Easily extendable to support additional geocoding services.
- **Test Cases**: Includes comprehensive test cases for both Amap and Tianditu to ensure feature reliability.

## Usage

### Add Dependencies

Ensure your `pom.xml` file includes the required dependencies, such as `OkHttpClient` and other necessary libraries.

### Initialize Reverse Geocoders

#### Amap

```java
AmapRegeoCoder amapRegeoCoder = new AmapRegeoCoder.Builder("your_api_key").build();
AmapResponse response = amapRegeoCoder.reverseGeocode(116.397428, 39.90923);
```

#### Tianditu

```java
TiandituGeocoder tiandituGeocoder = new TiandituGeocoder.Builder("your_api_key").build();
TiandituResponse response = tiandituGeocoder.reverseGeocode(116.397428, 39.90923);
```

### Parse Response Data

Extract structured address information easily from the returned `AmapResponse` or `TiandituResponse` objects.

## Project Structure

- `src/main/java/io/github/jukejuke/map/amap/AmapRegeoCoder.java`: Implementation of Amap reverse geocoding.
- `src/main/java/io/github/jukejuke/geocoder/tianditu/TiandituGeocoder.java`: Implementation of Tianditu reverse geocoding.
- `src/test/java/io/github/jukejuke/map/amap/AmapRegeoCoderTest.java`: Test cases for Amap reverse geocoding.
- `src/test/java/io/github/jukejuke/geocoder/tianditu/TiandituGeocoderTest.java`: Test cases for Tianditu reverse geocoding.

## License

This project is licensed under the MIT License. For details, see the [LICENSE](LICENSE) file.

## Contribution

Contributions are welcome. Please follow these steps:

1. Fork this project.
2. Create a new branch.
3. Submit a Pull Request.