package deposito.api.dto.usuario;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;

public record DadosCadastroUsuario(
        @NotBlank
        String nome,
        @NotBlank
        @Pattern(regexp = "\\d{11}")
        String cpf,
        @NotBlank
        @Email
        String email,
        @NotBlank
        String senha,
        @NotBlank
        @Pattern(regexp = "\\d{11}")
        String telefone
) {
}
