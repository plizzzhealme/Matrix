package matrix;

import java.io.FileWriter;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

class Main {

    private static final String INPUT_FILE = "in.txt";
    private static final String OUTPUT_FILE = "out.txt";

    public static void main(String[] args) {

        List<String> lines = readFromFile();
        int[][] matrix;

        if (onlyNumbers(lines)) {
            matrix = listToMatrix(lines);

            if (isMatrix(matrix)) {

                List<Integer> sortedRows = getSortedColumns(matrix);
                int sortedCounter = sortedRows.size();

                String outputString = "Отсортировано столбцов: " + sortedCounter + ". Номера столбцов: ";

                for (int column : sortedRows) {
                    outputString += column + 1 + " ";
                }

                System.out.println(outputString);
                writeToFile(outputString);
            } else {
                System.out.println("Файл содержит не матрицу, проверьте количество цифр в каждом ряду");
            }
        } else {
            System.out.println("Ошибка в матрице. Файл должен содержать только числа");
        }
    }

    /**
     * Считывает файл in.txt в список строк
     *
     * @return список строк либо пустой список в случае ошибки
     */
    private static List<String> readFromFile() {
        try {
            return Files.readAllLines(Paths.get(INPUT_FILE));
        } catch (IOException e) {
            return new ArrayList<>();
        }
    }

    /**
     * Преобразует список строк в двумерный массив чисел
     *
     * @param lines список строк
     * @return матрица в виде двумерного массива
     */
    private static int[][] listToMatrix(List<String> lines) {
        int[][] matrix = new int[lines.size()][];
        int row = 0;

        for (String line : lines) {
            matrix[row] = Arrays.stream(line.split(" ")).mapToInt(Integer::parseInt).toArray();
            row++;
        }

        return matrix;
    }

    /**
     * Ищет отсортированные по возрастанию столбцы в матрице
     *
     * @param matrix матрица
     * @return список с номерами отсортированных столбцов
     */
    private static List<Integer> getSortedColumns(int[][] matrix) {
        int rowsNumber = matrix.length;
        int columnsNumber = matrix[0].length;
        List<Integer> sortedRows = new ArrayList<>();

        for (int column = 0; column < columnsNumber; column++) {
            boolean sorted = true;
            int raw = 0;

            while (sorted && raw < rowsNumber - 1) {
                if (matrix[raw][column] > matrix[raw + 1][column]) {
                    sorted = false;
                }
                raw++;
            }

            if (sorted) {
                sortedRows.add(column);
            }
        }
        return sortedRows;
    }

    /**
     * записывает строку в файл out.txt
     *
     * @param outputString строка с результатом
     */
    private static void writeToFile(String outputString) {
        try (FileWriter writer = new FileWriter(OUTPUT_FILE, false)) {
            writer.write(outputString);
            writer.flush();
        } catch (IOException ex) {
            System.out.println(ex.getMessage());
        }
    }

    /**
     * проверяет является ли массив матрицей (одинаковый ли длины строки)
     *
     * @param matrix матрица
     * @return true если являетя, иначе false
     */
    private static boolean isMatrix(int[][] matrix) {
        int size = matrix[0].length;

        for (int[] raw : matrix) {
            if (raw.length != size) {
                return false;
            }
        }

        return true;
    }

    /**
     * Проверка введенных данных
     *
     * @param lines список
     * @return true если все верно, иначе false
     */
    private static boolean onlyNumbers(List<String> lines) {
        String rexEx = "([0-9+-]*\\)*\\(*\\s*)+";

        for (String line : lines) {
            if (!line.matches(rexEx)) {
                return false;
            }
        }

        return true;
    }

}
