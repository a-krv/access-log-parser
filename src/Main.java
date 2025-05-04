import java.util.Scanner;

public class Main {
    public static void main(String[] args) {
        System.out.println("Введите первое число:");
        int firstNumber = new Scanner(System.in).nextInt();
        System.out.println("Введите второе число:");
        int secondNumber = new Scanner(System.in).nextInt();

        int sum = firstNumber + secondNumber;
        int subtraction = firstNumber - secondNumber;
        int multiplication = firstNumber * secondNumber;
        double division = (double) firstNumber / secondNumber;

        System.out.println("Сумма первого и второго числа: " + sum);
        System.out.println("Разность первого и второго числа: " + subtraction);
        System.out.println("Произведение первого и второго числа: " + multiplication);
        System.out.println("Частное первого и второго числа: " + division);



    }
}
