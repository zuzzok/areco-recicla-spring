package zuzzok.arecorecicla.data.models;

import java.util.Set;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.ManyToMany;
import jakarta.persistence.Table;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@Entity
@Table(name = "rol")
public class Rol extends BaseEntity {

  @Column(name = "nombre", nullable = false)
  private String nombre;

  @ManyToMany(mappedBy = "roles")
  private Set<Usuario> usuarios;

}
