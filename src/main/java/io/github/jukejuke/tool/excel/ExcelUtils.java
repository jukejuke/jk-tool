package io.github.jukejuke.tool.excel;

import lombok.extern.slf4j.Slf4j;
import org.apache.poi.ss.usermodel.*;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

import java.io.IOException;
import java.io.OutputStream;
import java.lang.reflect.Field;
import java.text.SimpleDateFormat;
import java.util.*;

/**
 * Excel 导出工具类
 * 提供 Excel 文件的创建、数据填充和导出功能
 */
@Slf4j
public class ExcelUtils {

    /**
     * 导出列表数据到 Excel 文件
     * @param dataList 数据列表
     * @param headers 表头映射，key 为字段名，value 为表头名称
     * @param sheetName 工作表名称
     * @param outputStream 输出流
     * @param <T> 数据类型
     * @throws Exception 导出过程中发生的异常
     */
    public static <T> void export(List<T> dataList, Map<String, String> headers, String sheetName, OutputStream outputStream) throws Exception {
        if (dataList == null || dataList.isEmpty()) {
            throw new IllegalArgumentException("数据列表不能为空");
        }
        if (headers == null || headers.isEmpty()) {
            throw new IllegalArgumentException("表头映射不能为空");
        }
        if (outputStream == null) {
            throw new IllegalArgumentException("输出流不能为空");
        }

        // 创建工作簿
        Workbook workbook = new XSSFWorkbook();
        try {
            // 创建工作表
            Sheet sheet = workbook.createSheet(sheetName);

            // 创建表头行
            Row headerRow = sheet.createRow(0);
            int colIndex = 0;

            // 填充表头
            for (Map.Entry<String, String> entry : headers.entrySet()) {
                Cell cell = headerRow.createCell(colIndex);
                cell.setCellValue(entry.getValue());
                // 设置表头样式
                cell.setCellStyle(createHeaderCellStyle(workbook));
                colIndex++;
            }

            // 填充数据
            int rowIndex = 1;
            for (T data : dataList) {
                Row dataRow = sheet.createRow(rowIndex);
                colIndex = 0;
                
                for (String fieldName : headers.keySet()) {
                    Cell cell = dataRow.createCell(colIndex);
                    Object value = getFieldValue(data, fieldName);
                    setCellValue(cell, value);
                    colIndex++;
                }
                rowIndex++;
            }

            // 自动调整列宽
            for (int i = 0; i < headers.size(); i++) {
                sheet.autoSizeColumn(i);
            }

            // 写入输出流
            workbook.write(outputStream);
            log.info("Excel 导出成功，导出数据条数：{}", dataList.size());
        } catch (Exception e) {
            log.error("Excel 导出失败", e);
            throw e;
        } finally {
            try {
                if (workbook != null) {
                    workbook.close();
                }
            } catch (IOException e) {
                log.error("关闭工作簿失败", e);
            }
        }
    }

    /**
     * 导出列表数据到 Excel 文件（使用注解配置）
     * @param dataList 数据列表
     * @param sheetName 工作表名称
     * @param outputStream 输出流
     * @param <T> 数据类型
     * @throws Exception 导出过程中发生的异常
     */
    public static <T> void exportWithAnnotation(List<T> dataList, String sheetName, OutputStream outputStream) throws Exception {
        if (dataList == null || dataList.isEmpty()) {
            throw new IllegalArgumentException("数据列表不能为空");
        }
        if (outputStream == null) {
            throw new IllegalArgumentException("输出流不能为空");
        }

        // 创建工作簿
        Workbook workbook = new XSSFWorkbook();
        try {
            // 创建工作表
            Sheet sheet = workbook.createSheet(sheetName);

            // 获取并排序字段信息
            List<FieldInfo> fieldInfos = getFieldInfos(dataList.get(0).getClass());

            // 创建表头行
            Row headerRow = sheet.createRow(0);

            // 填充表头
            for (int i = 0; i < fieldInfos.size(); i++) {
                FieldInfo fieldInfo = fieldInfos.get(i);
                Cell cell = headerRow.createCell(i);
                cell.setCellValue(fieldInfo.getColumnName());
                // 设置表头样式
                cell.setCellStyle(createHeaderCellStyle(workbook));
                // 设置列宽
                if (fieldInfo.getWidth() > 0) {
                    sheet.setColumnWidth(i, fieldInfo.getWidth() * 256);
                }
            }

            // 填充数据
            int rowIndex = 1;
            for (T data : dataList) {
                Row dataRow = sheet.createRow(rowIndex);
                
                for (int i = 0; i < fieldInfos.size(); i++) {
                    FieldInfo fieldInfo = fieldInfos.get(i);
                    Cell cell = dataRow.createCell(i);
                    Object value = getFieldValue(data, fieldInfo.getFieldName());
                    // 处理默认值
                    if (value == null) {
                        value = fieldInfo.getDefaultValue();
                    }
                    // 设置单元格值
                    setCellValue(cell, value, fieldInfo.getFormat());
                }
                rowIndex++;
            }

            // 自动调整列宽（仅对未设置宽度的列）
            for (int i = 0; i < fieldInfos.size(); i++) {
                if (fieldInfos.get(i).getWidth() <= 0) {
                    sheet.autoSizeColumn(i);
                }
            }

            // 写入输出流
            workbook.write(outputStream);
            log.info("Excel 导出成功，导出数据条数：{}", dataList.size());
        } catch (Exception e) {
            log.error("Excel 导出失败", e);
            throw e;
        } finally {
            try {
                if (workbook != null) {
                    workbook.close();
                }
            } catch (IOException e) {
                log.error("关闭工作簿失败", e);
            }
        }
    }

    /**
     * 获取字段信息列表
     * @param clazz 类
     * @return 字段信息列表
     */
    private static List<FieldInfo> getFieldInfos(Class<?> clazz) {
        List<FieldInfo> fieldInfos = new ArrayList<>();
        Field[] fields = clazz.getDeclaredFields();
        
        for (Field field : fields) {
            ExcelColumn annotation = field.getAnnotation(ExcelColumn.class);
            if (annotation != null) {
                FieldInfo fieldInfo = new FieldInfo();
                fieldInfo.setFieldName(field.getName());
                fieldInfo.setColumnName(annotation.name());
                fieldInfo.setOrder(annotation.order());
                fieldInfo.setFormat(annotation.format());
                fieldInfo.setWidth(annotation.width());
                fieldInfo.setRequired(annotation.required());
                fieldInfo.setDefaultValue(annotation.defaultValue());
                fieldInfos.add(fieldInfo);
            }
        }
        
        // 按顺序排序
        fieldInfos.sort(Comparator.comparingInt(FieldInfo::getOrder));
        return fieldInfos;
    }

    /**
     * 创建表头单元格样式
     * @param workbook 工作簿
     * @return 表头单元格样式
     */
    private static CellStyle createHeaderCellStyle(Workbook workbook) {
        CellStyle style = workbook.createCellStyle();
        // 设置背景色（使用更美观的蓝色）
        style.setFillForegroundColor(IndexedColors.SKY_BLUE.getIndex());
        style.setFillPattern(FillPatternType.SOLID_FOREGROUND);
        // 设置边框
        style.setBorderTop(BorderStyle.THIN);
        style.setBorderBottom(BorderStyle.THIN);
        style.setBorderLeft(BorderStyle.THIN);
        style.setBorderRight(BorderStyle.THIN);
        // 设置字体
        Font font = workbook.createFont();
        font.setBold(true);
        font.setFontHeightInPoints((short) 12);
        font.setColor(IndexedColors.WHITE.getIndex()); // 白色字体
        style.setFont(font);
        // 设置对齐方式
        style.setAlignment(HorizontalAlignment.CENTER);
        style.setVerticalAlignment(VerticalAlignment.CENTER);
        return style;
    }

    /**
     * 创建数据单元格样式
     * @param workbook 工作簿
     * @return 数据单元格样式
     */
    private static CellStyle createDataCellStyle(Workbook workbook) {
        CellStyle style = workbook.createCellStyle();
        // 设置边框
        style.setBorderTop(BorderStyle.THIN);
        style.setBorderBottom(BorderStyle.THIN);
        style.setBorderLeft(BorderStyle.THIN);
        style.setBorderRight(BorderStyle.THIN);
        // 设置对齐方式
        style.setAlignment(HorizontalAlignment.LEFT);
        style.setVerticalAlignment(VerticalAlignment.CENTER);
        return style;
    }

    /**
     * 获取对象的字段值
     * @param obj 对象
     * @param fieldName 字段名
     * @return 字段值
     * @throws Exception 反射获取字段值时发生的异常
     */
    private static Object getFieldValue(Object obj, String fieldName) throws Exception {
        Class<?> clazz = obj.getClass();
        Field field = clazz.getDeclaredField(fieldName);
        field.setAccessible(true);
        return field.get(obj);
    }

    /**
     * 设置单元格值
     * @param cell 单元格
     * @param value 值
     */
    private static void setCellValue(Cell cell, Object value) {
        setCellValue(cell, value, "");
    }

    /**
     * 设置单元格值（支持格式）
     * @param cell 单元格
     * @param value 值
     * @param format 格式
     */
    private static void setCellValue(Cell cell, Object value, String format) {
        Workbook workbook = cell.getSheet().getWorkbook();
        // 创建基础数据样式
        CellStyle style = createDataCellStyle(workbook);
        
        if (value == null) {
            cell.setCellValue("");
        } else if (value instanceof String) {
            cell.setCellValue((String) value);
        } else if (value instanceof Number) {
            if (!format.isEmpty()) {
                // 设置数字格式
                DataFormat dataFormat = workbook.createDataFormat();
                style.setDataFormat(dataFormat.getFormat(format));
            }
            cell.setCellValue(((Number) value).doubleValue());
        } else if (value instanceof Boolean) {
            cell.setCellValue((Boolean) value);
        } else if (value instanceof java.util.Date) {
            if (!format.isEmpty()) {
                // 设置日期格式
                DataFormat dataFormat = workbook.createDataFormat();
                style.setDataFormat(dataFormat.getFormat(format));
            }
            cell.setCellValue((java.util.Date) value);
        } else {
            cell.setCellValue(value.toString());
        }
        
        // 应用样式
        cell.setCellStyle(style);
    }

    /**
     * 字段信息类
     */
    private static class FieldInfo {
        private String fieldName;
        private String columnName;
        private int order;
        private String format;
        private int width;
        private boolean required;
        private String defaultValue;

        public String getFieldName() {
            return fieldName;
        }

        public void setFieldName(String fieldName) {
            this.fieldName = fieldName;
        }

        public String getColumnName() {
            return columnName;
        }

        public void setColumnName(String columnName) {
            this.columnName = columnName;
        }

        public int getOrder() {
            return order;
        }

        public void setOrder(int order) {
            this.order = order;
        }

        public String getFormat() {
            return format;
        }

        public void setFormat(String format) {
            this.format = format;
        }

        public int getWidth() {
            return width;
        }

        public void setWidth(int width) {
            this.width = width;
        }

        public boolean isRequired() {
            return required;
        }

        public void setRequired(boolean required) {
            this.required = required;
        }

        public String getDefaultValue() {
            return defaultValue;
        }

        public void setDefaultValue(String defaultValue) {
            this.defaultValue = defaultValue;
        }
    }
}
