package matrix;

import java.util.ArrayList;
import java.util.List;

class Matrix {
    private final int[][] data;
    private final int columnsNumber;
    private final int rowsNumber;

    Matrix(int[][] array) {
        rowsNumber = array.length;
        columnsNumber = array[0].length;
        data = new int[rowsNumber][columnsNumber];
        for (int i = 0; i < rowsNumber; i++) {
            System.arraycopy(array[i], 0, data[i], 0, columnsNumber);
        }
    }

    /**
     * @return строку, представляющую собой матрицу, элементы разделены пробелами и построчно
     */
    @Override
    public String toString() {
        StringBuilder result = new StringBuilder();
        for (int i = 0; i < rowsNumber; i++) {
            for (int j = 0; j < columnsNumber; j++) {
                result.append(data[i][j]).append(" ");
            }
            result.append("\n");
        }
        return result.toString();
    }

    /**
     * @return список с индексами отсортированных по возрастанию столбцов
     */
    List<Integer> getSortedColumns() {
        List<Integer> sortedColumns = new ArrayList<>();
        for (int column = 0; column < columnsNumber; column++) {
            boolean sorted = true;
            int row = 0;
            while (sorted && row < rowsNumber - 1) {
                if (data[row][column] > data[row + 1][column]) {
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
     * если матрица порядка 2n
     */
    void replace() {
        if (columnsNumber == rowsNumber && columnsNumber % 2 == 0) {
            int n = columnsNumber / 2;
            for (int i = 0; i < n; i++) {
                for (int j = 0; j < n; j++) {
                    int temp = data[i][j];
                    data[i][j] = data[i][j + n];
                    data[i][j + n] = temp;
                }
            }
            for (int i = 0; i < n; i++) {
                int[] temp = data[i];
                data[i] = data[i + n];
                data[i + n] = temp;
            }
        }
    }
}
