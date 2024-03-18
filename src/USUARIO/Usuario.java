/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package USUARIO;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Date;

/**
 *
 * @author Josue Gavidia
 */
public class Usuario implements Serializable {

    private String NAME;
    private String username;
    private int puntos;
    private Date fechaCreacion;
    private String password;
    private int team;
    private ArrayList<historial> historial;
    private ArrayList<String> cartas;

    public Usuario(String name, String user, String password) {
        this.NAME = name;
        username = user;
        puntos = 0;
        fechaCreacion = new Date();
        this.password = password;
        cartas  = new ArrayList();
        historial= new ArrayList();
    }

    public int getTeam() {
        return team;
    }
    
    public void setTeam(int team){
        this.team = team;
    }

    public String getNAME() {
        return NAME;
    }

    public String getUsername() {
        return username;
    }

    public int getPuntos() {
        return puntos;
    }

    public Date getFechaCreacion() {
        return fechaCreacion;
    }

    public String getPassword() {
        return password;
    }

    public ArrayList<historial> getHistorial() {
        return historial;
    }

    public void setHistorial(ArrayList<historial> historial) {
        this.historial = historial;
    }

   

    public ArrayList<String> getCartas() {
        return cartas;
    }

    public void setCartas(ArrayList<String> cartas) {
        this.cartas = cartas;
    }

    
    
    

}
