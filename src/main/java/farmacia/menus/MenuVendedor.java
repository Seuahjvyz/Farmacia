package farmacia.menus;

import farmacia.db.Conexion;
import farmacia.models.Usuario;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.Scanner;

public class MenuVendedor {

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
            System.out.println("         MENU VENDEDOR");
            System.out.println("-----------------------------------------");
            System.out.println("1. Hacer venta");
            System.out.println("2. Consultar productos");
            System.out.println("3. Consultar stock");
            System.out.println("4. Mis ventas");
            System.out.println("0. Cerrar sesion");
            System.out.print("Opcion: ");

            String opcionStr = scanner.nextLine();

            try {
                int opcion = Integer.parseInt(opcionStr);

                switch (opcion) {
                    case 1:
                        hacerVenta();
                        break;
                    case 2:
                        consultarProductos();
                        break;
                    case 3:
                        consultarStock();
                        break;
                    case 4:
                        misVentas();
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

    private static void hacerVenta() {
        System.out.println("Funcionalidad en desarrollo");
    }

    private static void consultarProductos() {
        System.out.println("Funcionalidad en desarrollo");
    }

    private static void consultarStock() {
        System.out.println("Funcionalidad en desarrollo");
    }

    private static void misVentas() {
        System.out.println("Funcionalidad en desarrollo");
    }
}
