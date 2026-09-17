package ar.edu.unvime.apiblank.producto;

import java.util.List;

/**
 * Modelo para recibir la respuesta paginada que devuelve GET
 * https://dummyjson.com/products
 */
public record DummyJsonResponse(
        List<ProductoExterno> products,
        int total,
        int skip,
        int limit) {
}
