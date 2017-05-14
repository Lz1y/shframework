package com.shframework.modules.dict.service;

import com.shframework.common.util.PageSupport;
import com.shframework.common.util.PageTerminal;
import com.shframework.modules.dict.entity.DictEduCommonMajor;

/**
 * 专业字典 维护
 * @author RanWeizheng
 *
 */
public interface DictEduCommonMajorService {
		public int deleteById( int id, boolean isSchoolFix);

		public PageTerminal<DictEduCommonMajor> findAllByPage(PageSupport pageSupport, boolean isSchoolFix) ;

		public DictEduCommonMajor getDict(int id, boolean isSchoolFix);

		public int saveDict(DictEduCommonMajor record);
}
