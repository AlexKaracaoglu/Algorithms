/*
    Alex Karacaoglu
    Homework 4
    #4 - Fast Fourier Transform
 */

import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class FastFourierTransform {

    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        List<List<Double>> littleXs = new ArrayList<>();
        while(scanner.hasNext()) {
            List<Double> x = new ArrayList<>();
            String realStuff = scanner.next();
            String imaginaryStuff = scanner.next();
            x.add(Double.valueOf(realStuff));
            x.add(Double.valueOf(imaginaryStuff));
            littleXs.add(x);
        }
        List<List<Double>> bigXs = fastFourierTransform(littleXs);
        for (int i = 0; i <bigXs.size(); i++) {
            System.out.println(String.format("%.3f",bigXs.get(i).get(0)) + " " + String.format("%.3f",bigXs.get(i).get(1)));
        }
    }


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
