package deposito.api.dto.produto;

import deposito.api.model.Produto;

import java.time.LocalDate;

public record DadosDetalhamentoProduto(
        Long id,
        String nome,
        double preco,
        String descricao,
        LocalDate data,
        Long idUsuario
) {
    public DadosDetalhamentoProduto(Produto produto) {
        this(
                produto.getId(),
                produto.getNome(),
                produto.getPreco(),
                produto.getDescricao(),
                produto.getData(),
                produto.getUsuario().getId()
        );
    }
}
