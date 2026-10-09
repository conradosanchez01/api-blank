package ar.edu.unvime.apiblank.favorito;

import java.time.LocalDateTime;

/**
 * DTO de salida: datos que devuelve la API sobre un favorito.
 * Cumple con el punto 4.1 de la consigna.
 */
public record FavoritoResponse(
    Long id,
    Long productoId,
    String nota,
    LocalDateTime fechaAgregado,
    Long listaId
) {}
