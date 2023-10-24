package zuzzok.arecorecicla.data.models;

import java.util.Date;

import jakarta.persistence.CascadeType;
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

  @Column(name = "nombre", nullable = false)
  private String nombre;

  @Column(name = "kilogramos", nullable = false)
  private float kilogramos;

  @Column(name = "creado", nullable = false)
  @Temporal(TemporalType.DATE)
  private Date creado;

  @ManyToOne
  @JoinColumn(name = "usuario_id", nullable = false)
  private Usuario usuario;

  @OneToOne(cascade = CascadeType.ALL)
  @JoinColumn(name = "ecopunto_id", referencedColumnName = "id")
  private Ecopunto ecopunto;

}
