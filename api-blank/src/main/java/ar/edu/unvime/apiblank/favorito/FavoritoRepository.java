package ar.edu.unvime.apiblank.favorito;

import java.util.List;
import java.util.Optional;

/**
 * Contrato de persistencia para la entidad Favorito.
 * Cumple con el punto 3.2 de la consigna.
 */
public interface FavoritoRepository {
    List<Favorito> buscarTodos();
    Optional<Favorito> buscarPorId(Long id);
    Favorito guardar(Favorito favorito);
    void eliminar(Long id);
}
