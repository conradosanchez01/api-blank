package ar.edu.unvime.apiblank.favorito;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

/**
 * DTO de entrada: datos que envía el cliente para guardar o actualizar un favorito.
 * Cumple con los puntos 4.1 y 6.1 de la consigna.
 */
public record CrearFavoritoRequest(
    @NotNull(message = "productoId es obligatorio")
    Long productoId,

    @NotBlank(message = "nota no puede estar vacía")
    String nota,

    @NotNull(message = "listaId es obligatorio")
    Long listaId
) {}
