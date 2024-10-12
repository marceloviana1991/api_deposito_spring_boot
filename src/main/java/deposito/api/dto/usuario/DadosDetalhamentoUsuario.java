package deposito.api.dto.usuario;

import deposito.api.model.TipoUsuario;
import deposito.api.model.Usuario;

public record DadosDetalhamentoUsuario(
        Long id,
        String nome,
        String email,
        String cpf,
        String telefone,
        TipoUsuario tipoUsuario
) {

    public DadosDetalhamentoUsuario(Usuario usuario) {
        this(
                usuario.getId(),
                usuario.getNome(),
                usuario.getEmail(),
                usuario.getCpf(),
                usuario.getEmail(),
                usuario.getTipoUsuario()
        );
    }
}
