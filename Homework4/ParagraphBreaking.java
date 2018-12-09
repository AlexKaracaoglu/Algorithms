/*
    Alex Karacaoglu
    Homework 4
    #1 - Dynamic Programming Paragraph Breaking
 */

import java.util.*;

public class ParagraphBreaking {

    public static void main(String args[] ) throws Exception {
        Scanner scanner = new Scanner(System.in);
        List<Double> a = new ArrayList<>();
        while(scanner.hasNext()) {
            double stuff = scanner.next().length()+1;
            a.add(stuff);
        }

        Double[] last = getLast(a);
        Double[] first = getFirst(a);

        Double[][] phi = createBreakChart(a);

        Integer[][] minIndexes = new Integer[a.size()-2][a.size()];
        int row = a.size()-3;
        Double[] psi = psi(last, phi, minIndexes, row);
        row--;

        int iterations = a.size() - 3;
        for (int i = 0; i < iterations; i++) {
            psi = psi(psi, phi, minIndexes, row);
            row--;
        }

        int index = finalMinIndex(psi, first);
        System.out.println(index + 1);
        Set<Integer> indices = new HashSet<>();
        indices.add(index + 1);
        for (Integer[] indexList: minIndexes) {
            if (indices.contains(indexList[index] + 1)) {
                break;
            }
            System.out.println(indexList[index] + 1);
            index = indexList[index];
            indices.add(index + 1);
        }

    }

    private static int finalMinIndex(Double[] psi, Double[] first) {
        double min = Double.POSITIVE_INFINITY;
        int mindex = 0;
        for (int i = 0; i < psi.length; i++) {
            double value = psi[i] + first[i];
            if (value <= min) {
                mindex = i;
                min = value;
            }
        }
        return mindex;
    }

    private static Double[] psi(Double[] a, Double[][] b, Integer[][] minIndexes, int row) {
        Double[] psi = new Double[a.length];
        for (int i = 0; i < b.length; i++) {
            double min = Double.POSITIVE_INFINITY;
            int minValueIndex = 0;
            for (int j = 0; j < a.length; j++) {
                double value = a[j] + b[i][j];
                if (value <= min) {
                    minValueIndex = j;
                    min = value;
                }
            }
            minIndexes[row][i] = minValueIndex;
            psi[i] = min;
        }
        return psi;
    }

    private static Double[][] createBreakChart(List<Double> a) {
        Double[][] breakChart = new Double[a.size()][a.size()];
        for (int i = 0; i < a.size(); i++) {
            for (int j = 0; j < a.size(); j++) {
                breakChart[i][j] = (sum(a, i+1,j+1));
            }
        }
        return breakChart;
    }

    private static Double[] getLast(List<Double> a) {
        Double[] last = new Double[a.size()];
        for (int i = 0; i < a.size(); i++) {
            last[i] = (sum(a, i + 1, a.size()));
        }
        return last;
    }

    private static Double[] getFirst(List<Double> a) {
        Double[] first = new Double[a.size()];
        for (int i = 0; i < a.size(); i++) {
            first[i] = (sum(a, 0, i + 1));
        }
        return first;
    }

    private static Double sum(List<Double> a, int start, int end) {
        double sum = 0;
        if (end < start) {
            return Double.POSITIVE_INFINITY;
        }
        else if (end == start) {
            return 0.;
        }
        else {
            for (int j = start; j < end; j++) {
                sum = sum + a.get(j);
            }
            if (sum > 51) {
                return Double.POSITIVE_INFINITY;
            } else {
                return Math.sqrt(51 - sum);
            }
        }
    }

}

