package farmacia.dao;

import farmacia.models.Usuario;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class UsuarioDAO {

    private Connection conn;

    public UsuarioDAO(Connection conn) {
        this.conn = conn;
    }

    public Usuario autenticar(String user, String contrasena) throws SQLException {
        String sql = "SELECT u.*, r.nombre as rol_nombre "
                + "FROM usuario u "
                + "JOIN rol r ON u.id_rol = r.id_rol "
                + "WHERE u.user = ? AND u.contrasena = ? AND u.activo = 1";

        try (PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setString(1, user);
            stmt.setString(2, contrasena);

            try (ResultSet rs = stmt.executeQuery()) {
                if (rs.next()) {
                    return mapearUsuario(rs);
                }
            }
        }
        return null;
    }

    public List<Usuario> listarTodos() throws SQLException {
        List<Usuario> usuarios = new ArrayList<>();
        String sql = "SELECT u.*, r.nombre as rol_nombre "
                + "FROM usuario u "
                + "JOIN rol r ON u.id_rol = r.id_rol "
                + "ORDER BY u.id_usuario";

        try (PreparedStatement stmt = conn.prepareStatement(sql); ResultSet rs = stmt.executeQuery()) {

            while (rs.next()) {
                usuarios.add(mapearUsuario(rs));
            }
        }
        return usuarios;
    }

    public Usuario buscarPorId(int idUsuario) throws SQLException {
        String sql = "SELECT u.*, r.nombre as rol_nombre "
                + "FROM usuario u "
                + "JOIN rol r ON u.id_rol = r.id_rol "
                + "WHERE u.id_usuario = ?";

        try (PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setInt(1, idUsuario);

            try (ResultSet rs = stmt.executeQuery()) {
                if (rs.next()) {
                    return mapearUsuario(rs);
                }
            }
        }
        return null;
    }

    public Usuario buscarPorUser(String user) throws SQLException {
        String sql = "SELECT u.*, r.nombre as rol_nombre "
                + "FROM usuario u "
                + "JOIN rol r ON u.id_rol = r.id_rol "
                + "WHERE u.user = ?";

        try (PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setString(1, user);

            try (ResultSet rs = stmt.executeQuery()) {
                if (rs.next()) {
                    return mapearUsuario(rs);
                }
            }
        }
        return null;
    }

    public boolean insertar(Usuario usuario) throws SQLException {
        String sql = "INSERT INTO usuario (nombre, user, correo, contrasena, id_rol) "
                + "VALUES (?, ?, ?, ?, ?)";

        try (PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setString(1, usuario.getNombre());
            stmt.setString(2, usuario.getUser());
            stmt.setString(3, usuario.getCorreo());
            stmt.setString(4, usuario.getContrasena());
            stmt.setInt(5, usuario.getIdRol());

            return stmt.executeUpdate() > 0;
        }
    }

    public boolean actualizar(Usuario usuario) throws SQLException {
        String sql = "UPDATE usuario SET nombre = ?, user = ?, correo = ?, "
                + "contrasena = ?, id_rol = ?, activo = ? WHERE id_usuario = ?";

        try (PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setString(1, usuario.getNombre());
            stmt.setString(2, usuario.getUser());
            stmt.setString(3, usuario.getCorreo());
            stmt.setString(4, usuario.getContrasena());
            stmt.setInt(5, usuario.getIdRol());
            stmt.setBoolean(6, usuario.isActivo());
            stmt.setInt(7, usuario.getIdUsuario());

            return stmt.executeUpdate() > 0;
        }
    }

    public boolean eliminar(int idUsuario) throws SQLException {
        String sql = "DELETE FROM usuario WHERE id_usuario = ?";

        try (PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setInt(1, idUsuario);
            return stmt.executeUpdate() > 0;
        }
    }

    private Usuario mapearUsuario(ResultSet rs) throws SQLException {
        Usuario u = new Usuario();
        u.setIdUsuario(rs.getInt("id_usuario"));
        u.setNombre(rs.getString("nombre"));
        u.setUser(rs.getString("user"));
        u.setCorreo(rs.getString("correo"));
        u.setContrasena(rs.getString("contrasena"));
        u.setActivo(rs.getBoolean("activo"));
        u.setIdRol(rs.getInt("id_rol"));
        u.setNombreRol(rs.getString("rol_nombre"));
        u.setFechaRegistro(rs.getTimestamp("fecha_registro"));
        return u;
    }
}
