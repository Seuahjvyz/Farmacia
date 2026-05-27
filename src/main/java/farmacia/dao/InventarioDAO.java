package farmacia.dao;

import farmacia.models.Inventario; // Cambia a farmacia.modelos.Inventario si usan "modelos"
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class InventarioDAO {
    private Connection conn;

    public InventarioDAO(Connection conn) {
        this.conn = conn;
    }

    public List<Inventario> listarTodos() throws SQLException {
        String sql = "SELECT i.*, p.nombre_comercial as nombreProducto FROM inventario i JOIN producto p ON i.id_producto = p.id_producto ORDER BY i.id_inventario DESC";
        List<Inventario> lista = new ArrayList<>();
        
        try (PreparedStatement ps = conn.prepareStatement(sql);
             ResultSet rs = ps.executeQuery()) {
            
            while (rs.next()) {
                lista.add(mapearInventario(rs));
            }
        }
        return lista;
    }

    public Inventario buscarPorId(int id) throws SQLException {
        String sql = "SELECT i.*, p.nombre_comercial as nombreProducto FROM inventario i JOIN producto p ON i.id_producto = p.id_producto WHERE i.id_inventario = ?";
        
        try (PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setInt(1, id);
            try (ResultSet rs = ps.executeQuery()) {
                if (rs.next()) return mapearInventario(rs);
            }
        }
        return null;
    }

    public List<Inventario> listarPorProducto(int idProducto) throws SQLException {
        String sql = "SELECT i.*, p.nombre_comercial as nombreProducto FROM inventario i JOIN producto p ON i.id_producto = p.id_producto WHERE i.id_producto = ?";
        List<Inventario> lista = new ArrayList<>();
        
        try (PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setInt(1, idProducto);
            try (ResultSet rs = ps.executeQuery()) {
                while (rs.next()) lista.add(mapearInventario(rs));
            }
        }
        return lista;
    }

    // CORRECCIÓN 1: Faltaban los 6 signos de ?
    public boolean insertar(Inventario inv) throws SQLException {
        String sql = "INSERT INTO inventario(id_producto, lote, fecha_caducidad, stock_actual, precio_compra, precio_venta) VALUES (?, ?, ?, ?, ?, ?)";
        try (PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setInt(1, inv.getIdProducto());
            ps.setString(2, inv.getLote());
            ps.setDate(3, inv.getFechaCaducidad());
            ps.setInt(4, inv.getStockActual());
            ps.setBigDecimal(5, inv.getPrecioCompra());
            ps.setBigDecimal(6, inv.getPrecioVenta());
            return ps.executeUpdate() > 0;
        }
    }

   
