package ar.edu.unvime.apiblank.producto;

import java.util.List;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * Capa Web: expone los endpoints del catálogo de productos.
 * Cumple con los puntos 2.3 y 2.4 de la consigna.
 */
@RestController
@RequestMapping("/api/productos")
public class ProductoController {

    private final ProductoService service;

    // Inyección de dependencias: Spring nos entrega el ProductoService
    public ProductoController(ProductoService service) {
        this.service = service;
    }

    /**
     * Punto 2.3: Exponer GET /api/productos
     * Devuelve la lista de productos mapeados a nuestro DTO ProductoResponse.
     */
    @GetMapping
    public List<ProductoResponse> listar() {
        return service.listar();
    }

    /**
     * Punto 2.4: Exponer GET /api/productos/{id}
     * Devuelve un producto individual por su ID.
     */
    @GetMapping("/{id}")
    public ProductoResponse buscarPorId(@PathVariable Long id) {
        return service.buscarPorId(id);
    }
}