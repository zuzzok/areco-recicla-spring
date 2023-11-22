package zuzzok.arecorecicla.data.validators;

import jakarta.validation.constraints.NotEmpty;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class ValidateCouponForm {

  @NotEmpty(message = "Por favor escanea el código del cupón.")
  private String codigo;

}
