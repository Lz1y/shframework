package com.shframework.common.util;

import java.util.List;

import com.google.common.collect.Lists;

/**
 * page limit
 * @author OneBoA
 *
 * @param <E>
 */
public class PageTerminal<E> {

	private List<E> list = Lists.newArrayList();
    
    private final PageSupport pageSupport;
    
    private final long total;
    
    private long totalPages;
    
    private boolean hasNext;
    
    private boolean hasPre;
    
    public PageTerminal(List<E> list, PageSupport pageSupport, int total) {
        this.list = list;
        this.total = total;
        this.pageSupport = pageSupport;
    }

    public PageTerminal(List<E> content) {
        this(content, null, null == content ? 0 : content.size());
    }

    public long getTotal() {
        return total;
    }
    

	public List<E> getList() {
		return list;
	}

	public PageSupport getPageSupport() {
		return pageSupport;
	}
	
	public void setList(final List<E> list) {
		this.list = list;
	}

    /**
     * 根据总记录数与页大小计算总页数.
     */
    public long getTotalPages() {
        if (total < 0) {
            return -1;
        }

        long count = total / pageSupport.getPageSize();
        if (total % pageSupport.getPageSize() > 0) {
            count ++;
        }
        totalPages = count; 
        return totalPages;
    }
    
    /**
     * 是否还有下一页.
     */
    public boolean isHasNext() {
    	this.hasNext = (pageSupport.getCurPage() + 1 <= getTotalPages());
        return hasNext;
    }

    /**
     * 取得下页的页号, 序号从1开始. 当前页为尾页时仍返回尾页序号.
     */
    public int getNextPage() {
        if (isHasNext()) {
            return pageSupport.getCurPage() + 1;
        } else {
            return pageSupport.getCurPage();
        }
    }

    /**
     * 是否还有上一页.
     */
    public boolean isHasPre() {
    	this.hasPre = (pageSupport.getCurPage() - 1 >= 1); 
        return hasPre;
    }

    /**
     * 取得上页的页号, 序号从1开始. 当前页为首页时返回首页序号.
     */
    public int getPrePage() {
        if (isHasPre()) {
            return pageSupport.getCurPage() - 1;
        } else {
            return pageSupport.getCurPage();
        }
    }

}
