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

    public static void main(String[] args) {
        //System.out.println(hammingWeight(11));
    }
}
