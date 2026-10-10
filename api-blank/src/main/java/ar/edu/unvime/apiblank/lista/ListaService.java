package ar.edu.unvime.apiblank.lista;

import java.util.List;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ar.edu.unvime.apiblank.error.RecursoNoEncontradoException;
import ar.edu.unvime.apiblank.error.ConflictoEstadoException;
import ar.edu.unvime.apiblank.favorito.FavoritoService;
import ar.edu.unvime.apiblank.favorito.FavoritoResponse;
import ar.edu.unvime.apiblank.favorito.CrearFavoritoRequest;

@Service
public class ListaService {
    private final ListaRepository repository;
    private final FavoritoService favoritoService;

    public ListaService(ListaRepository repository, FavoritoService favoritoService) {
        this.repository = repository;
        this.favoritoService = favoritoService;
    }

    public List<ListaResponse> buscarTodas() {
        return repository.buscarTodas().stream()
            .map(l -> new ListaResponse(l.id(), l.nombre()))
            .toList();
    }

    public ListaResponse buscarPorId(Long id) {
        return repository.buscarPorId(id)
            .map(l -> new ListaResponse(l.id(), l.nombre()))
            .orElseThrow(() -> new RecursoNoEncontradoException("No existe la lista con id " + id));
    }

    public ListaResponse crear(CrearListaRequest request) {
        Lista nueva = new Lista(null, request.nombre());
        Lista guardada = repository.guardar(nueva);
        return new ListaResponse(guardada.id(), guardada.nombre());
    }

    public void eliminar(Long id) {
        // 1. Validamos que exista antes de intentar eliminar
        repository.buscarPorId(id)
            .orElseThrow(() -> new RecursoNoEncontradoException("No existe la lista con id " + id));
            
        // 2. Validamos que la lista esté vacía (Punto 5.6)
        List<FavoritoResponse> favoritos = favoritoService.buscarPorLista(id);
        if (!favoritos.isEmpty()) {
            throw new ConflictoEstadoException("No se puede eliminar la lista porque tiene " + favoritos.size() + " favoritos asociados.");
        }
        
        repository.eliminar(id);
    }

    public List<FavoritoResponse> obtenerFavoritos(Long id) {
        // Validamos que exista la lista primero
        buscarPorId(id);
        return favoritoService.buscarPorLista(id);
    }

    @Transactional
    public void moverFavoritos(Long origenId, MoverFavoritosRequest request) {
        Long destinoId = request.destinoId();

        // 1. Validamos que ambas listas existan (tira 404 si no)
        buscarPorId(origenId);
        buscarPorId(destinoId);

        // 2. Buscamos los favoritos de la lista original
        List<FavoritoResponse> favoritos = favoritoService.buscarPorLista(origenId);

        // 3. Reasignamos uno por uno a la lista destino
        for (FavoritoResponse f : favoritos) {
            CrearFavoritoRequest updateReq = new CrearFavoritoRequest(
                f.productoId(), 
                f.nota(), 
                destinoId
            );
            favoritoService.actualizar(f.id(), updateReq);
        }

        // 4. Eliminamos la lista original (ahora sí está vacía, pasa la validación 409)
        eliminar(origenId);
    }
}
