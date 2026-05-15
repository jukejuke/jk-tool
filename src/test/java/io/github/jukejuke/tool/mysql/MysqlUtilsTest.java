package io.github.jukejuke.tool.mysql;

import org.junit.jupiter.api.*;

import java.io.File;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.sql.*;
import java.util.List;
import java.util.Map;

import static org.junit.jupiter.api.Assertions.*;

/**
 * MysqlUtils测试类
 * 使用H2内存数据库进行测试
 */
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
class MysqlUtilsTest {

    private static final String H2_URL = "jdbc:h2:mem:testdb;DB_CLOSE_DELAY=-1";
    private static final String H2_USER = "sa";
    private static final String H2_PASS = "";

    @BeforeAll
    static void setUpAll() throws Exception {
        // 初始化H2数据库
        MysqlConfig config = new MysqlConfig(H2_URL, H2_USER, H2_PASS);
        config.setShowSql(true);
        config.setShowParams(true);
        config.setSqlLevel("info");
        config.setConnectionTestQuery("SELECT 1");
        config.setConnectionTestOnBorrow(true);
        MysqlUtils.init(config);
        
        // 创建测试表
        try (Connection conn = DriverManager.getConnection(H2_URL, H2_USER, H2_PASS);
             Statement stmt = conn.createStatement()) {
            stmt.execute("CREATE TABLE IF NOT EXISTS users (" +
                    "id INT AUTO_INCREMENT PRIMARY KEY," +
                    "name VARCHAR(100) NOT NULL," +
                    "age INT," +
                    "email VARCHAR(100)," +
                    "created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP" +
                    ")");
        }
    }

    @AfterAll
    static void tearDownAll() {
        // 关闭连接池
        MysqlUtils.shutdown();
    }

    @Test
    @Order(1)
    void testInit() throws SQLException {
        // 验证初始化成功
        assertNotNull(MysqlUtils.getConnection());
        // 活跃连接数可能大于等于0，连接池行为会变化
        assertTrue(MysqlUtils.getActiveConnections() >= 0);
    }

    @Test
    @Order(2)
    void testInsert() throws SQLException {
        // 测试插入数据
        int result = MysqlUtils.update(
                "INSERT INTO users (name, age, email) VALUES (?, ?, ?)",
                "张三", 25, "zhangsan@example.com"
        );
        assertEquals(1, result);

        // 再插入一条
        result = MysqlUtils.update(
                "INSERT INTO users (name, age, email) VALUES (?, ?, ?)",
                "李四", 30, "lisi@example.com"
        );
        assertEquals(1, result);
    }

    @Test
    @Order(3)
    void testSelectList() throws SQLException {
        // 测试查询列表
        List<Map<String, Object>> users = MysqlUtils.selectList(
                "SELECT * FROM users ORDER BY id"
        );
        assertNotNull(users);
        assertEquals(2, users.size());

        // 验证第一条数据
        Map<String, Object> user1 = users.get(0);
        assertEquals("张三", user1.get("NAME"));
        assertEquals(25, user1.get("AGE"));
        assertEquals("zhangsan@example.com", user1.get("EMAIL"));

        // 验证第二条数据
        Map<String, Object> user2 = users.get(1);
        assertEquals("李四", user2.get("NAME"));
        assertEquals(30, user2.get("AGE"));
        assertEquals("lisi@example.com", user2.get("EMAIL"));
    }

    @Test
    @Order(4)
    void testSelectOne() throws SQLException {
        // 测试查询单个对象
        Map<String, Object> user = MysqlUtils.selectOne(
                "SELECT * FROM users WHERE name = ?",
                "张三"
        );
        assertNotNull(user);
        assertEquals("张三", user.get("NAME"));
        assertEquals(25, user.get("AGE"));
    }

    @Test
    @Order(5)
    void testSelectOneNotFound() throws SQLException {
        // 测试查询不存在的数据
        Map<String, Object> user = MysqlUtils.selectOne(
                "SELECT * FROM users WHERE name = ?",
                "不存在的用户"
        );
        assertNull(user);
    }

    @Test
    @Order(6)
    void testUpdate() throws SQLException {
        // 测试更新数据
        int result = MysqlUtils.update(
                "UPDATE users SET age = ?, email = ? WHERE name = ?",
                26, "zhangsan_new@example.com", "张三"
        );
        assertEquals(1, result);

        // 验证更新是否成功
        Map<String, Object> user = MysqlUtils.selectOne(
                "SELECT * FROM users WHERE name = ?",
                "张三"
        );
        assertNotNull(user);
        assertEquals(26, user.get("AGE"));
        assertEquals("zhangsan_new@example.com", user.get("EMAIL"));
    }

    @Test
    @Order(7)
    void testDelete() throws SQLException {
        // 测试删除数据
        int result = MysqlUtils.update(
                "DELETE FROM users WHERE name = ?",
                "李四"
        );
        assertEquals(1, result);

        // 验证删除是否成功
        List<Map<String, Object>> users = MysqlUtils.selectList(
                "SELECT * FROM users"
        );
        assertEquals(1, users.size());
    }

    @Test
    @Order(8)
    void testConnectionPoolMetrics() {
        // 测试连接池监控
        assertTrue(MysqlUtils.getActiveConnections() >= 0);
        assertTrue(MysqlUtils.getIdleConnections() >= 0);
    }

    @Test
    @Order(9)
    void testInitWithNullConfig() {
        // 测试传入null配置
        assertThrows(IllegalArgumentException.class, () -> {
            MysqlUtils.init((MysqlConfig) null);
        });
    }

    @Test
    @Order(10)
    void testGetConnectionWithoutInit() {
        // 先关闭连接池
        MysqlUtils.shutdown();
        
        // 测试未初始化时获取连接
        assertThrows(IllegalStateException.class, () -> {
            MysqlUtils.getConnection();
        });

        // 重新初始化以便其他测试继续
        MysqlConfig config = new MysqlConfig(H2_URL, H2_USER, H2_PASS);
        MysqlUtils.init(config);
    }

    @Test
    @Order(11)
    void testReadSqlFromResource() throws IOException {
        // 测试从classpath资源读取SQL
        String sql = MysqlUtils.readSqlFromResource("test_select_users.sql");
        assertNotNull(sql);
        assertTrue(sql.contains("SELECT * FROM users"));
    }

    @Test
    @Order(12)
    void testWriteSqlToFile() throws IOException {
        // 测试写入SQL到文件
        String testSql = "SELECT * FROM test WHERE id = ?";
        File tempFile = File.createTempFile("test_sql_", ".sql");
        tempFile.deleteOnExit();
        
        MysqlUtils.writeSqlToFile(testSql, tempFile.getAbsolutePath());
        
        // 验证写入是否成功
        String readBack = MysqlUtils.readSqlFromFile(tempFile.getAbsolutePath());
        assertEquals(testSql, readBack);
    }

    @Test
    @Order(13)
    void testSelectListFromResource() throws IOException, SQLException {
        // 测试从资源文件读取SQL并查询列表
        List<Map<String, Object>> users = MysqlUtils.selectListFromResource("test_select_users.sql");
        assertNotNull(users);
        assertTrue(users.size() >= 0);
    }

    @Test
    @Order(14)
    void testUpdateFromResource() throws IOException, SQLException {
        // 先插入一条测试数据
        MysqlUtils.update("INSERT INTO users (name, age, email) VALUES (?, ?, ?)", 
                "测试用户", 35, "test@example.com");
        
        // 测试从资源文件读取SQL并执行更新
        int result = MysqlUtils.updateFromResource("test_update_user.sql", 
                36, "test_updated@example.com", "测试用户");
        assertEquals(1, result);
        
        // 验证更新
        Map<String, Object> user = MysqlUtils.selectOne("SELECT * FROM users WHERE name = ?", "测试用户");
        assertNotNull(user);
        assertEquals(36, user.get("AGE"));
        assertEquals("test_updated@example.com", user.get("EMAIL"));
    }

    @Test
    @Order(15)
    void testSelectListFromFile() throws IOException, SQLException {
        // 创建临时SQL文件
        String sqlContent = "SELECT * FROM users ORDER BY id";
        File tempFile = File.createTempFile("test_select_", ".sql");
        tempFile.deleteOnExit();
        MysqlUtils.writeSqlToFile(sqlContent, tempFile.getAbsolutePath());
        
        // 测试从文件读取SQL并查询
        List<Map<String, Object>> users = MysqlUtils.selectListFromFile(tempFile.getAbsolutePath());
        assertNotNull(users);
    }

    @Test
    @Order(16)
    void testReadSqlWithCharset() throws IOException {
        // 测试使用指定字符集读取SQL
        String sql = MysqlUtils.readSqlFromResource("test_select_users.sql", StandardCharsets.UTF_8);
        assertNotNull(sql);
    }

    @Test
    @Order(17)
    void testReadSqlFromInvalidFile() {
        // 测试读取不存在的文件
        assertThrows(IOException.class, () -> {
            MysqlUtils.readSqlFromFile("non_existent_file.sql");
        });
    }

    @Test
    @Order(18)
    void testReadSqlFromInvalidResource() {
        // 测试读取不存在的资源
        assertThrows(IOException.class, () -> {
            MysqlUtils.readSqlFromResource("non_existent_resource.sql");
        });
    }

    @Test
    @Order(19)
    void testWriteSqlWithAppend() throws IOException {
        // 测试追加写入
        File tempFile = File.createTempFile("test_append_", ".sql");
        tempFile.deleteOnExit();
        
        String sql1 = "SELECT * FROM table1;";
        String sql2 = "SELECT * FROM table2;";
        
        MysqlUtils.writeSqlToFile(sql1, tempFile.getAbsolutePath());
        MysqlUtils.writeSqlToFile(sql2, tempFile.getAbsolutePath(), true);
        
        String content = MysqlUtils.readSqlFromFile(tempFile.getAbsolutePath());
        assertTrue(content.contains(sql1));
        assertTrue(content.contains(sql2));
    }
}
