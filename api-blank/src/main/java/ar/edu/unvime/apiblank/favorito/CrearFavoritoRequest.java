package ar.edu.unvime.apiblank.favorito;

/**
 * DTO de entrada: datos que envía el cliente para guardar o actualizar un favorito.
 * Cumple con el punto 4.1 de la consigna.
 */
public record CrearFavoritoRequest(
    Long productoId,
    String nota
) {}
