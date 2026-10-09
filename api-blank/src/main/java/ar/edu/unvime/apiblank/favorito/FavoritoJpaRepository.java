package ar.edu.unvime.apiblank.favorito;

import org.springframework.data.jpa.repository.JpaRepository;


import java.util.List;

public interface FavoritoJpaRepository extends JpaRepository<FavoritoEntity, Long> {
    List<FavoritoEntity> findByListaId(Long listaId);
}