package matrix;

import java.io.FileWriter;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

class Main {
    private static final boolean ADD = true;
    private static final boolean REPLACE = false;
    private static final String INPUT_FILE = "in.txt";
    private static final String OUTPUT_FILE = "out.txt";
    private static final String INCORRECT_DATA = "Исходный файл содержит недопустимые символы, " +
            "либо массив не является матрицей";

    public static void main(String[] args) {
        List<String> list = readFromFile(INPUT_FILE);
        int[][] array;
        Matrix matrix;
        String result;

        if (isDigital(list) && isMatrix(array = listToArray(list))) { //если данные в файле корректны

            /*
            создаем и печатаем исходную матрицу
             */
            matrix = new Matrix(array);
            System.out.println(matrix);

            /*
            Считываем и печатаем отсортированные столбцы
             */
            List<Integer> sortedColumns = matrix.getSortedColumns();
            result = getSortedColumnsInfo(sortedColumns);
            System.out.println(result);
            writeToFile(OUTPUT_FILE, result, REPLACE);

            /*
            перемещаем элементы в матрице, печатаем получившуюся матрицу
             */
            matrix.replace();
            System.out.println(matrix);
            writeToFile(OUTPUT_FILE, matrix.toString(), ADD);

        } else {
            System.out.println(INCORRECT_DATA);
        }
    }

    /**
     * Считываем текстовый файл в список строк
     *
     * @param path путь к файлу
     * @return получившийся список, в случае ошибки - пустой список
     */
    private static List<String> readFromFile(String path) {
        try {
            return Files.readAllLines(Paths.get(path));
        } catch (IOException e) {
            return new ArrayList<>();
        }
    }

    /**
     * Преобразует список строк в двумерный массив чисел
     *
     * @param list список строк из файла
     * @return двумерный массив целых чисел
     */
    private static int[][] listToArray(List<String> list) {
        int[][] array = new int[list.size()][];
        int row = 0;
        for (String line : list) {
            array[row] = Arrays.stream(line.split(" ")).mapToInt(Integer::parseInt).toArray();
            row++;
        }
        return array;
    }

    /**
     * Сохраняем строку в текстовый файл
     *
     * @param path   путь к файлу
     * @param data   записываемая строка
     * @param append true - добавить в конец файла, false - перезаписать файл
     */
    private static void writeToFile(String path, String data, boolean append) {
        try (FileWriter writer = new FileWriter(path, append)) {
            writer.write(data);
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
        outputString.append("\n");
        return String.valueOf(outputString);
    }
}