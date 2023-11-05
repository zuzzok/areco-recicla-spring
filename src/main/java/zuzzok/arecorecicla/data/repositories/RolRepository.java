package zuzzok.arecorecicla.data.repositories;

import org.springframework.data.jpa.repository.JpaRepository;

import zuzzok.arecorecicla.data.models.Rol;

public interface RolRepository extends JpaRepository<Rol, Long> {
  Rol findByNombre(String nombre);
}
