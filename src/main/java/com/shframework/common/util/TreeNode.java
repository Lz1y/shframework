package com.shframework.common.util;

import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;

/**
 * 
 * @author OneBoA
 *
 * @param <T>
 */
public class TreeNode<T> implements Iterable<TreeNode<T>> {

	T data;
    TreeNode<T> parent;
    List<TreeNode<T>> children;
    
    public TreeNode(T data) {
        this.data = data;
        this.children = new LinkedList<TreeNode<T>>();
    }

    public TreeNode<T> addChild(T child) {
        TreeNode<T> childNode = new TreeNode<T>(child);
        childNode.parent = this;
        this.children.add(childNode);
        return childNode;
    }
    
	@Override
	public Iterator<TreeNode<T>> iterator() {
		return null;
	}

	public T getData() {
		return data;
	}

	public TreeNode<T> getParent() {
		return parent;
	}

	public List<TreeNode<T>> getChildren() {
		return children;
	}

	public void setData(T data) {
		this.data = data;
	}

	public void setParent(TreeNode<T> parent) {
		this.parent = parent;
	}

	public void setChildren(List<TreeNode<T>> children) {
		this.children = children;
	}

}