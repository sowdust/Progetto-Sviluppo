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

    public int getId() {
        return this.id;
    }

    // SOLO PER TESTING!
    public void setAlbero(AlberoIpotesi albero) {
        this.albero = albero;
    }

    public void setId(int id) {
        this.id = id;
    }

    public static Sessione load(int id) throws SQLException, IOException, ClassNotFoundException {

        DBController dbc = DBController.getInstance();
        CachedRowSet crs = dbc.execute("SELECT * FROM Sessione WHERE id = ?", id);
        crs.next();
        Blob bl = crs.getBlob("albero");
        byte[] buf = bl.getBytes(1, (int) bl.length());

        ObjectInputStream objectIn;
        objectIn = new ObjectInputStream(new ByteArrayInputStream(buf));

        Sessione sess = new Sessione(UserInfo.load(crs.getInt("proprietario")), Messaggio.load(crs.getInt("messaggio")));
        sess.setAlbero((AlberoIpotesi) objectIn.readObject());
        sess.setId(crs.getInt("id"));
        return sess;
    }

    public void save() throws SQLException, IOException {
        ByteArrayOutputStream bos = new ByteArrayOutputStream();
        ObjectOutputStream oos = new ObjectOutputStream(bos);
        oos.writeObject(albero);
        oos.close();
        DBController dbc = DBController.getInstance();
        String q = "INSERT INTO Sessione (proprietario, messaggio, albero) VALUES (?, ?, ?)";
        id = dbc.executeInsert(q, messaggio.getId(), proprietario.getId(), bos.toByteArray());
    }

    public boolean faiAssunzione(MappaturaParziale map) {
        return albero.faiAssunzione(map);
    }

    public boolean salvaSoluzione() throws SQLException {
        MappaturaParziale map = albero.getStato();
        List<Character> listaCaratteri = messaggio.getSimboli();
        if(map.isCompleta(listaCaratteri)) {
            return false;
        }
        Soluzione s = new Soluzione(map,messaggio,proprietario);
        if(s.save()) {
           return this.elimina();
        } else {
            return false;
        }
    }

    private boolean elimina() throws SQLException {
        DBController dbc = DBController.getInstance();
        return dbc.executeUpdate("DELETE * FROM crypto_user.Sessione WHERE id = ?", id);        
    }

}
