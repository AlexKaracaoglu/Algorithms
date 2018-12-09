/*
    Alex Karacaoglu
    Algorithms-HW2
    Non-Recursive Merge Sort
 */

public class NonRecursiveMergeSort {

    /*
     * Complete the 'merge_sort' function below.
     *
     * The function is expected to return an INTEGER_ARRAY.
     * The function accepts INTEGER_ARRAY arr as parameter.
     */

    public static int [] merge_sort(int[] arr) {
        int i = 1;
        while (i < arr.length) {
            int groups = arr.length/i;
            for (int j = 0; j < Math.ceil((float)groups/2); j++) {
                int startIndexOnLeft = 2*j*i;
                int endIndexOnLeft = calculateEndIndex(arr.length, startIndexOnLeft, i);
                int startIndexOnRight = Math.min(2*j*i+i, arr.length - 1);
                int endIndexOnRight = calculateEndIndex(arr.length, startIndexOnRight, i);
                updateArray(arr,
                        startIndexOnLeft,
                        endIndexOnLeft,
                        startIndexOnRight,
                        endIndexOnRight);
            }
            i = i * 2;
        }
        return arr;
    }

    private static int calculateEndIndex(int length, int startIndex, int i) {
        if (startIndex + i < length) {
            return startIndex + i - 1;
        }
        return length - 1;
    }

    private static void updateArray(int[] arr, int indexLeft, int indexEndLeft, int indexRight, int indexEndRight) {
        int numberOfElements = indexEndRight - indexLeft + 1;
        int[] workArray = new int[numberOfElements];
        int startIndexLeft = indexLeft;
        for (int i = 0; i < workArray.length; i++) {
            if (indexLeft > indexEndLeft) {
                workArray[i] = arr[indexRight];
                indexRight++;
            }
            else if (indexRight > indexEndRight) {
                workArray[i] = arr[indexLeft];
                indexLeft++;
            }
            else if (arr[indexLeft] <= arr[indexRight]) {
                workArray[i] = arr[indexLeft];
                indexLeft++;
            }
            else {
                workArray[i] = arr[indexRight];
                indexRight++;
            }
        }
        insertWorkArrayIntoArr(workArray, arr, startIndexLeft);
    }

    private static void insertWorkArrayIntoArr(int[] workArray, int[] arr, int index) {
        for (int item: workArray) {
            arr[index] = item;
            index++;
        }
    }

    public static void main(String[] args) {
        int[] a = new int[]{5,0,4,2,-1, 69, 84,-99, 12};
        merge_sort(a);
    }

}
