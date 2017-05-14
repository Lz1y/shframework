package com.shframework.common.util;

import java.awt.image.BufferedImage;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.IOException;
import java.io.RandomAccessFile;
import java.nio.channels.FileChannel;
import java.util.List;

import javax.imageio.ImageIO;

/**
 * 
 * @author OneBoA
 *
 */
public class FileUtil {

	/**
	 * 使用文件通道的方式复制文件
	 * 
	 * @param s 源文件
	 * @param t 复制到的新文件
	 */
	public static void fileChannelCopy(File s, File t) {
		FileInputStream fi = null;
		FileOutputStream fo = null;
		FileChannel in = null;
		FileChannel out = null;

		try {
			mkdirFolder(t.getPath().substring(0, t.getPath().lastIndexOf(Constants.SEP)));
			
			fi = new FileInputStream(s);
			fo = new FileOutputStream(t);
			in = fi.getChannel();// 得到对应的文件通道
			out = fo.getChannel();// 得到对应的文件通道
			in.transferTo(0, in.size(), out);// 连接两个通道，并且从in通道读取，然后写入out通道
		} catch (IOException e) {
			e.printStackTrace();
		} finally {
			try {
				fi.close();
				in.close();
				fo.close();
				out.close();
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
	}

	/**
	 * 使用文件通道的方式复制多文件
	 * 
	 * @param s 各文件路径
	 * @param t 保存文件路径
	 */
	public static void fileChannelCopy(List<String> s, List<String> t) {
		FileInputStream fi = null;
		FileOutputStream fo = null;
		FileChannel in = null;
		FileChannel out = null;

		try {
			if (s != null && t != null && s.size() == t.size()) {
				for (int i = 0; i < s.size(); i++) {
					mkdirFolder(t.get(i).substring(0, t.get(i).lastIndexOf(Constants.URL_SEP)));

					fi = new FileInputStream(new File(s.get(i)));
					fo = new FileOutputStream(new File(t.get(i)));
					in = fi.getChannel();// 得到对应的文件通道
					out = fo.getChannel();// 得到对应的文件通道
					in.transferTo(0, in.size(), out);// 连接两个通道，并且从in通道读取，然后写入out通道
				}
			}
		} catch (IOException e) {
			e.printStackTrace();
		} finally {
			try {
				fi.close();
				in.close();
				fo.close();
				out.close();
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
	}
	
	/**
	 * 该目录不存在则创建
	 * @param path
	 */
	public static void mkdirFolder(String path){
		File file = new File(path);
		if (!file.exists() && !file.isDirectory()) file.mkdirs();
	}
	
	public static boolean deleteFile(String sPath) {  
	    boolean flag = false;  
	    File file = new File(sPath);  
	    // 路径为文件且不为空则进行删除  
	    if (file.isFile() && file.exists()) {  
	        file.delete();  
	        flag = true;  
	    }  
	    return flag;  
	}  
	
	/**
	 * 删除目录
	 * @param sPath
	 * @return
	 */
	public static boolean deleteDirectory(String sPath) {  
	    //如果sPath不以文件分隔符结尾，自动添加文件分隔符  
	    if (!sPath.endsWith(File.separator)) {  
	        sPath = sPath + File.separator;  
	    }  
	    File dirFile = new File(sPath);  
	    //如果dir对应的文件不存在，或者不是一个目录，则退出  
	    if (!dirFile.exists() || !dirFile.isDirectory()) {  
	        return false;  
	    }  
	    boolean flag = true;  
	    //删除文件夹下的所有文件(包括子目录)  
	    File[] files = dirFile.listFiles();  
	    for (int i = 0; i < files.length; i++) {  
	        //删除子文件  
	        if (files[i].isFile()) {  
	            flag = deleteFile(files[i].getAbsolutePath());  
	            if (!flag) break;  
	        } //删除子目录  
	        else {  
	            flag = deleteDirectory(files[i].getAbsolutePath());  
	            if (!flag) break;  
	        }  
	    }  
	    if (!flag) return false;  
	    //删除当前目录  
	    if (dirFile.delete()) {  
	        return true;  
	    } else {  
	        return false;  
	    }  
	}  
	
	public static void screenshot(String realPath, int w, int h, int x, int y) throws Exception {
		File file = new File(realPath);
		String format = file.getName().substring(file.getName().lastIndexOf(".") + 1);
		BufferedImage bImg = ImageIO.read(file);
		SculpImage sculpImage = SculpImage.getInstance();
		BufferedImage bImage = sculpImage.imageZoomOut(bImg, w, h);
		bImage.getGraphics().drawImage(bImage, x, y, w, h, null);
		ImageIO.write(bImage, format, file);
	}

	/**
	 * 创建文件
	 * 
	 * @param fileName
	 * @return
	 */
	public static boolean createFile(File fileName) {
		boolean flag = false;
		try {
			if (!fileName.exists()) {
				flag = fileName.createNewFile();
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return flag;
	}

	/**
	 * 读TXT文件内容
	 * 
	 * @param fileName
	 * @return
	 */
	public static String readTxtFile(File fileName) throws Exception {
		String result = null;
		FileReader fileReader = null;
		BufferedReader bufferedReader = null;
		try {
			fileReader = new FileReader(fileName);
			bufferedReader = new BufferedReader(fileReader);
			try {
				String read = null;
				while ((read = bufferedReader.readLine()) != null) 
					result = result + read + "\r\n";
				
			} catch (Exception e) {
				e.printStackTrace();
			}
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			if (bufferedReader != null) {
				bufferedReader.close();
			}
			if (fileReader != null) {
				fileReader.close();
			}
		}
		return result;
	}

	public static boolean writeTxtFile(String content, File fileName) throws IOException {
		boolean flag = false;
		RandomAccessFile mm = null;
		FileOutputStream o = null;
		try {
			o = new FileOutputStream(fileName);
			o.write(content.getBytes("utf-8"));
			o.close();
			flag = true;
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			if (mm != null) {
				mm.close();
			}
		}
		return flag;
	}
	
	public static List<String> getFilesPath(List<String> pathList,File dir,String suffix) {
		
        //如果当前文件或目录存在
        if(dir.exists()){
            //如果是目录，则：
            if(dir.isDirectory()){
                //打印当前目录的路径
//            	System.out.println(dir);
//            	System.out.println(dir.getPath());
                //获取该目录下的所有文件和目录组成的File数组
                File[] files = dir.listFiles();
                //递归遍历每一个子文件
                for(File file : files){
                	getFilesPath(pathList,file,suffix);
                }
            }
            //如果是文件，则打印该文件路径及名称
            else{
            	if(dir.getPath().endsWith(suffix)){
//            		System.out.println(dir.getPath());
            		pathList.add(dir.getPath());
            	}
            }
        }
        return pathList;
    }
	
}
