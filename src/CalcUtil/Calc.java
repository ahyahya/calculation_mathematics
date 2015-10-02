package CalcUtil;

import java.util.Arrays;
import java.util.ArrayList;
import java.util.List;
import java.util.function.BiFunction;
import java.util.function.Function;
import java.util.function.IntFunction;
import java.util.function.Supplier;
import java.util.stream.Collectors;
import java.util.stream.IntStream;
import java.util.stream.Stream;

import static java.lang.Math.*;


public class Calc {

    public static void printVec(double[] ary) {
        Arrays.stream(ary).forEach((v) -> {
            System.out.print(v + " ");
        });
        System.out.println();
    }

    public static void printMat(double[][] mat) {
        Arrays.stream(mat).forEach((ary) -> {
            Arrays.stream(ary).forEach((v) -> {
                System.out.print(v + " ");
            });
            System.out.println();
        });
    }

    public static double[] scalarMultiple(double c, double[] ary) {
        return Arrays.stream(ary)
            .map((v) -> c * v)
            .toArray();
    }

    public static double[] addVec(double[] ary1, double[] ary2) {
        return IntStream.range(0, ary1.length)
            .mapToDouble((i) -> ary1[i] + ary2[i])
            .toArray();
    }

    public static double[] subVec(double[] ary1, double[] ary2) {
        return addVec(ary1, negative(ary2));
    }
    public static double[] negative(double[] ary) {
        return scalarMultiple(-1, ary);
    }

    public static double innProd(double[] ary1, double[] ary2) {
        return IntStream
            .range(0, ary1.length)
            .mapToDouble((i) -> ary1[i] * ary2[i])
            .sum();
    }

    public static double[] matVec(double[][] mat, double[] ary) {
        return IntStream
            .range(0, mat.length)
            .mapToDouble((i) -> innProd(mat[i], ary))
            .toArray();
    }

    public static double[] residual(double[][] mat, double[] ary1, double[] ary2) {
        return addVec(matVec(mat, ary1), ary2);
    }

    public static double[][] addMat(double[][] mat1, double[][] mat2) {
        return matrixMap(mat1, (x, y) -> (double) x + y);
    }

    public static double[][] scalarMultiple(double c, double[][] mat) {
        return matrixMap(mat, (x, y) -> c * mat[x][y]);
    }
    public static double[][] multipleMat(double[][] mat1, double[][] mat2) {
        double[][] mat2t = matTransposition(mat2);
        return twoDimMap(mat1.length, mat2t.length, (x, y) -> innProd(mat1[x], mat2t[y]));
    }

    public static double[][] twoDimMap(int x, int y, BiFunction<Integer, Integer, Double> callback) {
        double[][] result = new double[x][y];
        IntStream.range(0, x).forEach((_x) -> {
            IntStream.range(0, y).forEach((_y) -> {
                result[_x][_y] = callback.apply(_x, _y);
            });
        });
        return result;
    }
    public static double[][] matrixMap(double[][] mtx, BiFunction<Integer, Integer, Double> callback) {
        return twoDimMap(mtx.length, mtx[0].length, callback);
    }

    public static double vecNorm1(double[] ary) {
        return Arrays.stream(ary)
            .map(Math::abs)
            .sum();
    }

    public static double vecNorm2(double[] ary) {
        return IntStream
            .range(0, ary.length)
            .mapToDouble((i) -> ary[i] * ary[i])
            .sum();
    }

    public static double vecNormInf(double[] ary) {
        return Arrays.stream(ary)
            .map(Math::abs)
            .max()
            .getAsDouble();
    }

    public static double matNorm1(double[][] mat) {
        return vecNormInf(
            IntStream.range(0, mat.length)
                .mapToDouble((i) -> vecNorm1(mat[i]))
                .toArray()
        );
    }

    public static double matNormInf(double[][] mat) {
        return matNorm1(matTransposition(mat));
    }

    public static double[][] matTransposition(double[][] mat) {
        return twoDimMap(mat[0].length, mat.length, (x, y) -> mat[y][x]);
    }

}
