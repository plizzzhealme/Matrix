package matrix;

import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.*;

public class Main {

    public static void main(String[] args) {

        List<String> lines;

        try (Scanner in = new Scanner(new FileReader("in.txt"))) {
            lines = Files.readAllLines(Paths.get("in.txt")); //считываем файл в список строк
        } catch (IOException e) {
            lines = new ArrayList<>(); //если ошибка, создаем пустой список
        }

        int[][] matrix = new int[lines.size()][];

        /*
         * TODO сделать проверку на корректность введенных данных
         */


        int i = 0;
        /*
         * Преобразуем все в двумерный массив чисел
         */
        for (String line : lines) {
            matrix[i] = Arrays.stream(line.split(" ")).mapToInt(Integer::parseInt).toArray();
            i++;
        }

        //TODO проверить, одинаковый ли длины все строки

        int columns = lines.size();
        int rows = matrix[0].length;
        int sortedCount = 0;
        for (int l = 0; l < rows; l++) {
            boolean isSorted = true;
            for (int m = 0; m < columns - 1; m++) {
                if (matrix[m][l] > matrix[m + 1][l]) {
                    isSorted = false;
                    break;
                }
            }
            if (isSorted) {
                sortedCount++;
            }
            System.out.println(isSorted);
        }

        System.out.println("Отсортировано столбцов: " + sortedCount);

        try(FileWriter writer = new FileWriter("out.txt", false))
        {
            writer.write("Отсортировано столбцов: " + sortedCount);
            writer.flush();
        }
        catch(IOException ex){
            System.out.println(ex.getMessage());
        }


    }
}
