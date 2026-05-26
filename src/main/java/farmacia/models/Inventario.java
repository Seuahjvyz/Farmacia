1.	farmacia/models/Inventario.java
package farmacia.models;
import java.math.BigDecimal;
import java.sql.Date;
public class Inventario {
    private int idInventario;
    private int idProducto;
    private String lote;
    private Date fechaCaducidad;
    private int stockActual;
    private BigDecimal precioCompra;
    private BigDecimal precioVenta;
    private String nombreProducto;

    public Inventario() {}

    public int getIdInventario() { return idInventario; }
    public void setIdInventario(int idInventario) { this.idInventario = idInventario; }
    
    public int getIdProducto() { return idProducto; }
    public void setIdProducto(int idProducto) { this.idProducto = idProducto; }
    
    public String getLote() { return lote; }
    public void setLote(String lote) { this.lote = lote; }
    
    public Date getFechaCaducidad() { return fechaCaducidad; }
    public void setFechaCaducidad(Date fechaCaducidad) { this.fechaCaducidad = fechaCaducidad; }
    
    public int getStockActual() { return stockActual; }
    public void setStockActual(int stockActual) { this.stockActual = stockActual; }
    
    public BigDecimal getPrecioCompra() { return precioCompra; }
    public void setPrecioCompra(BigDecimal precioCompra) { this.precioCompra = precioCompra; }
    
    public BigDecimal getPrecioVenta() { return precioVenta; }
    public void setPrecioVenta(BigDecimal precioVenta) { this.precioVenta = precioVenta; }
    
    public String getNombreProducto() { return nombreProducto; }
    public void setNombreProducto(String nombreProducto) { this.nombreProducto = nombreProducto; }

    public String toString() {
        return idInventario + " | " + nombreProducto + " | Lote: " + lote + " | Stock: " + stockActual + " | Vence: " + fechaCaducidad;
    }
}
