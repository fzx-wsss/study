package com.wsss.basic.shell;

import java.io.IOException;
import java.io.InputStreamReader;
import java.io.LineNumberReader;
import java.nio.charset.Charset;
import java.util.Scanner;

public class InvokeShell {
	public static void main(String[] args) throws IOException {
		Scanner sc = new Scanner(System.in);
		//Runtime.getRuntime().exec("chcp 936");
		while(true) {
			String shell = sc.nextLine();
			if(null == shell || "".equals(shell)) continue;
			Process pro = Runtime.getRuntime().exec(shell);
			print(pro);
		}
	}
	
	private static void print(Process pro) throws IOException {
		InputStreamReader ir = new InputStreamReader(pro.getInputStream(), Charset.forName("GBK"));
		LineNumberReader input = new LineNumberReader(ir);
		String line = null;
		while ((line = input.readLine()) != null) {
			System.out.println(line);
		}
		if (null != input) input.close();
		if (null != ir) ir.close();
	}
}
