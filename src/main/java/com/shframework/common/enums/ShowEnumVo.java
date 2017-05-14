package com.shframework.common.enums;

public class ShowEnumVo implements Comparable<ShowEnumVo>{

	Integer realValue;
	String showValue;
	
	public Integer getRealValue() {
		return realValue;
	}
	public void setRealValue(Integer realValue) {
		this.realValue = realValue;
	}
	public String getShowValue() {
		return showValue;
	}
	public void setShowValue(String showValue) {
		this.showValue = showValue;
	} 
	public int compareTo(ShowEnumVo vo) {
		return this.getRealValue().compareTo(vo.getRealValue());
	}
}
