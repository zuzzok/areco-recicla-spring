package zuzzok.arecorecicla.data.validators;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import lombok.Getter;
import lombok.Setter;
import zuzzok.arecorecicla.data.models.Ecopunto;

@Getter
@Setter
public class AddRecyclableForm {

  @NotEmpty(message = "Por favor ingresa tu correo.")
  @Email(message = "El correo no es valido.")
  private String email;

  @NotNull(message = "Por favor ingresa el peso en kilogramos de los recyclables.")
  @Positive(message = "El valor tiene que ser positivo y distinto de cero.")
  private float kilogramos;

  @NotNull(message = "Por favor selecciona el ecopunto.")
  private Ecopunto ecopunto;

}
