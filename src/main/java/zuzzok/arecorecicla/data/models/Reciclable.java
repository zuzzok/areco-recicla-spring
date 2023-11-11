package zuzzok.arecorecicla.data.models;

import java.time.LocalDate;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToOne;
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
@Table(name = "reciclable")
public class Reciclable extends BaseEntity {

  @Column(name = "descripcion", nullable = false)
  private String descripcion;

  @Column(name = "kilogramos", nullable = false)
  private float kilogramos;

  @Column(name = "creado", nullable = false, columnDefinition = "date default now()")
  @Temporal(TemporalType.DATE)
  private LocalDate creado;

  @ManyToOne
  @JoinColumn(name = "usuario_id", nullable = false)
  private Usuario usuario;

  @ManyToOne
  @JoinColumn(name = "ecopunto_id", nullable = false)
  private Ecopunto ecopunto;

  @OneToOne(mappedBy = "reciclable")
  private Punto punto;

}
