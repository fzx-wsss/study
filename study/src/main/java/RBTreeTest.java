
public class RBTreeTest {
	/**
	 * @param args
	 */
	public static void main(String[] args) {
		long start = System.currentTimeMillis();
		//List<String> list = new LinkedList<String>();
		//Map<String,String> map = new HashMap<>();
		RBTree rbTree = new RBTree();
		for(int i=0;i<10000000;i++) {
			//list.add(System.nanoTime() + "");
			//map.put(String.valueOf(System.nanoTime()), String.valueOf(System.nanoTime()));
			rbTree.rbInsert(System.nanoTime() + "");
		}
		System.out.println(System.currentTimeMillis() - start);
		System.out.println("");

		//rbTree.printTree();
		rbTree.print();
		//rbTree.rbDelete(t);

		//rbTree.printTree();

	}
	
	static class TreeNode implements Comparable<TreeNode> {
		private long sort;
		@Override
		public int compareTo(TreeNode o) {
			if(this.sort > o.sort) return 1;
			else if(this.sort == o.sort) return 0;
			else return -1;
		}
		public long getSort() {
			return sort;
		}
		public void setSort(long sort) {
			this.sort = sort;
		}
		public String toString() {
			return String.valueOf(sort);
		}
	}
}
