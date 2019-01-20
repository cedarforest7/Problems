import java.util.*;

public class Number {
    //lintcode 513
    public int numSquares(int n) {
        int[] sq = new int[n + 1];
        //build the array from 1
        for (int i = 1; i * i <= n; i++) {
            sq[i * i] = 1;
        }
        if (sq[n] != 0) {
            return sq[n];
        }
        for (int i = 1; i <= n; i++) {
            if (sq[i] == 1) {
                continue;
            }
            sq[i] = Integer.MAX_VALUE;
            for (int j = 1; j * j < i; j++) {
                sq[i] = Math.min(sq[i], sq[j * j] + sq[ i - j * j]);
            }

        }
        return sq[n];
    }

    //lintcode 116
    public boolean canJump(int[] A) {
        if (A == null || A.length == 0) {
            return false;
        }
        int max = A[0];
        int i = 0;
        for (; i <= max; i++) {
            if (i + A[i] >= A.length - 1) {
                return true;
            }
            //System.out.println(i);
            max = Math.max(max, i + A[i]);
            if (max >= A.length - 1 ) {
                return true;
            }
        }

        return i >= A.length;
    }

    //lintcode 76
    public int longestIncreasingSubsequence(int[] nums) {
        //DP
        if (nums == null || nums.length == 0) {
            return 0;
        }
        int n = nums.length;
        int[] lis = new int[n];
        lis[0] = nums[0];
        int len = 1;
        for (int i = 1; i < n; i++) {
            //compare with the tail of current LIS
            if (lis[len - 1] < nums[i]) {
                lis[len] = nums[i];
                len++;
            } else {
                //tail >= nums[i]
                //find the first value in LIS that is larger than or equals nums[i], and replace it
                int index = firstGTE(lis, len, nums[i]);
                lis[index] = nums[i];
            }
        }
        return len;
    }

    private int firstGTE(int[] lis, int len, int num) {
        //binary search
        int start = 0, end = len - 1;
        while (start + 1 < end) {
            int mid = start + (end - start) / 2;
            if (lis[mid] >= num) {
                //move to left
                end = mid;
            } else {
                start = mid;
            }
        }
        if (lis[start] >= num) {
            return start;
        }
        return end;
    }

    //No.904, 3 pointers
    public int totalFruit(int[] tree) {
        if (tree == null) {
            return 0;
        }

        int n = tree.length;
        if (n < 3) {
            return n;
        }
        int p1 = 0, p2 = 0;
        if (tree[1] != tree[0]) {
            p2 = 1;
        }
        int[] nums = {tree[0], tree[1]};

        int maxLen = 2;
        for (int i = 2; i < n; i++) {
            if (tree[i] == nums[0] || tree[i] == nums[1]) {
                if (tree[i] != tree[i - 1]) {
                    p2 = i;
                    nums[0] = nums[1];
                    nums[1] = tree[i];
                }

            } else {
                p1 = p2;
                p2 = i;
                nums[0] = nums[1];
                nums[1] = tree[i];
            }
            maxLen = Math.max(maxLen, i - p1 + 1);
            //System.out.println(p1 + ", " + p2 + "[" + nums[0] + "," + nums[1] + "]");
        }
        return maxLen;
    }

    //No.322
    public int coinChange(int[] coins, int amount) {

        if (coins == null || coins.length == 0 || amount < 0) {
            return -1;
        }
        //Arrays.sort(coins);
        int[] dp = new int[amount + 1];
        Arrays.fill(dp, Integer.MAX_VALUE);
        dp[0] = 0;
        for (int val : coins) {
            for (int i = val; i <= amount; i++) {
                if (dp[i - val] != Integer.MAX_VALUE) {
                    dp[i] = Math.min(dp[i], 1 + dp[i - val]);
                }
            }
        }
        return dp[amount] == Integer.MAX_VALUE ? -1 : dp[amount];
    }

    class Entry<String, Double> implements Map.Entry<String, Double> {
        private String key;
        private Double val;

        public Entry(String key, Double val) {
            this.key = key;
            this.val = val;
        }

        @Override
        public String getKey() {
            return key;
        }

        @Override
        public Double getValue() {
            return val;
        }

        @Override
        public Double setValue(Double value) {
            Double old = val;
            val = value;
            return val;
        }
    }

    //No.399
    public double[] calcEquation(String[][] equations, double[] values, String[][] queries) {
        if (equations == null || values == null || queries == null) {
            return null;
        }
        double[] res = new double[queries.length];
        Map<String, List<Entry<String, Double>>> adj = new HashMap<>();     //adjacency list
        for (int i = 0; i < equations.length; i++) {
            String[] str = equations[i];
            adj.putIfAbsent(str[0], new ArrayList<>());
            adj.get(str[0]).add(new Entry(str[1], values[i]));
            adj.putIfAbsent(str[1], new ArrayList<>());
            adj.get(str[1]).add(new Entry(str[0], 1 / values[i]));
        }

        for (int i = 0; i < queries.length; i++) {
            String start = queries[i][0];
            String end = queries[i][1];
            if (!adj.containsKey(start)) {
                res[i] = -1;
                continue;
            }
            res[i] = dfs(adj, start, end, 1, new HashSet<>());
        }
        return res;
    }

    private double dfs (Map<String, List<Entry<String, Double>>> adj, String start, String end, double pre, Set<String> visited) {
        if (start.equals(end)) {
            return pre;
        }
        if (!adj.containsKey(start)) {
            return -1;
        }

        List<Entry<String, Double>> lis = adj.get(start);
        for (Entry ent : lis) {
            double val = (double)ent.getValue();
            String temp = (String)ent.getKey();
            if (visited.contains(temp)) {
                continue;
            }
            visited.add(temp);
            double res = dfs(adj, temp, end, pre * val, visited);
            if (res != -1) {
                return res;
            }
        }
        return -1;
    }

    //621
    public int leastInterval(char[] tasks, int n) {
        if (tasks == null || tasks.length == 0) {
            return 0;
        }
        int[] freq = new int[26];
        for (char c : tasks) {
            freq[c - 'A']++;
        }
        Arrays.sort(freq);
        int rowNum = freq[25];
        int idles = (rowNum - 1) * n;
        for (int i = 24; i >= 0; i--) {
            if (freq[i] == 0) {
                continue;
            }
            idles = idles - Math.min(freq[i], rowNum - 1);
        }
        return tasks.length + (idles > 0 ? idles : 0);
    }

    public int leastInterval1(char[] tasks, int n) {
        if (tasks == null || tasks.length == 0) {
            return 0;
        }
        int[] freq = new int[26];
        int[] lastOccur = new int[26];

        for (char c : tasks) {
            freq[c - 'A']++;
            Arrays.fill(lastOccur, - n - 1);        //O(26) = O(1)
        }
        int intervals = 0;
        Comparator<Character> cmp = new Comparator<Character>() {
            @Override
            public int compare(Character c1, Character c2) {
                if (lastOccur[c1 - 'A'] != lastOccur[c2 - 'A']) {
                    //last occur smaller, higher priority
                    return lastOccur[c1 - 'A'] - lastOccur[c2 - 'A'];

                }
                //higher freq, higher priority
                return freq[c2 - 'A'] - freq[c1 - 'A'];
            }
        };
        PriorityQueue<Character> pq = new PriorityQueue<>(26, cmp);
        for (int i = 0; i < 26; i++) {
            if (freq[i] != 0) {
                pq.offer((char)('A' + i));
            }

        }
        while (!pq.isEmpty()) {
            char temp = pq.poll();  //task is executed once
            int f = freq[temp - 'A'] - 1;
            int last = lastOccur[temp - 'A'];
            if (intervals - last < n) {
                System.out.print("idle"+ (last + n - intervals) + " ");
                intervals = last + n;
            }
            System.out.print(temp + " ");
            intervals++;
            //update frequency and occurrence
            freq[temp - 'A'] = f;
            lastOccur[temp - 'A'] = intervals;
            if (f > 0) {
                pq.offer(temp);
            }
            if (intervals % (n + 1) == 0) {
                //reset
                Arrays.fill(lastOccur, - n - 1);
                pq = new PriorityQueue<>(26, cmp);
                for (int i = 0; i < 26; i++) {
                    if (freq[i] != 0) {
                        pq.offer((char)('A' + i));
                    }
                }

            }
        }
        return intervals;
    }

    //79
    public boolean exist(char[][] board, String word) {
        if (board == null || board.length == 0 || board[0].length == 0 || word.length() == 0) {
            return false;
        }
        int m = board.length, n = board[0].length;
        for (int i = 0; i < m; i++) {
            for (int j = 0; j < n; j++) {
                boolean[][] visited = new boolean[m][n];
                if (dfs79(board, word, i, j, 0, visited)) {
                    return true;
                }
            }
        }
        return false;
    }

    private boolean dfs79(char[][] board, String word, int i, int j, int k, boolean[][] visited) {
        if (k == word.length()) {
            return true;
        }
        if (i < 0 || i >= board.length || j < 0 || j >= board[0].length || board[i][j] != word.charAt(k) || visited[i][j]) {
            return false;
        }
        visited[i][j] = true;
        return dfs79(board, word, i + 1, j, k + 1, visited) || dfs79(board, word, i - 1, j, k + 1, visited)
                || dfs79(board, word, i, j + 1, k + 1, visited) || dfs79(board, word, i, j - 1, k + 1, visited);
    }

    //lintcode 57
    public List<List<Integer>> threeSum(int[] numbers) {
        List<List<Integer>> res = new ArrayList<>();
        if (numbers == null || numbers.length < 3) {
            return res;
        }
        Arrays.sort(numbers);
        for (int i = 0; i < numbers.length; i++) {
            if (i > 0 && numbers[i] == numbers[i - 1]) {
                continue;
            }
            int j = i + 1, k = numbers.length - 1;
            while (j < k) {
                int sum = numbers[j] + numbers[k];
                //2sum
                if (sum == -numbers[i]) {
                    List<Integer> lis = new ArrayList<>();
                    lis.add(numbers[i]);
                    lis.add(numbers[j]);
                    lis.add(numbers[k]);
                    res.add(lis);
                    while (j + 1 < numbers.length && numbers[j + 1] == numbers[j]) {
                        j++;
                    }
                    while (k - 1 >= 0 && numbers[k - 1] == numbers[k]) {
                        k--;
                    }
                    j++;
                    k--;
                } else if (sum < -numbers[i]) {
                    j++;
                } else if (sum > -numbers[i]) {
                    k--;
                }
            }
        }
        return res;
    }

    //lintcoed 918
    public int threeSumSmaller(int[] nums, int target) {
        if (nums == null || nums.length < 3) {
            return 0;
        }
        Arrays.sort(nums);
        int count = 0, n = nums.length;
        for (int i = 0; i < n; i++) {
            int j = i + 1, k = n - 1;
            while (j < k) {
                int sum = nums[j] + nums[k];
                //2sum
                if (sum < target - nums[i]) {
                    //System.out.println(nums[i] + " " + nums[j] + " " + nums[k]);
                    count += k - j;
                    j++;
                } else {
                    //sum >= target - nums[i]
                    k--;
                }
            }
        }
        return count;
    }

    //16
    public int threeSumClosest(int[] nums, int target) {
        if (nums == null || nums.length < 3) {
            return 0;
        }
        Arrays.sort(nums);
        int res = 0, n = nums.length, dif = Integer.MAX_VALUE;
        for (int i = 0; i < n; i++) {
            int j = i + 1, k = n - 1;
            while (j < k) {
                int sum = nums[i] + nums[j] + nums[k];
                if (sum == target) {
                    return target;
                }
                if (Math.abs(sum - target) < dif) {
                    res = sum;
                    dif = Math.abs(sum - target);
                }
                if (sum < target) {
                    j++;
                } else {
                    k--;
                }
            }
        }
        return res;
    }

    //67
    public String addBinary(String a, String b) {
        StringBuilder sb = new StringBuilder();
        StringBuilder sba = new StringBuilder(a);
        sba = sba.reverse();
        StringBuilder sbb = new StringBuilder(b);
        sbb = sbb.reverse();
        int nextBit = 0, i = 0;
        for (; i < Math.max(a.length(), b.length()); i++) {
            int x = i < a.length() ? sba.charAt(i) - '0' : 0, y = i < b.length() ? sbb.charAt(i) - '0' : 0;
            int sum = x + y + nextBit;
            switch(sum) {
                case 0: sb.append('0'); nextBit = 0; break;
                case 1: sb.append('1'); nextBit = 0; break;
                case 2: sb.append('0'); nextBit = 1; break;
                case 3: sb.append('1'); nextBit = 1; break;
                default: return "";
            }
        }
        if (nextBit == 1) {
            sb.append('1');
        }
        return sb.reverse().toString();
    }
    
    public static void main(String[] args) {
        Number nb = new Number();
        //System.out.println(nb.coinChange(coins, 12));
        /*String[][] equations = {{"a","b"},{"b","c"}};
        double[] values = {2.0, 3.0};
        String[][] queries = {{"a","c"},{"b","c"},{"a","e"},{"a","a"},{"a","x"}};
        double[] res = nb.calcEquation(equations, values, queries);
        for (double x : res) {
            System.out.println(x + " ");
        }*/
        char[] tasks = {'A', 'A','A', 'A', 'B', 'B', 'B', 'C', 'C', 'D', 'E'};
        System.out.println(nb.leastInterval(tasks, 3));
    }
}
