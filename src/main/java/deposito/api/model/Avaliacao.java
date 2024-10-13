package deposito.api.model;

import deposito.api.dto.avaliacao.DadosCadastroAvaliacao;
import jakarta.persistence.*;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDateTime;

@Table(name = "avaliacoes")
@Entity(name = "Avaliacao")
@Getter
@Setter
@NoArgsConstructor
@EqualsAndHashCode(of = "id")
public class Avaliacao {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    Long id;
    Double nota;
    String feedback;
    LocalDateTime dataEmissaoAvaliacao;
    @ManyToOne
    @JoinColumn(name = "id_usuario")
    Usuario usuario;
    @ManyToOne
    @JoinColumn(name = "id_produto")
    Produto produto;

    public Avaliacao(DadosCadastroAvaliacao dadosCadastroAvaliacao, Usuario usuario, Produto produto) {
        this.nota = dadosCadastroAvaliacao.nota();
        this.feedback = dadosCadastroAvaliacao.feedback();
        this.dataEmissaoAvaliacao = LocalDateTime.now();
        this.produto = produto;
        this.usuario = usuario;
    }
}
