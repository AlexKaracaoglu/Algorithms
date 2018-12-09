/*
    Alex Karacaoglu
    Algorithms-HW2
    Simple Queries
 */

import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;

public class SimpleQueries {

    // Complete the counts function below.
    static int[] counts(int[] nums, int[] maxes) {
        nums = merge_sort(nums);
        int[] maxesNotSorted = Arrays.copyOf(maxes,maxes.length);
        Map<Integer, Integer> elementAndItemsBelowMap = new HashMap<>();
        maxes = merge_sort(maxes);
        int count = 0;
        int[] counts = new int[maxes.length];
        int maxIndex = 0;
        for (int numsIndex = 0; ((numsIndex < nums.length) && (maxIndex < maxes.length)); numsIndex++) {
            if (nums[numsIndex] <= maxes[maxIndex]) {
                count++;
            }
            else {
                elementAndItemsBelowMap.put(maxes[maxIndex], count);
                maxIndex++;
                numsIndex--;
            }
        }
        if (maxIndex == maxes.length - 1) {
            elementAndItemsBelowMap.put(maxes[maxIndex], count);
        }
        for (int i = 0; i < counts.length; i++) {
            if( elementAndItemsBelowMap.get(maxesNotSorted[i]) == null) {
                counts[i] = nums.length;
            }
            else {
                counts[i] = elementAndItemsBelowMap.get(maxesNotSorted[i]);
            }
        }
        return counts;
    }

    // Below is just my merge_sort from (1) in order to sort arrays

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
//        int[] nums = new int[]{1, 4, 5, 8};
//        int[] maxes = new int[]{2, 4, 8};
//        int[] num = new int[]{23, 4, 2, 12, 18, 20, 16};
//        int[] max = new int[]{13, 22, 29, 1};
//        int[] num = new int[]{23, 4,1, 2, 12, 18, 20, 16, 30, 31, 33, 34};
//        int[] max = new int[]{13, 22, 29, 1};

        int[] nums = new int[]{1,2,3};
        int[] maxes = new int[]{2,3};
        counts(nums, maxes);

    }
}
