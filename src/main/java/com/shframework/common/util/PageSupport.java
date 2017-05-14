package com.shframework.common.util;

import com.shframework.common.base.vo.ParamVo;

/**
 * 分页支持
 * @author OneBoA
 *
 */
public final class PageSupport {
	
	private static final PageSupport DEFAULT_CRITERIA = new PageSupport(1, PageSupport.DEFAULT_PAGESIZE, null);
    
    private static final int DEFAULT_PAGESIZE = 10;
    
    private int curPage;
    
    private int pageSize;
    
    private int isKeepPage;	// 是否保留分页信息
    
    /** 存储过程专用封装参数 */
    private ParamVo paramVo;

    private PageSupport(int curPage, int pageSize, ParamVo paramVo) {
        this.curPage = curPage;
        this.pageSize = pageSize;
        this.paramVo = paramVo;
    }

    public static PageSupport createCriteria(int curPage, int pageSize, ParamVo paramVo) {
        return new PageSupport(curPage, pageSize, paramVo);
    }

    public static PageSupport getDefaultCriteria() {
        return DEFAULT_CRITERIA;
    }

	public int getCurPage() {
		return curPage;
	}

	/**
	 * 根据curPage于pageSize计算limitStart
	 * @return
	 */
	public int getLimitStart() {
		return (this.curPage - 1) * this.pageSize;
	}
	
	/**
	 * 根据curPage于pageSize计算limitEnd
	 * @return
	 */
	public int getLimitEnd() {
		return this.pageSize;
	}

    public int getPageSize() {
		return pageSize;
	}

	public ParamVo getParamVo() {
		return paramVo;
	}

	public void setParamVo(ParamVo paramVo) {
		this.paramVo = paramVo;
	}

	public void setPageSize(int pageSize) {
		this.pageSize = pageSize;
	}

	public int getIsKeepPage() {
		return isKeepPage;
	}

	public void setIsKeepPage(int isKeepPage) {
		this.isKeepPage = isKeepPage;
	}
    
	/**
	 * 向pageSupport中增加查询条件
	 * @param searchKey
	 * @param searchValue
	 * @author RanWeizheng
	 * @date 2016年6月22日 下午6:50:34
	 */
	public void addSearchCondition(String searchKey, String searchValue){
		paramVo.getMap().put(searchKey, searchValue);
		String searchCondition = paramVo.getSearchCondition() +  " and " + searchKey +" = '" + searchValue  + "'";
		paramVo.setSearchCondition(searchCondition);
		paramVo.setParm(paramVo.getParm().append("&p_" + searchKey + "=" + searchValue));
	}
    
}
