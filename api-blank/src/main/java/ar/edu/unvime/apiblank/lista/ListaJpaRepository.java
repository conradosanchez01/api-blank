package ar.edu.unvime.apiblank.lista;

import org.springframework.data.jpa.repository.JpaRepository;
//La interfaz que Spring usa para generarnos el código SQL automáticamente (nuestro generador dinámico).
public interface ListaJpaRepository extends JpaRepository<ListaEntity, Long> {
}