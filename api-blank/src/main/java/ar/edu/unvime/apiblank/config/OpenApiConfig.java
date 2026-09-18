package ar.edu.unvime.apiblank.config;

import io.swagger.v3.oas.annotations.OpenAPIDefinition;
import io.swagger.v3.oas.annotations.info.Contact;
import io.swagger.v3.oas.annotations.info.Info;
import org.springframework.context.annotation.Configuration;

/**
 * Configuración global de OpenAPI / Swagger UI.
 * Cumple con el punto 8 de la consigna.
 */
@Configuration
@OpenAPIDefinition(
    info = @Info(
        title = "API Web II - TP1",
        version = "1.0.0",
        description = "Servicio backend con catálogo externo de productos y CRUD de favoritos en memoria.",
        contact = @Contact(name = "Web II - UNVIME", email = "web2@unvime.edu.ar")
    )
)
public class OpenApiConfig {}
