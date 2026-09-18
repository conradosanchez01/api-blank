package ar.edu.unvime.apiblank.favorito;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.atomic.AtomicLong;
import org.springframework.stereotype.Repository;

/**
 * Implementación en memoria del repositorio de favoritos sin JPA.
 * Cumple con el punto 3.2 de la consigna.
 */
@Repository
public class FavoritoRepositoryMemoria implements FavoritoRepository {

    // Almacenamiento en memoria concurrente: clave es el ID, valor es el Favorito
    private final Map<Long, Favorito> datos = new ConcurrentHashMap<>();
    
    // Generador autoincremental de IDs (1, 2, 3...)
    private final AtomicLong secuencia = new AtomicLong();

    @Override
    public List<Favorito> buscarTodos() {
        return new ArrayList<>(datos.values());
    }

    @Override
    public Optional<Favorito> buscarPorId(Long id) {
        return Optional.ofNullable(datos.get(id));
    }

    @Override
    public Favorito guardar(Favorito favorito) {
        // Si el favorito no tiene ID, le asignamos el siguiente de la secuencia
        Long id = (favorito.id() == null) ? secuencia.incrementAndGet() : favorito.id();
        
        Favorito guardado = new Favorito(
            id,
            favorito.productoId(),
            favorito.nota(),
            favorito.fechaAgregado()
        );
        
        datos.put(id, guardado);
        return guardado;
    }

    @Override
    public void eliminar(Long id) {
        datos.remove(id);
    }
}
