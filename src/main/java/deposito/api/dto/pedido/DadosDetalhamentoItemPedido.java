package deposito.api.dto.pedido;

import deposito.api.model.ItemPedido;

public record DadosDetalhamentoItemPedido(
        Long id,
        Integer quantidade,
        String localEntregaCep,
        String localEntregaCidade,
        String localEntregaBairro,
        String localEntregaRua,
        String localEntregaNumero,
        Long idUsuario,
        Long idProduto
) {
    public DadosDetalhamentoItemPedido(ItemPedido itemPedido) {
        this(
                itemPedido.getId(),
                itemPedido.getQuantidade(),
                itemPedido.getLocalEntregaCep(),
                itemPedido.getLocalEntregaCidade(),
                itemPedido.getLocalEntregaBairro(),
                itemPedido.getLocalEntregaRua(),
                itemPedido.getLocalEntregaNumero(),
                itemPedido.getUsuario().getId(),
                itemPedido.getProduto().getId()
        );
    }
}
