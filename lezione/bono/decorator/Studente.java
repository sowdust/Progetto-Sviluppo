public class Studente extends Ruolo { //si noti che il pattern decorator non dà la possibilità di avere informazioni proprie di una classe, ma solo
    String uni;                       //della classe più generica PersonaConRuolo e quindi ai metodi definiti per tutte le classi. potrei dichiarare tutti i 
                                      //metodi delle classi più specifiche dentro PersonaConRuolo, ma allora se sono richiamati da classi sbagliate? un pasticcio
    public Studente (PersonaConRuolo p, String u) {
        super(p); //richiama quello di Ruolo
        uni = u;
    }

    public void whois() {
        super.whois();
        sonoStud();
    }

    public void sonoStud() {
        System.out.println("Studio presso "+uni);
    }
}
