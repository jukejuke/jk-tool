package io.github.jukejuke.tool.mysql;

import org.junit.jupiter.api.*;

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
        assertEquals(0, MysqlUtils.getActiveConnections());
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
        assertEquals(0, MysqlUtils.getActiveConnections());
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
}
