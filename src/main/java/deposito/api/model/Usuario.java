package deposito.api.model;

import deposito.api.dto.usuario.DadosAtualizacaoUsuario;
import deposito.api.dto.usuario.DadosCadastroUsuario;
import deposito.api.dto.usuario.DadosDetalhamentoUsuario;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

import java.util.Collection;
import java.util.List;

@Table(name = "usuarios")
@Entity(name = "Usuario")
@Getter
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode(of = "id")
public class Usuario implements UserDetails {
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
        this.senha = new BCryptPasswordEncoder().encode(dadosCadastroUsuario.senha());
        this.telefone = dadosCadastroUsuario.telefone();
    }

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        if(this.tipoUsuario == TipoUsuario.ADMIN) {
            return List.of(new SimpleGrantedAuthority("ROLE_USER"), new SimpleGrantedAuthority("ROLE_ADMIN"));
        } else if (this.tipoUsuario == TipoUsuario.USER) {
            return List.of(new SimpleGrantedAuthority("ROLE_USER"));
        }
        return List.of(new SimpleGrantedAuthority("ROLE_BLOQUEADO"));
    }

    @Override
    public String getPassword() {
        return senha;
    }

    @Override
    public String getUsername() {
        return email;
    }

    public void atualizar(DadosAtualizacaoUsuario dadosAtualizacaoUsuario) {
        if (dadosAtualizacaoUsuario.nome() != null) {
            this.nome = dadosAtualizacaoUsuario.nome();
        }
        if (dadosAtualizacaoUsuario.cpf() != null) {
            this.cpf = dadosAtualizacaoUsuario.cpf();
        }
        if (dadosAtualizacaoUsuario.email() != null) {
            this.email = dadosAtualizacaoUsuario.email();
        }
        if (dadosAtualizacaoUsuario.senha() != null) {
            this.senha = new BCryptPasswordEncoder().encode(dadosAtualizacaoUsuario.senha());
        }
        if (dadosAtualizacaoUsuario.telefone() != null) {
            this.telefone = dadosAtualizacaoUsuario.telefone();
        }
        if (dadosAtualizacaoUsuario.tipoUsuario() != null) {
            this.tipoUsuario = dadosAtualizacaoUsuario.tipoUsuario();
        }
    }
}
