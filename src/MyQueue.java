import java.util.Stack;
//No.232
public class MyQueue {
    /** Initialize your data structure here. */
    Stack<Integer> s1, s2;

    public MyQueue() {
        s1 = new Stack<>();
        s2 = new Stack<>();
    }

    /** Push element x to the back of queue. */
    public void push(int x) {
        while (!s2.empty()) {
            s1.push(s2.pop());
        }
        s1.push(x);
        while (!s1.empty()) {
            s2.push(s1.pop());
        }
    }

    /** Removes the element from in front of queue and returns that element. */
    public int pop() {
        return s2.pop();
    }

    /** Get the front element. */
    public int peek() {
        return s2.peek();
    }

    /** Returns whether the queue is empty. */
    public boolean empty() {
        return s2.empty();
    }
}
