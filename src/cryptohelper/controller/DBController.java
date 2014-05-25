package cryptohelper.controller;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
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
    private final String url = "jdbc:derby://localhost:1527/crypto_db";
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

    public static DBController getInstance() throws SQLException {
        if (instance == null) {
            instance = new DBController();
        }
        return instance;
    }

}
