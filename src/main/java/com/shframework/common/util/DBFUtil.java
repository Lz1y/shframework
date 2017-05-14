package com.shframework.common.util;

import java.io.File;
import java.io.FileInputStream;
import java.io.InputStream;
import java.text.SimpleDateFormat;
import java.util.Date;

import org.apache.commons.lang3.StringUtils;

import com.linuxense.javadbf.DBFField;
import com.linuxense.javadbf.DBFReader;
import com.linuxense.javadbf.DBFWriter;

public class DBFUtil {

	/**
	 * 按行读取dbf文件中的内容
	 * 
	 * @param
	 * @return
	 * @memo
	 * @author wangkang
	 * @date 2015年3月2日 上午10:50:45
	 */
	public static void readDBF(String path) {
		InputStream fis = null;
		try {
			// 读取文件的输入流
			fis = new FileInputStream(path);
			// 根据输入流初始化一个DBFReader实例，用来读取DBF文件信息
			DBFReader reader = new DBFReader(fis);
			reader.setCharactersetName("UTF-8");
			// 调用DBFReader对实例方法得到path文件中字段的个数
			int fieldsCount = reader.getFieldCount();

			// 取出字段信息
			System.out.println("------------");
			for (int i = 0; i < fieldsCount; i++) {
				DBFField field = reader.getField(i);
				System.out.println(field.getName());
			}
			System.out.println("------------");

			Object[] rowValues;

			// 一条条取出path文件中记录
			while ((rowValues = reader.nextRecord()) != null) {
				for (int i = 0; i < rowValues.length; i++) {
					System.out.println(rowValues[i]);
				}
				System.out.println("------------");
			}
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			try {
				fis.close();
			} catch (Exception e) {
			}
		}
	}

	/**
	 * 生成DBF文件
	 * 
	 * @param dbfName
	 *            dbf文件的全路径名称,eg:"D:\\1\\Book2.dbf"
	 * @param fieldNames
	 *            dbf首行标题字段名称,eg:"asset_type","asset_name","barcode","cat_name",
	 *            "storage","project","own_org"
	 * @param fieldLength
	 *            dbf文件内容的长度，这里统一使用一种。
	 * @param data
	 *            以二维数组存储的dbf文件内容。
	 * @param fileEncoding
	 *            导出的dbf文件编码，默认是"utf-8"。
	 * @return
	 * @memo
	 * @author wangkang
	 * @date 2015年3月3日 下午5:56:37
	 */
	public static boolean writeDBF(String dbfName,
			String[] fieldNames,
			int[] fieldLengthArray,
			Object[][] data,
			String fileEncoding) {
		// 创建目录
		File f = new File(dbfName);
		if (!f.getParentFile().exists()) {
			f.getParentFile().mkdirs();
		}
		boolean flag = false;
		try {
			// 定义DBF文件字段
			int fieldCount = fieldNames.length;
			DBFField[] fields = new DBFField[fieldCount];

			for (int i = 0; i < fieldCount; i++) {
				fields[i] = new DBFField();
				fields[i].setName(fieldNames[i]);
				fields[i].setDataType(DBFField.FIELD_TYPE_C);
				fields[i].setFieldLength(fieldLengthArray[i]);
			}

			// 定义DBFWriter实例用来写DBF文件
			DBFWriter writer = new DBFWriter(new File(dbfName));
			if (StringUtils.isNotBlank(fileEncoding) && ("gbk".toUpperCase().equals(fileEncoding) || "gb2312".toUpperCase().equals(fileEncoding) || "gb18080".toUpperCase().equals(fileEncoding))) {
				writer.setCharactersetName("GBK");
			} else {
				writer.setCharactersetName("UTF-8");
			}

			// 把字段信息写入DBFWriter实例，即定义表结构
			writer.setFields(fields);

			int rowCount = data.length;
			// 一条条的写入记录
			for (int i = 0; i < rowCount; i++) {
				writer.addRecord(data[i]);
			}
			// 写入数据
			writer.write();
			flag = true;
		} catch (Exception e) {
			flag = false;
			e.printStackTrace();
		} finally {
		}
		return flag;
	}
	
	public static String getDBFPath() {

		String dbfPath = Constants.getProperty("file.root.savepath") + Constants.getProperty("file.tmp.path.output")
				+ Constants.getProperty("file.download.path.dbf") + new SimpleDateFormat("yyyyMMdd").format(new Date()) + Constants.URL_SEP;
		return dbfPath;
	}

	public static void main(String[] args) {
		String dbfName = "D:\\1\\2\\Book1.dbf";
		String[] fieldNames = new String[] { "cand_no", "clazz_id", "notes", "reg_status", "stu_no"};
		int[] fieldLengthArray = new int[] { 18, 30, 60, 12, 18, 30 };
		Object[][] data = new Object[][] { { "32260", "1", "防守对方11", "1", "12345" },{ "32260", "1", "防守对方11", "1", "12345" } };

		boolean flag = writeDBF(dbfName, fieldNames, fieldLengthArray, data,"GBK");
		System.out.println(flag);
	}

}
