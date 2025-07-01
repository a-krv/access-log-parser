import java.awt.*;
import java.io.*;
import java.util.Scanner;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class Main {

    static int googleBotCounter = 0;
    static int yandexBotCounter = 0;

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

            int lineCounter = 0;

            try {
                FileReader fileReader = new FileReader(path);
                BufferedReader reader = new BufferedReader(fileReader);

                String line;




                while ((line = reader.readLine()) != null) {
                    int lenght = line.length();
                    lineCounter++;

                    if (lenght > 1024) {
                        throw new LongLineException("В файле найдена строка длиной: " + lenght + " символов. Это больше допустимой длины в 1024 символа!");
                    }

                    parseLog(line);

                }
                reader.close();

            } catch (FileNotFoundException e) {
                System.out.println("Файл не найден.");
            } catch (IOException e) {
                System.out.println("Ошибка при чтении файла");
            } catch (LongLineException e) {
                System.out.println("Ошибка:" + e.getMessage());
                break;
            }
            System.out.println("Всего строк: " + lineCounter);
            System.out.println("Количество строк с GoogleBot: " + googleBotCounter);
            System.out.println("Количество строк с YandexBot: " + yandexBotCounter);

            if (lineCounter > 0) {
                double googleBotPercent = (double) googleBotCounter / lineCounter * 100;
                double yandexBotPercent = (double) yandexBotCounter / lineCounter * 100;

                System.out.printf("Доля запросов с GoogleBot: %.2f%%\n", googleBotPercent);
                System.out.printf("Доля запросов с YandexBot: %.2f%%\n", yandexBotPercent);
                break;

            } else {
                System.out.println("Файл пустой!");
            }
        }
    }

    static void parseLog(String line) {
        String[] parts = line.split("\"");

        if (parts.length < 6) {
            return;
        }

        String userAgent = parts[5];

        int open = userAgent.indexOf("(");
        int close = userAgent.indexOf(")");

        if (open != -1 && close != -1 && close > open) {
            String firstBracket = userAgent.substring(open + 1, close);

            String[] fragments = firstBracket.split(";");
            for (int i = 0; i < fragments.length; i++) {
                fragments[i] = fragments[i].trim();
            }

            if (fragments.length >= 2) {
                String secondFragment = fragments[1];
                int slashIndex = secondFragment.indexOf("/");
                String programName;

                if (slashIndex != -1) {
                    programName = secondFragment.substring(0, slashIndex).trim();
                } else {
                    programName = secondFragment.trim();
                }

                if (programName.equals("Googlebot")) {
                    googleBotCounter++;
                } else if (programName.equals("YandexBot")) {
                    yandexBotCounter++;
                }
            }
        }
    }
}
