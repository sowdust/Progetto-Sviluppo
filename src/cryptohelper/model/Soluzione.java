package cryptohelper.model;

import cryptohelper.controller.DBController;
import java.sql.SQLException;
import javax.sql.rowset.CachedRowSet;

public class Soluzione {

    private int id;
    private final Mappatura mappatura;
    private final MessaggioSpia messaggio;
    private final UserInfo creatore;

    public Soluzione(Mappatura mappatura, MessaggioSpia messaggio, UserInfo creatore) {
        this.id = -1;
        this.creatore = creatore;
        this.mappatura = mappatura;
        this.messaggio = messaggio;
    }

    public Soluzione(CachedRowSet crs) throws SQLException {
        this.id = crs.getInt("id");
        this.creatore = UserInfo.load(crs.getInt("creatore"));
        this.messaggio = Messaggio.load(crs.getInt("messaggio"));
        this.mappatura = new Mappatura(crs.getString("mappatura"));
    }

    public boolean save() throws SQLException {
        DBController dbc = DBController.getInstance();
        if (id == -1) {
            String q = "INSERT INTO Soluzione (creatore, messaggio, mappatura) "
                    + "VALUES ( ?, ?, ? )";
            id = dbc.executeInsert(q, creatore.getId(), messaggio.getId(), mappatura.serialize());
            return id != -1;
        } else {
            String q = "UPDATE Soluzione SET mappatura = ?, WHERE id = ?";
            return dbc.executeUpdate(q, mappatura.serialize(), id);
        }
    }

    public static Soluzione load(int id) throws SQLException {
        DBController dbc = DBController.getInstance();
        CachedRowSet crs = dbc.execute("SELECT * FROM Soluzione WHERE id = ?", id);
        crs.next();
        return new Soluzione(crs);
    }

    public Mappatura getMappatura() {
        return mappatura;
    }

    public boolean elimina() throws SQLException {
        DBController dbc = DBController.getInstance();
        return dbc.executeUpdate("DELETE FROM Soluzione WHERE id = ?", id);
    }

    public String toString() {
        return "F:" + messaggio.getMittente()
                + " T: " + messaggio.getDestinatario()
                + " Oggetto:" + messaggio.getTitolo();

    }

}
