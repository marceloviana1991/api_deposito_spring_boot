package deposito.api.controller;

import deposito.api.dto.avaliacao.DadosCadastroAvaliacao;
import deposito.api.dto.avaliacao.DadosDetalhamentoAvaliacao;
import deposito.api.model.Avaliacao;
import deposito.api.model.Usuario;
import deposito.api.repository.AvaliacaoRepository;
import deposito.api.repository.ProdutoRepository;
import deposito.api.repository.UsuarioRepository;
import deposito.api.service.autenticacao.TokenService;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.transaction.Transactional;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.util.UriComponentsBuilder;

import java.util.List;

@SecurityRequirement(name = "bearer-key")
@RestController
@RequestMapping("/avaliacoes")
public class AvaliacaoController {

    @Autowired
    private AvaliacaoRepository avaliacaoRepository;

    @Autowired
    private UsuarioRepository usuarioRepository;

    @Autowired
    private ProdutoRepository produtoRepository;

    @Autowired
    private TokenService tokenService;

    @PostMapping
    @Transactional
    public ResponseEntity<DadosDetalhamentoAvaliacao> cadastrar(
            @RequestBody @Valid DadosCadastroAvaliacao dadosCadastroAvaliacao,
            UriComponentsBuilder uriComponentsBuilder,
            HttpServletRequest request) {
        var tokenJWT = tokenService.getToken(request);
        var subject = tokenService.getSubject(tokenJWT);
        var usuario = (Usuario) usuarioRepository.findByEmail(subject);
        var produto = produtoRepository.getReferenceById(dadosCadastroAvaliacao.idProduto());
        Avaliacao avaliacao = new Avaliacao(dadosCadastroAvaliacao, usuario, produto);
        avaliacaoRepository.save(avaliacao);
        var uri = uriComponentsBuilder.path("avaliacoes/{id}").buildAndExpand(avaliacao.getId()).toUri();
        return ResponseEntity.created(uri).body(new DadosDetalhamentoAvaliacao(avaliacao));
    }

    @GetMapping
    public ResponseEntity<List<DadosDetalhamentoAvaliacao>> listar(Pageable pageable) {
        List<DadosDetalhamentoAvaliacao> dadosDetalhamentoAvaliacaoList = avaliacaoRepository.findAll(pageable).stream()
                .map(DadosDetalhamentoAvaliacao::new).toList();
        return ResponseEntity.ok(dadosDetalhamentoAvaliacaoList);
    }

    @DeleteMapping("/{id}")
    @Transactional
    public ResponseEntity<?> excluir(@PathVariable Long id) {
        Avaliacao avaliacao = avaliacaoRepository.getReferenceById(id);
        avaliacaoRepository.delete(avaliacao);
        return ResponseEntity.noContent().build();
    }
}
