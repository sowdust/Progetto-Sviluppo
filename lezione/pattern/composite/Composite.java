public class Composite {
    public ? op() {
        for each c in Children do c.op(); // qui il binding dinamico ci permette di chiamare magari
                                          // metodi completamente diversi per ogni children
    }

    // getChild, removeChild, getChild hanno senso solo in composite, ma devo implementarle anche in Leaf
    // perchè li definisco in Composition. non posso metterli direttamente in Composite perchè uccide
    // l'idea del pattern in cui voglio poter richiamare i metodi indipendentemente da se sono leaf
    // o composite. riferimento slides: 4_Guidi-... pagina 57 del file    
 }

