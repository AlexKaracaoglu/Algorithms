/*
 * Alex Karacaoglu
 * Algorithms Homework 1
 * Professor: Bento
 * Custom Sort
 */

import java.util.*;

public class CustomSort {

    /*
     * create the frequencyMap and itemsWithSameFrequencyMap
     * get the list of all frequencies and sort them
     * iterate over these sorted frequencies and get their items from the itemsWithSameFrequency
     * print each item its frequency number of times
     */

    static void customSort(int[] arr) {
        Map<Integer, Integer>  frequencyMap = createFrequencyMap(arr);
        Map<Integer, List<Integer>> itemsWithSameFrequencyMap = createItemsWithSameFrequencyMap(frequencyMap);

        List<Integer> sortedFrequencies = new ArrayList<>(itemsWithSameFrequencyMap.keySet());
        Collections.sort(sortedFrequencies);

        for (Integer frequency: sortedFrequencies) {
            List<Integer> items = itemsWithSameFrequencyMap.get(frequency);
            printFrequencyNumberOfEachItem(frequency, items);
        }
    }

    // create a map that is key=item and value=item frequency
    private static Map<Integer,Integer> createFrequencyMap(int[] arr) {
        Map<Integer,Integer> frequencyMap = new HashMap<>();
        for (int item: arr) {
            addItemToFrequencyMap(item, frequencyMap);
        }
        return frequencyMap;
    }

    private static void addItemToFrequencyMap(int item, Map<Integer,Integer> frequencies) {
        if (frequencies.containsKey(item)) {
            int updatedValue = frequencies.get(item) + 1;
            frequencies.put(item, updatedValue);
        }
        else {
            frequencies.put(item, 1);
        }
    }

    // create map with key=frequency and value=list of items with that frequency
    private static Map<Integer, List<Integer>> createItemsWithSameFrequencyMap(Map<Integer, Integer> frequencyMap) {
        Map<Integer, List<Integer>> itemsWithSameFrequencyMap = new HashMap<>();
        for (Integer keyInFrequencyMap: frequencyMap.keySet()) {
            int frequency = frequencyMap.get(keyInFrequencyMap);
            addItemToSameFrequencyMap(itemsWithSameFrequencyMap, keyInFrequencyMap, frequency);
        }
        return itemsWithSameFrequencyMap;
    }

    // add item to same frequency map, if key exists then get its value, sort it, and update the map
    private static void addItemToSameFrequencyMap(Map<Integer, List<Integer>> itemsWithSameFrequencyMap, int keyInFrequencyMap, int itemFrequency) {
        if (itemsWithSameFrequencyMap.containsKey(itemFrequency)) {
            List<Integer> itemsWithFrequencyItemFrequency = itemsWithSameFrequencyMap.get(itemFrequency);
            itemsWithFrequencyItemFrequency.add(keyInFrequencyMap);
            Collections.sort(itemsWithFrequencyItemFrequency);
            itemsWithSameFrequencyMap.put(itemFrequency, itemsWithFrequencyItemFrequency);
        }
        else {
            List<Integer> newListWithFrequencyItemFrequency = new ArrayList<>();
            newListWithFrequencyItemFrequency.add(keyInFrequencyMap);
            itemsWithSameFrequencyMap.put(itemFrequency, newListWithFrequencyItemFrequency);
        }
    }

    private static void printFrequencyNumberOfEachItem(Integer frequency, List<Integer> items) {
        for (Integer item: items) {
            for (int freq = 0; freq < frequency; freq++) {
                System.out.println(item);
            }
        }
    }

    public static void main(String[] args) {
        int[] arr = {4,5,6,5,4,3};
	    customSort(arr);
    }
}
