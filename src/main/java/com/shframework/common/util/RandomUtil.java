package com.shframework.common.util;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashSet;
import java.util.List;
import java.util.Random;



/**
 * 随机数工具类		 					
 * @create 	wangkang 2016年5月17日 上午11:24:15
 * @modify  
 * @version V1.0
 */
public class RandomUtil {
	
	/** 
	 * 随机指定范围内N个不重复的数 
	 * 利用HashSet的特征，只能存放不同的值 
	 * @param min 指定范围最小值 
	 * @param max 指定范围最大值 
	 * @param n 随机数个数 
	 * @param HashSet<Integer> set 随机数结果集 
	 */  
   public static void randomSet(int min, int max, int n, HashSet<Integer> set) {  
       if (n > (max - min + 1) || max < min) {  
           return;  
       }  
       for (int i = 0; i < n; i++) {  
           // 调用Math.random()方法  
           int num = (int) (Math.random() * (max - min)) + min;  
           set.add(num);// 将不同的数存入HashSet中  
       }  
       int setSize = set.size();  
       // 如果存入的数小于指定生成的个数，则调用递归再生成剩余个数的随机数，如此循环，直到达到指定大小  
       if (setSize < n) {  
        randomSet(min, max, n - setSize, set);// 递归  
       }  
   }  
   
	/** 
	 * 随机指定范围内N个不重复的数 
	 * 最简单最基本的方法 
	 * @param min 指定范围最小值 
	 * @param max 指定范围最大值 
	 * @param n 随机数个数 
	 */  
	public static int[] randomCommon(int min, int max, int n){  
	    if (n > (max - min + 1) || max < min) {  
	           return null;  
	       }  
	    int[] result = new int[n];  
	    int count = 0;  
	    while(count < n) {  
	        int num = (int) (Math.random() * (max - min)) + min;  
	        boolean flag = true;  
	        for (int j = 0; j < n; j++) {  
	            if(num == result[j]){  
	                flag = false;  
	                break;  
	            }  
	        }  
	        if(flag){  
	            result[count] = num;  
	            count++;  
	        }  
	    }  
	    return result;  
	} 
	
   /** 
    * 随机指定范围内N个不重复的数 
    * 在初始化的无重复待选数组中随机产生一个数放入结果中， 
    * 将待选数组被随机到的数，用待选数组(len-1)下标对应的数替换 
    * 然后从len-2里随机产生下一个随机数，如此类推 
    * @param max  指定范围最大值 
    * @param min  指定范围最小值 
    * @param n  随机数个数 
    * @return int[] 随机数结果集 
    */  
   public static List<Integer> randomArray(int min,int max,int n){  
       int len = max-min+1;  
         
       if(max < min || n > len){  
           return null;  
       }  
         
       //初始化给定范围的待选数组  
       int[] source = new int[len];  
          for (int i = min; i < min+len; i++){  
           source[i-min] = i;  
          }  
            
          int[] result = new int[n];  
          Random rd = new Random();  
          int index = 0;  
          for (int i = 0; i < result.length; i++) {  
           //待选数组0到(len-2)随机一个下标  
              index = Math.abs(rd.nextInt() % len--);  
              //将随机到的数放入结果集  
              result[i] = source[index];  
              //将待选数组中被随机到的数，用待选数组(len-1)下标对应的数替换  
              source[index] = source[len];  
          } 
          
          List<Integer> list = new ArrayList<Integer>();
          if(result != null && result.length > 0){
        	  for(int a:result){
        		  list.add(a);
              }  
          }
          return list;  
   }  
   
	public static void main(String[] args) throws InterruptedException {
		
//		int[] reult1 = randomCommon(1,10,10);  
//	    for (int i : reult1) {  
//	        System.out.println(i);  
//		}  
	      
		// hao
		List<Integer> list = new ArrayList<Integer>();
		list.add(1);
		list.add(2);
		list.add(3);
		list.add(4);
		list.add(5);
		list.add(6);
		list.add(7);
		list.add(8);
		list.add(9);
		
		int size = list.size();
    	
    	for(int j=0;j<10;j++){
    		String s = "";
    		List<Integer> reult2 = randomArray(0,size-1,size);  
    		for (Integer i : reult2) {  
	 	       s += i +",";  
	 	    }
	    	System.out.println(DateUtils.formatDateToString(new Date(),"yyyy-MM-dd　HH:mm:ss:SSS")+"-->"+s);
	    	Thread.sleep(1);	// 1毫秒
    	}
    	
/**
2016-05-18　16:43:32:914-->5,0,7,4,2,6,1,3,8,
2016-05-18　16:43:32:915-->5,0,2,4,6,1,7,3,8,
2016-05-18　16:43:32:916-->3,0,7,1,5,4,6,2,8,
2016-05-18　16:43:32:917-->5,7,1,8,3,2,4,0,6,
2016-05-18　16:43:32:918-->4,5,0,7,2,3,6,8,1,
2016-05-18　16:43:32:919-->2,4,5,8,6,3,1,7,0,
2016-05-18　16:43:32:920-->6,2,8,4,1,7,0,3,5,
2016-05-18　16:43:32:921-->0,4,1,3,6,7,5,8,2,
2016-05-18　16:43:32:922-->8,6,7,4,0,3,1,2,5,
*/
	    
//	    HashSet<Integer> set = new HashSet<Integer>();  
//	    randomSet(1,10,6,set);  
//	       for (int j : set) {  
//	        System.out.println(j);  
//	    }  
	    
	}
}
