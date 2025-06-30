import java.awt.*;
import java.io.*;
import java.util.Scanner;

public class Main {
    public static void main(String[] args) {
        int fileCounter = 0;

        while (true) {
            System.out.print("Введите путь к файлу:");
            String path = new Scanner(System.in).nextLine();
            File file = new File(path);
            boolean fileExists = file.exists();
            boolean isDirectory = file.isDirectory();

            if (fileExists == false || isDirectory == true) {
                System.out.println("Путь указан неверно или указан путь до папки!");
                continue;
            } else {
                fileCounter++;
                System.out.println("Путь указан верно.");
                System.out.println("Это файл номер " + fileCounter + ".");
            }
            try {
                FileReader fileReader = new FileReader(path);
                BufferedReader reader = new BufferedReader(fileReader);

                String line;
                int lineCounter = 0;
                int maxLenght = 0;
                int minLenght = Integer.MAX_VALUE;

                while ((line = reader.readLine()) != null) {
                    int lenght = line.length();

                    if (lenght > 1024) {
                        throw new LongLineException("В файле найдена строка длиной: " + lenght + " символов. Это больше допустимой длины в 1024 символа!");
                    }


                    System.out.println("Длина строки: " + lenght);
                    lineCounter++;

                    if (lenght > maxLenght) {
                        maxLenght = lenght;
                    }
                    if (lenght < minLenght) {
                        minLenght = lenght;
                    }
                }
                reader.close();

                System.out.println("Всего строк: " + lineCounter);
                System.out.println("Длина самой длинной строки: " + maxLenght);
                System.out.println("Длина самой короткой строки: " + minLenght);

            } catch (FileNotFoundException e) {
                System.out.println("Файл не найден.");
            } catch (IOException e) {
                System.out.println("Ошибка при чтении файла");
            }
            catch (LongLineException e) {
                System.out.println("Ошибка:" + e.getMessage());
                break;
            }
        }
    }
}
