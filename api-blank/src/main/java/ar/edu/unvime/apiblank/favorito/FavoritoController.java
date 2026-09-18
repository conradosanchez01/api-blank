package ar.edu.unvime.apiblank.favorito;

import java.net.URI;
import java.util.List;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * Controlador REST para el CRUD de Favoritos.
 * Cumple con los puntos 5 y 8 de la consigna.
 */
@RestController
@RequestMapping("/api/favoritos")
@Tag(name = "favoritos", description = "CRUD de favoritos en memoria")
public class FavoritoController {

    private final FavoritoService service;

    public FavoritoController(FavoritoService service) {
        this.service = service;
    }

    // GET /api/favoritos -> 200 OK
    @GetMapping
    @Operation(summary = "Listar todos los favoritos")
    public List<FavoritoResponse> listar() {
        return service.listar();
    }

    // GET /api/favoritos/{id} -> 200 OK
    @GetMapping("/{id}")
    @Operation(summary = "Buscar un favorito por su ID")
    public FavoritoResponse buscarPorId(@PathVariable Long id) {
        return service.buscarPorId(id);
    }

    // POST /api/favoritos -> 201 Created (con header Location)
    @PostMapping
    @Operation(summary = "Crear un nuevo favorito")
    public ResponseEntity<FavoritoResponse> crear(@Valid @RequestBody CrearFavoritoRequest request) {
        FavoritoResponse creado = service.crear(request);
        return ResponseEntity
            .created(URI.create("/api/favoritos/" + creado.id()))
            .body(creado);
    }

    // PUT /api/favoritos/{id} -> 200 OK
    @PutMapping("/{id}")
    @Operation(summary = "Actualizar un favorito existente")
    public FavoritoResponse actualizar(@PathVariable Long id, @Valid @RequestBody CrearFavoritoRequest request) {
        return service.actualizar(id, request);
    }

    // DELETE /api/favoritos/{id} -> 204 No Content
    @DeleteMapping("/{id}")
    @Operation(summary = "Eliminar un favorito por su ID")
    public ResponseEntity<Void> eliminar(@PathVariable Long id) {
        service.eliminar(id);
        return ResponseEntity.noContent().build();
    }
}
