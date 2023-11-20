package zuzzok.arecorecicla.data.repositories;

import java.time.LocalDate;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import zuzzok.arecorecicla.data.models.Beneficio;

public interface BeneficioRepository extends JpaRepository<Beneficio, Long> {

  Page<Beneficio> findByExpiraBeforeOrExpiraNullOrderByCreadoDesc(LocalDate currenDate, Pageable pageable);

}
