/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Modelo;

public class Favorito {
    private int codFavorito;
    private int dniPersona;
    private int codProducto;
    
    public Favorito(){

    }

    // Constructor
    public Favorito(int codFavorito, int dniPersona, int codProducto) {
        this.codFavorito=codFavorito;
        this.dniPersona = dniPersona;
        this.codProducto = codProducto;
    }

    // Getters y Setters
    public int getCodFavorito() {
        return codFavorito;
    }

    public void setCodFavorito(int codFavorito) {
        this.codFavorito = codFavorito;
    }

    public int getDniPersona() {
        return dniPersona;
    }

    public void setDniPersona(int dniPersona) {
        this.dniPersona = dniPersona;
    }

    public int getCodProducto() {
        return codProducto;
    }

    public void setCodProducto(int codProducto) {
        this.codProducto = codProducto;
    }
}
