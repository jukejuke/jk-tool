package io.github.jukejuke.tool.image;

import io.github.jukejuke.tool.file.FileUtils;
import io.github.jukejuke.tool.log.LogUtil;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.File;

/**
 * ImageUtils 测试类
 */
public class ImageUtilsTest {

    public static void main(String[] args) {
        ImageUtilsTest test = new ImageUtilsTest();
        
        // 测试基础图片压缩
        test.testBasicCompress();
        
        // 测试指定质量压缩
        test.testCompressWithQuality();
        
        // 测试按尺寸压缩
        test.testCompressWithSize();
        
        // 测试按文件大小压缩
        test.testCompressToSize();
    }

    /**
     * 测试基础图片压缩
     */
    private void testBasicCompress() {
        LogUtil.info("=== 测试基础图片压缩 ===");
        
        String testImage = "test-image.jpg";
        String compressedImage = "test-image-compressed.jpg";
        
        try {
            // 创建测试图片
            createTestImage(testImage, 800, 600);
            LogUtil.info("创建测试图片: " + testImage);
            
            // 获取原始文件大小
            File originalFile = new File(testImage);
            long originalSize = originalFile.length();
            LogUtil.info("原始图片大小: " + originalSize + " 字节");
            
            // 压缩图片
            boolean result = ImageUtils.compressImage(testImage, compressedImage);
            LogUtil.info("压缩结果: " + result);
            
            // 检查压缩文件是否存在
            File compressedFile = new File(compressedImage);
            LogUtil.info("压缩文件存在: " + compressedFile.exists());
            if (compressedFile.exists()) {
                long compressedSize = compressedFile.length();
                LogUtil.info("压缩后图片大小: " + compressedSize + " 字节");
                double ratio = (1.0 - (double) compressedSize / originalSize) * 100;
                LogUtil.info(String.format("压缩率: %.2f%%", ratio));
            }
        } finally {
            // 清理
            FileUtils.deleteFile(testImage);
            FileUtils.deleteFile(compressedImage);
            LogUtil.info("清理完成\n");
        }
    }

    /**
     * 测试指定质量压缩
     */
    private void testCompressWithQuality() {
        LogUtil.info("=== 测试指定质量压缩 ===");
        
        String testImage = "test-quality.jpg";
        String compressedImage50 = "test-quality-50.jpg";
        String compressedImage80 = "test-quality-80.jpg";
        String compressedImage95 = "test-quality-95.jpg";
        
        try {
            // 创建测试图片
            createTestImage(testImage, 800, 600);
            LogUtil.info("创建测试图片: " + testImage);
            
            // 获取原始文件大小
            File originalFile = new File(testImage);
            long originalSize = originalFile.length();
            LogUtil.info("原始图片大小: " + originalSize + " 字节");
            
            // 50% 质量压缩
            boolean result50 = ImageUtils.compressImage(testImage, compressedImage50, 0.5f);
            LogUtil.info("50% 质量压缩结果: " + result50);
            File file50 = new File(compressedImage50);
            if (file50.exists()) {
                LogUtil.info("50% 质量压缩后大小: " + file50.length() + " 字节");
            }
            
            // 80% 质量压缩
            boolean result80 = ImageUtils.compressImage(testImage, compressedImage80, 0.8f);
            LogUtil.info("80% 质量压缩结果: " + result80);
            File file80 = new File(compressedImage80);
            if (file80.exists()) {
                LogUtil.info("80% 质量压缩后大小: " + file80.length() + " 字节");
            }
            
            // 95% 质量压缩
            boolean result95 = ImageUtils.compressImage(testImage, compressedImage95, 0.95f);
            LogUtil.info("95% 质量压缩结果: " + result95);
            File file95 = new File(compressedImage95);
            if (file95.exists()) {
                LogUtil.info("95% 质量压缩后大小: " + file95.length() + " 字节");
            }
        } finally {
            // 清理
            FileUtils.deleteFile(testImage);
            FileUtils.deleteFile(compressedImage50);
            FileUtils.deleteFile(compressedImage80);
            FileUtils.deleteFile(compressedImage95);
            LogUtil.info("清理完成\n");
        }
    }

    /**
     * 测试按尺寸压缩
     */
    private void testCompressWithSize() {
        LogUtil.info("=== 测试按尺寸压缩 ===");
        
        String testImage = "test-size.jpg";
        String compressedImage400 = "test-size-400.jpg";
        String compressedImage300x200 = "test-size-300x200.jpg";
        
        try {
            // 创建测试图片
            createTestImage(testImage, 800, 600);
            LogUtil.info("创建测试图片: 800x600");
            
            // 限制最大宽度为400
            boolean result400 = ImageUtils.compressImage(testImage, compressedImage400, 0.8f, 400);
            LogUtil.info("最大宽度400压缩结果: " + result400);
            
            // 限制最大宽度300和高度200
            boolean result300x200 = ImageUtils.compressImage(testImage, compressedImage300x200, 0.8f, 300, 200);
            LogUtil.info("最大尺寸300x200压缩结果: " + result300x200);
            
            // 检查文件是否存在
            LogUtil.info("400宽度文件存在: " + new File(compressedImage400).exists());
            LogUtil.info("300x200文件存在: " + new File(compressedImage300x200).exists());
        } finally {
            // 清理
            FileUtils.deleteFile(testImage);
            FileUtils.deleteFile(compressedImage400);
            FileUtils.deleteFile(compressedImage300x200);
            LogUtil.info("清理完成\n");
        }
    }

    /**
     * 测试按文件大小压缩
     */
    private void testCompressToSize() {
        LogUtil.info("=== 测试按文件大小压缩 ===");
        
        String testImage = "test-to-size.jpg";
        String compressedImage100 = "test-to-size-100kb.jpg";
        String compressedImage50 = "test-to-size-50kb.jpg";
        String compressedImage20 = "test-to-size-20kb.jpg";
        
        try {
            // 创建测试图片
            createTestImage(testImage, 1200, 900);
            LogUtil.info("创建测试图片: 1200x900");
            
            // 获取原始文件大小
            File originalFile = new File(testImage);
            long originalSize = originalFile.length() / 1024;
            LogUtil.info("原始图片大小: " + originalSize + " KB");
            
            // 压缩到100KB
            boolean result100 = ImageUtils.compressImageToSize(testImage, compressedImage100, 100);
            LogUtil.info("压缩到100KB结果: " + result100);
            File file100 = new File(compressedImage100);
            if (file100.exists()) {
                LogUtil.info("压缩后大小: " + (file100.length() / 1024) + " KB");
            }
            
            // 压缩到50KB
            boolean result50 = ImageUtils.compressImageToSize(testImage, compressedImage50, 50);
            LogUtil.info("压缩到50KB结果: " + result50);
            File file50 = new File(compressedImage50);
            if (file50.exists()) {
                LogUtil.info("压缩后大小: " + (file50.length() / 1024) + " KB");
            }
            
            // 压缩到20KB
            boolean result20 = ImageUtils.compressImageToSize(testImage, compressedImage20, 20);
            LogUtil.info("压缩到20KB结果: " + result20);
            File file20 = new File(compressedImage20);
            if (file20.exists()) {
                LogUtil.info("压缩后大小: " + (file20.length() / 1024) + " KB");
            }
        } finally {
            // 清理
            FileUtils.deleteFile(testImage);
            FileUtils.deleteFile(compressedImage100);
            FileUtils.deleteFile(compressedImage50);
            FileUtils.deleteFile(compressedImage20);
            LogUtil.info("清理完成");
        }
    }

    /**
     * 创建测试图片
     * @param filePath 图片路径
     * @param width 宽度
     * @param height 高度
     */
    private void createTestImage(String filePath, int width, int height) {
        try {
            BufferedImage image = new BufferedImage(width, height, BufferedImage.TYPE_INT_RGB);
            Graphics2D g2d = image.createGraphics();
            
            // 填充背景色
            g2d.setColor(Color.WHITE);
            g2d.fillRect(0, 0, width, height);
            
            // 绘制一些彩色图形
            g2d.setColor(Color.RED);
            g2d.fillOval(width / 4, height / 4, width / 2, height / 2);
            
            g2d.setColor(Color.GREEN);
            g2d.fillRect(width / 3, height / 3, width / 3, height / 3);
            
            g2d.setColor(Color.BLUE);
            g2d.fillPolygon(
                new int[]{width / 2, width / 4, width * 3 / 4},
                new int[]{height / 4, height * 3 / 4, height * 3 / 4},
                3
            );
            
            // 添加文字
            g2d.setColor(Color.BLACK);
            g2d.setFont(new Font("Arial", Font.BOLD, 24));
            g2d.drawString("Test Image", width / 2 - 60, height - 50);
            
            g2d.dispose();
            
            ImageIO.write(image, "jpg", new File(filePath));
        } catch (Exception e) {
            LogUtil.error("创建测试图片失败", e);
        }
    }
}
