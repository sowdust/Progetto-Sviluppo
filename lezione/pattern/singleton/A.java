/*
Il pattern singleton risolve il problema di fare in modo che una certa classe possa essere istanziata una volta sola.
Ciò graficamente si rende con un numero 1 vicino al numero della classe.
A livello di codice si rappresenta con:
- un campo statico dello stesso tipo della classe
- il costruttore privato
- un metodo pubblico create che restituisce un oggetto A
  se è la prima volta che viene chiamato ne crea uno nuovo
  altrimenti chiama un'eccezione o ritorna l'oggetto già creato.
Posso fare piccole varianti dando la possibilità di creare un tot di istanze e non solo una.
In java il pattern singleton dà la possibilità di avere l'idea di variable globale a tutto il programma
cosa che in java solitamente non esiste.
*/
public class A {
    private static A istance;

    private A() {
    }

    public static A create() {
        if (instance == null) {
            instance = new A();
        }
        return instance;
    }
}


