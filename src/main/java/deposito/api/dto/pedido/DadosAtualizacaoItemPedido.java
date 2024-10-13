package deposito.api.dto.pedido;

import jakarta.validation.constraints.NotNull;

public record DadosAtualizacaoItemPedido(
        @NotNull
        Long id,
        Integer quantidade,
        String localEntregaCep,
        String localEntregaCidade,
        String localEntregaBairro,
        String localEntregaRua,
        String localEntregaNumero
) {
}
