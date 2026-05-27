package services;

import dao.ProductoDAO;
import models.Producto;
import java.util.List;

public class ProductoService {
    private ProductoDAO productoDAO = new ProductoDAO();

    public String registrarProducto(Producto p) {
        
        if (getNombreComercial().trim().isEmpty() || getMarcaLaboratorio().trim().isEmpty() || 
            getFormaFarmaceutica().trim().isEmpty() || getViaAdministracion().trim().isEmpty() || 
            getPresentacion().trim().isEmpty()) {
            return "⚠️ Error: Todos los campos son obligatorios.";
        }
        
        if (productoDAO.buscarPorNombreExacto(p.getNombreComercial())) {
            return "⚠️ Error: Ya existe un producto registrado con el nombre '" + p.getNombreComercial() + "'.";
        }

        boolean exito = productoDAO.agregar(p);
        return exito ? "✅ Producto agregado con éxito." : "❌ No se pudo agregar el producto de forma interna.";
    }

    public String modificarProducto(Producto p) {
        if (p.getNombreComercial().trim().isEmpty() || p.getMarcaLaboratorio().trim().isEmpty()) {
            return "⚠️ Error: El nombre y la marca no pueden quedar vacíos.";
        }
        
        boolean exito = productoDAO.editar(p);
        return exito ? "✅ Producto actualizado con éxito." : "❌ No se pudo actualizar el producto.";
    }

    public List<Producto> obtenerTodos() {
        return productoDAO.consultarTodos();
    }

    public List<Producto> filtrarPorNombre(String nombre) {
        return productoDAO.buscarPorNombre(nombre);
    }

    public String borrarProducto(int id) {
        boolean exito = productoDAO.eliminar(id);
        return exito ? "✅ Producto eliminado con éxito." : "❌ No se pudo eliminar el producto.";
    }
}
