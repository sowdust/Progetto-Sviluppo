/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package cryptohelper;

import java.sql.SQLException;
import java.util.List;

/**
 *
 * @author mat
 */
public class SistemaCifraturaTest  {
    
    public static void main(String[] args) throws SQLException {
        
        Studente st = new Studente(0,"tory","","","");
        
        
       List<SistemaCifratura> sis = SistemaCifratura.caricaSistemiCifratura(st);
       System.out.println(sis);
        
        
    }
}
