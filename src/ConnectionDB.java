import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class ConnectionDB {
    private final String URL = "jdbc:mysql://localhost:3306/library?useUnicode=true&serverTimezone=UTC";
    private final String USER = "root";
    private final String PASSWORD = "Al667395";

    /*
    Создание connection к БД.
     */
    public Connection connection() {
        try {
            Connection connection = null;
            connection = DriverManager.getConnection(URL, USER, PASSWORD);
            if (!connection.isClosed()) {
                return connection;
            } else {
                System.out.println("Connection failed...");
            }
        } catch (SQLException eSQL) {
            eSQL.printStackTrace();
        }
        return null;
    }
}