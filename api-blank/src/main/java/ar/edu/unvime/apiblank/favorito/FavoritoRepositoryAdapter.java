package ar.edu.unvime.apiblank.favorito;

import org.springframework.stereotype.Component;
import ar.edu.unvime.apiblank.lista.ListaEntity;
import java.util.List;
import java.util.Optional;

@Component
public class FavoritoRepositoryAdapter implements FavoritoRepository {

    // Inyectamos nuestra interfaz de JPA
    private final FavoritoJpaRepository jpaRepository;

    public FavoritoRepositoryAdapter(FavoritoJpaRepository jpaRepository) {
        this.jpaRepository = jpaRepository;
    }

    @Override
    public List<Favorito> buscarTodos() {
        // Buscamos todas las Entities y las mapeamos (traducimos) a records Favorito
        return jpaRepository.findAll().stream()
            .map(entity -> new Favorito(
                entity.getId(),
                entity.getProductoId(),
                entity.getNota(),
                entity.getFechaAlta(),
                entity.getLista() != null ? entity.getLista().getId() : null
            ))
            .toList();
    }

    @Override
    public Optional<Favorito> buscarPorId(Long id) {
        return jpaRepository.findById(id)
            .map(entity -> new Favorito(
                entity.getId(),
                entity.getProductoId(),
                entity.getNota(),
                entity.getFechaAlta(),
                entity.getLista() != null ? entity.getLista().getId() : null
            ));
    }

    @Override
    public Favorito guardar(Favorito favorito) {
        ListaEntity listaRef = null;
        if (favorito.listaId() != null) {
            listaRef = new ListaEntity();
            listaRef.setId(favorito.listaId());
        }

        // 1. Traducimos el Record (Dominio) a la Entity (Base de datos)
        FavoritoEntity entity = new FavoritoEntity(
            favorito.id(),
            favorito.productoId(),
            favorito.nota(),
            favorito.fechaAgregado(),
            listaRef
        );

        // 2. Guardamos en BD. Nos devuelve el objeto con el ID que se autogeneró.
        FavoritoEntity guardado = jpaRepository.save(entity);

        // 3. Volvemos a traducir a Record para devolverlo
        return new Favorito(
            guardado.getId(),
            guardado.getProductoId(),
            guardado.getNota(),
            guardado.getFechaAlta(),
            guardado.getLista() != null ? guardado.getLista().getId() : null
        );
    }

    @Override
    public void eliminar(Long id) {
        jpaRepository.deleteById(id);
    }
}