package farmacia.menus;

import farmacia.db.Conexion;
import farmacia.models.Usuario;
import farmacia.services.UsuarioService;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.List;
import java.util.Scanner;

public class MenuAdministrador {

    private static Scanner scanner = new Scanner(System.in);
    private static Connection conn;
    private static Usuario usuarioActual;

    // Metodo para recibir el usuario directamente desde Main.java
    public static void iniciar(Usuario usuario) {
        usuarioActual = usuario;
        try {
            conn = Conexion.getConnection();
            mostrarMenuPrincipal();
        } catch (SQLException e) {
            System.out.println("Error de conexion: " + e.getMessage());
        } finally {
            Conexion.cerrarConexion(conn);
        }
    }

    private static void mostrarMenuPrincipal() {
        boolean salir = false;

        while (!salir) {
            System.out.println("\n----------------------------------------");
            System.out.println("         MENU ADMINISTRADOR");
            System.out.println("-----------------------------------------");
            System.out.println("1. Usuarios");
            System.out.println("2. Productos");
            System.out.println("3. Inventario");
            System.out.println("4. Ventas (Consultas)");
            System.out.println("0. Cerrar sesion");
            System.out.print("Opcion: ");

            String opcionStr = scanner.nextLine();

            try {
                int opcion = Integer.parseInt(opcionStr);

                switch (opcion) {
                    case 1:
                        menuUsuarios();
                        break;
                    case 2:
                        menuProductos();
                        break;
                    case 3:
                        menuInventario(); // <-- AQUÍ YA ENTRA TU MÓDULO
                        break;
                    case 4:
                        menuVentas();
                        break;
                    case 0:
                        System.out.println("Cerrando sesion...");
                        salir = true;
                        break;
                    default:
                        System.out.println("Opcion no valida");
                }
            } catch (NumberFormatException e) {
                System.out.println("Error: Ingrese un numero");
            }
        }
    }

    private static void menuUsuarios() {
        boolean regresar = false;

        while (!regresar) {
            System.out.println("\n----------------------------------------");
            System.out.println("         MENU USUARIOS");
            System.out.println("-----------------------------------------");
            System.out.println("1. Agregar usuario");
            System.out.println("2. Editar usuario");
            System.out.println("3. Eliminar usuario");
            System.out.println("4. Consultar usuarios");
            System.out.println("5. Regresar");
            System.out.print("Opcion: ");

            String opcionStr = scanner.nextLine();

            try {
                int opcion = Integer.parseInt(opcionStr);

                switch (opcion) {
                    case 1:
                        agregarUsuario();
                        break;
                    case 2:
                        editarUsuario();
                        break;
                    case 3:
                        eliminarUsuario();
                        break;
                    case 4:
                        consultarUsuarios();
                        break;
                    case 5:
                        regresar = true;
                        break;
                    default:
                        System.out.println("Opcion no valida");
                }
            } catch (NumberFormatException e) {
                System.out.println("Error: Ingrese un numero");
            }
        }
    }

    private static void agregarUsuario() {
        System.out.println("\n--- AGREGAR USUARIO ---");
        System.out.print("Nombre: ");
        String nombre = scanner.nextLine();
        System.out.print("Usuario: ");
        String user = scanner.nextLine();
        System.out.print("Correo: ");
        String correo = scanner.nextLine();
        System.out.print("Contraseña: ");
        String contrasena = scanner.nextLine();
        System.out.print("Rol (1=Administrador, 2=Vendedor): ");
        int idRol = Integer.parseInt(scanner.nextLine());

        try {
            UsuarioService service = new UsuarioService(conn);
            Usuario nuevo = new Usuario();
            nuevo.setNombre(nombre);
            nuevo.setUser(user);
            nuevo.setCorreo(correo);
            nuevo.setContrasena(contrasena);
            nuevo.setIdRol(idRol);
            nuevo.setActivo(true);

            if (service.crearUsuario(nuevo)) {
                System.out.println("Usuario creado exitosamente");
            } else {
                System.out.println("Error al crear usuario");
            }
        } catch (SQLException e) {
            System.out.println("Error: " + e.getMessage());
        }
    }

    private static void editarUsuario() {
        System.out.println("\n--- EDITAR USUARIO ---");
        System.out.print("ID de usuario a editar: ");
        int id = Integer.parseInt(scanner.nextLine());

        try {
            UsuarioService service = new UsuarioService(conn);
            Usuario u = service.buscarPorId(id);

            if (u == null) {
                System.out.println("Usuario no encontrado");
                return;
            }

            System.out.println("Usuario actual: " + u.getNombre() + " (" + u.getUser() + ")");
            System.out.print("Nuevo nombre (Enter para mantener): ");
            String nombre = scanner.nextLine();
            if (!nombre.isEmpty()) {
                u.setNombre(nombre);
            }

            System.out.print("Nuevo correo (Enter para mantener): ");
            String correo = scanner.nextLine();
            if (!correo.isEmpty()) {
                u.setCorreo(correo);
            }

            System.out.print("Nueva contraseña (Enter para mantener): ");
            String contrasena = scanner.nextLine();
            if (!contrasena.isEmpty()) {
                u.setContrasena(contrasena);
            }

            System.out.print("Activo | true/false | (Enter para mantener): ");
            String activo = scanner.nextLine();
            if (!activo.isEmpty()) {
                u.setActivo(Boolean.parseBoolean(activo));
            }

            System.out.print("Nuevo rol | 1=Administrador, 2=Vendedor | (Enter para mantener): ");
            String rol = scanner.nextLine();
            if (!rol.isEmpty()) {
                u.setIdRol(Integer.parseInt(rol));
            }

            if (service.actualizarUsuario(u)) {
                System.out.println("Usuario actualizado");
            } else {
                System.out.println("Error al actualizar");
            }
        } catch (SQLException e) {
            System.out.println("Error: " + e.getMessage());
        }
    }

    private static void eliminarUsuario() {
        System.out.println("\n--- ELIMINAR USUARIO ---");
        System.out.print("ID de usuario a eliminar: ");
        int id = Integer.parseInt(scanner.nextLine());

        try {
            UsuarioService service = new UsuarioService(conn);
            if (service.eliminarUsuario(id)) {
                System.out.println("Usuario eliminado");
            } else {
                System.out.println("Usuario no encontrado");
            }
        } catch (SQLException e) {
            System.out.println("Error: " + e.getMessage());
        }
    }

    private static void consultarUsuarios() {
        System.out.println("\n--- CONSULTAR USUARIOS ---");
        try {
            UsuarioService service = new UsuarioService(conn);
            List<Usuario> usuarios = service.listarTodos();

            if (usuarios.isEmpty()) {
                System.out.println("No hay usuarios registrados");
                return;
            }

            System.out.println("\nID |   Nombre   |   Usuario   |   Correo   |   Rol   |   Activo  ");
            System.out.println("----------------------------------------");
            for (Usuario u : usuarios) {
                System.out.println(u.getIdUsuario() + " | " + u.getNombre() + " | " + u.getUser() + " | " + u.getCorreo() + " | " + u.getNombreRol() + " | " + (u.isActivo() ? "Si" : "No"));
            }
        } catch (SQLException e) {
            System.out.println("Error: " + e.getMessage());
        }
    }

    private static void menuProductos() {
        boolean regresar = false;

        while (!regresar) {
            System.out.println("\n----------------------------------------");
            System.out.println("         MENU PRODUCTOS");
            System.out.println("-----------------------------------------");
            System.out.println("1. Agregar producto");
            System.out.println("2. Editar producto");
            System.out.println("3. Eliminar producto");
            System.out.println("4. Consultar productos");
            System.out.println("5. Regresar");
            System.out.print("Opcion: ");

            String opcionStr = scanner.nextLine();

            try {
                int opcion = Integer.parseInt(opcionStr);

                switch (opcion) {
                    case 1:
                        System.out.println("Funcionalidad en desarrollo");
                        break;
                    case 2:
                        System.out.println("Funcionalidad en desarrollo");
                        break;
                    case 3:
                        System.out.println("Funcionalidad en desarrollo");
                        break;
                    case 4:
                        System.out.println("Funcionalidad en desarrollo");
                        break;
                    case 5:
                        regresar = true;
                        break;
                    default:
                        System.out.println("Opcion no valida");
                }
            } catch (NumberFormatException e) {
                System.out.println("Error: Ingrese un numero");
            }
        }
    }

    private static void menuInventario() {
        // Ahora llamamos a tu clase MenuAdminInventario que ya tiene las 4 opciones
        // que pide la guía: consultar todo, consultar por producto, agregar lote, editar
        MenuAdminInventario menuInv = new MenuAdminInventario();
        menuInv.mostrarMenu(scanner);
    }

    private static void menuVentas() {
        boolean regresar = false;

        while (!regresar) {
            System.out.println("\n----------------------------------------");
            System.out.println("      MENU VENTAS (CONSULTAS)");
            System.out.println("-----------------------------------------");
            System.out.println("1. Ventas de hoy");
            System.out.println("2. Ventas de la semana");
            System.out.println("3. Ventas del mes");
            System.out.println("4. Ventas por rango de fechas");
            System.out.println("5. Productos mas vendidos");
            System.out.println("6. Ganancias totales");
            System.out.println("7. Regresar");
            System.out.print("Opcion: ");

            String opcionStr = scanner.nextLine();

            try {
                int opcion = Integer.parseInt(opcionStr);

                switch (opcion) {
                    case 1:
                        System.out.println("Funcionalidad en desarrollo");
                        break;
                    case 2:
                        System.out.println("Funcionalidad en desarrollo");
                        break;
                    case 3:
                        System.out.println("Funcionalidad en desarrollo");
                        break;
                    case 4:
                        System.out.println("Funcionalidad en desarrollo");
                        break;
                    case 5:
                        System.out.println("Funcionalidad en desarrollo");
                        break;
                    case 6:
                        System.out.println("Funcionalidad en desarrollo");
                        break;
                    case 7:
                        regresar = true;
                        break;
                    default:
                        System.out.println("Opcion no valida");
                }
            } catch (NumberFormatException e) {
                System.out.println("Error: Ingrese un numero");
            }
        }
    }
}
