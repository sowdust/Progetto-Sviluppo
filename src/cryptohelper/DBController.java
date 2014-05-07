
package cryptohelper;

import java.sql.Connection;
import java.sql.DriverManager;
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
    private String user;
    private String pwd;
    private Connection conn;
    
    private DBController() {}
    
    public String execute(String query) {
        String out = "";
        Statement st; // TODO
        ResultSet rs;
        try {
            conn = DriverManager.getConnection(url, "cryptouser", "cryptopass"); //ateam è username e password
            st = conn.createStatement();
            rs = st.executeQuery(query);
            out = analyzeResult(rs);
            rs.close();
            st.close();
            conn.close();
        } catch(SQLException e) {
            System.out.println(e.getMessage());
        }
        return out;
    }
    
    private String analyzeResult(ResultSet rs) {
        String out = "";
        try {
            while(rs.next()) {
                out = out + "ID: "+ rs.getInt("ID") + "; NOME: " + rs.getString("firstName") 
                      + "; COGNOME: " + rs.getString("secondName") + "\n";
            }
        } catch(SQLException e) {
            System.out.println(e.getMessage());
        }
        return out;
    }
    
    public static DBController getInstance() {
        if(instance == null) {
            instance = new DBController();
        }
        else {
            System.out.println("warning: db già inizializzato");
        }
        return instance;
    }
        
}
