import java.util.*;

public class ListNode {
    int val;
    ListNode next;
    ListNode(int x) {
        val = x;
        next = null;
    }
    public static void printList(ListNode l) {
        if (l == null) {
            System.out.print("Empty list!");
            return;
        }
        for (ListNode scan = l; scan != null; scan = scan.next) {
            System.out.print(scan.val + " ");
        }
        System.out.print("\n");
    }
    public static boolean hasCycle1(ListNode head) {
        if (head == null) {
            return false;
        }
        ListNode front;
        ListNode scan = head;
        int i = 0;
        while (scan.next != null) {
            scan = scan.next;
            i++;
            if (scan.next == null) {
                return false;
            }
            front = head;
            for (int j = 0; j < i; j++, front = front.next) {
                if (front.next == scan.next) {
                    return true;
                }
            }
        }
        return false;
    }
    public static boolean hasCycle2(ListNode head) {
        if (head == null) {
            return false;
        }
        ListNode scan = head;
        int i = 0;      //index of the listnode
        Map<Integer, ListNode> listMap = new HashMap<>();
        while (scan != null) {
            listMap.put(i, scan);
            //check if scan.next exists in the map
            if (listMap.containsValue(scan.next)) {
                return true;
            }
            scan = scan.next;
            i++;
        }
        return false;
    }
    public static boolean hasCycle3(ListNode head) {
        if (head == null) {
            return false;
        }
        ListNode scan = head;
        Set<ListNode> listSet = new HashSet<>();
        while (scan != null) {
            listSet.add(scan);
            //check if scan.next exists in the map
            if (listSet.contains(scan.next)) {
                return true;
            }
            scan = scan.next;
        }
        return false;
    }
    public static ListNode removeElements(ListNode head, int val) {
        ListNode pre = new ListNode(0);
        pre.next = head;
        ListNode scan = pre;
        while (scan.next != null) {
            if (scan.next.val == val) {
                scan.next = scan.next.next;
            } else {
                scan = scan.next;
            }
        }
        return pre.next;
    }

    public static ListNode reverseList(ListNode head) {
        if (head == null || head.next == null) {
            return head;
        }
        ListNode scan = head;
        ListNode reverse;
        for (;scan.next.next != null; scan = scan.next);
        reverse = scan.next;
        scan.next = null;
        reverse.next = reverseList(head);
        return reverse;
    }

    public static ListNode reverseList2(ListNode head) {
        if (head == null) {
            return head;
        }
        ListNode pre = null;
        ListNode scan = head;
        ListNode Next = head.next;
        while (scan.next != null) {
            scan.next = pre;
            pre = scan;
            scan = Next;
            Next = Next.next;
        }
        scan.next = pre;
        return scan;
    }

    public static void deleteNode(ListNode node) {
        node.val = node.next.val;
        node.next = node.next.next;
        return;
    }
    //l1 and l2 are two non-empty linked lists representing two non-negative integers.
    public static ListNode addTwoNumbers1(ListNode l1, ListNode l2) { //abandoned
        int a = 0, b = 0;
        int i = 1, j = 1;       //to multiply the digits
        ListNode s1 = l1, s2 = l2;        //scan
        while (s1 != null) {
            a = a + s1.val * i;
            i = i * 10;
            s1 = s1.next;
        }
        while (s2 != null) {
            b = b + s2.val * j;
            j = j * 10;
            s2 = s2.next;
        }

        int sum = a + b;
        ListNode dummy = new ListNode(0);
        ListNode scan = dummy;
        if (sum == 0) {
            dummy.next = new ListNode(0);
        }
        while (sum != 0) {
            scan.next = new ListNode(sum % 10);
            sum = sum / 10;
            scan = scan.next;
        }
        return dummy.next;
    }

    public static ListNode addTwoNumbers(ListNode l1, ListNode l2) {
        int a = 0, b = 0;
        ListNode s1 = l1, s2 = l2;        //scan
        ListNode dummy = new ListNode(0);
        ListNode scan = dummy;
        int len1 = 0, len2 = 0;
        while (s1 != null && s2 != null) {
            len1++;
            s1 = s1.next;
        }
        while (s2 != null) {
            len2++;
            s2 = s2.next;
        }
        s1 = l1;
        s2 = l2;
        int quo = 0, rem;

        if (len1 > len2) {      //s1 longer
            while (s2 != null){
                rem = (s1.val + s2.val + quo) % 10;
                scan.next = new ListNode(rem);
                quo = (s1.val + s2.val + quo) / 10;
                s1 = s1.next;
                s2 = s2.next;
                scan = scan.next;
            }
            while (s1 != null) {
                rem = (quo + s1.val) % 10;
                scan.next = new ListNode(rem);
                quo = (quo + s1.val) / 10;
                scan = scan.next;
                s1 = s1.next;
            }
        } else {
            while (s1 != null) {
                rem = (s1.val + s2.val + quo) % 10;
                scan.next = new ListNode(rem);
                quo = (s1.val + s2.val + quo) / 10;
                s1 = s1.next;
                s2 = s2.next;
                scan = scan.next;
            }
            while (s2 != null) {
                rem = (quo + s2.val) % 10;
                scan.next = new ListNode(rem);
                quo = (quo + s2.val) / 10;
                scan = scan.next;
                s2 = s2.next;
            }
        }
        if (quo == 1) {
            scan.next = new ListNode(1);
        }
        return dummy.next;
    }
    public ListNode removeNthFromEnd(ListNode head, int n) {
        ListNode scan = head;
        ListNode dummy = new ListNode(0);
        dummy.next = head;
        int len = 0;
        while (scan != null) {
            len++;
            scan = scan.next;
        }
        int index = len - n;
        scan = dummy;
        for (int i = 0; i < index; i++) {
            scan = scan.next;
        }
        scan.next = scan.next.next;
        return dummy.next;
    }

    public static ListNode rotateRight(ListNode head, int k) {
        if (head == null || head.next == null) {
            return head;
        }
        ListNode dummy = new ListNode(0);
        dummy.next = head;
        ListNode scan = head;
        int len = 0;
        while (scan.next != null) {
            len++;
            scan = scan.next;
        }
        len++;
        ListNode end = scan;
        int index;
        if (k >= len) {
            index = len - (k - len) % len;
        } else {
            index = len - k;
        }
        if (index == len) {
            return head;
        }
        scan = dummy;
        for (int i = 0; i < index; i++) {       //scan to the index to the end
            scan = scan.next;
        }
        dummy.next = scan.next;
        scan.next = null;
        end.next = head;
        return dummy.next;
    }
    //swap every two adjacent nodes and return its head.
    public static ListNode swapPairs(ListNode head) {
        if (head == null) {
            return head;
        }
        ListNode dummy = new ListNode(0);
        dummy.next = head;
        ListNode s1 = head;
        ListNode pre = dummy;
        ListNode s2 = head.next;
        while (s1.next != null && s2.next != null) {
            s1.next = s2.next;
            s2.next = s1;
            pre.next = s2;
            pre = s1;
            s1 = s1.next;
            s2 = s1.next;
        }
        if (s2 == null) {
            return dummy.next;
        }
        s1.next = s2.next;
        s2.next = s1;
        pre.next = s2;
        return dummy.next;
    }

    public static ListNode deleteDuplicates(ListNode head) { //delete all items that have duplicates from a sorted list
        if (head == null || head.next == null) {
            return head;
        }
        ListNode dummy = new ListNode(0);
        dummy.next = head;
        ListNode scan = dummy;
        boolean dup = false;
        while (scan.next != null && scan.next.next != null) {
            while (scan.next.next != null && scan.next.val == scan.next.next.val) {
                scan.next = scan.next.next;
                dup = true;     //scan.next should be deleted in the end
            }
            if (!dup) {
                scan = scan.next;
            } else {
                scan.next = scan.next.next;
                dup = false;
            }
        }
        return dummy.next;
    }

    public static ListNode partition(ListNode head, int x) {
        ListNode dummy = new ListNode(x - 1);
        dummy.next = head;
        ListNode scan = dummy;
        ListNode tail = new ListNode(0);
        ListNode tailEnd = tail;
        while (scan.next != null) {
            if(scan.next.val < x) {
                scan = scan.next;
            } else {
                tailEnd.next = new ListNode(scan.next.val);
                tailEnd = tailEnd.next;
                scan.next = scan.next.next;
            }
        }
        scan.next = tail.next;
        return dummy.next;
    }

    public ListNode middleNode(ListNode head) {
        // write your code here
        if (head == null) {
            return null;
        }
        ListNode slow = head, fast = head;
        while (fast.next != null && fast.next.next != null) {
            slow = slow.next;
            fast = fast.next.next;
        }
        return slow;
    }

    //lintcode 104
    public ListNode mergeKLists(List<ListNode> lists) {
        if (lists == null || lists.size() == 0) {
            return null;
        }
        PriorityQueue<ListNode> pq = new PriorityQueue<>(lists.size(), new Comparator<ListNode>(){
            @Override
            public int compare(ListNode node1, ListNode node2) {
                return node1.val - node2.val;
            }
        });
        ListNode dummy = new ListNode(-1);
        ListNode scan = dummy;
        for (ListNode node : lists) {
            if(node != null) {
                pq.offer(node);
            }

        }
        while(!pq.isEmpty()) {
            System.out.println("xxx");
            ListNode temp = pq.poll();
            scan.next = new ListNode(temp.val);
            scan = scan.next;
            if (temp.next != null) {
                pq.offer(temp.next);
            }
        }

        return dummy.next;
    }

    //No.141
    public boolean hasCycle(ListNode head) {
        if (head == null) {
            return false;
        }
        ListNode fast = head;
        ListNode slow = head;
        while (fast != null) {
            if (fast.next == null) {
                return false;
            }
            fast = fast.next.next;
            slow = slow.next;
            if (fast == slow) {
                return true;
            }
        }
        return false;

    }

    public static void main(String[] args) {
//        ListNode lx6 = new ListNode(9);
//        ListNode lx5 = new ListNode(9);
//        lx5.next = lx6;
//        ListNode lx4 = new ListNode(9);
//        lx4.next = lx5;
//        ListNode lx3 = new ListNode(9);
//        lx3.next = lx4;
//        ListNode lx2 = new ListNode(9);
//        lx2.next = lx3;
        ListNode lx1 = new ListNode(2);
//        lx1.next = lx2;
        ListNode l1 = new ListNode(2);
        l1.next = lx1;
        ListNode l2 = new ListNode(2);
        l2.next = l1;
        ListNode l3 = new ListNode(2);
        l3.next = l2;
//        ListNode l4 = new ListNode(4);
//        l4.next = l4;
//        System.out.println(hasCycle1(l3));
        ListNode l6 = new ListNode(1);
        l6.next = l3;
//        ListNode l5 = removeElements(l6, 2);
//        printList(l5);
//        printList(lx1);
//        printList(l6);
//        printList(addTwoNumbers(lx1, l6));
//        printList(swapPairs(l6));
        printList(deleteDuplicates(l6));
    }
}
