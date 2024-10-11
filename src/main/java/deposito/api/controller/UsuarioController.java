package deposito.api.controller;

import deposito.api.dto.usuario.DadosCadastroUsuario;
import deposito.api.model.Usuario;
import deposito.api.repository.UsuarioRepository;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/usuarios")
public class UsuarioController {

    @Autowired
    private UsuarioRepository usuarioRepository;

    @PostMapping
    public void cadastrar(@RequestBody @Valid DadosCadastroUsuario dadosCadastroUsuario) {
        usuarioRepository.save(new Usuario(dadosCadastroUsuario));
    }
}
