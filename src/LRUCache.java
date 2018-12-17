import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;



public class LRUCache {
    /*Map<Integer, Integer> map;
    List<Integer> cache;
    //list as a FIFO queue

    int size;

    public LRUCache(int capacity) {
        map = new HashMap<>();
        cache = new ArrayList<>();
        size = capacity;
    }

    public int get(int key) {

        if (!map.keySet().contains(key)) {
            return -1;
        }
        //after get key, move it to the end of the queue
        cache.remove((Object)key);
        cache.add(key);
        return map.get(key);
    }

    public void put(int key, int value) {
        if (map.keySet().contains(key)) {
            get(key);
        } else {
            cache.add(key);
            if (cache.size() > size) {
                // cache is full, remove the first element
                map.remove(cache.get(0));
                cache.remove(0);
            }
        }
        map.put(key, value);
    }
*/


    class ListNode {
        int key;
        int val;
        ListNode next;

        public ListNode(int key, int val) {
            this.key = key;
            this.val = val;
            next = null;
        }
    }

    ListNode dummy;   //store the keys
    Map<Integer, ListNode> map;    //store the key nodes
    Map<Integer, ListNode> prev;     //store the previous node
    //Map<Integer, Integer> values;
    ListNode last;
    int capacity;


    public LRUCache(int capacity) {
        this.capacity = capacity;
        dummy = new ListNode(-1, -1);
        map = new HashMap<>();
        prev = new HashMap<>();
        last = dummy;
        //values = new HashMap<>();
    }

    /*
     * @param key: An integer
     * @return: An integer
     */
    public int get(int key) {
        if (!map.containsKey(key)) {
            return -1;
        }
        moveToLast(key);
        return last.val;
    }

    /*
     * @param key: An integer
     * @param value: An integer
     * @return: nothing
     */
    public void set(int key, int value) {

        if (map.containsKey(key)) {
            //key is already present, delete the original node
            moveToLast(key);
            last.val = value;
        } else if (map.keySet().size() == capacity) {
            //does not contain the key and reached capacity
            //System.out.println(key);
            ListNode first = dummy.next;
            map.remove(first.key);
            prev.remove(first.key);
            first.key = key;
            first.val = value;
            map.put(key, first);
            prev.put(key, dummy);
            moveToLast(key);
        } else {
            //insert a new node
            //addLastNode(key, map.get(key).val);
            ListNode temp = new ListNode(key, value);
            last.next = temp;
            map.put(key, temp);
            prev.put(key, last);
            last = temp;
        }


    }
    

    private void moveToLast(int key) {
        ListNode node = map.get(key);
        if (last == node) {
            return;
        }
        ListNode pre = prev.get(key);
        pre.next = pre.next.next;
        if (node.next != null) {
            prev.put(node.next.key, pre);
        }
        prev.put(key, last);
        last.next = node;
        last = node;
        node.next = null;
    }

    public static void main(String[] args) {
        LRUCache cache = new LRUCache( 2 /* capacity */ );

        /*cache.put(1, 1);
        cache.put(2, 2);
        cache.get(1);       // returns 1
        cache.put(3, 3);    // evicts key 2
        cache.get(2);       // returns -1 (not found)
        cache.put(4, 4);    // evicts key 1
        cache.get(1);       // returns -1 (not found)
        cache.get(3);       // returns 3
        cache.get(4);       // returns 4*/


        cache.set(2,1);
        cache.set(1,1);
        System.out.println(cache.get(2));
        cache.set(4,1);
        System.out.println(cache.get(1));
        System.out.println(cache.get(2));


    }

}
