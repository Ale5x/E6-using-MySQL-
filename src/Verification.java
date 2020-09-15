import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

public class Verification {
    private  String passwordCipherResult;
    private StringBuilder passwordCipher;

    private ConnectionDB conn = new ConnectionDB();
    private Statement statement = null;
    private ResultSet resultSet = null;
    private PreparedStatement preparedStatement = null;

    /*
            Верификация пользователя. Полученные данные проверяются на null и email на соответствие
     стандартов email: если не будет символа `@`, то будет сообщение о проверки введенного email.
     Затем создается запрос в БД. Из БД получаем email и password пользователей и сравнивается с
     тем email, который ввёл пользователь при входе, после подтверждения, сравниваются пароли.
     Пароль в БД зашифрован => веденный пользователем пароль, проходит этап шифрования и только
     потом сравнивается, если пароль проходит проверку, метод возвращает true иначе вернет false.
     */
    public boolean verificationUser(String email, String password) {
        if (!(email == null) && !(password == null)) {
            if (email.matches("\\b[A-Za-z0-9._%+-]+@[A-Za-z0-9.-]+\\.[A-Za-z]{2,4}\\b")) {
                if (!(conn.connection() == null)) {
                    try {
                        if (!conn.connection().isClosed()) {
                            statement = conn.connection().createStatement();
                            resultSet = statement.executeQuery("select * from user_data;");
                            while (resultSet.next()) {
                                String emailUs = resultSet.getString("email");
                                if (emailUs.toLowerCase().equals(email.toLowerCase())) {
                                    String passwordCipher = cipherPassword(password);
                                    String passwordUs = resultSet.getString("password");
                                    if (passwordCipher.equals(passwordUs)) {
                                        return true;
                                    }
                                }
                            }
                        }
                    } catch (SQLException sqlE) {
                        sqlE.printStackTrace();
                        System.out.println("Connection in the verification last");
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
        }
        return false;
    }

    /*
            Регистрация нового пользователя. Пришедшие данные проверяются на null, если они проходят
        проверку, то затем проверяется email на соответствие стандартам email (наличие символа "@" и тд).
        После проверки проверяется connection() на null. Затем создается подключение к БД и формируется
        запрос о наличие email в БД, если совпадений нет, то происходит регистрация пользователя.
     */
    public boolean userRegistration(String login, String email, String password) {
        if(!(login == null) || !(email == null) || !(password == null)) {
            if(email.matches("\\b[A-Za-z0-9._%+-]+@[A-Za-z0-9.-]+\\.[A-Za-z]{2,4}\\b")) {
                try {
                    if(!(conn.connection() == null)) {
                        if (!conn.connection().isClosed()) {
                            statement = conn.connection().createStatement();
                            resultSet = statement.executeQuery("SELECT Count(email) FROM user_data WHERE email='" + email.trim() + "';");
                            int countEmailUser = 0;
                            while (resultSet.next()) {
                                countEmailUser = resultSet.getInt(1);
                            }
                            if (countEmailUser == 0) {
                                resultSet = statement.executeQuery("SELECT Count(login) FROM user_data WHERE login='" + login.trim() + "';");
                                int countLoginUser = 0;
                                while (resultSet.next()) {
                                    countLoginUser = resultSet.getInt(1);
                                }
                                if (countLoginUser == 0) {
                                    String QUERY = "insert into user_data (email, login, password, user_access) values (?, ?, ?, ?);";
                                    preparedStatement = conn.connection().prepareStatement(QUERY);
                                    preparedStatement.setString(1, email);
                                    preparedStatement.setString(2, login);
                                    preparedStatement.setString(3, cipherPassword(password));
                                    preparedStatement.setBoolean(4, false);
                                    preparedStatement.execute();
                                    System.out.println("Регистрация прошла успешно, войдите в систему, чтобы продолжить...");
                                    return true;
                                } else {
                                    System.out.println("Login busy...");
                                }
                            } else {
                                System.out.println("Email busy...");
                            }
                        }
                    }
                } catch (SQLException eSQL) {
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
        }
        return false;
    }

    /*
    Алгоритм шифрования
     */
    public String cipherPassword(String passwordUser) {
        try {
            MessageDigest digest = MessageDigest.getInstance("MD5");
            byte[] bytes = digest.digest(passwordUser.getBytes());
            passwordCipher = new StringBuilder();
            for (byte b : bytes) {
                passwordCipher.append(String.format("%02X", b));
            }
            passwordCipherResult =  passwordCipher.toString();
        }catch (NoSuchAlgorithmException e) {
            e.getStackTrace();
        }
        return  passwordCipherResult;
    }

    /*
        Получение id пользователя по email. Необходим для дальйшего взаимодействия с библиотекой.
    Проверяется email на соответствие стандартам email (наличие символа "@" и тд) и на null, после этого
    создается запрос в БД, где по email получают его id.
     */
    public int getIdUser(String email) {
        if(!(email == null)) {
            if(email.matches("\\b[A-Za-z0-9._%+-]+@[A-Za-z0-9.-]+\\.[A-Za-z]{2,4}\\b")) {
                try {
                    if(!(conn.connection() == null)) {
                        statement = conn.connection().createStatement();
                        resultSet = statement.executeQuery("select id from user_data where email='" + email.trim() + "';");
                        while (resultSet.next()) {
                            int id = resultSet.getInt("id");
                            return id;
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
            }
        }
        return -1;
    }
}