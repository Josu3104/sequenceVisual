/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package JUEGO;

import MENU.MenuPrincipal;
import java.awt.Component;
import java.awt.Image;
import java.awt.event.ActionEvent;
import java.util.ArrayList;
import java.util.Date;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JOptionPane;
import javax.swing.JPanel;

/**
 *
 * @author Josue Gavidia
 */
public class boardManager {

    public static String WINNER;
    public static int teamWin;
    private int sequencesPerTeam[];
    public static boolean SEQUENCE;
    public static Date fechaGANE;

    public static cartita[][] board = new cartita[10][10];
    String[][] cartas = {
        {"corner", "10S", "QS", "KS", "AS", "2D", "3D", "4D", "5D", "corner",},
        {"9S", "10H", "9H", "8H", "7H", "6H", "5H", "4H", "3H", "6H",},
        {"8S", "QH", "7D", "8D", "9D", "10D", "QD", "KD", "2H", "7D",},
        {"7S", "KH", "6D", "2C", "AH", "KH", "QH", "AD", "2S", "8D",},
        {"6S", "AH", "5D", "3C", "4H", "3H", "10H", "AC", "3S", "9D",},
        {"5S", "2C", "4D", "4S", "5H", "2H", "9H", "KS", "4S", "10D",},
        {"4S", "3C", "3D", "5C", "6H", "7H", "8H", "QC", "5S", "QD",},
        {"3S", "4C", "2D", "6C", "7C", "8C", "9C", "10C", "6S", "KD",},
        {"2S", "5C", "AS", "KS", "QS", "10S", "9S", "8S", "7S", "AD",},
        {"corner", "6C", "7C", "8C", "9C", "10C", "QC", "KC", "AC", "corner",}};

    public boardManager() {

        SEQUENCE = false;
        sequencesPerTeam = new int[MenuPrincipal.cantEquipos];
    }

    public void initBoard(JPanel pan) {

        for (int row = 0; row < 10; row++) {
            for (int col = 0; col < 10; col++) {
                board[row][col] = new cartita(row, col, cartas[row][col]);

                board[row][col].setBounds((pan.getWidth() / 10) * col, (pan.getHeight() / 10) * row, (pan.getWidth() / 10), (pan.getHeight() / 10));

                board[row][col].setText(null);

                try {
                    //RESIZER DE IMAGENES
                    String imagePath = "CARDS/" + cartas[row][col] + ".png";
                    ImageIcon neocard = new ImageIcon(imagePath);
                    Image Scalecard = neocard.getImage().getScaledInstance(board[row][col].getWidth(), board[row][col].getHeight(), Image.SCALE_SMOOTH);
                    neocard = new ImageIcon(Scalecard);
                    board[row][col].setIcon(neocard);

                } catch (Exception e) {
                    System.out.println("Muere aqui" + row + " " + col);
                    System.out.println(e.getMessage());
                }

                //ACTION LISTENER DE LOS BOTONES
                board[row][col].addActionListener((ActionEvent e) -> {
                    int Fila = ((cartita) e.getSource()).getRow();
                    int Columna = ((cartita) e.getSource()).getCol();
                    System.out.println(BOARD.enTurno.getTeam());
                    cleanBorders(board);
                    String actualCardName = board[Fila][Columna].getCardName() + ".png";

                    if (Mazo.canPlaceToken) {
                        if (actualCardName.equals(Mazo.auxLastPcard.getName())) {

                            //SI ES CARTA NORMAL
                            if (!this.takeCard(Fila, Columna)) {
                                JOptionPane.showMessageDialog(null, "CASILLA OCUPADA");
                                return;
                            } else {
                                takeCard(Fila, Columna);
                                ImageIcon neoFicha = getTokenColor();
                                Image Scalecard = neoFicha.getImage().getScaledInstance(board[Fila][Columna].getWidth(), board[Fila][Columna].getHeight(), Image.SCALE_SMOOTH);
                                neoFicha = new ImageIcon(Scalecard);
                                board[Fila][Columna].getFichita().setIcon(neoFicha);
                                pan.repaint();
                                if (this.checkHorizontal(Fila, Columna) || this.checkVertical(Fila, Columna) || this.checkDiagonal(Fila, Columna)) {
                                    int teamNumber = board[Fila][Columna].getTeam();
                                    sequencesPerTeam[teamNumber]++;

                                    for (int i = 0; i < MenuPrincipal.cantEquipos; i++) {
                                        if (sequencesPerTeam[i] >= 2) {
                                            SEQUENCE = true;
                                            fechaGANE = new Date();
                                            WINNER = "HA GANADO EL EQUIPO " + (i);
                                            teamWin = i;
                                            System.out.println("TERMINA EL JUEGO");
                                            return;
                                        }

                                    }
                                    Mazo.canPlaceToken = false;
                                }
                            }

                        } else if (isCardJack(Mazo.auxLastPcard.getName())) {

                            //SI ES CARTA JACK
                            if (this.getJackEye(Mazo.auxLastPcard) == 1) {

                                if (board[Fila][Columna].getFichita().getIcon() != null) {
                                    this.OneEyeJackAction(Mazo.auxLastPcard, board[Fila][Columna]);
                                } else {
                                    JOptionPane.showMessageDialog(null, "PARA REMOVER UNA FICHA, DEBE HABER UNA COLOCADA");
                                    return;
                                }
                            } else if (this.getJackEye(Mazo.auxLastPcard) == 2) {
                                if (board[Fila][Columna].getFichita().getIcon() == null) {
                                    this.TwoEyeJackAction(Mazo.auxLastPcard, board[Fila][Columna]);
                                } else {
                                    JOptionPane.showMessageDialog(null, "NO PUEDE BLOQUEAR UNA CASILLA OCUPADA");
                                    return;
                                }
                            }
                             Mazo.canPlaceToken = false;
                        } else {
                            JOptionPane.showMessageDialog(null, "SELECCIONE LA CASILLA SEÑALADA");
                            return;
                        }
                       
                    } else {
                        JOptionPane.showMessageDialog(null, "SELECCIONE UNA CARTA DE SU MANOJO");
                        return;
                    }
                     BOARD.baraja.doClick();
                }
                );

                pan.add(board[row][col]);

            }
        }
        board[0][0].setChecked(true);
        board[0][0].setEnabled(false);
        board[9][0].setChecked(true);
        board[9][0].setEnabled(false);
        board[0][9].setChecked(true);
        board[0][9].setEnabled(false);
        board[9][9].setChecked(true);
        board[9][9].setEnabled(false);
    }

    private boolean takeCard(int row, int col) {
        if (board[row][col].getTakenBy() == null) {
            board[row][col].claimCard(BOARD.enTurno.getUsername(), BOARD.enTurno.getTeam());
            System.out.println("claimed by team " + board[row][col].getTeam());
            return true;
        }
        return false;
    }

    //3h sin solucion alguna, Hay que solidificar el gane con una RECURSIVA
    private boolean validPos(int row, int col) {
        return row >= 0 && row < board.length && col >= 0 && col < board.length;
    }

    public void initPlayerCards(JPanel left, JPanel right, JPanel bottom) {
        Component[] cards = left.getComponents();
        Component[] cards2 = right.getComponents();
        Component[] cards3 = bottom.getComponents();

        for (Component a : cards) {
            if (a instanceof JPanel) {
                a.setVisible(false);
            }
        }

        for (Component a : cards2) {
            if (a instanceof JPanel) {
                a.setVisible(false);
            }
        }

        for (Component a : cards3) {
            if (a instanceof JPanel) {
                a.setVisible(false);
            }
        }
    }

    private ImageIcon getTokenColor() {
        switch (BOARD.equipoTurn) {
            case 1:
                return getTokenColor(MenuPrincipal.chosenColorT1);
            case 2:
                return getTokenColor(MenuPrincipal.chosenColorT2);
            case 3:
                return getTokenColor(MenuPrincipal.chosenColorT3);
        }
        return null;
    }

    private ImageIcon getTokenColor(String color) {
        switch (color) {
            case "ROJO":
                return new ImageIcon("TOKENS/redToken.png");
            case "VERDE":
                return new ImageIcon("TOKENS/greenToken.png");
            case "AMARILLO":
                return new ImageIcon("TOKENS/yellowToken.png");
            case "AZUL":
                return new ImageIcon("TOKENS/blueToken.png");
        }
        return null;
    }

    public static void cleanBorders(cartita[][] array) {
        for (int row = 0; row < boardManager.board.length; row++) {
            for (int col = 0; col < boardManager.board.length; col++) {
                array[row][col].setBorder(null);
            }
        }
    }

    private boolean isCardJack(String name) {
        return name.charAt(0) == 'J';
    }

    private int getJackEye(JButton lastCard) {
        String namae = lastCard.getName();
        System.out.println(namae);
        if (isCardJack(namae)) {
            if (namae.contains("ONE")) {
                return 1;
            } else if (namae.contains("TWO")) {
                return 2;
            }
        }
        return -1;
    }

    //UN OJO, QUITAR, DOS OJOS, BLOQUEAR
    private void OneEyeJackAction(JButton lastCard, cartita boardButton) {
        if (getJackEye(lastCard) == 1) {
            quitarFicha(boardButton);
        }
    }

    private void TwoEyeJackAction(JButton lastCard, cartita boardButton) {
        if (getJackEye(lastCard) == 2) {
            boardButton.setEnabled(false);
            boardButton.setTakenBy(BOARD.enTurno.getUsername());
            boardButton.setTeam(BOARD.enTurno.getTeam());
        }

    }

    private void quitarFicha(cartita boardB) {
        boardB.setTakenBy(null);
        boardB.setTeam(0);
        boardB.setAlreadySequenced(false);
        boardB.setChecked(false);
        boardB.getFichita().setIcon(null);
        Mazo.canPlaceToken=false;
    }

    //EL INFAME "GANE"
    private boolean checkHorizontal(int row, int col) {
        return checkXleft(row, col) || checkXright(row, col);
    }

    private boolean checkVertical(int row, int col) {
        return checkYUp(row, col) || checkYDown(row, col);
    }

    private boolean checkDiagonal(int row, int col) {
        return this.checkDiagNE(row, col) || this.checkDiagSW(row, col) || this.checkDiagNW(row, col) || this.checkDiagSE(row, col);
    }

    private boolean checkXright(int row, int col) {
        ArrayList<cartita> test = new ArrayList<>();
        if (board[row][col].isChecked() && !board[row][col].isAlreadySequenced()) {

            for (int i = 0; i < 5; i++) {

                if (isValid(row, col + i)) {
                    test.add(board[row][col + i]);
                    System.out.println("card ADDED");
                } else {
                    System.out.println("POS NOT WORTH TO LOOP THROUGH");
                    return false;
                }
            }
            return checkIndividualArray(test);
        } else {
            System.out.println("NOT EVEN THE FIRST ONE IS CHECKED");

        }
        return false;
    }

    private boolean checkXleft(int row, int col) {
        ArrayList<cartita> test = new ArrayList<>();
        if (board[row][col].isChecked() && !board[row][col].isAlreadySequenced()) {
            for (int i = 0; i > -5; i--) {
                if (isValid(row, col + i)) {
                    test.add(board[row][col + i]);
                    System.out.println("Card ADDED");
                } else {
                    System.out.println("Position not worth to loop through");
                    return false;
                }
            }
            return checkIndividualArray(test);
        } else {
            System.out.println("Not even the first one is checked");
        }
        return false;
    }

    private boolean checkYUp(int row, int col) {
        ArrayList<cartita> test = new ArrayList<>();
        if (board[row][col].isChecked() && !board[row][col].isAlreadySequenced()) {
            for (int i = 0; i > -5; i--) {
                if (isValid(row + i, col)) {
                    test.add(board[row + i][col]);
                    System.out.println("Card ADDED");
                } else {
                    System.out.println("Position not worth to loop through");
                    return false;
                }
            }
            return checkIndividualArray(test);
        } else {
            System.out.println("Not even the first one is checked");
        }
        return false;
    }

    private boolean checkYDown(int row, int col) {
        ArrayList<cartita> test = new ArrayList<>();
        if (board[row][col].isChecked() && !board[row][col].isAlreadySequenced()) {
            for (int i = 0; i < 5; i++) {
                if (isValid(row + i, col)) {
                    test.add(board[row + i][col]);
                    System.out.println("Card ADDED");
                } else {
                    System.out.println("Position not worth to loop through");
                    return false;
                }
            }
            return checkIndividualArray(test);
        } else {
            System.out.println("Not even the first one is checked");
        }
        return false;
    }

    private boolean checkIndividualArray(ArrayList<cartita> array) {
        int i = 0;
        cartita primera = array.get(0);
        for (cartita card : array) {

            if ((card.isChecked() && !card.isAlreadySequenced() && card.getTeam() == primera.getTeam()) || card.getCardName().equals("corner")) {
                i++;
                System.out.println("SE SUMA I++");
            }
        }
        return i == 5;
    }

    private boolean isValid(int row, int col) {
        return row >= 0 && row < board.length && col >= 0 && col < board.length;
    }

    private boolean checkDiagNW(int row, int col) {
        ArrayList<cartita> test = new ArrayList<>();
        if (board[row][col].isChecked() && !board[row][col].isAlreadySequenced()) {
            for (int i = 0; i > -5; i--) {
                if (isValid(row + i, col + i)) {
                    test.add(board[row + i][col + i]);
                    System.out.println("Card ADDED");
                } else {
                    System.out.println("Position not worth to loop through");
                    return false;
                }
            }
            return checkIndividualArray(test);
        } else {
            System.out.println("Not even the first one is checked");
        }
        return false;
    }

    private boolean checkDiagSE(int row, int col) {
        ArrayList<cartita> test = new ArrayList<>();
        if (board[row][col].isChecked() && !board[row][col].isAlreadySequenced()) {
            for (int i = 0; i < 5; i++) {
                if (isValid(row + i, col - i)) {
                    test.add(board[row + i][col - i]);
                    System.out.println("Card ADDED");
                } else {
                    System.out.println("Position not worth to loop through");
                    return false;
                }
            }
            return checkIndividualArray(test);
        } else {
            System.out.println("Not even the first one is checked");
        }
        return false;
    }

    private boolean checkDiagNE(int row, int col) {
        ArrayList<cartita> test = new ArrayList<>();
        if (board[row][col].isChecked() && !board[row][col].isAlreadySequenced()) {
            for (int i = 0; i > -5; i--) {
                if (isValid(row + i, col - i)) {
                    test.add(board[row + i][col - i]);
                    System.out.println("Card ADDED");
                } else {
                    System.out.println("Position not worth to loop through");
                    return false;
                }
            }
            return checkIndividualArray(test);
        } else {
            System.out.println("Not even the first one is checked");
        }
        return false;
    }

    private boolean checkDiagSW(int row, int col) {
        ArrayList<cartita> test = new ArrayList<>();
        if (board[row][col].isChecked() && !board[row][col].isAlreadySequenced()) {
            for (int i = 0; i < 5; i++) {
                if (isValid(row + i, col + i)) {
                    test.add(board[row + i][col + i]);
                    System.out.println("Card ADDED");
                } else {
                    System.out.println("Position not worth to loop through");
                    return false;
                }
            }
            return checkIndividualArray(test);
        } else {
            System.out.println("Not even the first one is checked");
        }
        return false;
    }

}
