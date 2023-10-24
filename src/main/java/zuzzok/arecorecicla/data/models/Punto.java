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

@Entity
@Getter
@Setter
@NoArgsConstructor
@Table(name = "punto")
public class Punto extends BaseEntity {

    @Column(name = "creado", nullable = false)
    @Temporal(TemporalType.DATE)
    private Date creado;
    @Column(name = "cantidad", nullable = false)
    private Integer cantidad;
    @Column(name = "descripcion", nullable = true)
    private String descripcion;

    @ManyToOne
    @JoinColumn(name = "usuario_id", nullable = false)
    private Usuario usuario;

}
