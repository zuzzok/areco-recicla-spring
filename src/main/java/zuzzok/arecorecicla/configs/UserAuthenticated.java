package zuzzok.arecorecicla.configs;

import java.util.Collection;
import java.util.List;
import java.util.stream.Collectors;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import lombok.Getter;
import zuzzok.arecorecicla.data.models.Usuario;

public class UserAuthenticated implements UserDetails {

  @Getter
  private String nombre;
  @Getter
  private String apellido;
  private String email;
  private String clave;
  private List<GrantedAuthority> authorities;

  public UserAuthenticated(Usuario usuario) {
    this.nombre = usuario.getNombre();
    this.apellido = usuario.getApellido();
    this.email = usuario.getEmail();
    this.clave = usuario.getClave();
    this.authorities = usuario.getRoles().stream().map(role -> new SimpleGrantedAuthority("ROLE_" + role.getNombre()))
        .collect(Collectors.toList());
  }

  @Override
  public Collection<? extends GrantedAuthority> getAuthorities() {
    return this.authorities;
  }

  @Override
  public String getPassword() {
    return this.clave;
  }

  @Override
  public String getUsername() {
    return this.email;
  }

  @Override
  public boolean isAccountNonExpired() {
    return true;
  }

  @Override
  public boolean isAccountNonLocked() {
    return true;
  }

  @Override
  public boolean isCredentialsNonExpired() {
    return true;
  }

  @Override
  public boolean isEnabled() {
    return true;
  }

}
