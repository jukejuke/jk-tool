package io.github.jukejuke.tool.mysql;

import java.util.List;
import java.util.Map;

/**
 * 真实MySQL数据库测试程序
 * 使用配置好的MySQL数据库进行测试
 */
public class MysqlRealDatabaseTest {

    public static void main(String[] args) {
        System.out.println("========================================");
        System.out.println("   MySQL 真实数据库测试程序");
        System.out.println("========================================\n");

        try {
            // 步骤1: 初始化MySQL工具
            System.out.println("【1】正在初始化MySQL连接池...");
            MysqlUtils.init("test-mysql.properties");
            System.out.println("   ✓ 连接池初始化成功\n");

            // 步骤2: 测试连接
            System.out.println("【2】正在测试数据库连接...");
            try (java.sql.Connection conn = MysqlUtils.getConnection()) {
                System.out.println("   ✓ 数据库连接成功");
                System.out.println("   - 数据库URL: " + conn.getMetaData().getURL());
                System.out.println("   - 数据库用户: " + conn.getMetaData().getUserName());
                System.out.println("   - 数据库产品: " + conn.getMetaData().getDatabaseProductName());
                System.out.println("   - 数据库版本: " + conn.getMetaData().getDatabaseProductVersion() + "\n");
            }

            // 步骤3: 创建测试表
            System.out.println("【3】正在创建测试表...");
            String createTableSql = "CREATE TABLE IF NOT EXISTS test_users (" +
                    "id INT AUTO_INCREMENT PRIMARY KEY, " +
                    "name VARCHAR(100) NOT NULL, " +
                    "age INT, " +
                    "email VARCHAR(100), " +
                    "created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP, " +
                    "updated_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP" +
                    ") ENGINE=InnoDB DEFAULT CHARSET=utf8mb4";
            
            MysqlUtils.update(createTableSql);
            System.out.println("   ✓ 测试表创建成功\n");

            // 步骤4: 清空测试数据（如果有）
            System.out.println("【4】正在清空测试数据...");
            MysqlUtils.update("DELETE FROM test_users");
            System.out.println("   ✓ 测试数据已清空\n");

            // 步骤5: 插入测试数据
            System.out.println("【5】正在插入测试数据...");
            
            // 插入用户1
            int result1 = MysqlUtils.update(
                    "INSERT INTO test_users (name, age, email) VALUES (?, ?, ?)",
                    "张三", 25, "zhangsan@example.com"
            );
            System.out.println("   - 插入用户张三, 影响行数: " + result1);
            
            // 插入用户2
            int result2 = MysqlUtils.update(
                    "INSERT INTO test_users (name, age, email) VALUES (?, ?, ?)",
                    "李四", 30, "lisi@example.com"
            );
            System.out.println("   - 插入用户李四, 影响行数: " + result2);
            
            // 插入用户3
            int result3 = MysqlUtils.update(
                    "INSERT INTO test_users (name, age, email) VALUES (?, ?, ?)",
                    "王五", 28, "wangwu@example.com"
            );
            System.out.println("   - 插入用户王五, 影响行数: " + result3);
            
            System.out.println("   ✓ 测试数据插入完成\n");

            // 步骤6: 查询所有数据
            System.out.println("【6】正在查询所有用户数据...");
            List<Map<String, Object>> allUsers = MysqlUtils.selectList(
                    "SELECT * FROM test_users ORDER BY id"
            );
            System.out.println("   ✓ 查询到 " + allUsers.size() + " 条数据");
            System.out.println("   - 用户列表:");
            for (Map<String, Object> user : allUsers) {
                System.out.println("     ID: " + user.get("id") + 
                        ", 姓名: " + user.get("name") + 
                        ", 年龄: " + user.get("age") + 
                        ", 邮箱: " + user.get("email") + 
                        ", 创建时间: " + user.get("created_at"));
            }
            System.out.println();

            // 步骤7: 查询单个用户
            System.out.println("【7】正在查询单个用户...");
            Map<String, Object> user = MysqlUtils.selectOne(
                    "SELECT * FROM test_users WHERE name = ?",
                    "张三"
            );
            if (user != null) {
                System.out.println("   ✓ 查询到用户:");
                System.out.println("     - ID: " + user.get("id"));
                System.out.println("     - 姓名: " + user.get("name"));
                System.out.println("     - 年龄: " + user.get("age"));
                System.out.println("     - 邮箱: " + user.get("email"));
            }
            System.out.println();

            // 步骤8: 更新用户信息
            System.out.println("【8】正在更新用户信息...");
            int updateResult = MysqlUtils.update(
                    "UPDATE test_users SET age = ?, email = ? WHERE name = ?",
                    26, "zhangsan_new@example.com", "张三"
            );
            System.out.println("   ✓ 更新用户张三, 影响行数: " + updateResult);
            
            // 验证更新结果
            Map<String, Object> updatedUser = MysqlUtils.selectOne(
                    "SELECT * FROM test_users WHERE name = ?",
                    "张三"
            );
            if (updatedUser != null) {
                System.out.println("   - 更新后的年龄: " + updatedUser.get("age"));
                System.out.println("   - 更新后的邮箱: " + updatedUser.get("email"));
            }
            System.out.println();

            // 步骤9: 连接池监控
            System.out.println("【9】连接池监控信息...");
            System.out.println("   - 活跃连接数: " + MysqlUtils.getActiveConnections());
            System.out.println("   - 空闲连接数: " + MysqlUtils.getIdleConnections());
            System.out.println("   ✓ 连接池监控正常\n");

            // 步骤10: 删除测试数据
            System.out.println("【10】正在删除测试数据...");
            int deleteResult = MysqlUtils.update(
                    "DELETE FROM test_users WHERE name = ?",
                    "李四"
            );
            System.out.println("   - 删除用户李四, 影响行数: " + deleteResult);
            
            // 验证删除后的结果
            List<Map<String, Object>> remainingUsers = MysqlUtils.selectList(
                    "SELECT * FROM test_users ORDER BY id"
            );
            System.out.println("   - 删除后剩余用户数: " + remainingUsers.size());
            System.out.println("   ✓ 删除操作成功\n");

            // 步骤11: 测试条件查询
            System.out.println("【11】正在测试条件查询...");
            List<Map<String, Object>> youngUsers = MysqlUtils.selectList(
                    "SELECT * FROM test_users WHERE age < ? ORDER BY id",
                    30
            );
            System.out.println("   ✓ 查询年龄小于30岁的用户:");
            for (Map<String, Object> u : youngUsers) {
                System.out.println("     - " + u.get("name") + " (年龄: " + u.get("age") + ")");
            }
            System.out.println();

            // 步骤12: 统计查询
            System.out.println("【12】正在测试统计查询...");
            Map<String, Object> stats = MysqlUtils.selectOne(
                    "SELECT COUNT(*) as total, AVG(age) as avg_age, MAX(age) as max_age FROM test_users"
            );
            if (stats != null) {
                System.out.println("   ✓ 统计数据:");
                System.out.println("     - 总用户数: " + stats.get("total"));
                System.out.println("     - 平均年龄: " + stats.get("avg_age"));
                System.out.println("     - 最大年龄: " + stats.get("max_age"));
            }
            System.out.println();

            // ========== 以下是新增的文件 SQL 测试 ==========

            // 步骤13: 测试从 classpath 资源读取 SQL
            System.out.println("【13】正在测试从资源文件读取 SQL...");
            String sqlFromResource = MysqlUtils.readSqlFromResource("real_test_select_all.sql");
            System.out.println("   ✓ 从资源文件读取 SQL 成功");
            System.out.println("   - SQL 内容:");
            System.out.println("     " + sqlFromResource.replace("\n", "\n     "));
            System.out.println();

            // 步骤14: 测试从资源文件读取 SQL 并执行查询
            System.out.println("【14】正在测试从资源文件执行查询...");
            List<Map<String, Object>> usersFromFile = MysqlUtils.selectListFromResource("real_test_select_all.sql");
            System.out.println("   ✓ 从资源文件查询成功");
            System.out.println("   - 查询到 " + usersFromFile.size() + " 条数据:");
            for (Map<String, Object> u : usersFromFile) {
                System.out.println("     - " + u.get("name") + " (年龄: " + u.get("age") + ")");
            }
            System.out.println();

            // 步骤15: 测试从资源文件读取 SQL 并执行插入
            System.out.println("【15】正在测试从资源文件执行插入...");
            int insertFromFileResult = MysqlUtils.updateFromResource(
                    "real_test_insert.sql",
                    "赵六", 35, "zhaoliu@example.com"
            );
            System.out.println("   ✓ 从资源文件插入成功, 影响行数: " + insertFromFileResult);
            
            // 验证插入
            Map<String, Object> newUser = MysqlUtils.selectOne(
                    "SELECT * FROM test_users WHERE name = ?", 
                    "赵六"
            );
            if (newUser != null) {
                System.out.println("   - 新增用户: " + newUser.get("name") + ", 邮箱: " + newUser.get("email"));
            }
            System.out.println();

            // 步骤16: 测试从资源文件读取 SQL 并执行更新
            System.out.println("【16】正在测试从资源文件执行更新...");
            int updateFromFileResult = MysqlUtils.updateFromResource(
                    "real_test_update.sql",
                    36, "zhaoliu_updated@example.com", "赵六"
            );
            System.out.println("   ✓ 从资源文件更新成功, 影响行数: " + updateFromFileResult);
            
            // 验证更新
            Map<String, Object> updatedUserFromFile = MysqlUtils.selectOne(
                    "SELECT * FROM test_users WHERE name = ?", 
                    "赵六"
            );
            if (updatedUserFromFile != null) {
                System.out.println("   - 更新后的用户: " + updatedUserFromFile.get("name"));
                System.out.println("     年龄: " + updatedUserFromFile.get("age") + ", 邮箱: " + updatedUserFromFile.get("email"));
            }
            System.out.println();

            // 步骤17: 测试从资源文件读取 SQL 并执行统计查询
            System.out.println("【17】正在测试从资源文件执行统计查询...");
            Map<String, Object> statsFromFile = MysqlUtils.selectOneFromResource("real_test_stats.sql");
            if (statsFromFile != null) {
                System.out.println("   ✓ 从资源文件统计查询成功:");
                System.out.println("     - 总用户数: " + statsFromFile.get("total"));
                System.out.println("     - 平均年龄: " + statsFromFile.get("avg_age"));
                System.out.println("     - 最大年龄: " + statsFromFile.get("max_age"));
                System.out.println("     - 最小年龄: " + statsFromFile.get("min_age"));
            }
            System.out.println();

            // 步骤18: 测试写入 SQL 到临时文件并读取
            System.out.println("【18】正在测试写入 SQL 到文件并读取...");
            String customSql = "SELECT name, age FROM test_users WHERE age > 25 ORDER BY age DESC";
            java.io.File tempSqlFile = java.io.File.createTempFile("custom_sql_", ".sql");
            tempSqlFile.deleteOnExit();
            
            MysqlUtils.writeSqlToFile(customSql, tempSqlFile.getAbsolutePath());
            System.out.println("   ✓ 写入 SQL 到临时文件成功");
            
            // 从临时文件读取并执行
            List<Map<String, Object>> customQueryResult = MysqlUtils.selectListFromFile(tempSqlFile.getAbsolutePath());
            System.out.println("   ✓ 从临时文件读取并执行查询成功");
            System.out.println("   - 年龄大于25岁的用户:");
            for (Map<String, Object> u : customQueryResult) {
                System.out.println("     - " + u.get("name") + " (年龄: " + u.get("age") + ")");
            }
            System.out.println();

            // 步骤19: 测试从资源文件读取 SQL 并执行删除
            System.out.println("【19】正在测试从资源文件执行删除...");
            int deleteFromFileResult = MysqlUtils.updateFromResource(
                    "real_test_delete.sql",
                    "赵六"
            );
            System.out.println("   ✓ 从资源文件删除成功, 影响行数: " + deleteFromFileResult);
            
            // 验证删除
            List<Map<String, Object>> finalUsers = MysqlUtils.selectList("SELECT * FROM test_users");
            System.out.println("   - 删除后剩余用户数: " + finalUsers.size());
            System.out.println();

            // 完成
            System.out.println("========================================");
            System.out.println("   ✓ 所有测试通过！");
            System.out.println("========================================");

        } catch (Exception e) {
            System.err.println("✗ 测试过程中发生错误: " + e.getMessage());
            e.printStackTrace();
        } finally {
            // 关闭连接池
            System.out.println("\n正在关闭连接池...");
            try {
                MysqlUtils.shutdown();
                System.out.println("✓ 连接池已关闭");
            } catch (Exception e) {
                System.err.println("关闭连接池时出错: " + e.getMessage());
            }
        }
    }
}
