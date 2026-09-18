package ar.edu.unvime.apiblank.error;

import java.util.Map;

/**
 * Estructura uniforme para todas las respuestas de error de la API.
 */
public record ApiError(
    int status,
    String mensaje,
    Map<String, String> campos
) {}
