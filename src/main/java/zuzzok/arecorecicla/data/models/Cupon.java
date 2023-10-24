
package zuzzok.arecorecicla.data.models;

import java.util.Date;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
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
@Table(name = "cupon")
public class Cupon extends BaseEntity {

  @Column(name = "valido", nullable = false)
  private boolean valido;

  @Column(name = "creado", nullable = false)
  @Temporal(TemporalType.DATE)
  private Date creado;

  @ManyToOne
  @JoinColumn(name = "usuario_id", nullable = false)
  private Usuario usuario;

  @ManyToOne
  @JoinColumn(name = "beneficio_id", nullable = false)
  private Beneficio beneficio;

}
