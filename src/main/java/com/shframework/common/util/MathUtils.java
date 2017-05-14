package com.shframework.common.util;

import java.math.BigDecimal;
import java.util.Arrays;
import java.util.List;

import org.apache.commons.lang3.math.NumberUtils;

public class MathUtils {

	public static double roundTo(double v, double r) {
		return Math.round(v / r) * r;
	}
	
	/**
	 * 2个String 类型的Double数字相加，保留一位小数，并返回基础数据类型的 double 数据
	 * @RP: 	
	 * @param	
	 * @return		
	 * @create 	wangkang 2016年11月18日 上午11:50:29
	 * @modify
	 */
	public static double doubleSum(String a,String b){
		return (double) (Math.round((NumberUtils.toDouble(a, 0.0) + NumberUtils.toDouble(b, 0.0))*10)/10.0);
	}

	/**
	 * 多个Double求和
	 */
	public static Double doubleSum(List<Double> list) {

		double sum = 0;
		for (int i = 0; i < list.size(); i++) {
			BigDecimal a1 = new BigDecimal(Double.toString(sum));
			BigDecimal a2 = new BigDecimal(list.get(i).toString());
			sum = a1.add(a2).doubleValue();
		}
		return new Double(sum);
	}

	/**
	 * 两个Double数相加
	 * 
	 * @param v1
	 * @param v2
	 * @return Double
	 */
	public static Double doubleadd(Double v1, Double v2) {
		BigDecimal b1 = new BigDecimal(v1.toString());
		BigDecimal b2 = new BigDecimal(v2.toString());
		return b1.add(b2).doubleValue();
	}

	/**
	 * 两个Double数相减
	 * 
	 * @param v1
	 * @param v2
	 * @return Double
	 */
	public static Double doublesub(Double v1, Double v2) {
		BigDecimal b1 = new BigDecimal(v1.toString());
		BigDecimal b2 = new BigDecimal(v2.toString());
		return b1.subtract(b2).doubleValue();
	}

	/**
	 * 两个Double数相乘
	 * 
	 * @param v1
	 * @param v2
	 * @return Double
	 */
	public static Double doublemul(Double v1, Double v2) {
		BigDecimal b1 = new BigDecimal(v1.toString());
		BigDecimal b2 = new BigDecimal(v2.toString());
		return b1.multiply(b2).doubleValue();
	}
	
	/**
	 * 
	 * <p>连续相乘保留2位小数</p>
	 * @param scale
	 * @param v
	 * @return
	 */
	public static Double doublemul(int scale, Double... v) {
		if(v.length==2){
			BigDecimal b1 = new BigDecimal(v[0].toString());
			BigDecimal b2 = new BigDecimal(v[1].toString());			
			BigDecimal b3 = b1.multiply(b2);
			b3.setScale(scale, BigDecimal.ROUND_HALF_UP);
			return b3.doubleValue();
		}else{
			BigDecimal b1 = new BigDecimal(v[v.length-1].toString());
			Double[] dt = Arrays.copyOfRange(v, 0, v.length-1);
			return b1.multiply(new BigDecimal(MathUtils.doublemul(scale,dt).toString()))
					.setScale(scale, BigDecimal.ROUND_HALF_UP)
					.doubleValue();
		}
	}

	/**
	 * 两个Double数相除，并保留scale位小数
	 * 
	 * @param v1
	 * @param v2
	 * @param scale
	 * @return Double
	 */
	public static Double doublediv(Double v1, Double v2, int scale) {
		if (scale < 0) {
			throw new IllegalArgumentException("The scale must be a positive integer or zero");
		}
		BigDecimal b1 = new BigDecimal(v1.toString());
		BigDecimal b2 = new BigDecimal(v2.toString());
		return b1.divide(b2, scale, BigDecimal.ROUND_HALF_UP).doubleValue();
	}

	public static void main(String[] args) {
//		Double a = 1.2;
//		Double b = 1.3;
//		System.out.println(doublesub(b, a) > 0 ? 1 : 0);
//		System.out.println(doubleadd(a, b));
		
//		String a = "1.3";
//		String b = "2.1";
//		double c = doubleSum(a, b);
//		double c1 = doubleSum(a, "0");
//		if(c > 0){
//			System.out.println(c+"--"+c1);
//		}
		
		String a = "1.3";
		String b = "2.1";
		double c = (double) (Math.round((NumberUtils.toDouble(a, 0.0) + NumberUtils.toDouble(b, 0.0))*10)/10.0);
		
		double d = 3.4;
		double d1 = 3.5;
		double d2 = 3.3;
		System.out.println((d-c) == 0);
		System.out.println((d1-c) > 0);
		System.out.println((d2-c) < 0);
	}

}
