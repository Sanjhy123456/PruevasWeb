package Modelo;

import java.io.InputStream;

public class Carrito {
    private int cod_producto;
    private String nombre;
    private String descripcion;
    private int precio; // Cambiado a int como mencionaste
    private int cantidad;
    private InputStream imagen; // Imagen como InputStream
    private int subTotal;
public Carrito (){

}
    public Carrito(int cod_producto, String nombre, String descripcion, int precio, int cantidad, InputStream imagen, int subTotal) {
        this.cod_producto = cod_producto;
        this.nombre = nombre;
        this.descripcion = descripcion;
        this.precio = precio;
        this.cantidad = cantidad;
        this.imagen = imagen;
        this.subTotal = subTotal;
    }

    // Getters y Setters
    public int getCod_producto()                                                { return cod_producto;}

    public void setCod_producto(int cod_producto)                               { this.cod_producto = cod_producto; }

    public String getNombre()                                                   { return nombre; }

    public void setNombre(String nombre)                                        {this.nombre = nombre;}

    public String getDescripcion()                                              { return descripcion;}

    public void setDescripcion(String descripcion)                              { this.descripcion = descripcion;}

    public int getPrecio()                                                      { return precio;}

    public void setPrecio(int precio)                                           { this.precio = precio; }

    public int getCantidad()                                                    {return cantidad;}

    public void setCantidad(int cantidad)                                       { this.cantidad = cantidad;}

   public InputStream getImagen()                                               {return imagen;}
   
    public void setImagen(InputStream imagen)                                   { this.imagen = imagen; }
    
    public int getSubTotal()                                                    { return subTotal;}
    
    public void setSubTotal(int subTotal)                                       { this.subTotal = subTotal; }
}
