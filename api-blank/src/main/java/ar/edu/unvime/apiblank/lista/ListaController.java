package ar.edu.unvime.apiblank.lista;

import ar.edu.unvime.apiblank.favorito.FavoritoResponse;
import java.net.URI;
import java.util.List;
import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

@RestController
@RequestMapping("/api/listas")
@io.swagger.v3.oas.annotations.tags.Tag(name = "listas", description = "CRUD de listas y sus favoritos")
public class ListaController {
    private final ListaService service;

    public ListaController(ListaService service) {
        this.service = service;
    }

    @GetMapping
    @io.swagger.v3.oas.annotations.Operation(summary = "Listar todas las listas")
    @io.swagger.v3.oas.annotations.responses.ApiResponse(responseCode = "200", description = "Listas obtenidas exitosamente")
    public List<ListaResponse> buscarTodas() {
        return service.buscarTodas();
    }

    @GetMapping("/{id}")
    @io.swagger.v3.oas.annotations.Operation(summary = "Buscar una lista por su ID")
    @io.swagger.v3.oas.annotations.responses.ApiResponses(value = {
        @io.swagger.v3.oas.annotations.responses.ApiResponse(responseCode = "200", description = "Lista encontrada"),
        @io.swagger.v3.oas.annotations.responses.ApiResponse(responseCode = "404", description = "La lista no existe")
    })
    public ListaResponse buscarPorId(@PathVariable Long id) {
        return service.buscarPorId(id);
    }

    @PostMapping
    @io.swagger.v3.oas.annotations.Operation(summary = "Crear una nueva lista")
    @io.swagger.v3.oas.annotations.responses.ApiResponses(value = {
        @io.swagger.v3.oas.annotations.responses.ApiResponse(responseCode = "201", description = "Lista creada exitosamente"),
        @io.swagger.v3.oas.annotations.responses.ApiResponse(responseCode = "400", description = "Datos de entrada inválidos o faltantes")
    })
    public ResponseEntity<ListaResponse> crear(@Valid @RequestBody CrearListaRequest request) {
        ListaResponse creada = service.crear(request);
        URI location = ServletUriComponentsBuilder.fromCurrentRequest()
            .path("/{id}")
            .buildAndExpand(creada.id())
            .toUri();
        return ResponseEntity.created(location).body(creada);
    }

    @DeleteMapping("/{id}")
    @io.swagger.v3.oas.annotations.Operation(summary = "Eliminar una lista (borrado en cascada)")
    @io.swagger.v3.oas.annotations.responses.ApiResponses(value = {
        @io.swagger.v3.oas.annotations.responses.ApiResponse(responseCode = "204", description = "Lista eliminada exitosamente (incluye sus favoritos)"),
        @io.swagger.v3.oas.annotations.responses.ApiResponse(responseCode = "404", description = "La lista no existe")
    })
    public ResponseEntity<Void> eliminar(@PathVariable Long id) {
        service.eliminar(id);
        return ResponseEntity.noContent().build();
    }

    @GetMapping("/{id}/favoritos")
    @io.swagger.v3.oas.annotations.Operation(summary = "Obtener todos los favoritos de una lista")
    @io.swagger.v3.oas.annotations.responses.ApiResponses(value = {
        @io.swagger.v3.oas.annotations.responses.ApiResponse(responseCode = "200", description = "Favoritos obtenidos exitosamente"),
        @io.swagger.v3.oas.annotations.responses.ApiResponse(responseCode = "404", description = "La lista no existe")
    })
    public List<FavoritoResponse> obtenerFavoritos(@PathVariable Long id) {
        return service.obtenerFavoritos(id);
    }

    @PostMapping("/{origenId}/mover-favoritos")
    @io.swagger.v3.oas.annotations.Operation(summary = "Mover favoritos a otra lista y eliminar la original")
    @io.swagger.v3.oas.annotations.responses.ApiResponses(value = {
        @io.swagger.v3.oas.annotations.responses.ApiResponse(responseCode = "200", description = "Favoritos movidos y lista original eliminada exitosamente"),
        @io.swagger.v3.oas.annotations.responses.ApiResponse(responseCode = "400", description = "Datos de entrada inválidos"),
        @io.swagger.v3.oas.annotations.responses.ApiResponse(responseCode = "404", description = "Alguna de las listas no existe")
    })
    public ResponseEntity<Void> moverFavoritos(
            @PathVariable Long origenId, 
            @Valid @RequestBody MoverFavoritosRequest request) {
        service.moverFavoritos(origenId, request);
        return ResponseEntity.ok().build();
    }
}
