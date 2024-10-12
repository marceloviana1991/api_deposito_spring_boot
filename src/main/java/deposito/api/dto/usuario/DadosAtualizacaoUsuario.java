package deposito.api.dto.usuario;

import deposito.api.model.TipoUsuario;
import jakarta.validation.constraints.NotNull;

public record DadosAtualizacaoUsuario(
        @NotNull
        Long id,
        String nome,
        String email,
        String senha,
        String cpf,
        String telefone,
        TipoUsuario tipoUsuario
) {
}
