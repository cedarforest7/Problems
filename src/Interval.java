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

}
