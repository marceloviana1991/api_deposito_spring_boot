package deposito.api.controller;

import deposito.api.dto.produto.DadosAtualizacaoProduto;
import deposito.api.dto.produto.DadosCadastroProduto;
import deposito.api.dto.produto.DadosDetalhamentoProduto;
import deposito.api.model.Produto;
import deposito.api.model.Usuario;
import deposito.api.repository.ProdutoRepository;
import deposito.api.repository.UsuarioRepository;
import deposito.api.service.autenticacao.TokenService;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.transaction.Transactional;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.util.UriComponentsBuilder;

import java.util.List;

@RestController
@RequestMapping("/produtos")
public class ProdutoController {

    @Autowired
    private ProdutoRepository produtoRepository;

    @Autowired
    private UsuarioRepository usuarioRepository;

    @Autowired
    private TokenService tokenService;

    @PostMapping
    @Transactional
    public ResponseEntity<DadosDetalhamentoProduto> cadastrar(
            @RequestBody @Valid DadosCadastroProduto dadosCadastroProduto, UriComponentsBuilder uriComponentsBuilder,
            HttpServletRequest request) {
        var tokenJWT = tokenService.getToken(request);
        var subject = tokenService.getSubject(tokenJWT);
        var usuario = (Usuario) usuarioRepository.findByEmail(subject);
        Produto produto = new Produto(dadosCadastroProduto, usuario);
        produtoRepository.save(produto);
        var uri = uriComponentsBuilder.path("produtos/{id}").buildAndExpand(produto.getId()).toUri();
        return ResponseEntity.created(uri).body(new DadosDetalhamentoProduto(produto));
    }

    @GetMapping
    public ResponseEntity<List<DadosDetalhamentoProduto>> listar(Pageable pageable) {
        List<DadosDetalhamentoProduto> dadosDetalhamentoProdutoList = produtoRepository.findAll(pageable)
                .stream().map(DadosDetalhamentoProduto::new).toList();
        return ResponseEntity.ok(dadosDetalhamentoProdutoList);
    }

    @PutMapping
    @Transactional
    public ResponseEntity<DadosDetalhamentoProduto> editar(
            @RequestBody @Valid DadosAtualizacaoProduto dadosAtualizacaoProduto, HttpServletRequest request
    ) {
        var tokenJWT = tokenService.getToken(request);
        var subject = tokenService.getSubject(tokenJWT);
        var usuario = (Usuario) usuarioRepository.findByEmail(subject);
        Produto produto = produtoRepository.getReferenceById(dadosAtualizacaoProduto.id());
        produto.atualizar(dadosAtualizacaoProduto, usuario);
        return ResponseEntity.ok(new DadosDetalhamentoProduto(produto));
    }

    @DeleteMapping("/{id}")
    @Transactional
    public ResponseEntity<?> excluir(@PathVariable Long id) {
        Produto produto = produtoRepository.getReferenceById(id);
        produtoRepository.delete(produto);
        return ResponseEntity.noContent().build();
    }
}
