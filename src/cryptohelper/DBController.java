
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
    private final String user = "crypto_user";
    private final String pwd = "crypto_pass";
    private Connection conn;
    
    private DBController() {}
    
    public ResultSet execute(String query) {
        String out = "";
        Statement st; // TODO
        ResultSet rs = null;
        try {
            conn = DriverManager.getConnection(url, user, pwd); //ateam è username e password
            st = conn.createStatement();
            rs = st.executeQuery(query);
            /*
            out = analyzeResult(rs);
            rs.close();
            st.close();
            conn.close();
            */
        } catch(SQLException e) {
            System.out.println(e.getMessage());
        }
        //return out;
        return rs;
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
