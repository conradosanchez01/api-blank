package ar.edu.unvime.apiblank.lista;

import java.util.List;
import java.util.Optional;

public interface ListaRepository {
    List<Lista> buscarTodas();
    Optional<Lista> buscarPorId(Long id);
    Lista guardar(Lista lista);
    void eliminar(Long id);
}