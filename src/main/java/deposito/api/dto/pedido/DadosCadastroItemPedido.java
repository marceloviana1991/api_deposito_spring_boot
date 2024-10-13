package deposito.api.dto.pedido;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;

public record DadosCadastroItemPedido(
        @NotNull
        Long idProduto,
        @NotNull
        Integer quantidade,
        @NotBlank
        @Pattern(regexp = "\\d{8}", message = "CEP deve conter 8 dígitos")
        String localEntregaCep,
        @NotBlank
        String localEntregaCidade,
        @NotBlank
        String localEntregaBairro,
        @NotBlank
        String localEntregaRua,
        @NotBlank
        String localEntregaNumero
) {
}
