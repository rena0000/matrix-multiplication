/**
 * MULTITHREADED MATRIX MULTIPLICATION DEMO
 * ========================================
 * @author Serena He
 */

public class Main {

    static int[][] MATRIX_3x2 = new int[][] {
            {1, 2, 3},
            {4, 5, 6}
    };
    static int[][] MATRIX_2x3 = new int[][]{
            {7, 8},
            {9, 10},
            {11, 12}
    };

    static int[][] singleThreadMultiplyMatrix(int[][] matrix1, int[][] matrix2) {
        int width = matrix2[0].length;
        int height = matrix1.length;
        int[][] solution = new int[width][height];

        for (int i=0; i < matrix2[0].length; i++) {

        }





        return solution;
    }

    public static void main(String[] args) {

        System.out.println("-----Single Threaded-----");
        long singleThreadStart = System.nanoTime();

        int[][] result = singleThreadMultiplyMatrix(MATRIX_3x2, MATRIX_2x3);

        System.out.println("rows: " + result.length);
        System.out.println("cols " + result[0].length);

        long singleThreadEnd = System.nanoTime();
        long singleThreadTime = (singleThreadEnd - singleThreadStart)/1000;
        System.out.println("Time: " + singleThreadTime + " ms");

    }
}
a