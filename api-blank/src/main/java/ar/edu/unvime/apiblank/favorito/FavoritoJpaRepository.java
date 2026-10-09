package ar.edu.unvime.apiblank.favorito;

import org.springframework.data.jpa.repository.JpaRepository;


public interface FavoritoJpaRepository extends JpaRepository<FavoritoEntity, Long> {
    // Al pasarle <FavoritoEntity, Long>, sabe qué tabla manejar y qué tipo de dato es su ID.
}