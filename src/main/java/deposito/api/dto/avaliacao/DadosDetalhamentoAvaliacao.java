package deposito.api.dto.avaliacao;

import deposito.api.model.Avaliacao;

import java.time.LocalDateTime;

public record DadosDetalhamentoAvaliacao(
        Long id,
        Double nota,
        String feedback,
        LocalDateTime dataEmissaoAvaliacao,
        Long idProduto,
        Long idUsuario
) {
    public DadosDetalhamentoAvaliacao(Avaliacao avaliacao) {
        this(
                avaliacao.getId(),
                avaliacao.getNota(),
                avaliacao.getFeedback(),
                avaliacao.getDataEmissaoAvaliacao(),
                avaliacao.getProduto().getId(),
                avaliacao.getUsuario().getId()
        );
    }
}
