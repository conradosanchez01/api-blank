package ar.edu.unvime.apiblank.favorito;

import java.time.LocalDateTime;
import java.util.List;
import org.springframework.stereotype.Service;
import ar.edu.unvime.apiblank.error.RecursoNoEncontradoException;

/**
 * Capa de servicio para la gestión de favoritos (CRUD y reglas de negocio).
 */
@Service
public class FavoritoService {

    private final FavoritoRepository repository;

    public FavoritoService(FavoritoRepository repository) {
        this.repository = repository;
    }

    public List<FavoritoResponse> listar() {
        return repository.buscarTodos().stream()
            .map(this::aResponse)
            .toList();
    }

    public FavoritoResponse buscarPorId(Long id) {
        return repository.buscarPorId(id)
            .map(this::aResponse)
            .orElseThrow(() -> new RecursoNoEncontradoException("No existe el favorito con id " + id));
    }

    public FavoritoResponse crear(CrearFavoritoRequest request) {
        Favorito nuevo = new Favorito(
            null, // El ID se genera en el repositorio
            request.productoId(),
            request.nota(),
            LocalDateTime.now(), // Fecha automática de creación
            request.listaId()
        );
        return aResponse(repository.guardar(nuevo));
    }

    public FavoritoResponse actualizar(Long id, CrearFavoritoRequest request) {
        // Verificamos que exista primero
        Favorito existente = repository.buscarPorId(id)
            .orElseThrow(() -> new RecursoNoEncontradoException("No existe el favorito con id " + id));

        Favorito actualizado = new Favorito(
            id,
            request.productoId(),
            request.nota(),
            existente.fechaAgregado(), // Mantenemos la fecha original en que se agregó
            request.listaId()
        );
        return aResponse(repository.guardar(actualizado));
    }

    public void eliminar(Long id) {
        // Verificamos que exista antes de borrar
        repository.buscarPorId(id)
            .orElseThrow(() -> new RecursoNoEncontradoException("No existe el favorito con id " + id));
        repository.eliminar(id);
    }

    // Mapeo manual de la entidad Favorito hacia el DTO FavoritoResponse (Punto 4.2)
    private FavoritoResponse aResponse(Favorito favorito) {
        return new FavoritoResponse(
            favorito.id(),
            favorito.productoId(),
            favorito.nota(),
            favorito.fechaAgregado(),
            favorito.listaId()
        );
    }
}
