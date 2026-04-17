package io.github.jukejuke.tool.image;

import io.github.jukejuke.tool.file.FileUtils;
import io.github.jukejuke.tool.string.StringUtils;
import lombok.extern.slf4j.Slf4j;

import javax.imageio.IIOImage;
import javax.imageio.ImageIO;
import javax.imageio.ImageWriteParam;
import javax.imageio.ImageWriter;
import javax.imageio.stream.ImageOutputStream;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.*;
import java.util.Iterator;

/**
 * 图片压缩工具类
 * 提供图片压缩、缩放等功能
 */
@Slf4j
public class ImageUtils {

    /** 默认图片质量 (0.0-1.0) */
    public static final float DEFAULT_QUALITY = 0.8f;

    /**
     * 压缩图片（保持原尺寸）
     * @param sourcePath 源图片路径
     * @param targetPath 目标图片路径
     * @return 是否压缩成功
     */
    public static boolean compressImage(String sourcePath, String targetPath) {
        return compressImage(sourcePath, targetPath, DEFAULT_QUALITY, -1, -1);
    }

    /**
     * 压缩图片（指定质量）
     * @param sourcePath 源图片路径
     * @param targetPath 目标图片路径
     * @param quality 压缩质量 (0.0-1.0)
     * @return 是否压缩成功
     */
    public static boolean compressImage(String sourcePath, String targetPath, float quality) {
        return compressImage(sourcePath, targetPath, quality, -1, -1);
    }

    /**
     * 压缩图片（指定质量和最大宽度）
     * @param sourcePath 源图片路径
     * @param targetPath 目标图片路径
     * @param quality 压缩质量 (0.0-1.0)
     * @param maxWidth 最大宽度（-1表示不限制）
     * @return 是否压缩成功
     */
    public static boolean compressImage(String sourcePath, String targetPath, float quality, int maxWidth) {
        return compressImage(sourcePath, targetPath, quality, maxWidth, -1);
    }

    /**
     * 压缩图片（完整参数）
     * @param sourcePath 源图片路径
     * @param targetPath 目标图片路径
     * @param quality 压缩质量 (0.0-1.0)
     * @param maxWidth 最大宽度（-1表示不限制）
     * @param maxHeight 最大高度（-1表示不限制）
     * @return 是否压缩成功
     */
    public static boolean compressImage(String sourcePath, String targetPath, float quality, int maxWidth, int maxHeight) {
        if (StringUtils.isEmpty(sourcePath) || StringUtils.isEmpty(targetPath)) {
            log.error("源图片路径或目标图片路径不能为空");
            return false;
        }

        File sourceFile = new File(sourcePath);
        if (!sourceFile.exists() || !sourceFile.isFile()) {
            log.error("源图片不存在或不是文件: {}", sourcePath);
            return false;
        }

        // 限制质量在合理范围内
        if (quality <= 0) {
            quality = DEFAULT_QUALITY;
        } else if (quality > 1) {
            quality = 1;
        }

        try {
            BufferedImage image = ImageIO.read(sourceFile);
            if (image == null) {
                log.error("无法读取图片: {}", sourcePath);
                return false;
            }

            // 确定目标尺寸
            int targetWidth = image.getWidth();
            int targetHeight = image.getHeight();

            // 计算缩放比例
            if (maxWidth > 0 && targetWidth > maxWidth) {
                float ratio = (float) maxWidth / targetWidth;
                targetWidth = maxWidth;
                targetHeight = (int) (targetHeight * ratio);
            }

            if (maxHeight > 0 && targetHeight > maxHeight) {
                float ratio = (float) maxHeight / targetHeight;
                targetHeight = maxHeight;
                targetWidth = (int) (targetWidth * ratio);
            }

            // 缩放图片
            BufferedImage scaledImage = scaleImage(image, targetWidth, targetHeight);

            // 获取文件扩展名
            String extension = FileUtils.getFileExtension(targetPath);
            if (StringUtils.isEmpty(extension)) {
                extension = "jpg";
            }

            // 写入图片
            writeImage(scaledImage, targetPath, extension, quality);

            log.info("图片压缩成功: {} -> {}, 质量: {}, 尺寸: {}x{}", 
                    sourcePath, targetPath, quality, targetWidth, targetHeight);
            return true;
        } catch (IOException e) {
            log.error("图片压缩失败", e);
            return false;
        }
    }

    /**
     * 压缩图片到指定大小
     * @param sourcePath 源图片路径
     * @param targetPath 目标图片路径
     * @param maxSizeKB 最大文件大小（KB）
     * @return 是否压缩成功
     */
    public static boolean compressImageToSize(String sourcePath, String targetPath, int maxSizeKB) {
        if (StringUtils.isEmpty(sourcePath) || StringUtils.isEmpty(targetPath)) {
            log.error("源图片路径或目标图片路径不能为空");
            return false;
        }

        if (maxSizeKB <= 0) {
            log.error("最大文件大小必须大于0");
            return false;
        }

        // 获取文件扩展名
        String extension = FileUtils.getFileExtension(targetPath);
        if (StringUtils.isEmpty(extension)) {
            extension = "jpg";
        }

        try {
            BufferedImage image = ImageIO.read(new File(sourcePath));
            if (image == null) {
                log.error("无法读取图片: {}", sourcePath);
                return false;
            }

            // 逐步降低质量直到达到目标大小
            float quality = 1.0f;
            while (quality > 0.05f) {
                ByteArrayOutputStream baos = new ByteArrayOutputStream();
                writeImageToStream(image, baos, extension, quality);
                int currentSizeKB = baos.size() / 1024;

                if (currentSizeKB <= maxSizeKB) {
                    // 写入最终图片
                    try (FileOutputStream fos = new FileOutputStream(targetPath)) {
                        baos.writeTo(fos);
                    }
                    log.info("图片压缩成功: {} -> {}, 目标大小: {}KB, 实际大小: {}KB, 质量: {}", 
                            sourcePath, targetPath, maxSizeKB, currentSizeKB, quality);
                    return true;
                }

                // 降低质量
                quality -= 0.1f;
            }

            // 如果质量降到很低还没达到目标，尝试缩小图片
            int currentWidth = image.getWidth();
            int currentHeight = image.getHeight();
            while (currentWidth > 100 && currentHeight > 100) {
                currentWidth = (int) (currentWidth * 0.9);
                currentHeight = (int) (currentHeight * 0.9);

                BufferedImage scaledImage = scaleImage(image, currentWidth, currentHeight);
                ByteArrayOutputStream baos = new ByteArrayOutputStream();
                writeImageToStream(scaledImage, baos, extension, 0.5f);
                int currentSizeKB = baos.size() / 1024;

                if (currentSizeKB <= maxSizeKB) {
                    // 写入最终图片
                    try (FileOutputStream fos = new FileOutputStream(targetPath)) {
                        baos.writeTo(fos);
                    }
                    log.info("图片压缩成功: {} -> {}, 目标大小: {}KB, 实际大小: {}KB, 尺寸: {}x{}", 
                            sourcePath, targetPath, maxSizeKB, currentSizeKB, currentWidth, currentHeight);
                    return true;
                }
            }

            log.warn("无法将图片压缩到指定大小: {}KB", maxSizeKB);
            return false;
        } catch (IOException e) {
            log.error("图片压缩失败", e);
            return false;
        }
    }

    /**
     * 缩放图片
     * @param image 源图片
     * @param targetWidth 目标宽度
     * @param targetHeight 目标高度
     * @return 缩放后的图片
     */
    private static BufferedImage scaleImage(BufferedImage image, int targetWidth, int targetHeight) {
        Image scaledImage = image.getScaledInstance(targetWidth, targetHeight, Image.SCALE_SMOOTH);
        BufferedImage result = new BufferedImage(targetWidth, targetHeight, BufferedImage.TYPE_INT_RGB);
        
        Graphics2D g2d = result.createGraphics();
        g2d.setRenderingHint(RenderingHints.KEY_INTERPOLATION, RenderingHints.VALUE_INTERPOLATION_BILINEAR);
        g2d.setRenderingHint(RenderingHints.KEY_RENDERING, RenderingHints.VALUE_RENDER_QUALITY);
        g2d.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
        
        g2d.drawImage(scaledImage, 0, 0, null);
        g2d.dispose();
        
        return result;
    }

    /**
     * 写入图片到文件
     * @param image 图片对象
     * @param filePath 目标文件路径
     * @param formatName 图片格式
     * @param quality 压缩质量
     * @throws IOException IO异常
     */
    private static void writeImage(BufferedImage image, String filePath, String formatName, float quality) throws IOException {
        // 创建父目录
        File parentDir = new File(filePath).getParentFile();
        if (parentDir != null && !parentDir.exists()) {
            parentDir.mkdirs();
        }

        try (FileOutputStream fos = new FileOutputStream(filePath)) {
            writeImageToStream(image, fos, formatName, quality);
        }
    }

    /**
     * 写入图片到输出流
     * @param image 图片对象
     * @param outputStream 输出流
     * @param formatName 图片格式
     * @param quality 压缩质量
     * @throws IOException IO异常
     */
    private static void writeImageToStream(BufferedImage image, OutputStream outputStream, String formatName, float quality) throws IOException {
        // 获取图片写入器
        Iterator<ImageWriter> writers = ImageIO.getImageWritersByFormatName(formatName);
        if (!writers.hasNext()) {
            // 如果找不到指定格式的写入器，尝试使用JPG
            writers = ImageIO.getImageWritersByFormatName("jpg");
            if (!writers.hasNext()) {
                throw new IOException("不支持的图片格式: " + formatName);
            }
        }

        ImageWriter writer = writers.next();
        ImageWriteParam writeParam = writer.getDefaultWriteParam();

        // 设置压缩参数
        if (writeParam.canWriteCompressed()) {
            writeParam.setCompressionMode(ImageWriteParam.MODE_EXPLICIT);
            writeParam.setCompressionQuality(quality);
        }

        try (ImageOutputStream ios = ImageIO.createImageOutputStream(outputStream)) {
            writer.setOutput(ios);
            writer.write(null, new IIOImage(image, null, null), writeParam);
        } finally {
            writer.dispose();
        }
    }
}
