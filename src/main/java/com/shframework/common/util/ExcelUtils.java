package com.shframework.common.util;

import static com.shframework.common.util.Constants.getProperty;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.lang.reflect.InvocationTargetException;
import java.text.DecimalFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.regex.Pattern;

import org.apache.commons.beanutils.BeanUtils;
import org.apache.commons.io.FileUtils;
import org.apache.commons.lang3.StringUtils;
import org.apache.poi.hssf.usermodel.HSSFFont;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.hssf.util.HSSFColor;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.CellStyle;
import org.apache.poi.ss.usermodel.DateUtil;
import org.apache.poi.ss.usermodel.Font;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.ss.util.CellRangeAddress;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.springframework.web.multipart.MultipartFile;

/**
 * <p>主要方法：获取excel表头</p>
 * <p>主要方法：根据 二维数组（数据）在指定路径下生成文件</p>
 * <p>主要方法：得到字节流之后，可直接写入客户端，也可写入到指定文件</p>
 * <p>主要方法：生成excel后，成功返回 导出数据条数、生成文件名称</p>
 * <p>避免用office打开生成的文件时，出现这样的提示：
 * 此文件中的某些文本格式可能已经更改，因为它已经超出最多允许的字体数。
 * 降级：.xls格式
 * </p> 
 * @author zhangjinkui
 */
public class ExcelUtils {
	
	public static String getExportFileName(String prefix){
		String fileName = new SimpleDateFormat("yyyyMMddHHmmss").format(new Date()); 
		if (StringUtils.isNotBlank(prefix))
			return prefix + "_" + fileName;
		return fileName;
		
	}
	public static String getExportFileDate(String prefix){
		String fileName = new SimpleDateFormat("yyyy-MM-dd").format(new Date()); 
		if (StringUtils.isNotBlank(prefix))
			return prefix + "_" + fileName;
		return fileName;
		
	}
	
	/**
	 * 上传excel文件
	 */
	public static String fileUpload(String parent, MultipartFile file) throws IOException {
	String originalFilename = file.getOriginalFilename();
		
		String format = originalFilename.substring(originalFilename.lastIndexOf(".") + 1);
		
		String child = new SimpleDateFormat("yyyyMMddHHmmssSSS").format(new Date()) + "." + format;

		
    	FileUtils.copyInputStreamToFile(file.getInputStream(), new File(getProperty("file.root.savepath") + getProperty("file.tmp.path.input") + parent, child));
		String realPath = getProperty("file.root.savepath")+getProperty("file.tmp.path.input")+ parent + child;
		return realPath;
	}
	
	/**
	 * <p>获取excel表头</p>
	 */
	@SuppressWarnings("resource")
	public static Map<Short,String>  getExcelHeader(MultipartFile file) {
//		You'd use HSSF if you needed to read or write an Excel file using Java (XLS). 
//		You'd use XSSF if you need to read or write an OOXML Excel file using Java (XLSX). 		
		boolean isE2007 = false;	//判断是否是excel2007格式
		
		if(file.getOriginalFilename().endsWith("xlsx")) isE2007 = true;
		//得到表头字段
        HashMap<Short, String> excelHeaderMap = new HashMap<Short, String>();
		InputStream input;
		try {
			//建立输入流
			input = file.getInputStream();
			Workbook wb = null;
			//根据文件格式(2003或者2007)来初始化
			if(isE2007) wb = new XSSFWorkbook(input);
			else wb = new HSSFWorkbook(input);
			
			Sheet sheet = wb.getSheetAt(0);	 //获得第一个表单
//			获取excel第一列的表头
	        Row row = sheet.getRow(0);
	        int columnNum = row.getLastCellNum();
	     
	        for (short i = 0; i < columnNum; i++) {
	            Cell cell = row.getCell(i);
	            excelHeaderMap.put(i, cell.getRichStringCellValue().getString().trim());
	        }
		} catch (IOException e) {
			e.printStackTrace();
		}
		
		return excelHeaderMap;
	}
	
	@SuppressWarnings({ "resource"})
	public static Map<String,String>  getExcelHeaderString(MultipartFile file) {
//		You'd use HSSF if you needed to read or write an Excel file using Java (XLS). 
//		You'd use XSSF if you need to read or write an OOXML Excel file using Java (XLSX). 		
		boolean isE2007 = false;	//判断是否是excel2007格式
		
		if(file.getOriginalFilename().endsWith("xlsx")) isE2007 = true;
		//得到表头字段
        Map<String, String> excelHeaderMap = new LinkedHashMap<String, String>();
		InputStream input;
		try {
			//建立输入流
			input = file.getInputStream();
			Workbook wb = null;
			//根据文件格式(2003或者2007)来初始化
			if(isE2007) wb = new XSSFWorkbook(input);
			else wb = new HSSFWorkbook(input);
			
			Sheet sheet = wb.getSheetAt(0);	 //获得第一个表单
//			获取excel第一列的表头
	        Row row = sheet.getRow(0);
	        int columnNum = row.getLastCellNum();
	     
	        for (short i = 0; i < columnNum; i++) {
	            Cell cell = row.getCell(i);
	            String HeaderString = null;
	            if (cell == null || cell.getRichStringCellValue() == null) {
	            	continue;
	            } else {
	            	HeaderString = StringUtils.trim(cell.getRichStringCellValue().getString()); 
	            }
	            excelHeaderMap.put(HeaderString,HeaderString);
	        }
		} catch (IOException e) {
			e.printStackTrace();
		}
		
		return excelHeaderMap;
	}
	
	@SuppressWarnings("resource")
	public static Map<String,String>  getExcelHeaderString(String filePath) {
//		You'd use HSSF if you needed to read or write an Excel file using Java (XLS). 
//		You'd use XSSF if you need to read or write an OOXML Excel file using Java (XLSX). 		
		boolean isE2007 = false;	//判断是否是excel2007格式
		
		if(filePath.endsWith("xlsx")) isE2007 = true;
		//得到表头字段
        Map<String, String> excelHeaderMap = new LinkedHashMap<String, String>();
		InputStream input;
		try {
			//建立输入流
			input = new FileInputStream(filePath);
			Workbook wb = null;
			//根据文件格式(2003或者2007)来初始化
			if(isE2007) wb = new XSSFWorkbook(input);
			else wb = new HSSFWorkbook(input);
			
			Sheet sheet = wb.getSheetAt(0);	 //获得第一个表单
//			获取excel第一列的表头
	        Row row = sheet.getRow(0);
	        int columnNum = row.getLastCellNum();
	     
	        for (short i = 0; i < columnNum; i++) {
				Cell cell = row.getCell(i);
	            String HeaderString = cell.getRichStringCellValue().getString().trim();
	            excelHeaderMap.put(HeaderString,HeaderString);
	        }
		} catch (IOException e) {
			e.printStackTrace();
		}
		
		return excelHeaderMap;
	}
	
	/**
	 * 根据 二维数组（数据）在指定路径下生成文件
	 * @param fileFullPathName 绝对文件路径（包含文件名）
	 * @param fieldNames 表头字段数组
	 * @param data 二维数组（数据）Object[行][列]
	 * @return boolean (标识是否成功生成文件)
	 * @author zhangjinkui
	 */
	public static boolean writeFile(String fileFullPathName,String[] fieldNames,Object[][] data) {
		
		boolean flag = false;
		try {
			ByteArrayOutputStream baos = new ByteArrayOutputStream();
	    	HSSFWorkbook wb = new HSSFWorkbook();
	    	// 创建字体
	    	Font font = wb.createFont();
	    	// 创建列样式
	    	CellStyle cellStyle = wb.createCellStyle();
	    	
	    	Sheet sheet = wb.createSheet();
			Row row = sheet.createRow(0);
			int colLength = fieldNames.length;
			for (int i = 0; i < colLength; i++) {
				row.setHeightInPoints(25);
				sheet.setColumnWidth(i, 256*16);
				ExcelUtils.createCell(row, i, fieldNames[i],crateHeadCell(wb));
			}
			int line = 0;
			for (line = 0; line < data.length; line++) {
				Row rowtemp = sheet.createRow(line +1);
				for (int col = 0; col < colLength; col++) {
					createCell2(rowtemp, col,data[line][col],createCellStyle(font,cellStyle));
				}
			}
			wb.write(baos);
			wb.close(); 
			File file = new File(fileFullPathName);
            FileUtils.writeByteArrayToFile(file, baos.toByteArray());	
			flag = true;
		} catch (Exception e) {
			flag = false;
			e.printStackTrace();
		} finally {
		}
		return flag;
	}
	
	/**
	 * <p>得到字节流之后，可直接写入客户端，也可写入到指定文件</p>
	 * @param sheetName sheet页名称
	 * @param fieldNames 表头字段数组
	 * @param data 二维数组（数据）Object[行][列]
	 * @return  返回字节数组
	 * @throws IOException 
	 * @throws NoSuchMethodException 
	 * @throws InvocationTargetException 
	 * @throws IllegalAccessException 
	 * @author zhangjinkui
	 */
	public static byte[] writeExcel(String sheetName,String[] fieldNames,Object[][] data) throws IOException, IllegalAccessException, InvocationTargetException, NoSuchMethodException{
		ByteArrayOutputStream baos = new ByteArrayOutputStream();
    	HSSFWorkbook wb = new HSSFWorkbook();
    	// 创建字体
    	Font font = wb.createFont();
    	// 创建列样式
    	CellStyle cellStyle = wb.createCellStyle();
    	Sheet sheet = null;
    	if(StringUtils.isBlank(sheetName)){
    		sheet = wb.createSheet("默认Sheet页名称");
    	}else{
    		sheet =  wb.createSheet(sheetName);
    	}
		Row row = sheet.createRow(0);
		int colLength = fieldNames.length;
		for (int i = 0; i < colLength; i++) {
			row.setHeightInPoints(25);
			sheet.setColumnWidth(i, 256*16);
			ExcelUtils.createCell(row, i, fieldNames[i], crateHeadCell(wb));
		}
		int line = 0;
		for (line = 0; line < data.length; line++) {
			Row rowtemp = sheet.createRow(line +1);
			for (int col = 0; col < colLength; col++) {
				createCell2(rowtemp, col,data[line][col],createCellStyle(font,cellStyle));
			}
		}
		wb.write(baos);
		wb.close(); 
		return baos.toByteArray();
	}
	
	/**
	 * 合并单元格 行
	 */
	public static void mergedRegionRow(Sheet sheet, int columnNum, int rowStart, int rowspan){
		 sheet.addMergedRegion(new CellRangeAddress(
				 rowStart, //first row (0-based)
				 rowStart + rowspan-1, //last row  (0-based)
				 columnNum, //first column (0-based)
				 columnNum  //last column  (0-based)
         ));
	}
	/**
	 * 合并单元格 列
	 */
	public static void mergedRegionColumn(Sheet sheet, int rowNum, int colStart, int colspan){
		 sheet.addMergedRegion(new CellRangeAddress(
				 rowNum, //first row (0-based)
				 rowNum, //last row  (0-based)
				 colStart, //first column (0-based)
				 colStart + colspan - 1 //last column  (0-based)
         ));
	}
	
	
	/**
	 * <p>得到字节流之后，可直接写入客户端，也可写入到指定文件</p>
	 * @param sheetName sheet页名称
	 * @param fieldNames 表头字段数组
	 * @param dataList 数据集List<bean>
	 * @param fieldList 集合<属性名字>
	 * @return  返回字节数组
	 * @throws IOException 
	 * @throws NoSuchMethodException 
	 * @throws InvocationTargetException 
	 * @throws IllegalAccessException 
	 * @author zhangjinkui
	 */
	public static <T> byte[] writeExcel(String sheetName,String[] fieldNames,List<T> dataList,List<String> fieldList) throws IOException, IllegalAccessException, InvocationTargetException, NoSuchMethodException{
		ByteArrayOutputStream baos = new ByteArrayOutputStream();
    	HSSFWorkbook wb = new HSSFWorkbook();
    	// 创建字体
    	Font font = wb.createFont();
    	// 创建列样式
    	CellStyle cellStyle = wb.createCellStyle();
    	Sheet sheet = null;
    	if(StringUtils.isBlank(sheetName)){
    		sheet = wb.createSheet("默认Sheet页名称");
    	}else{
    		sheet =  wb.createSheet(sheetName);
    	}
		Row row = sheet.createRow(0);
		for (int i = 0; i < fieldNames.length; i++) {//表头
			row.setHeightInPoints(25);
			sheet.setColumnWidth(i, 256*16);
			createCell(row, i, fieldNames[i], crateHeadCell(wb));
		}
		
		int line = 0;
		for (line = 0; line < dataList.size(); line++) {
			List<String> list = getOneValues(dataList.get(line),fieldList);
			Row rowtemp = sheet.createRow(line +1);
			for (int col = 0; col < list.size(); col++) {
				createCell2(rowtemp, col, list.get(col),createCellStyle(font,cellStyle));
			}
		}
			wb.write(baos);
			wb.close(); 
			return baos.toByteArray();
	}
	
	/**
	 * <p>返回成功导出数据条数、生成文件名称</p>
	 * @param sheetName sheet页名称
	 * @param excelPath	文件存放全路径
	 * @param fieldNames 表头字段数组
	 * @param dataList 数据集List<bean>
	 * @param fieldList 集合<属性名字>
	 * @return  返回 {"导出数据条数","文件名称"}
	 * @throws IOException 
	 * @throws NoSuchMethodException 
	 * @throws InvocationTargetException 
	 * @throws IllegalAccessException 
	 * @author zhangjinkui
	 */
	public static <T> String[] writeByteArrayToExcelFile(String sheetName, String excelPath,String[] fieldNames,List<T> dataList,List<String> fieldList) throws IOException, IllegalAccessException, InvocationTargetException, NoSuchMethodException{
		ByteArrayOutputStream baos = new ByteArrayOutputStream();
    	HSSFWorkbook wb = new HSSFWorkbook();
    	// 创建字体
    	Font font = wb.createFont();
    	// 创建列样式
    	CellStyle cellStyle = wb.createCellStyle();
    	Sheet sheet = null;
    	if(StringUtils.isBlank(sheetName)){
    		sheet = wb.createSheet("默认Sheet页名称");
    	}else{
    		sheet = wb.createSheet(sheetName);
    	}
		Row row = sheet.createRow(0);
		for (int i = 0; i < fieldNames.length; i++) {
			row.setHeightInPoints(25);
			sheet.setColumnWidth(i, 256*16);
			createCell(row, i, fieldNames[i], crateHeadCell(wb));
		}
		int line = 0;
		for (line = 0; line < dataList.size(); line++) {
			List<String> list = getOneValues(dataList.get(line),fieldList);
			Row rowtemp = sheet.createRow(line +1);
			for (int col = 0; col < list.size(); col++) {
				createCell2(rowtemp, col, list.get(col),createCellStyle(font,cellStyle));
				
			}
		}
			wb.write(baos);
			wb.close(); 
			String excelFileName = new SimpleDateFormat("yyyyMMddHHmmss").format(new Date()) + ".xls";
			String excelPathName = excelPath + excelFileName;
			File file = new File(excelPathName);
            FileUtils.writeByteArrayToFile(file, baos.toByteArray());			
            
            String dataTotalNumber = String.valueOf(dataList.size());
            String[] strArray = {dataTotalNumber,excelFileName};
            
			return strArray;
	}
	
	/**
	 *<p>返回指定bean的指定的属性值，并以字符串方式返回。</p>
	 * @param bean	数据传输对象
	 * @param fieldList 集合<属性名字>
	 * @return  The property's value, converted to a String
	 * @throws IllegalAccessException
	 * @throws InvocationTargetException
	 * @throws NoSuchMethodException
	 * @author zhangjinkui
	 */
	public static <T> List<String> getOneValues(T bean,List<String> fieldList) throws IllegalAccessException, InvocationTargetException, NoSuchMethodException {
		List<String> resList = new ArrayList<String>();
		if (bean == null)
			return resList;
		
		for (int i = 0; i < fieldList.size(); i++){
			String fieldName = fieldList.get(i);
			
			String originalValueString = BeanUtils.getProperty(bean, fieldName);
			
			if (originalValueString == null || originalValueString.length() == 0){
				resList.add("");
			}
			else { 
				resList.add(originalValueString);
			}
		}
		
		return resList;
	}
	
	
	/**
	 * 设置生成Cell单元格 
	 * @param row
	 * @param column
	 * @param cellValue
	 * @param cellStyle
	 */
	public static void createCell(Row row, Integer column, String cellValue, CellStyle cellStyle) {
		Cell cell = row.createCell(column);
		if(StringUtils.isEmpty(cellValue)) cellValue = "";
		cell.setCellValue(cellValue);
		// 统一将单元格格式设置成字符串类型
		cell.setCellType(Cell.CELL_TYPE_STRING);
		cell.setCellStyle(cellStyle);
	}
	
	public static void createCell2(Row row, Integer column, Object cellValue, CellStyle cellStyle) {
		Cell cell = row.createCell(column);
		if(StringUtils.isEmpty(String.valueOf(cellValue))) cellValue = "";
		
		try {
			if (isNumeric(String.valueOf(cellValue))){
				double num = Double.valueOf(String.valueOf(cellValue));
				cell.setCellValue(num);
				cell.setCellType(Cell.CELL_TYPE_NUMERIC);
			} else {
				cell.setCellValue(String.valueOf(cellValue));
				cell.setCellType(Cell.CELL_TYPE_STRING);
			}
		}catch(Exception e){
			cell.setCellValue(String.valueOf(cellValue));
			cell.setCellType(Cell.CELL_TYPE_STRING);
		}
		
		cell.setCellStyle(cellStyle);
	}
	
	 private static boolean isNumeric(String value){
		 	if (StringUtils.isBlank(value) ){
		 		return false;
		 	}
		 	//http://blog.jobbole.com/96052/ 
		 	//非负浮点数：^\d+(\.\d+)?$ 或 ^[1-9]\d*\.\d*|0\.\d*[1-9]\d*|0?\.0+|0$ 
//	        String reg = "^\\d+(\\.\\d+)?$";
	        String reg = "^(0|([1-9]\\d{0,6}))(\\.\\d+)?$";//避免长数字出现科学计数法
	        return Pattern.compile(reg).matcher(value).find();
	    }
	
	/**
	 * 设置生成Cell单元格 
	 * @param row
	 * @param column
	 * @param cellValue
	 */
	public static void createCell(Row row, Integer column, String cellValue) {
		Cell cell = row.createCell(column);
		if(StringUtils.isEmpty(cellValue)) cellValue = "";
		cell.setCellValue(cellValue);
		// 统一将单元格格式设置成字符串类型
		cell.setCellType(Cell.CELL_TYPE_STRING);
	}
	
	/**
	 * 设置生成Cell单元格 
	 * @param row
	 * @param column
	 * @param cellValue
	 */
	public static void createCell(Row row, Integer column, Object cellValue) {
		Cell cell = row.createCell(column);
		if(StringUtils.isEmpty(String.valueOf(cellValue))) cellValue = "";
		cell.setCellValue(String.valueOf(cellValue));
		// 统一将单元格格式设置成字符串类型
		cell.setCellType(Cell.CELL_TYPE_STRING);
	}
	
	/**
	 * 获得excel列的值，以字符串格式返回
	 * 避免过大数字 以出现科学计数法形式出现
	 * @param cell
	 * @return
	 */
	public static String getCellContent(Cell cell){
		switch (cell.getCellType()) {
        case Cell.CELL_TYPE_STRING:
            return String.valueOf(cell.getRichStringCellValue().getString());
        case Cell.CELL_TYPE_NUMERIC:
            if (DateUtil.isCellDateFormatted(cell)) {
            	return String.valueOf(cell.getDateCellValue());
            } else {
            	DecimalFormat df = new DecimalFormat("0");
            	return  df.format(cell.getNumericCellValue());
            }
        case Cell.CELL_TYPE_BOOLEAN:
        	return String.valueOf(cell.getBooleanCellValue());
        case Cell.CELL_TYPE_FORMULA:
        	return String.valueOf(cell.getCellFormula());
        default:
        	return String.valueOf(cell.getRichStringCellValue().getString());
		}
	}
	
	/**
	 * <p>得到excel绝对文件路径</p>
	 * @return
	 */
	public static String getExcelFileFullPath() {
		String filePath = getProperty("file.root.savepath") + getProperty("file.tmp.path.output") + getProperty("file.download.path.excel")+DateUtils.formatDateToString(new Date(), DateUtils.patternyyyyMMdd)+Constants.URL_SEP;
		return filePath;
	}
	
	/**
	 * <p>下载excel导入模板 专用方法</p>
	 * <p>得到字节流之后，可直接写入客户端，也可写入到指定文件</p>
	 * @param fieldNames 表头字段数组 String[]
	 * @return  返回字节数组
	 * @throws IOException 
	 * @author zhangjinkui
	 */
	public static byte[] writeExcelHeader(String[] fieldNames) throws IOException{
		ByteArrayOutputStream baos = new ByteArrayOutputStream();
    	HSSFWorkbook wb = new HSSFWorkbook();
    	Sheet sheet = wb.createSheet("默认Sheet页名称");;
		Row row = sheet.createRow(0);
		int colLength = fieldNames.length;
		for (int i = 0; i < colLength; i++) {
			row.setHeightInPoints(25);
			sheet.setColumnWidth(i, 256*16);
			ExcelUtils.createCell(row, i, fieldNames[i], crateHeadCell(wb));
		}
	
		wb.write(baos);
		wb.close(); 
		return baos.toByteArray();
	}
	
	/**
	 * <p>正文 默认字体样式，且不允许换行</p>
	 * @return
	 */
	public static CellStyle createCellStyle(Font font,CellStyle cellStyle) {

		// 设置字体颜色 (黑色)
		font.setColor(HSSFColor.BLACK.index);
		// 设置字体
		font.setFontName("宋体");
		// 设置粗体
		font.setBoldweight(HSSFFont.BOLDWEIGHT_NORMAL);
		// 设置字体大小
		font.setFontHeightInPoints((short)12);
		// 设置不换行
		cellStyle.setWrapText(false);
		// 设置字体
		cellStyle.setFont(font);
		// 设置对齐
		cellStyle.setAlignment(CellStyle.ALIGN_CENTER);
		// 垂直居中
		cellStyle.setVerticalAlignment(CellStyle.VERTICAL_CENTER);
		cellStyle.setLocked(false);
		return cellStyle;
	}
	
	/**
	 * 允许换行
	 * @param wb
	 * @return
	 */
	public static CellStyle createBrCellStyle(HSSFWorkbook wb) {
		// 创建列样式
		CellStyle style = wb.createCellStyle();
		// 创建字体
		Font font = wb.createFont();
		// 设置字体颜色 (黑色)
		font.setColor(HSSFColor.BLACK.index);
		// 设置字体
		font.setFontName("宋体");
		// 设置粗体
		font.setBoldweight(HSSFFont.BOLDWEIGHT_NORMAL);
		// 设置字体大小
		font.setFontHeightInPoints((short)12);
		// 设置不换行
		style.setWrapText(true);
		// 设置字体
		style.setFont(font);
		// 设置对齐
		style.setAlignment(CellStyle.ALIGN_LEFT);
		// 垂直居中
		style.setVerticalAlignment(CellStyle.ALIGN_LEFT);
		style.setLocked(false);
		
		return style;
	}
	
	/**
	 * <p>表头默认样式</p>
	 * @return
	 */
	public static CellStyle crateHeadCell(HSSFWorkbook wb) {
		Font font = wb.createFont();
		CellStyle style = wb.createCellStyle();
		// 设置字体颜色 (黑色)
		font.setColor(HSSFColor.BLACK.index);
		font.setFontName("宋体");
		// 设置粗体
		font.setBoldweight(HSSFFont.BOLDWEIGHT_NORMAL);
		// 设置字体大小
		font.setFontHeightInPoints((short)12);
		// 设置不换行
		style.setWrapText(false);
		// 设置字体
		style.setFont(font);
		
		// 设置背景色
		style.setFillForegroundColor(HSSFColor.GREY_25_PERCENT.index);
		// 设置背景填充(背景色必须)
		style.setFillPattern(CellStyle.SOLID_FOREGROUND);
		// 设置边框样式
		style.setBorderRight(CellStyle.BORDER_DASHED);

		// 设置对齐
		style.setAlignment(CellStyle.ALIGN_CENTER);
		style.setVerticalAlignment(CellStyle.VERTICAL_CENTER);
		style.setLocked(false);
		
		return style;
	}
	
	/**
	 * <p>特殊格式自定义</p>
	 * @param boldweight
	 * @param fontheight
	 * @param alignment
	 * @return
	 */
	public static  CellStyle createHSSFCellStyle(HSSFWorkbook wb,short boldweight, short fontheight, short alignment) {
		// 创建列样式
		CellStyle style = wb.createCellStyle();
		// 创建字体
		Font font = wb.createFont();
		// 设置字体颜色 (黑色)
		font.setColor(HSSFColor.BLACK.index);
		// 设置字体
		font.setFontName("宋体");
		// 设置粗体
		font.setBoldweight(boldweight);
		// 设置字体大小
		font.setFontHeightInPoints(fontheight);
		// 设置不换行
		style.setWrapText(false);
		// 设置字体
		style.setFont(font);
		// 设置对齐
		style.setAlignment(alignment);
		// 垂直居中
		style.setVerticalAlignment(CellStyle.VERTICAL_CENTER);
		style.setLocked(false);
		
		return style;
	}
	
}
