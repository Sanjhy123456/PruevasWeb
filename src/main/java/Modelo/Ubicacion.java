package Modelo;

public class Ubicacion {
    private int codUbicacionCliente;
    private String departamento;
    private String provincia;
    private String distrito;
    private String direccion;
    private String referencia;

    public Ubicacion() {
    }

    public Ubicacion(int codUbicacionCliente, String departamento, String provincia, String distrito, String direccion, String referencia) {
        this.codUbicacionCliente = codUbicacionCliente;
        this.departamento = departamento;
        this.provincia = provincia;
        this.distrito = distrito;
        this.direccion = direccion;
        this.referencia = referencia;
    }

    public int getCodUbicacionCliente()                         {return codUbicacionCliente;}

    public void setCodUbicacionCliente(int codUbicacionCliente) {this.codUbicacionCliente = codUbicacionCliente; }

    public String getDepartamento()                             { return departamento; }

    public void setDepartamento(String departamento)            { this.departamento = departamento; }

    public String getProvincia()                                { return provincia;}

    public void setProvincia(String provincia)                  {this.provincia = provincia;}

    public String getDistrito()                                 { return distrito;}

    public void setDistrito(String distrito)                    {this.distrito = distrito;}

    public String getDireccion()                                { return direccion; }

    public void setDireccion(String direccion)                  {this.direccion = direccion;}

    public String getReferencia()                               {return referencia;}

    public void setReferencia(String referencia)                {this.referencia = referencia; }

}

