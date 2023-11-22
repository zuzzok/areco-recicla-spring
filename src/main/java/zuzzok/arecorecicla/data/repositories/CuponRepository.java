package zuzzok.arecorecicla.data.repositories;

import java.time.LocalDate;
import java.util.List;
import java.util.UUID;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import zuzzok.arecorecicla.data.models.Cupon;
import zuzzok.arecorecicla.data.models.Usuario;

public interface CuponRepository extends JpaRepository<Cupon, Long> {

  List<Cupon> findByCreadoBetweenAndUsuarioOrderByCreadoDesc(LocalDate startDate, LocalDate endDate,
      Usuario usuario);

  Cupon findByCodigo(UUID codigo);

  Page<Cupon> findByUsuarioAndValidoTrueOrderByCreadoDesc(Pageable pageable, Usuario usuario);

}
