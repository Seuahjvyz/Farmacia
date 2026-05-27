package dao;

import models.Producto;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class ProductoDAO {
    
    public boolean buscarPorNombreExacto(String nombre) {
        String sql = "SELECT COUNT(*) FROM producto WHERE nombre_comercial = ?";
        try (Connection conn = Conexion.getConexion();
             PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setString(1, nombre);
            try (ResultSet rs = ps.executeQuery()) {
                if (rs.next()) {
                    return rs.getInt(1) > 0;
                }
            }
        } catch (SQLException e) {
            System.out.println("❌ Error al validar nombre único: " + e.getMessage());
        }
        return false;
    }

    // CREATE: Agregar producto
    public boolean agregar(Producto p) {
        String sql = "INSERT INTO producto (nombre_comercial, marca_laboratorio, forma_farmaceutica, via_administracion, presentacion) VALUES (?, ?, ?, ?, ?)";
        try (Connection conn = Conexion.getConexion();
             PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setString(1, p.getNombreComercial());
            ps.setString(2, p.getMarcaLaboratorio());
            ps.setString(3, p.getFormaFarmaceutica());
            ps.setString(4, p.getViaAdministracion());
            ps.setString(5, p.getPresentacion());
            return ps.executeUpdate() > 0;
        } catch (SQLException e) {
            System.out.println("❌ Error al agregar producto: " + e.getMessage());
            return false;
        }
    }

    // READ ALL: Consultar todos
    public List<Producto> consultarTodos() {
        List<Producto> lista = new ArrayList<>();
        String sql = "SELECT * FROM producto";
        try (Connection conn = Conexion.getConexion();
             PreparedStatement ps = conn.prepareStatement(sql);
             ResultSet rs = ps.executeQuery()) {
            while (rs.next()) {
                lista.add(new Producto(
                    rs.getInt("id_producto"),
                    rs.getString("nombre_comercial"),
                    rs.getString("marca_laboratorio"),
                    rs.getString("forma_farmaceutica"),
                    rs.getString("via_administracion"),
                    rs.getString("presentacion")
                ));
            }
        } catch (SQLException e) {
            System.out.println("❌ Error al consultar productos: " + e.getMessage());
        }
        return lista;
    }

    // READ BY NAME (Parcial/Buscador): Buscar por nombre
    public List<Producto> buscarPorNombre(String nombre) {
        List<Producto> lista = new ArrayList<>();
        String sql = "SELECT * FROM producto WHERE nombre_comercial  ?";
        try (Connection conn = Conexion.getConexion();
             PreparedStatement  = get.prepareStatement(sql)) {
            setString(1, "%" + nombre + "%");
            try (ResultSet rs = get.executeQuery()) {
                while (rs.next()) {
                    lista.add(new Producto(
                        getInt("id_producto"),
                        getString("nombre_comercial"),
                        getString("marca_laboratorio"),
                        getString("forma_farmaceutica"),
                        getString("via_administracion"),
                        getString("presentacion")
                    ));
                }
            }
        } catch (SQLException e) {
            System.out.println("❌ Error al buscar producto: " + e.getMessage());
        }
        return lista;
    }

    // UPDATE: Editar producto
    public boolean editar(Producto p) {
        String sql = "UPDATE producto SET nombre_comercial=?, marca_laboratorio=?, forma_farmaceutica=?, via_administracion=?, presentacion=? WHERE id_producto=?";
        try (Connection conn = Conexion.getConexion();
             PreparedStatement  = conn.prepareStatement(sql)) {
            ps.setString(1, p.getNombreComercial());
            ps.setString(2, p.getMarcaLaboratorio());
            ps.setString(3, p.getFormaFarmaceutica());
            ps.setString(4, p.getViaAdministracion());
            ps.setString(5, p.getPresentacion());
            ps.setInt(6, p.getIdProducto());
            return ps.executeUpdate() > 0;
        } catch (SQLException e) {
            System.out.println("❌ Error al editar producto: " + e.getMessage());
            return false;
        }
    }

    // DELETE: Eliminar producto
    public boolean eliminar(int id) {
        String sql = "DELETE FROM producto WHERE id_producto = ?";
        try (Connection conn = Conexion.getConexion();
             PreparedStatement  = conn.prepareStatement(sql)) {
            ps.setInt(1, id);
            return ps.executeUpdate() > 0;
        } catch (SQLException e) {
            System.out.println("❌ Error al eliminar producto. Asegúrate de que no tenga inventario o ventas asociadas: " + e.getMessage());
            return false;
        }
    }
}
