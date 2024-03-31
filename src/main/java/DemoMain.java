import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;

public class DemoMain {
    public static void main(String[] args) {
        List<Integer> numbers = new ArrayList<>();
        numbers.add(1);
        numbers.add(2);
        numbers.add(3);
        System.out.println(numbers.getClass().getName()); // ArrayList

        HashSet<Boolean> booleans = new HashSet<>();
        booleans.add(true);
        booleans.add(false);
        System.out.println(booleans.getClass().getName()); // HashSet

        // This is type erasure - backward compatibility in Java 5, when Generics was introduced
        // So that individual types in code & runtime are not different
    }
}
