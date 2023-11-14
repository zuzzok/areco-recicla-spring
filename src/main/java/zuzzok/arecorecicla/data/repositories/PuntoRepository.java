package zuzzok.arecorecicla.data.repositories;

import java.time.LocalDate;
import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import zuzzok.arecorecicla.data.models.Punto;
import zuzzok.arecorecicla.data.models.Usuario;

public interface PuntoRepository extends JpaRepository<Punto, Long> {

  List<Punto> findByCreadoBetweenAndUsuarioOrderByCreadoDesc(LocalDate startDate, LocalDate endDate,
      Usuario usuario);

  Page<Punto> findByUsuarioOrderByCreadoDesc(Pageable pageable, Usuario usuario);

}
