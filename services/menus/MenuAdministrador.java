package menus;

import models.Producto;
import services.ProductoService;
import java.util.Scanner;
import java.util.List;

public class MenuAdministrador {
    private static ProductoService service = new ProductoService();
    private static Scanner sc = new Scanner(System.in);

    // Este método se manda llamar desde tu MenuAdministrador principal al seleccionar "Productos"
    public static void seccionProductos() {
        int opcion = -1;
        do {
            System.out.println("\n--- 📦 ADMINISTRACIÓN DE PRODUCTOS ---");
            System.out.println("1. Agregar Producto");
            System.out.println("2. Editar Producto");
            System.out.println("3. Eliminar Producto");
            System.out.println("4. Consultar Todos los Productos");
            System.out.println("5. Buscar Producto por Nombre");
            System.out.println("0. Volver al Menú Principal");
            System.out.print("Selecciona una opción: ");
            
            try {
                opcion = Integer.parseInt(sc.nextLine());
                switch (opcion) {
                    case 1: menuAgregar(); break;
                    case 2: menuEditar(); break;
                    case 3: menuEliminar(); break;
                    case 4: mostrarProductos(); break;
                    case 5: menuBuscar(); break;
                    case 0: System.out.println("Volviendo..."); break;
                    default: System.out.println("⚠️ Opción no válida.");
                }
            } catch (NumberFormatException e) {
                System.out.println("⚠️ Por favor, introduce un número válido.");
            }
        } while (opcion != 0);
    }

    private static void menuAgregar() {
        System.out.println("\n--- Agregar Nuevo Producto ---");
        System.out.print("Nombre Comercial: "); String nombre = sc.nextLine();
        System.out.print("Marca / Laboratorio: "); String marca = sc.nextLine();
        System.out.print("Forma Farmacéutica: "); String forma = sc.nextLine();
        System.out.print("Vía de Administración: "); String via = sc.nextLine();
        System.out.print("Presentación: "); String presentacion = sc.nextLine();

        Producto nuevo = new Producto(0, nombre, marca, forma, via, presentacion);
        String resultado = service.registrarProducto(nuevo);
        System.out.println(resultado);
    }

    private static void menuEditar() {
        System.out.println("\n--- Editar Producto ---");
        System.out.print("Introduce el ID del producto a editar: ");
        int id = Integer.parseInt(sc.nextLine());
        System.out.print("Nuevo Nombre Comercial: "); String nombre = sc.nextLine();
        System.out.print("Nueva Marca / Laboratorio: "); String marca = sc.nextLine();
        System.out.print("Nueva Forma Farmacéutica: "); String forma = sc.nextLine();
        System.out.print("Nueva Vía de Administración: "); String via = sc.nextLine();
        System.out.print("Nueva Presentación: "); String presentacion = sc.nextLine();

        Producto editado = new Producto(id, nombre, marca, forma, via, presentacion);
        String resultado = service.modificarProducto(editado);
        System.out.println(resultado);
    }

    private static void menuEliminar() {
        System.out.println("\n--- Eliminar Producto ---");
        System.out.print("Introduce el ID del producto a borrar: ");
        int id = Integer.parseInt(sc.nextLine());
        String resultado = service.borrarProducto(id);
        System.out.println(resultado);
    }

    private static void mostrarProductos() {
        System.out.println("\n--- Lista Completa de Productos ---");
        List<Producto> lista = service.obtenerTodos();
        if(lista.isEmpty()) System.out.println("No hay productos registrados.");
        else lista.forEach(System.out.println);
    }

    private static void menuBuscar() {
        System.out.println("\n--- Buscar Producto ---");
        System.out.print("Escribe el nombre (o parte del nombre): ");
        String busqueda = sc.nextLine();
        List<Producto> lista = service.filtrarPorNombre(busqueda);
        if(lista.isEmpty()) System.out.println("No se encontraron coincidencias.");
        else lista.forEach(System.out.println);
    }
}
