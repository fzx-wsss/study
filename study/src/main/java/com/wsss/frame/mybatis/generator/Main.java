package com.wsss.frame.mybatis.generator;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import org.mybatis.generator.api.MyBatisGenerator;
import org.mybatis.generator.config.Configuration;
import org.mybatis.generator.config.xml.ConfigurationParser;
import org.mybatis.generator.exception.XMLParserException;
import org.mybatis.generator.internal.DefaultShellCallback;

/**
 * mybatis根据表生成model，xml，dao
 * @author hasee
 *
 */
public class Main {
	public static void main(String[] args) throws Exception {
		List<String> warnings = new ArrayList<String>();  
		  boolean overwrite = true;  
		  File configFile = new File(Main.class.getResource("generator.xml").getFile());  
		  ConfigurationParser cp = new ConfigurationParser(warnings);  
		  Configuration config = cp.parseConfiguration(configFile);  
		  DefaultShellCallback callback = new DefaultShellCallback(overwrite);  
		  MyBatisGenerator myBatisGenerator = new MyBatisGenerator(config, callback, warnings);  
		  myBatisGenerator.generate(null);
	}
}
