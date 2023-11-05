package zuzzok.arecorecicla.data.services;

import java.util.List;
import java.util.Optional;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import zuzzok.arecorecicla.data.models.Rol;
import zuzzok.arecorecicla.data.repositories.RolRepository;

@Service
public class RolService {

  private final RolRepository repository;

  public RolService(RolRepository repository) {
    this.repository = repository;
  }

  public Optional<Rol> get(Long id) {
    return repository.findById(id);
  }

  public Rol get(String nombre) {
    return repository.findByNombre(nombre);
  }

  public Rol update(Rol entity) {
    return repository.save(entity);
  }

  public void delete(Long id) {
    repository.deleteById(id);
  }

  public List<Rol> list() {
    return repository.findAll();
  }

  public Page<Rol> list(Pageable pageable) {
    return repository.findAll(pageable);
  }

  public int count() {
    return (int) repository.count();
  }

}