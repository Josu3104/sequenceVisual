/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package JUEGO;

import MENU.MenuPrincipal;
import USUARIO.Usuario;
import java.awt.Color;
import java.awt.Component;
import java.awt.Image;
import java.awt.event.ActionEvent;
import java.io.File;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Iterator;
import java.util.Random;
import javax.swing.BorderFactory;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.border.Border;

/**
 *
 * @author Josue Gavidia
 */
public class Mazo {

    public ArrayList<String> deck, cementerio;
    private File jacks;
    private File manojo;
    private File special;
    public static boolean canPlaceToken, haRobadoCarta;

    public static JButton auxLastPcard;

    public Mazo() {
        deck = new ArrayList<>();
        cementerio = new ArrayList<>();
        jacks = new File("JACKS");
        manojo = new File("MANOJO");
        special = new File("SPECIAL");
        setDeck();
        Collections.shuffle(deck);
        this.repatir();
        canPlaceToken = false;
        haRobadoCarta = false;

    }

    public final void setDeck() {

        File[] m = manojo.listFiles();

        ArrayList<String> newM = new ArrayList();

        newM = stripCardsFromDir(m, newM);

        for (int i = 0; i < 2; i++) {
            Collections.addAll(deck, newM.toArray(String[]::new));

        }

    }

    private ArrayList<String> stripCardsFromDir(File[] array, ArrayList<String> arrayTwo) {
        for (File name : array) {
            arrayTwo.add(name.getName());

        }
        return arrayTwo;
    }

    public static void setManojo(JPanel left, JPanel right, JPanel bottom, Usuario userEnTurno) {
        switch (userEnTurno.getTeam()) {
            case 1:
                Mazo.ocultarCartas(right);
                Mazo.ocultarCartas(bottom);
                Mazo.mostrarCartas(left, userEnTurno);

                break;
            case 2:
                Mazo.ocultarCartas(left);
                Mazo.ocultarCartas(bottom);
                Mazo.mostrarCartas(right, userEnTurno);

                break;
            case 3:
                Mazo.ocultarCartas(right);
                Mazo.ocultarCartas(left);
                Mazo.mostrarCartas(bottom, userEnTurno);

                break;
        }

    }
//Se guia del arreglo de cada usuario para setear las cartas

    private static void mostrarCartas(JPanel panel, Usuario player) {
        ArrayList<JButton> botones = getBotonesManojo(panel);
        ArrayList<String> playerManojo = player.getCartas();
        for (int i = 0; i < playerManojo.size(); i++) {
            botones.get(i).setVisible(true);
            botones.get(i).setIcon(imageResizer("MANOJO/" + playerManojo.get(i), botones.get(i)));
            botones.get(i).setName(playerManojo.get(i));
            System.out.println("IDK " + playerManojo.get(i));
        }

    }

    public static void ocultarCartas(JPanel panel) {
        ArrayList<JButton> botones = getBotonesManojo(panel);
        for (int i = 0; i < BOARD.enTurno.getCartas().size(); i++) {
            botones.get(i).setVisible(true);
            botones.get(i).setIcon(imageResizer("cardBack.png", botones.get(i)));
            botones.get(i).setName("BACK");
        }
    }

    //reparte las cartas a los arreglos de cada usuario
    private void repartir(Usuario player, int cant) {

        System.out.println("DECK ORIGINAL SIZE " + deck.size());
        for (int i = 0; i < cant; i++) {
            Collections.shuffle(deck);
            String carta = deck.get(i);
            player.getCartas().add(carta);
            deck.remove(carta);
            System.out.println("DECK NEW SIZE " + deck.size());
        }
    }

    public void repatir() {

        switch (MenuPrincipal.cantPlayersSettings) {
            case 2:
                MenuPrincipal.team1.forEach(player -> repartir(player, 7));
                MenuPrincipal.team2.forEach(player -> repartir(player, 7));
                break;
            case 3:
                MenuPrincipal.team1.forEach(player -> repartir(player, 6));
                MenuPrincipal.team2.forEach(player -> repartir(player, 6));
                MenuPrincipal.team3.forEach(player -> repartir(player, 6));
                break;
            case 4:
                MenuPrincipal.team1.forEach(player -> repartir(player, 7));
                MenuPrincipal.team2.forEach(player -> repartir(player, 7));
                break;
            case 6:
                MenuPrincipal.team1.forEach(player -> repartir(player, 5));
                MenuPrincipal.team2.forEach(player -> repartir(player, 5));
                MenuPrincipal.team3.forEach(player -> repartir(player, 5));
                break;
            case 8:
                MenuPrincipal.team1.forEach(player -> repartir(player, 4));
                MenuPrincipal.team2.forEach(player -> repartir(player, 4));
                break;
        }
    }

    private static ArrayList<JButton> getBotonesManojo(JPanel panel) {
        ArrayList<JButton> botones = new ArrayList<>();
        for (Component comp : panel.getComponents()) {
            if (comp instanceof JButton boton) {
                botones.add(boton);
            }
        }
        return botones;
    }

    public void initManojos(JPanel pan) {
        Component[] but = pan.getComponents();

        for (Component a : but) {
            if (a instanceof JButton) {
                a.setVisible(false);
                //Action listener de cada boton de los manojos

                ((JButton) a).addActionListener((ActionEvent ev) -> {

                    boardManager.cleanBorders(boardManager.board);
                    auxLastPcard = (JButton) ev.getSource();
                    String name = auxLastPcard.getName();
                    if (name.equals("BACK")) {
                        JOptionPane.showMessageDialog(null, "ESPERE A SU TURNO");

                    } else {

                        //VERIFICAR SI ES JACK
                        if (auxLastPcard.getName().charAt(0) == 'J') {
                            canPlaceToken = true;
                        }

                        String cutName = name.substring(0, name.lastIndexOf('.'));
                        //AL TOCAR LA CARTA, SE RESALTAN TODAS SUS ITERACIONES EN EL TABLERO

                        for (int row = 0; row < boardManager.board.length; row++) {
                            for (int col = 0; col < boardManager.board.length; col++) {

                                if (boardManager.board[row][col].getCardName().equals(cutName)) {

                                    Border borde = BorderFactory.createLineBorder(Color.BLUE, 4);
                                    boardManager.board[row][col].setBorder(borde);
                                    canPlaceToken = true;

                                }

                            }
                        }
                    }

                });

            }
        }
    }

    public static ImageIcon imageResizer(String imagePath, JButton botonsito) {
        ImageIcon neocard = new ImageIcon(imagePath);
        Image Scalecard = neocard.getImage().getScaledInstance(botonsito.getWidth(), botonsito.getHeight(), Image.SCALE_SMOOTH);
        neocard = new ImageIcon(Scalecard);
        return neocard;
    }

    public void initBARAJA(JButton baraja) {
        Random rand = new Random();
        int ind = rand.nextInt(deck.size());
        String card = deck.get(ind);
        String imagePath = "cardBack.png";
        ImageIcon neocard = new ImageIcon(imagePath);
        Image Scalecard = neocard.getImage().getScaledInstance(baraja.getWidth(), baraja.getHeight(), Image.SCALE_SMOOTH);
        neocard = new ImageIcon(Scalecard);
        baraja.setIcon(neocard);
        baraja.setName(card);
    }
// TOMAR UNA CARTA DE LA BARAJA ANTES DEL SIGUIENTE TURNO

    public void robarCarta(JButton baraja, JButton cementerio) {
        String cartaSeleccionada = auxLastPcard.getName();
        String cartaActualBaraja = baraja.getName();

        removeCardFromUserArray(cartaSeleccionada);
        BOARD.enTurno.getCartas().add(cartaActualBaraja);

        auxLastPcard.setIcon(imageResizer("MANOJO/" + cartaActualBaraja, auxLastPcard));
        auxLastPcard.setName(cartaActualBaraja);

        this.mandarAlCementerio(cementerio, cartaSeleccionada);
        removeCardFromBaraja(cartaActualBaraja);
        this.initBARAJA(baraja);

        auxLastPcard = null;
    }

    private void mandarAlCementerio(JButton cement, String carta) {
        cement.setIcon(imageResizer("MANOJO/" + carta, cement));
    }

    private void removeCardFromUserArray(String carta) {
        Iterator<String> iterator = BOARD.enTurno.getCartas().iterator();
        while (iterator.hasNext()) {
            String card = iterator.next();
            if (card.equals(carta)) {
                iterator.remove();
                break;
            }
        }
    }

    private void removeCardFromBaraja(String carta) {
        Iterator<String> iterator = deck.iterator();
        while (iterator.hasNext()) {
            String card = iterator.next();
            if (card.equals(carta)) {
                iterator.remove();
                break;
            }
        }

    }

}
