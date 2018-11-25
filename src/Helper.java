import java.util.List;

public class Helper {
    public static void printListofList(List<List<String>> lis) {
        for (List<String> l : lis) {
            for (String x : l) {
                System.out.print(x + " ");
            }
            System.out.print("\n");
        }
    }
}
