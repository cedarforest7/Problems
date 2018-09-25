import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;


//best sol: doubly linked list
public class LRUCache {
    Map<Integer, Integer> map;
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


    public static void main(String[] args) {
        LRUCache cache = new LRUCache( 2 /* capacity */ );

        cache.put(1, 1);
        cache.put(2, 2);
        cache.get(1);       // returns 1
        cache.put(3, 3);    // evicts key 2
        cache.get(2);       // returns -1 (not found)
        cache.put(4, 4);    // evicts key 1
        cache.get(1);       // returns -1 (not found)
        cache.get(3);       // returns 3
        cache.get(4);       // returns 4
    }

}
