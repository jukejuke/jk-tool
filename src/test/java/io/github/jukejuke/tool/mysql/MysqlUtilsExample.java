package io.github.jukejuke.tool.mysql;

import java.util.List;
import java.util.Map;

/**
 * MysqlUtils使用示例类
 * 演示如何使用MysqlUtils进行数据库操作
 */
public class MysqlUtilsExample {

    public static void main(String[] args) {
        System.out.println("=== MysqlUtils 使用示例 ===\n");

        try {
            // 示例1：使用配置文件初始化
            System.out.println("1. 使用配置文件初始化:");
            try {
                MysqlUtils.init();
                System.out.println("   ✓ 从默认配置文件初始化成功\n");
            } catch (Exception e) {
                System.out.println("   ✗ 配置文件不存在，使用代码配置方式\n");
                
                // 示例2：使用代码配置初始化
                System.out.println("2. 使用代码配置初始化:");
                MysqlConfig config = new MysqlConfig(
                        "jdbc:h2:mem:exampledb;DB_CLOSE_DELAY=-1",
                        "sa",
                        ""
                );
                config.setShowSql(true);
                config.setFormatSql(false);
                config.setShowParams(true);
                config.setSqlLevel("info");
                config.setMinimumIdle(2);
                config.setMaximumPoolSize(5);
                config.setConnectionTestQuery("SELECT 1");
                config.setConnectionTestOnBorrow(true);
                
                MysqlUtils.init(config);
                System.out.println("   ✓ 代码配置初始化成功\n");
            }

            // 示例3：创建表
            System.out.println("3. 创建表:");
            MysqlUtils.update(
                    "CREATE TABLE IF NOT EXISTS products (" +
                            "id INT AUTO_INCREMENT PRIMARY KEY," +
                            "name VARCHAR(100) NOT NULL," +
                            "price DECIMAL(10,2)," +
                            "stock INT," +
                            "created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP" +
                            ")"
            );
            System.out.println("   ✓ 表创建成功\n");

            // 示例4：插入数据
            System.out.println("4. 插入数据:");
            int insertResult1 = MysqlUtils.update(
                    "INSERT INTO products (name, price, stock) VALUES (?, ?, ?)",
                    "iPhone 15", 5999.00, 100
            );
            int insertResult2 = MysqlUtils.update(
                    "INSERT INTO products (name, price, stock) VALUES (?, ?, ?)",
                    "MacBook Pro", 12999.00, 50
            );
            int insertResult3 = MysqlUtils.update(
                    "INSERT INTO products (name, price, stock) VALUES (?, ?, ?)",
                    "AirPods Pro", 1899.00, 200
            );
            System.out.println("   ✓ 插入 " + (insertResult1 + insertResult2 + insertResult3) + " 条数据\n");

            // 示例5：查询列表
            System.out.println("5. 查询产品列表:");
            List<Map<String, Object>> products = MysqlUtils.selectList(
                    "SELECT * FROM products ORDER BY id"
            );
            for (Map<String, Object> product : products) {
                System.out.println("   - " + product.get("NAME") + 
                        ", 价格: " + product.get("PRICE") + 
                        ", 库存: " + product.get("STOCK"));
            }
            System.out.println("   ✓ 共查询到 " + products.size() + " 条记录\n");

            // 示例6：查询单个对象
            System.out.println("6. 查询单个产品:");
            Map<String, Object> product = MysqlUtils.selectOne(
                    "SELECT * FROM products WHERE name = ?",
                    "iPhone 15"
            );
            if (product != null) {
                System.out.println("   - 产品名称: " + product.get("NAME"));
                System.out.println("   - 价格: " + product.get("PRICE"));
                System.out.println("   - 库存: " + product.get("STOCK"));
            }
            System.out.println("   ✓ 单条查询成功\n");

            // 示例7：更新数据
            System.out.println("7. 更新数据:");
            int updateResult = MysqlUtils.update(
                    "UPDATE products SET price = ?, stock = ? WHERE name = ?",
                    5799.00, 95, "iPhone 15"
            );
            System.out.println("   ✓ 更新 " + updateResult + " 条记录\n");

            // 示例8：验证更新结果
            System.out.println("8. 验证更新结果:");
            Map<String, Object> updatedProduct = MysqlUtils.selectOne(
                    "SELECT * FROM products WHERE name = ?",
                    "iPhone 15"
            );
            System.out.println("   - 新价格: " + updatedProduct.get("PRICE"));
            System.out.println("   - 新库存: " + updatedProduct.get("STOCK"));
            System.out.println("   ✓ 验证成功\n");

            // 示例9：连接池监控
            System.out.println("9. 连接池监控:");
            System.out.println("   - 活跃连接数: " + MysqlUtils.getActiveConnections());
            System.out.println("   - 空闲连接数: " + MysqlUtils.getIdleConnections());
            System.out.println("   ✓ 监控数据获取成功\n");

            // 示例10：删除数据
            System.out.println("10. 删除数据:");
            int deleteResult = MysqlUtils.update(
                    "DELETE FROM products WHERE name = ?",
                    "AirPods Pro"
            );
            System.out.println("   ✓ 删除 " + deleteResult + " 条记录\n");

            // 示例11：验证删除结果
            System.out.println("11. 验证删除结果:");
            List<Map<String, Object>> remainingProducts = MysqlUtils.selectList(
                    "SELECT * FROM products"
            );
            System.out.println("   - 剩余产品数: " + remainingProducts.size());
            System.out.println("   ✓ 验证成功\n");

            // 示例12：获取原生连接
            System.out.println("12. 获取原生Connection:");
            try (java.sql.Connection conn = MysqlUtils.getConnection()) {
                System.out.println("   ✓ 获取原生连接成功");
                System.out.println("   - 连接是否有效: " + conn.isValid(5));
            }
            System.out.println();

            // 关闭连接池
            System.out.println("13. 关闭连接池:");
            MysqlUtils.shutdown();
            System.out.println("   ✓ 连接池已关闭\n");

            System.out.println("=== 所有示例运行完成 ===");

        } catch (Exception e) {
            System.err.println("✗ 发生错误: " + e.getMessage());
            e.printStackTrace();
            
            // 确保关闭连接池
            try {
                MysqlUtils.shutdown();
            } catch (Exception ex) {
                // 忽略
            }
        }
    }
}
