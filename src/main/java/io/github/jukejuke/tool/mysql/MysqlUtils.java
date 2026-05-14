package io.github.jukejuke.tool.mysql;

import com.zaxxer.hikari.HikariConfig;
import com.zaxxer.hikari.HikariDataSource;
import io.github.jukejuke.tool.file.FileUtils;
import io.github.jukejuke.tool.string.StringUtils;
import lombok.extern.slf4j.Slf4j;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.nio.charset.Charset;
import java.nio.charset.StandardCharsets;
import java.sql.*;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * MySQL工具类
 * 封装JDBC数据库操作，提供便捷的SQL执行和查询功能
 * 使用HikariCP连接池管理连接
 */
@Slf4j
public class MysqlUtils {

    /**
     * HikariCP数据源
     */
    private static HikariDataSource dataSource;

    /**
     * 配置信息
     */
    private static MysqlConfig config;

    /**
     * 初始化工具类（从默认配置文件加载）
     * @throws Exception 读取配置文件或初始化连接池时发生异常
     */
    public static void init() throws Exception {
        init(MysqlConfig.loadFromDefaultConfig());
    }

    /**
     * 初始化工具类（从指定配置文件加载）
     * @param configFilePath 配置文件路径
     * @throws Exception 读取配置文件或初始化连接池时发生异常
     */
    public static void init(String configFilePath) throws Exception {
        init(MysqlConfig.loadFromConfig(configFilePath));
    }

    /**
     * 初始化工具类（使用指定配置对象）
     * @param mysqlConfig MySQL配置对象
     */
    public static void init(MysqlConfig mysqlConfig) {
        if (mysqlConfig == null) {
            throw new IllegalArgumentException("MySQL配置不能为空");
        }
        config = mysqlConfig;
        
        // 如果已经初始化，先关闭旧的连接池
        if (dataSource != null && !dataSource.isClosed()) {
            dataSource.close();
        }
        
        // 初始化HikariCP连接池
        HikariConfig hikariConfig = new HikariConfig();
        hikariConfig.setJdbcUrl(config.getUrl());
        hikariConfig.setUsername(config.getUser());
        hikariConfig.setPassword(config.getPass());
        hikariConfig.setMinimumIdle(config.getMinimumIdle());
        hikariConfig.setMaximumPoolSize(config.getMaximumPoolSize());
        hikariConfig.setConnectionTimeout(config.getConnectionTimeout());
        hikariConfig.setIdleTimeout(config.getIdleTimeout());
        hikariConfig.setMaxLifetime(config.getMaxLifetime());
        hikariConfig.setConnectionTestQuery(config.getConnectionTestQuery());
        if (config.isConnectionTestOnBorrow()) {
            hikariConfig.addDataSourceProperty("testOnBorrow", "true");
        }
        
        dataSource = new HikariDataSource(hikariConfig);
        log.info("MySQL工具类初始化成功，连接池已启动");
    }

    /**
     * 获取数据库连接
     * @return Connection对象
     * @throws SQLException 获取连接时发生异常
     */
    public static Connection getConnection() throws SQLException {
        if (dataSource == null || dataSource.isClosed()) {
            throw new IllegalStateException("MySQL工具类未初始化或已关闭，请先调用init方法");
        }
        return dataSource.getConnection();
    }

    /**
     * 关闭资源（不关闭连接池）
     * @param conn Connection对象
     * @param stmt Statement对象
     * @param rs ResultSet对象
     */
    private static void close(Connection conn, Statement stmt, ResultSet rs) {
        try {
            if (rs != null) rs.close();
        } catch (SQLException e) {
            log.warn("关闭ResultSet失败", e);
        }
        try {
            if (stmt != null) stmt.close();
        } catch (SQLException e) {
            log.warn("关闭Statement失败", e);
        }
        try {
            if (conn != null) conn.close();
        } catch (SQLException e) {
            log.warn("关闭Connection失败", e);
        }
    }

    /**
     * 关闭连接池
     */
    public static void shutdown() {
        if (dataSource != null && !dataSource.isClosed()) {
            dataSource.close();
            log.info("MySQL连接池已关闭");
        }
    }

    /**
     * 记录SQL日志
     * @param sql SQL语句
     * @param params 参数
     */
    private static void logSql(String sql, Object... params) {
        if (!config.isShowSql()) {
            return;
        }

        String sqlToLog = sql;
        if (config.isFormatSql()) {
            sqlToLog = formatSql(sql);
        }

        String logMessage = "执行SQL: " + sqlToLog;
        if (config.isShowParams() && params != null && params.length > 0) {
            StringBuilder paramsStr = new StringBuilder();
            for (int i = 0; i < params.length; i++) {
                paramsStr.append("[").append(i).append("] ").append(params[i]);
                if (i < params.length - 1) {
                    paramsStr.append(", ");
                }
            }
            logMessage += "\n参数: " + paramsStr;
        }

        String level = config.getSqlLevel().toLowerCase();
        switch (level) {
            case "info":
                log.info(logMessage);
                break;
            case "warn":
                log.warn(logMessage);
                break;
            case "error":
                log.error(logMessage);
                break;
            default:
                log.debug(logMessage);
        }
    }

    /**
     * 格式化SQL语句（简单格式化）
     * @param sql SQL语句
     * @return 格式化后的SQL
     */
//    private static String formatSql(String sql) {
//        if (sql == null) {
//            return null;
//        }
//        return sql.replaceAll("\\s+", " ").trim();
//    }

    /**
     * 设置PreparedStatement参数
     * @param pstmt PreparedStatement对象
     * @param params 参数
     * @throws SQLException 设置参数时发生异常
     */
    private static void setParams(PreparedStatement pstmt, Object... params) throws SQLException {
        if (params != null && params.length > 0) {
            for (int i = 0; i < params.length; i++) {
                pstmt.setObject(i + 1, params[i]);
            }
        }
    }

    /**
     * 执行更新操作（INSERT、UPDATE、DELETE）
     * @param sql SQL语句
     * @param params 参数
     * @return 影响的行数
     * @throws SQLException 执行SQL时发生异常
     */
    public static int update(String sql, Object... params) throws SQLException {
        logSql(sql, params);
        Connection conn = null;
        PreparedStatement pstmt = null;
        try {
            conn = getConnection();
            pstmt = conn.prepareStatement(sql);
            setParams(pstmt, params);
            return pstmt.executeUpdate();
        } finally {
            close(conn, pstmt, null);
        }
    }

    /**
     * 执行查询，返回对象列表
     * @param sql SQL语句
     * @param params 参数
     * @return 查询结果列表
     * @throws SQLException 执行SQL时发生异常
     */
    public static List<Map<String, Object>> selectList(String sql, Object... params) throws SQLException {
        logSql(sql, params);
        Connection conn = null;
        PreparedStatement pstmt = null;
        ResultSet rs = null;
        List<Map<String, Object>> resultList = new ArrayList<>();
        try {
            conn = getConnection();
            pstmt = conn.prepareStatement(sql);
            setParams(pstmt, params);
            rs = pstmt.executeQuery();
            ResultSetMetaData metaData = rs.getMetaData();
            int columnCount = metaData.getColumnCount();
            while (rs.next()) {
                Map<String, Object> row = new HashMap<>();
                for (int i = 1; i <= columnCount; i++) {
                    String columnName = metaData.getColumnName(i);
                    row.put(columnName, rs.getObject(i));
                }
                resultList.add(row);
            }
            return resultList;
        } finally {
            close(conn, pstmt, rs);
        }
    }

    /**
     * 执行查询，返回单个对象
     * @param sql SQL语句
     * @param params 参数
     * @return 查询结果
     * @throws SQLException 执行SQL时发生异常
     */
    public static Map<String, Object> selectOne(String sql, Object... params) throws SQLException {
        List<Map<String, Object>> list = selectList(sql, params);
        return list.isEmpty() ? null : list.get(0);
    }

    /**
     * 获取当前连接池活跃连接数
     * @return 活跃连接数
     */
    public static int getActiveConnections() {
        return dataSource != null ? dataSource.getHikariPoolMXBean().getActiveConnections() : 0;
    }

    /**
     * 获取当前连接池空闲连接数
     * @return 空闲连接数
     */
    public static int getIdleConnections() {
        return dataSource != null ? dataSource.getHikariPoolMXBean().getIdleConnections() : 0;
    }

    /**
     * 从文件读取SQL语句（默认UTF-8编码）
     * @param filePath SQL文件路径
     * @return SQL语句
     * @throws IOException 读取文件时发生异常
     */
    public static String readSqlFromFile(String filePath) throws IOException {
        return readSqlFromFile(filePath, StandardCharsets.UTF_8);
    }

    /**
     * 从文件读取SQL语句
     * @param filePath SQL文件路径
     * @param charset 字符集
     * @return SQL语句
     * @throws IOException 读取文件时发生异常
     */
    public static String readSqlFromFile(String filePath, Charset charset) throws IOException {
        if (StringUtils.isEmpty(filePath)) {
            throw new IllegalArgumentException("SQL文件路径不能为空");
        }
        String sql = FileUtils.readFile(filePath, charset);
        if (sql == null) {
            throw new IOException("读取SQL文件失败：" + filePath);
        }
        log.debug("从文件读取SQL成功: {}", filePath);
        return sql.trim();
    }

    /**
     * 从classpath资源读取SQL语句（默认UTF-8编码）
     * @param resourcePath 资源路径
     * @return SQL语句
     * @throws IOException 读取资源时发生异常
     */
    public static String readSqlFromResource(String resourcePath) throws IOException {
        return readSqlFromResource(resourcePath, StandardCharsets.UTF_8);
    }

    /**
     * 从classpath资源读取SQL语句
     * @param resourcePath 资源路径
     * @param charset 字符集
     * @return SQL语句
     * @throws IOException 读取资源时发生异常
     */
    public static String readSqlFromResource(String resourcePath, Charset charset) throws IOException {
        if (StringUtils.isEmpty(resourcePath)) {
            throw new IllegalArgumentException("SQL资源路径不能为空");
        }
        StringBuilder sql = new StringBuilder();
        try (InputStream is = MysqlUtils.class.getClassLoader().getResourceAsStream(resourcePath)) {
            if (is == null) {
                throw new IOException("找不到SQL资源文件：" + resourcePath);
            }
            try (BufferedReader reader = new BufferedReader(new InputStreamReader(is, charset))) {
                String line;
                while ((line = reader.readLine()) != null) {
                    sql.append(line).append(System.lineSeparator());
                }
            }
        }
        log.debug("从资源读取SQL成功: {}", resourcePath);
        return sql.toString().trim();
    }

    /**
     * 将SQL语句写入文件（默认UTF-8编码，覆盖模式）
     * @param sql SQL语句
     * @param filePath 文件路径
     * @throws IOException 写入文件时发生异常
     */
    public static void writeSqlToFile(String sql, String filePath) throws IOException {
        writeSqlToFile(sql, filePath, StandardCharsets.UTF_8, false);
    }

    /**
     * 将SQL语句写入文件（默认UTF-8编码）
     * @param sql SQL语句
     * @param filePath 文件路径
     * @param append 是否追加模式
     * @throws IOException 写入文件时发生异常
     */
    public static void writeSqlToFile(String sql, String filePath, boolean append) throws IOException {
        writeSqlToFile(sql, filePath, StandardCharsets.UTF_8, append);
    }

    /**
     * 将SQL语句写入文件
     * @param sql SQL语句
     * @param filePath 文件路径
     * @param charset 字符集
     * @param append 是否追加模式
     * @throws IOException 写入文件时发生异常
     */
    public static void writeSqlToFile(String sql, String filePath, Charset charset, boolean append) throws IOException {
        if (StringUtils.isEmpty(filePath)) {
            throw new IllegalArgumentException("文件路径不能为空");
        }
        if (sql == null) {
            throw new IllegalArgumentException("SQL语句不能为空");
        }
        boolean success = FileUtils.writeFile(filePath, sql, charset, append);
        if (!success) {
            throw new IOException("写入SQL文件失败：" + filePath);
        }
        log.debug("写入SQL到文件成功: {}", filePath);
    }

    /**
     * 从文件读取SQL并执行查询，返回对象列表
     * @param sqlFilePath SQL文件路径
     * @param params 参数
     * @return 查询结果列表
     * @throws IOException 读取文件时发生异常
     * @throws SQLException 执行SQL时发生异常
     */
    public static List<Map<String, Object>> selectListFromFile(String sqlFilePath, Object... params) throws IOException, SQLException {
        String sql = readSqlFromFile(sqlFilePath);
        return selectList(sql, params);
    }

    /**
     * 从文件读取SQL并执行查询，返回单个对象
     * @param sqlFilePath SQL文件路径
     * @param params 参数
     * @return 查询结果
     * @throws IOException 读取文件时发生异常
     * @throws SQLException 执行SQL时发生异常
     */
    public static Map<String, Object> selectOneFromFile(String sqlFilePath, Object... params) throws IOException, SQLException {
        String sql = readSqlFromFile(sqlFilePath);
        return selectOne(sql, params);
    }

    /**
     * 从文件读取SQL并执行更新操作（INSERT、UPDATE、DELETE）
     * @param sqlFilePath SQL文件路径
     * @param params 参数
     * @return 影响的行数
     * @throws IOException 读取文件时发生异常
     * @throws SQLException 执行SQL时发生异常
     */
    public static int updateFromFile(String sqlFilePath, Object... params) throws IOException, SQLException {
        String sql = readSqlFromFile(sqlFilePath);
        return update(sql, params);
    }

    /**
     * 从classpath资源读取SQL并执行查询，返回对象列表
     * @param resourcePath 资源路径
     * @param params 参数
     * @return 查询结果列表
     * @throws IOException 读取资源时发生异常
     * @throws SQLException 执行SQL时发生异常
     */
    public static List<Map<String, Object>> selectListFromResource(String resourcePath, Object... params) throws IOException, SQLException {
        String sql = readSqlFromResource(resourcePath);
        return selectList(sql, params);
    }

    /**
     * 从classpath资源读取SQL并执行查询，返回单个对象
     * @param resourcePath 资源路径
     * @param params 参数
     * @return 查询结果
     * @throws IOException 读取资源时发生异常
     * @throws SQLException 执行SQL时发生异常
     */
    public static Map<String, Object> selectOneFromResource(String resourcePath, Object... params) throws IOException, SQLException {
        String sql = readSqlFromResource(resourcePath);
        return selectOne(sql, params);
    }

    /**
     * 从classpath资源读取SQL并执行更新操作（INSERT、UPDATE、DELETE）
     * @param resourcePath 资源路径
     * @param params 参数
     * @return 影响的行数
     * @throws IOException 读取资源时发生异常
     * @throws SQLException 执行SQL时发生异常
     */
    public static int updateFromResource(String resourcePath, Object... params) throws IOException, SQLException {
        String sql = readSqlFromResource(resourcePath);
        return update(sql, params);
    }

    /**
     * 格式化SQL语句（保留换行和缩进，只移除多余空白）
     * @param sql SQL语句
     * @return 格式化后的SQL
     */
    private static String formatSql(String sql) {
        if (sql == null) {
            return null;
        }
        // 保留原有的换行，只处理每行的首尾空白
        String[] lines = sql.split(System.lineSeparator());
        StringBuilder formatted = new StringBuilder();
        for (int i = 0; i < lines.length; i++) {
            String trimmedLine = lines[i].trim();
            if (trimmedLine.isEmpty()) {
                continue;
            }
            if (i > 0) {
                formatted.append(System.lineSeparator());
            }
            formatted.append(trimmedLine);
        }
        return formatted.toString();
    }
}
