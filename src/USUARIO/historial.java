/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package USUARIO;

import MENU.MenuPrincipal;
import java.io.Serializable;
import java.util.Date;

/**
 *
 * @author Josue Gavidia
 */
public class historial implements Serializable {
    private Date fecha;
    private int puntos;
    private int equipo;

    public historial(Date fecha, int puntos, int equipo) {
        this.fecha = fecha;
        this.puntos += puntos;
        this.equipo = equipo;
    }

    @Override
    public String toString() {
        return "FECHA: " + MenuPrincipal.fechaNeitor.format(fecha) + "- PUNTOS GANADOS: " + puntos + "- EQUIPO: #" + equipo;
    }
    
    
    

    public Date getFecha() {
        return fecha;
    }

    public void setFecha(Date fecha) {
        this.fecha = fecha;
    }

    public int getPuntos() {
        return puntos;
    }

    public void setPuntos(int puntos) {
        this.puntos = puntos;
    }

    public int getEquipo() {
        return equipo;
    }

    public void setEquipo(int equipo) {
        this.equipo = equipo;
    }
    
    
    
}
