package org.jeecg.modules.edusport.util;

import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.CellStyle;
import org.apache.poi.ss.usermodel.Font;
import org.apache.poi.ss.usermodel.IndexedColors;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;

/**
 * A weekly timesheet created using Apache POI. Usage: TimesheetDemo -xls|xlsx
 *
 * @author Yegor Kozlov
 */
public class ExcelUtil {

	/**
	 * Create a library of cell styles
	 */
	@SuppressWarnings("unused")
	private static Map<String, CellStyle> createStyles(Workbook wb) {
		Map<String, CellStyle> styles = new HashMap<String, CellStyle>();
		CellStyle style;
		Font titleFont = wb.createFont();
		titleFont.setFontHeightInPoints((short) 18);
//        titleFont.setBoldweight(Font.BOLDWEIGHT_BOLD);
		style = wb.createCellStyle();
		style.setAlignment(CellStyle.ALIGN_CENTER);
		style.setVerticalAlignment(CellStyle.ALIGN_CENTER);
		style.setFont(titleFont);
		styles.put("title", style);

		Font monthFont = wb.createFont();
		monthFont.setFontHeightInPoints((short) 11);
		monthFont.setColor(IndexedColors.WHITE.getIndex());
		style = wb.createCellStyle();
		style.setAlignment(CellStyle.ALIGN_CENTER);
		style.setVerticalAlignment(CellStyle.ALIGN_CENTER);
		style.setFillForegroundColor(IndexedColors.GREY_50_PERCENT.getIndex());
		style.setFillPattern(CellStyle.SOLID_FOREGROUND);
		style.setFont(monthFont);
		style.setWrapText(true);
		styles.put("header", style);

		style = wb.createCellStyle();
		style.setAlignment(CellStyle.ALIGN_CENTER);
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
		style.setVerticalAlignment(CellStyle.ALIGN_CENTER);
		style.setFillForegroundColor(IndexedColors.GREY_25_PERCENT.getIndex());
		style.setFillPattern(CellStyle.SOLID_FOREGROUND);
		style.setDataFormat(wb.createDataFormat().getFormat("0.00"));
		styles.put("formula", style);

		style = wb.createCellStyle();
		style.setAlignment(CellStyle.ALIGN_CENTER);
		style.setVerticalAlignment(CellStyle.ALIGN_CENTER);
		style.setFillForegroundColor(IndexedColors.GREY_40_PERCENT.getIndex());
		style.setFillPattern(CellStyle.SOLID_FOREGROUND);
		style.setDataFormat(wb.createDataFormat().getFormat("0.00"));
		styles.put("formula_2", style);

		return styles;
	}

	public static void write(Sheet sheet, int startRow, List<?> data, CellStyle style) {
		for (int i = 0; i < data.size(); i++) {
			Row row = sheet.createRow(startRow + i);
			Object rowData = data.get(i);
			writeRow(sheet, row, rowData, style);
		}
	}

	public static void writeRow(Sheet sheet, Row row, Object rowData, CellStyle style) {
		if (rowData instanceof List) {
			@SuppressWarnings("unchecked")
			List<Object> values = (List<Object>) rowData;
			for (int i = 0; i < values.size(); i++) {
				String value = values.get(i).toString();
				setCellValue(row, i, value, style);
			}

		}
		if (rowData instanceof Map) {
			@SuppressWarnings("unchecked")
			Object[] values = ((LinkedHashMap<String, Object>) rowData).values().toArray();
			for (int i = 0; i < values.length; i++) {
				String value = values[i].toString();
				setCellValue(row, i, value, style);
			}
		}
	}

	public static void setCellValue(Row row, int colNum, String value, CellStyle style) {
		Cell cell = row.createCell(colNum);
		cell.setCellValue(value);
		cell.setCellStyle(style);
	}

	/**
	 * 得到单元格数据
	 * 
	 * @param workbook
	 * @param sheetAt
	 * @param argRow
	 * @param argCol
	 * @return
	 * @throws FileNotFoundException
	 * @throws IOException
	 */
	public static Cell getCellValue(Workbook workbook, int sheetAt, int argRow, int argCol) {
		// 创建对工作表的引用。
		Sheet sheet = workbook.getSheetAt(sheetAt);
		// 读取行
		Row row = sheet.getRow(argRow);
		Cell cell = row.getCell((short) argCol);

		return cell;
	}
}