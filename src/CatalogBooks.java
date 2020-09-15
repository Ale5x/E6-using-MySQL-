import java.io.BufferedReader;
import java.io.IOException;
import java.sql.*;
import java.io.InputStreamReader;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

public class CatalogBooks {

    private int numberChoice = 77;
    private String author;
    private String nameBook;
    private String typeBook;
    private String type;
    private String word;
    private String QUERY;
    private boolean isNext = true;
    private int resultCount = 0;
    private int limit = 20;
    private int offset = 0;
    private String lineEquals = "=====================";

    private BufferedReader reader = new BufferedReader(new InputStreamReader(System.in));
    private Statement statement = null;
    private PreparedStatement preparedStatement = null;
    private ResultSet resultSet = null;
    private ConnectionDB conn = new ConnectionDB();
    private JavaMailUtill mailUtill = new JavaMailUtill();
    private StringBuilder sbBook = new StringBuilder();

    /*
        Постраничный просмотр каталога книг. На страние 20 книг, возможность совершать действия: вперёд, назад и
    переход на указанную страницу, а также выход из просмотра. При выборе действия (вперёд, назад и переход на
    указанную страницу), происходит проверка типа данных: если введена строка, будет предупреждение.
     */
    public void watchListPage() {
        System.out.println("Просмотр каталога...");
        try {
            int page = 1;
            int maxIdBook = 0;
            Statement statement = conn.connection().createStatement();
            String QUERY_START = "select * from book_catalog limit 20 offset 0;";
            String QUERY_MAX_ID = "select max(id) from book_catalog;";
            resultSet = statement.executeQuery(QUERY_MAX_ID);
            while (resultSet.next()) {
                maxIdBook = resultSet.getInt(1);
            }
            resultSet = statement.executeQuery(QUERY_START);
            while (resultSet.next()) {
                String author = resultSet.getString("author");
                String bookTitle = resultSet.getString("book_title");
                int id = resultSet.getInt("id");
                String type = resultSet.getString("type");
                if (!(author == null) || !(bookTitle == null)) {
                    System.out.println(id + ". " + author + " " + bookTitle + ". Тип книги - " + type);
                }
            }
            System.out.print(lineEquals);
            System.out.print(page);
            System.out.println(lineEquals);
            while (isNext) {
                System.out.println("1 - Предыдущая страница; 2 - Переход к заданной страние; 3 - Следущая страница; 0 - Выход ");
                System.out.print("\nДействие \n");
                String lineChoiceNumber = reader.readLine();
                int numberChoice = 77;
                if(isInteger(lineChoiceNumber)) {
                    numberChoice = Integer.parseInt(lineChoiceNumber);
                }else{
                    System.out.println("\nВведено не число... Повторите ввод... Введите число...");
                    System.out.print("\nНажмите \"Enter\", чтобы продолжить ");
                    String enter = reader.readLine();
                }
                if (numberChoice == 0) {
                    System.out.println("Конец просмотра... До свидание...\n");
                    isNext = false;
                } else if (numberChoice == 1) {
                    page--;
                    if(page > 0) {
                        offset = (page * limit) - limit;
                    }else {
                        page = 1;
                        offset = (page * limit) - limit;
                    }
                } else if (numberChoice == 2) {
                    System.out.print("Введите номер страницы: ");

                    String linePage = reader.readLine();
                    if(isInteger(linePage)) {
                        page = Integer.parseInt(lineChoiceNumber);
                    }else{
                        System.out.println("\nВведено не число... Повторите ввод... Введите число...");
                    }
                    offset = (page * limit) - limit;
                } else if (numberChoice == 3) {
                    page++;
                    offset = (page * limit) - limit;
                } else {
                    System.out.println("Сделайте правильный выбор либо нажмите '0'");
                }
                if(!(maxIdBook > (page * limit))) {
                    page = maxIdBook / limit;
                    offset = (page * limit) - limit;
                }
                String QUERY = "select * from book_catalog limit 20 offset ";
                resultSet = statement.executeQuery(QUERY + Integer.toString(offset) + ";");
                while (resultSet.next()) {
                    String author = resultSet.getString("author");
                    String bookTitle = resultSet.getString("book_title");
                    int id = resultSet.getInt("id");
                    String type = resultSet.getString("type");
                    if (!(author == null) || !(bookTitle == null)) {
                        System.out.println(id + ". " + author + " " + bookTitle + ". Тип книги - " + type);
                    }
                }
                System.out.print(lineEquals);
                System.out.print(page);
                System.out.println(lineEquals);
            }
        }catch (SQLException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }finally {
            if(!(resultSet == null)) {
                try {
                    resultSet.close();
                }catch (SQLException e) {

                }
            }
            if(!(statement == null)) {
                try {
                    statement.close();
                }catch (SQLException e) {

                }
            }
            if(!(conn.connection() == null)) {
                try {
                    conn.connection().close();
                }catch (SQLException e) {

                }
            }
        }
    }

    /*
        Поиск книги по названию. Пользователь вводит название книги, формируется запрос в БД и получение результатов,
    имеется информация о количестве найденых результатов.
     */
    public void searchNameTitle() {
        try {
            if(!conn.connection().isClosed()) {
                System.out.print("Введите ключевое слово в произведении - ");
                word = reader.readLine();
                System.out.println("Поиск произведения по ключевому слову либо фразе - \"" + word + "\"");
                statement = conn.connection().createStatement();
                QUERY = "SELECT * from book_catalog WHERE book_title LIKE '%" + word + "%';";
                resultSet = statement.executeQuery(QUERY);
                while (resultSet.next()) {
                    int id = resultSet.getInt("id");
                    String author = resultSet.getString("author");
                    String book_title = resultSet.getString("book_title");
                    System.out.println("id - " + id + " " + author + " - " + book_title + ". Тип книги - " + type);
                    resultCount++;
                }
                System.out.println("\nПоиск завершен. Колличество найденных результатов - " + resultCount);
                System.out.print("\nНажмите \"Enter\", чтобы продолжить ");
                String enter = reader.readLine();
                resultCount = 0;
            }
        }catch (SQLException e) {
            e.printStackTrace();
        }catch (IOException e) {
            e.getStackTrace();
        }finally {
            if(!(resultSet == null)) {
                try {
                    resultSet.close();
                }catch (SQLException e) {

                }
            }
            if(!(statement == null)) {
                try {
                    statement.close();
                }catch (SQLException e) {

                }
            }
            if(!(conn.connection() == null)) {
                try {
                    conn.connection().close();
                }catch (SQLException e) {

                }
            }
        }
    }

    /*
        Поиск книги по автору. Пользователь вводит имя автора книги, формируется запрос в БД и получение результатов,
    имеется информация о количестве найденых результатов.
     */
    public void searchAuthor() {
        try {
            System.out.print("Поиск по автору:\nВведите автора - ");
            word = reader.readLine().trim();
            System.out.println("Поиск автора по ключевому слову - \"" + word + "\"");
            if(!conn.connection().isClosed()) {
                statement = conn.connection().createStatement();
                String QUERY = "SELECT * FROM book_catalog WHERE author LIKE '%" + word + "%';";
                resultSet = statement.executeQuery(QUERY);
                while (resultSet.next()) {
                    String author = resultSet.getString("author");
                    String bookTitle = resultSet.getString("book_title");
                    int idBook = resultSet.getInt("id");
                    String type = resultSet.getString("type");
                    resultCount++;
                    System.out.println("id - " + idBook + " " + author + " - " + bookTitle + ". Тип книги - " + type);
                }
                System.out.println("\nПоиск завершен. Колличество найденных результатов - " + resultCount);
                System.out.print("\nНажмите \"Enter\", чтобы продолжить ");
                String enter = reader.readLine();
                resultCount = 0;
            }
        }catch (IOException e) {
            System.out.println("Error - " + e);
        }catch (SQLException eSQL) {
            eSQL.printStackTrace();
        }finally {
            if(!(resultSet == null)) {
                try {
                    resultSet.close();
                }catch (SQLException e) {

                }
            }
            if(!(statement == null)) {
                try {
                    statement.close();
                }catch (SQLException e) {

                }
            }
            if(!(conn.connection() == null)) {
                try {
                    conn.connection().close();
                }catch (SQLException e) {

                }
            }
        }
    }

    /*
        Регистрация книги в книжном фонде библиотеки. Администратор вводит название, автора и тип книги, формируется
    запрос в БД и отправляется в БД, после этого формируется сообщение пользователям о появлении новой книги в
    библиотеке, которое отправляется на email пользователям.
     */
    public void setBookList() {
        System.out.println("Регистрации книги в книжном фонде библиотеке");
        while (isNext) {
            try {
                System.out.print("Введите имя автора: ");
                author = reader.readLine();
                System.out.println();
                System.out.print("Введите название книги: ");
                nameBook = reader.readLine();
                System.out.print("\nВведите тип книги (бумажный либо электронный): ");
                typeBook = reader.readLine();
                if ("Электронный".toLowerCase().startsWith(typeBook.toLowerCase().trim())|| "Электронный".toLowerCase().endsWith(typeBook.toLowerCase().trim())) {
                    type = typeBook;
                } else if ("Бумажный".toLowerCase().startsWith(typeBook.toLowerCase().trim())|| "Бумажный".toLowerCase().endsWith(typeBook.toLowerCase().trim())) {
                    type = typeBook;
                } else {
                    System.out.println("Ошибка! Некоректно ведённые данные.");
                    type = "-";
                }
            } catch (NumberFormatException e) {
                System.err.println("Введите корректно данные ");
            } catch (IOException e) {
                System.err.println("Error " + e);
            }
            try {
                statement = conn.connection().createStatement();
                String QUERY_INSERT = "insert into book_catalog (author, book_title, type) values (?, ?, ?);";
                preparedStatement = conn.connection().prepareStatement(QUERY_INSERT);
                preparedStatement.setString(1, author);
                preparedStatement.setString(2, nameBook);
                preparedStatement.setString(3, type);
                preparedStatement.execute();

                resultSet = statement.executeQuery("select email from user_data;");
                String text = "Hello. \nWe have a new book in our the library.";
                sbBook.append("«");
                sbBook.append(nameBook);
                sbBook.append("»");
                sbBook.append(" - ");
                sbBook.append(author);
                while (resultSet.next()) {
                    String email = resultSet.getString("email");
                    mailUtill.sendMail(email, sbBook.toString(), text);
                }
                System.out.println("Message send successfully");
                System.out.println("Продолжить заполнение? 1 - да; 0 - нет");
                String lineChoiceNumber = reader.readLine();
                if(isInteger(lineChoiceNumber)) {
                    numberChoice = Integer.parseInt(lineChoiceNumber);
                }else{
                    System.out.println("\nВведено не число... Повторите ввод... Введите число...");
                    System.out.print("\nНажмите \"Enter\", чтобы продолжить ");
                    String enter = reader.readLine();
                }
                switch (numberChoice) {
                    case 0:
                        isNext = false;
                        break;
                    case 1:
                        break;
                    default:
                        System.out.println("Для продолжнения заполнения нажмите - 1 или 0 - для выхода");
                }
            } catch (NumberFormatException e){
                System.err.println("Введите корректно данные: введите число!");
            } catch (IOException e) {
                System.err.println("Error " + e);
            }catch (SQLException eSQL) {
                eSQL.printStackTrace();
            }catch (Exception e) {
                e.printStackTrace();
            }finally {
                if(!(resultSet == null)) {
                    try {
                        resultSet.close();
                    }catch (SQLException e) {

                    }
                }
                if(!(statement == null)) {
                    try {
                        statement.close();
                    }catch (SQLException e) {

                    }
                }
                if(!(conn.connection() == null)) {
                    try {
                        conn.connection().close();
                    }catch (SQLException e) {

                    }
                }
            }
        }
    }

    /*
         Метод проверят тип данных. Необходим для проверки, когда пользователь выбирает действие.
     Если пользователь вводит текст, то метод вернет false
     */
    public boolean isInteger(String s) {
        try {
            Integer.parseInt(s);
        } catch(NumberFormatException e) {
            return false;
        } catch(NullPointerException e) {
            return false;
        }
        return true;
    }
}