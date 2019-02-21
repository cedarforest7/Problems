import java.util.*;

public class AutocompleteSystem {

    private class TrieNode {
        char val;
        HashMap<Character, TrieNode> children;
        boolean isEnd;
        PriorityQueue<String> top3;
        TrieNode(char c) {
            val = c;
            children = new HashMap<>();
            isEnd = false;
            top3 = new PriorityQueue<>(3, cmp);     //maxheap
        }
    }

    private Comparator<String> cmp;

    private Map<String, Integer> count;
    private Map<TrieNode, String> strMap;
    //private Set<String> set;
    //private PriorityQueue<String> pq;   //maxheap

    private TrieNode dummy;
    private TrieNode trie;
    private StringBuilder sb;
    //private List<String> lis;

    public AutocompleteSystem(String[] sentences, int[] times) {
        count = new HashMap<>();
        strMap = new HashMap<>();
        sb = new StringBuilder();
        cmp = new Comparator<>(){
            @Override
            public int compare(String s1, String s2) {
                int f1 = count.get(s1), f2 = count.get(s2);
                if (f1 == f2) {
                    return s2.compareTo(s1);
                }
                return f1 - f2;
            }
        };
        dummy = new TrieNode('0');

        //build trie
        for (int k = 0; k < sentences.length; k++) {
            String s = sentences[k];
            count.put(s, times[k]);
            TrieNode cur = dummy;
            for (int i = 0; i < s.length(); i++) {
                char c = s.charAt(i);
                Map<Character, TrieNode> map = cur.children;
                if (map.containsKey(c)) {
                    cur = map.get(c);
                } else {
                    cur = new TrieNode(c);
                    map.put(c, cur);
                }
                if (i == s.length() - 1) {
                    cur.isEnd = true;
                    strMap.putIfAbsent(cur, s);
                }
            }
        }
        trie = dummy;
        constructTop3(dummy);
    }
    
    private void insertNode(String s, int i, TrieNode node) {
        //backtrack
        if (i == s.length()) {
            node.isEnd = true;
            strMap.putIfAbsent(node, s);
            //update priority
            if (node.top3.contains(s)) {
                node.top3.remove(s);
            }
            node.top3.offer(s);
            if (node.top3.size() > 3) {
                node.top3.poll();
            }
            return;
        }
        char c = s.charAt(i);
        Map<Character, TrieNode> map = node.children;
        TrieNode next;
        if (map.containsKey(c)) {
            next = map.get(c);
        } else {
            next = new TrieNode(c);
            map.put(c, next);
            next.top3.offer(s);
        }
        insertNode(s, i + 1, next);
        //update top3
        PriorityQueue<String> pq = new PriorityQueue<>(next.top3);
        while(!node.top3.isEmpty()) {
            String str = node.top3.poll();
            if (pq.contains(str)) {
                continue;
            }
            pq.offer(str);
        }
        while(pq.size() > 3) {
            pq.poll();
        }
        node.top3 = pq;
    }
    private PriorityQueue<String> constructTop3(TrieNode node){
        //post order traversal, only get called once
        Map<Character, TrieNode> map = node.children;
        if (node.isEnd) {
            //end
            node.top3.offer(strMap.get(node));
            if(map.isEmpty()) {
                return node.top3;
            }
        }
        for (char c : map.keySet()) {
            TrieNode temp = map.get(c);

            PriorityQueue<String> pq = new PriorityQueue<>(constructTop3(temp));
            while(!pq.isEmpty()) {
                if(node.top3.size() < 3) {
                    node.top3.offer(pq.poll());
                    continue;
                }
                String s = pq.poll();
                if (cmp.compare(s, node.top3.peek()) > 0) {
                    node.top3.poll();
                    node.top3.offer(s);
                }
            }
        }
        return node.top3;
    }

    public List<String> input(char c) {
        if(c == '#') {
            //reset
            trie = dummy;
            String s = sb.toString();
            count.put(s, count.getOrDefault(s, 0) + 1);
            insertNode(s, 0, dummy);
            sb = new StringBuilder();
            return new ArrayList<>();
        }
        HashMap<Character, TrieNode> map = trie.children;
        if (!map.containsKey(c)) {
            trie = new TrieNode('0');
            sb.append(c);
            return new ArrayList<>();
        }
        sb.append(c);
        trie = map.get(c);
        List<String> res = new ArrayList<>();
        PriorityQueue<String> pq = new PriorityQueue<>(trie.top3);
        while(!pq.isEmpty()) {
            res.add(pq.poll());
        }
        Collections.reverse(res);
        return res;
    }

    public static void main(String[] args) {
        AutocompleteSystem sys = new AutocompleteSystem(new String[]{"abc", "abbc", "a"}, new int[]{3,3,3});
        List<String> l1 = sys.input('a');
        List<String> l2 = sys.input('c');
        List<String> l3 = sys.input('#');
        //sys.input('#');
        Helper.printList(l1);
        Helper.printList(l2);
        Helper.printList(l3);
        List<String> l11 = sys.input('b');
        List<String> l22 = sys.input('c');
        List<String> l33 = sys.input('#');
        //sys.input('#');
        Helper.printList(l11);
        Helper.printList(l22);
        Helper.printList(l33);
    }
}
