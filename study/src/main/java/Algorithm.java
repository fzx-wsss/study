import java.util.ArrayDeque;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.HashSet;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Queue;
import java.util.Set;
import java.util.TreeSet;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ForkJoinPool;
import java.util.concurrent.RecursiveAction;
import java.util.concurrent.RecursiveTask;

import org.apache.commons.lang3.StringUtils;
import org.apache.commons.math3.analysis.function.Add;



public class Algorithm {
	static long startTime;
	static long endTime;
	
	static {
		startTime = System.currentTimeMillis();
	}
	public static void main(String[] args) throws Exception {
		// TODO Auto-generated method stub
		long s1 = System.currentTimeMillis();
		for(int i=0;i<100000000;i++) {
			String s = String.valueOf(s1);
			//String s = String.valueOf(s1).intern();
		}
		
		/*--------------------------------------------------------------*/
		test();
	}
	
	public static void test() {
		endTime = System.currentTimeMillis();
		long usedTime = endTime - startTime;
		long usedMemory = (Runtime.getRuntime().totalMemory()-Runtime.getRuntime().freeMemory())/1024;
		System.out.println("此程序使用内存为:" + usedMemory + "kb");
		System.out.println("此程序使用时间为：" + usedTime + "毫秒");
	}
	
	
	/**
	 * 设s、t为两个字符串，分别放在两个一维数组中，m、n分别为其长度，判断t是否为s的子串。如果是，输出子串所在位置（第一个字符），否则输出0。（注：用程序实现）【南京航空航天大学 1997 九(10分)】
	 * for(int i=0;i<100;i++) {
			System.out.println(new Algorithm1().algorithm_1("1234567890qwertyuiopasdfghjklzxcvbnm","fgh"));
		}
		此程序使用内存为:1331kb
		此程序使用时间为：1毫秒
	 */
	public int algorithm_1(String s,String t) {
		if(t.length() > s.length()) {
			return 0;
		}
		
		int result = 0;
		boolean success = false;
		
		char[] schars = s.toCharArray();
		char[] tchars = t.toCharArray();
		
		for(int i=0;i<schars.length && !success;i++) {
			boolean flag = true;
			
			for(int j=0;j<tchars.length && flag;j++) {
				if(schars[j+i] != tchars[j]) {
					flag = false;
					continue;
				}
				result = i + 1;
				success = true;
			}
		}
		
		return result;
	}
	
	/**
	 * 用1、2、2、3、4、5这六个数字，用java写一个main函数，打印出所有不同的排列，如：512234、412345等，要求："4"不能在第三位，"3"与"5"不能相连
	 * 网上对此题的一种解法：http://developer.51cto.com/art/200906/130626.htm
	 * main
	 * List<Integer> list = new ArrayList<Integer>();
		list.add(1);
		list.add(2);
		list.add(4);
		list.add(3);
		list.add(2);
		list.add(5);
		new Algorithm1().algorithm_2(list,null);
		此程序使用内存为:1997kb
		此程序使用时间为：40毫秒
	 */
	public void algorithm_2(List<Integer> arr,List<Integer> result) {
		if(null == result) {
			result = new ArrayList<Integer>();
		}
		
		if(arr.size() == 1) {
			result.add(arr.get(0));
			if(arrtest(result)) {
				System.out.println("符合条件：" + result);
			}else {
				System.out.println("不符合条件：" + result);
			}
			result.remove(arr.get(0));
			return;
		}
		
		for(int i=0;i<arr.size();i++) {
			Integer num = arr.get(i);
			result.add(num);
			List<Integer> list = new ArrayList<Integer>();
			list.addAll(arr);
			list.remove(i);
			algorithm_2(list,result);
			result.remove(num);
		}
	}
	/**
	 * 辅助上题algorithm_2
	 * @param arr
	 * @return
	 */
	public boolean arrtest(List<Integer> arr) {
		for(int i=0;i<arr.size();i++) {
			if(i==2 && arr.get(i).intValue() == 4) {
				return true;
			}
			
			if(arr.get(i).intValue() == 3 && arr.get(i).intValue() == 5) {
				return true;
			}
			
			if(arr.get(i).intValue() == 5 && arr.get(i).intValue() == 3) {
				return true;
			}
		}
		return false;
	}
	
	/**
	 * 用1、2、2、3、4、5这六个数字，用java写一个main函数，打印出所有不同的排列，如：512234、412345等，要求："4"不能在第三位，"3"与"5"不能相连
	 * 此程序使用内存为:3230kb
	         此程序使用时间为：2155毫秒
	 */
	
	public static void algorithm_3(List<String> list,List<String> res) {
		Set<String> set = new HashSet<>();
		if(list.isEmpty()) {
			String s = StringUtils.join(res.toArray());
			if(s.indexOf("4") == 2 || s.indexOf("35") != -1 || s.indexOf("53") != -1) return;
			System.out.println(s);
		}
		
		for(int i=0;i<list.size();i++) {
			if(set.contains(list.get(i))) continue;
			String s = list.remove(i);
			set.add(s);
			res.add(0,s);
			algorithm_3(list,res);
			res.remove(0);
			list.add(i, s);
		}
	}
	
	public static void algorithm_3() {
		StringBuffer s = new StringBuffer(6);
		
		for(int i=122345;i<=543221;i++) {
			s.append(i + "");
			if(s.indexOf("4") == 2) {
				s.setLength(0);
				continue;
			}
			if(s.indexOf("35") != -1 || s.indexOf("53") != -1) {
				s.setLength(0);
				continue;
			}
			System.out.println(s.toString());
			s.setLength(0);
		}
	}
	
	/**
	 * 用1、2、2、3、4、5这六个数字，用java写一个main函数，打印出所有不同的排列，如：512234、412345等，要求："4"不能在第三位，"3"与"5"不能相连
	 * 此程序使用内存为:1331kb
		此程序使用时间为：21毫秒
	 */
	private String[] b = new String[]{"1", "2", "2", "3", "4", "5"};  
	private int n = b.length;  
	private boolean[] visited = new boolean[n]; 
	private int[][] a = new int[n][n];  
	private String result = "";  
	private TreeSet treeSet = new TreeSet(); 
	
	
	public void algorithm_4() {  
		for(int i=0;i<n;i++) {
			visited[i] = false;
		}
			
		for (int i = 0; i < n; i++) {  
			for (int j = 0; j < n; j++) {  
				if (i == j) {  
					a[i][j] = 0;  
				} else {  
					a[i][j] = 1;  
				}  
			}  
		}
		a[3][5] = 0;  
		a[5][3] = 0;  
		
		for (int i = 0; i < n; i++) {  
		    this.depthFirstSearch(i);  
		}  
		Iterator it = treeSet.iterator();  
		while (it.hasNext()) {  
			String string = (String) it.next();  
		 
			if (string.indexOf("4") != 2) {  
				System.out.println(string);  
			}  
		}  
	}
	
	//辅助上题
	private void depthFirstSearch(int startIndex) {
		visited[startIndex] = true;
		result = result + b[startIndex];
		if (result.length() == n) {
			treeSet.add(result);
		}
		for (int j = 0; j < n; j++) {
			if (a[startIndex][j] == 1 && visited[j] == false) {
				depthFirstSearch(j);
			} else {
				continue;
			}
		}

		result = result.substring(0, result.length() - 1);
		visited[startIndex] = false;
	}

	/**
	 * 给你10分钟时间，根据上排给出十个数，在其下排填出对应的十个数
		要求下排每个数都是先前上排那十个数在下排出现的次数。
		上排的十个数如下：
		【0，1，2，3，4，5，6，7，8，9】
		举一个例子，
		数值: 0,1,2,3,4,5,6,7,8,9
		分配: 6,2,1,0,0,0,1,0,0,0
		0在下排出现了6次，1在下排出现了2次，
		2在下排出现了1次，3在下排出现了0次....
		以此类推.
	 * 
	 * @param ori
	 */
	public static void calculateCount(Integer[] ori) {
		Integer[] res = new Integer[ori.length];

		// flag表示第一层循环ori.length次之后，res数组都没有修改，则循环结束
		int flag = 0;
		for (int i = 0; flag < ori.length; i = (++i) % ori.length) {
			// 统计当前ori[i]在res中出现的次数
			int count = 0;
			for (int j = 0; j < res.length; j++) {
				if (null != res[j] && ori[i] == res[j]) {
					count++;
				}
			}

			if (null == res[i] || res[i] != count) {
				res[i] = count;
				flag = 0;
			} else {
				flag++;
			}
			System.out.println(Arrays.toString(res));
		}
	}
	
	public static void calculateCount(int[] ori) {
		int[] res = new int[ori.length];
		for(int i=0;i<res.length;i++) {
			res[i] = 0;
		}
		
		boolean success = true;
		// flag表示第一层循环ori.length次之后，res数组都没有修改，则循环结束
		while(add(res, res.length-1)) {
			success = true;
			for (int i=0;i<ori.length && success; i++) {
				int count = 0;
				for (int j = 0; j < res.length; j++) {
					if (ori[i] == res[j]) {
						count++;
					}
				}
				if(res[i] != count) success = false;
			}
			if(success) break;
		};
		
		System.out.println(success + Arrays.toString(res));
	}
	
	private static boolean add(int[] ori,int length) {
		if(length < 0) return false;
		if(++ori[length]>9) {
			ori[length]=0;
			return add(ori, length-1);
		}
		return true;
	}
	
	/**
	 * 很多成对出现数字保存在磁盘文件中，注意成对的数字不一定是相邻的，如2, 3, 4, 3, 4, 2……，由于意外有一个数字消失了，如何尽快的找到是哪个数字消失了？
	 */
	
	public static int findLost(int a[]){
        int result=0;
        for(int i=0;i<a.length;i++) {
            result^=a[i];
        }
        return result;
	} 
	
	/**
	 * 吸血鬼数字是指位数为偶数的数字，可以由一对数字相乘而得到，而这对数字各包含乘积的一半位数的数字，其中从最初的数字中选取的数字可以任意排序。
	 * 以两个0结尾的数字是不允许的，例如，下列数字都是“吸血鬼”数字：
	　　1260 = 21 * 60
	　　1827 = 21 * 87
	　　2187 = 27 * 81
		找出10000以内的所有吸血鬼数字
	 */
	public static void algor2() {
		for(int i=1;i<10000;i++) {
			for(int j=i;j<10000;j++) {
				int n = i;
				int m = j;
				int sum = i*j;
				
				if(sum > 10000) break;
				if((sum&1) == 1) continue; 
				List<Integer> list = new LinkedList<>();
				while(sum!=0) {
					list.add(sum%10);
					sum = sum/10;
				}
				
				List<Integer> list2 = new LinkedList<>();
				while(n!=0) {
					list2.add(n%10);
					n = n/10;
				}
				
				while(m!=0) {
					list2.add(m%10);
					m = m/10;
				}
				
				Collections.sort(list);
				Collections.sort(list2);
				if(list.size() != list2.size()) continue;
				boolean eq = true;
				for(int k=0;k<list.size() && eq;k++) {
					if(!list.get(k).equals(list2.get(k))) eq = false;
				}
				if(eq) System.out.println(i +"*"+ j + "=" + i*j);
			}
		}
	}
	
	/**
	 * N的阶乘(N!)中的末尾有多少个0?
     	例如:N = 5,N! = 120.末尾有1个0.
	 */
	public static int fun1(int n) {
		int num = 0;
		int i, j;

		for (i = 5; i <= n; i += 5) {
			j = i;
			while (j % 5 == 0) {
				num++;
				j /= 5;
			}
		}

		return num;
	}
	//上题的另一种解法
	public static int fun2(int n) {
		int num = 0;

		while (n != 0) {
			num += n / 5;
			n = n / 5;
		}

		return num;
	}
	/**
	 * int[] A = {1, -2, 3, 5, -1,-1,-1, 4};
		System.out.println(MaxSubString(A, A.length));
	 * @param A
	 * @param n
	 * @return
	 */
	public static int MaxSubString(int[] A, int n)
	{
	        int Start = A[0];
	        int All = A[0];
	        for(int i = 1; i < n; i++)    //从后向前遍历，反之亦可。
	        {
	                Start = Start < 0 ? A[i] : A[i] + Start;
	                All = Math.max(Start, All);
	        }
	        return All;                       //All[0] 中存放结果
	}
	
	/**
	 * 快速排序(递归)
	 * ForkJoinPool pool = new ForkJoinPool();
		int[] arr = new int[100000];
		for(int i=0;i<arr.length;i++) {
			arr[i] = (int)(Math.random()*100000);
		}
		
		for(int i=0;i<10;i++) {
			//quickSortNonRecursive(arr);
			//quickSort(arr,0,arr.length-1);
			//SortTask processpr = new SortTask(arr,0,arr.length-1);
			//pool.invoke(processpr);
		}
	 */
	public static void quickSort(int[] arr,int start,int end) {
		int avg = arr[start];
		int i=start,j=end;
		boolean b = false;
		while(j>i) {
			if(arr[i] > avg || arr[j] < avg) {
				int temp = arr[i];
				arr[i] = arr[j];
				arr[j] = temp;
				b = !b;
			}
			
			if(b) i++; else j--;
		}
		
		if(start < i-1) quickSort(arr,start,i-1);
		if(end > i+1) quickSort(arr,i+1,end);
	}
	
	/**
	 * 快速排序(非递归)
	 */
	public static void quickSortNonRecursive(int[] arr) {
		//Queue<Integer> queue = new ArrayDeque<>();
		Queue<Integer> queue = new ArrayDeque<Integer>();
		queue.add(0);
		queue.add(arr.length-1);
		
		while(!queue.isEmpty()) {
			int start = queue.poll();
			int end = queue.poll();
			int i = start;
			int j = end;
			int avg = arr[start];
			boolean b = false;
			while(j>i) {
				if(arr[i] > avg || arr[j] < avg) {
					int temp = arr[i];
					arr[i] = arr[j];
					arr[j] = temp;
					b = !b;
				}
				if(b) i++; else j--;
			}
			if(start < i-1) {
				queue.add(start);
				queue.add(i-1);
			}
			if(end > i+1) {
				queue.add(i+1);
				queue.add(end);
			}
		}
	}
	
	/**
	 * 快速排序（多线程）
	 * @author Administrator
	 *
	 */
	static class SortTask extends RecursiveAction {
		int[] arr;
		int start;
		int end;
		
		public SortTask(int[] arr, int start, int end) {
			super();
			this.arr = arr;
			this.start = start;
			this.end = end;
		}

		@Override
		protected void compute() {
			int avg = arr[start];
			int i=start,j=end;
			boolean b = false;
			while(j>i) {
				if(arr[i] > avg || arr[j] < avg) {
					int temp = arr[i];
					arr[i] = arr[j];
					arr[j] = temp;
					b = !b;
				}
				
				if(b) i++; else j--;
			}
			
			if(start < i-1) {
				SortTask task = new SortTask(arr,start,i-1);
				task.fork();
			}
			if(end > i+1) {
				SortTask task = new SortTask(arr,i+1,end);
				task.fork();
			}
		}
		
	}
	
	/**
	 * 现有‘abcdefghijkl’12个字符，将其所有的排列按字典序进行排序，给出任意一组排列，说出这租排列在所有排列中是第几小的
	 * System.out.println(getSortNum("abcdefghijlk")); //2
	 * System.out.println(getSortNum("hgebkflacdji")); //302715242
	 * System.out.println(getSortNum("gfkedhjblcia")); //260726926
	 */
	public static int getSortNum(String value) {
		// 记录已经出现过得字符和未出现的字符顺序
		String sort = "abcdefghijkl";
		
		// 记录结果数
		int res = 1;
		for(int i=0;i<value.length();i++) {
			// 依次取各位的字符
			char c = value.charAt(i);
			// 查看该字符在所有字符中的当前顺序
			int head = sort.lastIndexOf(c);
			// 计算排在前面的字符串数量
			res += ((head-i)*factorial(11-i));
			
			// 修改字符顺序，将当前出现的字符，交换到字符最前面
			// 因为我们不关心已经出现过得字符的顺序，我们只要知道出现过哪些字符以及未出现的字符的字典序
			// 所以直接把出现过得字符扔到最前面就可以了
			sort = change(sort,head);
		}
		return res;
	}
	
	/**
	 * 求阶乘
	 * @param i
	 * @return
	 */
	public static int factorial(int i) {
		if(i==0) return 0;
		int result = 1;
		for(;i>1;i--) {
			result *= i;
		}
		return result;
	}
	
	/**
	 * 交换字符串第a位和第b位
	 * @param str
	 * @param a
	 * @param b
	 * @return
	 */
	public static String change(String str,int a,int b) {
		if(a == b) {
			return str;
		}
		return str.replace("(.{"+(a)+"})(.)(.{"+(b-a-1)+"})(.)(.*)", "$1$4$3$2$5");
	}
	/**
	 * 交换字符串第a位和第b位
	 * 比正则表达式快很多
	 * @param str
	 * @param a
	 * @param b
	 * @return
	 */
	public static String changes(String str,int a,int b) {
		if(a == b) {
			return str;
		}
		char[] chars = str.toCharArray();
		char temp = chars[a];
		chars[a] = chars[b];
		chars[b] = temp;
		return String.valueOf(chars);
	}
	
	/**
	 * 将字符串第a位换到最前面
	 * @param str
	 * @param a
	 * @param b
	 * @return
	 */
	public static String change(String str,int a) {
		char[] chars = str.toCharArray();
		char temp = chars[a];
		for(int i=a;i>0;i--) {
			chars[i] = chars[i-1];
		}
		chars[0] = temp;
		return String.valueOf(chars);
	}
	
	/**
	 * 判断字符串b中的所有字符是否在字符串a中全部出现过,b中出现过几次的字符串，a中也要出现几次
	 */
	
	/**
	 * 输入一个数字n  如果n为偶数则除以2，若为奇数则加1或者减1，直到n为1，求最少次数  写出一个函数
	 * 动态规划法，效率比较低
	 */
	public static int getNum(int n) {
		if(n<1) {
			return 0;
		}
		int[] res = new int[n+1];
		res[0] = 0;
		res[1] = 0;
		for(int i=2;i<=n;i++) {
			if((i&1) == 0) {
				res[i] = res[i/2] + 1;
			}else {
				res[i] = Math.min(res[(i+1)/2], res[(i-1)/2]) + 2;
			}
		}
		return res[n];
	}
	
	/**
	 * 输入一个数字n  如果n为偶数则除以2，若为奇数则加1或者减1，直到n为1，求最少次数  写出一个函数
	 * 倒推法，递归，有递归的缺陷，效率也仍可改进
	 * @param n
	 * @return
	 */
	public static int getNum2(long n) {
		if(n<=1) {
			return 0;
		}
		
		if((n&1) == 0) {
			return getNum2(n/2) + 1;
		}else {
			long a = (n-1) / 2;
			long b = (n+1) / 2;

			return Math.min(getNum2(a), getNum2(b)) + 2;
		}
	}
	
	/**
	 * 将上面的递归式改写成非递归式
	 * @param n
	 * @return
	 */
	public static int getNum3(long n) {
		MyTask task = new MyTask(n);
		
		ForkJoinPool pool = new ForkJoinPool();
		int res = pool.invoke(task);
		pool.shutdown();
		return res;
	}
	
	static class MyTask extends RecursiveTask<Integer> {
		private long number;
		private static final Map<Long,Integer> map = new ConcurrentHashMap<Long,Integer>();
		
		public MyTask(long number) {
			super();
			this.number = number;
		}
		
		private synchronized void  put(Long a,Integer b) {
			if(map.containsKey(a)) {
				System.out.println("had existed!");
			}else {
				map.put(a, b);
			}
		}
		
		private Integer get(Long a) {
			System.out.println("success");
			return map.get(a);
		}

		@Override
		protected Integer compute() {
			if(number<=1) {
				put(number, 0);
				return 0;
			}else if(map.containsKey(number)) {
				return get(number);
			}
			
			int res = 0;
			if((number&1) == 0) {
				MyTask task = new MyTask(number / 2);
				
				try {
					res =  task.fork().get() + 1;
					put(number, res);
				} catch (Exception e) {
					e.printStackTrace();
				}
				return res;
			}else {
				MyTask task1 = new MyTask((number-1) / 2);
				MyTask task2 = new MyTask((number+1) / 2);
				try {
					int a = task1.fork().get() + 2;
					int b = task2.fork().get() + 2;
					
					res = Math.min(a,b);
					put(number, res);
				} catch (Exception e) {
					e.printStackTrace();
				}
				return res;
			}
		}
		
	}
	
	/**
	 * 如果我们有面值为1元、3元和5元的硬币若干枚，如何用最少的硬币凑够11元？
	 */
	public static int getMoney(int[] moneys,int money) {
		int[] res = new int[money+1];
		res[0] = 0;
		for(int i=1;i<=money;i++) {
			res[i] = Integer.MAX_VALUE - 1;
			
			for(int j=0;j<moneys.length;j++) {
				int current = i - moneys[j];
				if(current < 0) {
					continue;
				}
				
				res[i] = Math.min(res[i], res[current]+1);
			}
		}
		return res[money];
	}
	/**
	 * 有10张纸牌，编号分别是1到10，现在要将这10张纸牌分为2堆，其中一堆求和为36，另一堆求积为360，问应该怎么分？
	 * 也就是说，最终的结果应该是：一堆为2+7+8+9+10=36，另一堆为1*3*4*5*6=360。
	 * 当然，如果修改题目中的参数，改为32和360，那么结果就是2+3+4+6+7+10=32 和 1*5*8*9=360
	 * @author wsss
	 */
	public static void algor(List<Integer> list,int sum,int mul) {
		Queue<List<Integer>> queue = new LinkedList<>();
		
		for(Integer i:list) {
			List<Integer> l = new LinkedList<>();
			l.add(i);
			queue.add(l);
		}
		
		while(!queue.isEmpty()) {
			List<Integer> l = queue.poll();
			int s=0;
			for(int i : l) {
				s+=i;
			}
			if(s < sum) {
				for(int i : list) {
					List<Integer> newlist = new LinkedList<>();
					newlist.addAll(l);
					if(newlist.contains(i)) continue;
					
					newlist.add(i);
					queue.add(newlist);
				}
			}else if(s == sum) {
				int m =1;
				for(int i : list) {
					if(l.contains(i)) continue;
					
					m*=i;
				}
				if(m==mul) {
					System.out.println("sum:"+l);
				}
			}
		}
	}
	
	/**
	 * 牛牛的作业薄上有一个长度为 n 的排列 A，这个排列包含了从1到n的n个数，但是因为一些原因，其中有一些位置（不超过 10
	 * 个）看不清了，但是牛牛记得这个数列顺序对的数量是 k，顺序对是指满足 i < j 且 A[i] < A[j]
	 * 的对数，请帮助牛牛计算出，符合这个要求的合法排列的数目。
	 * 
	 * 输入描述: 每个输入包含一个测试用例。每个测试用例的第一行包含两个整数 n 和 k（1 <= n <= 100, 0 <= k <=
	 * 1000000000），接下来的 1 行，包含 n 个数字表示排列 A，其中等于0的项表示看不清的位置（不超过 10 个）
	 */
	public static void listAll(List<Integer> list) {
		Queue<Integer[]> queue = new LinkedList<Integer[]>();
		
		for(Integer i : list) {
			queue.add(new Integer[]{i});
		}
		list(list, queue);
		//List<Integer> print = new LinkedList<Integer>();
		//list(list,print);
	}
	private static int count=0;
	private static void list(List<Integer> list,List<Integer> list2) {
		if (list.isEmpty()) count++;
		else for (int i = 0; i < list.size(); i++) {
			Integer temp = list.remove(i);
			list2.add(temp);
			list(list, list2);
			list2.remove(list2.size() - 1);
			list.add(i, temp);
		}
	}
	
	private static void list(List<Integer> list,Queue<Integer[]> queue) {
		
		while(!queue.isEmpty()) {
			List<Integer> copy = new LinkedList<>(list);
			Integer[] arr = queue.poll();
			copy.removeAll(Arrays.asList(arr));
			
			for(Integer i : copy) {
				Integer[] newArr = Arrays.copyOf(arr, arr.length+1);
				newArr[arr.length] = i;
				if(newArr.length == list.size()) count++;
				else queue.add(newArr);
			}
			
		}
		
	}
	
	/*题目：公司进行了一次黑客马拉松大赛，全公司一共分为了N个组，每组一个房间排成一排开始比赛，比赛结束后没有公布成绩，但是每个组能够看到自己相邻的两个组里比自己成绩低的组的成绩，比赛结束之后要发奖金，以1w为单位，每个组都至少会发1w的奖金，另外，如果一个组发现自己的奖金没有高于比自己成绩低的组发的奖金，就会不满意，作为比赛的组织方，根据成绩计算出至少需要发多少奖金才能让所有的组满意。 

	输入描述：
	每组数据先输入N，然后N行输入N个正整数，每个数表示每个组的比赛成绩。

	输出描述：
	输出至少需要多少w的奖金

	示例1

	输入

	10
	20 
	32 
	12 
	32 
	45 
	11 
	21 
	31 
	41 
	33

	输出
	20*/
	private static void money(int[] scory) {
		int[] money = new int[scory.length];
		int[] res = new int[scory.length];
		money[0] =1;
		for(int i=1;i<money.length;i++) {
			if(scory[i] > scory[i-1]) money[i] = money[i-1] +1;
			if(scory[i] < scory[i-1]) money[i] = 1;
		}
		
		res[scory.length-1] = money[scory.length-1];
		for(int i=scory.length-2;i>-1;i--) {
			if(scory[i] > scory[i+1]) res[i] = res[i+1] +1;
			if(scory[i] < scory[i+1]) res[i] = 1;
		}
		
		int sum = 0;
		for(int i=0;i<money.length;i++) {
			res[i] = res[i] > money[i] ? res[i] : money[i];
			sum += res[i];
		}
		
		System.out.println(sum);
		
	}
}
