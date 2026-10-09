package ar.edu.unvime.apiblank.lista;
//La clase "sucia" para que JPA sepa cómo armar la tabla en SQL.
import jakarta.persistence.*;

@Entity
@Table(name = "listas")
public class ListaEntity {
    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "nombre", nullable = false)
    private String nombre;

    // Constructor vacío para JPA
    public ListaEntity() {}

    // Constructor para nosotros
    public ListaEntity(Long id, String nombre) {
        this.id = id;
        this.nombre = nombre;
    }

    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }

    public String getNombre() { return nombre; }
    public void setNombre(String nombre) { this.nombre = nombre; }
}