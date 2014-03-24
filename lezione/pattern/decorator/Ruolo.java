public abstract class Ruolo implements PersonaConRuolo {
    PersonaConRuolo component; // può farlo perchè PersonaConRuolo può essere anche un vero oggetto, a patto che la classe di quell'oggetto implementi PersonaConRuolo
    
    public Ruolo(PersonaConRuolo p) {
        component = p;
    }

    public void whois() { //il meta-ruolo della classe Ruolo all'interno della struttura/pattern decorator è quello di essere 
                          // l'abstract decorator, quindi di richiamare il metodo whois sul component
        component.whois();
    }

    // l'idea del pattern decorator è quello di avere un core di classi ai quali vado ad aggiungere funzionalità. per questo motivo implemento Studente e Lavoratore,
    // che estendono Ruolo
}
