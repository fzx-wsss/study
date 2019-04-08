package com.wsss.basic.shell;

import java.io.BufferedReader;
import java.io.File;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.nio.charset.Charset;
import java.util.Arrays;
import java.util.List;
import java.util.Queue;
import java.util.concurrent.TimeUnit;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;


/**
 * 命令执行包装类
 * @author chenchen.qi
 *
 */
public class CommandProcess {

	private Process process;
	private BufferedReader inputBuffRead = null;
	private BufferedReader errorBuffRead = null;
	private boolean inputStreamFlag;//是否继续读取输入流
	private boolean errorStreamFlag;//是否继续读取错误流
	private Logger logger = LoggerFactory.getLogger(getClass());
	
	public static void main(String[] args) throws Exception {
		CommandProcess process = new CommandProcess();
		process.start(Arrays.asList(new String[]{"java","-version"}), null);
		System.out.println(process.waitFor());
		StringBuilder sb = process.readInputStream(true, null, false, new StringBuilder());
		//System.out.println(sb.toString());
		StringBuilder sb2 = process.readErrorStream(true, null, false, new StringBuilder());
		//System.out.println(sb2.toString());
		//TimeUnit.SECONDS.sleep(10);
	}
	
	public void start(List<String> commandList, String dir) throws Exception{
		this.inputStreamFlag = false;
		this.errorStreamFlag = false;
		ProcessBuilder builder = new ProcessBuilder(commandList);
		if (!"".equals(dir) && dir != null) {
			builder.directory(new File(dir));
		}
		this.process = builder.start();
	}
	
	public StringBuilder readInputStream(boolean printout, Queue<String> queue, boolean putQueue, StringBuilder runInfoSb) throws Exception{
		if(this.process != null){
			InputStream inputSteam = this.process.getInputStream();
			this.inputBuffRead = new BufferedReader(new InputStreamReader(inputSteam,Charset.forName("GBK")));
			String line1 = null;
			while (!inputStreamFlag){
				line1 = this.inputBuffRead.readLine();
				if(line1 == null){
					break;
				}
				runInfoSb.append(line1).append("\n");
				if(printout && putQueue){
					queue.offer(line1 + "\n;");
//					logger.info(line1);
				}else if(printout && !putQueue){
					logger.info(line1);
				}
			}
		}
		return runInfoSb;
	}
	
	public StringBuilder readErrorStream(boolean printout, Queue<String> queue, boolean putQueue, StringBuilder runInfoSb) throws Exception{
		if(this.process != null){
			InputStream errorSteam = this.process.getErrorStream();
			this.errorBuffRead = new BufferedReader(new InputStreamReader(errorSteam));
			String line2 = null;
			while (!errorStreamFlag) {
				line2 = this.errorBuffRead.readLine();
				if(line2 == null){
					break;
				}
				runInfoSb.append(line2).append("\n");
				if(printout && putQueue){
					queue.offer(line2 + "\n;");
//					logger.info(line2);
				}else if(printout && !putQueue){
					logger.info(line2);
				}
			}
		}
		return runInfoSb;
	}
	
	public int waitFor() throws Exception{
		if(this.process != null){
			return this.process.waitFor();//为0则表示正常，输出在br1里，非0则表示错误，输出在br2里
		}else{
			return 0;
		}
		
	}
	
	public void destroy(){
		this.inputStreamFlag = true;
		this.errorStreamFlag = true;
		if(this.process != null){
			this.process.destroy();
			this.process = null;
		}
	}
}
