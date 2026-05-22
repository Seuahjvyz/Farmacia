package farmacia.services;

import farmacia.dao.UsuarioDAO;
import farmacia.models.Usuario;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.List;

public class UsuarioService {

    private UsuarioDAO usuarioDAO;

    public UsuarioService(Connection conn) {
        this.usuarioDAO = new UsuarioDAO(conn);
    }

    public Usuario autenticar(String user, String contrasena) throws SQLException {
        if (user == null || user.trim().isEmpty()) {
            System.out.println("El usuario no puede estar vacio");
            return null;
        }

        if (contrasena == null || contrasena.trim().isEmpty()) {
            System.out.println("La contraseña no puede estar vacia");
            return null;
        }

        return usuarioDAO.autenticar(user, contrasena);
    }

    public List<Usuario> listarTodos() throws SQLException {
        return usuarioDAO.listarTodos();
    }

    public Usuario buscarPorId(int idUsuario) throws SQLException {
        if (idUsuario <= 0) {
            System.out.println("ID invalido");
            return null;
        }
        return usuarioDAO.buscarPorId(idUsuario);
    }

    public Usuario buscarPorUser(String user) throws SQLException {
        if (user == null || user.trim().isEmpty()) {
            System.out.println("Usuario invalido");
            return null;
        }
        return usuarioDAO.buscarPorUser(user);
    }

    public boolean crearUsuario(Usuario usuario) throws SQLException {
        if (usuario == null) {
            System.out.println("Usuario no puede ser nulo");
            return false;
        }

        if (usuario.getNombre() == null || usuario.getNombre().trim().isEmpty()) {
            System.out.println("El nombre es obligatorio");
            return false;
        }

        if (usuario.getUser() == null || usuario.getUser().trim().isEmpty()) {
            System.out.println("El nombre de usuario es obligatorio");
            return false;
        }

        if (usuario.getContrasena() == null || usuario.getContrasena().trim().isEmpty()) {
            System.out.println("La contraseña es obligatoria");
            return false;
        }

        return usuarioDAO.insertar(usuario);
    }

    public boolean actualizarUsuario(Usuario usuario) throws SQLException {
        if (usuario == null || usuario.getIdUsuario() <= 0) {
            System.out.println("Usuario invalido");
            return false;
        }
        return usuarioDAO.actualizar(usuario);
    }

    public boolean eliminarUsuario(int idUsuario) throws SQLException {
        if (idUsuario <= 0) {
            System.out.println("ID invalido");
            return false;
        }
        return usuarioDAO.eliminar(idUsuario);
    }
}
