package matrix;

import java.util.ArrayList;
import java.util.List;

class Matrix {
    private final int[][] matrix;
    private final int columnsNumber;
    private final int rowsNumber;

    Matrix(int[][] array) {
        rowsNumber = array.length;
        columnsNumber = array[0].length;
        matrix = new int[rowsNumber][columnsNumber];

        for (int i = 0; i < rowsNumber; i++) {
            System.arraycopy(array[i], 0, matrix[i], 0, columnsNumber);
        }
    }

    @Override
    public String toString() {
        StringBuilder result = new StringBuilder();

        for (int i = 0; i < rowsNumber; i++) {
            for (int j = 0; j < columnsNumber; j++) {
                result.append(matrix[i][j]).append(" ");
            }

            result.append("\n");
        }

        return result.toString();
    }

    /**
     * возвращает список с индексами отсортированных столбцов
     *
     * @return список int
     */
    List<Integer> getSortedColumns() {
        List<Integer> sortedColumns = new ArrayList<>();

        for (int column = 0; column < columnsNumber; column++) {
            boolean sorted = true;
            int row = 0;

            while (sorted && row < rowsNumber - 1) {
                if (matrix[row][column] > matrix[row + 1][column]) {
                    sorted = false;
                }
                row++;
            }

            if (sorted) {
                sortedColumns.add(column);
            }
        }
        return sortedColumns;
    }

    /**
     * Меняет четверти матрицы местами,
     * если матрица квадратная,
     * и число рядов и столбцов четное
     */
    void replace() {
        if (columnsNumber == rowsNumber && columnsNumber % 2 == 0) {
            int n = columnsNumber / 2;

            for (int i = 0; i < n; i++) {
                for (int j = 0; j < n; j++) {
                    int temp = matrix[i][j];
                    matrix[i][j] = matrix[i][j + n];
                    matrix[i][j + n] = temp;
                }
            }

            for (int i = 0; i < n; i++) {
                int[] temp = matrix[i];
                matrix[i] = matrix[i + n];
                matrix[i + n] = temp;
            }
        }
    }
}
