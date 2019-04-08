package com.wsss.basic.util.excel;

import java.awt.Color;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

import org.apache.poi.hssf.usermodel.HSSFCell;
import org.apache.poi.hssf.usermodel.HSSFCellStyle;
import org.apache.poi.hssf.usermodel.HSSFFont;
import org.apache.poi.hssf.usermodel.HSSFPalette;
import org.apache.poi.hssf.usermodel.HSSFRow;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.hssf.util.HSSFColor;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.CellStyle;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xssf.usermodel.XSSFCellStyle;
import org.apache.poi.xssf.usermodel.XSSFColor;
import org.apache.poi.xssf.usermodel.XSSFFont;
import org.apache.poi.xssf.usermodel.XSSFRow;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.codehaus.jackson.type.TypeReference;

import com.wsss.basic.util.json.JsonUtil;
import com.wsss.basic.util.json.JsonUtils;

public class ExcelUtil {
	public static void main(String[] args) throws Exception {
		StringBuilder sb = new StringBuilder(300000);
		File f = new File("C:\\Users\\hasee\\Desktop\\123.xlsx");
		BufferedReader rd = new BufferedReader(new InputStreamReader(new FileInputStream(f)));
		String s = null;
		while((s = rd.readLine()) != null) {
			sb.append(s);
		}
		List<Map<String, String>> list = JsonUtils.toObject(sb.toString(), new TypeReference<List<Map<String, String>>>(){});
		
		XSSFWorkbook wb = new XSSFWorkbook();
		XSSFSheet sheet = wb.createSheet();
		
		int i=0;
		Map<String,Integer> map = new HashMap<>();
		for(int j=0;j<list.size();j++) {
			Map<String,String> m = list.get(j);
			Row row = sheet.createRow(j);
			for(Entry<String, String> entry : m.entrySet()) {
				Integer col = map.get(entry.getKey());
				if(null == col) {
					col = i++;
					map.put(entry.getKey(), col);
				}
				row.createCell(col).setCellValue(entry.getValue());
			}
		}
		
		
		File file = new File("C:\\Users\\hasee\\Desktop\\123.xls");
		if(file.exists()) {
			file.delete();
		}
		file.createNewFile();
    	FileOutputStream outputStream = new FileOutputStream(file);
    	wb.write(outputStream);
    	outputStream.flush();
    	outputStream.close();
	}
	
	public static void createHand(XSSFSheet sheet,XSSFCellStyle style) {
		XSSFRow row = sheet.createRow(0);
		
		row.createCell(0).setCellValue("*序号");
		row.createCell(1).setCellValue("*付款方客户账号");
		row.createCell(2).setCellValue("*付款方账户名称");
		row.createCell(3).setCellValue("*收款方行别代码（01-本行 02-国内他行）");
		row.createCell(4).setCellValue("*收款方客户账号");
		row.createCell(5).setCellValue("*收款方账户名称");
		row.createCell(6).setCellValue("收款方开户行名称");
		row.createCell(7).setCellValue("收款方联行号");
		row.createCell(8).setCellValue("客户方流水号");
		row.createCell(9).setCellValue("*金额");
		row.createCell(10).setCellValue("*用途");
		row.createCell(11).setCellValue("备注");
		
		for(int i=0;i<row.getLastCellNum();i++) {
			row.getCell(i).setCellStyle(style);
			sheet.setColumnWidth(i,20 * 256);  
		}
		row.setHeight((short)1200);
	}
	
	public static XSSFCellStyle createHandStyle(XSSFWorkbook wb) {
		XSSFCellStyle style = wb.createCellStyle(); 
		style.setFillForegroundColor(HSSFColor.PALE_BLUE.index);
		style.setFillPattern(CellStyle.SOLID_FOREGROUND);
		style.setBorderBottom(HSSFCellStyle.BORDER_THIN); //下边框    
		style.setBorderLeft(HSSFCellStyle.BORDER_THIN);//左边框    
		style.setBorderTop(HSSFCellStyle.BORDER_THIN);//上边框    
		style.setBorderRight(HSSFCellStyle.BORDER_THIN);//右边框   
		style.setVerticalAlignment(HSSFCellStyle.VERTICAL_CENTER);
		style.setAlignment(HSSFCellStyle.ALIGN_CENTER);
		style.setWrapText(true);
		
		XSSFFont font = wb.createFont();    
		font.setBoldweight(HSSFFont.BOLDWEIGHT_BOLD);//粗体显示    
		style.setFont(font);
		return style;
	}
	
	public static void readExcel(String fileName){  
        boolean isE2007 = false;    //判断是否是excel2007格式  
        if(fileName.endsWith("xlsx"))  
            isE2007 = true;  
        try {  
            InputStream input = new FileInputStream(fileName);  //建立输入流  
            Workbook wb = null;
            //根据文件格式(2003或者2007)来初始化  
            if(isE2007)  
                wb = new XSSFWorkbook(input);  
            else  
                wb = new HSSFWorkbook(input);  
            Sheet sheet = wb.getSheetAt(0);     //获得第一个表单  
            Iterator<Row> rows = sheet.rowIterator(); //获得第一个表单的迭代器  
            while (rows.hasNext()) {  
                Row row = rows.next();  //获得行数据  
                System.out.println("Row #" + row.getRowNum());  //获得行号从0开始  
                Iterator<Cell> cells = row.cellIterator();    //获得第一行的迭代器  
                while (cells.hasNext()) {  
                    Cell cell = cells.next();  
                    System.out.println("Cell #" + cell.getColumnIndex());  
                    switch (cell.getCellType()) {   //根据cell中的类型来输出数据  
                    case HSSFCell.CELL_TYPE_NUMERIC:  
                        System.out.println(cell.getNumericCellValue());  
                        break;  
                    case HSSFCell.CELL_TYPE_STRING:  
                        System.out.println(cell.getStringCellValue());  
                        break;  
                    case HSSFCell.CELL_TYPE_BOOLEAN:  
                        System.out.println(cell.getBooleanCellValue());  
                        break;  
                    case HSSFCell.CELL_TYPE_FORMULA:  
                        System.out.println(cell.getCellFormula());  
                        break;  
                    default:  
                        System.out.println("unsuported sell type");  
                    break;  
                    }  
                }  
            }  
        } catch (IOException ex) {  
            ex.printStackTrace();  
        }  
    }  
}
