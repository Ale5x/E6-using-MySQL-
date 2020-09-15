import javax.mail.*;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardOpenOption;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.Properties;

public class UserAction {
    private int numberChoice = 77;
    private boolean isNext = true;
    private String lineEquals = "=================";
    private boolean access;
    private String message;
    private String user;
    private int id;

    private BufferedReader reader = new BufferedReader(new InputStreamReader(System.in));
    private CatalogBooks catalogBooks = new CatalogBooks();
    private StringBuilder stringBuilder = null;
    private ConnectionDB conn = new ConnectionDB();
    private Statement statement = null;
    private ResultSet resultSet = null;
    private Calendar date = new GregorianCalendar();

    /*
    В данном методе представлено основное меню библиотеки и варианты работы для пользователей и администраторов.
     */
    public void work(int id) {
        user = verificationAdmin(id);
        this.id = id;
        try {
            while (isNext) {
                System.out.print("\n" + lineEquals);
                System.out.print("Действия пользователя");
                System.out.print(lineEquals + "\n");
                System.out.println();
                System.out.println("1 - Просмотр каталога; 2 - Поиск по автору; 3 - Поиск по произведению; \n4 - Предложить книгу; 5 - Сохранение каталога на жесткий диск; 0 - Выход.");
                System.out.print("\n" + lineEquals);
                System.out.print("Действия Администратора");
                System.out.print(lineEquals + "\n");
                System.out.println();
                System.out.println("6 - Добавление книги в каталог; 7 - Просмотр пользователей;\n8 - Добавление пользователя в администраторы; 9 - Перевод из администраторов в пользователи");
                System.out.print("Выбеите действие - ");
                String lineChoiceNumber = reader.readLine();
                if(isInteger(lineChoiceNumber)) {
                    numberChoice = Integer.parseInt(lineChoiceNumber);
                }else{
                    System.out.println("\nВведено не число... Повторите ввод... Введите число...");
                    System.out.print("\nНажмите \"Enter\", чтобы продолжить ");
                    String enter = reader.readLine();
                }
                System.out.println();
                switch (numberChoice) {
                    case 0:
                        isNext = false;
                        System.out.println("Работа с библиотекой завершена... До свидания...");
                        break;
                    case 1:
                        catalogBooks.watchListPage();
                        System.out.println("Действие завершено");
                        break;
                    case 2:
                        catalogBooks.searchAuthor();
                        System.out.println("Действие завершено");
                        break;
                    case 3:
                        catalogBooks.searchNameTitle();
                        System.out.println("Действие завершено");
                        break;
                    case 4:
                        SuggestNewBook();
                        System.out.println("Действие завершено");
                        break;
                    case 5:
                        saveCatalogBooks();
                        System.out.println("Действие завершено");
                        break;
                    case 6:
                        if(access) {
                            catalogBooks.setBookList();
                            System.out.println("Действие завершено");
                        } else System.out.println("У вас нет прав доступа к данному разделу...\n");
                        break;
                    case 7:
                        if(access) {
                            lookUsers();
                            System.out.println("Действие завершено");
                        } else System.out.println("У вас нет прав доступа к данному разделу...\n");
                        break;
                    case 8:
                        if(access){
                            newAdmin();
                            System.out.println("Действие завершено");
                        } else System.out.println("У вас нет прав доступа к данному разделу...\n");
                        break;
                    case 9:
                        if(access) {
                            backInUser();
                            System.out.println("Действие завершено");
                        } else System.out.println("У вас нет прав доступа к данному разделу...\n");
                        break;
                    default:
                        System.out.println("Выберите действие!");
                        break;
                }
            }
        }catch (IOException e) {
            System.out.println("Error - " + e.getStackTrace());
        }
    }

    /*
    Метод производит верификацию администратора. Необходим для работы в разделах с доступом администратора
     */
    public String verificationAdmin(int id) {
        try  {
            if(!(conn.connection() == null)) {
                if(!conn.connection().isClosed()) {
                    statement = conn.connection().createStatement();
                    resultSet = statement.executeQuery("select user_access, login from user_data where id=" + id + ";");
                    while (resultSet.next()) {
                        String admin = resultSet.getString("login");
                        boolean accessUs = resultSet.getBoolean("user_access");
                        this.access = accessUs;
                        if (access) {
                            this.user = admin;
                            return admin;
                        }
                    }
                }
            }
        }catch (SQLException e) {
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
        return null;
    }

    /*
        Сообщение пользователя для администратора насчёт добавления книги в книжный фонд.
    В сообщение будет приведено сообщение пользователя, название книги, кто отправил
    сообщение и дата отправки сообщения. Сообщение отправляется на email администратору.
    А также сообщение сохраняется в корень проекта "Library/Message for Admin"
    Если директория не создана, то создается директория "Message for Admin".
    */
    private void SuggestNewBook() {
        try {
            System.out.print("Введите автора произведения - ");
            String author = reader.readLine();
            System.out.println();
            System.out.print("Введите название произведения - ");
            String nameBook = reader.readLine();
            System.out.println();
            System.out.print("Введите тип книги (бумажный или электронный) - ");
            String typeBook = reader.readLine();
            System.out.println("Введите ваше сообщение:");
            message = reader.readLine();
            String messageNext = message + "\n\"" + author + " - " + nameBook + "\"." + typeBook + "\n\nUser - " + user;

            String book = author + " - " + nameBook + ". " + typeBook;
            SimpleDateFormat dateFormat = new SimpleDateFormat("y.MM.d HH.mm.ss");
            String newDate = dateFormat.format(new Date());

            if(!Files.exists(Paths.get("Library"))) {
                Files.createDirectory(Paths.get("Library"));
            }
            if(!Files.exists(Paths.get("Library/Message for Admin"))) {
                Files.createDirectory(Paths.get("Library/Message for Admin"));
            }

            Path newBookInTheCatalog = Paths.get("Library/Message for Admin/! Message user for Admin from " + user + " - " + newDate +".txt");
            if(!Files.exists(newBookInTheCatalog)) {
                Files.createFile(newBookInTheCatalog);
                Files.write(newBookInTheCatalog, "Сообщение пользователя: ".getBytes(), StandardOpenOption.APPEND);
                Files.write(newBookInTheCatalog, message.getBytes(), StandardOpenOption.APPEND);
                Files.write(newBookInTheCatalog, "\n".getBytes(), StandardOpenOption.APPEND);
                Files.write(newBookInTheCatalog, "Предложенная книга - ".getBytes(), StandardOpenOption.APPEND);
                Files.write(newBookInTheCatalog, book.getBytes(), StandardOpenOption.APPEND);
                Files.write(newBookInTheCatalog, "\n\n".getBytes(), StandardOpenOption.APPEND);
                Files.write(newBookInTheCatalog, "********************\n\n".getBytes(), StandardOpenOption.APPEND);
                Files.write(newBookInTheCatalog, "User - ".getBytes(), StandardOpenOption.APPEND);
                Files.write(newBookInTheCatalog, user.getBytes(), StandardOpenOption.APPEND);
                Files.write(newBookInTheCatalog, "\n\n".getBytes(), StandardOpenOption.APPEND);
                Files.write(newBookInTheCatalog, date.getTime().toString().getBytes(), StandardOpenOption.APPEND);
            }

            messageOnEmail(messageNext);

            System.out.println("Message send successfully");
        } catch (IOException e) {
            e.printStackTrace();

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /*
        Отправка сообщения администратору от пользователя. В данном случае главным администратором считается
    "Admin@gmail.com" (email принят условно).
     */
    private void messageOnEmail(String messageUser) throws Exception {

        final Properties proporties = new Properties();

        proporties.put("mail.smtp.auth", "true");
        proporties.put("mail.smtp.starttls.enable", "true");
        proporties.put("mail.smtp.host", "smtp.gmail.com");
        proporties.put("mail.smtp.port", "587");

        String myAccountEmail = "projectjava20@gmail.com";
        String myAccountPassword = "1999AlG1999";

        Session session = Session.getInstance(proporties, new Authenticator() {
            @Override
            protected PasswordAuthentication getPasswordAuthentication() {
                return new PasswordAuthentication(myAccountEmail, myAccountPassword);
            }
        });

        Message message = prepareMessage(session, myAccountEmail, "Admin@gmail.com", messageUser);
        Transport.send(message);
        System.out.println("Message send successfully");
    }

    private Message prepareMessage(Session session, String myAccountEmail, String reception, String messageUser) throws Exception {
        try {
            Message message = new MimeMessage(session);
            message.setFrom(new InternetAddress(myAccountEmail));
            message.setRecipient(Message.RecipientType.TO, new InternetAddress(reception));
            message.setSubject("Message to administrator from user");
            message.setText("Hello." + "\n" + messageUser);
            return message;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    /*
        Просмотр списка пользователей зарегистрируемых в библеотеке: Email и login
    Также доступен выбор для просмотра полных данных пользователей Email, login,
    Password и уровень доступа.
     */
    public void lookUsers() {
        System.out.println("User - " + user);
        try {
            if (!conn.connection().isClosed()) {
                if (access) {
                    System.out.println("Вывод данных пользователей...");
                    System.out.println("\nid/login/email/access\n");
                    statement = conn.connection().createStatement();
                    resultSet = statement.executeQuery("select * from user_data;");
                    while (resultSet.next()) {
                        int idUser = resultSet.getInt("id");
                        String loginUser = resultSet.getString("login");
                        String emailUser = resultSet.getString("email");
                        boolean accessUser = resultSet.getBoolean("user_access");
                        System.out.println("id - " + idUser + ". login - " + loginUser + ". Email - " + emailUser + ". Access - " + accessUser);
                    }
                    boolean isNextTwo = true;
                    while (isNextTwo) {
                        System.out.println("\n1 - Сохранить данные в файл; 0 - Выход");
                        System.out.print("Действие - ");
                        int choice = 77;
                        String lineChoiceNumber = reader.readLine();
                        if(isInteger(lineChoiceNumber)) {
                            choice = Integer.parseInt(lineChoiceNumber);
                        }else{
                            System.out.println("\nВведено не число... Повторите ввод... Введите число...");
                            System.out.print("\nНажмите \"Enter\", чтобы продолжить ");
                            String enter = reader.readLine();
                        }
                        switch (choice) {
                            case 1:
                                saveUsersData();
                                isNextTwo = false;
                                break;
                            case 0:
                                isNextTwo = false;
                                break;
                            default:
                                isNextTwo = false;
                                break;
                        }
                    }
                }
            }
            System.out.print("\nНажмите \"Enter\", чтобы продолжить ");
            String enter = reader.readLine();
        } catch (IOException e) {

        } catch (SQLException sqlE) {

        }
    }

    /*
       Сохранение всех данных пользователя в файл формата txt. Доступно сохранение по умолчанию
   и на указанный путь. Происходит сохранение всех пользователей, а также пользователей, которые
   имеются в списке администраторов, имя пользователя/администратора, который сохраняет данные и дата.
    */
    private void saveUsersData() {
        try {
            System.out.println("\n1 - Сохранение данных в корень проекта; 2 - Указать путь пользователя");
            System.out.print("Выбеите действие - ");
            int number = 77;
            String lineChoiceNumber = reader.readLine();
            if(isInteger(lineChoiceNumber)) {
                number = Integer.parseInt(lineChoiceNumber);
            }else{
                System.out.println("\nВведено не число... Повторите ввод... Введите число...");
                System.out.print("\nНажмите \"Enter\", чтобы продолжить ");
                String enter = reader.readLine();
            }
            String accessString;
            Path fileUser;
            switch (number) {
                case 1:
                    fileUser = Paths.get("Library/Library users data.txt");
                    break;
                case 2:
                    System.out.print("Укажите путь - ");
                    String path = reader.readLine();
                    System.out.print("Укажите имя файла - ");
                    String nameFile = reader.readLine();

                    // Замена символов "\" на "/"
                    fileUser = Paths.get(path.replaceAll("/", "\\") + "/" + nameFile + ".txt");
                    break;
                default:
                    fileUser = Paths.get("Library/Library users data.txt");
                    System.out.println("Файл будет сохранен по умолчанию... " + fileUser.getParent());
                    break;
            }

            if (!(fileUser == null)) {
               if(!Files.exists(Paths.get("Library"))) {
                   Files.createDirectory(Paths.get("Library"));
               }
               if(!Files.exists(Paths.get("Library/Library data archive"))) {
                   Files.createDirectory(Paths.get("Library/Library data archive"));
               }
                if(Files.exists(fileUser)) {
                    if (Files.exists(fileUser)) {
                        SimpleDateFormat dateFormat = new SimpleDateFormat("y.MM.d HH.mm.ss");
                        String newDate = dateFormat.format(new Date());
                        Files.copy(fileUser, Paths.get("Library/Library data archive/Copy file Library users data "
                                + newDate +".txt"));
                        Files.deleteIfExists(fileUser);
                    }
                    Files.deleteIfExists(fileUser);
                }
                if (!Files.exists(fileUser)) {
                    Files.createFile(fileUser);
                    Files.write(fileUser, "id/login/email/access/password\r\n".getBytes(), StandardOpenOption.APPEND);
                    resultSet = statement.executeQuery("select * from user_data;");
                    while (resultSet.next()) {
                        int idUser = resultSet.getInt("id");
                        String passwordUser = resultSet.getString("password");
                        String loginUser = resultSet.getString("login");
                        String emailUser = resultSet.getString("email");
                        boolean accessUser = resultSet.getBoolean("user_access");
                        if (accessUser) {
                            accessString = "Доступ администратора";
                        } else {
                            accessString = "Доступ пользователя";
                        }
                        Files.write(fileUser, "\r\n".getBytes(), StandardOpenOption.APPEND);
                        Files.write(fileUser, Integer.toString(idUser).getBytes(), StandardOpenOption.APPEND);
                        Files.write(fileUser, "/".getBytes(), StandardOpenOption.APPEND);
                        Files.write(fileUser, loginUser.getBytes(), StandardOpenOption.APPEND);
                        Files.write(fileUser, "/".getBytes(), StandardOpenOption.APPEND);
                        Files.write(fileUser, emailUser.getBytes(), StandardOpenOption.APPEND);
                        Files.write(fileUser, "/".getBytes(), StandardOpenOption.APPEND);
                        Files.write(fileUser, accessString.getBytes(), StandardOpenOption.APPEND);
                        Files.write(fileUser, "/".getBytes(), StandardOpenOption.APPEND);
                        Files.write(fileUser, passwordUser.getBytes(), StandardOpenOption.APPEND);
                    }
                    Files.write(fileUser, "\r\n\r\n".getBytes(), StandardOpenOption.APPEND);
                    Files.write(fileUser, "User/Admin - ".getBytes(), StandardOpenOption.APPEND);
                    Files.write(fileUser, user.getBytes(), StandardOpenOption.APPEND);
                    Files.write(fileUser, "\r\n".getBytes(), StandardOpenOption.APPEND);
                    Files.write(fileUser, date.getTime().toString().getBytes(), StandardOpenOption.APPEND);
                }
                if (Files.exists(fileUser)) {
                    System.out.println("Файл сохранен...");
                } else System.out.println("Файл не сохранен...");
            }
        } catch (IOException e) {

        }catch (SQLException sqlE) {

        }
    }

    /*
        Добавление пользователя в администраторы. Администратор вводит email пользователя,
    которого необходимо добавить в список администраторов. Создается запрос в БД об
    предоставлении уровня доступа "true" данному пользователю, чей email указал другой
    администратор. И выведется соответствущее сообщение об добавлении в администраторы либо нет
     */
    public void newAdmin() {
        try {
            System.out.print("Добавление пользователя в администраторы...\nВведите email пользователя: ");
            String emailNewAdmin = reader.readLine();
            if(!conn.connection().isClosed()) {
                String QUERY_UPDATE = "update user_data set user_access=true where email='" + emailNewAdmin.trim() + "';";
                statement = conn.connection().createStatement();
                int row = statement.executeUpdate(QUERY_UPDATE);
                if(row == 1) {
                    System.out.println("Пользователь '" + emailNewAdmin + "' добавлен в администраторы");
                }else {
                    System.out.println("Пользователь '" + emailNewAdmin + "' не добавлен в администраторы... Проверьте правильность введённых данных...");
                }
            }
            System.out.print("\nНажмите \"Enter\", чтобы продолжить ");
            String enter = reader.readLine();
        }catch (IOException e) {
            e.printStackTrace();
        }catch (SQLException sqlE) {
            sqlE.printStackTrace();
        }
    }

    /*
        Возврат пользователя из администраторов в обычные пользователи. Администратор вводит
    email пользователя, которого необходимо убрать из списока администраторов. Создается запрос
     в БД об предоставлении уровня доступа "false" данному пользователю, чей email указал
    администратор. И выведется соответствущее сообщение об итоге данной операции.
     */
    public void backInUser() {
        try {
            System.out.print("Перевод пользователя из администраторов в пользователи...\nВведите email пользователя: ");
            String emailBackUser = reader.readLine();
            if(!(emailBackUser.toLowerCase().trim().equals("Admin@gmail.com".toLowerCase()))) {
                if (!conn.connection().isClosed()) {
                    String QUERY_UPDATE = "update user_data set user_access=false where email='" + emailBackUser.trim() + "';";
                    statement = conn.connection().createStatement();
                    int row = statement.executeUpdate(QUERY_UPDATE);
                    if (row == 1) {
                        System.out.println("Пользователь '" + emailBackUser + "' переведен из администраторов в пользователи...");
                    } else {
                        System.out.println("Проверьте правильность введённых данных... Администратор '" + emailBackUser + "' не переведён в пользователи...");
                    }
                }
            }else {
                System.out.println("Пользователя \"" + emailBackUser + "\" нельзя понизить до обычного пользователя...");
            }
            System.out.print("\nНажмите \"Enter\", чтобы продолжить ");
            String enter = reader.readLine();
        }catch (IOException e) {
            e.printStackTrace();
        }catch (SQLException sqlE) {
            sqlE.printStackTrace();
        }
    }

    /*
    Сохранение каталога книг на диск D либо по пути, который укажет пользователь
     */
    public void saveCatalogBooks() {

        try {
            System.out.println("\n1 - Сохранение на диск D; 2 - Указать путь пользователя");
            System.out.print("Выбеите действие - ");
            int number = 77;
            String lineChoiceNumber = reader.readLine();
            if(isInteger(lineChoiceNumber)) {
                number = Integer.parseInt(lineChoiceNumber);
            }else{
                System.out.println("\nВведено не число... Повторите ввод... Введите число...");
                System.out.print("\nНажмите \"Enter\", чтобы продолжить ");
                String enter = reader.readLine();
            }
            Path fileCatalogBooks;
            switch (number) {
                case 1:
                    fileCatalogBooks = Paths.get("D:/Catalog books.txt");
                    break;
                case 2:
                    System.out.print("Укажите путь - ");
                    String path = reader.readLine();
                    System.out.print("Укажите имя файла - ");
                    String nameFile = reader.readLine();
                    fileCatalogBooks = Paths.get(path.replaceAll("/", "\\") +  "/" + nameFile + ".txt");
                    break;
                default:
                    fileCatalogBooks = Paths.get("D:/Catalog books.txt");
                    System.out.println("Файл будет сохранен по умолчанию... " + fileCatalogBooks.getParent());
                    break;
            }
            if (!Files.exists(fileCatalogBooks)) {
                Files.createFile(fileCatalogBooks);

                    if (!conn.connection().isClosed()) {
                        statement = conn.connection().createStatement();
                        resultSet = statement.executeQuery("select * from book_catalog;");
                        while (resultSet.next()) {
                            String author = resultSet.getString("author");
                            String bookTitle = resultSet.getString("book_title");
                            int id = resultSet.getInt("id");
                            String type = resultSet.getString("type");

                            Files.write(fileCatalogBooks, String.valueOf(id).getBytes(), StandardOpenOption.APPEND);
                            Files.write(fileCatalogBooks, ". ".getBytes(), StandardOpenOption.APPEND);
                            Files.write(fileCatalogBooks, author.getBytes(), StandardOpenOption.APPEND);
                            Files.write(fileCatalogBooks, " - ".getBytes(), StandardOpenOption.APPEND);
                            Files.write(fileCatalogBooks, bookTitle.getBytes(), StandardOpenOption.APPEND);
                            Files.write(fileCatalogBooks, ". Тип книги - ".getBytes(), StandardOpenOption.APPEND);
                            Files.write(fileCatalogBooks, type.getBytes(), StandardOpenOption.APPEND);
                            Files.write(fileCatalogBooks, "\n".getBytes(), StandardOpenOption.APPEND);
                        }
                    }

                Files.write(fileCatalogBooks, "\n".getBytes(), StandardOpenOption.APPEND);
                Files.write(fileCatalogBooks, "User - ".getBytes(), StandardOpenOption.APPEND);
                Files.write(fileCatalogBooks, user.getBytes(), StandardOpenOption.APPEND);
                Files.write(fileCatalogBooks, "\n".getBytes(), StandardOpenOption.APPEND);
                Files.write(fileCatalogBooks, date.getTime().toString().getBytes(), StandardOpenOption.APPEND);

                System.out.println("Файл сохранён на диск...");
            }

            System.out.println("Нажмите \"Enter\" чтобы продолжить...");
            String enter = reader.readLine();
        }catch (IOException e) {
            System.out.println("Ошибка в сохранении каталога на диск");
            e.printStackTrace();
        }catch (SQLException e) {

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