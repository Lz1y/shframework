package com.shframework.common.enums;

import java.util.ArrayList;
import java.util.List;

public enum WeekDayEnum {
	Sunday(0,"周日"),
	Monday (1,"周一"),
	Tuesday (2, "周二"), 
	Wednesday (3, "周三"), 
	Thursday  (4, "周四"),
	Friday (5,"周五"),
	Saturday (6,"周六");
	private int key;
	private String value;

	private WeekDayEnum(int key, String value) {
		this.key = key;
		this.value = value;
	}

	public int getKey() {
		return key;
	}

	public void setKey(int key) {
		this.key = key;
	}

	public String getValue() {
		return value;
	}

	public void setValue(String value) {
		this.value = value;
	}
	
	public static WeekDayEnum findByValue(int key) {
		switch (key) {
		case 0:
			return Sunday;
		case 1:
			return Monday;
		case 2:
			return Tuesday;
		case 3:
			return Wednesday;
		case 4:
			return Thursday;
		case 5:
			return Friday;
		case 6:
			return Saturday;
		default:
			return null;
		}
	}

	public static String findByKey(int key) {
		switch (key) {
		case 0:
			return Sunday.value;
		case 1:
			return Monday.value;
		case 2:
			return Tuesday.value;
		case 3:
			return Wednesday.value;
		case 4:
			return Thursday.value;
		case 5:
			return Friday.value;
		case 6:
			return Saturday.value;
		default:
			return null;
		}
	}


	public static List<WeekDayEnum> findWeekDayEnum() {
		List<WeekDayEnum> list = new ArrayList<WeekDayEnum>();
		list.add(Monday);
		list.add(Tuesday);
		list.add(Wednesday);
		list.add(Thursday);
		list.add(Friday);
		list.add(Saturday);
		list.add(Sunday);
		return list;
	}
	
	public static List<ShowEnumVo> getWeekDayEnumVo(){
		List<WeekDayEnum> weekDayList = WeekDayEnum.findWeekDayEnum();
		List<ShowEnumVo> showEnumVoList = new ArrayList<ShowEnumVo>();
		for(WeekDayEnum weekDay : weekDayList){
			ShowEnumVo vo = new ShowEnumVo();
			vo.setRealValue(weekDay.getKey());
			vo.setShowValue(weekDay.getValue());
			showEnumVoList.add(vo);
		}
		return showEnumVoList;
	}
	
}
