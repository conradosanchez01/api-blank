package ar.edu.unvime.apiblank.favorito;

import jakarta.persistence.*;
import java.time.LocalDateTime;

import ar.edu.unvime.apiblank.lista.ListaEntity;

@Entity
@Table(name = "favoritos")
public class FavoritoEntity {
    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    // Le indicamos el nombre exacto de la columna en la BD
    @Column(name = "producto_id", nullable = false)
    private Long productoId;

    @Column(name = "nota")
    private String nota;

    @Column(name = "fecha_alta", nullable = false)
    private LocalDateTime fechaAlta;

    @ManyToOne
    @JoinColumn(name = "lista_id")
    private ListaEntity lista;

    // Constructor vacío obligatorio para que JPA funcione
    public FavoritoEntity() {}

    // Constructor completo 
    public FavoritoEntity(Long id, Long productoId, String nota, LocalDateTime fechaAlta, ListaEntity lista) {
        this.id = id;
        this.productoId = productoId;
        this.nota = nota;
        this.fechaAlta = fechaAlta;
        this.lista = lista;
    }

    // Getters y Setters
    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }

    public Long getProductoId() { return productoId; }
    public void setProductoId(Long productoId) { this.productoId = productoId; }

    public String getNota() { return nota; }
    public void setNota(String nota) { this.nota = nota; }

    public LocalDateTime getFechaAlta() { return fechaAlta; }
    public void setFechaAlta(LocalDateTime fechaAlta) { this.fechaAlta = fechaAlta; }

    public ListaEntity getLista() { return lista; }
    public void setLista(ListaEntity lista) { this.lista = lista; }
}