package deposito.api.model;

import deposito.api.dto.pedido.DadosAtualizacaoItemPedido;
import deposito.api.dto.pedido.DadosCadastroItemPedido;
import jakarta.persistence.*;

import jakarta.validation.Valid;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDateTime;

@Table(name = "itens_pedidos")
@Entity(name = "ItemPedido")
@Getter
@Setter
@NoArgsConstructor
@EqualsAndHashCode(of = "id")
public class ItemPedido {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    int quantidade;
    LocalDateTime dataEmissaoPedido;
    String localEntregaCep;
    String localEntregaCidade;
    String localEntregaBairro;
    String localEntregaRua;
    String localEntregaNumero;
    @ManyToOne
    @JoinColumn(name = "id_usuario")
    Usuario usuario;
    @ManyToOne
    @JoinColumn(name = "id_produto")
    Produto produto;

    public ItemPedido(DadosCadastroItemPedido dadosCadastroItemPedido, Usuario usuario, Produto produto) {
        this.dataEmissaoPedido = LocalDateTime.now();
        this.quantidade = dadosCadastroItemPedido.quantidade();
        this.localEntregaCep = dadosCadastroItemPedido.localEntregaCep();
        this.localEntregaCidade = dadosCadastroItemPedido.localEntregaCidade();
        this.localEntregaBairro = dadosCadastroItemPedido.localEntregaBairro();
        this.localEntregaRua = dadosCadastroItemPedido.localEntregaRua();
        this.localEntregaNumero = dadosCadastroItemPedido.localEntregaNumero();
        this.produto = produto;
        this.usuario = usuario;
    }

    public void atualizar(DadosAtualizacaoItemPedido dadosAtualizacaoItemPedido) {
        if (dadosAtualizacaoItemPedido.quantidade() != null) {
            this.quantidade = dadosAtualizacaoItemPedido.quantidade();
        }
        if (dadosAtualizacaoItemPedido.localEntregaCep() != null) {
            this.localEntregaCep = dadosAtualizacaoItemPedido.localEntregaCep();
        }
        if (dadosAtualizacaoItemPedido.localEntregaCidade() != null) {
            this.localEntregaCidade = dadosAtualizacaoItemPedido.localEntregaCidade();
        }
        if (dadosAtualizacaoItemPedido.localEntregaBairro() != null) {
            this.localEntregaBairro = dadosAtualizacaoItemPedido.localEntregaBairro();
        }
        if (dadosAtualizacaoItemPedido.localEntregaRua() != null) {
            this.localEntregaRua = dadosAtualizacaoItemPedido.localEntregaRua();
        }
        if (dadosAtualizacaoItemPedido.localEntregaNumero() != null) {
            this.localEntregaNumero = dadosAtualizacaoItemPedido.localEntregaNumero();
        }
    }
}
