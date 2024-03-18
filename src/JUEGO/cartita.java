/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package JUEGO;

import javax.swing.JButton;
import javax.swing.JLabel;

/**
 *
 * @author Josue Gavidia
 */
public class cartita extends JButton {

    private String takenBy;
    private String cardName;
    private int team;
    private int row, col;
    private boolean Checked;
    private boolean alreadySequenced;
    private JLabel fichita;

    public cartita(int row, int col, String carta) {
        this.takenBy = null;
        this.cardName = carta;
        this.row = row;
        this.col = col;
        Checked = false;
        alreadySequenced = false;
        fichita = new JLabel();
        fichita.setBounds(this.getBounds());
        fichita.setIcon(null);
        this.add(fichita);
        
    }

    public void claimCard(String name, int team) {
        this.takenBy = name;
        this.team = team;
        this.Checked = true;
       

    }

    public String getTakenBy() {
        return takenBy;
    }

    public void setTakenBy(String takenBy) {
        this.takenBy = takenBy;
    }

    public void setTeam(int team) {
        this.team = team;
    }
    
    

    public int getTeam() {
        return team;
    }

    public int getRow() {
        return row;
    }

    public int getCol() {
        return col;
    }

    public boolean isChecked() {
        return Checked;
    }

    public void setChecked(boolean Checked) {
        this.Checked = Checked;
    }

    public boolean isAlreadySequenced() {
        return alreadySequenced;
    }

    public void setAlreadySequenced(boolean alreadySequenced) {
        this.alreadySequenced = alreadySequenced;
    }

    public JLabel getFichita() {
        return fichita;
    }

    public void setFichita(JLabel fichita) {
        this.fichita = fichita;
    }

    public String getCardName() {
        return cardName;
    }

    public void setCardName(String cardName) {
        this.cardName = cardName;
    }
    
    
    

}
