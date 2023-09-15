import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import com.toedter.calendar.JDateChooser;
import java.util.Date;
import java.text.SimpleDateFormat;

public class Inserisci {
	

    public static void createAndShowGUI() {
        JFrame frame = new JFrame("Inserisci Libro");
        frame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);

        JPanel panel = new JPanel(new GridLayout(6, 2));

        JLabel nomeLabel = new JLabel("Nome:");
        JTextField nomeField = new JTextField(20);
        panel.add(nomeLabel);
        panel.add(nomeField);

        JLabel autoreLabel = new JLabel("Autore:");
        JTextField autoreField = new JTextField(20);
        panel.add(autoreLabel);
        panel.add(autoreField);

        JLabel inizioLabel = new JLabel("Inizio:");
        JDateChooser inizioDateChooser = new JDateChooser();
        panel.add(inizioLabel);
        panel.add(inizioDateChooser);

        JLabel fineLabel = new JLabel("Fine:");
        JDateChooser fineDateChooser = new JDateChooser();
        panel.add(fineLabel);
        panel.add(fineDateChooser);

        JCheckBox inCorsoCheckBox = new JCheckBox("In Corso");
        panel.add(inCorsoCheckBox);

        JButton inserisciButton = new JButton("Inserisci");
        
        JButton homeButton = new JButton("Home");

        JPanel bottomPanel = new JPanel(new FlowLayout(FlowLayout.LEFT));
        bottomPanel.add(homeButton);
        bottomPanel.add(inserisciButton);
        
        frame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        
        frame.add(bottomPanel, BorderLayout.SOUTH);
        frame.add(panel);
        frame.pack();
        frame.setLocationRelativeTo(null);
        frame.setVisible(true);
        
        homeButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                frame.dispose();
                Home.createAndShowGUI();
            }
        });
        
        inserisciButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                String nome = nomeField.getText();
                String autore = autoreField.getText();
                String inizio = formatDate(inizioDateChooser.getDate());
                String fine = formatDate(fineDateChooser.getDate());
                boolean inCorso = inCorsoCheckBox.isSelected();
                
                if (!fine.isEmpty() && inCorso) {
                    JOptionPane.showMessageDialog(frame, "Il libro risulta finito.");
                } 
                else if (fine.isEmpty() && !inCorso) {
                    int result = JOptionPane.showConfirmDialog(frame, "Non c'è nessuna data di fine, il libro è ancora in corso?", "Attenzione", JOptionPane.YES_NO_OPTION);
                    if (result == JOptionPane.YES_OPTION) {
                        inCorsoCheckBox.setSelected(true);
                    } else {
                        int confirmResult = JOptionPane.showConfirmDialog(frame, "Vuoi inserire comunque senza data di fine libro?", "Attenzione", JOptionPane.YES_NO_OPTION);
                        if (confirmResult == JOptionPane.YES_OPTION) {
                            LibreriaManager.inserisciLibro(nome, autore, inizio, fine, inCorso);
                            frame.dispose();
                        } else {
                            // Non fare nulla, lascia la finestra di inserimento aperta
                        }
                    }
                } 
                else if ((inizio.isEmpty() && !fine.isEmpty())) {
                	int confirmResult = JOptionPane.showConfirmDialog(frame, "Non c'è una data di inizio, inserire comunque?", "Attenzione", JOptionPane.YES_NO_OPTION);
                    if (confirmResult == JOptionPane.YES_OPTION) {
                    	inCorsoCheckBox.setSelected(false);
                        LibreriaManager.inserisciLibro(nome, autore, inizio, fine, inCorso);
                        frame.dispose();
                    } else {
                        // Non fare nulla, lascia la finestra di inserimento aperta
                    }
				}
                else {
                    LibreriaManager.inserisciLibro(nome, autore, inizio, fine, inCorso);
                    frame.dispose();
                }
            }
        });
    }
    
    private static String formatDate(Date date) {
        if (date == null) {
            return "";
        }
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
        return dateFormat.format(date);
    }
}

