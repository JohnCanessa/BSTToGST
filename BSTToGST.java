import java.util.Arrays;
import java.util.LinkedList;
import java.util.List;
import java.util.Queue;
import java.util.Scanner;


 /**
 * Class for a binary tree node.
 */
class TreeNode {
   
    // **** class members ****
    int         val;
    TreeNode    left;
    TreeNode    right;
   
    // **** constructor(s) ****
    TreeNode() {}
   
    TreeNode(int val) {
        this.val = val;
    }
   
    TreeNode(int val, TreeNode left, TreeNode right) {
        this.val    = val;
        this.left   = left;
        this.right  = right;
    }
 
    // **** ****
    @Override
    public String toString() {
        return "" + this.val;
    }
}


/**
 * 1038. Binary Search Tree to Greater Sum Tree
 * https://leetcode.com/problems/binary-search-tree-to-greater-sum-tree/
 */
public class BSTToGST {

    /**
     * Enumerate which child in the node at the head of the queue 
     * (see populateTree function) should be updated.
     * !!! PART OF TEST SCAFFOLDING!!!
     */
    enum Child {
        LEFT,
        RIGHT
    }
    
    
    // **** child turn to insert on node at head of queue ****
    static Child  insertChild = Child.LEFT;


    // **** sum of nodes ****
    static int sum = 0;


    /**
     * Recursive call.
     */
    static void dfs(TreeNode root) {

        // **** base case ****
        if (root == null)
            return;

        // **** traverse right subtree ****
        dfs(root.right);
           
        // **** update node value ****
        root.val += sum;

        // **** update sum ****
        sum = root.val;

        // **** traverse left subtree ****
        dfs(root.left);
    }


    /**
     * Entry call.
     * 
     * Runtime: 0 ms, faster than 100.00% of Java online submissions.
     * Memory Usage: 36.4 MB, less than 8.89% of Java online submissions.
     */
    static TreeNode bstToGst(TreeNode root) {

        // **** initialize sum ****
        sum = 0;

        // **** dfs ****
        dfs(root);

        // **** ****
        return root;
    }


    /**
     * Generate the number of nodes in the BT at the specified level.
     * !!! PART OF TEST SCAFFOLDING!!!
     */
    static int nodesInLevel(int level) {
        if (level < 1)
            return 0;
        else
            return (int)Math.pow(2.0, level - 1);
    }


    /**
     * Populate the specified level in the specified binary tree.
     * !!! PART OF TEST SCAFFOLDING!!!
     */
    static TreeNode populateBTLevel(TreeNode root, int level, String[] subArr, Queue<TreeNode> q) {
    
        // **** populate binary tree root (if needed) ****
        if (root == null) {
            root = new TreeNode(Integer.parseInt(subArr[0]));
            q.add(root);
            return root;
        }
    
        // **** ****
        TreeNode child = null;
    
        // **** traverse the sub array of node values ****
        for (int i = 0; (i < subArr.length) && (subArr[i] != null); i++) {
    
            // **** child node ****
            child = null;
    
            // **** create and attach child node (if needed) ****
            if (!subArr[i].equals("null"))
                child = new TreeNode(Integer.parseInt(subArr[i]));
    
            // **** add child to the queue ****
            q.add(child);
    
            // **** attach child node (if NOT null) ****
            if (child != null) {
                if (insertChild == Child.LEFT)
                    q.peek().left = child;
                else
                    q.peek().right = child;
            }
    
            // **** remove node from the queue (if needed) ****
            if (insertChild == Child.RIGHT) {    
                q.remove();
            }
    
            // **** toggle insert for next child ****
            insertChild = (insertChild == Child.LEFT) ? Child.RIGHT : Child.LEFT;
        }
    
        // **** return root of binary tree ****
        return root;
    }


    /**
     * Populate binary tree using the specified array of integer and null values.
     * !!! PART OF TEST SCAFFOLDING!!!
     */
    static TreeNode populateBT(String[] arr) {
    
        // **** auxiliary queue ****
        Queue<TreeNode> q = new LinkedList<TreeNode>();
    
        // **** root for the binary tree ****
        TreeNode root = null;

        // **** start with the left child ****
        insertChild = Child.LEFT;
    
        // **** begin and end of substring to process ****
        int b   = 0;
        int e   = 0;
    
        // **** loop once per binary tree level ****
        for (int level = 1; b < arr.length; level++) {
    
            // **** count of nodes at this level ****
            int count = nodesInLevel(level);
    
            // **** compute e ****
            e = b + count;
    
            // **** generate sub array of strings ****
            String[] subArr = Arrays.copyOfRange(arr, b, e);
    
            // **** populate the specified level in the binary tree ****
            root = populateBTLevel(root, level, subArr, q);
    
            // **** update b ****
            b = e;
        }
    
        // **** return populated binary tree ****
        return root;
    }


    /**
    * This method implements a breadth-first search traversal of a binary tree.
    * This method is iterative.
    * It displays all nodes at each level on a separate line.
    * !!! PART OF TEST SCAFFOLDING!!!
    */
    static String bfsTraversal(TreeNode root) {
    
        // **** to generate string ****
        StringBuilder sb = new StringBuilder();
    
        // **** define the current and next queues ****
        List<TreeNode> currentQ = new LinkedList<TreeNode>();
        List<TreeNode> nextQ    = new LinkedList<TreeNode>();
    
        // **** add the root node to the current queue ****
        currentQ.add(root);
    
        // ***** ****
        boolean allNulls = false;

        // **** loop while the current queue has entries ****
        while (!currentQ.isEmpty()) {

            // **** check if the queue only contains null nodes ****
            if (allNulls)
                break;

            // **** remove the next node from the current queue ****
            TreeNode n = currentQ.remove(0);
    
            // **** display the node value ****
            if (n != null)
                sb.append(n.toString() + " ");
            else
                sb.append("null ");
    
            // **** add left and right children to the next queue ****
            if (n != null) {
                if (n.left != null)
                    nextQ.add(n.left);
                else
                    nextQ.add(null);
    
                if (n.right != null)
                    nextQ.add(n.right);
                else
                    nextQ.add(null);
            }
    
            // **** check if the current queue is empty (reached end of level) ****
            if (currentQ.isEmpty()) {
                
                // **** end of current level ****
                sb.append("\n");
    
                // **** check if we have all null nodes in the next queue ****
                allNulls = true;
                for (TreeNode t : nextQ) {
                    if (t != null) {
                        allNulls = false;
                        break;
                    }
                }
    
                // **** point the current to the next queue ****
                currentQ = nextQ;
    
                // **** clear the next queue ****
                nextQ = new LinkedList<TreeNode>();
    
                // **** clear the current queue (all null nodes) ****
                if (allNulls)
                    currentQ = new LinkedList<TreeNode>();
            }
        }
    
        // **** return a string representation of the BT ****
        return sb.toString();
    }


    /**
     * Perform post-order tree traversal.
     * This is a recursive function.
     * !!!! NOT PART OF SOLUTION !!!
     */
    public static void postOrder(TreeNode root) {
    
        // **** check if we are done ****
        if (root == null)
            return;
    
        // **** visit the left child ****
        postOrder(root.left);
    
        // **** visit the right child ****
        postOrder(root.right);
    
        // **** display the data in this node ****
        System.out.print(root.val + " ");
    }


    /**
     * Perform pre-order tree traversal.
     * This is a recursive function.
     * !!!! NOT PART OF SOLUTION !!!
     */
    public static void preOrder(TreeNode root) {
    
        // **** check if we are done ****
        if (root == null)
            return;
    
        // **** display the data in this node ****
        System.out.print(root.val + " ");
    
        // **** traverse the left child ****
        preOrder(root.left);
    
        // **** traverse the right child ****
        preOrder(root.right);
    }


    /**
     * Perform in-order tree traversal.
     * This is a recursive function.
     * !!!! NOT PART OF SOLUTION !!!
     */
    public static void inOrder(TreeNode root) {
    
        // **** check if we are done ****
        if (root == null)
            return;

        // *** visit the left child ****
        inOrder(root.left);

        // **** display the data in this node ****
        System.out.print(root.val + " ");

        // **** visit the right child ****
        inOrder(root.right);
    }


    /**
     * Perform reverse in-order tree traversal.
     * This is a recursive function.
     * !!!! NOT PART OF SOLUTION !!!
     */
    public static void reverseInOrder(TreeNode root) {
    
        // **** check if we are done ****
        if (root == null)
            return;

        // **** visit the right child ****
        reverseInOrder(root.right);

        // **** display the data in this node ****
        System.out.print(root.val + " ");

        // *** visit the left child ****
        reverseInOrder(root.left);
    }


    /**
     * Test scaffolding.
     * !!!! NOT PART OF SOLUTION !!!
     */
    public static void main(String[] args) {
        
        // **** open scanner ****
        Scanner sc = new Scanner(System.in);

        // **** read data for the BST ****
        String[] arr = sc.nextLine().trim().split(",");

        // **** close scanner ****
        sc.close();

        // **** populate the BST ****
        TreeNode root = populateBT(arr);

        // **** display the binary tree (sanity check) ****
        System.out.println("main <<< root:");
        System.out.print(bfsTraversal(root));

        // **** ****
        System.out.print("main <<<      postOrder: ");
        postOrder(root);
        System.out.println();

        // **** ****
        System.out.print("main <<<       preOrder: ");
        preOrder(root);
        System.out.println();

        // **** ****
        System.out.print("main <<<        inOrder: ");
        inOrder(root);
        System.out.println();

        // **** ****
        System.out.print("main <<< reverseInOrder: ");
        reverseInOrder(root);
        System.out.println();

        // **** generate the GST tree ****
        TreeNode gst = bstToGst(root);

        // **** display the GST tree ****
        System.out.println("main <<< Output: ");
        System.out.print(bfsTraversal(gst));
    }
}