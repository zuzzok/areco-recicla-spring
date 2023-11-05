package zuzzok.arecorecicla.data.services;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import zuzzok.arecorecicla.configs.UserAuthenticated;
import zuzzok.arecorecicla.data.models.Usuario;
import zuzzok.arecorecicla.data.repositories.UsuarioRepository;

@Service
public class UsuarioService implements UserDetailsService {

  @Autowired
  private BCryptPasswordEncoder encoder;

  @Autowired
  private UsuarioRepository repository;

  public Optional<Usuario> get(Long id) {
    return repository.findById(id);
  }

  public Usuario update(Usuario user) {
    return repository.save(user);
  }

  public void delete(Long id) {
    repository.deleteById(id);
  }

  public Page<Usuario> list(Pageable pageable) {
    return repository.findAll(pageable);
  }

  public int count() {
    return (int) repository.count();
  }

  public Usuario save(Usuario user) {
    if (user == null) {
      return null;
    }

    user.setClave(encoder.encode(user.getClave()));

    return repository.save(user);
  }

  @Override
  public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {

    Usuario user = repository.findByEmail(username);

    if (user == null) {
      throw new UsernameNotFoundException("Credenciales de inicio invalidas.");
    } else {
      return new UserAuthenticated(user);
    }
  }

  // private Collection<? extends GrantedAuthority>
  // mapRolesToAuthorities(Collection<Rol> roles) {
  // return roles.stream().map(
  // role -> new SimpleGrantedAuthority("ROLE_" +
  // role.getNombre())).collect(Collectors.toList());
  // }

}
