/**
 * MULTITHREADED MATRIX MULTIPLICATION DEMO
 * ========================================
 * @author Serena He
 */

import java.util.Arrays;
import java.util.Random;

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
        /**
         * Finds the dot product of two matrices using a single thread.
         * @param int[][] Left-side matrix for dot product
         * @param int[][] Right-side matrix for dot product
         * @return int[][] Dot product of two given matrices.
         */
        int newRows = matrix1.length;
        int newCols = matrix2[0].length;
        int[][] solution = new int[newRows][newCols];

        // Check that matrices can be multiplied
        try {
            if (matrix2.length != matrix1[0].length) {
                throw new ArrayIndexOutOfBoundsException("Error");
            }
            else {
                for (int row=0; row < newRows; row++) {
                    for (int col=0; col < newCols; col++) {
                        solution[row][col] = 0;
                        for (int i=0; i < matrix2.length; i++) {
                            solution[row][col] += matrix1[row][i] * matrix2[i][col];
                        }
                    }
                }
            }
        }
        catch (ArrayIndexOutOfBoundsException e){
            System.out.println("Cannot find dot product of matrices: " + e);
        }

        return solution;
    }

    static void printMatrix(int[][] matrix) {
        /**
         * Prints a given matrix to console.
         * @return Nothing.
         */
        for (int row=0; row < matrix.length; row++) {
            System.out.print("| ");
            for (int col = 0; col < matrix[0].length; col++) {
                System.out.print(matrix[row][col] + " | ");
            }
            System.out.println();
        }
    }

    static int[][] generateRandomMatrix(int rows, int cols, int minNum, int maxNum) {
        /**
         * Returns a random matrix given the dimensions and the range for the possible values
         * @param int Number of rows
         * @param int Number of columns
         * @param int Minimum value
         * @param int Maximum value
         * @return int[][] New matrix with random values.
         */
        int[][] newMatrix = new int[rows][cols];
        Random randomNum = new Random();

        for (int row=0; row < rows; row++) {
            for (int col=0; col < cols; col++) {
                newMatrix[row][col] = randomNum.nextInt(maxNum - minNum) + minNum;
            }
        }
        return newMatrix;
    }

    public static void main(String[] args) {
        // Times
        long startTime;
        long endTime;
        long duration;
        long singleTime;
        long doubleTime;
        long quadrupleTime;
        // Results
        int[][] singleThreadResult;
        int[][] doubleThreadResult;
        int[][] quadrupleThreadResult;
        // Generate matrices (1000x1000)
        int [][] matrix1 = generateRandomMatrix(24, 24, -20, 20);
        int [][] matrix2 = generateRandomMatrix(24, 24, -20, 20);
        int matrix1Rows = matrix1.length;

        // -----Single thread-----
        startTime = System.nanoTime();
        System.out.println("-----Single Thread-----");

        MultiplierThread thread11 = new MultiplierThread(matrix1, matrix2);
        thread11.start();
        try {
            thread11.join();
            singleThreadResult = thread11.resultMatrix;
            printMatrix(singleThreadResult);
        }
        catch (Exception e) {
            System.out.println("Exception caught: " + e);
        }

        endTime = System.nanoTime();
        duration = (endTime - startTime)/1000000;
        singleTime = duration;
        System.out.println("Time: " + duration + " ms\n");

        // -----Two threads-----
        startTime = System.nanoTime();
        System.out.println("-----Two Threads-----");
        int[][] double_matrix1_1 = Arrays.copyOfRange(matrix1, 0, matrix1Rows/2);
        int[][] double_matrix1_2 = Arrays.copyOfRange(matrix1, matrix1Rows/2, matrix1Rows);

        MultiplierThread thread21 = new MultiplierThread(double_matrix1_1, matrix2);
        MultiplierThread thread22 = new MultiplierThread(double_matrix1_2, matrix2);
        thread21.start();
        thread22.start();
        try {
            thread21.join();
            thread22.join();

            doubleThreadResult = new int[matrix1Rows][];
            System.arraycopy(thread21.resultMatrix, 0, doubleThreadResult, 0, thread21.resultMatrix.length);
            System.arraycopy(thread22.resultMatrix, 0, doubleThreadResult, thread21.resultMatrix.length, thread22.resultMatrix.length);
            printMatrix(doubleThreadResult);
        }
        catch (Exception e) {
            System.out.println("Exception caught: " + e);
        }

        endTime = System.nanoTime();
        duration = (endTime - startTime)/1000000;
        doubleTime = duration;
        System.out.println("Time: " + duration + " ms");

        // -----Four threads-----
        startTime = System.nanoTime();
        System.out.println("-----Four Threads-----");

        int quadSectionLength = matrix1Rows/4;

        int[][] quad_matrix1_1 = Arrays.copyOfRange(matrix1, 0, quadSectionLength);
        int[][] quad_matrix1_2 = Arrays.copyOfRange(matrix1, quadSectionLength, 2*quadSectionLength);
        int[][] quad_matrix1_3 = Arrays.copyOfRange(matrix1, 2*quadSectionLength, 3*quadSectionLength);
        int[][] quad_matrix1_4 = Arrays.copyOfRange(matrix1, 3*quadSectionLength, matrix1Rows);

        MultiplierThread thread41 = new MultiplierThread(quad_matrix1_1, matrix2);
        MultiplierThread thread42 = new MultiplierThread(quad_matrix1_2, matrix2);
        MultiplierThread thread43 = new MultiplierThread(quad_matrix1_3, matrix2);
        MultiplierThread thread44 = new MultiplierThread(quad_matrix1_4, matrix2);

        thread41.start();
        thread42.start();
        thread43.start();
        thread44.start();
        try {
            thread41.join();
            thread42.join();
            thread43.join();
            thread44.join();

            quadrupleThreadResult = new int[matrix1Rows][];
            System.arraycopy(thread41.resultMatrix, 0, quadrupleThreadResult, 0, quadSectionLength);
            System.arraycopy(thread42.resultMatrix, 0, quadrupleThreadResult, quadSectionLength, quadSectionLength);
            System.arraycopy(thread43.resultMatrix, 0, quadrupleThreadResult, 2*quadSectionLength, quadSectionLength);
            System.arraycopy(thread44.resultMatrix, 0, quadrupleThreadResult, 3*quadSectionLength, quadSectionLength);

            printMatrix(quadrupleThreadResult);
        }
        catch (Exception e) {
            System.out.println("Exception caught: " + e);
        }

        endTime = System.nanoTime();
        duration = (endTime - startTime)/1000000;
        quadrupleTime = duration;
        System.out.println("Time: " + duration + " ms");

        // -----Print results-----
        System.out.println("=====================RESULTS=====================");
        System.out.println("Single thread: " + singleTime + " ms");
        System.out.println("Two threads: " + doubleTime + " ms");
        System.out.println("Four threads: " + quadrupleTime + " ms");

    }
}