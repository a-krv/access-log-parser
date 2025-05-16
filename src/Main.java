import java.io.File;
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
        }
    }
}
