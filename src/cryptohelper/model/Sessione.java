package cryptohelper.model;

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
import java.util.ArrayList;

public class Sessione {
    
    private int id;
    private final UserInfo proprietario;
    private final Messaggio messaggio;
    public AlberoIpotesi albero;
    
    public Sessione(UserInfo proprietario, Messaggio messaggio) {
        this.id = -1;
        this.proprietario = proprietario;
        this.messaggio = messaggio;        
    }
    
    // SOLO PER TESTING!
    public void setAlbero(AlberoIpotesi albero) {
        this.albero = albero;
    }
    public void setId(int id) {
        this.id = id;
    }
    
    public static Sessione load(int id) throws SQLException, IOException, ClassNotFoundException {
        
        String url = "jdbc:derby://localhost:1527/crypto_db";
        String user = "crypto_user";
        String pwd = "crypto_pass";    
        Connection conn = DriverManager.getConnection(url, user, pwd);
        PreparedStatement stmt = conn.prepareStatement("SELECT * FROM sessione WHERE id = 12");
        
        ResultSet crs = stmt.executeQuery();
        crs.next();
        
        byte[] buf = crs.getBytes("albero");
	ObjectInputStream objectIn = null;
        objectIn = new ObjectInputStream(new ByteArrayInputStream(buf));             

        Sessione sess = new Sessione(UserInfo.load(crs.getInt("proprietario")), Messaggio.load(crs.getInt("messaggio")));
        sess.setAlbero((AlberoIpotesi) objectIn.readObject());
        sess.setId(crs.getInt("id"));
        
        crs.close();
        return sess;

    }
    
    public boolean save() throws SQLException, IOException {
        
        // Seriealizziamo l'albero
        ByteArrayOutputStream bos = new ByteArrayOutputStream();
        ObjectOutputStream oos = new ObjectOutputStream(bos);
        oos.writeObject(albero);
        oos.close();
        
        
        String url = "jdbc:derby://localhost:1527/crypto_db";
        String user = "crypto_user";
        String pwd = "crypto_pass";
        ArrayList a = new ArrayList();

        PreparedStatement stmt;
        Connection conn = DriverManager.getConnection(url, user, pwd);
        
        if(id == -1) {
            stmt = conn.prepareStatement("INSERT INTO Sessione (proprietario, messaggio, albero) VALUES (?, ?, ?)");
            stmt.setInt(1,messaggio.getId());
            stmt.setInt(2,proprietario.getId());
            stmt.setObject(3,bos.toByteArray());

            //  IN QUALCHE MODO RESTITUIRE L'ID!!
            this.id = -2;
        } else {
            stmt = conn.prepareStatement("UPDATE Sessione SET albero = (?) WHERE id = (?)");
            stmt.setObject(1,bos.toByteArray());
            stmt.setInt(2,id);
        }
        
        stmt.executeUpdate();
        stmt.close();
        
        return false;
    }

}
