package cryptohelper.model;

import cryptohelper.controller.DBController;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
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
        CachedRowSet crs = dbc.execute("SELECT * FROM sessione WHERE id = ?", id);
        
        String url = "jdbc:derby://localhost:1527/crypto_db";
        String user = "crypto_user";
        String pwd = "crypto_pass";    
        Connection conn = DriverManager.getConnection(url, user, pwd);
        PreparedStatement stmt = conn.prepareStatement("SELECT * FROM sessione WHERE id = " + id);
        
        ResultSet rs = stmt.executeQuery();
        if(!crs.next()) {
            throw new RuntimeException("oooops1");
            
        }
        if(!rs.next()) {
            throw new RuntimeException("oooops2");
            
        }
        byte[] buf = crs.getBytes("albero");
	ObjectInputStream objectIn = null;
        objectIn = new ObjectInputStream(new ByteArrayInputStream(buf));             

        Sessione sess = new Sessione(UserInfo.load(crs.getInt("proprietario")), Messaggio.load(crs.getInt("messaggio")));
        sess.setAlbero((AlberoIpotesi) objectIn.readObject());
        sess.setId(crs.getInt("id"));
        
        //crs.close();
        return sess;

    }
    
    public void save() throws SQLException, IOException {
        ByteArrayOutputStream bos = new ByteArrayOutputStream();
        ObjectOutputStream  oos = new ObjectOutputStream(bos);
        oos.writeObject(albero);
        oos.close();
        DBController dbc = DBController.getInstance();
        String q = "INSERT INTO Sessione (proprietario, messaggio, albero) VALUES (?, ?, ?)";
        id = dbc.executeInsert(q,messaggio.getId(),proprietario.getId(),bos.toByteArray());   
    }
    
    public boolean faiAssunzione(MappaturaParziale map) {
        return albero.faiAssunzione(map);
    }
    
    public void salvaSoluzione() {
        MappaturaParziale map = albero.getStato();
        
        throw new UnsupportedOperationException();
    }

}
