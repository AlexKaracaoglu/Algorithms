/*
    Alex Karacaoglu
    Algorithms
    Homework 5
    1 - Image Processing, Blur Filter
 */

import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class Blur {

    public static void main(String[] args) {

        Scanner sc = new Scanner(System.in);
        List<List<List<Double>>> image = new ArrayList<>();
        while (sc.hasNextLine()) {
            List<List<Double>> row = new ArrayList<>();
            String rowString = sc.next();
            String[] rowArray = rowString.split(",", -1);
            for (String realStuff : rowArray) {
                List<Double> item = new ArrayList<>();
                item.add(Double.valueOf(realStuff));
                item.add(0.);
                row.add(item);
            }
            image.add(row);
        }

        // 1. Calculate R
        List<List<List<Double>>> R = new ArrayList<>();
        for (List<List<Double>> row: image) {
            R.add(fastFourierTransform(row));
        }

        // 2. Calculate T
        transpose(R);
        List<List<List<Double>>> T = new ArrayList<>();
        for (List<List<Double>> row: R) {
            T.add(fastFourierTransform(row));
        }
        transpose(T);

        // 4. Calculate P
        int M = T.size();
        int N = T.size();
        for (int i = 0; i < T.size() / 2; i++) {
            for (int j = 0; j <T.size() / 2; j++) {
                if (!closeToACorner(i, j, M)) {
                    T.get(i).get(j).set(0, 0.);
                    T.get(i).get(j).set(1, 0.);
                    T.get(i).get(M-j-1).set(0, 0.);
                    T.get(i).get(M-j-1).set(1, 0.);
                    T.get(N-i-1).get(j).set(0, 0.);
                    T.get(N-i-1).get(j).set(1, 0.);
                    T.get(N-i-1).get(M-j-1).set(0, 0.);
                    T.get(N-i-1).get(M-j-1).set(1, 0.);
                }
            }
        }
        List<List<List<Double>>> P = T;

        // 5. Calculate Q
        List<List<List<Double>>> Q = new ArrayList<>();
        transpose(P);
        for (List<List<Double>> row: P) {
            Q.add(inverseFFT(row));
        }
        transpose(Q);

        // 6. Calculate S
        List<List<List<Double>>> S = new ArrayList<>();
        for (List<List<Double>> row: Q) {
            S.add(inverseFFT(row));
        }

        // 7, 8. Go through items of S, calculate resulting image, and print result
        for (List<List<Double>> row: S) {
            String s = "";
            for (List<Double> item: row) {
                s = s + calculateImageResult(item) + ",";
            }
            System.out.println(s.substring(0, s.length()-1));
        }
    }

    private static Integer calculateImageResult(List<Double> item) {
        if (Math.sqrt(Math.pow(item.get(0), 2) + Math.pow(item.get(1), 2)) <= .5) {
            return 0;
        }
        return 1;
    }

    private static List<List<Double>> inverseFFT(List<List<Double>> items) {

        // Set imaginary values to negative
        for (List<Double> item: items) {
            item.set(1, -1 * item.get(1));
        }

        // Run fft
        List<List<Double>> inverse = fastFourierTransform(items);


        // Divide reals by size and imaginaries by -size
        for (List<Double> item: inverse) {
            item.set(0, item.get(0) / items.size());
            item.set(1, - 1 * item.get(1) / items.size());
        }

        return inverse;
    }

    private static boolean closeToACorner(int i, int j, int M) {
        double comparator = Math.round(.15 * M);
        return (Math.sqrt(Math.pow(i, 2) + Math.pow(j, 2))) <= comparator;
    }

    private static void transpose(List<List<List<Double>>> T) {
        for (int i = 0; i < T.size(); i++) {
            for (int j = 0; j < i; j++) {
                List<Double> itemA = T.get(i).get(j);
                List<Double> itemB = T.get(j).get(i);
                T.get(i).set(j, itemB);
                T.get(j).set(i, itemA);
            }
        }
    }

    // fastFourierTransform from last Homework
    private static List<List<Double>> fastFourierTransform(List<List<Double>> littleXs) {
        if (littleXs.size() == 1) {
            return littleXs;
        }
        List<List<Double>> xEven = createXEven(littleXs);
        xEven = fastFourierTransform(xEven);
        List<List<Double>> xOdd = createXOdd(littleXs);
        xOdd = fastFourierTransform(xOdd);
        List<List<Double>> bigXs = instantiateBigXs(littleXs.size());
        for (int k = 0; k < littleXs.size()/2; k++) {
            List<Double> xSubK = calculateXSubK(xEven, xOdd, k, littleXs.size());
            List<Double> xSubSizeOver2PlusK = calculateXSubSizeOver2PlusK(xEven, xOdd, k, littleXs.size());
            bigXs.set(k, xSubK);
            bigXs.set(littleXs.size()/2 + k, xSubSizeOver2PlusK);
        }
        return bigXs;
    }

    private static List<Double> calculateXSubSizeOver2PlusK(List<List<Double>> xEven, List<List<Double>> xOdd, int k, int size) {
        List<Double> exponentialStuff = new ArrayList<>();
        exponentialStuff.add(Math.cos(-2*Math.PI*k/size));
        exponentialStuff.add(Math.sin(-2*Math.PI*k/size));
        return complexSubtraction(xEven.get(k), complexMultiplication(exponentialStuff, xOdd.get(k)));
    }

    private static List<Double> calculateXSubK(List<List<Double>> xEven, List<List<Double>> xOdd, int k, int size) {
        List<Double> exponentialStuff = new ArrayList<>();
        exponentialStuff.add(Math.cos(-2*Math.PI*k/size));
        exponentialStuff.add(Math.sin(-2*Math.PI*k/size));
        return complexAddition(xEven.get(k), complexMultiplication(exponentialStuff, xOdd.get(k)));
    }

    private static List<List<Double>> instantiateBigXs(int size) {
        List<List<Double>> bigXs = new ArrayList<>();
        List<Double> a = new ArrayList<>();
        a.add(0.0);
        a.add(0.0);
        for (int i = 0; i < size; i++) {
            bigXs.add(a);
        }
        return bigXs;
    }

    private static List<Double> complexAddition(List<Double> a, List<Double> b) {
        List<Double> result = new ArrayList<>();
        result.add(a.get(0) + b.get(0));
        result.add(a.get(1) + b.get(1));
        return result;
    }

    private static List<Double> complexMultiplication(List<Double> a, List<Double> b) {
        List<Double> result = new ArrayList<>();
        result.add((a.get(0) * b.get(0)) - (a.get(1)*b.get(1)));
        result.add((a.get(0) * b.get(1)) + (a.get(1)*b.get(0)));
        return result;
    }

    private static List<Double> complexSubtraction(List<Double> a, List<Double> b) {
        List<Double> result = new ArrayList<>();
        result.add(a.get(0) - b.get(0));
        result.add(a.get(1) - b.get(1));
        return result;
    }

    private static List<List<Double>> createXOdd(List<List<Double>> littleXs) {
        List<List<Double>> xOdd = new ArrayList<>();
        for (int i = 1; i < littleXs.size(); i = i + 2) {
            xOdd.add(littleXs.get(i));
        }
        return xOdd;
    }

    private static List<List<Double>> createXEven(List<List<Double>> littleXs) {
        List<List<Double>> xEven = new ArrayList<>();
        for (int i = 0; i < littleXs.size(); i = i + 2) {
            xEven.add(littleXs.get(i));
        }
        return xEven;
    }

}
