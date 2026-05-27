package farmacia.services;

import farmacia.dao.VentaDAO;
import farmacia.dao.InventarioDAO;
import farmacia.models.Venta;
import farmacia.models.DetalleVenta;

import java.sql.Connection;
import java.util.List;

public class VentaService {

    private Connection conn;
    private VentaDAO ventaDAO;
    private InventarioDAO inventarioDAO;

    public VentaService(Connection conn) {
        this.conn = conn;
        this.ventaDAO = new VentaDAO(conn);
        this.inventarioDAO = new InventarioDAO(conn);
    }

    public boolean registrarVenta(Venta venta, List<DetalleVenta> detalles) {

        try {
            conn.setAutoCommit(false);

            int idVenta = ventaDAO.insertarVenta(venta);

            if (idVenta == -1) {
                conn.rollback();
                return false;
            }

            double subtotal = 0;

            for (DetalleVenta d : detalles) {

                subtotal += d.getSubtotal();
                d.setIdVenta(idVenta);

                boolean ok = inventarioDAO.descontarStock(
                        d.getIdInventario(),
                        d.getCantidad()
                );

                if (!ok) {
                    conn.rollback();
                    return false;
                }

                String sql = "INSERT INTO detalle_venta "
                        + "(id_venta, id_inventario, cantidad, precio_unitario, subtotal) "
                        + "VALUES (?, ?, ?, ?, ?)";

                try (var stmt = conn.prepareStatement(sql)) {
                    stmt.setInt(1, d.getIdVenta());
                    stmt.setInt(2, d.getIdInventario());
                    stmt.setInt(3, d.getCantidad());
                    stmt.setDouble(4, d.getPrecioUnitario());
                    stmt.setDouble(5, d.getSubtotal());
                    stmt.executeUpdate();
                }
            }

            double descuento = 0;
            double totalConDesc = subtotal - descuento;
            double iva = totalConDesc * 0.16;
            double total = totalConDesc + iva;

            String update = "UPDATE venta SET subtotal=?, descuento=?, total_con_descuento=?, iva=?, total=? WHERE id_venta=?";

            try (var stmt = conn.prepareStatement(update)) {
                stmt.setDouble(1, subtotal);
                stmt.setDouble(2, descuento);
                stmt.setDouble(3, totalConDesc);
                stmt.setDouble(4, iva);
                stmt.setDouble(5, total);
                stmt.setInt(6, idVenta);
                stmt.executeUpdate();
            }

            conn.commit();
            return true;

        } catch (Exception e) {
            try { conn.rollback(); } catch (Exception ex) {}
            e.printStackTrace();
            return false;
        }
    }

    public List<Venta> listar() throws Exception {
        return ventaDAO.listarTodos();
    }

    public Venta buscar(int id) throws Exception {
        return ventaDAO.buscarPorId(id);
    }

    public boolean cancelar(int idVenta) throws Exception {
        return ventaDAO.cancelar(idVenta);
    }
}