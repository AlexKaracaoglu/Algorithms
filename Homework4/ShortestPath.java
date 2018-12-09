/*
    Alex Karacaoglu
    Homework 4
    #2 - Dynamic Programming Shortest Path
 */

import java.util.*;

public class ShortestPath {

    public static void main(String args[] ) throws Exception {
        Scanner scanner = new Scanner(System.in);
        Map<Double, List<List<Double>>> graph = new HashMap<>();
        while (scanner.hasNext()) {
            List<Double> endAndWeight = new ArrayList<>();
            List<Double> startAndWeight = new ArrayList<>();
            Double startNode = Double.valueOf(scanner.next());
            Double endNode = Double.valueOf(scanner.next());
            Double weight = Double.valueOf(scanner.next());
            endAndWeight.add(endNode);
            endAndWeight.add(weight);
            startAndWeight.add(startNode);
            startAndWeight.add(weight);
            if (!graph.containsKey(startNode)) {
                List<List<Double>> value = new ArrayList<>();
                value.add(endAndWeight);
                graph.put(startNode, value);
                value = new ArrayList<>();
                value.add(startAndWeight);
                graph.put(endNode, value);
            } else {
                List<List<Double>> value = graph.get(startNode);
                value.add(endAndWeight);
                graph.put(startNode, value);
                if (graph.containsKey(endNode)) {
                    value = graph.get(endNode);
                    value.add(startAndWeight);
                    graph.put(endNode, value);
                }
                else {
                    value = new ArrayList<>();
                    value.add(startAndWeight);
                    graph.put(endNode, value);
                }
            }
        }
        List<List<Double>> starting = graph.get(1.);
        List<Double> stayInPlace = new ArrayList<>();
        stayInPlace.add(1.);
        stayInPlace.add(0.);
        starting.add(stayInPlace);
        graph.put(1., starting);
        List<List<Integer>> minIndexes = new ArrayList<>();
        Integer[] minIndex = new Integer[graph.size()];
        Double[] psi = getLast(graph, minIndex);
        List<Integer> mindex = new ArrayList<>(Arrays.asList(minIndex));
        minIndexes.add(mindex);
        int iterations = graph.size() - 3;
        for (int i = 0; i < iterations; i++) {
            minIndex = new Integer[graph.size()];
            psi = psi(graph, psi, minIndex);
            mindex = new ArrayList<>(Arrays.asList(minIndex));
            minIndexes.add(0, mindex);
        }
        int index = finalMinIndex(graph, psi);
        List<Integer> results = new ArrayList<>();
        results.add(index);
        for (List<Integer> indexList: minIndexes) {
            index = indexList.get(index-1);
            results.add(index);
        }
        Set<Integer> resultsMinusDuplicates = new LinkedHashSet<>(results);
        for (Integer item: resultsMinusDuplicates) {
            System.out.println(item);
        }


    }

    private static int finalMinIndex(Map<Double,List<List<Double>>> graph, Double[] psi) {
        int mindex = 0;
        Double min = Double.POSITIVE_INFINITY;
        List<List<Double>> startNbrs = graph.get(1.);
        for (int i = 0; i < startNbrs.size(); i++) {
            int nbrVertex = startNbrs.get(i).get(0).intValue();
            Double newWeight = psi[nbrVertex-1] + startNbrs.get(i).get(1);
            if (newWeight < min) {
                min = newWeight;
                mindex = nbrVertex;
            }
        }
        return mindex;
    }

    private static Double[] psi(Map<Double,List<List<Double>>> graph, Double[] psi, Integer[] minIndex) {
        Double[] result = new Double[graph.size()];
        for (int vertex = 0; vertex < graph.size(); vertex++) {
            List<List<Double>> nbrs = graph.get((double)vertex+1);
            double min = Double.POSITIVE_INFINITY;
            int mindex = 0;
            for (List<Double> neighborsAndWeight: nbrs) {
                int nbrVertex = neighborsAndWeight.get(0).intValue();
                Double nbrWeight = neighborsAndWeight.get(1);
                Double previousWeight = psi[nbrVertex-1];
                Double newWeight = previousWeight + nbrWeight;
                if (newWeight < min) {
                    min = newWeight;
                    mindex = nbrVertex;
                }
            }
            result[vertex] = min;
            minIndex[vertex] = mindex;
        }
        return result;
    }

    private static Double[] getLast(Map<Double, List<List<Double>>> graph, Integer[] minIndex) {
        Double[] last = new Double[graph.size()];
        Arrays.fill(last, Double.POSITIVE_INFINITY);
        for (List<Double> lastMove: graph.get(2.)) {
            last[lastMove.get(0).intValue() - 1] = lastMove.get(1);
            minIndex[lastMove.get(0).intValue() - 1] = 2;
        }
        return last;
    }

}
