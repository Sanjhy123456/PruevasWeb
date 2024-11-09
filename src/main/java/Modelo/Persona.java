package Modelo;

public class Persona {
    private int dni;
    private String nombre; // Nombre de la persona
    private String apellido; // Apellido de la persona
    private String correo; // Correo electrónico de la persona
    private int telefono; // Número de teléfono de la persona
    private String contraseña; // Contraseña de la persona
    private int cod_ubicacion_cliente; // Clave foránea para la ubicación del cliente
    private int cod_rol; // Clave foránea para el rol

    // Constructor vacío
    public Persona() {
    }

    // Constructor con parámetros
    public Persona(int dni, String nombre, String apellido, String correo, 
                   int telefono, String contraseña, int cod_ubicacion_cliente, int cod_rol) {
        this.dni = dni;
        this.nombre = nombre;
        this.apellido = apellido;
        this.correo = correo;
        this.telefono = telefono;
        this.contraseña = contraseña;
        this.cod_ubicacion_cliente = cod_ubicacion_cliente;
        this.cod_rol = cod_rol;
    }

    // Getters y Setters

    public int getDni()                                                         {return dni;}

    public void setDni(int dni)                                                 { this.dni = dni;}

    public String getNombre()                                                   {return nombre;}

    public void setNombre(String nombre)                                        { this.nombre = nombre;}

    public String getApellido()                                                 {return apellido; }

    public void setApellido(String apellido)                                    {this.apellido = apellido;}

    public String getCorreo()                                                   { return correo;}

    public void setCorreo(String correo)                                        { this.correo = correo;}

    public int getTelefono()                                                    { return telefono;}

    public void setTelefono(int telefono)                                       {this.telefono = telefono;}

    public String getContraseña()                                               { return contraseña;}

    public void setContraseña(String contraseña)                                {this.contraseña = contraseña; }

    public int getCod_ubicacion_cliente()                                       {return cod_ubicacion_cliente;}

    public void setCod_ubicacion_cliente(int cod_ubicacion_cliente)             {this.cod_ubicacion_cliente = cod_ubicacion_cliente;}

    public int getCod_rol()                                                     { return cod_rol;}

    public void setCod_rol(int cod_rol)                                         { this.cod_rol = cod_rol; }
}
