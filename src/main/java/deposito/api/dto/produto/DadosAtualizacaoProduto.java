package deposito.api.dto.produto;

import jakarta.validation.constraints.NotNull;

public record DadosAtualizacaoProduto(
        @NotNull
        Long id,
        String nome,
        Double preco,
        String descricao
) {
}
