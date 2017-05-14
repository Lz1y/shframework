package com.shframework.modules.dict.service;

import com.shframework.common.util.PageSupport;
import com.shframework.common.util.PageTerminal;

/**
 * 特殊结构表的维护操作接口
 * @author RanWeizheng
 *
 * @param <E>
 */
public interface DictSpecialService<E> {

	public int deleteById( int id);

	public PageTerminal<E> findAllByPage(PageSupport pageSupport) ;

	public E getDict(int id);

	public  int saveDict(E record);

}
