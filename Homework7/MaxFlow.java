/*
    Alex Karacaoglu
    Homework 7
    Algorithms
    Max Flow
 */

import java.util.*;

public class MaxFlow {

    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);
        List<List<List<Integer>>> graph = new ArrayList<>();
        String line = sc.nextLine();
        String[] firstAndLast = line.split(" ", -1);
        int first = Integer.valueOf(firstAndLast[0]);
        int last = Integer.valueOf(firstAndLast[1]);
        int maxNumber = 0;
        while (sc.hasNextLine()) {
            List<List<Integer>> row = new ArrayList<>();
            line = sc.nextLine();
            String[] verticesAndCapacities = line.split(" ", -1);
            int mid = verticesAndCapacities.length / 2;
            for (int i = 0; i < mid; i++) {
                List<Integer> item = new ArrayList<>();
                item.add(Integer.valueOf(verticesAndCapacities[i]));
                if (Integer.valueOf(verticesAndCapacities[i]) > maxNumber) {
                    maxNumber = Integer.valueOf(verticesAndCapacities[i]);
                }
                item.add(Integer.valueOf(verticesAndCapacities[mid + i + 1]));
                row.add(item);
            }
            graph.add(row);
        }
        int graphSize = graph.size();
        if (maxNumber > graphSize) {
            List<List<Integer>> empty = new ArrayList<>();
            graph.add(empty);
        }
        int[][] residualGraph = createResidual(graph);
        int totalFlow = 0;
        printResidualAndFlow(residualGraph, 0);
        List<Integer> flowAndPath = bfs(residualGraph, first, last);
        while (flowAndPath.get(0) != -1) {
            updateResidual(flowAndPath, residualGraph);
            totalFlow = totalFlow + flowAndPath.get(0);
            printResidualAndFlow(residualGraph, totalFlow);
            flowAndPath = bfs(residualGraph, first, last);
        }
    }

    private static void printResidualAndFlow(int[][] residualGraph, int totalFlow) {
        for (int i = 0; i < residualGraph.length; i++) {
            String vertexString = "";
            String valueString = "";
            for (int j = 0; j < residualGraph.length; j++) {
                if (residualGraph[i][j] > 0) {
                    vertexString = vertexString + (j+1) + " ";
                    valueString = valueString + residualGraph[i][j] + " ";
                }
            }
            String result = vertexString + "- " + valueString;
            if (result.equals("- ")) {
                System.out.println();
            }
            else {
                System.out.println(result.substring(0, result.length() - 1));
            }
        }
        System.out.println(totalFlow);
    }

    private static void updateResidual(List<Integer> flowAndPath, int[][] residual) {
        int flow = flowAndPath.get(0);
        for (int i = 1; i < flowAndPath.size() - 1; i++) {
            residual[flowAndPath.get(i) - 1][flowAndPath.get(i + 1) - 1] = residual[flowAndPath.get(i) - 1][flowAndPath.get(i + 1) - 1] - flow;
            residual[flowAndPath.get(i + 1) - 1][flowAndPath.get(i) - 1] = residual[flowAndPath.get(i + 1) - 1][flowAndPath.get(i) - 1] + flow;
        }
    }

    private static int[][] createResidual(List<List<List<Integer>>> graph) {
        int[][] residualGraph = new int[graph.size()][graph.size()];
        for (int i = 0; i < graph.size(); i++) {
            for (int j = 0; j < graph.size(); j++) {
                residualGraph[i][j] = 0;
            }
        }
        int rowCount = 0;
        for (List<List<Integer>> row: graph) {
            for (List<Integer> item: row) {
                residualGraph[rowCount][item.get(0) - 1] = item.get(1);
            }
            rowCount++;
        }
        return residualGraph;
    }

    private static List<Integer> bfs(int[][] residual, int first, int last) {
        int[] visited = new int[residual.length];
        int[] upStream = new int[residual.length];
        for (int i = 0; i < residual.length; i++) {
            visited[i] = 0;
            upStream[i] = 0;
        }
        Queue<Integer> queue = new LinkedList<>();
        visited[first - 1] = 1;
        queue.add(first);
        int element = first;
        while (!queue.isEmpty()) {
            element = queue.element();
            queue.remove();
            for (int i = 0; i < residual.length; i++) {
                int neighbor = residual[element - 1][i];
                if (visited[i] == 0 && neighbor > 0) {
                    visited[i] = 1;
                    queue.add(i + 1);
                    upStream[i] = element;
                    if (i + 1 == last) {
                        return calculatePathAndFlowWeight(upStream, first, last, residual);
                    }
                }
            }
        }
        List<Integer> noPath = new ArrayList<>();
        noPath.add(-1);
        return noPath;
    }

    private static List<Integer> calculatePathAndFlowWeight(int[] upStream, int first, int last, int[][] residual) {
        List<Integer> flowAndPath = new ArrayList<>();
        int minFlow = 10000;
        int element = last;
        flowAndPath.add(element);
        while (element != first) {
            int upElement = upStream[element - 1];
            if (residual[upElement - 1][element - 1] < minFlow) {
                minFlow = residual[upElement - 1][element - 1];
            }
            element = upElement;
            flowAndPath.add(element);
        }
        Collections.reverse(flowAndPath);
        flowAndPath.add(0, minFlow);
        return flowAndPath;
    }
}
