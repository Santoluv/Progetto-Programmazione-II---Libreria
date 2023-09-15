import java.util.Date;

public class Libro {

    private int id;
    private String nome;
    private String autore;
    private Date inizio;
    private Date fine;
    private boolean inCorso;
    //private byte[] immagine;

    public Libro(int id, String nome, String autore, Date inizio, Date fine, boolean inCorso/*, byte[] immagine*/) {
        this.id = id;
        this.nome = nome;
        this.autore = autore;
        this.inizio = inizio;
        this.fine = fine;
        this.inCorso = inCorso;
        //this.immagine = immagine;
    }

    public int getId() {
        return id;
    }

    public String getNome() {
        return nome;
    }

    public String getAutore() {
        return autore;
    }

    public Date getInizio() {
        return inizio;
    }

    public Date getFine() {
        return fine;
    }

    public boolean isInCorso() {
        return inCorso;
    }
    
    @Override
    public String toString() {
        return "ID: " + id + ",\nNome: " + nome + ",\nAutore: " + autore + ",\nInizio: " + inizio + ",\nFine: " + fine + ",\nIn Corso: " + inCorso +"\n";
    }

    //public byte[] getImmagine() {
        //return immagine;
    //}

    // Altri metodi se necessari...
}
