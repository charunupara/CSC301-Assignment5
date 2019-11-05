public class TreeNode {

    /**
     * The value or key of the node
     */
    Integer val;

    /**
     * The left child of the node
     */
    TreeNode left;

    /**
     * The right child of the node
     */
    TreeNode right;

    /**
     * The parent of the node
     */
    TreeNode parent;

    /**
     * The color of the node
     */
    char color;

    public TreeNode() {
        this.val = null;
    }

    public TreeNode(int val) {
        this.val = val;
        this.left = null;
        this.right = null;
        this.parent = null;
        this.color = 'r';
    }


}
