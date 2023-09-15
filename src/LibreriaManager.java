import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class LibreriaManager {
	
	public static List<Libro> ricercaLibri(OpzioneRicerca opzioni){
	    return Connessione.ricercaLibri(opzioni);
	}
    
    public static void inserisciLibro(String nome, String autore, String inizio, String fine, boolean inCorso) {
    	Connessione.inserisciLibro(nome, autore, inizio, fine, inCorso);
    }
}
