import java.util.Arrays;
import java.util.Scanner;

public class Calculator {
    static String[] romanArr = {" ","I", "II", "III", "IV", "V", "VI", "VII", "VIII", "IX", "X"};
    static String operation = "";
    static String roman = "";
    static String arabic = "";
    public static void main(String[] args) throws Exception {
        Scanner scanner = new Scanner(System.in);
        System.out.println("Введите выражение: ");
        String line = scanner.nextLine();
        scanner.close();
        check(line);
        System.out.println(calc(line));
    }
    public static String check(String input) throws Exception {
        String[] strArr = input.split(" ");
        /*Проверка ввода*/
        if (strArr.length == 1 && strArr[0].length() < 1) {
            throw new Exception("Строка не является математической операцией");
        } else if (strArr.length > 3) {
            throw new Exception("Формат математической операции не удовлетворяет заданию - два операнда и один оператор (+, -, /, *)");
        } else if (strArr.length < 2) {
            throw new Exception("Некорректный ввод");
        }else if (strArr.length != 3) {
            throw new Exception("Некорректный ввод");
        }

        /*Проверка на систему исчисления*/
        if (Arrays.asList(romanArr).contains(strArr[0]) || Arrays.asList(romanArr).contains(strArr[2])) {
            roman = Arrays.toString(strArr);
        } else if (Integer.parseInt(strArr[0]) >= 1 && Integer.parseInt(strArr[0]) <= 10 && Integer.parseInt(strArr[2]) >= 1 && Integer.parseInt(strArr[2]) <= 10) {
            arabic = Arrays.toString(strArr);
        } else {
            throw new Exception("Некорректный ввод");
        }

        /*Проверка операции*/
        if (strArr[1].equals("+") || strArr[1].equals("-") || strArr[1].equals("*") || strArr[1].equals("/")) {
            operation = strArr[1];
        } else {
            throw new Exception("Строка не содержит операторов");
        }
        /*Првоерка на использование одновременно разных системы исчисления*/
        if (Arrays.asList(romanArr).contains(strArr[0]) && !Arrays.asList(romanArr).contains(strArr[2])) {
            throw new Exception("Используются одновременно разные системы счисления");
        } else if (!Arrays.asList(romanArr).contains(strArr[0]) && Arrays.asList(romanArr).contains(strArr[2])) {
            throw new Exception("Используются одновременно разные системы исчисления");
        }
        return Arrays.toString(strArr);
    }
    public static String calc(String input) throws Exception {
        int x = 0, y = 0, result = 0;
        /*Присваиваем значения x и y, согласно системе исчисления*/
        String[] strArr = input.split(" ");
        if (!roman.isEmpty()) {
            for (int i = 0; i < romanArr.length; i++) {
                if (strArr[0].equals(romanArr[i]) && x == 0) {
                    x = i;
                    i = 0;
                } else if (strArr[2].equals(romanArr[i])) {
                    y = i;
                }
            }
        } else if (!arabic.isEmpty()) {
            x = Integer.parseInt(strArr[0]);
            y = Integer.parseInt(strArr[2]);
        }
        /*Производим операцию, согласно оператору*/
        switch (operation) {
            case "+": result = x + y; break;
            case "-": result = x - y; break;
            case "*": result = x * y; break;
            case "/": result = x / y; break;
        }
        /*Получаем результат в римской системе исчисления*/
        String a = "", b = "", resultForRoman = "";
        String line = String.valueOf(result);
        String[] arrline = line.split("");
        if (arrline.length == 1) {
            for (int i = 0; i < romanArr.length; i++) {
                if (Integer.parseInt(arrline[0]) == i && !roman.isEmpty()) {
                    b = romanArr[i];
                    resultForRoman = b;
                }
            }

        }
        if (arrline.length == 2) {
            for (int i = 0; i < romanArr.length; i++) {
                if (Integer.parseInt(arrline[1]) == i && !roman.isEmpty()) {
                    b = romanArr[i];
                }
            }
            switch (arrline[0]) {
                case "1": a = "X"; break;
                case "2": a = "XX"; break;
                case "3": a = "XXX"; break;
                case "4": a = "XL"; break;
                case "5": a = "L"; break;
                case "6": a = "LX"; break;
                case "7": a = "LXX"; break;
                case "8": a = "LXXX"; break;
                case "9": a = "XC"; break;
            }
            resultForRoman = a + b;
        }
        if (arrline.length == 3) {
            resultForRoman = "C";
        }
        /*Проверяем результат на отгрицательное значение*/
        if (!roman.isEmpty() && result < 0) {
            throw new Exception("В римской системе нет отрицательных чисел");
        }
        /*Возвращаем результат, согласно системе исчисления*/
        if (!arabic.isEmpty()) {
            return String.valueOf(result);
        } else {
            return resultForRoman;
        }

    }
}
