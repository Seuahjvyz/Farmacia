package farmacia.services; // Cambia a farmacia.servicios si así está en el repo

import farmacia.dao.InventarioDAO;
import farmacia.db.Conexion;
import farmacia.models.Inventario; // Cambia a farmacia.modelos.Inventario si usan "modelos"
import java.math.BigDecimal;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.List;

public class InventarioService {
    
    public List<Inventario> listarTodos() throws SQLException {
        Connection conn = null;
        try {
            conn = Conexion.getConnection();
            InventarioDAO dao = new InventarioDAO(conn);
            return dao.listarTodos();
        } finally {
            Conexion.cerrarConexion(conn);
        }
    }

    public Inventario buscarPorId(int id) throws SQLException {
        Connection conn = null;
        try {
            conn = Conexion.getConnection();
            InventarioDAO dao = new InventarioDAO(conn);
            return dao.buscarPorId(id);
        } finally {
            Conexion.cerrarConexion(conn);
        }
    }

    public List<Inventario> buscarPorProducto(int idProducto) throws SQLException {
        Connection conn = null;
        try {
            conn = Conexion.getConnection();
            InventarioDAO dao = new InventarioDAO(conn);
            return dao.listarPorProducto(idProducto);
        } finally {
            Conexion.cerrarConexion(conn);
        }
    }

    // CORRECCIÓN 1: Método nuevo que pide la guía para que Donnet lo use
    public boolean verificarStock(int idInventario, int cantidad) throws SQLException {
        Connection conn = null;
        try {
            conn = Conexion.getConnection();
            InventarioDAO dao = new InventarioDAO(conn);
            Inventario inv = dao.buscarPorId(idInventario);
            return inv != null && inv.getStockActual() >= cantidad;
        } finally {
            Conexion.cerrarConexion(conn);
        }
    }

    public String agregarLote(Inventario inv) {
        if (inv.getLote() == null || inv.getLote().trim().isEmpty()) {
            return "Error: El lote no puede estar vacío";
        }
        if (inv.getFechaCaducidad().before(new java.sql.Date(System.currentTimeMillis()))) {
            return "Error: Fecha de caducidad inválida";
        }
        if (inv.getStockActual() < 0) {
            return "Error: Stock no puede ser negativo";
        }
        if (inv.getPrecioCompra() == null || inv.getPrecioCompra().compareTo(BigDecimal.ZERO) <= 0) {
            return "Error: Precio de compra debe ser mayor a 0";
        }
        if (inv.getPrecioVenta() == null || inv.getPrecioVenta().compareTo(BigDecimal.ZERO) <= 0) {
            return "Error: Precio de venta debe ser mayor a 0";
        }

        Connection conn = null;
        try {
            conn = Conexion.getConnection();
            InventarioDAO dao = new InventarioDAO(conn);
            boolean ok = dao.insertar(inv);
            return ok ? "Lote agregado correctamente" : "Error al insertar";
        } catch (SQLException e) {
            if (e.getMessage().contains("Duplicate entry")) {
                return "Error: Ya existe ese lote para el producto";
            }
            return "Error BD: " + e.getMessage();
        } finally {
            Conexion.cerrarConexion(conn);
        }
    }


    public String
