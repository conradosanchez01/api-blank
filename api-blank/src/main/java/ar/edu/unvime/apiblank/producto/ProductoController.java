package ar.edu.unvime.apiblank.producto;

import java.util.List;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * Capa Web: expone los endpoints del catálogo de productos.
 * Cumple con los puntos 2.3, 2.4 y 8 de la consigna.
 */
@RestController
@RequestMapping("/api/productos")
@Tag(name = "productos", description = "Catálogo de productos externos (DummyJSON)")
public class ProductoController {

    private final ProductoService service;

    public ProductoController(ProductoService service) {
        this.service = service;
    }

    /**
     * Punto 2.3: Exponer GET /api/productos
     */
    @GetMapping
    @Operation(summary = "Listar todos los productos del catálogo")
    public List<ProductoResponse> listar() {
        return service.listar();
    }

    /**
     * Punto 2.4: Exponer GET /api/productos/{id}
     */
    @GetMapping("/{id}")
    @Operation(summary = "Buscar un producto por ID")
    public ProductoResponse buscarPorId(@PathVariable Long id) {
        return service.buscarPorId(id);
    }
}