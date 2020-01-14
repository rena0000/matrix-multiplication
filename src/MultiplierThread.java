public class MultiplierThread  extends Thread {

    int[][] matrix1;
    int[][] matrix2;
    int[][] resultMatrix;

    public MultiplierThread (int[][] matrix1, int[][] matrix2) {
        this.matrix1 = matrix1;
        this.matrix2 = matrix2;
    }

    public int[][] multiplyMatrix(int[][] matrix1, int[][] matrix2) {
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

    public void run() {
        System.out.println("Thread start");
        resultMatrix = multiplyMatrix(matrix1, matrix2);
    }
}
