/*
    Alex Karacaoglu
    Algorithms-HW2
    Distinct Pairs
 */

import java.util.HashSet;
import java.util.Set;

public class DistinctPairs {

    // Complete the numberOfPairs function below.
    static int numberOfPairs(int[] a, long k) {
        int resultCount = 0;
        Set<Integer> items = new HashSet<>();
        Set<Integer> itemsThatWorked = new HashSet<>();
        for (int item: a) {
            int min = Math.min(item, (int)k - item);
            if ((items.contains((int)k - item) && (!itemsThatWorked.contains(min)))) {
                itemsThatWorked.add(min);
                resultCount++;
            }
            else {
                items.add(item);
            }
        }
        return resultCount;
    }

    public static void main(String[] args) {
        int[] b = new int[]{1,2,3,6,7,8,9,1};
        System.out.println(numberOfPairs(b, 10));
    }
}
