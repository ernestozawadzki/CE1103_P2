package main.java;

public class StackNode {

    TreeNode treeNode;
    StackNode next;

    public StackNode(TreeNode treeNode) {

        this.treeNode = treeNode;
        next = null;

    }
}