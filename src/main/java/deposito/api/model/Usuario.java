package deposito.api.model;

import deposito.api.dto.usuario.DadosCadastroUsuario;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Table(name = "usuarios")
@Entity(name = "Usuario")
@Getter
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode(of = "id")
public class Usuario {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String nome;
    private String cpf;
    private String email;
    private String senha;
    private String telefone;
    @Enumerated(EnumType.STRING)
    private TipoUsuario tipoUsuario = TipoUsuario.USER;

    public Usuario(DadosCadastroUsuario dadosCadastroUsuario) {
        this.nome = dadosCadastroUsuario.nome();
        this.cpf = dadosCadastroUsuario.cpf();
        this.email = dadosCadastroUsuario.email();
        this.senha = dadosCadastroUsuario.senha();
        this.telefone = dadosCadastroUsuario.telefone();
    }

}
