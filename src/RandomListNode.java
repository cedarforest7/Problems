import java.util.HashMap;
import java.util.Map;


//No.138
public class RandomListNode {
    int label;
    RandomListNode next, random;
    RandomListNode(int x) { this.label = x; }


    public RandomListNode copyRandomList(RandomListNode head) {
        //could it be cyclic??
        //connect old w/ new
        if (head == null) {
            return null;
        }
        for (RandomListNode p = head; p != null; p = p.next.next) {
            RandomListNode temp = p.next;
            p.next = new RandomListNode(p.label);       //make a dup
            p.next.next = temp;
        }
        //connect random in new
        for (RandomListNode p = head; p != null; p = p.next.next) {
            if (p.random == null) {
                p.next.random = null;
            } else {
                p.next.random = p.random.next;
            }
        }
        RandomListNode dummy = new RandomListNode(-1);
        dummy.next = head.next;
        //disconnect old w/ new
        for (RandomListNode p = head; p != null; p = p.next) {
            RandomListNode temp = p.next.next;
            if (temp == null) {
                p.next = null;
                break;
            }
            p.next.next = temp.next;       //skip old node
            p.next = temp;
        }
        return dummy.next;
    }


    public static void main(String[] args) {
        RandomListNode r1 = new RandomListNode(1);
        RandomListNode r2 = new RandomListNode(2);
        RandomListNode r3 = new RandomListNode(3);
        RandomListNode r4 = new RandomListNode(4);
        r1.next = r2;
        r2.next = r3;
        r3.next = r4;
        r4.next = null;
        r1.random = r3;
        r2.random = r4;
        r3.random = r4;
        r4.random = r1;

        RandomListNode r0 = new RandomListNode(1);
        RandomListNode rx = r0.copyRandomList(r0);

    }
}
