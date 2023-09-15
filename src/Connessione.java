import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Date;

public class Connessione {

    private static final String DB_URL = "jdbc:mysql://localhost:3306/Libreria";
    private static final String DB_USER = "root";
    private static final String DB_PASSWORD = "";

    // Metodo per ottenere una connessione al database
    public static Connection getConnection() throws SQLException {
        return DriverManager.getConnection(DB_URL, DB_USER, DB_PASSWORD);
    }

    // Metodo per eseguire una query e ottenere i risultati come lista di libri
    
    public static List<Libro> ricercaLibri(OpzioneRicerca opzioni) {
        List<Libro> risultati = new ArrayList<>();
        Connection connection = null;
        PreparedStatement preparedStatement = null;
        ResultSet resultSet = null;

        try {
            connection = getConnection();

            String query = "SELECT * FROM Libri WHERE 1=1"; // Parte iniziale della query

            // Verifica quali criteri di ricerca sono stati specificati e aggiungili alla query
            if (opzioni.getDataInizio() != null) {
                // Se è stata specificata una data di inizio, aggiungi il criterio alla query
                query += " AND Inizio >= ?";
            }

            if (opzioni.getDataFine() != null) {
                // Se è stata specificata una data di fine, aggiungi il criterio alla query
                query += " AND (Fine IS NULL OR Fine <= ?)";
            }

            if (opzioni.getNome() != null && !opzioni.getNome().isEmpty()) {
                // Se è stato specificato un nome, aggiungi il criterio alla query
                query += " AND Nome LIKE ?";
            }

            if (opzioni.getAutore() != null && !opzioni.getAutore().isEmpty()) {
                // Se è stato specificato un autore, aggiungi il criterio alla query
                query += " AND Autore LIKE ?";
            }

            preparedStatement = connection.prepareStatement(query);

            // Imposta i parametri nella PreparedStatement
            int parameterIndex = 1;
            if (opzioni.getDataInizio() != null) {
                preparedStatement.setDate(parameterIndex++, new java.sql.Date(opzioni.getDataInizio().getTime()));
            }

            if (opzioni.getDataFine() != null) {
                preparedStatement.setDate(parameterIndex++, new java.sql.Date(opzioni.getDataFine().getTime()));
            }

            if (opzioni.getNome() != null && !opzioni.getNome().isEmpty()) {
                preparedStatement.setString(parameterIndex++, "%" + opzioni.getNome() + "%");
            }

            if (opzioni.getAutore() != null && !opzioni.getAutore().isEmpty()) {
                preparedStatement.setString(parameterIndex, "%" + opzioni.getAutore() + "%");
            }

            // Esegui la query
            resultSet = preparedStatement.executeQuery();

            while (resultSet.next()) {
                int id = resultSet.getInt("ID");
                String nome = resultSet.getString("Nome");
                String autore = resultSet.getString("Autore");
                Date inizio = resultSet.getDate("Inizio");
                Date fine = resultSet.getDate("Fine");
                boolean inCorso = resultSet.getBoolean("In_Corso");

                Libro libro = new Libro(id, nome, autore, inizio, fine, inCorso);
                risultati.add(libro);
            }
        } catch (SQLException ex) {
            ex.printStackTrace();
        } finally {
            closeResources(connection, preparedStatement, resultSet);
        }

        return risultati;
    }
    
    public static void inserisciLibro(String nome, String autore, String inizio, String fine, boolean inCorso) {
        Connection connection = null;
        PreparedStatement preparedStatement = null;
        
        String query = "INSERT INTO Libri (Nome, Autore, Inizio, Fine, In_Corso) VALUES (?, ?, ?, ?, ?)";
        
        try {
            connection = Connessione.getConnection();
            preparedStatement = connection.prepareStatement(query);
            
            preparedStatement.setString(1, nome);
            preparedStatement.setString(2, autore);
            preparedStatement.setString(3, inizio);
            
            if (fine == null || fine.isEmpty()) {
                preparedStatement.setNull(4, java.sql.Types.DATE);
            } else {
                preparedStatement.setString(4, fine);
            }
            
            preparedStatement.setBoolean(5, inCorso);
            
            preparedStatement.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
            
        } finally {
            try {
                if (preparedStatement != null) {
                    preparedStatement.close();
                }
                if (connection != null) {
                    connection.close();
                }
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
    }
    // Altri metodi per inserire, aggiornare e gestire i libri nel database

    // Metodo per chiudere le risorse di database
    private static void closeResources(Connection connection, PreparedStatement preparedStatement, ResultSet resultSet) {
        try {
            if (resultSet != null) resultSet.close();
            if (preparedStatement != null) preparedStatement.close();
            if (connection != null) connection.close();
        } catch (SQLException ex) {
            ex.printStackTrace();
        }
    }
}