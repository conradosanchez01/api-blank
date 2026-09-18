# Web II · TP1: Spring Boot, API REST y Arquitectura en Capas

Trabajo Práctico N° 1 de la materia **Web II (UNVIME)**.  
Servicio backend desarrollado en **Java 25** y **Spring Boot 4.1.x**, con arquitectura en capas (*Controller → Service → Repository/Client*), consumo de API pública externa, CRUD en memoria de favoritos, validación con Bean Validation, manejo uniforme de errores y documentación interactiva con OpenAPI / Swagger.

---

## Requisitos previos

* **Java JDK 25** (OpenJDK LTS) instalado y configurado en el `PATH`.
* Conexión a internet (para la descarga inicial de dependencias vía Maven Wrapper y para el consumo del catálogo de DummyJSON).

---

## Cómo levantar el proyecto

1. Abrir una terminal en la carpeta del proyecto (`api-blank`).
2. Ejecutar la aplicación usando el Maven Wrapper:

* **En Windows (PowerShell / CMD):**
  ```powershell
  .\mvnw.cmd spring-boot:run
  ```
* **En macOS / Linux:**
  ```bash
  ./mvnw spring-boot:run
  ```

3. El servicio estará listo cuando en la consola se muestre:
   ```text
   Tomcat started on port 8080 (http) with context path '/'
   Started ApiBlankApplication in ... seconds
   ```

---

## Documentación interactiva (Swagger UI)

Una vez iniciado el servicio, se puede acceder a la interfaz gráfica interactiva de Swagger UI para explorar y probar todos los endpoints:

* **Swagger UI:** [http://localhost:8080/swagger-ui.html](http://localhost:8080/swagger-ui.html)
* **Especificación OpenAPI (JSON):** [http://localhost:8080/v3/api-docs](http://localhost:8080/v3/api-docs)

---

## Endpoints de la API

### 1. Verificación de Salud
| Método | Endpoint | Descripción | Código esperado |
|---|---|---|---|
| `GET` | `/health` | Chequeo de estado del servicio base | `200 OK` |

### 2. Catálogo de Productos (Consumo externo a DummyJSON)
| Método | Endpoint | Descripción | Códigos posibles |
|---|---|---|---|
| `GET` | `/api/productos` | Lista los productos mapeados a DTO propio en español | `200 OK`, `502 Bad Gateway` |
| `GET` | `/api/productos/{id}` | Obtiene un producto por su ID | `200 OK`, `404 Not Found`, `502 Bad Gateway` |

### 3. Favoritos (CRUD en memoria)
| Método | Endpoint | Descripción | Códigos posibles |
|---|---|---|---|
| `GET` | `/api/favoritos` | Lista todos los favoritos en memoria | `200 OK` |
| `GET` | `/api/favoritos/{id}` | Busca un favorito por su ID | `200 OK`, `404 Not Found` |
| `POST` | `/api/favoritos` | Crea un favorito con validación de entrada | `201 Created` (header Location), `400 Bad Request` |
| `PUT` | `/api/favoritos/{id}` | Actualiza un favorito preservando su fecha de creación | `200 OK`, `400 Bad Request`, `404 Not Found` |
| `DELETE` | `/api/favoritos/{id}` | Elimina un favorito por ID | `204 No Content`, `404 Not Found` |

---

## Ejemplos de prueba (Casos de éxito y de error)

### 1. Crear un favorito (Éxito - 201 Created)
* **Petición:** `POST /api/favoritos`
* **Body (JSON):**
  ```json
  {
    "productoId": 1,
    "nota": "Comprar para el cumpleaños"
  }
  ```
* **Respuesta esperada (201 Created):**
  ```json
  {
    "id": 1,
    "productoId": 1,
    "nota": "Comprar para el cumpleaños",
    "fechaAgregado": "2026-09-17T23:30:00.123456"
  }
  ```

### 2. Crear favorito con datos inválidos (Error de validación - 400 Bad Request)
* **Petición:** `POST /api/favoritos`
* **Body (JSON):**
  ```json
  {
    "nota": ""
  }
  ```
* **Respuesta esperada (400 Bad Request):**
  ```json
  {
    "status": 400,
    "mensaje": "Uno o más campos no son válidos",
    "campos": {
      "productoId": "productoId es obligatorio",
      "nota": "nota no puede estar vacía"
    }
  }
  ```

### 3. Buscar recurso inexistente (Error 404 Not Found)
* **Petición:** `GET /api/favoritos/999`
* **Respuesta esperada (404 Not Found):**
  ```json
  {
    "status": 404,
    "mensaje": "No existe el favorito con id 999",
    "campos": {}
  }
  ```

---

## Arquitectura del Proyecto

El código está organizado por funcionalidad (*package-by-feature*) y sigue una arquitectura en capas:

```text
src/main/java/ar/edu/unvime/apiblank/
├── ApiBlankApplication.java
├── config/
│   └── OpenApiConfig.java              # Configuración de OpenAPI / Swagger
├── error/
│   ├── ApiError.java                   # Formato uniforme de error
│   ├── GlobalExceptionHandler.java     # @RestControllerAdvice centralizado
│   ├── RecursoNoEncontradoException.java
│   └── ServicioExternoException.java
├── producto/
│   ├── DummyJsonClient.java            # Cliente HTTP con RestClient hacia DummyJSON
│   ├── DummyJsonResponse.java          # Deserialización externa
│   ├── ProductoExterno.java            # Deserialización externa
│   ├── ProductoResponse.java           # DTO propio de salida
│   ├── ProductoService.java            # Lógica y mapeo a DTO propio
│   └── ProductoController.java         # Endpoints /api/productos
└── favorito/
    ├── Favorito.java                   # Entidad de dominio (record)
    ├── FavoritoRepository.java         # Interfaz de persistencia
    ├── FavoritoRepositoryMemoria.java  # Implementación en memoria (ConcurrentHashMap)
    ├── CrearFavoritoRequest.java       # DTO de entrada con Bean Validation
    ├── FavoritoResponse.java           # DTO de salida
    ├── FavoritoService.java            # Lógica de negocio y mapeo
    └── FavoritoController.java         # Endpoints CRUD /api/favoritos
```
