package com.wsss.algorithm.sort;

import java.util.Arrays;
import java.util.Random;

/**
 * 堆排序
 * @author hasee
 *
 */
public class HeapSort {
	public static void main(String[] args) {
		int[] ints = new int[100];
		Random ra = new Random();
		for(int i=0;i<ints.length;i++) {
			ints[i] = ra.nextInt(10);
		}
		
		sort(ints);
	}
	
	public static void sort(int[] ints) {
		int len = ints.length;
		for(int i=len-1; i>=0; i--) {
			for(int j=(i-1)/2; j>=0; j--) {
				int next = 2*j;
				
				if(next + 2 > i) {
					int left = next + 1;
					//int right = next + 2 > i ? left : next + 2;
					//int max = ints[left] > ints[right] ? ints[left] : ints[right];
					//int flag = ints[left] > ints[right] ? left : right;
					if(ints[left] > ints[j]) {
						change(ints, j, left);
					}
					continue;
				} 
				
				int left = next + 1;
				int right = next + 2;
				int max = ints[left] > ints[right] ? ints[left] : ints[right];
				int flag = ints[left] > ints[right] ? left : right;
				if(max > ints[j]) {
					change(ints, j, flag);
				}
			}
			
			change(ints, 0, i);
		}
		
		System.out.println(Arrays.toString(ints));
	}
	
	public static void change(int[] ints,int l,int r) {
		ints[l] = ints[l] ^ ints[r];
		ints[r] = ints[l] ^ ints[r];
		ints[l] = ints[l] ^ ints[r];
	}
}
