package ar.edu.unvime.apiblank.error;

import java.util.LinkedHashMap;
import java.util.Map;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;

/**
 * Manejador centralizado de errores para toda la aplicación.
 * Cumple con el punto 7 de la consigna.
 */
@RestControllerAdvice
public class GlobalExceptionHandler {

    // Caso 1: 404 Not Found (recurso inexistente)
    @ExceptionHandler(RecursoNoEncontradoException.class)
    @ResponseStatus(HttpStatus.NOT_FOUND)
    public ApiError manejarNoEncontrado(RecursoNoEncontradoException ex) {
        return new ApiError(404, ex.getMessage(), Map.of());
    }

    // Caso 2: 400 Bad Request (validación fallida de Bean Validation)
    @ExceptionHandler(MethodArgumentNotValidException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public ApiError manejarValidacion(MethodArgumentNotValidException ex) {
        Map<String, String> errores = new LinkedHashMap<>();
        ex.getBindingResult().getFieldErrors().forEach(error -> 
            errores.put(error.getField(), error.getDefaultMessage())
        );
        return new ApiError(400, "Uno o más campos no son válidos", errores);
    }

    // Caso 3: 502 Bad Gateway (falla externa de DummyJSON)
    @ExceptionHandler(ServicioExternoException.class)
    @ResponseStatus(HttpStatus.BAD_GATEWAY)
    public ApiError manejarServicioExterno(ServicioExternoException ex) {
        return new ApiError(502, ex.getMessage(), Map.of());
    }

    // Caso 4: 409 Conflict (ej. intentar borrar lista con elementos)
    @ExceptionHandler(ConflictoEstadoException.class)
    @ResponseStatus(HttpStatus.CONFLICT)
    public ApiError manejarConflictoEstado(ConflictoEstadoException ex) {
        return new ApiError(409, ex.getMessage(), Map.of());
    }
}
