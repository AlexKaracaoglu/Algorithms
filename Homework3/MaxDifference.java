/*
 * Alex Karacaoglu
 * Algorithms
 * Homework 3
 * maxDifference
 */

public class MaxDifference {

    // Complete the maxDifference function below.
    static int maxDifference(int[] a) {
        int[] b = calculateMins(a);
        int result = 0;
        for (int i = 1; i < a.length; i ++) {
            if (a[i] - b[i-1] > result) {
                result = a[i] - b[i-1];
            }
        }
        if (result == 0) {
            return -1;
        }
        return result;
    }

    private static int[] calculateMins(int[] a) {
        int b[] = new int[a.length - 1];
        b[0] = a[0];
        for (int i = 1; i <b.length; i++) {
            b[i] = Math.min(b[i-1], a[i]);
        }
        return b;
    }

    public static void main(String[] args) {
        int[] a = new int[]{1,2,3,4,5};
	    System.out.println(maxDifference(a));
    }
}
