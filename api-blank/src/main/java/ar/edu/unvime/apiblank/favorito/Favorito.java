package ar.edu.unvime.apiblank.favorito;

import java.time.LocalDateTime;

/**
 * Entidad de dominio que representa un producto marcado como favorito.
 * Cumple con el punto 3.1 de la consigna.
 */
public record Favorito(
    Long id,
    Long productoId,
    String nota,
    LocalDateTime fechaAgregado,
    Long listaId
) {}
