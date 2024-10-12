package deposito.api.model;

import deposito.api.dto.produto.DadosCadastroProduto;
import jakarta.persistence.*;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDate;

@Table(name = "produtos")
@Entity(name = "Produto")
@Getter
@Setter
@NoArgsConstructor
@EqualsAndHashCode(of = "id")
public class Produto {
    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String nome;
    private double preco;
    private String descricao;
    private LocalDate data;
    @ManyToOne
    @JoinColumn(name = "id_usuario")
    private Usuario usuario;

    public Produto(DadosCadastroProduto dadosCadastroProduto, Usuario usuario) {
        this.nome = dadosCadastroProduto.nome();
        this.preco = dadosCadastroProduto.preco();
        this.descricao = dadosCadastroProduto.descricao();
        this.data = LocalDate.now();
        this.usuario = usuario;
    }
}
