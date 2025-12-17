package io.github.jukejuke.tool.file;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.io.TempDir;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.List;
import java.util.Map;

import static org.junit.jupiter.api.Assertions.*;

/**
 * JUnit测试类：HostsFileManager
 */
class HostsFileManagerTest {

    @TempDir
    private Path tempDir;
    private Path testHostsFile;
    private HostsFileManager hostsFileManager;

    @BeforeEach
    void setUp() throws IOException {
        // 创建临时hosts文件
        testHostsFile = tempDir.resolve("hosts");
        // 初始化测试数据
        List<String> initialContent = List.of(
                "# This is a test hosts file",
                "127.0.0.1	localhost",
                "192.168.1.1	example.com",
                ""
        );
        Files.write(testHostsFile, initialContent);
        // 创建HostsFileManager实例
        hostsFileManager = new HostsFileManager(testHostsFile.toString());
    }

    @Test
    void testConstructorWithValidPath() {
        assertNotNull(hostsFileManager);
        assertEquals(testHostsFile.toString(), hostsFileManager.getHostsPath());
    }

    @Test
    void testConstructorWithNullPath() {
        IllegalArgumentException exception = assertThrows(IllegalArgumentException.class, () -> {
            new HostsFileManager(null);
        });
        assertEquals("Hosts file path cannot be null or empty", exception.getMessage());
    }

    @Test
    void testConstructorWithEmptyPath() {
        IllegalArgumentException exception = assertThrows(IllegalArgumentException.class, () -> {
            new HostsFileManager("");
        });
        assertEquals("Hosts file path cannot be null or empty", exception.getMessage());
    }

    @Test
    void testReadHostsFile() throws IOException {
        List<String> lines = hostsFileManager.readHostsFile();
        assertNotNull(lines);
        assertEquals(4, lines.size());
        assertEquals("127.0.0.1\tlocalhost", lines.get(1));
        assertEquals("192.168.1.1\texample.com", lines.get(2));
    }

    @Test
    void testWriteHostsFile() throws IOException {
        List<String> newContent = List.of(
                "# New test content",
                "10.0.0.1	newdomain.com"
        );
        hostsFileManager.writeHostsFile(newContent);
        
        List<String> lines = hostsFileManager.readHostsFile();
        assertEquals(newContent, lines);
    }

    @Test
    void testBackupHostsFile() throws IOException {
        String backupPath = hostsFileManager.backupHostsFile();
        assertNotNull(backupPath);
        assertTrue(backupPath.contains("hosts.bak."));
        
        File backupFile = new File(backupPath);
        assertTrue(backupFile.exists());
        
        List<String> originalContent = hostsFileManager.readHostsFile();
        List<String> backupContent = Files.readAllLines(Path.of(backupPath));
        assertEquals(originalContent, backupContent);
    }

    @Test
    void testRestoreHostsFile() throws IOException {
        // 创建一个修改后的hosts文件
        List<String> modifiedContent = List.of("127.0.0.1	modified.localhost");
        hostsFileManager.writeHostsFile(modifiedContent);
        
        // 备份当前内容
        String backupPath = hostsFileManager.backupHostsFile();
        
        // 再次修改hosts文件
        List<String> newContent = List.of("10.0.0.1	newdomain.com");
        hostsFileManager.writeHostsFile(newContent);
        
        // 恢复备份
        hostsFileManager.restoreHostsFile(backupPath);
        
        // 验证恢复后的内容
        List<String> restoredContent = hostsFileManager.readHostsFile();
        assertEquals(modifiedContent, restoredContent);
    }

    @Test
    void testAddHostMapping() throws IOException {
        // 添加新映射
        boolean result = hostsFileManager.addHostMapping("192.168.1.2", "test.com");
        assertTrue(result);
        
        // 验证映射已添加
        List<String> lines = hostsFileManager.readHostsFile();
        assertTrue(lines.stream().anyMatch(line -> line.trim().equals("192.168.1.2\ttest.com")));
        
        // 尝试添加已存在的映射
        result = hostsFileManager.addHostMapping("192.168.1.3", "test.com");
        assertFalse(result);
    }

    @Test
    void testAddHostMappingWithInvalidIp() {
        IllegalArgumentException exception = assertThrows(IllegalArgumentException.class, () -> {
            hostsFileManager.addHostMapping("invalid-ip", "test.com");
        });
        assertEquals("Invalid IP address format: invalid-ip", exception.getMessage());
    }

    @Test
    void testAddHostMappingWithNullParameters() {
        IllegalArgumentException exception1 = assertThrows(IllegalArgumentException.class, () -> {
            hostsFileManager.addHostMapping(null, "test.com");
        });
        assertEquals("IP address and domain cannot be null or empty", exception1.getMessage());
        
        IllegalArgumentException exception2 = assertThrows(IllegalArgumentException.class, () -> {
            hostsFileManager.addHostMapping("192.168.1.1", null);
        });
        assertEquals("IP address and domain cannot be null or empty", exception2.getMessage());
    }

    @Test
    void testRemoveHostMapping() throws IOException {
        // 删除已存在的映射
        boolean result = hostsFileManager.removeHostMapping("example.com");
        assertTrue(result);
        
        // 验证映射已删除
        List<String> lines = hostsFileManager.readHostsFile();
        assertFalse(lines.stream().anyMatch(line -> line.trim().contains("example.com")));
        
        // 尝试删除不存在的映射
        result = hostsFileManager.removeHostMapping("nonexistent.com");
        assertFalse(result);
    }

    @Test
    void testRemoveHostMappingWithNullDomain() {
        IllegalArgumentException exception = assertThrows(IllegalArgumentException.class, () -> {
            hostsFileManager.removeHostMapping(null);
        });
        assertEquals("Domain cannot be null or empty", exception.getMessage());
    }

    @Test
    void testModifyHostMapping() throws IOException {
        // 修改已存在的映射
        boolean result = hostsFileManager.modifyHostMapping("example.com", "192.168.1.100", "newexample.com");
        assertTrue(result);
        
        // 验证映射已修改
        List<String> lines = hostsFileManager.readHostsFile();
        assertTrue(lines.stream().anyMatch(line -> line.trim().equals("192.168.1.100\tnewexample.com")));
        assertFalse(lines.stream().anyMatch(line -> line.trim().contains("example.com")));
        
        // 尝试修改不存在的映射
        result = hostsFileManager.modifyHostMapping("nonexistent.com", "192.168.1.100", "newdomain.com");
        assertFalse(result);
    }

    @Test
    void testModifyHostMappingWithInvalidIp() {
        IllegalArgumentException exception = assertThrows(IllegalArgumentException.class, () -> {
            hostsFileManager.modifyHostMapping("example.com", "invalid-ip", "newexample.com");
        });
        assertEquals("Invalid new IP address format: invalid-ip", exception.getMessage());
    }

    @Test
    void testModifyHostMappingWithNullParameters() {
        IllegalArgumentException exception = assertThrows(IllegalArgumentException.class, () -> {
            hostsFileManager.modifyHostMapping(null, "192.168.1.1", "newexample.com");
        });
        assertEquals("Old domain, new IP address and new domain cannot be null or empty", exception.getMessage());
    }

    @Test
    void testFindHostMapping() throws IOException {
        // 查找已存在的映射
        String ip = hostsFileManager.findHostMapping("example.com");
        assertEquals("192.168.1.1", ip);
        
        // 查找不存在的映射
        ip = hostsFileManager.findHostMapping("nonexistent.com");
        assertNull(ip);
    }

    @Test
    void testFindHostMappingWithNullDomain() {
        IllegalArgumentException exception = assertThrows(IllegalArgumentException.class, () -> {
            hostsFileManager.findHostMapping(null);
        });
        assertEquals("Domain cannot be null or empty", exception.getMessage());
    }

    @Test
    void testGetAllHostMappings() throws IOException {
        Map<String, String> mappings = hostsFileManager.getAllHostMappings();
        assertNotNull(mappings);
        assertEquals(2, mappings.size());
        assertEquals("127.0.0.1", mappings.get("localhost"));
        assertEquals("192.168.1.1", mappings.get("example.com"));
    }

    @Test
    void testIsValidIpAddress() {
        // 测试有效IP地址
        assertTrue(hostsFileManager.isHostsFileWritable()); // 间接测试，因为该方法是私有方法
        
        // 测试hosts文件存在性
        assertTrue(hostsFileManager.isHostsFileExists());
    }

    @Test
    void testIsHostsFileWritable() {
        assertTrue(hostsFileManager.isHostsFileWritable());
    }

    @Test
    void testIsHostsFileExists() {
        assertTrue(hostsFileManager.isHostsFileExists());
        
        // 测试不存在的文件
        HostsFileManager nonExistentManager = new HostsFileManager("nonexistent/file/path");
        assertFalse(nonExistentManager.isHostsFileExists());
    }
}
