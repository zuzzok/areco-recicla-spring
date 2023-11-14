package zuzzok.arecorecicla.data.repositories;

import java.time.LocalDate;
import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import zuzzok.arecorecicla.data.models.Cupon;
import zuzzok.arecorecicla.data.models.Usuario;

public interface CuponRepository extends JpaRepository<Cupon, Long> {

  List<Cupon> findByCreadoBetweenAndUsuarioOrderByCreadoDesc(LocalDate startDate, LocalDate endDate,
      Usuario usuario);

}
