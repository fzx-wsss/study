package com.wsss.frame.poi.word;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletResponse;

import org.apache.poi.hwpf.HWPFDocument;
import org.apache.poi.hwpf.model.FieldsDocumentPart;
import org.apache.poi.hwpf.usermodel.Field;
import org.apache.poi.hwpf.usermodel.Fields;
import org.apache.poi.hwpf.usermodel.Range;
import org.apache.poi.xwpf.usermodel.XWPFDocument;
import org.apache.poi.xwpf.usermodel.XWPFParagraph;
import org.apache.poi.xwpf.usermodel.XWPFRun;
import org.apache.poi.xwpf.usermodel.XWPFTable;
import org.apache.poi.xwpf.usermodel.XWPFTableCell;
import org.apache.poi.xwpf.usermodel.XWPFTableRow;

public class ReadAndWriteDocx {
	public static void main(String[] args) throws FileNotFoundException {
		Map<String, String> map = new HashMap<String, String>();
		map.put("1", "yi");
		map.put("2", "er");
		map.put("3", "san");
		map.put("4", "si");
		map.put("5", "wu");

		readwriteWord("C:\\Users\\hasee\\Desktop\\test2.docx", map);
	}

	/**
	 * 实现对word读取和修改操作
	 * 
	 * @param filePath
	 *            word模板路径和名称
	 * @param map
	 *            待填充的数据，从数据库读取
	 * @throws FileNotFoundException
	 */
	public static void readwriteWord(String filePath, Map<String, String> map) throws FileNotFoundException {
		// 读取word模板
		// String fileDir = new
		// File(base.getFile(),"http://www.cnblogs.com/http://www.cnblogs.com/../doc/").getCanonicalPath();
		InputStream is = new FileInputStream(filePath);
		XWPFDocument hdt = null;
		try {
			hdt = new XWPFDocument(is);
		} catch (IOException e1) {
			e1.printStackTrace();
		}

		// 替换段落中的指定文字
		Iterator<XWPFParagraph> itPara = hdt.getParagraphsIterator();
		while (itPara.hasNext()) {
			XWPFParagraph paragraph = itPara.next();
			List<XWPFRun> runs = paragraph.getRuns();
			for (XWPFRun run : runs) {
				String oneparaString = run.getText(run.getTextPosition());
				for (Map.Entry<String, String> entry : map.entrySet()) {
					oneparaString = oneparaString.replace("$" + entry.getKey() + "$", entry.getValue());
				}
				run.setText(oneparaString, 0);
				System.out.println(oneparaString);
			}
		}

		// 替换表格中的指定文字
		Iterator<XWPFTable> itTable = hdt.getTablesIterator();
		while (itTable.hasNext()) {
			XWPFTable table = itTable.next();
			int rcount = table.getNumberOfRows();
			for (int i = 0; i < rcount; i++) {
				XWPFTableRow row = table.getRow(i);
				List<XWPFTableCell> cells = row.getTableCells();
				for (XWPFTableCell cell : cells) {
					String cellTextString = cell.getText();
					for (Map.Entry<String, String> e : map.entrySet()) {
						cellTextString = cellTextString.replace("$" + e.getKey() + "$", e.getValue());
					}
					cell.removeParagraph(0);
					System.out.println(cellTextString);
					cell.setText(cellTextString);
				}
			}
		}

		ByteArrayOutputStream ostream = new ByteArrayOutputStream();
		String fileName = "" + System.currentTimeMillis();
		fileName += ".docx";
		FileOutputStream out = null;
		try {
			out = new FileOutputStream("C:\\Users\\hasee\\Desktop\\" + fileName, true);
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		}
		try {
			hdt.write(ostream);
		} catch (IOException e) {
			e.printStackTrace();
		}
		// 输出字节流
		try {
			out.write(ostream.toByteArray());
		} catch (IOException e) {
			e.printStackTrace();
		}
		try {
			out.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
		try {
			ostream.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	// ======================输出文件流下载方式：==========================

	/**
	 * 实现对word读取和修改操作
	 * 
	 * @param response
	 *            响应,设置生成的文件类型,文件头编码方式和文件名,以及输出
	 * @param filePath
	 *            word模板路径和名称
	 * @param map
	 *            待填充的数据，从数据库读取
	 */
	public static void readwriteWord(HttpServletResponse response, String filePath, Map<String, String> map) {
		// 读取word模板文件
		// String fileDir = new
		// File(base.getFile(),"http://www.cnblogs.com/http://www.cnblogs.com/../doc/").getCanonicalPath();
		// FileInputStream in = new FileInputStream(new
		// File(fileDir+"/laokboke.doc"));
		FileInputStream in;
		HWPFDocument hdt = null;
		try {
			in = new FileInputStream(new File(filePath));
			hdt = new HWPFDocument(in);
		} catch (Exception e1) {
			e1.printStackTrace();
		}

		Fields fields = hdt.getFields();
		Iterator<Field> it = fields.getFields(FieldsDocumentPart.MAIN).iterator();
		while (it.hasNext()) {
			System.out.println(it.next().getType());
		}

		// 替换读取到的word模板内容的指定字段
		Range range = hdt.getRange();

		for (Map.Entry<String, String> entry : map.entrySet()) {
			range.replaceText("$" + entry.getKey() + "$", entry.getValue());
		}

		// 输出word内容文件流，提供下载
		response.reset();
		response.setContentType("application/x-msdownload");
		String fileName = "" + System.currentTimeMillis() + ".doc";
		response.addHeader("Content-Disposition", "attachment; filename=" + fileName);
		ByteArrayOutputStream ostream = new ByteArrayOutputStream();
		OutputStream servletOS = null;
		try {
			servletOS = response.getOutputStream();
			hdt.write(ostream);
			servletOS.write(ostream.toByteArray());
			servletOS.flush();
			servletOS.close();
		} catch (Exception e) {
			e.printStackTrace();
		}

	}
}