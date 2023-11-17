package zuzzok.arecorecicla.data.models;

import java.time.LocalDate;

import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.SourceType;

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

@Entity
@Getter
@Setter
@NoArgsConstructor
@Table(name = "punto")
public class Punto extends BaseEntity {

    @Column(name = "creado", nullable = false, updatable = false)
    @Temporal(TemporalType.DATE)
    @CreationTimestamp(source = SourceType.DB)
    private LocalDate creado;

    @Column(name = "cantidad", nullable = false, columnDefinition = "Decimal(15,2)")
    private float cantidad;

    @Column(name = "descripcion", nullable = true)
    private String descripcion;

    @ManyToOne
    @JoinColumn(name = "usuario_id", nullable = false)
    private Usuario usuario;

    @OneToOne
    @JoinColumn(name = "reciclable_id", nullable = true)
    private Reciclable reciclable;

    @ManyToOne
    @JoinColumn(name = "beneficio_id", nullable = true)
    private Beneficio beneficio;

}
