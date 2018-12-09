/*
 * Alex Karacaoglu
 * Algorithms Homework 1
 * Professor: Bento
 * Generate Gift Baskets
*/

import java.util.ArrayList;
import java.util.List;

public class GenerateGiftBaskets {

    /*
     * Complete the 'generate_gift_baskets' function below.
     *
     * The function accepts following parameters:
     *  1. INTEGER k
     *  2. STRING_ARRAY names
     */

    static void generate_gift_baskets(int k, List<String> names) {
        if (k == 1) {
            printKEqualsOneResult(names);
        }
        else {
            generateGiftBasketsLogic(names, k, names.size());
        }
    }

    // prints elements in names list
    private static void printKEqualsOneResult(List<String> names) {
        for (String string: names) {
            System.out.println(string);
        }
    }

    // start from right, creates an all zero list, sets indexWeAreLooking to 1 then generateResults
    private static void generateGiftBasketsLogic(List<String> names, int k, int length) {
        List<Integer> allZeroList = generateAllZeroListOfSizeLength(length);
        for (int indexLookingAt = 0; indexLookingAt <= length - k; indexLookingAt++) {
            List<Integer> scratchWorkList = new ArrayList<>(allZeroList);
            scratchWorkList.set(indexLookingAt, 1);
            int indexStartingToGenerateResults = indexLookingAt + 1;
            generateResults(names, scratchWorkList, indexStartingToGenerateResults, length, k);
        }
    }

    // create a list of length length of all zeros
    private static List<Integer> generateAllZeroListOfSizeLength(int length) {
        List<Integer> allZeroList = new ArrayList<>();
        for (int i = 0; i < length; i++) {
            allZeroList.add(0);
        }
        return allZeroList;
    }

    /*
     * for each iteration, addOne to the scratchWorkList
     * get items at indexes where ones are in scratchWorkList
     * if the number of items we got from names is k
     * then format and print the items we got from names
    */
    private static void generateResults(List<String> names, List<Integer> scratchWorkList, int indexStartingToGenerateResults, int length, int k) {
        for (int j = 0; j < Math.pow(2,length - indexStartingToGenerateResults); j++) {
            List<String> namesResults = addOneFromLeftOfScratchListCreateListOfOnesIndexesAndGetThoseFromNames(scratchWorkList, indexStartingToGenerateResults, names);
            int numberOfItemsWeGotFromNames = namesResults.size();
            if (numberOfItemsWeGotFromNames == k) {
                formatResultAndPrint(namesResults);
            }
        }
    }

    /*
     * put names.get(scratchWorkInitialIndex - 1) into our itemsFromNamesWhereScratchListIndexIsOne
     * add one to our scratchWorkList at scratchWorkInitialIndex, if sum is a 2, make it a 0 and carry over a 1
     * if see a 1 at scratchListIndex i, get the element at index i from names and add it to itemsFromNamesWhereScratchListIndexIsOne
     * continue simplifying the addition with the carry over and keep adding to itemsFromNamesWhereScratchListIndexIsOne if see a 1
     * return itemsFromNamesWhereScratchListIndexIsOne
    */
    private static List<String> addOneFromLeftOfScratchListCreateListOfOnesIndexesAndGetThoseFromNames(List<Integer> scratchWorkList, int scratchListIndex, List<String> names) {
        List<String> itemsFromNamesWhereScratchListIndexIsOne = new ArrayList<>();
        int scratchWorkInitialIndex = scratchListIndex;
        itemsFromNamesWhereScratchListIndexIsOne.add(names.get(scratchWorkInitialIndex - 1));
        int additionCarryOverAmount = 0;
        int sum;
        while (scratchListIndex < scratchWorkList.size()) {
            if (scratchWorkInitialIndex == scratchListIndex) {
                sum = scratchWorkList.get(scratchWorkInitialIndex) + 1;
            }
            else {
                sum = scratchWorkList.get(scratchListIndex) + additionCarryOverAmount;
            }
            scratchWorkList.set(scratchListIndex, sum);
            if (sum == 2) {
                scratchWorkList.set(scratchListIndex,0);
                additionCarryOverAmount = 1;
            }
            else {
                additionCarryOverAmount = 0;
            }
            if (scratchWorkList.get(scratchListIndex) == 1) {
                itemsFromNamesWhereScratchListIndexIsOne.add(names.get(scratchListIndex));
            }
            scratchListIndex++;
        }
        return  itemsFromNamesWhereScratchListIndexIsOne;
    }

    // print the items to the terminal formatted as desired
    private static void formatResultAndPrint(List<String> namesResultsWithOnesIndexes) {
        String result = "";
        for (int index = 0; index < namesResultsWithOnesIndexes.size(); index++) {
            if (index == 0) {
                result = result + namesResultsWithOnesIndexes.get(0);
            }
            else {
                result = result + " " + namesResultsWithOnesIndexes.get(index);
            }
        }
        System.out.println(result);
    }

    public static void main(String[] args) {
	    List<String> names = new ArrayList<>();
        names.add("car");
        names.add("ipod");
        names.add("orange");
        names.add("hotel");
        generate_gift_baskets(2, names);
    }
}
