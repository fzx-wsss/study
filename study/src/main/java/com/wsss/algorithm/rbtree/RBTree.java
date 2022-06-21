package com.wsss.algorithm.rbtree;

import java.util.LinkedList;
import java.util.Queue;

public class RBTree<T extends Comparable<T>> {

	private final Node<T> NIL = new Node<T>(null, null, null, Color.BLACK, null);
	private Node<T> root;

	public RBTree() {
		root = NIL;
	}

	public RBTree(Node<T> root) {
		this.root = root;
	}

	/**
	 * 插入节点
	 * @param node
	 */
	public void rbInsert(T n) {
		Node<T> node = new Node(n);
		Node<T> previous = NIL;
		Node<T> temp = root;

		while (temp != NIL) {
			previous = temp;
			if (temp.getValue().compareTo(node.getValue()) < 0) {
				temp = temp.getRight();
			} else {
				temp = temp.getLeft();
			}
		}
		node.setParent(previous);

		if (previous == NIL) {
			root = node;
			root.setParent(NIL);
		} else if (previous.getValue().compareTo(node.getValue()) > 0) {
			previous.setLeft(node);
		} else {
			previous.setRight(node);
		}

		node.setLeft(NIL);
		node.setRight(NIL);
		node.setColor(Color.RED);
		rb_Insert_Fixup(node);

	}

	/**
	 * 插入节点后的调整
	 * @param node
	 */
	private void rb_Insert_Fixup(Node<T> node) {
		// 只有当插入节点的父节点为红色时，才会对红黑树的性质产生影响，才会需要调整节点
		while (node.getParent().getColor() == Color.RED) { 
			// 当父节点为祖父节点的左孩子时
			if (node.getParent() == node.getParent().getParent().getLeft()) {

				Node<T> rightNuncle = node.getParent().getParent().getRight();
				
				// Case 1
				//当前结点的父结点是红色，叔叔结点是红色。
				//对策：将当前节点的父节点和叔叔节点涂黑，祖父结点涂红，把当前结点指向祖父节点，从新的当前节点重新开始算法。
				if (rightNuncle.getColor() == Color.RED) { 

					rightNuncle.setColor(Color.BLACK);
					node.getParent().setColor(Color.BLACK);
					node.getParent().getParent().setColor(Color.RED);
					node = node.getParent().getParent();
				
				// case 2
				//当前节点的父节点是红色,叔叔节点是黑色，当前节点是其父节点的右子
				//当前节点的父节点做为新的当前节点，以新当前节点为支点左旋。
				} else if (node == node.getParent().getRight()) { 

					node = node.getParent();
					leftRotate(node);
				
				// case 3
				//当前节点的父节点是红色,叔叔节点是黑色，当前节点是其父节点的左子
				//对策：父节点变为黑色，祖父节点变为红色，在祖父节点为支点右旋
				} else { 

					node.getParent().setColor(Color.BLACK);
					node.getParent().getParent().setColor(Color.RED);

					rightRotate(node.getParent().getParent());
				}
			
			//如果是右孩子，其实只是跟这种情况旋转方向相反而已
			} else {

				Node<T> leftNuncle = node.getParent().getParent().getLeft();

				if (leftNuncle.getColor() == Color.RED) { // case 4

					leftNuncle.setColor(Color.BLACK);
					node.getParent().setColor(Color.BLACK);
					node.getParent().getParent().setColor(Color.RED);
					node = node.getParent().getParent();

				} else if (node == node.getParent().getLeft()) { // case 5

					node = node.getParent();
					rightRotate(node);

				} else { // case 6

					node.getParent().setColor(Color.BLACK);
					node.getParent().getParent().setColor(Color.RED);
					leftRotate(node.getParent().getParent());

				}

			}

		}

		root.setColor(Color.BLACK);

	}

	/**
	 * 删除节点
	 * @param data
	 * @return
	 */
	public Node<T> rbDelete(T data) {

		Node<T> node = search(data);
		Node<T> temp = NIL;
		Node<T> child = NIL;
		if (node == null) {
			return null;
		} else {
			if (node.getLeft() == NIL || node.getRight() == NIL) {
				temp = node;
			} else {
				temp = successor(node);
			}

			if (temp.getLeft() != NIL) {
				child = temp.getLeft();
			} else {
				child = temp.getRight();
			}

			child.setParent(temp.getParent());

			if (temp.getParent() == NIL) {
				root = child;
			} else if (temp == temp.getParent().getLeft()) {
				temp.getParent().setLeft(child);
			} else {
				temp.getParent().setRight(child);
			}

			if (temp != node) {
				node.setValue(temp.getValue());
			}

			if (temp.getColor() == Color.BLACK) {
				rb_Delete_Fixup(child);
			}
			return temp;
		}

	}

	/**
	 * 删除节点后的调整
	 * @param node
	 */
	private void rb_Delete_Fixup(Node<T> node) {

		while (node != root && node.getColor() == Color.BLACK) {

			if (node == node.getParent().getLeft()) {

				Node<T> rightBrother = node.getParent().getRight();
				if (rightBrother.getColor() == Color.RED) { // case 1
															// node节点为左孩子，node节点的兄弟为RED
					rightBrother.setColor(Color.BLACK);
					node.getParent().setColor(Color.RED);
					leftRotate(node.getParent());
					rightBrother = node.getParent().getRight();
				}

				if (rightBrother.getLeft().getColor() == Color.BLACK
						&& rightBrother.getRight().getColor() == Color.BLACK) {
					rightBrother.setColor(Color.RED);
					node = node.getParent();
				} else if (rightBrother.getRight().getColor() == Color.BLACK) {
					rightBrother.getLeft().setColor(Color.BLACK);
					rightBrother.setColor(Color.RED);
					rightRotate(rightBrother);
					rightBrother = node.getParent().getRight();
				} else {
					rightBrother.setColor(node.getParent().getColor());
					node.getParent().setColor(Color.BLACK);
					rightBrother.getRight().setColor(Color.BLACK);
					leftRotate(node.getParent());
					node = root;
				}

			} else {

				Node<T> leftBrother = node.getParent().getLeft();
				if (leftBrother.getColor() == Color.RED) {
					leftBrother.setColor(Color.BLACK);
					node.getParent().setColor(Color.RED);
					rightRotate(node.getParent());
					leftBrother = node.getParent().getLeft();
				}

				if (leftBrother.getLeft().getColor() == Color.BLACK
						&& leftBrother.getRight().getColor() == Color.BLACK) {
					leftBrother.setColor(Color.RED);
					node = node.getParent();

				} else if (leftBrother.getLeft().getColor() == Color.BLACK) {

					leftBrother.setColor(Color.RED);
					leftBrother.getRight().setColor(Color.BLACK);
					leftRotate(leftBrother);
					leftBrother = node.getParent().getLeft();

				} else {

					leftBrother.setColor(node.getParent().getColor());
					node.getParent().setColor(Color.BLACK);
					leftBrother.getLeft().setColor(Color.BLACK);
					rightRotate(node.getParent());
					node = root;

				}

			}

		}

		node.setColor(Color.BLACK);
	}

	/**
	 * 查找节点node的后继节点
	 * @param node
	 * @return
	 */
	public Node<T> successor(Node<T> node) {

		Node<T> rightChild = node.getRight();
		if (rightChild != NIL) {
			Node<T> previous = null;
			while (rightChild != NIL) {
				previous = rightChild;
				rightChild = rightChild.getLeft();
			}
			return previous;
		} else {

			Node<T> parent = node.getParent();
			while (parent != NIL && node != parent.getLeft()) {
				node = parent;
				parent = parent.getParent();
			}

			return parent;

		}

	}

	/**
	 * 查找节点
	 * @param data
	 * @return
	 */
	public Node<T> search(T data) {
		Node<T> temp = root;

		while (temp != NIL) {
			if (temp.getValue().compareTo(data) == 0) {
				return temp;
			} else if (temp.getValue().compareTo(data) < 0) {
				temp = temp.getRight();
			} else {
				temp = temp.getLeft();
			}
		}
		return null;
	}

	/**
	 * 左转函数
	 * @param node
	 */
	private void leftRotate(Node<T> node) {

		Node<T> rightNode = node.getRight();

		node.setRight(rightNode.getLeft());
		if (rightNode.getLeft() != NIL) {
			rightNode.getLeft().setParent(node);
		}
		rightNode.setParent(node.getParent());

		if (node.getParent() == NIL) {
			root = rightNode;
		} else if (node == node.getParent().getLeft()) {
			node.getParent().setLeft(rightNode);
		} else {
			node.getParent().setRight(rightNode);
		}

		rightNode.setLeft(node);
		node.setParent(rightNode);

	}

	/**
	 * 右转函数
	 * @param node
	 */
	private void rightRotate(Node<T> node) {

		Node<T> leftNode = node.getLeft();
		node.setLeft(leftNode.getRight());

		if (leftNode.getRight() != null) {
			leftNode.getRight().setParent(node);
		}

		leftNode.setParent(node.getParent());

		if (node.getParent() == NIL) {
			root = leftNode;
		} else if (node == node.getParent().getLeft()) {
			node.getParent().setLeft(leftNode);
		} else {
			node.getParent().setRight(leftNode);
		}

		leftNode.setRight(node);
		node.setParent(leftNode);

	}

	/**
	 * 中序遍历红黑树
	 */
	public void printTree() {
		inOrderTraverse(root);
		System.out.println("---------------------------------------------------");
	}
	
	public void print() {
		Queue<Node<T>> queue = new LinkedList<>();
		queue.add(root);
		while(!queue.isEmpty()) {
			for(Node<T> n : queue) {
				if(n != NIL) {
					System.out.print(n.getValue() + "  ");
				}else {
					System.out.print("null  ");
				}
			}
			//System.out.print(n.getValue());
			System.out.println("");
			Queue<Node<T>> queue2 = new LinkedList<>();
			for(Node<T> n : queue) {
				if(n != NIL) {
					queue2.add(n.getLeft());
					queue2.add(n.getRight());
				}
			}
			queue = queue2;
		}
	}

	private void inOrderTraverse(Node<T> node) {

		if (node != NIL) {
			inOrderTraverse(node.getLeft());
			System.out.println(" 节点：" + node.getValue() + "的颜色为："
					+ node.getColor());
			inOrderTraverse(node.getRight());
		}

	}

	public Node<T> getNIL() {
		return NIL;
	}

}

class Node<T> {
	private Node<T> left;
	private Node<T> right;
	private Node<T> parent;
	private Color color;
	private T value;

	public Node(Node<T> left, Node<T> right, Node<T> parent, Color color, T value) {
		super();
		this.left = left;
		this.right = right;
		this.parent = parent;
		this.color = color;
		this.value = value;
	}

	public Node() {
	}

	public Node(T value) {
		this(null, null, null, null, value);
	}

	public Node<T> getLeft() {
		return left;
	}

	public void setLeft(Node<T> left) {
		this.left = left;
	}

	public Node<T> getRight() {
		return right;
	}

	public void setRight(Node<T> right) {
		this.right = right;
	}

	public Node<T> getParent() {
		return parent;
	}

	public void setParent(Node<T> parent) {
		this.parent = parent;
	}

	public Color getColor() {
		return color;
	}

	public void setColor(Color color) {
		this.color = color;
	}

	public T getValue() {
		return value;
	}

	public void setValue(T value) {
		this.value = value;
	}

}

enum Color {
	RED, BLACK
}