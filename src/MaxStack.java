import java.util.Comparator;
import java.util.PriorityQueue;
import java.util.Stack;

public class MaxStack {
    private class DequeNode {
        int val;
        DequeNode prev;
        DequeNode next;

        public DequeNode(int x) {
            val = x;
            prev = null;
            next = null;
        }
    }

    DequeNode dummyTail;
    PriorityQueue<Integer> pq;

    /** initialize your data structure here. */
    public MaxStack() {
        dummyTail = new DequeNode(Integer.MIN_VALUE);
        pq = new PriorityQueue<>(Comparator.reverseOrder());    //Max heap
    }

    public void push(int x) {
        //O(logN) time
        DequeNode node = new DequeNode(x);
        DequeNode prev = dummyTail.prev;
        dummyTail.prev = node;
        node.next = dummyTail;
        if (prev != null) {
            prev.next = node;
            node.prev = prev;
        }
        pq.offer(x);
    }

    public int pop() {
        int pop = dummyTail.prev.val;
        deleteNode(dummyTail.prev);
        pq.remove(pop);
        return pop;
    }

    public int top() {
        return dummyTail.prev.val;
    }

    public int peekMax() {
        return pq.peek();
    }

    public int popMax() {
        int max = pq.poll();
        DequeNode node = dummyTail.prev;
        while (node != null) {
            if (node.val == max) {
                break;
            } else {
                node = node.prev;
            }
        }
        deleteNode(node);
        return max;
    }

    private void deleteNode(DequeNode node) {
        DequeNode prev = node.prev;
        if (prev == null) {
            node.next.prev = null;
            return;
        }
        prev.next = node.next;
        node.next.prev = prev;
    }

    public static void main(String[] args) {
        MaxStack stack = new MaxStack();
        stack.push(5);
        stack.push(1);
        stack.push(5);
        stack.top(); // 5
        stack.popMax(); // 5
        stack.top(); // 1
        stack.peekMax(); // 5
        stack.pop(); // 1
        stack.top(); // 5
    }
}
