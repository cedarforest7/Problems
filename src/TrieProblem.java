import java.util.*;

public class TrieProblem {
    class TrieNode {
        Map<Character, TrieNode> next;
        boolean isWord;
        public TrieNode() {
            next = new HashMap<>();
        }
    }

    class Trie {
        //TrieNode only stores lowercase letters a-z

        TrieNode head;
        public Trie() {
            head = new TrieNode();
        }

        public void add(String word) {
            if (word == null || word.length() == 0) {
                return;
            }
            TrieNode curr = head;
            char[] chars = word.toCharArray();
            for (int i = 0; i < chars.length - 1; i++) {
                char c = chars[i];
                curr.next.putIfAbsent(c, new TrieNode());
                curr = curr.next.get(c);
            }
            char last = chars[chars.length - 1];
            curr.next.putIfAbsent(last, new TrieNode());
            curr.next.get(last).isWord = true;
        }


    }

    static final int[][] moves = {{0,1}, {1,0}, {0, -1}, {-1, 0}};
    //212
    public List<String> findWords(char[][] board, String[] words) {
        Trie trie = new Trie();
        //construct a trie
        for (String s : words) trie.add(s);
        Set<String> res = new HashSet<>();
        //DFS
        for (int i = 0; i < board.length; i++) {
            for (int j = 0; j < board[0].length; j++) {
                findWordsHelper(board, i, j, new boolean[board.length][board[0].length], res, new StringBuilder(), trie.head);
            }
        }
        return new ArrayList<>(res);
    }

    private void findWordsHelper(char[][] board, int i, int j, boolean[][] visited, Set<String> res, StringBuilder sb, TrieNode node) {
        if (i < 0 || i >= board.length || j < 0 || j >= board[0].length || visited[i][j]) {
            return;
        }
        //search all words start with sb + board[i][j]
        char c = board[i][j];
        if (!node.next.containsKey(c)) {
            return;
        }
        TrieNode next = node.next.get(c);
        sb.append(c);
        if (next.isWord) {
            res.add(sb.toString());
        }
        visited[i][j] = true;
        for (int k = 0; k < 4; k++) {
            findWordsHelper(board, i + moves[k][0], j + moves[k][1], visited, res, sb, next);
        }
        visited[i][j] = false;
        sb.deleteCharAt(sb.length() - 1);
    }

    public static void main(String[] args) {
        TrieProblem t = new TrieProblem();
        char[][] board = {
                {'o', 'a', 'a', 'n'},
                {'e', 't', 'a', 'e'},
                {'i', 'h', 'k', 'r'},
                {'i', 'f', 'l', 'v'}
            };
        String[] words = {"oath","pea","eat","rain"};
        List<String> lis = t.findWords(board, words);
        Helper.printList(lis);
    }
}
