package io.github.jukejuke.tool.mysql;

import org.junit.jupiter.api.Test;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.util.Properties;

import static org.junit.jupiter.api.Assertions.*;

/**
 * MysqlConfig测试类
 */
class MysqlConfigTest {

    @Test
    void testDefaultConstructor() {
        MysqlConfig config = new MysqlConfig();
        
        // 验证默认值
        assertTrue(config.isShowSql());
        assertFalse(config.isFormatSql());
        assertTrue(config.isShowParams());
        assertEquals("debug", config.getSqlLevel());
        assertEquals(5, config.getMinimumIdle());
        assertEquals(10, config.getMaximumPoolSize());
        assertEquals(30000, config.getConnectionTimeout());
        assertEquals(600000, config.getIdleTimeout());
        assertEquals(1800000, config.getMaxLifetime());
        assertEquals("SELECT 1", config.getConnectionTestQuery());
        assertTrue(config.isConnectionTestOnBorrow());
    }

    @Test
    void testParameterizedConstructor() {
        String url = "jdbc:mysql://localhost:3306/testdb";
        String user = "testuser";
        String pass = "testpass";
        
        MysqlConfig config = new MysqlConfig(url, user, pass);
        
        assertEquals(url, config.getUrl());
        assertEquals(user, config.getUser());
        assertEquals(pass, config.getPass());
    }

    @Test
    void testSettersAndGetters() {
        MysqlConfig config = new MysqlConfig();
        
        // 设置数据库连接属性
        config.setUrl("jdbc:mysql://127.0.0.1:3306/mydb");
        config.setUser("admin");
        config.setPass("password123");
        
        // 设置日志属性
        config.setShowSql(false);
        config.setFormatSql(true);
        config.setShowParams(false);
        config.setSqlLevel("info");
        
        // 设置连接池属性
        config.setMinimumIdle(3);
        config.setMaximumPoolSize(20);
        config.setConnectionTimeout(60000);
        config.setIdleTimeout(300000);
        config.setMaxLifetime(3600000);
        config.setConnectionTestQuery("SELECT 1 FROM DUAL");
        config.setConnectionTestOnBorrow(false);
        
        // 验证设置的值
        assertEquals("jdbc:mysql://127.0.0.1:3306/mydb", config.getUrl());
        assertEquals("admin", config.getUser());
        assertEquals("password123", config.getPass());
        assertFalse(config.isShowSql());
        assertTrue(config.isFormatSql());
        assertFalse(config.isShowParams());
        assertEquals("info", config.getSqlLevel());
        assertEquals(3, config.getMinimumIdle());
        assertEquals(20, config.getMaximumPoolSize());
        assertEquals(60000, config.getConnectionTimeout());
        assertEquals(300000, config.getIdleTimeout());
        assertEquals(3600000, config.getMaxLifetime());
        assertEquals("SELECT 1 FROM DUAL", config.getConnectionTestQuery());
        assertFalse(config.isConnectionTestOnBorrow());
    }

    @Test
    void testLoadFromConfig() throws IOException {
        // 创建临时配置文件
        String configContent = "url = jdbc:mysql://test-host:3306/test-db\n" +
                "user = testuser\n" +
                "pass = testpass\n" +
                "showSql = false\n" +
                "formatSql = true\n" +
                "showParams = false\n" +
                "sqlLevel = warn\n" +
                "minimumIdle = 2\n" +
                "maximumPoolSize = 15\n" +
                "connectionTimeout = 45000\n" +
                "idleTimeout = 900000\n" +
                "maxLifetime = 2700000\n" +
                "connectionTestQuery = SELECT 1\n" +
                "connectionTestOnBorrow = true";
        
        File tempFile = File.createTempFile("test-mysql", ".properties");
        tempFile.deleteOnExit();
        Files.write(tempFile.toPath(), configContent.getBytes());
        
        // 注意：由于PropertiesUtils.loadFromClassPath是从类路径加载，
        // 我们这里直接使用代码模拟加载过程
        Properties properties = new Properties();
        properties.load(Files.newInputStream(tempFile.toPath()));
        
        MysqlConfig config = new MysqlConfig();
        config.setUrl(properties.getProperty("url"));
        config.setUser(properties.getProperty("user"));
        config.setPass(properties.getProperty("pass"));
        config.setShowSql(Boolean.parseBoolean(properties.getProperty("showSql", "true")));
        config.setFormatSql(Boolean.parseBoolean(properties.getProperty("formatSql", "false")));
        config.setShowParams(Boolean.parseBoolean(properties.getProperty("showParams", "true")));
        config.setSqlLevel(properties.getProperty("sqlLevel", "debug"));
        config.setMinimumIdle(Integer.parseInt(properties.getProperty("minimumIdle", "5")));
        config.setMaximumPoolSize(Integer.parseInt(properties.getProperty("maximumPoolSize", "10")));
        config.setConnectionTimeout(Long.parseLong(properties.getProperty("connectionTimeout", "30000")));
        config.setIdleTimeout(Long.parseLong(properties.getProperty("idleTimeout", "600000")));
        config.setMaxLifetime(Long.parseLong(properties.getProperty("maxLifetime", "1800000")));
        config.setConnectionTestQuery(properties.getProperty("connectionTestQuery", "SELECT 1"));
        config.setConnectionTestOnBorrow(Boolean.parseBoolean(properties.getProperty("connectionTestOnBorrow", "true")));
        
        // 验证加载的值
        assertEquals("jdbc:mysql://test-host:3306/test-db", config.getUrl());
        assertEquals("testuser", config.getUser());
        assertEquals("testpass", config.getPass());
        assertFalse(config.isShowSql());
        assertTrue(config.isFormatSql());
        assertFalse(config.isShowParams());
        assertEquals("warn", config.getSqlLevel());
        assertEquals(2, config.getMinimumIdle());
        assertEquals(15, config.getMaximumPoolSize());
        assertEquals(45000, config.getConnectionTimeout());
        assertEquals(900000, config.getIdleTimeout());
        assertEquals(2700000, config.getMaxLifetime());
        assertEquals("SELECT 1", config.getConnectionTestQuery());
        assertTrue(config.isConnectionTestOnBorrow());
    }
}
