import java.io.*;
import java.util.Map;
import java.util.Scanner;
import java.util.Set;

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

            int lineCounter = 0;
            Statistics statistics = new Statistics();

            try {
                FileReader fileReader = new FileReader(path);
                BufferedReader reader = new BufferedReader(fileReader);

                String line;


                while ((line = reader.readLine()) != null) {
                    int length = line.length();
                    lineCounter++;

                    if (length > 1024) {
                        throw new LongLineException("В файле найдена строка длиной: " + length + " символов. Это больше допустимой длины в 1024 символа!");
                    }

                    LogEntry entry = new LogEntry(line);
                    statistics.addEntry(entry);

                }
                reader.close();

                System.out.println("Общее количество запросов: " + statistics.getTotalRequests());
                System.out.printf("Средний объем трафика за час: %.2f МБ\n", (double) statistics.getTrafficRate() / (1024 * 1024));
                System.out.printf("Среднее число посещений в час: %.2f%n", statistics.getAverageVisitsPerHour());
                System.out.printf("Среднее число ошибок в час: %.2f%n", statistics.getAverageErrorsPerHour());
                System.out.printf("Среднее число посещений одним пользователем (не ботом): %.2f%n", statistics.getAverageVisitsPerRealUser());
                System.out.println("GoogleBot запросов: " + statistics.getGoogleBotCount());
                System.out.println("YandexBot запросов: " + statistics.getYandexBotCount());

                System.out.printf("Доля запросов с GoogleBot: %.2f%%\n", statistics.getGoogleBotRatio() * 100);
                System.out.printf("Доля запросов с YandexBot: %.2f%%\n", statistics.getYandexBotRatio() * 100);
                System.out.println("~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~");

                Map<String, Double> osStats = statistics.getOsStatistics();
                System.out.println("Статистика операционных систем:");
                for (Map.Entry<String, Double> entry : osStats.entrySet()) {
                    System.out.printf("%s: %.2f%%\n", entry.getKey(), entry.getValue() * 100);
                }

                Map<String, Double> browserStats = statistics.getBrowserStatistics();
                System.out.println("Статистика браузеров:");
                for (Map.Entry<String, Double> entry : browserStats.entrySet()) {
                    System.out.printf("%s: %.2f%%\n", entry.getKey(), entry.getValue() * 100);
                }
                System.out.println("~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~");

//                Set<String> notExistingPages = statistics.getAllNotExistingPages();
//                System.out.println("Несуществующие страницы c кодом 404:");
//                for (String page : notExistingPages) {
//                    System.out.println(page);
//                }

//                Set<String> pages = statistics.getAllPages();
//                System.out.println("Уникальные страницы сайта c кодом 200:");
//                for (String page : pages) {
//                    System.out.println(page);
//                }

                break;

            } catch (FileNotFoundException e) {
                System.out.println("Файл не найден.");
            } catch (IOException e) {
                System.out.println("Ошибка при чтении файла");
            } catch (LongLineException e) {
                System.out.println("Ошибка:" + e.getMessage());
                break;
            }
        }
    }
}
