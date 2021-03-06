import java.util.List;

public class Helper {
    public static void printList(List<String> lis) {
        for (String x : lis) {
            System.out.print(x + " ");
        }
        System.out.print("\n");
    }

    public static void printListofListString(List<List<String>> lis) {
        for (List<String> l : lis) {
            for (String x : l) {
                System.out.print(x + " ");
            }
            System.out.print("\n");
        }
    }

    public static void printListofListInt(List<List<Integer>> lis) {
        for (List<Integer> l : lis) {
            for (int x : l) {
                System.out.print(x + " ");
            }
            System.out.print("\n");
        }
    }
}
