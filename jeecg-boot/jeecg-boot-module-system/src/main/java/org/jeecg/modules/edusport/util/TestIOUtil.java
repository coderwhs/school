package org.jeecg.modules.edusport.util;

import java.util.Date;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import org.apache.poi.hssf.usermodel.HSSFCell;
import org.apache.poi.hssf.usermodel.HSSFCellStyle;
import org.apache.poi.hssf.usermodel.HSSFFont;
import org.apache.poi.hssf.usermodel.HSSFRow;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.hssf.util.HSSFColor;
import org.apache.poi.hssf.util.Region;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;

import com.google.common.collect.Lists;

import cn.hutool.core.collection.CollUtil;
import cn.hutool.core.date.DateUtil;
import cn.hutool.core.lang.Console;
import cn.hutool.json.JSONObject;
import cn.hutool.json.JSONUtil;
import cn.hutool.poi.excel.ExcelReader;
import cn.hutool.poi.excel.ExcelUtil;
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
	public static HSSFWorkbook outputCoachTemplate(HSSFWorkbook workbook, List<Map<String, Object>> athletes, List<String> head12,
			List<String> testProjectList, String jsonStr) {
		List<String> head11 = CollUtil.newArrayList("学号", "姓名", "性别", "年级", "出生年月");
		List<String> head1 = Lists.newArrayList();// 标题.
		head1.addAll(head11);
		head1.addAll(head12);
		List<String> head2 = Lists.newArrayList();// 标题。
		head2.addAll(head11);
		head2.addAll(testProjectList);
		// 合并单元格后的标题行，使用默认标题样式
		JSONObject json = JSONUtil.parseObj(jsonStr);
		
		List<List<String>> heads = CollUtil.newArrayList(head1, head2);
		
		HSSFCellStyle columnTopStyle = getColumnTopStyle(workbook);//获取列头样式对象
		HSSFCellStyle style = getStyle(workbook);//单元格样式对象
		//创建HSSFWorkbook对象
		//workbook = new HSSFWorkbook();
		//创建HSSFSheet对象
		HSSFSheet sheet = workbook.createSheet("sheet0");
//		sheet.createFreezePane(5,4,6,4);// 冻结.
		
		//sheet.autoSizeColumn(1, true);
		//sheet.setColumnWidth(colIndex, length * 480);
		//sheet.setDefaultRowHeightInPoints(10);//设置缺省列高sheet.setDefaultColumnWidth(20);//设置缺省列宽
		//设置指定列的列宽，256 * 50这种写法是因为width参数单位是单个字符的256分之一
		//sheet.setColumnWidth(cell.getColumnIndex(), 256 * 50);
		// excel表头.
		Object[] indexName = (Object[]) testProjectList.toArray();
		HSSFRow row;
		int titleRows = 2;
		int titleCells = 5;
		for(int r = 0; r < titleRows; r++) {// 标题头的行数.
			//创建HSSFRow对象
			row = sheet.createRow(r);
			for(int c = 0; c < indexName.length + titleCells; c++) {// 5是固定的列数.
				//创建HSSFCell对象
				HSSFCell cell = row.createCell(c);
				cell.setCellStyle(columnTopStyle);
				//设置单元格的值
				if(r == 0) {
					cell.setCellValue(json.getStr("title"));
				} else {
					if(c == 0) {
						cell.setCellValue("项目：");
					} else if(c == 1) {
						cell.setCellValue(json.getStr("project"));
					} else if(c == 2) {
						cell.setCellValue("项目：");
					} else if(c == 3) {
						cell.setCellValue(json.getStr("coach"));
					} else if(c == 4) {
						cell.setCellValue("测试时间：");
					} else if(c == 5) {
						cell.setCellValue(json.getStr("date"));
					} else {
						cell.setCellValue("");
					}
				}
				
			}
		}
		
		for(int r = titleRows; r < titleRows + heads.size(); r++) {// 总行数.
			//创建HSSFRow对象
			row = sheet.createRow(r);
			List<String> columnList = heads.get(r - titleRows);
			for(int c = 0; c < columnList.size(); c++) {
				//创建HSSFCell对象
				HSSFCell cell = null;
				//设置单元格的值
				cell = row.createCell(c);
				cell.setCellStyle(columnTopStyle);
				cell.setCellValue(columnList.get(c));
			}
		}
		for(int r = titleRows + heads.size(); r < titleRows + heads.size() + athletes.size(); r++) {// 总行数.
			//创建HSSFRow对象
			row = sheet.createRow(r);
			HashMap<String, Object> athleteMap = (HashMap<String, Object>) athletes.get(r - titleRows - heads.size());
			for(int c = 0; c < athleteMap.size(); c++) {
				//创建HSSFCell对象
				HSSFCell cell = row.createCell(c);
				cell.setCellStyle(style);
				//设置单元格的值
				if(c == 0) {
					cell.setCellValue(athleteMap.get("学号").toString());
				} else if(c == 1) {
					cell.setCellValue(athleteMap.get("姓名").toString());
				} else if(c == 2) {
					cell.setCellValue(athleteMap.get("性别").toString());
				} else if(c == 3) {
					cell.setCellValue(athleteMap.get("年级").toString());
				} else if(c == 4) {
					cell.setCellValue(athleteMap.get("出生年月").toString());
				} else {
					cell.setCellType(Cell.CELL_TYPE_STRING);
					cell.setCellValue("");
				}
			}
		}
		
		// 行融合.
		int cells = sheet.getRow(0).getPhysicalNumberOfCells();// 第一行.
		Row rowNum = sheet.getRow(0);
		sheet.addMergedRegion(new Region(0, (short)(0), 0, (short)(rowNum.getPhysicalNumberOfCells() -1)));
		rowNum = sheet.getRow(2);// 第二行.
		List<String> columnList = heads.get(0);
		int startCell = 5;
		int endCell = startCell;
		for(int i = startCell; i < columnList.size() - 1; i++) {
			if(columnList.get(i).equals(columnList.get(i + 1))) {
				endCell = i + 1;
			} else {
				sheet.addMergedRegion(new Region(2, (short)(startCell), 2, (short)endCell));
				startCell = endCell + 1;
				endCell = startCell + 1;
			}
		}
		sheet.addMergedRegion(new Region(2, (short)(startCell), 2, (short)endCell));

		for(int i = 0; i < 5; i++) {
			sheet.addMergedRegion(new Region(2, (short)(i), 3, (short)i));
		}
		
		return workbook;
	}
	
	/**
	 * 导出模板。
	 * 
	 * @param writer          传入Excel 写入器
	 * @param athletes        运动员列表
	 * @param head12          大类列表
	 * @param testProjectList 测试项目列表
	 * @param jsonStr         title等其他内容的json字符串
	 */
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
	public static List<Map<String, Object>> testDataReader(ExcelReader reader) {
		int headerRowIndex = 3;
		int startRowIndex = 4;
		int endRowIndex = Integer.MAX_VALUE;
		List<Map<String, Object>> readAll = reader.read(headerRowIndex, startRowIndex, endRowIndex);
		return readAll;
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

	public static void main(String[] args) {
		// 通过工具类创建writer
		ExcelWriter writer = ExcelUtil.getWriter("c:/temp/writeMapTest" + new Date().getTime() + ".xlsx");

		List<String> head12 = CollUtil.newArrayList("素质", "素质", "素质", "专项", "专项", "专项", "身体");
		List<String> testProjectList = CollUtil.newArrayList("立定跳远", "后蹲", "窄硬拉", "抓举", "挺举", "总成绩", "体重");
		String jsonStr = JSONUtil.createObj().put("title", "2019-2020年省体校冬训第一次素质、专项测试成绩").put("project", "举重")
				.put("coach", "韩永生").put("date", DateUtil.format(new Date(), "yyy年M月")).toString();

		List<Map<String, Object>> rows = Lists.newArrayList();
		rows.add(createAthlete("S00001", "张三", "男", "初三", "", testProjectList));
		rows.add(createAthlete("S00002", "李四", "女", "", "2005年6月", testProjectList));

		outputTemplate(writer, rows, head12, testProjectList, jsonStr);

		// 关闭writer，释放内存
		writer.close();

		// 读取数据
		ExcelReader reader = ExcelUtil.getReader("c:/temp/writeMapTest.xlsx");
		List<Map<String, Object>> readAll = testDataReader(reader);
		for (int i = 0; i < readAll.size(); i++) {
			Map<String, Object> row = readAll.get(i);
			Console.log(row);
		}
	}

	/* 
	 * 列头单元格样式
	 */    
  	public static HSSFCellStyle getColumnTopStyle(HSSFWorkbook workbook) {
  		  // 设置字体
    	  HSSFFont font = workbook.createFont();
    	  //设置字体大小
    	  font.setFontHeightInPoints((short)11);
    	  //字体加粗
    	  font.setBoldweight(HSSFFont.BOLDWEIGHT_BOLD);
    	  //设置字体名字 
    	  font.setFontName("Courier New");
    	  //设置样式; 
    	  HSSFCellStyle style = workbook.createCellStyle();
    	  //设置底边框; 
    	  style.setBorderBottom(HSSFCellStyle.BORDER_THIN);
    	  //设置底边框颜色;  
    	  style.setBottomBorderColor(HSSFColor.BLACK.index);
    	  //设置左边框;   
    	  style.setBorderLeft(HSSFCellStyle.BORDER_THIN);
    	  //设置左边框颜色; 
    	  style.setLeftBorderColor(HSSFColor.BLACK.index);
    	  //设置右边框; 
    	  style.setBorderRight(HSSFCellStyle.BORDER_THIN);
    	  //设置右边框颜色; 
    	  style.setRightBorderColor(HSSFColor.BLACK.index);
    	  //设置顶边框; 
    	  style.setBorderTop(HSSFCellStyle.BORDER_THIN);
    	  //设置顶边框颜色;  
    	  style.setTopBorderColor(HSSFColor.BLACK.index);
    	  //在样式用应用设置的字体;  
    	  style.setFont(font);
    	  //设置自动换行; 
    	  style.setWrapText(false);
    	  //设置水平对齐的样式为居中对齐;  
    	  style.setAlignment(HSSFCellStyle.ALIGN_CENTER);
    	  //设置垂直对齐的样式为居中对齐; 
    	  style.setVerticalAlignment(HSSFCellStyle.VERTICAL_CENTER);
    	  
    	  return style;
    	  
  	}
  	
  	/*  
	 * 列数据信息单元格样式
	 */  
  	public static HSSFCellStyle getStyle(HSSFWorkbook workbook) {
	  	  // 设置字体
	  	  HSSFFont font = workbook.createFont();
	  	  //设置字体大小
	  	  //font.setFontHeightInPoints((short)10);
	  	  //字体加粗
	  	  //font.setBoldweight(HSSFFont.BOLDWEIGHT_BOLD);
	  	  //设置字体名字 
	  	  font.setFontName("Courier New");
	  	  //设置样式; 
	  	  HSSFCellStyle style = workbook.createCellStyle();
	  	  //设置底边框; 
	  	  style.setBorderBottom(HSSFCellStyle.BORDER_THIN);
	  	  //设置底边框颜色;  
	  	  style.setBottomBorderColor(HSSFColor.BLACK.index);
	  	  //设置左边框;   
	  	  style.setBorderLeft(HSSFCellStyle.BORDER_THIN);
	  	  //设置左边框颜色; 
	  	  style.setLeftBorderColor(HSSFColor.BLACK.index);
	  	  //设置右边框; 
	  	  style.setBorderRight(HSSFCellStyle.BORDER_THIN);
	  	  //设置右边框颜色; 
	  	  style.setRightBorderColor(HSSFColor.BLACK.index);
	  	  //设置顶边框; 
	  	  style.setBorderTop(HSSFCellStyle.BORDER_THIN);
	  	  //设置顶边框颜色;  
	  	  style.setTopBorderColor(HSSFColor.BLACK.index);
	  	  //在样式用应用设置的字体;  
	  	  style.setFont(font);
	  	  //设置自动换行; 
	  	  style.setWrapText(false);
	  	  //设置水平对齐的样式为居中对齐;  
	  	  style.setAlignment(HSSFCellStyle.ALIGN_CENTER);
	  	  //设置垂直对齐的样式为居中对齐; 
	  	  style.setVerticalAlignment(HSSFCellStyle.VERTICAL_CENTER);
	  	 
	  	  return style;
  	
  	}

}
