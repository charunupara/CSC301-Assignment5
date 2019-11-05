import com.sun.source.tree.Tree;

public class RBT {

    /**
     * Root node of the red-black tree
     */
    TreeNode root;

    /**
     * The T.NIL node as specified in the textbook
     */
    TreeNode NIL;

    /**
     * The number of nodes in the tree
     */
    int size;

    /**
     * The height of the tree
     */
    int height;

    public RBT() {
        this.NIL = new TreeNode();
        this.root = this.NIL;
        this.NIL.color ='b';
    }

    /*
    public RBT(TreeNode root) {
        this.root = root;
        this.NIL = new TreeNode();
        this.root.parent = this.NIL;
        this.size = 0;
        this.height = 0;
    }

     */






}
