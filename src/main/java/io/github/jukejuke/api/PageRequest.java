package io.github.jukejuke.api;

import java.io.Serializable;

/**
 * 分页查询参数封装类
 * 用于接收前端传递的分页查询参数，包含页码、每页记录数、排序字段和排序方向
 */
public class PageRequest implements Serializable {
    private static final long serialVersionUID = 1L;
    
    /**
     * 默认页码
     */
    public static final int DEFAULT_PAGE_NUM = 1;
    
    /**
     * 默认每页记录数
     */
    public static final int DEFAULT_PAGE_SIZE = 10;
    
    /**
     * 最大每页记录数
     */
    public static final int MAX_PAGE_SIZE = 100;
    
    /**
     * 当前页码
     */
    private int pageNum = DEFAULT_PAGE_NUM;
    
    /**
     * 每页记录数
     */
    private int pageSize = DEFAULT_PAGE_SIZE;
    
    /**
     * 排序字段
     */
    private String orderBy;
    
    /**
     * 排序方向：ASC 或 DESC
     */
    private String orderDir;
    
    /**
     * 构造方法
     */
    public PageRequest() {
    }
    
    /**
     * 构造方法
     * @param pageNum 当前页码
     * @param pageSize 每页记录数
     */
    public PageRequest(int pageNum, int pageSize) {
        this.pageNum = pageNum > 0 ? pageNum : DEFAULT_PAGE_NUM;
        this.pageSize = pageSize > 0 && pageSize <= MAX_PAGE_SIZE ? pageSize : DEFAULT_PAGE_SIZE;
    }
    
    /**
     * 构造方法
     * @param pageNum 当前页码
     * @param pageSize 每页记录数
     * @param orderBy 排序字段
     * @param orderDir 排序方向
     */
    public PageRequest(int pageNum, int pageSize, String orderBy, String orderDir) {
        this.pageNum = pageNum > 0 ? pageNum : DEFAULT_PAGE_NUM;
        this.pageSize = pageSize > 0 && pageSize <= MAX_PAGE_SIZE ? pageSize : DEFAULT_PAGE_SIZE;
        this.orderBy = orderBy;
        this.orderDir = orderDir;
    }
    
    /**
     * 创建分页请求实例
     * @param pageNum 当前页码
     * @param pageSize 每页记录数
     * @return PageRequest实例
     */
    public static PageRequest of(int pageNum, int pageSize) {
        return new PageRequest(pageNum, pageSize);
    }
    
    /**
     * 创建分页请求实例
     * @param pageNum 当前页码
     * @param pageSize 每页记录数
     * @param orderBy 排序字段
     * @param orderDir 排序方向
     * @return PageRequest实例
     */
    public static PageRequest of(int pageNum, int pageSize, String orderBy, String orderDir) {
        return new PageRequest(pageNum, pageSize, orderBy, orderDir);
    }
    
    /**
     * 获取当前页码
     * @return 当前页码
     */
    public int getPageNum() {
        return pageNum;
    }
    
    /**
     * 设置当前页码
     * @param pageNum 当前页码
     */
    public void setPageNum(int pageNum) {
        this.pageNum = pageNum > 0 ? pageNum : DEFAULT_PAGE_NUM;
    }
    
    /**
     * 获取每页记录数
     * @return 每页记录数
     */
    public int getPageSize() {
        return pageSize;
    }
    
    /**
     * 设置每页记录数
     * @param pageSize 每页记录数
     */
    public void setPageSize(int pageSize) {
        this.pageSize = pageSize > 0 && pageSize <= MAX_PAGE_SIZE ? pageSize : DEFAULT_PAGE_SIZE;
    }
    
    /**
     * 获取排序字段
     * @return 排序字段
     */
    public String getOrderBy() {
        return orderBy;
    }
    
    /**
     * 设置排序字段
     * @param orderBy 排序字段
     */
    public void setOrderBy(String orderBy) {
        this.orderBy = orderBy;
    }
    
    /**
     * 获取排序方向
     * @return 排序方向
     */
    public String getOrderDir() {
        return orderDir;
    }
    
    /**
     * 设置排序方向
     * @param orderDir 排序方向
     */
    public void setOrderDir(String orderDir) {
        this.orderDir = orderDir;
    }
    
    /**
     * 获取偏移量
     * @return 偏移量
     */
    public int getOffset() {
        return (pageNum - 1) * pageSize;
    }
    
    @Override
    public String toString() {
        return "PageRequest{" +
                "pageNum=" + pageNum +
                ", pageSize=" + pageSize +
                ", orderBy='" + orderBy + '\'' +
                ", orderDir='" + orderDir + '\'' +
                '}';
    }
}