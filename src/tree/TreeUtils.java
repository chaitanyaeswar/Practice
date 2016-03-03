package tree;

import java.util.LinkedList;
import java.util.Queue;
import java.util.Stack;

import binarytree.Node;
import binarytree.NodePointer;

public class TreeUtils {

	public static LinkedList<Node> bfs(Node root) {

		LinkedList<Node> bfsList = new LinkedList<Node>(); 

		if (root == null) return bfsList;
		
		Queue<Node> queue = new LinkedList<Node>();
		queue.add(root);

		while(!queue.isEmpty()) {

			Node current = queue.remove();
			bfsList.add(current);

			if (current.left != null) {
				queue.add(current.left);
			}
			if (current.right != null) {
				queue.add(current.right);
			}
		}

		return bfsList;
	}
	
	public static void bfsLevelWise(Node root) {

		if (root == null) return;
		
		int elementsInThisLevel = 0;
		
		Queue<Node> queue = new LinkedList<Node>();
		queue.add(root);
		elementsInThisLevel++;

		while(!queue.isEmpty()) {

			Node current = queue.remove();
			System.out.print(current.value + " ");
			
			if (current.left != null) {
				queue.add(current.left);
			}
			if (current.right != null) {
				queue.add(current.right);
			}
			
			elementsInThisLevel--;
			if (elementsInThisLevel == 0) {
				System.out.println();
				elementsInThisLevel = queue.size();
			}
		}
	}
	
	public static LinkedList<Node> inorder(Node root) {
		LinkedList<Node> inorderList = new LinkedList<Node>();
		
		if (root == null) return inorderList;
		
		inorderRecursive(root, inorderList);
		
		return inorderList;
	}
	
	public static Node createTreetoDllInorder(Node root) {
		if (root == null) return null;
		
		NodePointer headPointer = new NodePointer();
		
		createTreetoDllInorder(root, headPointer);
		
		return headPointer.node;
	}
	
	private static void createTreetoDllInorder(Node current, NodePointer previous) {
		if(current != null) {
			createTreetoDllInorder(current.right, previous);
			current.right = previous.node;
			if(previous.node != null) previous.node.left = current;
			previous.node = current;
			createTreetoDllInorder(current.left, previous);
		}
	}
	
	private static void inorderRecursive(Node current, LinkedList<Node> list) {
		if(current != null) {
			inorderRecursive(current.left, list);
			list.add(current);
			inorderRecursive(current.right, list);
		}
	}
	
	public static LinkedList<Node> inorderWithoutRecursion(Node node) {
		LinkedList<Node> list = new LinkedList<Node>();
		
		if (node == null) return list;
		
		Stack<Node> stack = new Stack<Node>();
		stack.push(node);
		
		while (!stack.isEmpty()) {
			while (node != null && node.left != null) {
				stack.push(node.left);
				node = node.left;
			}
			
			node = stack.pop();
			list.add(node);
			
			if (node != null && node.right != null) {
				stack.push(node.right);
				node = node.right;
			}
		}
		
		return list;
	}
	
	public static LinkedList<Node> preorder(Node root) {
		LinkedList<Node> inorderList = new LinkedList<Node>();
		
		if (root == null) return inorderList;
		
		preorderRecursive(root, inorderList);
		
		return inorderList;
	}
	
	private static void preorderRecursive(Node current, LinkedList<Node> list) {
		if(current != null) {
			list.add(current);
			preorderRecursive(current.left, list);
			preorderRecursive(current.right, list);
		}
	}
	
	public static LinkedList<Node> postorder(Node root) {
		LinkedList<Node> inorderList = new LinkedList<Node>();
		
		if (root == null) return inorderList;
		
		postorderRecursive(root, inorderList);
		
		return inorderList;
	}
	
	private static void postorderRecursive(Node current, LinkedList<Node> list) {
		if(current != null) {
			postorderRecursive(current.left, list);
			postorderRecursive(current.right, list);
			list.add(current);
		}
	}
	
	public static int size(Node cur) {
		if(cur != null) return (1 + size(cur.left) + size(cur.right));
		else return 0;
	}
	
	public static int height(Node cur) {
		if(cur != null) {
			int lh = height(cur.left);
			int rh = height(cur.right);
			
			if(lh > rh) return (lh + 1);
			return (rh + 1);
		}
		
		return 0;
	}
	
	public static boolean identicalTrees(Node n1, Node n2) {
		if(n1 == null && n2 == null) return true;
		if(n1 == null || n2 == null) return false;
		return ((n1.value == n2.value) && (identicalTrees(n1.left, n2.left)) && (identicalTrees(n1.right, n2.right)));
	}
	
	public static Boolean inorderPredecessorAndSuccessor(Node cur, NodePointer pre, NodePointer suc, int ele) {
		if(cur != null) {
			boolean found = inorderPredecessorAndSuccessor(cur.left, pre, suc, ele);
			if(found) return found;
			if (cur.value == ele) {
				suc.node = getLeftMostNode(cur.right);
				return true;
			} else {
				pre.node = cur;
			}
			
			return inorderPredecessorAndSuccessor(cur.right, pre, suc, ele);
		}else {
			return false;
		}
	}

	private static Node getLeftMostNode(Node cur) {
		while(cur != null) cur = cur.left;
		return cur;
	}
	
	public static Integer inorderPredecesor(Node cur, int ele) {
		
		if (cur != null) {

			Node pre = null;
			Stack<Node> stack = new Stack<Node>();
			stack.push(cur);
			
			while(!stack.isEmpty()) {
				while(cur.left != null) {
					stack.push(cur.left);
					cur = cur.left;
				}
				cur = stack.pop();
				if(cur.value == ele)
					if (pre != null) return pre.value;
					else return null;
				
				pre = cur;
				
				if (cur.right != null) {
					stack.push(cur.right);
					cur = cur.right;
				}
			}
		}
		return null;
	}
	
public static Integer inorderSuccessor(Node cur, int ele) {
		
		if (cur != null) {

			Node pre = null;
			Stack<Node> stack = new Stack<Node>();
			stack.push(cur);
			
			while(!stack.isEmpty()) {
				while(cur.left != null) {
					stack.push(cur.left);
					cur = cur.left;
				}
				cur = stack.pop();
				
				if(pre != null && pre.value == ele)
					return cur.value;
				
				pre = cur;
				
				if (cur.right != null) {
					stack.push(cur.right);
					cur = cur.right;
				}
			}
		}
		return null;
	}
	
	public void inorderPredecessorRecursive(Node cur, NodePointer pre, int ele) {
		if(cur != null) {
			inorderPredecessorRecursive(cur.left, pre, ele);
			if(cur.value == ele) return;
			else pre.node = cur;
			inorderPredecessorRecursive(cur.right, pre, ele);
		}
	}
}
