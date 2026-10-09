package ar.edu.unvime.apiblank.lista;

import jakarta.validation.constraints.NotBlank;

public record CrearListaRequest(
    @NotBlank(message = "El nombre de la lista es obligatorio")
    String nombre
) {}
