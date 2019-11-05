/**
 * @author Charun Upara
 * @author Govind Brahmanyapura
 * @version 1.0
 */

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;
import java.util.Scanner;

public class Main {
    public static void main(String[] args) throws Exception {

        /**
         * Initial seed for LFSR()
         */
        int seed = 221;

        /**
         * List of integer that will be used to create red-black tree (will be passed to LFSR() to fill value)
         */
        ArrayList<Integer> nums = new ArrayList<Integer>(10);

        /**
         * A list to keep track of new values being added to the tree
         */
        ArrayList<Integer> allVals = new ArrayList<>();

        //Generate 10 random numbers using LFRS which will be stored in the previously create array (nums)
        LFSR(seed, nums);

        //Initialize tree
        RBT tree = new RBT();

        //Populate tree with values generate by LFSR()
        for(int i = 0; i < 10; i++) {
            insertRBT(tree, nums.get(i), allVals);
        }


        levelOrderPrint(tree.root);

        //User control
        boolean control = true;
        while(control) {
            Scanner scanner = new Scanner(System.in);
            String input = scanner.nextLine();

            String[] inputArr = new String[10];
            inputArr = input.split(" ");
            if(inputArr[0].equals("ADD")) {
                insertRBT(tree, Integer.parseInt(inputArr[1]), nums);
                levelOrderPrint(tree.root);
            }
            else if(inputArr[0].equals("DEL")) {
                deleteRBT(tree, Integer.parseInt(inputArr[1]));
                levelOrderPrint(tree.root);
            }
            else if (inputArr[0].equals("BLKH")) {
                System.out.println(getBlackHeight(tree,Integer.parseInt(inputArr[1])));
            }
            else if(inputArr[0].equals("Quit")) {
                control = false;
            }
            else {
                System.out.println("Invalid Command");
            }
        }



    }

    /**
     * Insert a new node where node.val = val into red-black tree
     * @param tree the tree that the new node will be inserted into
     * @param val value of the node to be inserted into the tree
     * @param nums list of integers
     */
    public static void insertRBT(RBT tree, int val, List<Integer> nums) throws Exception {
        if(nums.contains(val)) {
            throw new Exception("Duplicate value");
        }

        TreeNode newNode = new TreeNode(val);

        TreeNode y = tree.NIL;
        TreeNode x = tree.root;
        while(x != tree.NIL) {
            y = x;
            if (newNode.val < x.val) {
                x = x.left;
            }
            else {
                x = x.right;
            }
        }
        newNode.parent = y;

        if(y == tree.NIL) {
            tree.root = newNode;
        }
        else if (newNode.val < y.val) {
            y.left = newNode;
        }
        else {
            y.right = newNode;
        }
        newNode.left = tree.NIL;
        newNode.right = tree.NIL;
        newNode.color = 'r';
        insertRBTFixUp(tree, newNode);
        nums.add(nums.size(), val);

    }

    /**
     * Rotates the node to the left by reassigning pointers
     * Since we are always reassigning the same pointers regardless of tbe size of tree,
     * this method runs in O(1) time.
     * @param tree the red-black tree containing the node to be left-rotated
     * @param x the node to be left-rotated
     */
    public static void leftRotate(RBT tree, TreeNode x) {
        TreeNode y = x.right;
        x.right = y.left;
        if(y.left != tree.NIL) {
            y.left.parent = x;
        }
        y.parent = x.parent;
        if(x.parent == tree.NIL) {
            tree.root = y;
        }
        else if (x == x.parent.left) {
            x.parent.left = y;
        }
        else {
            x.parent.right = y;
        }
        y.left = x;
        x.parent = y;
    }

    /**
     * Rotate the node to the right by reassigning pointers
     * Since we are always reassigning the same pointers regardless of tbe size of tree,
     * this method runs in O(1) time.
     * @param tree the red-black tree containing the node to be right-rotated
     * @param node the node to be right-rotated
     */
    public static void rightRotate(RBT tree, TreeNode node) {
        TreeNode y = node.left;
        node.left = y.right;
        if(y.right != tree.NIL) {
            y.right.parent = node;
        }
        y.parent = node.parent;
        if(node.parent == tree.NIL) {
            tree.root = y;
        }
        else if (node == node.parent.right) {
            node.parent.right = y;
        }
        else {
            node.parent.left = y;
        }
        y.right = node;
        node.parent = y;

    }

    /**
     * Recolor nodes to make sure that all five properties of red-black tree holds
     *
     * @param tree the tree that the new node is inserted into
     * @param node the node that is insserted into the tree
     */
    public static void insertRBTFixUp(RBT tree, TreeNode node) {
        //Case I: tree has one element
        if(tree.size == 1) {
            tree.root.color = 'b';
        }

        while(node.parent != tree.NIL && node.parent.color == 'r') {
            //Uncle is the right child of grandparent of node
            if(node.parent == node.parent.parent.left) {
                TreeNode uncle = node.parent.parent.right;

                //Case II: Both node and node.parent color are red
                if(uncle.color == 'r') {
                    node.parent.color ='b';
                    node.parent.parent.right.color = 'b';
                    node.parent.parent.color = 'r';
                    node = node.parent.parent;
                }
                else {
                    if(node == node.parent.right) {
                        node = node.parent;
                        leftRotate(tree, node);
                    }
                    else {
                        node.parent.color = 'b';
                        node.parent.parent.color = 'r';
                        rightRotate(tree, node.parent.parent);
                    }
                }
            }

            //Uncle is the left child of the grandparent of node
            else {
                TreeNode uncle = node.parent.parent.left;

                //Case II: Both node and node.parent color are red
                if(uncle.color == 'r') {
                    node.parent.color ='b';
                    node.parent.parent.left.color = 'b';
                    node.parent.parent.color = 'r';
                    node = node.parent.parent;
                }
                else {
                    if(node == node.parent.left) {
                        node = node.parent;
                        rightRotate(tree, node);
                    }
                    else {
                        node.parent.color = 'b';
                        node.parent.parent.color = 'r';
                        leftRotate(tree, node.parent.parent);
                    }

                }
            }
        }
        tree.root.color = 'b';

    }

    /**
     *
     * @param tree
     * @param u
     * @param v
     */
    public static void transplantRBT(RBT tree, TreeNode u, TreeNode v) {
        if(u.parent == tree.NIL) {
            tree.root = v;
        }
        else if (u == u.parent.left) {
            u.parent.left = v;
        }
        else {
            u.parent.right = v;
        }

        v.parent = u.parent;
    }

    /**
     *
     * @param tree
     * @param root
     * @param val
     * @return
     * @throws Exception
     */
    public static TreeNode search(RBT tree, TreeNode root, int val) throws Exception {

        while(root != tree.NIL) {
            if (val > root.val) {
                root = root.right;
            }
            else if (val < root.val) {
                root = root.left;
            }
            else {
                return root;
            }
        }
        throw new Exception("Node not in tree");
    }

    /**
     *
     * @param tree
     * @param val
     * @throws Exception
     */
    public static void deleteRBT(RBT tree, int val) throws Exception {
        TreeNode z;

        z = search(tree, tree.root, val);

        TreeNode y = z;
        char yOGColor = y.color;
        TreeNode x;

        if(z.left == tree.NIL) {
            x = z.right;
            transplantRBT(tree, z, z.right);
        }
        else if(z.right == tree.NIL) {
            x = z.left;
            transplantRBT(tree, z, z.left);
        }
        else {
            y = treeMinimum(z.right);
            yOGColor = y.color;
            x = y.right;

            if(y.parent == z) {
                x.parent = y;
            }
            else {
                transplantRBT(tree, y, y.right);
                y.right = z.right;
                y.right.parent = y;
            }
            transplantRBT(tree, z, y);
            y.left = z.left;
            y.left.parent = y;
            y.color = z.color;
        }
        if(yOGColor == 'b') {
            deleteRBTFixUp(tree, x);
        }
    }

    /**
     *
     * @param node
     * @return
     */
    public static TreeNode treeMinimum(TreeNode node) {
        while(node.left != null) {
            node = node.left;
        }
        return node;
    }

    /**
     *
     * @param tree
     * @param x
     */
    public static void deleteRBTFixUp(RBT tree, TreeNode x) {
        while(x != tree.root && x.color == 'b') {
            if(x == x.parent.left) {
                TreeNode w = x.parent.right;
                if(w.color == 'r') {
                    w.color = 'b';
                    x.parent.color ='r';
                    leftRotate(tree, x.parent);
                    w = x.parent.right;
                }
                if(w.left.color == 'b' && w.right.color == 'b') {
                    w.color = 'r';
                    x = x.parent;
                }
                else {
                    if (w.right.color == 'b') {
                        w.left.color = 'b';
                        w.color = 'r';
                        rightRotate(tree, w);
                        w = x.parent.right;
                    }
                    w.color = x.parent.color;
                    x.parent.color = 'b';
                    w.right.color = 'b';
                    leftRotate(tree, x.parent);
                    x = tree.root;
                }
            }
            else {
                TreeNode w = x.parent.left;
                if(w.color == 'r') {
                    w.color = 'b';
                    x.parent.color ='r';
                    rightRotate(tree, x.parent);
                    w = x.parent.left;
                }
                if(w.right.color == 'b' && w.left.color == 'b') {
                    w.color = 'r';
                    x = x.parent;
                }
                else {
                    if (w.left.color == 'b') {
                        w.right.color = 'b';
                        w.color = 'r';
                        leftRotate(tree, w);
                        w = x.parent.left;
                    }
                    w.color = x.parent.color;
                    x.parent.color = 'b';
                    w.left.color = 'b';
                    rightRotate(tree, x.parent);
                    x = tree.root;
                }
            }
        }
        x.color = 'b';
    }

    /**
     *
     * @param tree
     * @param val
     * @return
     * @throws Exception
     */
    public static int getBlackHeight(RBT tree, int val) throws Exception {
        TreeNode node = search(tree, tree.root, val);
        return bhHelper(tree, node);

    }

    /**
     *
     * @param tree
     * @param node
     * @return
     * @throws Exception
     */
    public static int bhHelper(RBT tree, TreeNode node) throws Exception {
        if(node == tree.NIL) {
            return 1;
        }
        int leftHeight = bhHelper(tree, node.left);

        if(leftHeight == 0) {
            return leftHeight;
        }
        int rightHeight = bhHelper(tree, node.right);
        if(rightHeight == 0) {
            return rightHeight;
        }
        if(leftHeight != rightHeight) {
            return 0;
        }
        else {
            if(node.color == 'b') {
                return leftHeight + 1;
            }
            else {
                return leftHeight;
            }

        }
    }

    /**
     * Generate 10 random numbers to be used to create tree
     * @param seed the initial seed that is parsed from args
     * @param nums empty array of 10 integers that will be filled by this method and used to create the tree
     *
     */
    public static void LFSR(int seed, List<Integer> nums) {
        nums.add(0, seed);
        String binaryInput = String.format("%8s", Integer.toBinaryString(seed));
        binaryInput = binaryInput.replace(' ', '0');

        for (int i = 1; i < 10; i++) {
            //Get the appropriate bits to calculate the new bit
            int[] binaryDigits = new int[4];
            binaryDigits[0] = (int) binaryInput.charAt(2) - '0';
            binaryDigits[1] = (int) binaryInput.charAt(4) - '0';
            binaryDigits[2] = (int) binaryInput.charAt(5) - '0';
            binaryDigits[3] = (int) binaryInput.charAt(7) - '0';

            int newBit = (binaryDigits[0] ^ ((binaryDigits[1] ^ (binaryDigits[2] ^ binaryDigits[3]))));

            String shiftedBinary = binaryInput.substring(0, binaryInput.length() - 1);
            String newBinary = newBit + shiftedBinary;
            int newInt = Integer.parseInt(newBinary, 2);
            nums.add(i, newInt);
            binaryInput = newBinary;
        }
    }


    /**
     * Print a red-black tree level-by-level
     * Since we are visit each node exactly once, the runtime complexity of this method would be O(n).
     * In this method, we also create a queue that we use to keep track of the traversal, so the space complexity would
     * also be O(n), since we need to add every element to the queue.
     * @param root root node of a red-black tree
     */
    public static void levelOrderPrint(TreeNode root) {

        /**
         * Queue used for tree traversal
         */
        LinkedList<TreeNode> queue = new LinkedList<TreeNode>();

        queue.add(root);

        /**
         * Print tree by level
         */
        while(!queue.isEmpty()) {
            TreeNode temp = queue.poll();

            if(temp.val != null) {
                System.out.println("(" + temp.val + ", " + temp.color + ")");
            }

            if(temp.left != null) {
                queue.add(temp.left);
            }
            if(temp.right != null) {
                queue.add(temp.right);
            }
        }

    }
}
