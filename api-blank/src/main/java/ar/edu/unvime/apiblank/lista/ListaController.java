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
public class ListaController {
    private final ListaService service;

    public ListaController(ListaService service) {
        this.service = service;
    }

    @GetMapping
    public List<ListaResponse> buscarTodas() {
        return service.buscarTodas();
    }

    @GetMapping("/{id}")
    public ListaResponse buscarPorId(@PathVariable Long id) {
        return service.buscarPorId(id);
    }

    @PostMapping
    public ResponseEntity<ListaResponse> crear(@Valid @RequestBody CrearListaRequest request) {
        ListaResponse creada = service.crear(request);
        URI location = ServletUriComponentsBuilder.fromCurrentRequest()
            .path("/{id}")
            .buildAndExpand(creada.id())
            .toUri();
        return ResponseEntity.created(location).body(creada);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> eliminar(@PathVariable Long id) {
        service.eliminar(id);
        return ResponseEntity.noContent().build();
    }

    @GetMapping("/{id}/favoritos")
    public List<FavoritoResponse> obtenerFavoritos(@PathVariable Long id) {
        return service.obtenerFavoritos(id);
    }
}
