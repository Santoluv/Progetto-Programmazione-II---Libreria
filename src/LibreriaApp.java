import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.List;


public class LibreriaApp {

	private static JComboBox<String> ricercaComboBox;
    private static JTextField ricercaField;
    private static JButton ricercaButton;
    private static JTextArea risultatiArea;
    private static JPanel dettagliPanel;
    private static JList<String> risultatiList;
    private static DefaultListModel<String> listModel = new DefaultListModel<>();
    
    
    public static void createAndShowGUI() {
        JFrame frame = new JFrame("Libreria App");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        JPanel topPanel = new JPanel(new FlowLayout(FlowLayout.LEFT));
        ricercaComboBox = new JComboBox<>(new String[]{"Nome", "Autore", "Periodo Specifico", "Ultimo Mese", "Ultimi 3 Mesi"});
        ricercaField = new JTextField(20);
        ricercaButton = new JButton("Ricerca");

        topPanel.add(new JLabel("Seleziona opzione di ricerca:"));
        topPanel.add(ricercaComboBox);
        topPanel.add(new JLabel("Inserisci termine di ricerca:"));
        topPanel.add(ricercaField);
        topPanel.add(ricercaButton);

        dettagliPanel = new JPanel(new BorderLayout());
        risultatiList = new JList<>(listModel);
        
        frame.add(dettagliPanel, BorderLayout.EAST);
        frame.add(topPanel, BorderLayout.NORTH);
        frame.add(new JScrollPane(risultatiList), BorderLayout.WEST);

        JButton chiudiButton = new JButton("Chiudi");
        JButton homeButton = new JButton("Home");

        JPanel bottomPanel = new JPanel(new FlowLayout(FlowLayout.LEFT));
        bottomPanel.add(homeButton);
        bottomPanel.add(chiudiButton);
        frame.add(bottomPanel, BorderLayout.SOUTH);
        frame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);

        // Gestione dell'azione del pulsante di ricerca
        
        ricercaButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                String opzioneRicerca = (String) ricercaComboBox.getSelectedItem();
                String searchTerm = ricercaField.getText();

                // Creare un oggetto OpzioniRicerca in base all'opzione selezionata
                
                OpzioneRicerca opzioni = creaOpzioniRicerca(opzioneRicerca, searchTerm);

                List<Libro> risultati = LibreriaManager.ricercaLibri(opzioni);
                mostraRisultati(risultati);
            }
        });

        chiudiButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                frame.dispose();
            }
        });

        homeButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                frame.dispose();
                Home.createAndShowGUI();
            }
        });

        frame.pack();
        frame.setLocationRelativeTo(null); // Centra la finestra
        frame.setVisible(true);
    }

    // Crea un oggetto OpzioniRicerca in base all'opzione selezionata dall'utente
    
    private static OpzioneRicerca creaOpzioniRicerca(String opzione, String searchTerm) {
        OpzioneRicerca opzioni = new OpzioneRicerca();

        switch (opzione) {
            case "Nome":
                opzioni.setNome(searchTerm);
                break;
            case "Autore":
                opzioni.setAutore(searchTerm);
                break;
            case "Periodo Specifico":
                // Imposta le date del periodo specifico nelle opzioni
                // Assicurati di validare e convertire le date in modo appropriato
                break;
            case "Ultimo Mese":
                // Imposta le opzioni per cercare libri nell'ultimo mese
                break;
            case "Ultimi 3 Mesi":
                // Imposta le opzioni per cercare libri negli ultimi 3 mesi
                break;
            default:
                break;
        }

        return opzioni;
    }

    // Restituisci i risultati della ricerca nell'area dei risultati
    
    private static void mostraRisultati(List<Libro> libri) {
        listModel.clear(); // Pulisce la lista prima di aggiungere nuovi elementi

        if (libri.isEmpty()) {
            listModel.addElement("Nessun risultato trovato.");
        } else {
            for (Libro libro : libri) {
                String info = //"ID: " + libro.getId() + " | " + 
                		  "Nome: " + libro.getNome() + " | "
                        + "Autore: " + libro.getAutore(); //+ " | "
                        //+ "Data di inizio: " + libro.getInizio() + " | "
                        //+ "Data di fine: " + libro.getFine() + " | ";

                /*if (libro.isInCorso()) {
                    info += "In Corso";
                } else {
                    info += "Finito2";
                }*/

                listModel.addElement(info); // Aggiungi ciascun elemento alla lista
            }
        }
    }
}
    
    