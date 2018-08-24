import java.util.HashSet;
import java.util.LinkedList;
import java.util.List;
import java.util.Set;

public class TreeLinkNode {
    int val;
    TreeLinkNode left, right, next;
    TreeLinkNode(int x) { val = x; }

    //No.116
    public void connect1(TreeLinkNode root) {
        LinkedList<TreeLinkNode> Q = new LinkedList<>();
        TreeLinkNode scan = root;
        Q.add(root);
        for (int i = 0; Q.peekFirst() != null; i++) {
            if ((i & (i + 1)) != 0) {       //i + 1 is not power of 2
                scan.next = Q.pollFirst();
                scan = scan.next;
            } else {
                scan = Q.pollFirst();
            }
            Q.add(scan.left);
            Q.add(scan.right);
        }
    }

}
