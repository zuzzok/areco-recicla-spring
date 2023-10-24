package zuzzok.arecorecicla.data.models;

import java.util.Date;
import java.util.Set;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;
import jakarta.persistence.Temporal;
import jakarta.persistence.TemporalType;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@Entity
@Table(name = "beneficio")
public class Beneficio extends BaseEntity {

  @Column(name = "nombre", nullable = false)
  private String nombre;

  @Column(name = "descripcion", nullable = false)
  private String descripcion;

  @Column(name = "costo", nullable = false)
  private Integer costo;

  @Column(name = "creado", nullable = false)
  @Temporal(TemporalType.DATE)
  private Date creado;

  @Column(name = "expira", nullable = true)
  @Temporal(TemporalType.DATE)
  private Date expira;

  @OneToMany(mappedBy = "beneficio")
  private Set<Cupon> cupones;

}
