package zuzzok.arecorecicla.data.models;

import java.util.Set;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@Entity
@Table(name = "genero")
public class Genero extends BaseEntity {

  @Column(name = "nombre", nullable = false)
  private String nombre;

  @OneToMany(mappedBy = "genero")
  private Set<Usuario> usuario;

}
