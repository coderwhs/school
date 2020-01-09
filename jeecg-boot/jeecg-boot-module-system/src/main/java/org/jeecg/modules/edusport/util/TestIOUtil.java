package org.jeecg.modules.edusport.util;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.util.Date;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.usermodel.CellStyle;
import org.apache.poi.ss.usermodel.Font;
import org.apache.poi.ss.usermodel.IndexedColors;
import org.apache.poi.ss.usermodel.PrintSetup;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.ss.util.CellRangeAddress;
import org.apache.poi.ss.util.RegionUtil;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

import com.google.common.collect.Lists;
import com.google.common.collect.Maps;

import cn.hutool.core.collection.CollUtil;
import cn.hutool.core.date.DateUtil;
import cn.hutool.core.lang.Console;
import cn.hutool.json.JSONObject;
import cn.hutool.json.JSONUtil;
import cn.hutool.poi.excel.ExcelReader;
import cn.hutool.poi.excel.ExcelWriter;

public class TestIOUtil {

	/**
	 * 导出模板。
	 * 
	 * @param writer          传入Excel 写入器
	 * @param athletes        运动员列表
	 * @param head12          大类列表
	 * @param testProjectList 测试项目列表
	 * @param jsonStr         title等其他内容的json字符串
	 */
	@Deprecated
	public static void outputTemplate(ExcelWriter writer, List<Map<String, Object>> athletes, List<String> head12,
			List<String> testProjectList, String jsonStr) {
		List<String> head11 = CollUtil.newArrayList("学号", "姓名", "性别", "年级", "出生年月");
		List<String> head1 = Lists.newArrayList();
		head1.addAll(head11);
		head1.addAll(head12);
		List<String> head2 = Lists.newArrayList();
		head2.addAll(head11);
		head2.addAll(testProjectList);
		// 合并单元格后的标题行，使用默认标题样式
		JSONObject json = JSONUtil.parseObj(jsonStr);

		writer.merge(head2.size() - 1, json.getStr("title"), false);
		writer.merge(head2.size() - 1, "项目：" + json.getStr("project") + "       教练员：" + json.getStr("coach")
				+ "            测试时间：" + json.getStr("date"), false);
		writer.merge(2, 3, 0, 0, "学号", false);
		writer.merge(2, 3, 1, 1, "姓名", false);
		writer.merge(2, 3, 2, 2, "性别", false);
		writer.merge(2, 3, 3, 3, "年级", false);
		writer.merge(2, 3, 4, 4, "出生年月", false);

		List<List<String>> heads = CollUtil.newArrayList(head1, head2);
		writer.write(heads, true);
		mergeTypeCell(writer, head12);
//		writer.merge(2, 2, 5, 7, "素质", false);
//		writer.merge(2, 2, 8, 10, "专项", false);
		// 一次性写出内容，使用默认样式，强制输出标题
		writer.write(athletes, false);
	}
	
	/**
	 * 导出模板（手工）。
	 * 
	 * @param writer          传入Excel 写入器
	 * @param athletes        运动员列表
	 * @param head12          大类列表
	 * @param testProjectList 测试项目列表
	 * @param jsonStr         title等其他内容的json字符串
	 */
	public static void outputTemplate(Sheet sheet, List<Map<String, Object>> athletes, List<String> head12,
			List<String> testProjectList, String jsonStr) {
		Map<String, CellStyle> styles = createStyles(sheet.getWorkbook());
		
		List<String> head11 = CollUtil.newArrayList("学号", "姓名", "性别", "年级", "出生年月");
		List<String> head1 = Lists.newArrayList();
		head1.addAll(head11);
		head1.addAll(head12);
		List<String> head2 = Lists.newArrayList();
		head2.addAll(head11);
		head2.addAll(testProjectList);
		// 合并单元格后的标题行，使用默认标题样式
		JSONObject json = JSONUtil.parseObj(jsonStr);

		List<List<String>> heads = CollUtil.newArrayList(head1, head2);

		
		org.jeecg.modules.edusport.util.ExcelUtil.setCellValue(sheet.createRow(0), 0, json.getStr("title"), styles.get("title"));
		org.jeecg.modules.edusport.util.ExcelUtil.setCellValue(sheet.createRow(1), 0, "项目：" + json.getStr("project") + "       教练员：" + json.getStr("coach")
		+ "            测试时间：" + json.getStr("date"), styles.get("header"));
		
		org.jeecg.modules.edusport.util.ExcelUtil.write(sheet, 2, heads, styles.get("header"));
		mergeTypeCell(sheet, head12);
		// 一次性写出内容，使用默认样式，强制输出标题
//		write(sheet, athletes, styles.get("cell"));
		org.jeecg.modules.edusport.util.ExcelUtil.write(sheet, 4, athletes, styles.get("cell"));

		merge(sheet, 0,0,0,head2.size() - 1, "", false);
		merge(sheet, 1,1,0,head2.size() - 1, "", false);
		merge(sheet, 2, 3, 0, 0, "", false);
		merge(sheet, 2, 3, 1, 1, "", false);
		merge(sheet, 2, 3, 2, 2, "", false);
		merge(sheet, 2, 3, 3, 3, "", false);
		merge(sheet, 2, 3, 4, 4, "", false);
	}

	private static void mergeTypeCell(Sheet sheet, List<String> head12) {
		int start = 5;
		int end = 5;
		String temp = "";
		int size = head12.size();

		for (int i = 0; i <= size; i++) {
			if (i == 0) {
				temp = head12.get(i);
			} else if (i != size) {
				if (temp.equals(head12.get(i))) {
					end++;
				} else {
//					Console.log(">>>>>>>>>>>>>" + start + ":" + end);
					if(start != end) {
						merge(sheet,2, 2, start, end, temp, false);
					}
					end++;
					start = end;
					temp = head12.get(i);
				}
			} else {
//				Console.log("<<<<<<<<<<<<" + start + ":" + end);
				if(start != end) {
					merge(sheet,2, 2, start, end, temp, false);
				}
			}
		}
	}
	
	private static void mergeTypeCell(ExcelWriter writer, List<String> head12) {
		int start = 5;
		int end = 5;
		String temp = "";
		int size = head12.size();

		for (int i = 0; i <= size; i++) {
			if (i == 0) {
				temp = head12.get(i);
			} else if (i != size) {
				if (temp.equals(head12.get(i))) {
					end++;
				} else {
//					Console.log(">>>>>>>>>>>>>" + start + ":" + end);
					if(start != end) {
						writer.merge(2, 2, start, end, temp, false);
					}
					end++;
					start = end;
					temp = head12.get(i);
				}
			} else {
//				Console.log("<<<<<<<<<<<<" + start + ":" + end);
				if(start != end) {
					writer.merge(2, 2, start, end, temp, false);
				}
			}
		}
	}

	/**
	 * 从文件读取器中读取内容
	 * 
	 * @param reader 读取器
	 * @return
	 */
	@Deprecated
	public static List<Map<String, Object>> testDataReader(ExcelReader reader) {
		int headerRowIndex = 3;
		int startRowIndex = 4;
		int endRowIndex = Integer.MAX_VALUE;
		List<Map<String, Object>> readAll = reader.read(headerRowIndex, startRowIndex, endRowIndex);
		return readAll;
	}
	
	/**
	 * 从文件读取器中读取内容
	 * 
	 * @param reader 读取器
	 * @return
	 */
	public static List<Map<String, Object>> testDataReader(Sheet sheet) {
		List<Map<String, Object>> readAll = Lists.newArrayList();
		int headerRowIndex = 3;
		int startRowIndex = 4;
		int endRowIndex = sheet.getLastRowNum();
		Object[] heads = getHeader(sheet, headerRowIndex);
		for (int i = startRowIndex; i <= endRowIndex; i++) {
			readAll.add(getData(sheet, i, heads));
		}
		
		return readAll;
	}
	
	public static Object[] getHeader(Sheet sheet, int headerRowIndex) {
		List<String> result = Lists.newArrayList();
		Row row = sheet.getRow(headerRowIndex);
		int first = row.getFirstCellNum();
		int last = row.getLastCellNum();
		for (int i = first; i < last; i++) {
			result.add(ExcelUtil.getCellValue(sheet.getWorkbook(), 0, headerRowIndex, first+i).toString());
		}
		return result.toArray();
	}
	
	public static Map<String, Object> getData(Sheet sheet, int rowNum, Object[] heads) {
		Map<String, Object> result = Maps.newLinkedHashMap();
		Row row = sheet.getRow(rowNum);
		int first = row.getFirstCellNum();
		int last = heads.length + first;
		for (int i = first; i < last; i++) {
			result.put((String)heads[i], ExcelUtil.getCellValue(sheet.getWorkbook(), 0, rowNum, first+i).toString());
		}
		return result;
	}

	public static Map<String, Object> createAthlete(String number, String name, String sex, String grade, String brith,
			List<String> testProjectList) {
		Map<String, Object> row = new LinkedHashMap<>();
		row.put("学号", number);
		row.put("姓名", name);
		row.put("性别", sex);
		row.put("年级", grade);
		row.put("出生年月", brith);
		for (int i = 0; i < testProjectList.size(); i++) {
			row.put(testProjectList.get(i), "");
		}
		return row;
	}

	public static Map<String, Object> createAthlete(String number, String name, List<String> testProjectList) {
		return createAthlete(number, name, "", "", "", testProjectList);
	}

//	public static void main(String[] args) {
//
//		// 通过工具类创建writer
//		ExcelWriter writer = ExcelUtil.getWriter("c:/temp/writeMapTest" + new Date().getTime() + ".xlsx");
//
//		List<String> head12 = CollUtil.newArrayList("素质", "素质", "素质", "专项", "专项", "专项", "身体");
//		List<String> testProjectList = CollUtil.newArrayList("立定跳远", "后蹲", "窄硬拉", "抓举", "挺举", "总成绩", "体重");
//		String jsonStr = JSONUtil.createObj().put("title", "2019-2020年省体校冬训第一次素质、专项测试成绩").put("project", "举重")
//				.put("coach", "韩永生").put("date", DateUtil.format(new Date(), "yyy年M月")).toString();
//
//		List<Map<String, Object>> rows = Lists.newArrayList();
//		rows.add(createAthlete("S00001", "张三", "男", "初三", "", testProjectList));
//		rows.add(createAthlete("S00002", "李四", "女", "", "2005年6月", testProjectList));
//
//		outputTemplate(writer, rows, head12, testProjectList, jsonStr);
//
//		// 关闭writer，释放内存
//		writer.close();
//
//		// 读取数据
//		ExcelReader reader = ExcelUtil.getReader("c:/temp/writeMapTest.xlsx");
//		List<Map<String, Object>> readAll = testDataReader(reader);
//		for (int i = 0; i < readAll.size(); i++) {
//			Map<String, Object> row = readAll.get(i);
//			Console.log(row);
//		}
//	}
	
	
	public static void merge(Sheet sheet, int firstRow, int lastRow, int firstColumn, int lastColumn, Object content, boolean isSetHeaderStyle) {
		CellRangeAddress region = new CellRangeAddress(firstRow, lastRow, firstColumn, lastColumn);
		sheet.addMergedRegion(region);
		setBorderStyle(CellStyle.BORDER_THIN, region, sheet, sheet.getWorkbook());
	}
	
	public static void setBorderStyle(int border, CellRangeAddress region, Sheet sheet, Workbook wb){
        RegionUtil.setBorderBottom(border, region, sheet, wb);   //下边框
        RegionUtil.setBorderLeft(border, region, sheet, wb);     //左边框
        RegionUtil.setBorderRight(border, region, sheet, wb);    //右边框
        RegionUtil.setBorderTop(border, region, sheet, wb);      //上边框
    }
	

    public static void main(String[] args) throws Exception {
        Workbook wb;

        if(args.length > 0 && args[0].equals("-xls")) 
        	wb = new HSSFWorkbook();
        else wb = new XSSFWorkbook();

//        Map<String, CellStyle> styles = createStyles(wb);
        
        
		List<String> head12 = CollUtil.newArrayList("素质", "素质", "素质", "专项", "专项", "专项", "身体");
		List<String> testProjectList = CollUtil.newArrayList("立定跳远", "后蹲", "窄硬拉", "抓举", "挺举", "总成绩", "体重");
		String jsonStr = JSONUtil.createObj().put("title", "2019-2020年省体校冬训第一次素质、专项测试成绩").put("project", "举重")
				.put("coach", "韩永生").put("date", DateUtil.format(new Date(), "yyy年M月")).toString();

		List<Map<String, Object>> rows = Lists.newArrayList();
		rows.add(createAthlete("S00001", "张三", "男", "初三", "", testProjectList));
		rows.add(createAthlete("S00002", "李四", "女", "", "2005年6月", testProjectList));

        Sheet sheet = wb.createSheet();
        PrintSetup printSetup = sheet.getPrintSetup();
        printSetup.setLandscape(true);
        sheet.setFitToPage(true);
        sheet.setHorizontallyCenter(true);
        
        outputTemplate(sheet, rows, head12, testProjectList, jsonStr);

        // Write the output to a file
        String file = "c:/temp/timesheet" + new Date().getTime() + ".xls";
        if(wb instanceof XSSFWorkbook) file += "x";
        FileOutputStream out = new FileOutputStream(file);
        wb.write(out);
        out.close();
        
//		ExcelReader reader = ExcelUtil.getReader("c:/temp/writeMapTest.xlsx");
//		List<Map<String, Object>> readAll = testDataReader(reader);
//		for (int i = 0; i < readAll.size(); i++) {
//			Map<String, Object> row = readAll.get(i);
//			Console.log(row);
//		}
        String xlsxpath = "c:/temp/writeMapTest.xlsx";
        FileInputStream fi = new FileInputStream(new File(xlsxpath));
        Workbook workbook = new XSSFWorkbook(fi);
        List<Map<String, Object>> readAll = testDataReader(workbook.getSheetAt(0));
        
        for (int i = 0; i < readAll.size(); i++) {
			Map<String, Object> row = readAll.get(i);
			Console.log(row);
		}
        
        fi.close();
    }
	
	/**
     * Create a library of cell styles
     */
    private static Map<String, CellStyle> createStyles(Workbook wb){
        Map<String, CellStyle> styles = new HashMap<>();
        CellStyle style;
        Font titleFont = wb.createFont();
        titleFont.setFontHeightInPoints((short)18);
//        titleFont.setBold(true);
        style = wb.createCellStyle();
        style.setAlignment(CellStyle.ALIGN_CENTER);
        style.setVerticalAlignment(CellStyle.VERTICAL_CENTER);
        style.setFont(titleFont);
        styles.put("title", style);

        Font monthFont = wb.createFont();
        monthFont.setFontHeightInPoints((short)11);
        monthFont.setColor(IndexedColors.WHITE.getIndex());
        style = wb.createCellStyle();
        style.setAlignment(CellStyle.ALIGN_CENTER);
        style.setVerticalAlignment(CellStyle.VERTICAL_CENTER);
        style.setWrapText(true);
        style.setBorderRight(CellStyle.BORDER_THIN);
        style.setRightBorderColor(IndexedColors.BLACK.getIndex());
        style.setBorderLeft(CellStyle.BORDER_THIN);
        style.setLeftBorderColor(IndexedColors.BLACK.getIndex());
        style.setBorderTop(CellStyle.BORDER_THIN);
        style.setTopBorderColor(IndexedColors.BLACK.getIndex());
        style.setBorderBottom(CellStyle.BORDER_THIN);
        style.setBottomBorderColor(IndexedColors.BLACK.getIndex());
        styles.put("header", style);

        style = wb.createCellStyle();
        style.setAlignment(CellStyle.ALIGN_CENTER);
        style.setVerticalAlignment(CellStyle.VERTICAL_CENTER);
        style.setWrapText(true);
        style.setBorderRight(CellStyle.BORDER_THIN);
        style.setRightBorderColor(IndexedColors.BLACK.getIndex());
        style.setBorderLeft(CellStyle.BORDER_THIN);
        style.setLeftBorderColor(IndexedColors.BLACK.getIndex());
        style.setBorderTop(CellStyle.BORDER_THIN);
        style.setTopBorderColor(IndexedColors.BLACK.getIndex());
        style.setBorderBottom(CellStyle.BORDER_THIN);
        style.setBottomBorderColor(IndexedColors.BLACK.getIndex());
        styles.put("cell", style);

        style = wb.createCellStyle();
        style.setAlignment(CellStyle.ALIGN_CENTER);
        style.setVerticalAlignment(CellStyle.VERTICAL_CENTER);
        style.setVerticalAlignment(CellStyle.ALIGN_CENTER);
        style.setFillForegroundColor(IndexedColors.GREY_25_PERCENT.getIndex());
        style.setFillPattern(CellStyle.SOLID_FOREGROUND);
        style.setDataFormat(wb.createDataFormat().getFormat("0.00"));
        styles.put("formula", style);

        style = wb.createCellStyle();
        style.setAlignment(CellStyle.ALIGN_CENTER);
        style.setVerticalAlignment(CellStyle.VERTICAL_CENTER);
        style.setVerticalAlignment(CellStyle.ALIGN_CENTER);
        style.setFillForegroundColor(IndexedColors.GREY_40_PERCENT.getIndex());
        style.setFillPattern(CellStyle.SOLID_FOREGROUND);
        style.setDataFormat(wb.createDataFormat().getFormat("0.00"));
        styles.put("formula_2", style);

        return styles;
    }

}
