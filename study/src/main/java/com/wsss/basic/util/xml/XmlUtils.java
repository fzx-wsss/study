package com.wsss.basic.util.xml;

import java.io.StringWriter;

import org.dom4j.Document;
import org.dom4j.DocumentHelper;
import org.dom4j.Element;
import org.dom4j.io.OutputFormat;
import org.dom4j.io.XMLWriter;

public class XmlUtils {
	public static void main(String[] args) {
		Document doc = DocumentHelper.createDocument();
		// 设置编码
		doc.setXMLEncoding("GBK");

		// Transaction_Header
		Element tx = doc.addElement("REQUEST");
		Element head = tx.addElement("HEAD");
		Element body = tx.addElement("BODY");

		head.addElement("Flag").addText("按时");
		System.out.println(tx.element("body"));
		System.out.println(doc.asXML());
		// 返回组装好的XML文件（XML字符串）
		OutputFormat format = OutputFormat.createCompactFormat();
		format.setEncoding("ISO-8859-1");
		StringWriter out = new StringWriter();
		XMLWriter writer = null;
		try {
			try {
				writer = new XMLWriter(out, format);
				writer.write(doc);
			} finally {
				if (writer != null) writer.close();
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		// 返回组装好的XML文件（XML字符串）
		System.out.println(out.toString());
	}
}
