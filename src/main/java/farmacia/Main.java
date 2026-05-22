package farmacia;

import farmacia.db.Conexion;
import farmacia.models.Usuario;
import farmacia.services.UsuarioService;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.Scanner;

public class Main {

    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        Connection conn = null;

        try {
            conn = Conexion.getConnection();
            System.out.println("\n¡Conexion exitosa!");
        } catch (SQLException e) {
            System.out.println("Error en la conexión:");
            System.out.println("Error: " + e.getMessage());
            scanner.close();
            return;
        }

        boolean salir = false;

        while (!salir) {
            System.out.println("\n----------------------------------------");
            System.out.println("         SISTEMA FARMACIA");
            System.out.println("-----------------------------------------");
            System.out.println("1. Iniciar sesión");
            System.out.println("0. Salir");
            System.out.print("Opción: ");

            String opcionStr = scanner.nextLine();

            try {
                int opcion = Integer.parseInt(opcionStr);

                if (opcion == 1) {

                    boolean loginExitoso = false;

                    while (!loginExitoso) {
                        try {
                            System.out.println("\n-------- INICIO DE SESIÓN --------");
                            System.out.print("Usuario: ");
                            String usuario = scanner.nextLine();

                            System.out.print("Contraseña: ");
                            String contrasena = scanner.nextLine();

                            if (usuario.trim().isEmpty() || contrasena.trim().isEmpty()) {
                                System.out.println("Error: Usuario y contraseña son obligatorios");
                                continue;
                            }

                            Connection connLogin = null;

                            try {
                                connLogin = Conexion.getConnection();
                                
                                UsuarioService usuarioService = new UsuarioService(connLogin);
                                Usuario usuarioAutenticado = usuarioService.autenticar(usuario, contrasena);

                                if (usuarioAutenticado != null) {
                                    System.out.println("\n¡Bienvenid@ " + usuarioAutenticado.getNombre() + "!");
                                    System.out.println("Rol: " + usuarioAutenticado.getRol());

                                    // Menú según el rol
                                    if (usuarioAutenticado.getRol().equalsIgnoreCase("admin")) {
                                        System.out.println("...");
                                        // TODO: MenuAdministrador.main(args);
                                    } else if (usuarioAutenticado.getRol().equalsIgnoreCase("vendedor")) {
                                        System.out.println("...");
                                        // TODO: MenuVendedor.main(args);
                                    } else {
                                        System.out.println("Error: Rol no reconocido");
                                    }

                                    loginExitoso = true;

                                } else {
                                    System.out.println("Usuario o contraseña incorrectos");
                                    System.out.print("¿Intentar de nuevo? (s/n): ");
                                    String reintentar = scanner.nextLine();

                                    if (reintentar.equalsIgnoreCase("n")) {
                                        loginExitoso = true;
                                    }
                                }

                            } catch (SQLException e) {
                                System.out.println("Error en la base de datos: " + e.getMessage());
                                loginExitoso = true;
                            } finally {
                                if (connLogin != null) {
                                    Conexion.cerrarConexion(connLogin);
                                }
                            }

                        } catch (Exception e) {
                            System.out.println("Error en login: " + e.getMessage());
                        }
                    }

                } else if (opcion == 0) {
                    System.out.println("\n¡Adioooooooo =)!");
                    salir = true;
                } else {
                    System.out.println("Opción no válida. Intente con 1 o 0.");
                }

            } catch (NumberFormatException e) {
                System.out.println("Error: Debe ingresar un número (1 o 0)");
                System.out.println("   Escribiste: " + opcionStr);
            } catch (Exception e) {
                System.out.println("Error en el menú: " + e.getMessage());
            }
        }

        Conexion.cerrarConexion(conn);
        scanner.close();
        System.out.println("Conexión cerrada. Programa finalizado.");
    }
}
