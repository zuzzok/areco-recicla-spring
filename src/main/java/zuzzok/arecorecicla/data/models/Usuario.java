
package zuzzok.arecorecicla.data.models;

import java.util.List;
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
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.Size;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@Entity
@Table(name = "usuario")
public class Usuario extends BaseEntity {

  @Size(min = 2, max = 200, message = "La longitud del nombre es mayor o menor al permitido.")
  @NotEmpty(message = "Por favor ingresa tu nombre.")
  @Column(name = "nombre", nullable = false)
  private String nombre;

  @Size(min = 2, max = 200, message = "La longitud del apellido es mayor o menor al permitido.")
  @NotEmpty(message = "Por favor ingresa tu apellido.")
  @Column(name = "apellido", nullable = false)
  private String apellido;

  @NotEmpty(message = "Por favor ingresa tu correo.")
  @Email(message = "El correo no es valido.")
  @Column(name = "email", nullable = false, unique = true)
  private String email;

  @Size(min = 4, max = 200, message = "La longitud de la contraseña es mayor o menor al permitido.")
  @NotEmpty(message = "Por favor ingresa una contraseña.")
  @Column(name = "clave", nullable = false)
  private String clave;

  @Column(name = "balance", columnDefinition = "Decimal(15,2) default '0'")
  private float balance;

  /*
   * Muchos a Muchos entre usuario y rol
   */
  @ManyToMany(fetch = FetchType.EAGER)
  @JoinTable(name = "rol_de_usuario", joinColumns = {
      @JoinColumn(name = "usuario_id")
  }, inverseJoinColumns = {
      @JoinColumn(name = "rol_id", nullable = false)
  })
  private Set<Rol> roles;

  /* relacion muchos a uno entre usuario y genero */
  @ManyToOne
  @JoinColumn(name = "genero_id")
  private Genero genero;

  /*
   * Uno a muchos entre usuario y reciclable.
   */
  @OneToMany(mappedBy = "usuario")
  private List<Reciclable> reciclables;

  @OneToMany(mappedBy = "usuario")
  private List<Punto> puntos;

  @OneToMany(mappedBy = "usuario")
  private List<Cupon> cupones;

}
