package com.wsss.basic.common;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;

import org.springframework.core.SpringVersion;

public class ReadJarFile {
	public static void main(String[] args) throws IOException {
		String str = "jar:" +SpringVersion.class.getResource("/META-INF/license.txt").getPath();
		System.out.println(str);
		File file = new File(str);
		System.out.println(file.exists());
		
		InputStream is = SpringVersion.class.getResourceAsStream("/META-INF/license.txt");
		BufferedReader reader = new BufferedReader(new InputStreamReader(is));
		System.out.println(reader.readLine());
	}
}
