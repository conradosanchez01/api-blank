package ar.edu.unvime.apiblank.lista;

import java.util.List;
import org.springframework.stereotype.Service;
import ar.edu.unvime.apiblank.error.RecursoNoEncontradoException;

@Service
public class ListaService {
    private final ListaRepository repository;

    public ListaService(ListaRepository repository) {
        this.repository = repository;
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
        // Validamos que exista antes de intentar eliminar
        repository.buscarPorId(id)
            .orElseThrow(() -> new RecursoNoEncontradoException("No existe la lista con id " + id));
            
        // TODO: (Punto 5.6) Falta validar que la lista esté vacía antes de borrar (Error 409)
        
        repository.eliminar(id);
    }
}
