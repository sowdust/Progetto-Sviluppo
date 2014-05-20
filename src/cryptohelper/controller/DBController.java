package cryptohelper.controller;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

/**
 *
 * @author Mattia Cerrato, mattia.cerrato[at]studenti.unito[dot]it
 */
public class DBController {
    /*
     NOTE:
     - la classe DBController viene istanziata una sola volta (singleton)
     e mantenere un oggetto "connessione al db"
     - ho importato la libreria "java db driver" per poter usare derby
     - ho separato la generazione del resultSet dall'analisi del resultSet. ora non ha senso, in futuro dovrebbe averne
     */

    private static DBController instance = null;
    private static final String url = "jdbc:derby://localhost:1527/crypto_db";
    private final String user = "crypto_user";
    private final String pwd = "crypto_pass";
    private Connection conn;

    private DBController() throws SQLException {
        conn = DriverManager.getConnection(url, user, pwd);
    }

    public ResultSet execute(String query, Object... args) throws SQLException {
        PreparedStatement stmt = conn.prepareStatement(query);
        for (int i = 0; i < args.length; i++) {
            stmt.setObject(i + 1, args[i]);
        }
        stmt.execute();
        return stmt.getResultSet();
    }

    public boolean executeUpdate(String query, Object... args) throws SQLException {
        PreparedStatement stmt = conn.prepareStatement(query);
        for (int i = 0; i < args.length; i++) {
            stmt.setObject(i + 1, args[i]);
        }
        int nrow = stmt.executeUpdate();
        return nrow != 0;
    }

    public static DBController getInstance() throws SQLException {
        if (instance == null) {
            instance = new DBController();
        }
        return instance;
    }

    public void close() throws SQLException {
        conn.close();
    }

}
