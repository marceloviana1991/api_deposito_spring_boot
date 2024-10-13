package deposito.api.dto.avaliacao;

import jakarta.validation.constraints.DecimalMax;
import jakarta.validation.constraints.DecimalMin;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

public record DadosCadastroAvaliacao(
        @NotNull
        @DecimalMax("5.0") @DecimalMin("0.0")
        Double nota,
        @NotBlank
        String feedback,
        @NotNull
        Long idProduto
) {
}
