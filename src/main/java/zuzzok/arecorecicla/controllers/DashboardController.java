package zuzzok.arecorecicla.controllers;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.security.access.annotation.Secured;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.validation.Valid;
import zuzzok.arecorecicla.configs.UserAuthenticated;
import zuzzok.arecorecicla.data.models.Beneficio;
import zuzzok.arecorecicla.data.models.Cupon;
import zuzzok.arecorecicla.data.models.Punto;
import zuzzok.arecorecicla.data.models.Reciclable;
import zuzzok.arecorecicla.data.models.Usuario;
import zuzzok.arecorecicla.data.repositories.BeneficioRepository;
import zuzzok.arecorecicla.data.repositories.CuponRepository;
import zuzzok.arecorecicla.data.repositories.EcopuntoRepository;
import zuzzok.arecorecicla.data.repositories.PuntoRepository;
import zuzzok.arecorecicla.data.repositories.ReciclableRepository;
import zuzzok.arecorecicla.data.repositories.UsuarioRepository;
import zuzzok.arecorecicla.data.services.UsuarioService;
import zuzzok.arecorecicla.data.validators.AddRecyclableForm;

@Controller
public class DashboardController {

        @Autowired
        UsuarioService userService;

        @Autowired
        UsuarioRepository userRepository;

        @Autowired
        PuntoRepository pointRepository;

        @Autowired
        ReciclableRepository recyclableRepository;

        @Autowired
        BeneficioRepository benefitRepository;

        @Autowired
        CuponRepository couponRepository;

        @Autowired
        EcopuntoRepository ecopointRepository;

        @GetMapping("/dashboard")
        @Secured({ "ROLE_ADMIN", "ROLE_OPERARIO", "ROLE_USUARIO" })
        public String dashboard(Model model, @AuthenticationPrincipal UserAuthenticated authenticated,
                        @PageableDefault(page = 0, size = 5) Pageable pageable) {

                // Obtener los datos de los puntos correspondientes al mes actual y al pasado.
                float currentMonthUserPoints = userService.getCurrentMonthUserPoints(authenticated.getId());
                float lastMonthUserPoints = userService.getLastMonthUserPoints(authenticated.getId());

                // Calcular el porcentaje de incremento entre ambos meses.
                float pointsIncrementPercent = lastMonthUserPoints == 0 ? 0
                                : ((currentMonthUserPoints - lastMonthUserPoints) / lastMonthUserPoints) * 100;

                // Agregar los resultados como atributos para ser mostrados en el dashboard.
                model.addAttribute("currentMonthUserPoints", currentMonthUserPoints);
                model.addAttribute("pointsIncrementPercent", pointsIncrementPercent);

                // Obtener los datos de los reciclables correspondientes al mes actual y al
                // pasado.
                float currentMonthUserRecyclables = userService.getCurrentMonthUserRecyclables(authenticated.getId());
                float lastMonthUserRecyclables = userService.getLastMonthUserPoints(authenticated.getId());

                // Calcular el porcentaje de incremento entre ambos meses.
                float recyclablesIncrementPercent = lastMonthUserRecyclables == 0 ? 0
                                : ((currentMonthUserRecyclables - lastMonthUserRecyclables) / lastMonthUserRecyclables)
                                                * 100;

                // Agregar los resultados como atributos para ser mostrados en el dashboard.
                model.addAttribute("currentMonthUserRecyclables", currentMonthUserRecyclables);
                model.addAttribute("recyclablesIncrementPercent", recyclablesIncrementPercent);

                // Obtener los datos de los beneficios correspondientes al mes actual y al
                // pasado. Calcular el porcentaje de incremente entre ambos meses y agregar los
                // resultados obtenidos a sus correspondientes atributos.
                float currentMonthUserCoupons = userService.getCurrentMonthUserCoupons(authenticated.getId());
                float lastMonthUserCoupons = userService.getLastMonthUserCoupons(authenticated.getId());
                float couponsIncrementPercent = lastMonthUserCoupons == 0 ? 0
                                : ((currentMonthUserCoupons - lastMonthUserCoupons) / lastMonthUserCoupons) * 100;
                model.addAttribute("currentMonthUserCoupons", currentMonthUserCoupons);
                model.addAttribute("couponsIncrementPercent", couponsIncrementPercent);

                // Obtener los datos de los reciclables anuales en formato de arreglo
                // y agregar el resutado a un atributo para mostrarlo en el dashboard.
                float[] currentYearRecyclablesAsArray = userService
                                .getCurrentYearUserRecyclablesArray(authenticated.getId());
                model.addAttribute("currentYearRecyclablesAsArray", currentYearRecyclablesAsArray);

                float currentYearRecyclables = userService.getCurrentYearUserRecyclables(authenticated.getId());
                float lastYearRecyclables = userService.getLastYearUserRecyclables(authenticated.getId());

                float yearRecyclablesIncrementPercent = lastYearRecyclables == 0 ? 0
                                : ((currentYearRecyclables - lastYearRecyclables) / lastYearRecyclables) * 100;

                model.addAttribute("yearRecyclablesIncrementPercent", yearRecyclablesIncrementPercent);

                // Paginacion del Historial
                Page<Punto> pageOfPoints = userService.getUserPointsPage(authenticated.getId(),
                                PageRequest.of(pageable.getPageNumber(), pageable.getPageSize()));

                model.addAttribute("pointsPage", pageOfPoints);

                var totalPages = pageOfPoints.getTotalPages();
                var currentPage = pageOfPoints.getNumber();

                var start = Math.max(1, currentPage);
                var end = Math.min(currentPage + 3, totalPages);

                if (totalPages > 0) {
                        List<Integer> pageNumbers = new ArrayList<>();
                        for (int i = start; i < end; i++) {
                                pageNumbers.add(i);
                        }

                        model.addAttribute("pageNumbers", pageNumbers);
                }

                return "pages/dashboard/index";
        }

        @GetMapping("/dashboard/benefits")
        @Secured({ "ROLE_ADMIN", "ROLE_OPERARIO", "ROLE_USUARIO" })
        public String getBenefis(Model model, @AuthenticationPrincipal UserAuthenticated authenticated,
                        AddRecyclableForm addRecyclableForm, @PageableDefault(page = 0, size = 10) Pageable pageable) {

                Page<Beneficio> pageOfBenefits = benefitRepository.findByExpiraBeforeOrExpiraNullOrderByCreadoDesc(
                                LocalDate.now(),
                                PageRequest.of(pageable.getPageNumber(), pageable.getPageSize()));

                model.addAttribute("benefitsPage", pageOfBenefits);

                var totalPages = pageOfBenefits.getTotalPages();
                var currentPage = pageOfBenefits.getNumber();

                var start = Math.max(1, currentPage);
                var end = Math.min(currentPage + 3, totalPages);

                if (totalPages > 0) {
                        List<Integer> pageNumbers = new ArrayList<>();
                        for (int i = start; i < end; i++) {
                                pageNumbers.add(i);
                        }

                        model.addAttribute("pageNumbers", pageNumbers);
                }

                return "pages/dashboard/benefits";
        }

        @GetMapping("/dashboard/benefits/trade/{id}")
        @Secured({ "ROLE_ADMIN", "ROLE_OPERARIO", "ROLE_USUARIO" })
        public String tradeBenefit(@PathVariable("id") Long id,
                        @AuthenticationPrincipal UserAuthenticated authenticated,
                        RedirectAttributes redirectAttributes) {

                Usuario user = userRepository.findById(authenticated.getId()).orElse(null);
                Beneficio benefit = benefitRepository.findById(id).orElse(null);

                if (user.getBalance() >= benefit.getCosto()) {
                        user.setBalance(user.getBalance() - benefit.getCosto());

                        Punto point = new Punto();
                        point.setUsuario(user);
                        point.setBeneficio(benefit);
                        point.setCantidad(benefit.getCosto() * -1);
                        point.setDescripcion("Intercambiaste Puntos: " + benefit.getNombre());

                        Cupon coupon = new Cupon();
                        coupon.setUsuario(user);
                        coupon.setBeneficio(benefit);

                        benefitRepository.save(benefit);
                        pointRepository.save(point);
                        couponRepository.save(coupon);
                        userRepository.save(user);

                        redirectAttributes.addFlashAttribute("success",
                                        "El intercambio de puntos ha sido exitoso, puedes encontrar el cupón correspondiente en la sección de cupones.");

                        return "redirect:/dashboard/benefits";
                } else {
                        redirectAttributes.addFlashAttribute("error",
                                        "Hubo un error al intercambiar tus puntos, tus puntos actuales no son suficientes para canjear el beneficio elegido.");
                        return "redirect:/dashboard/benefits";
                }

        }

        @GetMapping("/dashboard/scan")
        @Secured({ "ROLE_ADMIN", "ROLE_OPERARIO" })
        public String getScan(Model model, @AuthenticationPrincipal UserAuthenticated authenticated,
                        AddRecyclableForm addRecyclableForm) {

                model.addAttribute("ecopuntos", ecopointRepository.findAll());

                return "pages/dashboard/scan";
        }

        @PostMapping("/dashboard/scan")
        @Secured({ "ROLE_ADMIN", "ROLE_OPERARIO" })
        public String postScan(@Valid AddRecyclableForm addRecyclableForm, BindingResult result, Model model,
                        RedirectAttributes redirectAttributes,
                        @AuthenticationPrincipal UserAuthenticated authenticated) {

                model.addAttribute("ecopuntos", ecopointRepository.findAll());
                Usuario user = userRepository.findByEmail(addRecyclableForm.getEmail());

                if (user == null) {
                        result.rejectValue("email", "error.usuario.email",
                                        "Intenta con otro email, este no existe en nuestro sistema.");
                        return "pages/dashboard/scan";
                }

                if (user.getId() == authenticated.getId()) {
                        redirectAttributes.addFlashAttribute("error",
                                        "No puedes cargar reciclables a tu propia cuenta.");
                        return "redirect:/dashboard/scan";
                }

                if (result.hasErrors()) {
                        return "pages/dashboard/scan";
                }

                float cantidad = (float) (addRecyclableForm.getKilogramos() * 1.61803398874988);
                user.setBalance(user.getBalance() + cantidad);

                Reciclable reciclable = new Reciclable();
                reciclable.setUsuario(user);
                reciclable.setDescripcion("Dejaste " + addRecyclableForm.getKilogramos() + "Kg de Reciclables");
                reciclable.setKilogramos(addRecyclableForm.getKilogramos());
                reciclable.setEcopunto(addRecyclableForm.getEcopunto());

                Punto punto = new Punto();
                punto.setReciclable(reciclable);
                punto.setUsuario(user);
                punto.setDescripcion("Ganaste Puntos al Depositar " + addRecyclableForm.getKilogramos()
                                + " Kg de Residuos en el " + addRecyclableForm.getEcopunto().getNombre());
                punto.setCantidad(cantidad);

                recyclableRepository.save(reciclable);
                pointRepository.save(punto);
                userRepository.save(user);

                redirectAttributes.addFlashAttribute("success",
                                "Gracias por contribuir al cuidado de nuestro planeta. Tu compromiso con el reciclaje marca la diferencia.");

                return "redirect:/dashboard/scan";
        }

        @ModelAttribute("requestURI")
        public String requestURI(final HttpServletRequest request) {
                return request.getRequestURI();
        }

        @ModelAttribute("balance")
        public float balance(@AuthenticationPrincipal UserAuthenticated authenticated) {
                Usuario user = userRepository.findById(authenticated.getId()).orElse(null);
                return user.getBalance();
        }

        @ModelAttribute("email")
        public String email(@AuthenticationPrincipal UserAuthenticated authenticated) {
                Usuario user = userRepository.findById(authenticated.getId()).orElse(null);
                return user.getEmail();
        }

}