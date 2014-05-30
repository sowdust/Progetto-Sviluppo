package cryptohelper.model;

import cryptohelper.controller.DBController;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.sql.Blob;
import java.sql.SQLException;
import java.util.List;
import javax.sql.rowset.CachedRowSet;

public class Sessione {

    private int id;
    private final UserInfo proprietario;
    private final Messaggio messaggio;
    public AlberoIpotesi albero;

    public Sessione(UserInfo proprietario, Messaggio messaggio) {
        this.id = -1;
        this.proprietario = proprietario;
        this.messaggio = messaggio;
        this.albero = new AlberoIpotesi();
    }
    
    public Sessione(CachedRowSet crs) throws SQLException, IOException, ClassNotFoundException {
        this.id = crs.getInt("id");
        this.messaggio = Messaggio.load(crs.getInt("messaggio"));
        this.proprietario = UserInfo.load(crs.getInt("proprietario"));
        Blob bl = crs.getBlob("albero");
        byte[] buf = bl.getBytes(1, (int) bl.length());
        ObjectInputStream objectIn;
        objectIn = new ObjectInputStream(new ByteArrayInputStream(buf));
        this.albero = ((AlberoIpotesi) objectIn.readObject());
    }

    public static Sessione load(int id) throws SQLException, IOException, ClassNotFoundException {

        DBController dbc = DBController.getInstance();
        CachedRowSet crs = dbc.execute("SELECT * FROM Sessione WHERE id = ?", id);
        crs.next();
        return new Sessione(crs);
    }
    
    public int getId() {
        return this.id;
    }

    public boolean save() throws SQLException, IOException {
        ByteArrayOutputStream bos = new ByteArrayOutputStream();
        ObjectOutputStream oos = new ObjectOutputStream(bos);
        oos.writeObject(albero);
        oos.close();
        DBController dbc = DBController.getInstance();
        String q = "INSERT INTO Sessione (proprietario, messaggio, albero) VALUES (?, ?, ?)";
        id = dbc.executeInsert(q, messaggio.getId(), proprietario.getId(), bos.toByteArray());
        return id != -1;
    }

    public boolean faiAssunzione(MappaturaParziale nuoveAssunzioni) {
        return albero.faiAssunzione(nuoveAssunzioni);
    }

    public boolean salvaSoluzione() throws SQLException {
        MappaturaParziale map = albero.getMappaturaCorrente();
        List<Character> listaCaratteri = messaggio.getSimboli();
        if(map.isCompleta(listaCaratteri)) {
            throw new IllegalStateException("La mappatura non copre tutti i caratteri usati nel messaggio");
        }
        Soluzione s = new Soluzione(map,messaggio,proprietario);
        if(s.save()) {
           return this.elimina();
        } else {
            throw new IllegalStateException("Problemi durante il salvataggio della soluzione nel db");
        }
    }

    public boolean elimina() throws SQLException {
        DBController dbc = DBController.getInstance();
        return dbc.executeUpdate("DELETE * FROM crypto_user.Sessione WHERE id = ?", id);        
    }

    public void undo(String motivazione) {
        albero.undo(motivazione);
    }

    public boolean caricaSoluzione(Soluzione sol) {
        throw new UnsupportedOperationException("Not supported yet.");
    }

}
