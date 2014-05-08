
package cryptohelper;

import java.sql.SQLException;

/**
 *
 * @author Mattia Cerrato, mattia.cerrato[at]studenti.unito[dot]it
 */
public class DBTest {
    public static void main(String[] args) {
        String out = "";
        try {
            DBController db = DBController.getInstance();
        } catch(SQLException e) {
            System.out.println(e.getMessage());
        }
        //String result = db.execute("select * from CRYPTO_USER.USERS");
        //System.out.println(result);
    }
}
