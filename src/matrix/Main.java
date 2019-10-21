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
    private static final String FILE_ERROR = "Исходный файл содержит недопустимые символы";
    private static final String MATRIX_ERROR = "Исходный массив не является матрицей";

    public static void main(String[] args) {

        List<String> list = readFromFile();
        int[][] array;
        Matrix matrix;
        String result;

        if (isDigital(list)) {
            array = listToArray(list);

            if (isMatrix(array)) {
                matrix = new Matrix(array);
                System.out.println(matrix);

                List<Integer> sortedColumns = matrix.getSortedColumns();
                result = getSortedColumnsInfo(sortedColumns);
                System.out.println(result);
                writeToFile(result);

                matrix.replace();
                System.out.println(matrix);

            } else {
                System.out.println(FILE_ERROR);
            }
        } else {
            System.out.println(MATRIX_ERROR);
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
     * @param lines список строк из файла
     * @return двумерный массив целых чисел
     */
    private static int[][] listToArray(List<String> lines) {
        int[][] matrix = new int[lines.size()][];
        int row = 0;

        for (String line : lines) {
            matrix[row] = Arrays.stream(line.split(" ")).mapToInt(Integer::parseInt).toArray();
            row++;
        }

        return matrix;
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
     * проверяет является ли массив матрицей (одинаковой ли длины строки)
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
     * Проверка введенных данных регулярным выражением
     *
     * @param lines список строк из исходного файла
     * @return true если все верно, иначе false
     */
    private static boolean isDigital(List<String> lines) {
        String rexEx = "([0-9+-]*\\)*\\(*\\s*)+";

        for (String line : lines) {
            if (!line.matches(rexEx)) {
                return false;
            }
        }

        return true;
    }

    /**
     * Генерирует сообщение об отсортированных столбцов
     *
     * @param sortedColumns список с индексами столбцов
     * @return строка с информацией
     */
    private static String getSortedColumnsInfo(List<Integer> sortedColumns) {
        StringBuilder outputString = new StringBuilder("Отсортировано столбцов: "
                + sortedColumns.size() + ". Номера столбцов: ");

        for (int column : sortedColumns) {
            outputString.append(column + 1).append(" ");
        }

        return String.valueOf(outputString);
    }
}