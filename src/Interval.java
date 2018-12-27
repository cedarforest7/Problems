import java.util.*;

public class Interval {
    int start;
    int end;
    Interval() {
        start = 0; end = 0;
    }
    Interval(int s, int e) {
        start = s; end = e;
    }
    //No.56
    //could be faster with:
    //int[] starts = new int[len];
    //int[] ends = new int[len];
    public List<Interval> merge(List<Interval> intervals) {
        LinkedList<Interval> res = new LinkedList<>();
        if (intervals == null || intervals.size() == 0) {
            return res;
        }
        int len = intervals.size();
        Interval[] intv = new Interval[len];
        for (int i = 0; i < len; i++) {
            intv[i] = intervals.get(i);
        }
        Arrays.sort(intv, new Comparator<Interval>() {
            @Override
            public int compare(Interval o1, Interval o2) {
                return o1.start - o2.start;
            }
        });

        res.add(intv[0]);
        for (int i = 1; i < len; i++) {
            //merge with the previous interval
            int last = res.peekLast().end;
            if (intv[i].start > last) {
                res.add(intv[i]);
            } else if (intv[i].end > last){
                int st = res.peekLast().start;
                res.removeLast();
                res.add(new Interval(st, intv[i].end));
            }
        }
        return res;
    }

    //No.253
    public int minMeetingRooms(Interval[] intervals) {
        if (intervals == null ) {
            return 0;
        }
        int len = intervals.length;
        if (len < 2) {
            return len;
        }

        Arrays.sort(intervals, new Comparator<Interval>() {
            @Override
            public int compare(Interval o1, Interval o2) {
                return o1.start - o2.start;
            }
        });

        //List<Integer> ends = new ArrayList<>();

        PriorityQueue<Integer> ends = new PriorityQueue<>();

        ends.add(intervals[0].end);
        for (int i = 1; i < len; i++) {
            Interval cur = intervals[i];
            if (ends.peek() <= cur.start) {
                ends.poll();
            }
            ends.add(cur.end);
        }
        return ends.size();
    }

    //lintcode 839
    public static List<Interval> mergeTwoInterval(List<Interval> list1, List<Interval> list2) {
        if (list1 == null && list2 == null) {
            return null;
        }
        List<Interval> res = new ArrayList<>();
        int p = 0, q = 0;
        while (p < list1.size() && q < list2.size()) {
            Interval i = list1.get(p);
            Interval j = list2.get(q);
            if (compare(i, j) <= 0) {
                //i <= j
                addInterval(res, i);
                p++;
            } else {
                addInterval(res, j);
                q++;
            }
        }
        while (p < list1.size()) {
            addInterval(res, list1.get(p));
            p++;
        }
        while (q < list2.size()) {
            addInterval(res, list2.get(q));
            q++;
        }
        return res;
    }

    private static void addInterval(List<Interval> lis, Interval i) {
        if (lis.isEmpty()) {
            lis.add(i);
            return;
        }
        Interval last = lis.get(lis.size() - 1);
        if (last.end >= i.start) {
            if (last.end < i.end) {
                lis.set(lis.size() - 1, new Interval(last.start, i.end));
            }
        } else {
            lis.add(i);
        }
    }

    private static int compare (Interval i, Interval j) {
        if (i.start != j.start) {
            return i.start - j.start;
        }
        return i.end - j.end;
    }

    //lintcode 577
    public List<Interval> mergeKSortedIntervalLists(List<List<Interval>> intervals) {

        List<Interval> res = new ArrayList<>();
        if (intervals == null || intervals.size() == 0) {
            return res;
        }
        res = mergeLists(intervals);
        mergeIntervals(res);
        return res;
    }

    private List<Interval> mergeLists(List<List<Interval>> intervals) {
        Comparator<Interval> cmp = new Comparator<Interval>(){
            @Override
            public int compare (Interval o1, Interval o2) {
                if (o1.start != o2.start) {
                    return o1.start - o2.start;
                }
                return o1.end - o2.end;
            }
        };

        PriorityQueue<Interval> pq = new PriorityQueue<>(intervals.size(), cmp);
        int[] pointer = new int[intervals.size()];
        Map<Interval, Integer> index = new HashMap<>();
        int i = 0;
        //initialization
        for (List<Interval> lis : intervals) {
            if (lis == null || lis.size() == 0) {
                i++;
                continue;
            }
            Interval temp = lis.get(0);
            pq.offer(temp);
            index.put(temp, i);
            pointer[i]++;
            i++;
        }
        List<Interval> res = new ArrayList<>();
        while (!pq.isEmpty()) {
            Interval temp = pq.poll();
            res.add(temp);
            int lisIndex = index.get(temp);
            index.remove(temp);
            //System.out.println(pointer[lisIndex] + " " + intervals.get(lisIndex).size());
            if (pointer[lisIndex] < intervals.get(lisIndex).size()) {
                Interval next = intervals.get(lisIndex).get(pointer[lisIndex]);
                pq.offer(next);
                pointer[lisIndex]++;
                index.put(next, lisIndex);
            }
        }
        return res;
    }

    private void mergeIntervals(List<Interval> lis) {
        int i = 0;
        while (i < lis.size() - 1) {
            Interval cur = lis.get(i);
            Interval next = lis.get(i + 1);
            if (next.start <= cur.end) {
                if (next.end > cur.end) {
                    cur.end = next.end;
                }
                lis.remove(i + 1);
                continue;
            }
            i++;
        }
    }

    public static void main(String[] args) {
        List<Interval> l1 = new ArrayList<>();
        l1.add(new Interval(1,2));
        l1.add(new Interval(3,4));

        List<Interval> l2 = new ArrayList<>();
        l2.add(new Interval(2,3));
        l2.add(new Interval(5,6));
        List<Interval> l3 = mergeTwoInterval(l1, l2);
        for (Interval itv : l3) {
            System.out.println(itv.start + " " + itv.end);
        }

    }

}
