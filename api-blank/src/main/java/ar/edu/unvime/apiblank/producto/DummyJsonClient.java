package ar.edu.unvime.apiblank.producto;

import org.springframework.stereotype.Component;
import org.springframework.web.client.RestClient;

/**
 * Cliente HTTP encargado exclusivamente de comunicarse con la API externa DummyJSON.
 */
@Component
public class DummyJsonClient {

    private final RestClient restClient;

    // Usamos el builder nativo de RestClient sin requerir inyección externa
    public DummyJsonClient() {
        this.restClient = RestClient.builder()
            .baseUrl("https://dummyjson.com")
            .build();
    }

    /**
     * Llama a GET https://dummyjson.com/products
     * y mapea la respuesta a nuestro objeto DummyJsonResponse.
     */
    public DummyJsonResponse obtenerTodos() {
        return restClient.get()
            .uri("/products")
            .retrieve()
            .body(DummyJsonResponse.class);
    }

    /**
     * Llama a GET https://dummyjson.com/products/{id}
     * y mapea el producto obtenido a nuestro objeto ProductoExterno.
     */
    public ProductoExterno obtenerPorId(Long id) {
        return restClient.get()
            .uri("/products/{id}", id)
            .retrieve()
            .body(ProductoExterno.class);
    }
}
