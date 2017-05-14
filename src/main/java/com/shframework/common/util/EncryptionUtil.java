/**
 * @description 
 * @author Josh Yan
 * @version 1.0
 * @datetime 2016年12月23日 上午11:11:01
 */
package com.shframework.common.util;

import org.jasypt.encryption.pbe.PooledPBEStringEncryptor;

/**
 * @author Josh
 *
 */
public class EncryptionUtil {
    static PooledPBEStringEncryptor encryptor = null;
    static {
        encryptor = new PooledPBEStringEncryptor();
        encryptor.setPoolSize(4); 
        //  There are various approaches to pull this configuration via system level properties. 
        encryptor.setPassword("s4h8t3d6");
        encryptor.setAlgorithm("PBEWITHMD5ANDDES");
    }

    public static String encrypt(String input) {
        return encryptor.encrypt(input);
    }

    public static String decrypt(String encryptedMessage) {
        return encryptor.decrypt(encryptedMessage);
    }
    
    public static void main(String[] args){
    	PooledPBEStringEncryptor pooledPBEStringEncryptor = new PooledPBEStringEncryptor();
    	pooledPBEStringEncryptor.setPoolSize(4); 
    	pooledPBEStringEncryptor.setPassword("s4h8t3d6");
    	pooledPBEStringEncryptor.setAlgorithm("PBEWITHMD5ANDDES");
        String encrypted = pooledPBEStringEncryptor.encrypt("tms");  
        System.out.println("1******  " + encrypted);
        System.out.println(pooledPBEStringEncryptor.encrypt("jdbc:mysql://192.168.1.221:3306/tms?useUnicode=true&amp;characterEncoding=utf-8"));
        
        String decrypted = pooledPBEStringEncryptor.decrypt("XMdlblGUkzF76dgoCgPRK0wWrjLtHrwaclyDBuwt+SlRvSy/GAPjkngSVufnuh8MvlImI4WlYvkJJNGoX0xIF70f4PB3co4GsrRSXrIrEskGjhziJ4cLkg==");
        System.out.println("2******  " + decrypted);
        
        System.out.println("3******  " + pooledPBEStringEncryptor.decrypt("XMdlblGUkzF76dgoCgPRK0wWrjLtHrwaclyDBuwt+SlRvSy/GAPjkngSVufnuh8MvlImI4WlYvkJJNGoX0xIF70f4PB3co4GsrRSXrIrEskGjhziJ4cLkg=="));
        
        System.out.println("4******  " + pooledPBEStringEncryptor.decrypt("2GT9jQJXBXUuvhKqbdxmjA=="));
        
        System.out.println("5******  " + pooledPBEStringEncryptor.decrypt("1F3dbamxGff9fnR9qDM4wA=="));
    }
}
