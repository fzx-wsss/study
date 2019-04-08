package com.wsss.basic.common;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.util.ArrayDeque;
import java.util.Arrays;
import java.util.Comparator;
import java.util.LinkedList;
import java.util.List;
import java.util.Queue;

import com.jcraft.jsch.jce.Random;

public class BigFileSort {
	public static void main(String[] args) throws Exception {
		//会合并此目录下所有文件，生成的文件在此目录下to文件加下
		File files = new File("C:\\Users\\hasee\\Desktop\\test");
		//把每个文件拆成小文件，不超过100万行的不拆分
		for(File file:files.listFiles()) {
			BufferedReader br = new BufferedReader(new InputStreamReader(new FileInputStream(file)));
			String s = null;
			List<Line> list = new LinkedList<>();
			while((s = br.readLine()) != null) {
				String[] str = s.split(" ");
				list.add(new Line(str[0],str[1]));
				
				if(list.size() == 1000000) {
					read(list, file);
				}
			}
			
			read(list, file);
		}
		
		
		files = new File(files.getPath() + "/to");
		//合并文件
		while(files.listFiles().length > 1) {
			File first = null;
			File second = null;
			for(File file : files.listFiles()) {
				if(first == null) {
					first = file;
					continue;
				}
	
				second = file;
				
				merge(first, second);
				first = null;
				second = null;
			}
		}
	}
	
	public static void merge(File first,File second) throws Exception {
		File to = new File(first.getParent() + "/" + System.currentTimeMillis());
		
		OutputStream os = new FileOutputStream(to);
		BufferedReader br1 = new BufferedReader(new InputStreamReader(new FileInputStream(first)));
		BufferedReader br2 = new BufferedReader(new InputStreamReader(new FileInputStream(second)));
		
		String s1 = br1.readLine();
		String s2 = br2.readLine();
		while(s1 != null && s2 != null) {
			String[] arr1 = s1.split(" ");
			String[] arr2 = s2.split(" ");
			Line l1 = new Line(arr1[0],arr1[1]); 
			Line l2 = new Line(arr2[0],arr2[1]); 
			
			if(l1.getSort()> l2.getSort()) {
				wirteIn(os,s2.getBytes());
				s2 = br2.readLine();
			}else {
				wirteIn(os,s1.getBytes());
				s1 = br1.readLine();
			}
		}
		
		if(s1 == null) {
			if(s2 != null) wirteIn(os,s2.getBytes());
			while((s2 = br2.readLine()) != null) {
				wirteIn(os,s2.getBytes());
			}
		}
		
		if(s2 == null) {
			if(s1 != null) wirteIn(os,s1.getBytes());
			while((s1 = br1.readLine()) != null) {
				wirteIn(os,s1.getBytes());
			}
		}
		
		os.flush();
		br1.close();
		br2.close();
		os.close();
		
		first.delete();
		second.delete();
	}
	
	public static void wirteIn(OutputStream os,byte[] bytes) throws Exception {
		os.write(bytes);
		System.out.println(new String(bytes));
		os.write("\n".getBytes());
	}
	
	public static void read(List<Line> list,File file) throws Exception {
		Line[] lines = list.toArray(new Line[list.size()]);
		
		list = new LinkedList<>();
		
		Arrays.sort(lines, new Comparator<Line>() {
			@Override
			public int compare(Line o1, Line o2) {
				return o1.getSort() - o2.getSort();
			}
		});
		
		File toFile = new File(file.getParent() + "/to/" + System.currentTimeMillis());
		if(!toFile.getParentFile().exists()) toFile.getParentFile().mkdirs();
		write(lines, toFile);
	}
	
	public static void write(Line[] lines,File file) throws Exception {
		OutputStream os = new FileOutputStream(file);
		for(Line l : lines)
			os.write((l.getSort() + " " + l.getName() + "\n").getBytes());
		
		os.flush();
		os.close();
	}
	
	static class Line {
		private int sort;
		private String name;
		public Line(String s1,String s2) {
			this.sort = Integer.parseInt(s1);
			this.name = s2;
		}
		public int getSort() {
			return sort;
		}
		public void setSort(int sort) {
			this.sort = sort;
		}
		public String getName() {
			return name;
		}
		public void setName(String name) {
			this.name = name;
		}
		
	}
	
}
