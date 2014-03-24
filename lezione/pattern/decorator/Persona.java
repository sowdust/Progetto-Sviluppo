public class Persona implements PersonaConRuolo {
    private String nome;
    private String cognome;

    public void whois() {
        System.out.println(nome+" "+cognome);
    }

}
