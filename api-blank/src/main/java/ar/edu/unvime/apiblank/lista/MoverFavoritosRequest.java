package ar.edu.unvime.apiblank.lista;

import jakarta.validation.constraints.NotNull;

public record MoverFavoritosRequest(
    @NotNull(message = "El id de la lista destino es obligatorio")
    Long destinoId
) {}
