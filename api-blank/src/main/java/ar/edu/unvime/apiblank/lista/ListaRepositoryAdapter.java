//Convierte el record Lista a ListaEntity y viceversa.

package ar.edu.unvime.apiblank.lista;

import org.springframework.stereotype.Component;
import java.util.List;
import java.util.Optional;

@Component
public class ListaRepositoryAdapter implements ListaRepository {

    private final ListaJpaRepository jpaRepository;

    public ListaRepositoryAdapter(ListaJpaRepository jpaRepository) {
        this.jpaRepository = jpaRepository;
    }

    @Override
    public List<Lista> buscarTodas() {
        return jpaRepository.findAll().stream()
            .map(entity -> new Lista(entity.getId(), entity.getNombre()))
            .toList();
    }

    @Override
    public Optional<Lista> buscarPorId(Long id) {
        return jpaRepository.findById(id)
            .map(entity -> new Lista(entity.getId(), entity.getNombre()));
    }

    @Override
    public Lista guardar(Lista lista) {
        ListaEntity entity = new ListaEntity(lista.id(), lista.nombre());
        ListaEntity guardado = jpaRepository.save(entity);
        return new Lista(guardado.getId(), guardado.getNombre());
    }

    @Override
    public void eliminar(Long id) {
        jpaRepository.deleteById(id);
    }
}