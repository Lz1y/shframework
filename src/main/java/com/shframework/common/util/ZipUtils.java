package com.shframework.common.util;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.DataInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.Enumeration;
import java.util.zip.ZipInputStream;

import org.apache.tools.zip.ZipEntry;
import org.apache.tools.zip.ZipFile;
import org.apache.tools.zip.ZipOutputStream;

public class ZipUtils {

	private ZipUtils() {
	};
	private static final int BUFFER = 2048;
	
	/**
	 * 
	 * 创建ZIP文件
	 * 
	 * @param sourcePath 文件或文件夹路径
	 * 
	 * @param zipPath 生成的zip文件存在路径（包括文件名）
	 */

	public static void createZip(String sourcePath, String zipPath) {

		FileOutputStream fos = null;

		ZipOutputStream zos = null;

		try {

			fos = new FileOutputStream(zipPath);

			zos = new ZipOutputStream(fos);

			writeZip(new File(sourcePath), "", zos);

		} catch (FileNotFoundException e) {

		} finally {

			try {

				if (zos != null) {

					zos.close();

				}

			} catch (IOException e) {

			}

		}

	}

	private static void writeZip(File file, String parentPath,
			ZipOutputStream zos) {

		if (file.exists()) {

			// 处理文件夹

			if (file.isDirectory()) {

				parentPath += file.getName() + File.separator;

				File[] files = file.listFiles();

				for (File f : files) {

					writeZip(f, parentPath, zos);

				}

			} else {

				FileInputStream fis = null;

				DataInputStream dis = null;

				try {

					fis = new FileInputStream(file);

					dis = new DataInputStream(new BufferedInputStream(fis));

					ZipEntry ze = new ZipEntry(parentPath + file.getName());

					zos.putNextEntry(ze);

					// 添加编码，如果不添加，当文件以中文命名的情况下，会出现乱码

					// ZipOutputStream的包一定是apache的ant.jar包。JDK也提供了打压缩包，但是不能设置编码

					zos.setEncoding("GBK");

					byte[] content = new byte[1024];

					int len;

					while ((len = fis.read(content)) != -1) {

						zos.write(content, 0, len);

						zos.flush();

					}

				} catch (FileNotFoundException e) {

				} catch (IOException e) {

				} finally {

					try {

						if (dis != null) {

							dis.close();

						}

					} catch (IOException e) {

					}

				}

			}

		}

	}
	
	public static void unzip(String filePath, String upZipPath) {
        int count = -1;
        File file = null;
        InputStream is = null;
        FileOutputStream fos = null;
        BufferedOutputStream bos = null;
 
        // 生成指定的保存目录
        String savePath = upZipPath;
        if (!new File(savePath).exists()) {
            new File(savePath).mkdirs();
        }
 
        try {
            ZipFile zipFile = new ZipFile(filePath, "GBK");
            Enumeration<ZipEntry> enu = zipFile.getEntries();
            while (enu.hasMoreElements()) {
                ZipEntry zipEntry = (ZipEntry) enu.nextElement();
                if (zipEntry.isDirectory()) {
                    new File(savePath + "/" + zipEntry.getName()).mkdirs();
                    continue;
                }
                if (zipEntry.getName().indexOf("/") != -1) {
                    new File(savePath
                            + "/"
                            + zipEntry.getName().substring(0,
                                    zipEntry.getName().lastIndexOf("/")))
                            .mkdirs();
                }
                is = zipFile.getInputStream(zipEntry);
                file = new File(savePath + "/" + zipEntry.getName());
                fos = new FileOutputStream(file);
                bos = new BufferedOutputStream(fos, BUFFER);
 
                byte buf[] = new byte[BUFFER];
                while ((count = is.read(buf)) > -1) {
                    bos.write(buf, 0, count);
                }
 
                bos.flush();
                fos.close();
                is.close();
            }
 
            zipFile.close();
        } catch (IOException ioe) {
        }
    }
	
	/**
	 * 解压
	 * 
	 * @param input
	 * @param output
	 */
	public static void decompress(String input, String output) {
		try {
			ZipInputStream Zin = new ZipInputStream(new FileInputStream(input));
			BufferedInputStream Bin = new BufferedInputStream(Zin);
			File Fout = null;
			java.util.zip.ZipEntry entry;
			try {
				while ((entry = Zin.getNextEntry()) != null ) {
					if (entry.isDirectory()){
	                    (new File(output + "/" + entry.getName())).mkdirs();
	                    continue;
					}
					Fout = new File(output, entry.getName());
					if (!Fout.exists()) {
						(new File(Fout.getParent())).mkdirs();
					}
					FileOutputStream out = new FileOutputStream(Fout);
					BufferedOutputStream bos = new BufferedOutputStream(out);
					int b;
					while ((b = Bin.read()) != -1) {
						bos.write(b);
					}
					bos.close();
					out.close();
				}
				Bin.close();
				Zin.close();
			} catch (IOException e) {
				e.printStackTrace();
			}
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		}
	}
	
	public static void main(String[] args) {

		// 测试把F盘下的所有文件打包压缩成sql.zip文件放在F盘根目录下
//		ZipUtils.createZip("C:/tmsstatic/tmp/compress/edu/student/photo/fresh/20150805151159", "C:/123/Apache24/199/125.zip");
//		decompress("c:/111/WKB20161.zip", "c:/111/12");
		
//		unzip("c:/tmsstatic/tmp/cskd/input/20160823175526396/WKB20161.zip", "c:/tmsstatic/tmp/cskd/output/20160823175526398/");
		decompress("c:/tmsstatic/tmp/input/edu/student/photo/fresh/20161025153539884/20161025153539884.zip", "c:/tmsstatic/tmp/decompress/tmp/decompress/edu/student/photo/fresh/20161025153734");
	}

}
