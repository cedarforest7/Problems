import java.util.Collections;
import java.util.PriorityQueue;

public class MedianFinder {
    /*class DequeNode {

        DequeNode prev;
        DequeNode next;
        int val;

        public DequeNode(int val) {
            this.val = val;
            prev = null;
            next = null;
        }
    }

    DequeNode head, tail;
    int size;
    DequeNode p;

    public MedianFinder() {
        head = new DequeNode(Integer.MIN_VALUE);
        tail = new DequeNode(Integer.MAX_VALUE);
        head.next = tail;
        tail.prev = head;
        p = head;
        size = 0;
    }

    public void addNum(int num) {
        DequeNode node = head;
        for (; node.next != null && node.val < num; node = node.next);
        //now node.val >= num
        DequeNode temp = new DequeNode(num);
        temp.prev = node.prev;
        temp.next = node;
        temp.prev.next = temp;
        node.prev = temp;
        size++;

        if (num > p.val && size % 2 != 0) {
            p = p.next;
        }
        if (num < p.val && size % 2 == 0) {
            p = p.prev;
        }

    }

    public double findMedian() {
        if (size % 2 != 0) {
            return p.val;
        }
        return ((double)(p.val + p.next.val)) / 2;
    }*/

    PriorityQueue<Integer> left;
    PriorityQueue<Integer> right;

    public MedianFinder() {
        left = new PriorityQueue<>(11, Collections.reverseOrder());
        right = new PriorityQueue<>();
        left.offer(Integer.MIN_VALUE);
        right.offer(Integer.MAX_VALUE);
    }

    public void addNum(int num) {
        int p = left.peek();
        int q = right.peek();
        if (left.size() == right.size()) {
            if (num <= q) {
                left.offer(num);
            } else {
                // num > q
                right.offer(num);
                left.offer(right.poll());
            }
        } else {
            if (num >= p) {
                right.offer(num);
            } else {
                // num < p
                left.offer(num);
                right.offer(left.poll());
            }
        }
    }

    public double findMedian() {
        if (left.size() != right.size()) {
            return left.peek();
        }
        return (left.peek() + right.peek()) / 2.0;
    }
}
