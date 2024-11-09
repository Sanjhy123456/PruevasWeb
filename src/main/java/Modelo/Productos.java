/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Modelo;

import java.io.InputStream;

/**
 *
 * @author USER
 */
public class Productos {
    private int cod_producto;  // Código del producto
    private String nombre;
    private InputStream imagen; // Imagen como InputStream
    private String detalle;
    private String descripcion;
    private int precio;
    private int cod_categoria; // Código de la categoría

    // Constructor vacío
    public Productos() {
    }

    // Constructor con parámetros
    public Productos(int codproducto, String nombre, InputStream imagen, String detalle, String descripcion, int precio, int codcategoria) {
        this.cod_producto = codproducto;
        this.nombre = nombre;
        this.imagen = imagen; // Cambiado a InputStream
        this.detalle = detalle;
        this.descripcion = descripcion;
        this.precio = precio;
        this.cod_categoria = codcategoria;
    }

    // Getters y Setters
    public int getCodProducto()                     { return cod_producto; }
    
    public void setCodProducto(int codProducto)     { this.cod_producto = codProducto; }
    
    public String getNombre()                       {return nombre; }
    
    public void setNombre(String nombre)            { this.nombre = nombre; }
    
    public InputStream getImagen()                  { return imagen; }
    
    public void setImagen(InputStream imagen)       {  this.imagen = imagen; }
    
    public String getDetalle()                      {return detalle;}
    
    public void setDetalle(String detalle)          {this.detalle = detalle;}
    
    public String getDescripcion()                  { return descripcion;}
    
    public void setDescripcion(String descripcion)  {this.descripcion = descripcion; }
    
    public int getPrecio()                          {return precio;}
    
    public void setPrecio(int precio)               { this.precio = precio;}
    
    public int getCodCategoria()                    { return cod_categoria; }
    
    public void setCodCategoria(int codCategoria)   { this.cod_categoria = codCategoria; }
}
