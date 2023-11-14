package zuzzok.arecorecicla.data.services;

import java.time.LocalDate;
import java.time.temporal.TemporalAdjusters;
import java.util.List;
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
import zuzzok.arecorecicla.data.models.Cupon;
import zuzzok.arecorecicla.data.models.Punto;
import zuzzok.arecorecicla.data.models.Reciclable;
import zuzzok.arecorecicla.data.models.Usuario;
import zuzzok.arecorecicla.data.repositories.CuponRepository;
import zuzzok.arecorecicla.data.repositories.PuntoRepository;
import zuzzok.arecorecicla.data.repositories.ReciclableRepository;
import zuzzok.arecorecicla.data.repositories.UsuarioRepository;

@Service
public class UsuarioService implements UserDetailsService {

  @Autowired
  private BCryptPasswordEncoder encoder;

  @Autowired
  private UsuarioRepository repository;

  @Autowired
  private PuntoRepository pointRepository;

  @Autowired
  private ReciclableRepository reyclableRepository;

  @Autowired
  private CuponRepository couponRepository;

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

  public float getCurrentMonthUserPoints(Long id) {

    Usuario user = repository.getReferenceById(id);

    List<Punto> points = pointRepository.findByCreadoBetweenAndUsuarioOrderByCreadoDesc(
        LocalDate.now().with(TemporalAdjusters.firstDayOfMonth()),
        LocalDate.now().with(TemporalAdjusters.lastDayOfMonth()),
        user);

    return points.stream().map(punto -> punto.getCantidad() >= 0 ? punto.getCantidad() : 0).reduce(
        Float.valueOf(0),
        (sum, cantidad) -> sum + cantidad);
  }

  public float getLastMonthUserPoints(Long id) {

    Usuario user = repository.getReferenceById(id);

    List<Punto> points = pointRepository.findByCreadoBetweenAndUsuarioOrderByCreadoDesc(
        LocalDate.now().minusMonths(1).with(TemporalAdjusters.firstDayOfMonth()),
        LocalDate.now().minusMonths(1).with(TemporalAdjusters.lastDayOfMonth()),
        user);

    return points.stream().map(punto -> punto.getCantidad() >= 0 ? punto.getCantidad() : 0).reduce(
        Float.valueOf(0),
        (sum, cantidad) -> sum + cantidad);
  }

  public float getCurrentMonthUserRecyclables(Long id) {

    Usuario user = repository.getReferenceById(id);

    List<Reciclable> recyclables = reyclableRepository.findByCreadoBetweenAndUsuarioOrderByCreadoDesc(
        LocalDate.now().with(TemporalAdjusters.firstDayOfMonth()),
        LocalDate.now().with(TemporalAdjusters.lastDayOfMonth()),
        user);

    return recyclables.stream().map(recyclable -> recyclable.getKilogramos()).reduce(
        Float.valueOf(0),
        (sum, kilograms) -> sum + kilograms);
  }

  public float getLastMonthUserRecyclables(Long id) {

    Usuario user = repository.getReferenceById(id);

    List<Reciclable> recyclables = reyclableRepository.findByCreadoBetweenAndUsuarioOrderByCreadoDesc(
        LocalDate.now().minusMonths(1).with(TemporalAdjusters.firstDayOfMonth()),
        LocalDate.now().minusMonths(1).with(TemporalAdjusters.lastDayOfMonth()),
        user);

    return recyclables.stream().map(recyclable -> recyclable.getKilogramos()).reduce(
        Float.valueOf(0),
        (sum, kilograms) -> sum + kilograms);
  }

  public float getCurrentMonthUserCoupons(Long id) {

    Usuario user = repository.getReferenceById(id);

    List<Cupon> coupons = couponRepository.findByCreadoBetweenAndUsuarioOrderByCreadoDesc(
        LocalDate.now().with(TemporalAdjusters.firstDayOfMonth()),
        LocalDate.now().with(TemporalAdjusters.lastDayOfMonth()),
        user);

    return coupons.size();
  }

  public float getLastMonthUserCoupons(Long id) {

    Usuario user = repository.getReferenceById(id);

    List<Cupon> coupons = couponRepository.findByCreadoBetweenAndUsuarioOrderByCreadoDesc(
        LocalDate.now().minusMonths(1).with(TemporalAdjusters.firstDayOfMonth()),
        LocalDate.now().minusMonths(1).with(TemporalAdjusters.lastDayOfMonth()),
        user);

    return coupons.size();
  }

  public float[] getCurrentYearUserRecyclablesArray(Long id) {

    Usuario user = repository.getReferenceById(id);
    float[] currentYearRecyclables = new float[12];

    for (int i = 1; i <= 12; i++) {

      List<Reciclable> recyclables = reyclableRepository.findByCreadoBetweenAndUsuarioOrderByCreadoDesc(
          LocalDate.now().withMonth(i).with(TemporalAdjusters.firstDayOfMonth()),
          LocalDate.now().withMonth(i).with(TemporalAdjusters.lastDayOfMonth()),
          user);

      Float monthRecyclable = recyclables.stream().map(recyclable -> recyclable.getKilogramos()).reduce(
          Float.valueOf(0),
          (sum, kilograms) -> sum + kilograms);

      currentYearRecyclables[i - 1] = monthRecyclable;

    }

    return currentYearRecyclables;
  }

  public float getCurrentYearUserRecyclables(Long id) {

    Usuario user = repository.getReferenceById(id);

    List<Reciclable> recyclables = reyclableRepository.findByCreadoBetweenAndUsuarioOrderByCreadoDesc(
        LocalDate.now().with(TemporalAdjusters.firstDayOfYear()),
        LocalDate.now().with(TemporalAdjusters.lastDayOfYear()),
        user);

    float yearRecyclable = recyclables.stream().map(recyclable -> recyclable.getKilogramos()).reduce(
        Float.valueOf(0),
        (sum, kilograms) -> sum + kilograms);

    return yearRecyclable;
  }

  public float getLastYearUserRecyclables(Long id) {

    Usuario user = repository.getReferenceById(id);

    List<Reciclable> recyclables = reyclableRepository.findByCreadoBetweenAndUsuarioOrderByCreadoDesc(
        LocalDate.now().minusYears(1).with(TemporalAdjusters.firstDayOfYear()),
        LocalDate.now().minusYears(1).with(TemporalAdjusters.lastDayOfYear()),
        user);

    float yearRecyclable = recyclables.stream().map(recyclable -> recyclable.getKilogramos()).reduce(
        Float.valueOf(0),
        (sum, kilograms) -> sum + kilograms);

    return yearRecyclable;
  }

  public Page<Punto> getUserPointsPage(Long id, Pageable pageable) {
    Usuario user = repository.getReferenceById(id);
    return pointRepository.findByUsuarioOrderByCreadoDesc(pageable, user);
  }

}
