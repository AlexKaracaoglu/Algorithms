/*
 * Alex Karacaoglu
 * Algorithms
 * Homework 3
 * zombieCluster
 */

import java.util.*;

public class Zombies {

    /*
     * Complete the 'zombieCluster' function below.
     *
     * The function is expected to return an INTEGER.
     * The function accepts STRING_ARRAY zombies as parameter.
     */

    public static int zombieCluster(List<String> zombies) {
        int result = 0;
        Set<Integer> visited = new HashSet<>();
        for (int i = 0; i < zombies.size(); i++) {
            if (!visited.contains(i)) {
                calculate(visited, zombies, i);
                result++;
            }
        }
        return result;
    }

    private static void calculate(Set<Integer> visited, List<String> zombies, int i) {
        List<Integer> stuffToVisit = getOnes(zombies.get(i));
        for (int item: stuffToVisit) {
            if (!visited.contains(item)) {
                visited.add(item);
                calculate(visited, zombies, item);
            }
        }
    }

    private static List<Integer> getOnes(String zombie) {
        List<Integer> a = new ArrayList<>();
        for (int i = 0; i < zombie.length(); i++) {
            if (zombie.charAt(i) == '1') {
                a.add(i);
            }
        }
        return a;
    }

    public static void main(String[] args) {
        List<String> a = new ArrayList<>();
//        a.add("1100");
//        a.add("1110");
//        a.add("0110");
//        a.add("0001");

        a.add("11110");
        a.add("11110");
        a.add("11110");
        a.add("11110");
        a.add("00001");
        System.out.println(zombieCluster(a));
    }
}
