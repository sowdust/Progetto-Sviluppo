
package cryptohelper;

import java.sql.*;

/**
 *
 * @author Mattia Cerrato, mattia.cerrato[at]studenti.unito[dot]it
 */
public class DBTest {
    public static void main(String[] args) {
        String out = "";
        DBController db = DBController.getInstance();
        String result = db.execute("select * from ATEAM.USERS");
        System.out.println(result);
    }
}
