import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.core.JsonProcessingException;
import model.*;

import java.math.BigInteger;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.List;
import java.util.Scanner;
import java.util.Set;

import com.fasterxml.jackson.databind.ObjectMapper;

import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;

public class Main {

    static Scanner in = new Scanner(System.in);
    private static final EntityManagerFactory managerFactory = Persistence.createEntityManagerFactory("library");

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
        Reader reader = new Reader();
        System.out.print("Введите ваше имя: ");
        reader.setName(in.nextLine());
        System.out.print("Введите ваше отчество (если есть): ");
        reader.setPatronymic(in.nextLine());
        System.out.print("Введите вашу фамилию: ");
        reader.setSurname(in.nextLine());
        System.out.print("Придумайте логин: ");
        reader.setLogin(in.nextLine());
        System.out.print("Придумайте пароль: ");
        reader.setPassword(encryptThisString(in.nextLine()));
        //saving login-password
        MainCRUD.createObjectInDB(reader, managerFactory);
    }

    private static boolean authentication() {
        Reader reader = new Reader();
        System.out.print("Введите логин: ");
        String login = in.nextLine();
        System.out.print("Введите пароль: ");
        String password = encryptThisString(in.nextLine());
        reader.setLogin(login);
        reader.setPassword(password);
        return MainQuery.isReader(reader, managerFactory);
    }

    private static BookCopy inputBookCopy(Boolean isAdd) {
        Book book = inputBook(isAdd);
        BookCase bookCase = new BookCase();
        Shelf shelf = new Shelf();
        BookCopy bookCopy = new BookCopy();
        System.out.print("Введите номер шкафа, где стоит книга: ");
        bookCase.setName(in.nextLine());
        System.out.print("Введите номер полки, где стоит книга: ");
        shelf.setName(in.nextLine());
        bookCase.setShelves(Set.of(shelf));
        shelf.setBookCase(Set.of(bookCase));
        bookCase = MainQuery.getBookCase(bookCase, managerFactory);
        shelf = MainQuery.getShelf(shelf, managerFactory);
        book = MainQuery.getBook(book, managerFactory);
        bookCopy.setBook(book);
        bookCopy.setShelf(shelf);
        bookCopy.setBookCase(bookCase);
        return bookCopy;
    }

    private static Book inputBook(Boolean isAdd) {
        Book book = new Book();
        Author author;
        Genre genre = new Genre();
        Publisher publisher = new Publisher();
        System.out.print("Я знаю автора книги (да/нет): ");
        if (in.nextLine().toLowerCase().equals("да")) {
            author = inputAuthor(isAdd);
            book.setAuthor(author);
        }
        System.out.print("Я знаю название книги (да/нет): ");
        if (in.nextLine().toLowerCase().equals("да")) {
            System.out.print("Введите название книги: ");
            book.setName(in.nextLine());
        }
        if (isAdd) {
            System.out.print("Я знаю год выпуска книги (да/нет): ");
            if (in.nextLine().toLowerCase().equals("да")) {
                System.out.print("Введите год выпуска книги: ");
                book.setYear(Integer.parseInt(in.nextLine()));
            }
            System.out.print("Я знаю жанр книги (да/нет): ");
            if (in.nextLine().toLowerCase().equals("да")) {
                System.out.print("Введите жанр: ");
                genre.setName(in.nextLine());
                genre = MainQuery.getGenre(genre, managerFactory);
                book.setGenre(genre);
            }
            System.out.print("Я знаю издательство книги (да/нет): ");
            if (in.nextLine().toLowerCase().equals("да")) {
                System.out.print("Введите издательство: ");
                publisher.setName(in.nextLine());
                publisher = MainQuery.getPublisher(publisher, managerFactory);
                book.setPublisher(publisher);
            }
        }
        return book;
    }


    private static Author inputAuthor(Boolean isCreateIfNotExist) {
        System.out.println("Если не знаете ответ, нажмите \"ENTER\"");
        Author author = new Author();
        System.out.print("Введите имя автора: ");
        author.setName(in.nextLine());
        System.out.print("Введите отчество автора (если есть): ");
        author.setPatronymic(in.nextLine());
        System.out.print("Введите фамилию автора: ");
        author.setSurname(in.nextLine());
        System.out.print("Введите псевдоним автора (если есть): ");
        author.setAlias(in.nextLine());
        author = MainQuery.getAuthor(author, isCreateIfNotExist, managerFactory);
        return author;
    }

    private static Boolean checkIntent() throws JsonProcessingException {
        System.out.println("Что вы хотите сделать?");
        System.out.println("1. Начать вводить характеристики книги");
        System.out.println("2. Вернуться в меню");
        switch (in.nextLine()) {
            case "1" -> {
                return true;
            }
            case "2" -> {
                menu();
            }
        }
        return false;
    }

    private static void findBook() throws JsonProcessingException {
        System.out.println("1. Список всех книг в библиотеке");
        System.out.println("2. Найти расположение определенной книги");
        System.out.println("3. Вернуться в меню");
        switch (in.nextLine()) {
            case "1" -> {
                List<BookCopy> data = MainQuery.getAllBookCopy(managerFactory);
                for (BookCopy d : data) {
                    System.out.println(toJson(d));
                }
            }
            case "2" -> {
                Book book = inputBook(false);
                List<BookCopy> bookCopies = MainQuery.getBookCopy(book, managerFactory);
                if (bookCopies.size() != 0) {
                    for (BookCopy bookCopy : bookCopies) {
                        System.out.println(toJson(bookCopy));
                    }
                } else {
                    System.out.println("Такой книги не найдено");
                }
            }
            case "3" -> {
                menu();
            }
            default -> findBook();
        }
    }


    private static void addBook() throws JsonProcessingException {
        if (checkIntent()) {
            BookCopy bookCopy = inputBookCopy(true);
            //adding book
            MainCRUD.createObjectInDB(bookCopy, managerFactory);
            System.out.println("книга добавлена\n");
        }
    }

    private static void deleteBook() throws JsonProcessingException {
        System.out.println("1. Начать вводить характеристики книги");
        System.out.println("2. Вернуться в меню");
        switch (in.nextLine()) {
            case "1" -> {
                Book book = inputBook(false);
                List<BookCopy> bookCopies = MainQuery.getBookCopy(book, managerFactory);
                if (bookCopies.size() != 0) {
                    for (int i = 0; i < bookCopies.size(); i++) {
                        System.out.println("Книга номер " + (i + 1) + ". ");
                        System.out.println(toJson(bookCopies.get(i)));
                    }
                    System.out.println("Введите номер книги из списка, которую хотите удалить");
                    int num = in.nextInt();
                    num--;
                    if (num < bookCopies.size() && num > -1) {
                        MainCRUD.deleteBookCopy(bookCopies.get(num), managerFactory);
                        System.out.println("книга удалена\n");
                    } else {
                        System.out.println("Книги под таким номером не существует");
                    }
                } else {
                    System.out.println("Такой книги не найдено");
                }
            }
            case "2" -> {
                menu();
            }
        }
    }

    private static void moveBook() throws JsonProcessingException {
        BookCase bookCaseBefore = new BookCase();
        Shelf shelfBefore = new Shelf();
        BookCase bookCaseAfter = new BookCase();
        Shelf shelfAfter = new Shelf();
        if (checkIntent()) {
            System.out.print("Номер стелажа, где стояла книга: ");
            bookCaseBefore.setName(in.nextLine());
            System.out.print("Номер полки, где стояла книга: ");
            shelfBefore.setName(in.nextLine());
            System.out.print("Номер стелажа, где будет стоять книга: ");
            bookCaseAfter.setName(in.nextLine());
            System.out.print("Номер полки, где будет стоять книга: ");
            shelfAfter.setName(in.nextLine());
            bookCaseAfter.setShelves(Set.of(shelfAfter));
            shelfAfter.setBookCase(Set.of(bookCaseAfter));
            bookCaseAfter = MainQuery.getBookCase(bookCaseAfter, managerFactory);
            shelfAfter = MainQuery.getShelf(shelfAfter, managerFactory);
            //moving book
            List<BookCopy> bookCopies = MainQuery.getBookCopy(bookCaseBefore, shelfBefore, managerFactory);
            if (bookCopies.size() != 0) {
                for (int i = 0; i < bookCopies.size(); i++) {
                    System.out.println("Книга номер " + (i + 1) + ". ");
                    System.out.println(toJson(bookCopies.get(i)));
                }
                System.out.println("Введите номер книги из списка, которую хотите переместить");
                int num = in.nextInt();
                num--;
                if (num < bookCopies.size() && num > -1) {
                    BookCopy bookCopy = bookCopies.get(num);
                    bookCopy.setBookCase(bookCaseAfter);
                    bookCopy.setShelf(shelfAfter);
                    try {
                        MainCRUD.updateBookCopy(bookCopy, managerFactory);
                    } catch (Exception e) {
                        MainCRUD.updateBookCopy(bookCopy, managerFactory);
                    }
                    System.out.println("расположение изменено\n");
                } else {
                    System.out.println("Книги под таким номером не существует");
                }
            } else {
                System.out.println("Такой книги не найдено");
            }
        }
    }

    public static void main(String[] args) throws JsonProcessingException {
        entry();
    }

    private static String toJson(Object o) throws JsonProcessingException {
        return new ObjectMapper().setSerializationInclusion(JsonInclude.Include.NON_NULL).writerWithDefaultPrettyPrinter().writeValueAsString(o);
    }

    private static void entry() throws JsonProcessingException {
        System.out.println("Добро пожаловать в библиотеку!");
        boolean access = false;
        while (!access) {
            System.out.println("1. Войти в систему\n2. Зарегистрироваться\n3. Закрыть программу");
            String mode = in.nextLine();
            switch (mode) {
                case "1" -> {
                    access = authentication();
                    if (!access) {
                        System.out.println("Логин или пароль неверный");
                    }
                }
                case "2" -> {
                    identification();
                }
                case "3" -> {
                    System.exit(0);
                }
            }
        }
        menu();
    }

    private static void menu() throws JsonProcessingException {
        boolean flag = true;
        while (flag) {
            System.out.println("Что вы хотите сделать?");
            System.out.println("1. Найти книгу");
            System.out.println("2. Добавить книгу");
            System.out.println("3. Удалить книгу");
            System.out.println("4. Переместить книгу");
            System.out.println("5. Сменить аккаунт");
            System.out.println("6. Уйти из библиотеки");
            switch (in.nextLine()) {
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
                    entry();
                }
                case "6" -> {
                    flag = false;
                    System.exit(0);
                }
                default -> {
                    System.out.println("Введите номер");
                }
            }
        }
    }
}
