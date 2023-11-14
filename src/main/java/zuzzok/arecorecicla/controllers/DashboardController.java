package zuzzok.arecorecicla.controllers;

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
import org.springframework.web.bind.annotation.GetMapping;

import zuzzok.arecorecicla.configs.UserAuthenticated;
import zuzzok.arecorecicla.data.models.Punto;
import zuzzok.arecorecicla.data.models.Usuario;
import zuzzok.arecorecicla.data.repositories.PuntoRepository;
import zuzzok.arecorecicla.data.repositories.UsuarioRepository;
import zuzzok.arecorecicla.data.services.UsuarioService;

@Controller
public class DashboardController {

        @Autowired
        UsuarioService userService;

        @Autowired
        UsuarioRepository userRepository;

        @Autowired
        PuntoRepository pointRepository;

        @GetMapping("/dashboard")
        @Secured({ "ROLE_ADMIN", "ROLE_OPERARIO", "ROLE_USER" })
        public String dashboard(Model model, @AuthenticationPrincipal UserAuthenticated authenticated,
                        @PageableDefault(page = 0, size = 5) Pageable pageable) {

                Usuario user = userRepository.findById(authenticated.getId()).orElse(null);
                model.addAttribute("balance", user.getBalance());
                model.addAttribute("email", user.getEmail());

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
        @Secured({ "ROLE_ADMIN", "ROLE_OPERARIO", "ROLE_USER" })
        public String benefits(Model model, @AuthenticationPrincipal UserAuthenticated authenticated,
                        @PageableDefault(page = 0, size = 5) Pageable pageable) {

                Usuario user = userRepository.findById(authenticated.getId()).orElse(null);
                model.addAttribute("balance", user.getBalance());
                model.addAttribute("email", user.getEmail());

                return "pages/dashboard/benefits";
        }

}