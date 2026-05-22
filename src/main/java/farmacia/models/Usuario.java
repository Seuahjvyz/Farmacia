package farmacia.models;

import java.sql.Timestamp;

public class Usuario {

    private int idUsuario;
    private String nombre;
    private String user;
    private String correo;
    private String contrasena;
    private boolean activo;
    private int idRol;
    private String nombreRol;
    private Timestamp fechaRegistro;

    // Constructor vacío
    public Usuario() {
    }

    // Constructor con parametros
    public Usuario(int idUsuario, String nombre, String user, String correo,
            String contrasena, boolean activo, int idRol, Timestamp fechaRegistro) {
        this.idUsuario = idUsuario;
        this.nombre = nombre;
        this.user = user;
        this.correo = correo;
        this.contrasena = contrasena;
        this.activo = activo;
        this.idRol = idRol;
        this.fechaRegistro = fechaRegistro;
    }

    // Getters y Setters
    public int getIdUsuario() {
        return idUsuario;
    }

    public void setIdUsuario(int idUsuario) {
        this.idUsuario = idUsuario;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getUser() {
        return user;
    }

    public void setUser(String user) {
        this.user = user;
    }

    public String getCorreo() {
        return correo;
    }

    public void setCorreo(String correo) {
        this.correo = correo;
    }

    public String getContrasena() {
        return contrasena;
    }

    public void setContrasena(String contrasena) {
        this.contrasena = contrasena;
    }

    public boolean isActivo() {
        return activo;
    }

    public void setActivo(boolean activo) {
        this.activo = activo;
    }

    public int getIdRol() {
        return idRol;
    }

    public void setIdRol(int idRol) {
        this.idRol = idRol;
    }

    public String getNombreRol() {
        return nombreRol;
    }

    public void setNombreRol(String nombreRol) {
        this.nombreRol = nombreRol;
    }

    public Timestamp getFechaRegistro() {
        return fechaRegistro;
    }

    public void setFechaRegistro(Timestamp fechaRegistro) {
        this.fechaRegistro = fechaRegistro;
    }

    // Método helper para obtener el rol como texto
    public String getRol() {
        return nombreRol;
    }

    @Override
    public String toString() {
        return "Usuario{"
                + "idUsuario=" + idUsuario
                + ", nombre='" + nombre + '\''
                + ", user='" + user + '\''
                + ", correo='" + correo + '\''
                + ", activo=" + activo
                + ", idRol=" + idRol
                + ", rol='" + nombreRol + '\''
                + ", fechaRegistro=" + fechaRegistro
                + '}';
    }
}
