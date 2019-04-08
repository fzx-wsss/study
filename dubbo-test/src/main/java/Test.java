

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.io.Serializable;
import java.net.URL;
import java.util.ArrayList;
import java.util.Enumeration;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Set;

/**
 * @Description: 这里用一句话描述这个类的作用
 * @see: CIB520001Utils 此处填写需要参考的类
 * @version 2016年11月18日 下午1:40:50
 * @author zhongxuan.fan
 */
public class Test implements Serializable {
	
	public static void main(String[] args) throws Exception {
		Enumeration<URL> urls = Test.class.getClassLoader().getResources("META-INF/dubbo/internal/com.alibaba.dubbo.cache.CacheFactory");
		//Enumeration<URL> urls = Test.class.getClassLoader().getResources("META-INF/dubbo/internal/com.alibaba.dubbo.cache.CacheFactory");
		while(urls.hasMoreElements()) {
			System.out.println(urls.nextElement());
		}
	}
	
	public static List<String> groupKeyOper2(Map<String,Set<String>> groupKeyMap){
		List<String> resList=new ArrayList<String>();
		for(Entry<String,Set<String>> entry : groupKeyMap.entrySet()) {
			if(resList.isEmpty()) {
				resList.addAll(entry.getValue());
				continue;
			}
			
			List<String> tempList=new ArrayList<String>();
			for(String s1 : entry.getValue()) {
				for(String s2 : resList) {
					tempList.add(s2 + "," + s1);
				}
			}
			
			resList = tempList;
		}
		return resList;
	}
	
	
	public static List<String> groupKeyOper(Map<String,Set<String>> groupKeyMap){

		List<String> ruleKeyList=new ArrayList<String>();
		int i=0;
		for(String groupKeys:groupKeyMap.keySet()){
			List<String> tempList=new ArrayList<String>();

			for(String groupKey:groupKeyMap.get(groupKeys)){
				if(i==0){
					tempList.add(groupKey);
				}else{
					for(String str:ruleKeyList){
						tempList.add(str+","+groupKey);
					}
				}
			}
			i+=1;
			ruleKeyList.clear();
			ruleKeyList.addAll(tempList);
			tempList.clear();
		}
		return ruleKeyList;
	}
	
	
	
	public static void main1(String[] args) throws Exception {
		File file = new File("C:/Users/hasee/Desktop/111.txt");
		
		FileInputStream fis = new FileInputStream(file);
		BufferedReader br = new BufferedReader(new InputStreamReader(fis));
		String s = br.readLine();
		String replace = s.toCharArray()[0] + "";
		
		
		File file2 = new File("C:/Users/hasee/Desktop/222.txt");
		//file2.deleteOnExit();
		file2.createNewFile();
		FileOutputStream fis2 = new FileOutputStream(file2);
		BufferedWriter br2 = new BufferedWriter(new OutputStreamWriter(fis2));
		
		
		while((s=br.readLine()) != null) {
			String old = s;
			s = s.replaceAll("^\"([0-9]+).+$", "$1");
			char[] c = s.toCharArray();
			s = s.replace(replace, "");
			//System.out.println(s.intern().equals("1142"));
			System.out.println(s);
			int amount = Integer.parseInt(s);
			System.out.println(amount);
			amount = ((amount+999) / 1000) * 1000;
			
			String e = null;
			if(amount>50000) e = "50000+";
			else e = String.valueOf(amount);
			old = old + "," + e + "\n";
			br2.write(old);
		}
		br2.flush();
		br2.close();
	}
	
	public static double get(double d) {
		d += 2;
		return d;
	}
	
}
