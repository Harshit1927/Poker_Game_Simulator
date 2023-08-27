package heap_package;
import java.util.ArrayList;
 
public class Heap{

	protected Node root;								// root of the heap
	protected Node[] nodes_array;                    // Stores the address of node corresponding to the keys
	private int max_size;                           // Maximum number of nodes heap can have 
	private static final String NullKeyException = "NullKey";      // Null key exception
	private static final String NullRootException = "NullRoot";    // Null root exception
	private static final String KeyAlreadyExistsException = "KeyAlreadyExists";   // Key already exists exception

	/* 
	   1. Can use helper methods but they have to be kept private. 
	   2. Not allowed to use any data structure. 
	*/

	
	public Heap(int max_size, int[] keys_array, int[] values_array) throws Exception{

		/* 
		   1. Create Max Heap for elements present in values_array.
		   2. keys_array.length == values_array.length and keys_array.length number of nodes should be created. 
		   3. Store the address of node created for keys_array[i] in nodes_array[keys_array[i]].
		   4. Heap should be stored based on the value i.e. root element of heap should 
		      have value which is maximum value in values_array.
		   5. max_size denotes maximum number of nodes that could be inserted in the heap. 
		   6. keys will be in range 0 to max_size-1.
		   7. There could be duplicate keys in keys_array and in that case throw KeyAlreadyExistsException. 
		*/

		/* 
		   For eg. keys_array = [1,5,4,50,22] and values_array = [4,10,5,23,15] : 
		   => So, here (key,value) pair is { (1,4), (5,10), (4,5), (50,23), (22,15) }.
		   => Now, when a node is created for element indexed 1 i.e. key = 5 and value = 10, 
		   	  that created node address should be saved in nodes_array[5]. 
		*/ 
		
		/*
		n = keys_array.length
		Expected Time Complexity : O(n).
		*/
		// System.out.println(" buld");
		// for (Integer x:values_array){
		// 	System.out.print(x+" ");
		// }
		// System.out.println();
		// for (Integer x:keys_array){
		// 	System.out.print(x+" ");
		// }
		// System.out.println();
		int[] h1 = new int[keys_array.length];
		int[] h2 = new int[values_array.length];
		for (int i=0; i<keys_array.length; i++){
			h1[i] = keys_array[i];
			h2[i] = values_array[i];
		}

		this.max_size = max_size;
		this.nodes_array = new Node[this.max_size];
		for (int i=(h1.length); i>=0; i--){
			perc(h1, h2, i);
		}

		for (int i=0; i<h1.length; i++){
			if (nodes_array[h1[i]]!=null){
				throw new Exception(KeyAlreadyExistsException);
			} else {
				int jk = (i+1)/2-1;
				if (i!=0){
					nodes_array[h1[i]] = new Node(h1[i], h2[i], nodes_array[h1[jk]]);
				} else {
					nodes_array[h1[i]] = new Node(h1[i], h2[i], null);
				}
				// nodes_array[h1[i]].key = h1[i];
				// nodes_array[h1[i]].value = h2[i];
				if (i!=0){
					if (i%2==0){
						nodes_array[h1[jk]].right = nodes_array[h1[i]];
					} else {
						nodes_array[h1[jk]].left = nodes_array[h1[i]];
					}
				}
			}
		}

		for (int i = h1.length-1; i>=0; i--){
			int l = 0, r = 0;
			if (nodes_array[h1[i]].left!=null){
				l = nodes_array[h1[i]].left.height;
			}
			if (nodes_array[h1[i]].right!=null){
				r = nodes_array[h1[i]].right.height;
			}
			if (l==r){
				boolean curtail = false;
				if (nodes_array[h1[i]].left==null){
					curtail = true;
				} else if (nodes_array[h1[i]].left.is_complete && nodes_array[h1[i]].right.is_complete){
					curtail = true;
				} else {
					curtail = false;
				}
				nodes_array[h1[i]].is_complete = curtail;
			} else {
				nodes_array[h1[i]].is_complete = false;
			}
			nodes_array[h1[i]].height = max(l, r) + 1;
		}
		if (h1.length>0){
			root = nodes_array[h1[0]];
		}
		// To be filled in by the student
	}
	
	
	public ArrayList<Integer> getMax() throws Exception{
		// System.out.println(" getm");
		/* 
		   1. Returns the keys with maximum value in the heap.
		   2. There could be multiple keys having same maximum value. You have
		      to return all such keys in ArrayList (order doesn't matter).
		   3. If heap is empty, throw NullRootException.

		   Expected Time Complexity : O(1).
		*/

		ArrayList<Integer> max_keys = new ArrayList<Integer>();    // Keys with maximum values in heap.

		if (root == null){
			throw new Exception(NullRootException);
		}
		// To be filled in by the student
		get(max_keys, root);

		return max_keys;
	}

	public void insert(int key, int value) throws Exception{
		// System.out.println(" ins "+key+" "+value);

		/* 
		   1. Insert a node whose key is "key" and value is "value" in heap 
		      and store the address of new node in nodes_array[key]. 
		   2. If key is already present in heap, throw KeyAlreadyExistsException.

		   Expected Time Complexity : O(logn).
		*/
		if (key>nodes_array.length || nodes_array[key]!=null){
			throw new Exception(KeyAlreadyExistsException);
		}
		if (root==null){
			root = new Node(key, value, null);
			root.is_complete = true;
			root.height = 1;
			return;
		}
		Node huh = root;
		boolean l = false;
		while (huh!=null){
			if (huh.height==1){
				l = true;
				break;
			} else if (huh.left.is_complete==false){
				huh = huh.left;
			} else if (huh.right == null){
				break;
			} else if (huh.right.is_complete==false){
				huh = huh.right;
			} else {
				if (huh.right.height==huh.left.height){
					huh = huh.left;
				} else {
					huh = huh.right;
				}
			}
		}
		if (l){
			Node nt = new Node(key, value, huh);
			huh.left = nt;
			nodes_array[key] = nt;
			while (nt.parent!=null){
				jip(nt);
				if (nt.parent.value<nt.value){
					swap(nt, nt.parent);
					// int tem = nt.parent.value, tem2 = nt.parent.key;
					// nt.parent.value = nt.value;
					// nt.parent.key = nt.key;
					// nt.value = tem;
					// nt.key = tem2;
					// nt = nt.parent;
				} else {
					nt = nt.parent;
					// jip(nt);
				}
			}
			jip(nt);
		} else {
			Node nt = new Node(key, value, huh);
			huh.right = nt;
			nodes_array[key] = nt;
			while (nt.parent!=null){
				jip(nt);
				if (nt.parent.value<nt.value){
					swap(nt, nt.parent);
					// int tem = nt.parent.value, tem2 = nt.parent.key;
					// nt.parent.value = nt.value;
					// nt.parent.key = nt.key;
					// nt.value = tem;
					// nt.key = tem2;
					// nt = nt.parent;
					// jip(nt);
				} else {
					nt = nt.parent;
				}
			}
			jip(nt);
		}
		// To be filled in by the student
	}

	public ArrayList<Integer> deleteMax() throws Exception{
		// System.out.println("dmax ");

		/* 
		   1. Remove nodes with the maximum value in the heap and returns their keys.
		   2. There could be multiple nodes having same maximum value. You have
		      to delete all such nodes and return all such keys in ArrayList (order doesn't matter).
		   3. If heap is empty, throw NullRootException.

		   Expected Average Time Complexity : O(logn).
		*/

		ArrayList<Integer> max_keys = new ArrayList<Integer>();   // Keys with maximum values in heap that will be deleted.

		// To be filled in by the student
		if (root == null){
			throw new Exception(NullRootException);
		} else {
			int cur = root.value;
			while (cur==root.value){
				max_keys.add(root.key);
				drt(root);
				if (root == null){
					break;
				}
				Node nt = nodes_array[root.key];
				// jip(nt);
				while (nt.height!=1){
					int l = 0;
					if (nt.left==null){
						break;
					} else if (nt.right==null){
						l=1;
					} else if (nt.left.value>nt.right.value){
						l = 1;
					} else {
						l = 0;
					}
					if (l==1 && nt.left.value>nt.value){
						swap2(nt.left, nt);
						// int tem = nt.parent.value, tem2 = nt.parent.key;
						// nt.parent.value = nt.value;
						// nt.parent.key = nt.key;
						// nt.value = tem;
						// nt.key = tem2;
						// nt = nt.parent;
					} else if (l==0 && nt.right.value>nt.value){
						// nt = nt.parent;
						// jip(nt);
						swap2(nt.right, nt);
					} else {
						break;
					}
					jip(nt);
				}
			}
		}
		return max_keys;
	}

	public void update(int key, int diffvalue) throws Exception{
		// System.out.println("upd "+key+" "+diffvalue);

		/* 
		   1. Update the heap by changing the value of the node whose key is "key" to value+diffvalue.
		   2. If key doesn't exists in heap, throw NullKeyException.

		   Expected Time Complexity : O(logn).
		*/
		if (nodes_array[key]==null){
			throw new Exception(NullKeyException);
		}
		// To be filled in by the student
		if (diffvalue>0){
			nodes_array[key].value+=diffvalue;
			Node nt = nodes_array[key];
			while (nt.parent!=null){
				jip(nt);
				if (nt.parent.value<nt.value){
					swap(nt, nt.parent);
					// int tem = nt.parent.value, tem2 = nt.parent.key;
					// nt.parent.value = nt.value;
					// nt.parent.key = nt.key;
					// nt.value = tem;
					// nt.key = tem2;
					// nt = nt.parent;
				} else {
					nt = nt.parent;
					break;
					// jip(nt);
				}
			}
			jip(nt);
		} else {
			nodes_array[key].value+=diffvalue;
			Node nt = nodes_array[key];
			// jip(nt);
			while (nt.height!=1){
				int l = 0;
				if (nt.left==null){
					break;
				} else if (nt.right==null){
					l=1;
				} else if (nt.left.value>nt.right.value){
					l = 1;
				} else {
					l = 0;
				}
				if (l==1 && nt.left.value>nt.value){
					swap2(nt.left, nt);
					// int tem = nt.parent.value, tem2 = nt.parent.key;
					// nt.parent.value = nt.value;
					// nt.parent.key = nt.key;
					// nt.value = tem;
					// nt.key = tem2;
					// nt = nt.parent;
				} else if (l==0 && nt.right.value>nt.value){
					// nt = nt.parent;
					// jip(nt);
					swap2(nt.right, nt);
				} else {
					break;
				}
				jip(nt);
			}
		}
	}

	public int getMaxValue() throws Exception{
		// System.out.println(" gemavl");
		/* 
		   1. Returns maximum value in the heap.
		   2. If heap is empty, throw NullRootException.

		   Expected Time Complexity : O(1).
		*/

		// To be filled in by the student
		if (root == null){
			throw new Exception(NullRootException);
		}

		return root.value;
	}

	
	public ArrayList<Integer> getKeys() throws Exception{

		/*
		   1. Returns keys of the nodes stored in heap.
		   2. If heap is empty, throw NullRootException.
		 
		   Expected Time Complexity : O(n).
		*/

		ArrayList<Integer> keys = new ArrayList<Integer>();   // Stores keys of nodes in heap

		// To be filled in by the student
		if (root == null){
			throw new Exception(NullRootException);
		}
		getk(keys, root);

		return keys;
	}

	// Write helper functions(if any) here (They have to be private).
	
	private void get(ArrayList<Integer> arr, Node cur){
		if (cur.value == root.value){
			arr.add(cur.key);
			get(arr, cur.left);
			get(arr, cur.right);
		}
	}
	
	private void getk(ArrayList<Integer> arr, Node cur){
		if (cur!=null){
			arr.add(cur.key);
			// System.out.print(cur.key+" o ");
			// if (cur!=root){
			// 	System.out.println(cur.parent.key+" ");
			// }
			if (cur.left!=null && cur.left.value>cur.value){
				System.out.println("false  "+cur.value);
			}
			if (cur.right!=null && cur.right.value>cur.value){
				System.out.println("false  "+cur.value);
			}
			getk(arr, cur.left);
			getk(arr, cur.right);
		}
	}
	
	private int max(int a, int b){
		return (a>b?a:b);
	}

	private void jip(Node nt){
		if (nt.left==null){
			nt.height = 1;
			nt.is_complete = true;
			return;
		} else if (nt.right==null){
			nt.height = nt.left.height+1;
			nt.is_complete = false;
		} else {
			if (nt.left.height == nt.right.height){
				if (nt.left.is_complete == false || nt.right.is_complete == false){
					nt.is_complete = false;
				} else {
					nt.is_complete = true;
				}
			} else {
				nt.is_complete = false;
			}
			nt.height = max(nt.left.height, nt.right.height)+1;
		}
	}

	private void perc(int[] keys, int[] values, int i){
		if ((2*(i+1))>=values.length){
			return;
		} else if ((2*(i+1)==values.length-1)){
			if (values[2*(i+1)]>values[i]){
				int tem1 = values[i], tem2 = keys[i];
				values[i] = values[2*(i+1)];
				keys[i] = keys[2*(i+1)];
				values[2*(i+1)]=tem1;
				keys[2*(i+1)]=tem2;
			}
		} else {
			int larger = i;
			if (values[2*(i+1)-1]>values[larger]){
				larger = 2*(i+1)-1;
			}
			if (values[2*(i+1)]>values[larger]){
				larger = 2*(i+1);
			}
			if (larger!=i){
				int tem1 = values[i], tem2 = keys[i];
				values[i] = values[larger];
				keys[i] = keys[larger];
				values[larger]=tem1;
				keys[larger]=tem2;
				perc(keys, values, larger);
			}
		}
	}

	private void swap(Node cur, Node par){
		if (par==null){
			return;
		} else if (cur==null){
			return;
		} else {
			// System.out.println(cur.value+" "+par.value);
			// System.out.println(cur.parent.value+" "+par.parent.value);
			Node n1 = cur.left, n2 = cur.right;
			if (par.left==cur){
				cur.left = par;
				cur.right = par.right;
				if (cur.right!=null){
					cur.right.parent = cur;
				}
			} else {
				cur.right = par;
				cur.left = par.left;
				if (cur.left!=null){
					cur.left.parent = cur;
				}
			}
			cur.parent = par.parent;
			if (par.parent!=null && par.parent.left==par){
				// System.out.println(par.parent.key + " " + cur.key + "par "+par.key);
				par.parent.left = cur;
			} else if (par.parent!=null) {
				// System.out.println(par.parent.key + " " + cur.key + "par "+par.key);
				par.parent.right = cur;
			} else {
				root = cur;
			}
			par.parent = cur;
			par.left = n1;
			par.right = n2;
			jip(par);
		}
	}

	private void swap2(Node cur, Node par){
		if (par==null){
			return;
		} else if (cur==null){
			return;
		} else {
			// System.out.println(cur.value+" "+par.value);
			// System.out.println(cur.parent.value+" "+par.parent.value);
			Node n1 = cur.left, n2 = cur.right;
			if (par.left==cur){
				cur.left = par;
				cur.right = par.right;
				if (cur.right!=null){
					cur.right.parent = cur;
				}
			} else {
				cur.right = par;
				cur.left = par.left;
				if (cur.left!=null){
					cur.left.parent = cur;
				}
			}
			cur.parent = par.parent;
			if (par.parent!=null && par.parent.left==par){
				// System.out.println(par.parent.key + " " + cur.key + "par "+par.key);
				par.parent.left = cur;
			} else if (par.parent!=null) {
				// System.out.println(par.parent.key + " " + cur.key + "par "+par.key);
				par.parent.right = cur;
			} else {
				root = cur;
			}
			par.parent = cur;
			par.left = n1;
			par.right = n2;
			jip(par);
			jip(cur);
		}
	}

	private void drt(Node cur){
		if (root==null){
			return;
		}
		if (cur.left==null){
			// System.out.println(cur.value);
			if (cur==root){
				root = null;
				return;
			}
			nodes_array[root.key] = null;
			Node hu = cur.parent;
			if (hu!=null){
				if (hu.left==cur){
					hu.left = null;
				} else {
					hu.right = null;
				}
			}
			cur.parent = root.parent;
			cur.left = root.left;
			cur.right = root.right;
			cur.is_complete = root.is_complete;
			cur.height = root.height;
			this.root = cur;
			return;
		} else {
			if (cur.right == null){
				drt(cur.left);
			} else {
				{
					if (cur.left.is_complete == false){
						drt(cur.left);
					} else if (cur.right.is_complete==false){
						drt(cur.right);
					} else if (cur.left.height==cur.right.height){
						drt(cur.left);
					} else {
						drt(cur.right);
					}
				}
			}
		}
		jip(cur);
	}
}
