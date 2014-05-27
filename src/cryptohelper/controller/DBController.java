package cryptohelper.controller;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import javax.sql.rowset.CachedRowSet;
import javax.sql.rowset.RowSetProvider;

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
     */

    private static DBController instance = null;
    private final String url = "jdbc:derby://localhost/crypto_db";
    private final String user = "crypto_user";
    private final String pwd = "crypto_pass";

    private DBController() {
    }

    public CachedRowSet execute(String query, Object... args) throws SQLException {
        Connection conn = null;
        PreparedStatement stmt = null;
        ResultSet rs = null;
        CachedRowSet crs = null;
        try {
            conn = DriverManager.getConnection(url, user, pwd);
            stmt = conn.prepareStatement(query);
            for (int i = 0; i < args.length; i++) {
                stmt.setObject(i + 1, args[i]);
            }
            stmt.execute();
            rs = stmt.getResultSet();
            crs = RowSetProvider.newFactory().createCachedRowSet();
            crs.populate(rs);
        } finally {
            if (rs != null) {
                rs.close();
            }
            if (stmt != null) {
                stmt.close();
            }
            if (conn != null) {
                conn.close();
            }
        }
        return crs;
    }

    public boolean executeUpdate(String query, Object... args) throws SQLException {
        Connection conn = null;
        PreparedStatement stmt = null;
        int nrow = 0;
        try {
            conn = DriverManager.getConnection(url, user, pwd);
            stmt = conn.prepareStatement(query);
            for (int i = 0; i < args.length; i++) {
                stmt.setObject(i + 1, args[i]);
            }
            nrow = stmt.executeUpdate();
        } finally {
            if (stmt != null) {
                stmt.close();
            }
            if (conn != null) {
                conn.close();
            }
        }
        return nrow != 0;
    }

    public int executeInsert(String query, Object... args) throws SQLException {
        Connection conn = null;
        PreparedStatement stmt = null;
        ResultSet generatedKey = null;
        int nrow = 0;
        try {
            conn = DriverManager.getConnection(url, user, pwd);
            stmt = conn.prepareStatement(query, Statement.RETURN_GENERATED_KEYS);
            for (int i = 0; i < args.length; i++) {
                stmt.setObject(i + 1, args[i]);
            }
            nrow = stmt.executeUpdate();
            if (nrow == 0) {
                throw new SQLException("Inserimento fallito.");
            }
            generatedKey = stmt.getGeneratedKeys();
            generatedKey.next();
            return generatedKey.getInt(1);
        } finally {
            if (generatedKey != null) {
                generatedKey.close();
            }
            if (stmt != null) {
                stmt.close();
            }
            if (conn != null) {
                conn.close();
            }
        }
    }

    public static DBController getInstance() throws SQLException {
        if (instance == null) {
            instance = new DBController();
        }
        return instance;
    }

}
