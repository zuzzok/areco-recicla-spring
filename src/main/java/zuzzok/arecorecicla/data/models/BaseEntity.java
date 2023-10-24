package zuzzok.arecorecicla.data.models;

import jakarta.persistence.Column;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.MappedSuperclass;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@MappedSuperclass()
abstract class BaseEntity {

  @Id
  @Column(name = "id", nullable = false, insertable = false)
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Long id;

}
