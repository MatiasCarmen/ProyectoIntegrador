package entidades;

import java.util.Date;

public class ProductoInstalado {
    private String id;
    private String idCuenta;
    private String nombre;
    private String descripcion;
    private Date fechaInstalacion;
    private String estado;

    // contructor principal 
    public ProductoInstalado() {}

    // Constructor con parámetros
    public ProductoInstalado(String id, String idCuenta, String nombre, String descripcion,
                           Date fechaInstalacion, String estado) {
        this.id = id;
        this.idCuenta = idCuenta;
        this.nombre = nombre;
        this.descripcion = descripcion;
        this.fechaInstalacion = fechaInstalacion;
        this.estado = estado;
    }

    // Getters y Setters
    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getIdCuenta() {
        return idCuenta;
    }

    public void setIdCuenta(String idCuenta) {
        this.idCuenta = idCuenta;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getDescripcion() {
        return descripcion;
    }

    public void setDescripcion(String descripcion) {
        this.descripcion = descripcion;
    }

    public Date getFechaInstalacion() {
        return fechaInstalacion;
    }

    public void setFechaInstalacion(Date fechaInstalacion) {
        this.fechaInstalacion = fechaInstalacion;
    }

    public String getEstado() {
        return estado;
    }

    public void setEstado(String estado) {
        this.estado = estado;
    }
}
