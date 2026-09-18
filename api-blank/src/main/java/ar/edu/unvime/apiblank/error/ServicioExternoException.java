package ar.edu.unvime.apiblank.error;

/**
 * Excepción lanzada cuando falla la comunicación con una API externa (timeout, caída de red, 5xx).
 */
public class ServicioExternoException extends RuntimeException {
    public ServicioExternoException(String mensaje, Throwable causa) {
        super(mensaje, causa);
    }
}
