public class Producto {
    private int idProducto;
    private String nombreComercial;
    private String marcaLaboratorio;
    private String formaFarmaceutica;
    private String viaAdministracion;
    private String presentacion;

    public Producto() {}

    public Producto(int idProducto, String nombreComercial, String marcaLaboratorio, 
                    String formaFarmaceutica, String viaAdministracion, String presentacion) {
        this.idProducto = idProducto;
        this.nombreComercial = nombreComercial;
        this.marcaLaboratorio = marcaLaboratorio;
        this.formaFarmaceutica = formaFarmaceutica;
        this.viaAdministracion = viaAdministracion;
        this.presentacion = presentacion;
    }

    public int getIdProducto() { return idProducto; }
    public void setIdProducto(int idProducto) { this.idProducto = idProducto; }

    public String getNombreComercial() { return nombreComercial; }
    public void setNombreComercial(String nombreComercial) { this.nombreComercial = nombreComercial; }

    public String getMarcaLaboratorio() { return marcaLaboratorio; }
    public void setMarcaLaboratorio(String marcaLaboratorio) { this.marcaLaboratorio = marcaLaboratorio; }

    public String getFormaFarmaceutica() { return formaFarmaceutica; }
    public void setFormaFarmaceutica(String formaFarmaceutica) { this.formaFarmaceutica = formaFarmaceutica; }

    public String getViaAdministracion() { return viaAdministracion; }
    public void setViaAdministracion(String viaAdministracion) { this.viaAdministracion = viaAdministracion; }

    public String getPresentacion() { return presentacion; }
    public void setPresentacion(String presentacion) { this.presentacion = presentacion; }


    public String String() {
        return "ID: " + idProducto + " | " + nombreComercial + " (" + marcaLaboratorio + ") - " + presentacion;
    }
}
