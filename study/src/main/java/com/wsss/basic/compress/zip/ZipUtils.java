package com.wsss.basic.compress.zip;

import java.io.IOException;
import java.util.zip.ZipEntry;
import java.util.zip.ZipFile;

public class ZipUtils {
	public static void main(String[] args) throws IOException {
		ZipFile zf = new ZipFile("C:\\work\\Workspaces\\unpsMchtEmu1\\lib\\SADK-3.1.0.8.jar");
		ZipEntry entry = zf.getEntry("cfca/org/bouncycastle/tsp/cms/MetaDataUtil.class");
		System.out.println(entry);
	}
}
