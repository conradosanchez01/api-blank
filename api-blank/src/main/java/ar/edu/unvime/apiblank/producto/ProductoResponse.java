package ar.edu.unvime.apiblank.producto;

import java.math.BigDecimal;

/**
 * DTO propio que expone nuestra API hacia afuera.
 * Cumple con el punto 2.2 de la consigna: "Definí un DTO propio para el producto".
 */
public record ProductoResponse(
    Long id,
    String nombre,
    String descripcion,
    BigDecimal precio,
    String categoria,
    String imagen
) {}
