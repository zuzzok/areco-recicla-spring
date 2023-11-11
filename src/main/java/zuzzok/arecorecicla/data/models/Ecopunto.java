
package zuzzok.arecorecicla.data.models;

import java.util.List;

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
@Table(name = "ecopunto")
public class Ecopunto extends BaseEntity {

  @Column(name = "nombre", nullable = false)
  private String nombre;

  @Column(name = "descripcion", nullable = false)
  private String descripcion;

  @OneToMany(mappedBy = "ecopunto")
  private List<Reciclable> reciclable;

}
