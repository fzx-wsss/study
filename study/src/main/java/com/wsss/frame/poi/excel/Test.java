package com.wsss.frame.poi.excel;

import java.io.FileInputStream;
import java.io.InputStream;
import java.math.BigDecimal;
import java.util.HashSet;
import java.util.Set;

import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.ss.usermodel.WorkbookFactory;

public class Test {
	public static void main(String[] args) throws Exception {
		String s = "C:\\Users\\hasee\\Desktop\\2345.xls";
		String s2 = "C:\\Users\\hasee\\Desktop\\1234.xls";
		
		InputStream inp = new FileInputStream(s);
		Workbook wb = WorkbookFactory.create(inp);
		
		InputStream inp2 = new FileInputStream(s2);
		Workbook wb2 = WorkbookFactory.create(inp2);
		
		Sheet sheet = wb.getSheetAt(0);
		Sheet sheet2 = wb2.getSheetAt(0);
		
		Set<Integer> set = new HashSet<>();
		BigDecimal total = new BigDecimal(0);
		for(int j=1;j<251;j++) {
			for(int i=1;i<204;i++) {
			
				Row row = sheet.getRow(i);
				Row row2 = sheet2.getRow(j);
				
				String code = row.getCell(1).getStringCellValue();
				String code2 = row2.getCell(0).getStringCellValue();
				
				String amount = row.getCell(12).getStringCellValue();
				String amount2 = row2.getCell(1).getStringCellValue();
				if(code.equals(code2) && amount.equals(amount2) && !set.contains(i)) {
					Cell cell = row.getCell(0);
					String res = cell != null ? cell.getStringCellValue() : "";
					total = total.add(new BigDecimal(amount));
					System.out.println(j);
					set.add(i);
					break;
				}
			}
		}
		System.out.println(total.doubleValue());
	}
}
