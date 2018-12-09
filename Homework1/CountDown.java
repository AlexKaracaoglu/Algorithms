/*
 * Alex Karacaoglu
 * Algorithms Homework 1
 * Professor: Bento
 * Count Down
 */

public class CountDown {

    /*
     * Complete the 'count_down' function below.
     *
     * The function accepts following parameters:
     *  1. INTEGER n
     *  2. INTEGER length
     */

    static void count_down(int n, int length) {
        int[] array = createArray(n, length);
        print(array);
        for (int i = 1; i < Math.pow(n+1, length); i++) {
            array = subtractOne(array, n);
            print(array);
        }
    }

    // creates an array of length length with all elements equal to n
    private static int[] createArray(int n, int length) {
        int[] result = new int[length];
        for (int i = 0; i < length; i++) {
            result[i] = n;
        }
        return result;
    }

    // performs a subtraction, starts from right, if sum gets below zero performs a carry over and continues subtraction
    private static int[] subtractOne(int[] array, int n) {
        Boolean isCarryOver = true;
        int i = array.length - 1;
        while (isCarryOver) {
            int sum = --array[i];
            if (sum == -1) {
                array[i] = n;
                i--;
                continue;
            }
            isCarryOver = false;
        }
        return array;
    }

    // print elements in the array
    private static void print(int[] array) {
        String result = Integer.toString(array[0]);
        for (int i = 1; i < array.length; i++) {
            result = result + " " + array[i];
        }
        System.out.println(result);
    }

    public static void main(String[] args) {
        count_down(2,4);
    }
}
