package com.shframework.common.util;

import java.io.UnsupportedEncodingException;
import java.security.MessageDigest;
import java.util.Random;

import javax.xml.bind.annotation.adapters.HexBinaryAdapter;

import org.apache.shiro.crypto.hash.Sha512Hash;

/**
 * 加密工具
 * @author OneBoA
 *
 */
public class EncryptUtils {

	
	/**
	 * 随机盐
	 * @param length
	 * @return
	 */
	public static String randomSalt(int length) {
		if (length < 1) return null;
		
		char[] randBuffer = new char[length];
		
		for (int i = 0; i < randBuffer.length; i++) 
			randBuffer[i] = ("0123456789abcdefghijklmnopqrstuvwxyz~!@#$%^&*()ABCDEFGHIJKLMNOPQRSTUVWXYZ")
			.toCharArray()[new Random().nextInt(72)];
		
		return new String(randBuffer);
	}

	public static void main(String[] args) throws UnsupportedEncodingException {
		System.out.println(EncryptUtils.encryptSHA512("zfsoft_xxx", "s4h8t3d6:7fduzjgmKkuFkWMVDng3vA==", 1024));
	}
	
	public static String encodeSHA512(byte[] data) throws Exception {  
        byte[] digest = MessageDigest.getInstance(Sha512Hash.ALGORITHM_NAME).digest(data);  
        return new HexBinaryAdapter().marshal(digest);  
    }
	
	public static String encryptSHA512(String password, String salt, int hashIterations) {
		return new Sha512Hash(password.getBytes(), salt, hashIterations).toBase64();
	}
	
	/*** 
     * MD5加码 生成32位md5码 
     */  
    public static String string2MD5(String inStr){  
        MessageDigest md5 = null;  
        try{  
            md5 = MessageDigest.getInstance("MD5");  
        }catch (Exception e){  
            System.out.println(e.toString());  
            e.printStackTrace();  
            return "";  
        }  
        char[] charArray = inStr.toCharArray();  
        byte[] byteArray = new byte[charArray.length];  
  
        for (int i = 0; i < charArray.length; i++)  
            byteArray[i] = (byte) charArray[i];  
        byte[] md5Bytes = md5.digest(byteArray);  
        StringBuffer hexValue = new StringBuffer();  
        for (int i = 0; i < md5Bytes.length; i++){  
            int val = ((int) md5Bytes[i]) & 0xff;  
            if (val < 16)  
                hexValue.append("0");  
            hexValue.append(Integer.toHexString(val));  
        }  
        return hexValue.toString();  
  
    }  
  
    /** 
     * 加密解密算法 执行一次加密，两次解密 
     */   
    public static String convertMD5(String inStr){  
  
        char[] a = inStr.toCharArray();  
        for (int i = 0; i < a.length; i++){  
            a[i] = (char) (a[i] ^ 't');  
        }  
        String s = new String(a);  
        return s;  
  
    }  
}
