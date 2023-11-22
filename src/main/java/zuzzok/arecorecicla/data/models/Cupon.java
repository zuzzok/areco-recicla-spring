
package zuzzok.arecorecicla.data.models;

import java.time.LocalDate;
import java.util.UUID;

import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.SourceType;
import org.hibernate.annotations.UuidGenerator;
import org.hibernate.annotations.UuidGenerator.Style;

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

  @UuidGenerator(style = Style.RANDOM)
  @Column(name = "codigo")
  private UUID codigo = UUID.randomUUID();

  @Column(name = "valido", nullable = false, columnDefinition = "boolean default true")
  private boolean valido;

  @Column(name = "creado", nullable = false, updatable = false)
  @Temporal(TemporalType.DATE)
  @CreationTimestamp(source = SourceType.DB)
  private LocalDate creado;

  @ManyToOne
  @JoinColumn(name = "usuario_id", nullable = false)
  private Usuario usuario;

  @ManyToOne
  @JoinColumn(name = "beneficio_id", nullable = false)
  private Beneficio beneficio;

}
