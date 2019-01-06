public class Bitwise {
    //No.191
    public static int hammingWeight(int n) {
        String binary = Integer.toBinaryString(n);
        int weight = 0;
        for (int i = 0; i < binary.length(); i++) {
            if (binary.charAt(i) == '1') {
                weight++;
            }
        }
        return weight;
    }

    //lintcode 236
    public int swapOddEvenBits(int x) {
        int num = 1;
        int odd = 0, even = 0;
        for (int i = 0; i < 32; i++) {

            if (i % 2 == 0) {
                even += x & num;
            } else {
                odd += x & num;
            }
            num = num << 1;
        }

        return (odd >>> 1) + (even << 1);
    }

    //lintcode 1
    public int aplusb(int a, int b) {
        return (a ^ b) + ((a & b) << 1);
    }

    public static void main(String[] args) {
        System.out.println(hammingWeight(11));
    }
}
