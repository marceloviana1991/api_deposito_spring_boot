package deposito.api.controller;

import deposito.api.dto.tokenJWT.DadosTokenJWT;
import deposito.api.dto.usuario.*;
import deposito.api.model.Usuario;
import deposito.api.repository.UsuarioRepository;
import deposito.api.service.autenticacao.TokenService;
import jakarta.transaction.Transactional;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.util.UriComponentsBuilder;

import java.util.List;

@RestController
@RequestMapping("/usuarios")
public class UsuarioController {

    @Autowired
    private UsuarioRepository usuarioRepository;

    @Autowired
    private AuthenticationManager authenticationManager;

    @Autowired
    private TokenService tokenService;

    @PostMapping
    @Transactional
    public ResponseEntity<DadosDetalhamentoUsuario> cadastrar(
            @RequestBody @Valid DadosCadastroUsuario dadosCadastroUsuario, UriComponentsBuilder uriComponentsBuilder) {
        Usuario usuario = new Usuario(dadosCadastroUsuario);
        usuarioRepository.save(usuario);
        var uri = uriComponentsBuilder.path("usuarios/{id}").buildAndExpand(usuario.getId()).toUri();
        return ResponseEntity.created(uri).body(new DadosDetalhamentoUsuario(usuario));
    }

    @PostMapping("/login")
    public ResponseEntity<?> efetuarLogin(@RequestBody @Valid DadosAutenticacaoUsuario dadosAutenticacaoUsuario) {
        var authenticationToken = new UsernamePasswordAuthenticationToken(
                dadosAutenticacaoUsuario.email(), dadosAutenticacaoUsuario.senha());
        var authentication = authenticationManager.authenticate(authenticationToken);
        var tokenJWT = tokenService.gerarToken((Usuario) authentication.getPrincipal());
        return ResponseEntity.ok(new DadosTokenJWT(tokenJWT));
    }

    @GetMapping
    public ResponseEntity<List<DadosDetalhamentoUsuario>> listar(Pageable pageable) {
        List<DadosDetalhamentoUsuario> dadosListagemUsuariosList = usuarioRepository
                .findAll(pageable).stream().map(DadosDetalhamentoUsuario::new).toList();
        return ResponseEntity.ok(dadosListagemUsuariosList);
    }

    @PutMapping
    @Transactional
    public ResponseEntity<DadosDetalhamentoUsuario> editar(
            @RequestBody @Valid DadosAtualizacaoUsuario dadosAtualizacaoUsuario) {
        Usuario usuario = usuarioRepository.getReferenceById(dadosAtualizacaoUsuario.id());
        usuario.atualizar(dadosAtualizacaoUsuario);
        return ResponseEntity.ok(new DadosDetalhamentoUsuario(usuario));
    }

    @DeleteMapping("/{id}")
    @Transactional
    public ResponseEntity<?> excluir(@PathVariable Long id) {
        Usuario usuario = usuarioRepository.getReferenceById(id);
        usuarioRepository.delete(usuario);
        return ResponseEntity.noContent().build();
    }

    @GetMapping("/{id}")
    public ResponseEntity<DadosDetalhamentoUsuario> detalhar (@PathVariable Long id) {
        Usuario usuario = usuarioRepository.getReferenceById(id);
        return ResponseEntity.ok(new DadosDetalhamentoUsuario(usuario));
    }

}
