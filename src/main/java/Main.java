import java.math.BigInteger;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.Scanner;

public class Main {

    static Scanner in = new Scanner(System.in);

    public static String encryptThisString(String input) {
        try {
            MessageDigest md = MessageDigest.getInstance("SHA-1");// метод getInstance() вызывается с алгоритмом SHA-1
            byte[] messageDigest = md.digest(input.getBytes());// возвращается как массив байтов
            BigInteger no = new BigInteger(1, messageDigest);// Преобразование байтового массива в представление знака
            StringBuilder hashText = new StringBuilder(no.toString(16));// Преобразуем дайджест сообщения в шестнадцатеричное значение
            while (hashText.length() < 32) {// Добавить предыдущие 0, чтобы сделать его 32-битным
                hashText.insert(0, "0");
            }
            return hashText.toString(); // возвращаем HashText
        } catch (NoSuchAlgorithmException e) {// Для указания неправильных алгоритмов дайджеста сообщений
            throw new RuntimeException(e);
        }
    }


    private static void identification() {
        System.out.print("Придумайте логин: ");
        String login = in.nextLine();
        System.out.print("Придумайте пароль: ");
        String password = encryptThisString(in.nextLine());
        //saving login-password
    }

    private static boolean authentication() {
        String rightLogin = "admin";
        String rightPassword = encryptThisString("12345");
        System.out.print("Введите логин: ");
        String login = in.nextLine();
        System.out.print("Введите пароль: ");
        String password = encryptThisString(in.nextLine());
        return rightLogin.equals(login) && rightPassword.equals(password);
    }

    private static String[] inputBook() {
        System.out.println("Если не знаете ответ, нажмите \"ENTER\"");
        System.out.print("Введите название книги: ");
        String bookName = in.nextLine();
        System.out.print("Введите автора: ");
        String bookAuthor = in.nextLine();
        System.out.print("Введите жанр: ");
        String bookGenre = in.nextLine();
        return new String[]{bookName, bookAuthor, bookGenre};
    }

    private static void findBook() {
        String[] book = inputBook();
        //finding book
        System.out.println("стелаж 2 полка 5\n");
    }

    private static void addBook() {
        String[] book = inputBook();
        //adding book
        System.out.println("книга добавлена\n");
    }

    private static void deleteBook() {
        String[] book = inputBook();
        //deleting book
        System.out.println("книга удалена\n");
    }

    private static void moveBook() {
        String[] book = inputBook();
        //moving book
        System.out.println("расположение изменено\n");
    }

    public static void main(String[] args) {
        System.out.println("Добро пожаловать в библиотеку!");
        boolean access = false;
        while (!access) {
            System.out.println("1. Войти в систему\n2. Зарегистрироваться");
            String mode = in.nextLine();
            switch (mode) {
                case "1" -> {
                    access = authentication();
                }
                case "2" -> {
                    identification();
                }
            }
        }
        boolean flag = true;
        while (flag) {
            System.out.println("Что вы хотите сделать?");
            System.out.println("1. Найти книгу");
            System.out.println("2. Добавить книгу");
            System.out.println("3. Удалить книгу");
            System.out.println("4. Изменить расположение книги");
            System.out.println("5. Уйти из библиотеки");
            String action = in.nextLine();
            switch (action) {
                case "1" -> {
                    findBook();
                }
                case "2" -> {
                    addBook();
                }
                case "3" -> {
                    deleteBook();
                }
                case "4" -> {
                    moveBook();
                }
                case "5" -> {
                    flag = false;
                }
            }
        }
    }
}
