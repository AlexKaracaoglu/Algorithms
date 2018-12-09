/*
 * Alex Karacaoglu
 * Algorithms
 * Homework 3
 * sortingNetwork
 */

import java.util.*;

public class SortingNetwork {

    public static void main(String args[] ) throws Exception {
        Scanner sc = new Scanner(System.in);
        int n = sc.nextInt();
        sortingNetwork(n);
    }

    private static void sortingNetwork(int n) {
        List<List<Integer>> result = printOneList(n);
        for (int i = 2; Math.pow(2, i) <= n; i++) {
            String toggle = "down";
            int itemsInEachBigBox = (int)Math.pow(2, i);
            int numberOfGroups = n / itemsInEachBigBox;
            List<List<Integer>> newResults = new ArrayList<>();
            for (int j = 0; j < numberOfGroups; j++) {
                toggle = toggleToggle(toggle);
                int min = j * itemsInEachBigBox;
                int max = min + itemsInEachBigBox - 1;
                int difference = (max - min + 1) / 2;
                calculateBox(min, max, difference, newResults, toggle);
            }
            if ((int)Math.pow(2, i) == n) {
                printFinal(result);
            }
            else {
                printSetUp(result, i);
            }
            result.addAll(0, newResults);
        }
    }

    private static void printFinal(List<List<Integer>> result) {
        for (List<Integer> item: result) {
            int min = item.get(0);
            int max = item.get(1);
            printLine(min, max, "up");
        }
    }

    private static void printSetUp(List<List<Integer>> result, int i) {
        i = (int)Math.pow(2,i - 1);
        int count = i;
        String toggle = "up";
        for (List<Integer> item: result) {
            if (count == 0) {
                toggle = toggleToggle(toggle);
                count = i;
            }
            int min = item.get(0);
            int max = item.get(1);
            printLine(min, max, toggle);
            count--;
        }
    }

    private static String toggleToggle(String toggle) {
        if (toggle.equals("up")) {
            return "down";
        }
        return "up";
    }

    private static void calculateBox(int min, int max, int difference, List<List<Integer>> newResult, String toggle) {
        int top = min + difference;
        while (top <= max) {
            List<Integer> minMax = new ArrayList<>();
            minMax.add(min);
            minMax.add(top);
            int size = newResult.size();
            newResult.add(size, minMax);
            printLine(min, top, toggle);
            min++;
            top++;
        }
    }

    private static List<List<Integer>> printOneList(int n) {
        List<List<Integer>> result = new ArrayList<>();
        int min = 0;
        int max = n-1;
        while (min <= max) {
            List<Integer> minMax = new ArrayList<>();
            minMax.add(min);
            minMax.add(min + 1);
            int size = result.size();
            result.add(size, minMax);
            min = min + 2;
        }
        printSetUp(result, 1);
        return result;
    }


    private static void printLine(int min, int max, String toggle) {
        String aMin = "a[" + min + "]";
        String aMax = "a[" + max + "]";
        if (toggle.equals("up")) {
            System.out.println("if(" + aMin + ">" + aMax +"){"+aMin+"="+aMin+"^"+aMax+";"+aMax+"="+aMin+"^"+aMax+";"+aMin+"="+aMin+"^"+aMax+";}");
        }
        else {
            System.out.println("if(" + aMax + ">" + aMin +"){"+aMax+"="+aMax+"^"+aMin+";"+aMin+"="+aMax+"^"+aMin+";"+aMax+"="+aMax+"^"+aMin+";}");
        }
    }
}
