package zuzzok.arecorecicla.data.models;

import java.time.LocalDate;
import java.util.List;

import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.SourceType;

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

  @Column(name = "creado", nullable = false, updatable = false)
  @Temporal(TemporalType.DATE)
  @CreationTimestamp(source = SourceType.DB)
  private LocalDate creado;

  @Column(name = "expira", nullable = true)
  @Temporal(TemporalType.DATE)
  private LocalDate expira;

  @OneToMany(mappedBy = "beneficio")
  private List<Cupon> cupones;

  @OneToMany(mappedBy = "beneficio")
  private List<Punto> puntos;

}
