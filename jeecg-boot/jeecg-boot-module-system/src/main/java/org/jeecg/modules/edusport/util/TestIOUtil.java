package org.jeecg.modules.edusport.util;

import java.util.Date;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

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
	 * @param writer 传入Excel 写入器
	 * @param athletes 运动员列表
	 * @param head12 大类列表
	 * @param testProjectList 测试项目列表
	 * @param jsonStr title等其他内容的json字符串
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
		writer.merge(2, 2, 5, 7, "", false);
		writer.merge(2, 2, 8, 10, "", false);

		List<List<String>> heads = CollUtil.newArrayList(head1, head2);
		writer.write(heads, true);
		// 一次性写出内容，使用默认样式，强制输出标题
		writer.write(athletes, false);
	}

	/**
	 * 从文件读取器中读取内容
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
	
	public static Map<String, Object> createAthlete(String number,String name,String sex,String grade,String brith, List<String> testProjectList) {
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
	
	public static Map<String, Object> createAthlete(String number,String name, List<String> testProjectList) {
		return createAthlete(number, name, "", "", "", testProjectList);
	}

	public static void main(String[] args) {

		// 通过工具类创建writer
		ExcelWriter writer = ExcelUtil.getWriter("c:/temp/writeMapTest" + new Date().getTime() + ".xlsx");

		List<String> head12 = CollUtil.newArrayList("素质", "素质", "素质", "专项", "专项", "专项");
		List<String> testProjectList = CollUtil.newArrayList("立定跳远", "后蹲", "窄硬拉", "抓举", "挺举", "总成绩");
		String jsonStr = JSONUtil.createObj().put("title", "2019-2020年省体校冬训第一次素质、专项测试成绩").put("project", "举重")
				.put("coach", "韩永生").put("date", DateUtil.format(new Date(), "yyy年M月")).toString();

		List<Map<String, Object>> rows = Lists.newArrayList();
		rows.add(createAthlete("S00001", "张三", "男", "初三", "", testProjectList));
		rows.add(createAthlete("S00002", "李四", "女", "", "2005年6月", testProjectList));
		
		outputTemplate(writer, rows, head12, testProjectList, jsonStr);

		// 关闭writer，释放内存
		writer.close();

		//读取数据		
		ExcelReader reader = ExcelUtil.getReader("c:/temp/writeMapTest.xlsx");
		List<Map<String, Object>> readAll = testDataReader(reader);
		for (int i = 0; i < readAll.size(); i++) {
			Map<String, Object> row = readAll.get(i);
			Console.log(row);
		}
	}

}
