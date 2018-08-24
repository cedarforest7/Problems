import jdk.nashorn.api.tree.Tree;

import java.lang.Math;
import java.util.*;

public class TreeNode {
    int val;
    TreeNode left;
    TreeNode right;
    TreeNode(int x) { val = x; }
    public int maxDepth(TreeNode root) {
        if (root == null) {
            return 0;
        }
//        int h = 0;
//        if (maxDepth(root.left) > maxDepth(root.right)) {
//            return 1 + maxDepth(root.left);
//        }
//        return 1 + maxDepth(root.right);
        return 1 + Math.max(maxDepth(root.left), maxDepth(root.right));
    }

    public static TreeNode invertTree(TreeNode root) {
        if (root == null) {
            return root;
        }
        invertTree(root.left);
        invertTree(root.right);
        TreeNode temp = root.left;
        root.left = root.right;
        root.right = temp;
        return root;
    }

    LinkedList<Integer> l = new LinkedList<>();
    public List<Integer> inorderTraversal(TreeNode root) {
        if (root == null) {
            return (List)root;
        }
        inorderTraversal(root.left);
        //l.addLast(root.val);
        l.add(root.val);
        inorderTraversal(root.right);
        return l;
    }

    public List<Integer> preorderTraversal(TreeNode root) {
        if (root == null) {
            return (List)root;
        }
        l.addLast(root.val);
        preorderTraversal(root.left);
        preorderTraversal(root.right);
        return l;
    }

    public class BSTIterator {
        private int[] num;
        private int size = 0;
        private int scan;
        public BSTIterator(TreeNode root) {
            scan = 0;
            traverse(root);
            num = new int[size];
            getNum(root, num);
        }

        private void traverse (TreeNode root) {
            if (root == null) {
                return;
            }
            size++;
            System.out.print(size + ":" + root.val + " ");
            traverse(root.left);
            traverse(root.right);
        }
        int i = 0;
        private void getNum (TreeNode root, int[] num) {
            if (root == null) {
                return;
            }
            getNum(root.left, num);
            num[i] = root.val;
            i++;
            getNum(root.right, num);
        }

        /** @return whether we have a next smallest number */
        public boolean hasNext() {
            return scan < size ;
        }

        /** @return the next smallest number */
        public int next() {
            scan++;
            return num[scan - 1];
        }
    }//use a list is much better

    //Given a binary search tree, write a function kthSmallest to find the kth smallest element in it.
    //You may assume k is always valid, 1 ≤ k ≤ BST's total elements.
    public int kthSmallest(TreeNode root, int k) {
        if (root == null) {
            throw new NoSuchElementException();
        }
        LinkedList<Integer> l1 = (LinkedList<Integer>) inorderTraversal(root);
        return l1.get(k - 1);
    }

    //Given an array where elements are sorted in ascending order, convert it to a height balanced BST.
    public TreeNode sortedArrayToBST(int[] nums) {
        if (nums.length == 0) {
            return null;
        }
        TreeNode t  = new TreeNode(nums[nums.length/2]);
        t.left = sortedArrayToBST(Arrays.copyOfRange(nums, 0, nums.length/2));
        t.right = sortedArrayToBST(Arrays.copyOfRange(nums, nums.length/2 + 1, nums.length));
        return t;
    }

    //Given a binary tree, return the level order traversal of its nodes' values. (ie, from left to right, level by level).
    public List<List<Integer>> levelOrder(TreeNode root) {
        List<List<Integer>> list = new LinkedList<>();
        for (int i = 0; i < height(root); i++) {
            list.add(visitLevel(root, i));
        }
        return list;
    }

    private static List<Integer> visitLevel (TreeNode T, int i) {
        if (T == null) {
            return null;
        }
        List<Integer> l = new LinkedList<>();
        if (i == 0) {
            l.add(T.val);
            return l;
        }
        List<Integer> left = visitLevel(T.left, i - 1);
        if (left != null) {
            l.addAll(left);
        }
        List<Integer> right = visitLevel(T.right, i - 1);
        if (right != null) {
            l.addAll(right);
        }
        return l;
    }

    private static int height (TreeNode root) {
        if (root == null) {
            return 0;
        }
        TreeNode scan = root;
        return 1 + Math.max(height(root.left), height(root.right));
    }
    //No.107
    public List<List<Integer>> reverseLevelOrder(TreeNode root) {
        List<List<Integer>> list = new LinkedList<>();
        for (int i = height(root) - 1; i >= 0; i--) {
            list.add(visitLevel(root, i));
        }
        return list;
    }

    public List<Integer> rightSideView(TreeNode root) {
        if (root == null) {
            return null;
        }
        List<Integer> list = new LinkedList<>();
        for (int i = 0; i < height(root); i++) {
            visitRight(root, i);
            list.add(visitRight);
        }
        return list;
    }

    int visitRight;
    private void visitRight (TreeNode T, int i) {
        if (i == 0) {
            visitRight = T.val;
        }
        if (T.left != null) {
            visitRight(T.left, i - 1);
        }
        if (T.right != null) {
            visitRight(T.right, i - 1);
        }
    }

    //No.145
    List<Integer> l1 = new LinkedList<>();
    public List<Integer> postorderTraversal(TreeNode root) {
        if (root == null) {
            return l1;
        }
        postorderTraversal(root.left);
        postorderTraversal(root.right);
        l1.add(root.val);
        return l1;
    }

    //No.257
    public List<String> binaryTreePaths(TreeNode root) {
        List<String> path = new LinkedList<>();
        if (root == null) {
            return path;
        }
        if (root.left == null && root.right == null) {
            path.add(Integer.toString(root.val));
            return path;
        }
        if (root.left != null) {
            for (String s : binaryTreePaths(root.left)) {
                String s1 = Integer.toString(root.val) + "->" + s;
                path.add(s1);
            }

        }
        if (root.right != null) {
            for (String s : binaryTreePaths(root.right)) {
                String s2 = Integer.toString(root.val) + "->" + s;
                path.add(s2);
            }
        }
        return path;
    }

    //No.101
    public boolean isSymmetric(TreeNode root) {
        if (root == null) {
            return true;
        }
        return symmetric(root.left, root.right);
    }

    private boolean symmetric (TreeNode t1, TreeNode t2) {
        if (t1 == null|| t2 == null) {
            return t1 == null && t2 == null;
        }
        return t1.val == t2.val && symmetric(t1.left, t2.right) && symmetric(t1.right, t2.left);
    }

    private boolean isLeaf (TreeNode root) {
        return (root.left == null) && (root.right == null);
    }

    //No.235
    public TreeNode lowestCommonAncestor1(TreeNode root, TreeNode p, TreeNode q) {
        LinkedList<TreeNode> ancp = ancestors(root, p);
        LinkedList<TreeNode> ancq = ancestors(root, q);
        if (ancp.size() < ancq.size()) {
            for (TreeNode t : ancp) {
                if (ancq.contains(t)) {
                    return t;
                }
            }
        } else {
            for (TreeNode t : ancq) {
                if (ancp.contains(t)) {
                    return t;
                }
            }
        }
        return null;
    }

    private LinkedList<TreeNode> ancestors (TreeNode root, TreeNode p) {
        LinkedList<TreeNode> anc = new LinkedList<>();
        if (root == null) {
            return anc;
        }
        if (root == p) {
            anc.add(root);
            return anc;
        }
        LinkedList<TreeNode> left = ancestors(root.left, p);
        anc.addAll(left);
        LinkedList<TreeNode> right = ancestors(root.right, p);
        anc.addAll(right);
        if (!(left.isEmpty() && right.isEmpty())) {
            anc.add(root);
        }
        return anc;
    }

    //above is for binary tree, below for BST
    public TreeNode lowestCommonAncestor2(TreeNode root, TreeNode p, TreeNode q) {
        if (p.val == q.val) {
            return p;
        }
        if (p.val > q.val) {
            return ancHelper(root, q, p);
        } else {
            return ancHelper(root, p, q);
        }
    }
    //p<q
    private TreeNode ancHelper (TreeNode root, TreeNode p, TreeNode q) {
        if (root.val >= p.val && root.val <= q.val) {
            return root;
        }
        if (root.val > q.val) {
            return ancHelper(root.left, p, q);
        } else {
            return ancHelper(root.right, p, q);
        }

    }

    public TreeNode lowestCommonAncestor(TreeNode root, TreeNode p, TreeNode q) {
        if (root.val > p.val && root.val > q.val) {
            return lowestCommonAncestor(root.left, p, q);
        } else if (root.val < p.val && root.val < q.val){
            return lowestCommonAncestor(root.right, p, q);
        } else {
            return root;
        }
    }

    //No.129
    int sum = 0;
    public int sumNumbers(TreeNode root) {
        sumHelper(root);
        return sum;
    }

//    private void preOrder (TreeNode root) {
//        if (root == null) {
//            return;
//        }
//
//        sum = sum*10 + root.val;
//        int temp = sum;
//        preOrder(root.left);
//        preOrder(root.right);
//        sum += temp;
//    }

    private void sumHelper (TreeNode root) {
        if (root == null) {
            return;
        }
        if (root.left == null && root.right == null) {  //isLeaf
            sum += root.val;
        }
        if (root.left != null) {
            root.left.val += root.val * 10;
            sumHelper(root.left);
        }
        if (root.right != null) {
            root.right.val += root.val * 10;
            sumHelper(root.right);
        }
    }

    //No.110
    public boolean isBalanced(TreeNode root) {
        return (boolean)balanced(root)[0];
    }

    private Object[] balanced (TreeNode root) {
        Object[] obj = new Object[2];
        if (root == null) {
            obj[0] = true;
            obj[1] = 0;
            return obj;
        }
        Object[] obj1 = balanced(root.left);
        Object[] obj2 = balanced(root.right);
        obj[0] = (boolean)obj1[0] && (boolean)obj2[0] && ( (int)obj1[1] - (int)obj2[1] >= -1) && ((int)obj1[1] - (int)obj2[1] <= 1);
        if (!(boolean)obj[0]) {
            return obj;
        }
        obj[1] = 1 + Math.max((int)obj1[1], (int)obj2[1]);
        return obj;
    }

    //No.103
    public List<List<Integer>> zigzagLevelOrder1(TreeNode root) {
        List<List<Integer>> zigzag = new LinkedList<>();
        int height = height(root);
        for (int i = 0; i < height; i++) {
            zigzag.add(zigzagHelper1(root, i, i));
        }
        return zigzag;
    }

    private List<Integer> zigzagHelper1 (TreeNode root, int i, int j) {
        List<Integer> list = new LinkedList<>();
        if (root == null) {
            return list;
        }
        if (i == 0) {
            list.add(root.val);
        } else if ((j & 1) == 0) {
            list.addAll(zigzagHelper1(root.left, i - 1, j));
            list.addAll(zigzagHelper1(root.right, i - 1, j));
        } else {
            list.addAll(zigzagHelper1(root.right, i - 1, j));
            list.addAll(zigzagHelper1(root.left, i - 1, j));
        }
        return list;
    }

    public List<List<Integer>> zigzagLevelOrder(TreeNode root) {
        List<List<Integer>> zigzag = new LinkedList<>();
        zigzagHelper(root, 0, zigzag);
        return zigzag;
    }

    private void zigzagHelper (TreeNode root, int level, List<List<Integer>> zigzag) {
        LinkedList<Integer> list = new LinkedList<>();
        if (root == null) {
            return;
        }
        if (level == zigzag.size()){
            zigzag.add(list);
        }
        if ((level & 1) == 0) {
            zigzag.get(level).add(root.val);
        } else {
            ((LinkedList)zigzag.get(level)).addFirst(root.val);
        }

        zigzagHelper(root.left, level + 1, zigzag);
        zigzagHelper(root.right, level + 1, zigzag);
    }

    //No.114
    public void flatten(TreeNode root) {
        if (root == null) {
            return;
        }
        flatten(root.left);
        flatten(root.right);
        if (root.left == null) {
            return;
        }
        TreeNode scan;
        for (scan = root.left; scan.right != null; scan = scan.right);
        scan.right = root.right;
        root.right = root.left;
        root.left = null;
    }

    //No.113
    List<List<Integer>> path = new LinkedList<>();
    public List<List<Integer>> pathSum(TreeNode root, int sum) {
        LinkedList<Integer> lis = new LinkedList<>();
        dfs113(root, sum, lis);
        return path;
    }

    private void dfs113 (TreeNode root, int sum, LinkedList<Integer> lis) {
        if (root == null) {
            return;
        }
        int num = root.val;
        if (root.left == null && root.right == null) {      //is a leaf
            if (num == sum) {
                lis.add(num);
                List<Integer> temp = new LinkedList<>(lis);
                path.add(temp);
                lis.removeLast();
            }
            return;
        }
        lis.add(num);
        dfs113(root.left, sum - num, lis);
        dfs113(root.right, sum - num, lis);
        lis.removeLast();
    }

    //No.112
    //no need to record path in a list
    public boolean hasPathSum(TreeNode root, int sum) {
        return dfs112(root, sum);
    }

    private boolean dfs112 (TreeNode root, int sum) {
        if (root == null) {
            return false;
        }
        int num = root.val;
        if (root.left == null && root.right == null) {      //is a leaf
            return num == sum;
        }
        return dfs112(root.left, sum - num) || dfs112(root.right, sum - num);
    }

    //No.109
    //top-down
    public TreeNode sortedListToBST1(ListNode head) {
        ArrayList<Integer> num = new ArrayList<>();
        for (ListNode scan = head; scan != null; scan = scan.next) {
            num.add(scan.val);
        }
        int end = num.size() - 1;
        return helper109(num, 0, end);
    }

    private TreeNode helper109 (List<Integer> num, int start, int end) {
        if (end < start) {
            return null;
        }
        int mid = (start + end)/2;
        TreeNode root = new TreeNode(num.get(mid));
        if (start == end) {
            return root;
        }
        root.left = helper109(num, start, mid - 1);
        root.right = helper109( num, mid + 1, end);
        return root;
    }

    //bottom-up, less space
    ListNode scan;
    public TreeNode sortedListToBST(ListNode head) {
        int len = 0;
        for (ListNode scan = head; scan != null; scan = scan.next) {
            len++;      //length of listnode
        }
        scan = head;
        return sortedListToBST(0, len - 1);
    }

    private TreeNode sortedListToBST(int start, int end) {
        if (end < start) {
            return null;
        }
        int mid = (start + end)/2;
        TreeNode left = sortedListToBST(start, mid - 1);
        TreeNode root = new TreeNode(scan.val);
        root.left = left;
        scan = scan.next;
        root.right = sortedListToBST(mid + 1, end);
        return root;
    }



    public static void main(String[] args) {
        TreeNode root = new TreeNode(1);
        TreeNode t2 = new TreeNode(2);
        TreeNode t3 = new TreeNode(3);
        TreeNode t4 = new TreeNode(4);
        TreeNode t5 = new TreeNode(5);
        TreeNode t6 = new TreeNode(6);
        TreeNode t7 = new TreeNode(7);
        root.left = t2;
        root.right = t3;
        t2.left = t4;
        t2.right = t5;
        t3.left = t6;
        t3.right = t7;
//        LinkedList<TreeNode> anc2 = root.ancestors(root, t2);
//        for (TreeNode t : anc2) {
//            System.out.print(t.val + " ");
//        }
//        List<List<Integer>> l = root.pathSum(root, 7);
//        for (List lx : l) {
//            for (Object x : lx) {
//                System.out.print(x + " ");
//            }
//            System.out.println("end\n");
//        }
        root.hasPathSum(root, 7);
    }


}
