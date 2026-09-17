package ar.edu.unvime.apiblank.producto;

import java.util.List;
import org.springframework.stereotype.Service;

/**
 * Capa de negocio: coordina la obtención de productos desde el cliente externo
 * y su transformación (mapeo) a nuestro DTO propio.
 */
@Service
public class ProductoService {

    private final DummyJsonClient cliente;

    // Inyección de dependencias por constructor
    public ProductoService(DummyJsonClient cliente) {
        this.cliente = cliente;
    }

    /**
     * Obtiene todos los productos externos y los transforma en una lista de ProductoResponse.
     */
    public List<ProductoResponse> listar() {
        DummyJsonResponse respuesta = cliente.obtenerTodos();
        
        return respuesta.products().stream()
            .map(this::mapear)
            .toList();
    }

    /**
     * Obtiene un producto por su id y lo transforma a ProductoResponse.
     */
    public ProductoResponse buscarPorId(Long id) {
        ProductoExterno externo = cliente.obtenerPorId(id);
        return mapear(externo);
    }

    /**
     * Método auxiliar privado para convertir de ProductoExterno a ProductoResponse.
     */
    private ProductoResponse mapear(ProductoExterno externo) {
        return new ProductoResponse(
            externo.id(),
            externo.title(),       // Mapeamos 'title' a 'nombre'
            externo.description(), // Mapeamos 'description' a 'descripcion'
            externo.price(),       // Mapeamos 'price' a 'precio'
            externo.category(),    // Mapeamos 'category' a 'categoria'
            externo.thumbnail()    // Mapeamos 'thumbnail' a 'imagen'
        );
    }
}