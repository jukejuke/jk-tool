package io.github.jukejuke.tool.excel;

import org.junit.Test;

import java.io.ByteArrayOutputStream;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static org.junit.Assert.*;

/**
 * ExcelUtils 测试类
 * 测试 Excel 导出功能的各种场景
 */
public class ExcelUtilsTest {

    /**
     * 测试基本的 Excel 导出功能
     */
    @Test
    public void testExportBasic() {
        // 创建测试数据
        List<User> userList = new ArrayList<>();
        userList.add(new User(1, "张三", 25, "男"));
        userList.add(new User(2, "李四", 30, "女"));
        userList.add(new User(3, "王五", 35, "男"));

        // 创建表头映射
        Map<String, String> headers = new HashMap<>();
        headers.put("id", "ID");
        headers.put("name", "姓名");
        headers.put("age", "年龄");
        headers.put("gender", "性别");

        // 导出到字节数组输出流
        ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
        try {
            ExcelUtils.export(userList, headers, "用户信息", outputStream);
            // 验证导出结果
            byte[] excelBytes = outputStream.toByteArray();
            assertNotNull(excelBytes);
            assertTrue(excelBytes.length > 0);
            System.out.println("Excel 导出成功，文件大小：" + excelBytes.length + " 字节");
        } catch (Exception e) {
            e.printStackTrace();
            fail("Excel 导出失败：" + e.getMessage());
        } finally {
            try {
                outputStream.close();
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }

    /**
     * 测试空数据列表的情况
     */
    @Test(expected = IllegalArgumentException.class)
    public void testExportEmptyData() throws Exception {
        // 创建空数据列表
        List<User> userList = new ArrayList<>();

        // 创建表头映射
        Map<String, String> headers = new HashMap<>();
        headers.put("id", "ID");
        headers.put("name", "姓名");

        // 导出到字节数组输出流
        ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
        ExcelUtils.export(userList, headers, "用户信息", outputStream);
    }

    /**
     * 测试空表头映射的情况
     */
    @Test(expected = IllegalArgumentException.class)
    public void testExportEmptyHeaders() throws Exception {
        // 创建测试数据
        List<User> userList = new ArrayList<>();
        userList.add(new User(1, "张三", 25, "男"));

        // 创建空表头映射
        Map<String, String> headers = new HashMap<>();

        // 导出到字节数组输出流
        ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
        ExcelUtils.export(userList, headers, "用户信息", outputStream);
    }

    /**
     * 测试空输出流的情况
     */
    @Test(expected = IllegalArgumentException.class)
    public void testExportNullOutputStream() throws Exception {
        // 创建测试数据
        List<User> userList = new ArrayList<>();
        userList.add(new User(1, "张三", 25, "男"));

        // 创建表头映射
        Map<String, String> headers = new HashMap<>();
        headers.put("id", "ID");
        headers.put("name", "姓名");

        ExcelUtils.export(userList, headers, "用户信息", null);
    }

    /**
     * 测试使用注解配置的 Excel 导出功能
     */
    @Test
    public void testExportWithAnnotation() throws Exception {
        // 创建测试数据
        List<UserWithAnnotation> userList = new ArrayList<>();
        userList.add(new UserWithAnnotation(1, "张三", 25, "男", new java.util.Date()));
        userList.add(new UserWithAnnotation(2, "李四", 30, "女", new java.util.Date()));
        userList.add(new UserWithAnnotation(3, "王五", 35, null, new java.util.Date())); // 测试默认值

        // 导出到字节数组输出流
        ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
        try {
            ExcelUtils.exportWithAnnotation(userList, "用户信息", outputStream);
            // 验证导出结果
            byte[] excelBytes = outputStream.toByteArray();
            assertNotNull(excelBytes);
            assertTrue(excelBytes.length > 0);
            System.out.println("使用注解配置的 Excel 导出成功，文件大小：" + excelBytes.length + " 字节");
        } catch (Exception e) {
            e.printStackTrace();
            fail("使用注解配置的 Excel 导出失败：" + e.getMessage());
        } finally {
            try {
                outputStream.close();
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }

    /**
     * 测试导出到本地文件
     */
    @Test
    public void testExportToLocalFile() throws Exception {
        // 创建测试数据
        List<UserWithAnnotation> userList = new ArrayList<>();
        userList.add(new UserWithAnnotation(1, "张三", 25, "男", new java.util.Date()));
        userList.add(new UserWithAnnotation(2, "李四", 30, "女", new java.util.Date()));
        userList.add(new UserWithAnnotation(3, "王五", 35, null, new java.util.Date())); // 测试默认值

        // 创建本地文件路径（使用时间戳避免文件冲突）
        String fileName = "test_excel_export_" + System.currentTimeMillis() + ".xlsx";
        java.io.File file = new java.io.File(fileName);

        // 导出到本地文件
        try (java.io.FileOutputStream fileOutputStream = new java.io.FileOutputStream(file)) {
            ExcelUtils.exportWithAnnotation(userList, "用户信息", fileOutputStream);
            // 验证文件是否存在
            assertTrue("导出文件不存在", file.exists());
            // 验证文件大小是否大于0
            assertTrue("导出文件大小为0", file.length() > 0);
            System.out.println("Excel 导出到本地文件成功，文件路径：" + file.getAbsolutePath() + "，文件大小：" + file.length() + " 字节");
        } catch (Exception e) {
            e.printStackTrace();
            fail("Excel 导出到本地文件失败：" + e.getMessage());
        } finally {
            // 清理测试文件
            if (file.exists()) {
//                boolean deleted = file.delete();
//                if (deleted) {
//                    System.out.println("测试文件已清理");
//                }
            }
        }
    }

    /**
     * 测试用户实体类
     */
    static class User {
        private int id;
        private String name;
        private int age;
        private String gender;

        public User(int id, String name, int age, String gender) {
            this.id = id;
            this.name = name;
            this.age = age;
            this.gender = gender;
        }

        public int getId() {
            return id;
        }

        public void setId(int id) {
            this.id = id;
        }

        public String getName() {
            return name;
        }

        public void setName(String name) {
            this.name = name;
        }

        public int getAge() {
            return age;
        }

        public void setAge(int age) {
            this.age = age;
        }

        public String getGender() {
            return gender;
        }

        public void setGender(String gender) {
            this.gender = gender;
        }
    }

    /**
     * 测试使用注解配置的用户实体类
     */
    static class UserWithAnnotation {
        @ExcelColumn(name = "用户ID", order = 1, width = 10)
        private int id;
        
        @ExcelColumn(name = "姓名", order = 2, width = 15)
        private String name;
        
        @ExcelColumn(name = "年龄", order = 3, width = 8)
        private int age;
        
        @ExcelColumn(name = "性别", order = 4, width = 8, defaultValue = "未知")
        private String gender;
        
        @ExcelColumn(name = "注册时间", order = 5, width = 20, format = "yyyy-MM-dd HH:mm:ss")
        private java.util.Date registerTime;

        public UserWithAnnotation(int id, String name, int age, String gender, java.util.Date registerTime) {
            this.id = id;
            this.name = name;
            this.age = age;
            this.gender = gender;
            this.registerTime = registerTime;
        }

        public int getId() {
            return id;
        }

        public void setId(int id) {
            this.id = id;
        }

        public String getName() {
            return name;
        }

        public void setName(String name) {
            this.name = name;
        }

        public int getAge() {
            return age;
        }

        public void setAge(int age) {
            this.age = age;
        }

        public String getGender() {
            return gender;
        }

        public void setGender(String gender) {
            this.gender = gender;
        }

        public java.util.Date getRegisterTime() {
            return registerTime;
        }

        public void setRegisterTime(java.util.Date registerTime) {
            this.registerTime = registerTime;
        }
    }
}
