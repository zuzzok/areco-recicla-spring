package zuzzok.arecorecicla.data.repositories;

import java.time.LocalDate;
import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import zuzzok.arecorecicla.data.models.Reciclable;
import zuzzok.arecorecicla.data.models.Usuario;

public interface ReciclableRepository extends JpaRepository<Reciclable, Long> {

  List<Reciclable> findByCreadoBetweenAndUsuarioOrderByCreadoDesc(LocalDate startDate, LocalDate endDate,
      Usuario usuario);

}
