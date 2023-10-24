
package zuzzok.arecorecicla.data.models;

import java.util.Set;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.JoinTable;
import jakarta.persistence.ManyToMany;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@Entity
@Table(name = "usuario")
public class Usuario extends BaseEntity {

  @Column(name = "nombre", nullable = false)
  private String nombre;

  @Column(name = "apellido", nullable = false)
  private String apellido;

  @Column(name = "email", nullable = false, unique = true)
  private String email;

  @Column(name = "clave", nullable = false, unique = true)
  private String clave;

  /*
   * Muchos a Muchos entre usuario y rol
   */
  @ManyToMany(fetch = FetchType.EAGER)
  @JoinTable(name = "rol_de_usuario", joinColumns = {
      @JoinColumn(name = "usuario_id")
  }, inverseJoinColumns = {
      @JoinColumn(name = "rol_id")
  })
  private Set<Rol> roles;

  /* relacion muchos a uno entre usuario y genero */
  @ManyToOne
  @JoinColumn(name = "genero_id", nullable = false)
  private Genero genero;

  /*
   * Uno a muchos entre usuario y reciclable.
   */
  @OneToMany(mappedBy = "usuario")
  private Set<Reciclable> reciclables;

  @OneToMany(mappedBy = "usuario")
  private Set<Punto> puntos;

  @OneToMany(mappedBy = "usuario")
  private Set<Cupon> cupones;

}
