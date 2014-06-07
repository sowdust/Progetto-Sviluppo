package cryptohelper.model;

import cryptohelper.controller.DBController;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.ObjectOutputStream;
import java.sql.SQLException;
import java.util.List;
import java.util.Stack;
import javax.sql.rowset.CachedRowSet;

public class Sessione {

    private int id;
    private final UserInfo proprietario;
    private final MessaggioSpia messaggio;
    private AlberoIpotesi albero = null;

    public Sessione(UserInfo proprietario, MessaggioSpia messaggio) {
        this.id = -1;
        this.proprietario = proprietario;
        this.messaggio = messaggio;
        this.albero = new AlberoIpotesi();
    }

    public Sessione(CachedRowSet crs) throws SQLException {
        this.id = crs.getInt("id");
        this.messaggio = Messaggio.load(crs.getInt("messaggio"));
        this.proprietario = UserInfo.load(crs.getInt("proprietario"));
    }

    public AlberoIpotesi getAlbero() {
        if (null == this.albero) {
            this.albero = AlberoIpotesi.load(this.id);
        }
        return this.albero;
    }

    public static Sessione load(int id) throws SQLException, IOException, ClassNotFoundException {

        DBController dbc = DBController.getInstance();
        CachedRowSet crs = dbc.execute("SELECT id, messaggio, proprietario FROM Sessione WHERE id = ?", id);
        if (crs.next()) {
            return new Sessione(crs);
        } else {
            return null;
        }
    }

    public int getId() {
        return this.id;
    }

    public boolean save() throws SQLException, IOException {
        ByteArrayOutputStream bos = new ByteArrayOutputStream();
        ObjectOutputStream oos = new ObjectOutputStream(bos);
        oos.writeObject(getAlbero());
        oos.close();
        DBController dbc = DBController.getInstance();
        if (id < 0) {
            id = dbc.executeInsert("INSERT INTO Sessione (proprietario, messaggio, albero) VALUES (?, ?, ?)", proprietario.getId(), messaggio.getId(), bos.toByteArray());
            return id != -1;
        }
        return dbc.executeUpdate("UPDATE Sessione SET albero =  ?", bos.toByteArray());
    }

    public boolean faiAssunzione(Mappatura nuoveAssunzioni) {
        return getAlbero().faiAssunzione(nuoveAssunzioni);
    }

    public boolean salvaSoluzione() throws SQLException {
        Mappatura mapCorrente = getAlbero().getMappaturaCorrente();
        List<Character> listaCaratteri = messaggio.getSimboli();
        if (!mapCorrente.isCompleta(listaCaratteri)) {
            for (char c : listaCaratteri) {
                System.out.println(c);
            }
            return false;
        }
        Soluzione s = new Soluzione(mapCorrente, messaggio, proprietario);
        if (s.save()) {
            return this.elimina();
        } else {
            throw new IllegalStateException("Problemi durante il salvataggio della soluzione nel db");
        }
    }

    public boolean elimina() throws SQLException {
        DBController dbc = DBController.getInstance();
        return dbc.executeUpdate("DELETE FROM Sessione WHERE id = ?", id);
    }

    public void undo(String motivazione) {
        getAlbero().undo(motivazione);
    }

    public Mappatura getMappaturaCorrente() {
        return getAlbero().getMappaturaCorrente();
    }

    public boolean caricaSoluzione(Soluzione sol) {
        Mappatura mappaturaVuota = getMappaturaCorrente().rimuoviTutti();
        Mappatura nuovaMappatura = mappaturaVuota.merge(sol.getMappatura());
        return faiAssunzione(nuovaMappatura);
    }

    public String getCommento() {
        return getAlbero().getCommento();
    }

    public MessaggioSpia getMessaggio() {
        return messaggio;
    }

    @Override
    public String toString() {
        return "Mittente: " + messaggio.getDestinatario() + " Destinatario: " + messaggio.getMittente() + " Titolo: " + messaggio.getTitolo();
    }

    public UserInfo getProprietario() {
        return proprietario;
    }

    public Stack<Ipotesi> getMosse() {
        return getAlbero().getMosse();
    }

}
