package com.shframework.common.util;

/**
 * 学年学期工具类
 * @author RanWeizheng
 *
 */
public class AcdYearTermUtil {

	private static final String CODE_SEP_DEFAULT="-";
	
	private static final int TERM_NUM_PER_YEAR = 2; //每年的学期数
	
	/**
	 * 根据学年学期代码和学期偏移量，获得目标学年学期代码，可用于获取预计毕业学年学期代码
	 * @author RanWeizheng
	 * @param yearTermCode 要求格式为  形如 <b>2015-2016-1</b>   这种
	 * @param offset 偏移学期，当前学期偏移量为0 ，下一学期为1，依次类推
	 * @return 正常情况下，返回相应的year_term 的 code；出错时，返回原始值
	 */
	public static String getGraduateYearTermCode(String yearTermCode, int offset){
		
		return getGraduateYearTermCode(yearTermCode, offset, CODE_SEP_DEFAULT, false);
	}
	
	public static String getGraduateYearTermCodeFC(String yearTermCode, int offset){
		return getGraduateYearTermCode(yearTermCode, offset, CODE_SEP_DEFAULT, true);
	}
	
	/**
	 * 
	 * @param yearTermCode
	 * @param offset
	 * @param CODE_SEP
	 * @param isFc   是否要完整的学年学期code 形如 2015-2016-1
	 * @return
	 * @author RanWeizheng
	 * @date 2016年3月14日 下午3:05:42
	 */
	public static String getGraduateYearTermCode(String yearTermCode, int offset, String CODE_SEP, boolean isFc){
		
		if (StringUtils.isBlank(yearTermCode) ||  offset==0 || StringUtils.isBlank(CODE_SEP)){
			return  yearTermCode;
		}
		
		String[] yearTerm = yearTermCode.split(CODE_SEP);
		if (!isFc && yearTerm.length != 2){
			return  yearTermCode;
		}
		if (isFc && yearTerm.length != 3){
			return  yearTermCode;
		}
		int year = 0;//默认的year
		int term = 1;//默认的term
		int toyear = 1;
		try {
			year = Integer.parseInt(yearTerm[0]);
			term = Integer.parseInt(yearTerm[yearTerm.length-1]);
			if (offset > 0){
				year += (term + offset-1)/TERM_NUM_PER_YEAR;
			} else {
				year += (term + offset-2)/TERM_NUM_PER_YEAR;//这是由于整数在做除法时的省略原则导致的， 会往绝对值小的方向靠
			}
			term = Math.abs((term + offset-1)%TERM_NUM_PER_YEAR )+ 1;
			toyear = year + 1;
		} catch (Exception e) {
			return yearTermCode; 
		}
		
		if (isFc){
			return year + CODE_SEP + toyear + CODE_SEP  + term;
		} else {
			return year + CODE_SEP  + term;
		}
	}
	
	/**
	 * 根据学年code/学年学期code 获取 获得学年</br>
	 * 支持形如<b>2015</b> or <b>2015-2016</b> or <b>2015-1</b> or <b>2015-2016-1</b>这几种形式的code获得学年
	 * @param yearCode/yearTermCode
	 * @return
	 */
	public static String getYearByCode(String code){
		return getYear(code, CODE_SEP_DEFAULT);
	}
	
	private static String getYear(String code, String CODE_SEP){
		if (StringUtils.isNotBlank(code)){
			String[] arr = code.split(CODE_SEP);
			if (arr.length >= 2){
				return  arr[0];
			}
			return code;
		}
		return "";
	}
	
	/**
	 * 获取完整的学年code
	 * @param oldAcdYearCode
	 * @return
	 * @author RanWeizheng
	 * @date 2016年3月5日 下午3:19:13
	 */
	public static String getFullAcdYearCode(String oldAcdYearCode){
		if (StringUtils.isNotBlank(oldAcdYearCode)){
			String[] arr = oldAcdYearCode.split(CODE_SEP_DEFAULT);
			if (arr.length >=1){
				return arr[0] + CODE_SEP_DEFAULT + (Integer.parseInt(arr[0])+1);
			}
		}
		return oldAcdYearCode;
	}
	
	/**
	 * 获取完整的学年学期code
	 * @param oldYearTermCode
	 * @return
	 * @author RanWeizheng
	 * @date 2016年3月5日 下午3:13:35
	 */
	public static String getFullYearTermCode(String oldYearTermCode){
		if (StringUtils.isNotBlank(oldYearTermCode)){
			String[] arr = oldYearTermCode.split(CODE_SEP_DEFAULT);
			if (arr.length >=2){
				return arr[0] + CODE_SEP_DEFAULT + (Integer.parseInt(arr[0])+1) + CODE_SEP_DEFAULT + arr[arr.length -1];
			}
		}
		return oldYearTermCode;
	}
	
	public static void main(String[] args){
		System.out.println(getYearByCode("2222"));
		System.out.println(getYearByCode("2222-2"));
		
		int offset = 5;//termNum == 6
		System.out.println("~~~~~~~~~~~~~~~~~~");
		System.out.println("初始值为2010-2011-1");
		System.out.println("初始值为2010-2011-1");
		System.out.println(getFullAcdYearCode("2010"));
		System.out.println(getFullAcdYearCode("2010-1"));
		System.out.println(getFullYearTermCode("2010-1"));
		for (int i=0; i<=offset; i++){
			System.out.println(i + "        "  + getGraduateYearTermCodeFC("2010-2011-1", i));
		}
		System.out.println("~~~~~~~~~~~~~~~~~~");
		System.out.println("初始值为2010-2011-2");
		System.out.println("~~~~~~~~~~~~~~~~~~");
		for (int i=0; i<=offset; i++){
			System.out.println(i + "        "  + getGraduateYearTermCodeFC("2010-2011-2", i));
		}
		
		System.out.println("~~~~~~~~~倒数~~~~~~~~~");
		System.out.println("初始值为2010-2011-1");
		System.out.println("~~~~~~~~~~~~~~~~~~");
		for (int i=0; i>=-offset; i--){
			System.out.println(i + "        "  + getGraduateYearTermCodeFC("2010-2011-1", i));
		}
		System.out.println("~~~~~~~~~~~~~~~~~~");
		System.out.println("初始值为2010-2011-2");
		System.out.println("~~~~~~~~~~~~~~~~~~");
		for (int i=0; i>=-offset; i--){
			System.out.println(i + "        "  + getGraduateYearTermCodeFC("2010-2011-2", i));
		}
	}
}
