

import java.io.File;
import java.io.IOException;
import java.io.Serializable;
import java.net.URL;
import java.util.Arrays;
import java.util.Enumeration;
import java.util.LinkedList;
import java.util.List;
import java.util.Random;
import java.util.jar.JarEntry;
import java.util.jar.JarFile;

import org.apache.commons.lang3.StringUtils;

import com.wsss.frame.aviator.function.NumberConverFunction;

/**
 * @Description: 这里用一句话描述这个类的作用
 * @see: CIB520001Utils 此处填写需要参考的类
 * @version 2016年11月18日 下午1:40:50
 * @author zhongxuan.fan
 */
public class Test implements Serializable {
	private static Random random = new Random();
	public static void main(String[] args) throws IOException {
		String[] s = new String[] {"1","2","3","4"};
		String[] s1 = new String[] {"11","22","33","44"};
		Arrays.stream(s).map(str -> str.split("")).flatMap(Arrays::stream).forEach(System.out::println);// aaabbbccc
		//Arrays.stream(s).flatMap(x->Arrays.stream(x))
	}
	
	public static void listPackage(String packageName,File file) {
		if(!file.exists()) return;
		if(file.isDirectory()) {
			for(File f : file.listFiles()) {
				String next = packageName + "." + f.getName();
				listPackage(next,f);
			}
		}
		if(file.isFile()) {
			System.out.println(packageName);
		}
	}
	
	public static void listPackage(String packageName,JarFile jarFile) {
	    Enumeration<JarEntry> files  = jarFile.entries();
	    String path = packageName;
	    while (files .hasMoreElements()) {
	    	JarEntry entry = files.nextElement();
	    	if(entry.getName().startsWith(path) 
	    			&& entry.getName().endsWith(".class")
	    			&& !entry.getName().contains("$")) {
	    		String className = entry.getName().replaceAll("/", ".");
	    		className = className.substring(0, className.length()-6);
	    		System.out.println(className);
	    	}
	    	
	    }
	}
	
	public static List<String> toList(String ips) {
		int[][] all = new int[4][];
		String[] temp = ips.split("\\.");
		for(int i=0;i<temp.length;i++) {
			String[] ip = temp[i].split("-");
			if(ip.length == 1) {
				all[i] = new int[1];
				all[i][0] = Integer.parseInt(ip[0]);
				continue;
			}
			
			int start = Integer.parseInt(ip[0]);
			int end = Integer.parseInt(ip[1]);
			all[i] = new int[end-start+1];
			for(int j=start;j<=end;j++) {
				all[i][j-start] = j;
			}
		}
		
		List<String> res = new LinkedList<>();
		//res.add("");
		for(int[] index : all) {
			if(res.isEmpty()) {
				for(int num : index) {
					res.add(String.valueOf(num));
				}
				continue;
			}
			int len = res.size();
			for(int i=0;i<len;i++) {
				String s = res.remove(0);
				for(int num : index) {
					res.add(StringUtils.join(new Object[] {s,num},"."));
				}
			}
		}
		return res;
	}
	
}
