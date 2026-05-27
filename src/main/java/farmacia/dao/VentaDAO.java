package farmacia.dao;

import farmacia.models.Venta;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class VentaDAO {

    private Connection conn;

    public VentaDAO(Connection conn) {
        this.conn = conn;
    }

    // INSERTAR Y OBTENER ID
    public int insertarVenta(Venta venta) throws SQLException {

        String sql = "INSERT INTO venta "
                + "(id_usuario, subtotal, descuento, total_con_descuento, iva, total, metodo_pago, estado) "
                + "VALUES (?, ?, ?, ?, ?, ?, ?, ?)";

        try (PreparedStatement stmt = conn.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) {

            stmt.setInt(1, venta.getIdUsuario());
            stmt.setDouble(2, venta.getSubtotal());
            stmt.setDouble(3, venta.getDescuento());
            stmt.setDouble(4, venta.getTotalConDescuento());
            stmt.setDouble(5, venta.getIva());
            stmt.setDouble(6, venta.getTotal());
            stmt.setString(7, venta.getMetodoPago());
            stmt.setString(8, venta.getEstado());

            stmt.executeUpdate();

            try (ResultSet rs = stmt.getGeneratedKeys()) {
                if (rs.next()) {
                    return rs.getInt(1);
                }
            }
        }
        return -1;
    }

    // LISTAR TODAS
    public List<Venta> listarTodos() throws SQLException {
        List<Venta> lista = new ArrayList<>();

        String sql = "SELECT v.*, u.nombre AS usuario_nombre "
                + "FROM venta v "
                + "JOIN usuario u ON v.id_usuario = u.id_usuario "
                + "ORDER BY v.id_venta DESC";

        try (PreparedStatement stmt = conn.prepareStatement(sql);
             ResultSet rs = stmt.executeQuery()) {

            while (rs.next()) {
                Venta v = new Venta();

                v.setIdVenta(rs.getInt("id_venta"));
                v.setFechaHora(rs.getTimestamp("fecha_hora"));
                v.setIdUsuario(rs.getInt("id_usuario"));
                v.setNombreUsuario(rs.getString("usuario_nombre"));

                v.setSubtotal(rs.getDouble("subtotal"));
                v.setDescuento(rs.getDouble("descuento"));
                v.setTotalConDescuento(rs.getDouble("total_con_descuento"));
                v.setIva(rs.getDouble("iva"));
                v.setTotal(rs.getDouble("total"));

                v.setMetodoPago(rs.getString("metodo_pago"));
                v.setEstado(rs.getString("estado"));

                lista.add(v);
            }
        }
        return lista;
    }

    // BUSCAR POR ID
    public Venta buscarPorId(int id) throws SQLException {

        String sql = "SELECT v.*, u.nombre AS usuario_nombre "
                + "FROM venta v "
                + "JOIN usuario u ON v.id_usuario = u.id_usuario "
                + "WHERE v.id_venta = ?";

        try (PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setInt(1, id);

            try (ResultSet rs = stmt.executeQuery()) {
                if (rs.next()) {

                    Venta v = new Venta();

                    v.setIdVenta(rs.getInt("id_venta"));
                    v.setFechaHora(rs.getTimestamp("fecha_hora"));
                    v.setIdUsuario(rs.getInt("id_usuario"));
                    v.setNombreUsuario(rs.getString("usuario_nombre"));

                    v.setSubtotal(rs.getDouble("subtotal"));
                    v.setDescuento(rs.getDouble("descuento"));
                    v.setTotalConDescuento(rs.getDouble("total_con_descuento"));
                    v.setIva(rs.getDouble("iva"));
                    v.setTotal(rs.getDouble("total"));

                    v.setMetodoPago(rs.getString("metodo_pago"));
                    v.setEstado(rs.getString("estado"));

                    return v;
                }
            }
        }
        return null;
    }

    // CANCELAR VENTA
    public boolean cancelar(int idVenta) throws SQLException {

        String sql = "UPDATE venta SET estado='Cancelada' WHERE id_venta=?";

        try (PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setInt(1, idVenta);
            return stmt.executeUpdate() > 0;
        }
    }
}