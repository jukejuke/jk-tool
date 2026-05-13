package io.github.jukejuke.tool.mysql;

import io.github.jukejuke.tool.config.PropertiesUtils;
import lombok.Data;

import java.io.IOException;
import java.util.Properties;

/**
 * MySQL配置类
 * 用于存储MySQL数据库连接和日志配置信息
 */
@Data
public class MysqlConfig {

    /**
     * 默认配置文件名称
     */
    public static final String DEFAULT_CONFIG_FILE = "mysql-template.properties";

    /**
     * 数据库连接URL
     */
    private String url;

    /**
     * 数据库用户名
     */
    private String user;

    /**
     * 数据库密码
     */
    private String pass;

    /**
     * 是否在日志中显示执行的SQL，默认true
     */
    private boolean showSql = true;

    /**
     * 是否格式化显示的SQL，默认false
     */
    private boolean formatSql = false;

    /**
     * 是否显示SQL参数，默认true
     */
    private boolean showParams = true;

    /**
     * 打印SQL的日志等级，默认debug
     * 可选值：debug, info, warn, error
     */
    private String sqlLevel = "debug";

    /**
     * 最小空闲连接数，默认5
     */
    private int minimumIdle = 5;

    /**
     * 最大连接数，默认10
     */
    private int maximumPoolSize = 10;

    /**
     * 连接超时时间（毫秒），默认30000
     */
    private long connectionTimeout = 30000;

    /**
     * 空闲连接超时时间（毫秒），默认600000
     */
    private long idleTimeout = 600000;

    /**
     * 连接最大生存时间（毫秒），默认1800000
     */
    private long maxLifetime = 1800000;

    /**
     * 连接测试查询SQL，默认SELECT 1
     */
    private String connectionTestQuery = "SELECT 1";

    /**
     * 是否在获取连接时测试连接有效性，默认true
     */
    private boolean connectionTestOnBorrow = true;

    /**
     * 默认构造函数
     */
    public MysqlConfig() {
    }

    /**
     * 带参数的构造函数
     * @param url 数据库连接URL
     * @param user 数据库用户名
     * @param pass 数据库密码
     */
    public MysqlConfig(String url, String user, String pass) {
        this.url = url;
        this.user = user;
        this.pass = pass;
    }

    /**
     * 从默认配置文件加载配置
     * @return MysqlConfig配置对象
     * @throws IOException 读取配置文件时发生异常
     */
    public static MysqlConfig loadFromDefaultConfig() throws IOException {
        return loadFromConfig(DEFAULT_CONFIG_FILE);
    }

    /**
     * 从指定配置文件加载配置
     * @param configFilePath 配置文件路径
     * @return MysqlConfig配置对象
     * @throws IOException 读取配置文件时发生异常
     */
    public static MysqlConfig loadFromConfig(String configFilePath) throws IOException {
        Properties properties = PropertiesUtils.loadFromClassPath(configFilePath);
        MysqlConfig config = new MysqlConfig();
        config.setUrl(PropertiesUtils.getString(properties, "url"));
        config.setUser(PropertiesUtils.getString(properties, "user"));
        config.setPass(PropertiesUtils.getString(properties, "pass"));
        config.setShowSql(PropertiesUtils.getBoolean(properties, "showSql", true));
        config.setFormatSql(PropertiesUtils.getBoolean(properties, "formatSql", false));
        config.setShowParams(PropertiesUtils.getBoolean(properties, "showParams", true));
        config.setSqlLevel(PropertiesUtils.getString(properties, "sqlLevel", "debug"));
        config.setMinimumIdle(PropertiesUtils.getInteger(properties, "minimumIdle", 5));
        config.setMaximumPoolSize(PropertiesUtils.getInteger(properties, "maximumPoolSize", 10));
        config.setConnectionTimeout(PropertiesUtils.getLong(properties, "connectionTimeout", 30000L));
        config.setIdleTimeout(PropertiesUtils.getLong(properties, "idleTimeout", 600000L));
        config.setMaxLifetime(PropertiesUtils.getLong(properties, "maxLifetime", 1800000L));
        config.setConnectionTestQuery(PropertiesUtils.getString(properties, "connectionTestQuery", "SELECT 1"));
        config.setConnectionTestOnBorrow(PropertiesUtils.getBoolean(properties, "connectionTestOnBorrow", true));
        return config;
    }
}
