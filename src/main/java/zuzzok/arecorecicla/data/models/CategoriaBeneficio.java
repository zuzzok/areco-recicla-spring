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
@Table(name = "categoria_beneficio")
public class CategoriaBeneficio extends BaseEntity {

  @Column(name = "nombre", nullable = false)
  private String nombre;

  @OneToMany(mappedBy = "categoriaBeneficio")
  private List<Beneficio> beneficios;

}
