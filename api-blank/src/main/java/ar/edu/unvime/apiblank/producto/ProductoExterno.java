package ar.edu.unvime.apiblank.producto;

import java.math.BigDecimal;

/**
 * Modelo interno para recibir los datos crudos que devuelve DummyJSON.
 * Los nombres de los campos coinciden con las claves del JSON externo.
 */
public record ProductoExterno(
    Long id,
    String title,
    String description,
    BigDecimal price,
    String category,
    String thumbnail
) {}
