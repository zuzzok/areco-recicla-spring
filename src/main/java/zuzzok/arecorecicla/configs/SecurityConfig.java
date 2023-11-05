package zuzzok.arecorecicla.configs;

import static org.springframework.boot.autoconfigure.security.servlet.PathRequest.toH2Console;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.HeadersConfigurer.FrameOptionsConfig;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.util.matcher.AntPathRequestMatcher;

import zuzzok.arecorecicla.data.services.UsuarioService;

@Configuration
@EnableWebSecurity
@EnableMethodSecurity(securedEnabled = true)
public class SecurityConfig {

  @Bean
  public SecurityFilterChain filterChain(HttpSecurity httpSecurity) throws Exception {
    return httpSecurity
        .csrf(conf -> conf
            .ignoringRequestMatchers(toH2Console()).disable())
        .authorizeHttpRequests((authorize) -> {
          authorize.requestMatchers(toH2Console()).permitAll();
          authorize.requestMatchers(
              new AntPathRequestMatcher("/css/**"),
              new AntPathRequestMatcher("/js/**"),
              new AntPathRequestMatcher("/assets/**"),
              new AntPathRequestMatcher("/vendors/**"),
              new AntPathRequestMatcher("/login"),
              new AntPathRequestMatcher("/register")).permitAll();
          authorize.anyRequest().authenticated();
        })
        .headers(headers -> {
          headers.frameOptions(FrameOptionsConfig::disable);
        })
        .formLogin((login) -> {
          login.permitAll();
          login.loginPage("/login");
        })
        .logout((logout) -> {
          logout.permitAll();
        })
        .build();
  }

  @Bean
  public UserDetailsService userDetailsService() {
    return new UsuarioService();
  }

  @Bean
  public BCryptPasswordEncoder bCryptPasswordEncoder() {
    return new BCryptPasswordEncoder();
  }

  @Bean
  public PasswordEncoder passwordEncoder() {
    return new BCryptPasswordEncoder();
  }

  @Bean
  public AuthenticationProvider authenticationProvider() {
    DaoAuthenticationProvider authenticationProvider = new DaoAuthenticationProvider();
    authenticationProvider.setUserDetailsService(userDetailsService());
    authenticationProvider.setPasswordEncoder(passwordEncoder());

    return authenticationProvider;
  }

}
