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
}
