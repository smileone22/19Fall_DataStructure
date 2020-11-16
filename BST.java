package project6;

import java.util.*;

/**
 * An implementation of a binary search tree. The elements are ordered in natural ordering. 
 * The implementation provides O(h) (h is the height of the tree which could be low as longN for balanced trees,
 * but could be large as N for unbalanced trees)
 * This class implements many of the methods provided by the Java's framework's Treeset Class
 * @author Heewon Kim
 *
 */
public class BST<E extends Comparable<E>> extends Object implements Iterable<E>, Cloneable {
    
	private Node<E>  root;
	private int size; 
	/**
	 * Constructs a new,empty tree, sorted according to the natural ordering of its elements.
	 * All elements inserted into tree must implement the comparable interface
	 * Operation of O(1)
	 */
	public BST() {
		root=null;
		size=0;
	}
	/**
	 * Constructs a new,empty tree containing the elements in the specified collection, sorted according to the natural ordering of its elements.
	 * All elements inserted into tree must implement the comparable interface
	 * Operation of O(Nlog N) where N is the number of elements in the collection.
	 * @param collection - collection whose elements will comprise the new tree
	 * @throws NullPointerException if the specified collection is null.  
	 */
	public BST(E[] collection) {
		
		if (collection==null) throw new NullPointerException();
		
		for (E e: collection){
			this.add(e);
			size++;
		}
	}
	/**
	 * Adds the specified element to this set if it is not present in the set. 
	 * Operation of O(h) where h is height of the tree. 
	 * @param e- element to be added to this tree
	 * @throws NullPointerException if the specified collection is null and this set uses natural ordering, 
	 * or its comparator does not permit null elements. 
	 */
	public boolean add(E element) {
		if (element==null) throw new NullPointerException();
		int oldSize = size;
	    root  =  add ( element, root );
	    if (oldSize == size)
	        return false;
	    else{
	    return true;
	    }
	}
	/**
	 * A helper method that adds the specified element to this set 
	 * @param value- element to be added to this tree
	 * @param root- node root that checks if the value should be added to the root
	 */
	private Node<E> add(E value, Node<E> root) {
	if (root == null) {
		Node<E> n= new Node<E>(value);
		n.height=1;
		size++;
	    return n;
	}
		
	if (value.equals(root.data)) {
	    
	    return root; // should return false 
	
	} else {
	    if (value.compareTo(root.data) < 0) {	// add to left subtree
		root.left = add(value, root.left);
	    } else {	
	    // add to right subtree
		root.right = add(value, root.right);
	    }
	}
	updateHeight(root);
	return root;
	
	}


	/**
	 * A helper method that update the height of the tree.
	 * When we add or remove a node, this method will be used. 
	 * It is to keep the height() method to the O(1).
	 * @param node n- the node that starts checking the height 
	 */
	
	private void updateHeight(Node<E > node){
		if (node.right==null && node.left==null){
			node.height=1;
		}
		else if(node.left==null){
			node.height=node.right.height+1;
		}
		else if (node.right==null){
			node.height=node.left.height+1;
		}
		else
			node.height=Math.max(node.right.height,node.left.height)+1;
	}
	
	/**
	 * Adds all of the elements in the specified collection to this tree.
	 * It works in the operation of O(MH) where M is the size of collection and 
	 * H is the size of the height of the current tree.
	 * @param collection- collection that is getting added to the tree 
	 * @return boolean- true if set is changed as a result of the call, false if not.
	 * @throws NullPointerException - if the collection is null or if any element of the collection is null.
	 * 
	 */
	public boolean addAll (Collection <? extends E> collection){
		if(collection==null || collection.contains(null))
			throw new NullPointerException("wrong collection");
		int tsize=this.size();
		int csize=collection.size();
		for(E e:collection){
			this.add(e);
		}
		if (this.size>tsize+csize)
			return true;
		else
			return false;
	}
	
	/**
	 * Removes the specified element from this tree if it is present.
	 * @param o- object that needs to be removed from the treeset.
	 * @return boolean- true if this set contained the specified element and removed it from the tree, false if not. 
	 * @throw NullPointerException if the specified element is null. 
	 */
	private boolean found ;
	public boolean remove(Object o){
		
		if(o==null){
			throw new NullPointerException("specified element is null");
		}
		if(o.getClass()!=this.root.data.getClass()){
			throw new ClassCastException("cannot be compared");
		}
		found = false;
		root = recRemove(o, root);
		if (found==true)
			size--;
		updateHeight(root);
		return found;
	}
	
	/**
	 * A helper method that finds the element that needs to be deleted 
	 * @param o- element to be deleted from this tree.
	 * @param node- compares o with the data in this node 
	 * @return node- by recursively finds the specified node in the tree 
	 */
	private Node<E> recRemove(Object o, Node<E>  node)
    {
        if (node == null)
            found = false;
        else if (((Comparable<E>) o).compareTo(node.data)<0)
            node.left = recRemove(o, node.left);
        else if (((Comparable<E>) o).compareTo(node.data)>0)
            node.right = recRemove(o, node.right );
        //found the node 
        else {
            node = removeNode(node);
            found = true;
        }
        return node;
    }
	/**
	 * A helper method that actually removes the node from the tree. 
	 * @param node- node that needs to be removed. 
	 * @return node- node that needs to be removed. 
	 */
    private Node<E> removeNode(Node<E> node)
    {
        
    	
        if (node.left == null)
            return node.right ;
        else if (node.right  == null)
            return node.left;
        //case for two children 
        else {
            E data = getPredecessor(node);
            node.data = data;
            node.left = recRemove(data, node.left);
            return node;
        }
    }
    /**
	 * A helper method that finds predecessor to remove the node from the tree. 
	 * @param node- node which is trying to find predecessor. 
	 * @return E-data of the predecessor
	 */
    private E getPredecessor(Node<E> subtree)
    {
        Node<E> temp = subtree;
        while (temp.right  != null)
            temp = temp.right ;
        return temp.data;
    }

	
	
    /**
	 * A method that checks to see if this set contains the specified element.
	 * @param o- object that needs to be checked for containment in this set. 
	 * @return boolean- true if this set contains the element, false if not. 
	 * @throws NullPointerException if the specified element is null and this set uses natural ordering, 
	 * or its comparator does not permit null elements
	 * @throws ClassCastException if the specified object cannot be compared with the elements.
	 * 
	 */
	 public boolean contains(Object o) {
		 if (o==null)
	    	 throw new NullPointerException();
	     if (o.getClass()!=this.root.data.getClass()){
	    	 throw new ClassCastException();
	     }
        if (root == null)
            return false;
        else{
        Node<E> cur = root;

        do {
            E curVal = cur.data;
            if (Objects.equals(o, curVal))  return true;
            if (curVal.compareTo((E) o)>0){
            	cur = cur.left;}
            else {
            	cur = cur.right;
            }
        } while (cur != null) ;}
 	
        return false;
    }
	 /**
		 * Checks to see if this set contains the specified collection. 
		 * It works in the operation of O(MH) where M is the size of collection and 
		 * H is the size of the height of the current tree.	  
		 * @param c- collection to be checked for containment in this tree. 
		 * @return boolean- true if this set contains all elements in the specified collection, false if not. 
		 * @throws NullPointerException if the specified collection contains one or 
		 * more null elements and this collection does not permit null elements, or if
		 * the specified collection is null.
		 *
		 */
	 public boolean containsAll(Collection<?> c){
		
		 if (c.contains(null)|| c==null){
			 throw new NullPointerException("null exception");
		 } 
		 Iterator< ? > iter = c.iterator();
		 while (iter.hasNext())
		    {
		      if (!(this.contains(iter.next())))
		      {
		        return false;
		      }
		    }
		    return true;
	 }

		/**
		 * Compares the specified object with this tree for equality.
		 * Returns true if the given object is also a tree, the two trees have the same size,
		 * and every member of the given tree is contained in this tree.
		 * This operation should be O(N).
		 * @param obj-object to be compared for equality with this tree.
		 * @returns boolean- if the specified object is equal to this tree. 
		 */
	@Override
	public boolean equals(Object obj) {
        if (obj == null || !(obj instanceof BST)) {
            return false;
        }
        BST otherBST = (BST) obj;
        if (this.size!=otherBST.size){
        	return false;
        }
        return root.data.equals(otherBST.root.data)
            && root.left.equals(otherBST.root.left)
            && root.right.equals(otherBST.root.right);
    
	}
	
	 /**
	 * Returns the size of the tree.
	 * Performance of O(1)
	 * @return size the number of element in this tree.
	 */
	public int size(){
		return this.size;
	}
	
	/**
	 * Returns the true if this set contains no elements
	 * Performance of O(1)
	 * @return boolean- true if this set contains no elements, false if not. 
	 */
	public boolean isEmpty(){
		return (size==0);
		
	}
	/**
	 * Returns the element at the specified position in this tree.
	 * The order of the indexed elements is the same as provided by this tree's iterator.
	 * Performance of O(H)
	 * @param index - the index of the element to return. 
	 * @return E- element at the specified position in this tree.
	 * @throws IndexOutOfBoundsException - if the index is out of range. 
	 *  
	 */
	public E get(int index) {
		if (index<0 || index>= size()){
			throw new IndexOutOfBoundsException("Index out of range");
		}
		ArrayList<E> list = new ArrayList<E>();
		inorder(root,list);
		return list.get(index - 1);
		
	}
	
	/**
	 * Returns a collection whose elements range from fromElement to toElement inclusive.
	 * The returned collection/ list is backed by this tree, so changes in returned list
	 * are reflected in this tree, vice versa. 
	 * Performance of O(M)
	 * @param fromElement - the low endpoint(inclusive) of the returned collection.
	 * @param toElement - the high endpoint(inclusive) of the returned collection.
	 * @return arraylist<E> - a collection containing a portion of this tree whose 
	 * elements range from fromElement to Toelement(both inclusive).
	 * @throws NullPointerException - if from element or toElement is null.
	 * @throws IllegalArgumentException- if fromElement is greater than toElement.
	 */
	public ArrayList<E> getRange(E fromElement, E toElement){
		if (fromElement ==null || toElement==null){
			throw new NullPointerException();
		}
		if (fromElement.compareTo(toElement)>0){
			throw new IllegalArgumentException("fromElement bigger than toElement ");
		}
		ArrayList<E> treelist=new ArrayList<>();
		inorder(root,treelist);
		int startindex=treelist.indexOf(fromElement);
		int endindex=treelist.indexOf(toElement);
		ArrayList<E> returnlist = new ArrayList<>();
		for (int i=startindex;i<=endindex;i++){
			returnlist.add(treelist.get(i));
		}
		return returnlist;
	}
	
	/**
	 * Returns the greatest element in this tree less than or equal to the given element,
	 * or null if there is no such element.
	 * Performance of O(H)
	 * @param e- the value to match 
	 * @return the greatest element less than or equal to e, or null if there is no such element. 
	 * @throws NullPointerException - if the specified element is null. 
	 * @throws ClassCastException - if the specified element cannot be compared with elements.
	 *  
	 */
	public E floor(E e) {
        if (e == null) throw new NullPointerException();
        Node<E> x = floor(root, e);
        if (x == null) return null;
        else 
        	return x.data;
    } 
	/**
	 * Helper method for finding floor through recursively calling the method.
	 * 
	 */
    private Node<E> floor(Node<E> n, E e) {
        if (n == null) return null;
        if (e.getClass()!=n.getClass()) 
			throw new ClassCastException();
        int tmp = e.compareTo(n.data);
        if (tmp == 0) return n;
        if (tmp <  0) return floor(n.left, e);
        Node<E> t = floor(n.right, e); 
        if (t != null) return t;
        else return n; 
    } 
    
	/**
	 * Returns the least element in this tree greater than or equal to the given element,
	 * or null if there is no such element.
	 * Performance of O(H)
	 * @param e- the value to match 
	 * @return the least element greater than or equal to e, or null if there is no such element. 
	 * @throws NullPointerException - if the specified element is null. 
	 * @throws ClassCastException - if the specified element cannot be compared with elements.
	 *  
	 */
	 public E ceiling(E e) {
	        if (e == null) throw new NullPointerException();
	        Node<E> x = ceiling(root, e);
	        if (x == null) return null;
	        if (x== root) return null;
	        else return x.data;
	    }
	 /**
		 * Helper method for finding ceiling through recursively calling the method.
		 * 
		 */
	 private Node<E> ceiling(Node<E> n, E e) {
	        if (n == null) return null;
			if (e.getClass()!=n.getClass()) 
				throw new ClassCastException();
	        int tmp = e.compareTo(n.data);
	        if (tmp == 0) return n;
	        //the case where e is smaller than root needs to go up 
	        if (tmp < 0) { 
	            Node<E> t = ceiling(n.left, e); 
	            if (t != null) return t;
	            else return n; 
	        } 
	         
	        return ceiling(n.right, e); 
	    } 
	 
	 /**
		 * Returns the first(lowest) element currently in this tree.
		 * Performance of O(H)
		 * @throws NosuchElementException if this set is empty. 
		 */
	public E first() {
        if (isEmpty()) 
        	throw new NoSuchElementException();
        return first(root);
    } 
	
    private E first(Node<E> n) { 
        if (n.left == null) 
        	return n.data; 
        else 
        	return first(n.left); 
    }
    /**
   	 * Returns the last(highest) element currently in this tree.
   	 * Performance of O(H)
   	 * @throws NosuchElementException if this set is empty. 
   	 */
    public E last() {
        if (isEmpty()) 
        	throw new NoSuchElementException();
        return last(root);
    } 

    private E last(Node<E> n) { 
        if (n.right == null) 
        	return n.data; 
        else 
        	return last(n.right); 
    }
    /**
	 * Removes all of the elements from this set. 
	 * Performance of O(1)
	 */
	public void clear(){
		root = null;
		size=0;
	}
	/**
	 * Returns the height of the tree where the height of a leaf is 1.
	 * Performance of O(1)
	 * @return height- the height of the tree is the height of its root node.
	 */
	public int height()
	   {
	      return root.height;
	   }
	   /**
		 * Returns the greatest element in this tree less the given element,
		 * or null if there is no such element.
		 * Performance of O(H)
		 * @param e- the value to match 
		 * @return the greatest element less than e, or null if there is no such element. 
		 * @throws NullPointerException - if the specified element is null. 
		 * @throws ClassCastException - if the specified element cannot be compared with elements.
		 *  
		 */
	public E lower(E e){
		if(e==null){
			throw new NullPointerException("wrong element");
		}
		if(e.getClass()!=this.root.getClass()){
			throw new ClassCastException("Cannot be compared");
		}
		Node<E> x = lower(root, e);
		if (x==null) return null;
		if (x==root) return null;
		else
			return x.data;
	}
    private Node<E> lower(Node<E> n, E e) {
        if (n == null) return null;
        int tmp = e.compareTo(n.data);
        if (tmp <  0) return lower(n.left, e);
        Node<E> t = lower(n.right, e); 
        if (t != null) return t;
        else return n; 
    } 
    /**
	 * Returns the least element in this tree greater the given element,
	 * or null if there is no such element.
	 * Performance of O(H)
	 * @param e- the value to match 
	 * @return the least element greater than e, or null if there is no such element. 
	 * @throws NullPointerException - if the specified element is null. 
	 * @throws ClassCastException - if the specified element cannot be compared with elements.
	 *  
	 */
	
	public E higher(E e){
		if(e==null){
			throw new NullPointerException("wrong element");
		}
		if(e.getClass()!=this.root.getClass()){
			throw new ClassCastException("Cannot be compared");
		}
		Node<E> x = higher(root, e);
		if (x==null) return null;
		if (x==root) return null;
		else
			return x.data;
		
	}
	 private Node<E> higher(Node<E> n, E e) {
	        if (n == null) return null;
	        int tmp = e.compareTo(n.data);
	        if (tmp == 0) return n;
	        if (tmp < 0) { 
	            Node<E> t = higher(n.left, e); 
	            if (t != null) return t;
	            else return n; 
	        } 
	        return higher(n.right, e);
	    } 
	 /**
		 * Returns a shallow copy of this tree instance(nodes copied)
		 * Performance of O(N)
		 * @return a shallow copy of this tree
		 * @throws CloneNotSupportedException 
		 */
	public BST<E> clone() throws CloneNotSupportedException {
		try {
			BST<E> result = (BST<E>) super.clone();
			result.root = deepCopy(this.root);
			return result;
		} catch (CloneNotSupportedException e) {
			throw new InternalError(e.toString());
		}
		
	}
	
	private Node<E> deepCopy(Node<E> node) {
		return node == null ? null : new Node<E>(node.data,
				deepCopy(node.left), deepCopy(node.right));
	}
	
	private void preOrderPrint(Node<E> tree, int level, StringBuilder output) {
        if (tree != null) {
            String spaces = "\n";
            if (level > 0) {
                for (int i = 0; i < level - 1; i++)
                    spaces += "   ";
                spaces += "|--";
            }
            output.append(spaces);
            output.append(tree.data);
            preOrderPrint(tree.left, level + 1, output);
            preOrderPrint(tree.right, level + 1, output);
        }
        
        
        // uncomment the part below to show "null children" in the output
        else {
            String spaces = "\n";
            if (level > 0) {
                for (int i = 0; i < level - 1; i++)
                    spaces += "   ";
                spaces += "|--";
            }
            output.append(spaces);
            output.append("null");
        }
    }
	
	/**
	 * Produces tree like string representation of this tree.
	 * Returns a string representation of this tree in a tree-like format.
	 * @author Joanna Klukowska
	 */
	 public String toStringTreeFormat() {
	
	        StringBuilder s = new StringBuilder();
	
	        preOrderPrint(root, 0, s);
	        return s.toString();
	    }
	 
	 public String toString(){
		 Iterator<E> iter=this.iterator();
		 String str="[";
		 while (iter.hasNext()){
			 str+=String.valueOf(iter.next())+", ";
		 }
		 str=str.substring(0, str.length()-1);
		 str+="]";
		 return str;
	 }
		/**
		 * Returns an array containing all the elments returned by this tree's iterator, in the 
		 * same order, stored in consecutive elements of array, starting with index 0.
		 * The length of the returned array is equal to the number of elements returned by
		 * the iterator. 
		 * Performance of O(N)
		 * @return Object array that keeps tree elements
		 */
	public Object[] toArray(){
		Object[] arr = new Object[size()];
		int i=0;
		Iterator<E> it = this.iterator();
		while(it.hasNext()){
			arr[i]=it.next();
			i++;
		}
		return arr;
	}
	/**
	 * Nested class that provides nodes for the bst. 
	 * It keeps height of the node, E type data, link to the left and right nodes.
	 * The height of the node is initialized to update the height of the tree 
	 */
	private static class Node<E>  {
		int height;
        E  data;
        Node<E>  left ;
        Node<E>  right ;

        Node(E element) {
            this.data = element;
          }
        Node(E element, Node<E> l, Node<E> r) {
            this.data=element;
            left = l;
            right = r;
          }

    }
	
	
	public void inorder(Node<E> root,ArrayList<E> l ){
		if (root!=null){
		inorder(root.left,l);
		l.add(root.data);
		inorder(root.right,l);}
	}
	/**
	 * Returns an iterator over the elements in this tree in postorder traversal.
	 * Performance of O(N)
	 * @return postorder iterator- an iterator over the elements in this set in postorder traversal. 
	 */
	public void preorder(Node<E> root,ArrayList<E> l ){
		if (root!=null){
		l.add(root.data);
		preorder(root.left,l);
		preorder(root.right,l);
		}
	}
	/**
	 * Returns an iterator over the elements in this tree in postorder traversal.
	 * Performance of O(N)
	 * @return postorder iterator- an iterator over the elements in this set in postorder traversal. 
	 */
	public void postorder(Node<E> root,ArrayList<E> l ){
		if (root!=null){
		postorder(root.left,l);
		postorder(root.right,l);
		l.add(root.data);
	}
	}
	/**
	 * Returns an iterator over the elements in this tree in preorder traversal.
	 * Performance of O(N)
	 * @return preorder iterator- an iterator over the elements in this set in preorder traversal. 
	 */
	public Iterator<E> iterator() {
		ArrayList<E> l = new ArrayList<>();
		// add in order to array list 
		inorder(root,l);
		return new itr(l);
	}
	
	public Iterator<E> preorderIterator(){
		ArrayList<E> l = new ArrayList<>();
		// add in order to array list 
		preorder(root,l);
		return new itr(l);
	}
	public Iterator<E> postorderIterator(){
		ArrayList<E> l = new ArrayList<>();
		// add in order to array list 
		postorder(root,l);
		return new itr(l);
	}

	/**
	 * 
	 * Private iterator class that implements Iterator<E> interface
	 * Used to create iterator, preorder iterator, and postorder iterator
	 *
	 */
	private class itr implements Iterator<E>{
		ArrayList<E> list;
		private int index;
		
		public itr(ArrayList<E> list){
			this.list=list;
			this.index=-1;
		}
		
		public boolean hasNext(){
		if( index<0|| index>= size()-1)
			return false; 
		return true;
		}
		
		public E next(){
			index++;
			return list.get(index);
		}
		public void remove() throws UnsupportedOperationException{
			
		}
	}
	
	
}

