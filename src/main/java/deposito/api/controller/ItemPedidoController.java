package deposito.api.controller;

import deposito.api.dto.pedido.DadosAtualizacaoItemPedido;
import deposito.api.dto.pedido.DadosCadastroItemPedido;
import deposito.api.dto.pedido.DadosDetalhamentoItemPedido;
import deposito.api.infra.exception.ValidacaoException;
import deposito.api.model.ItemPedido;
import deposito.api.model.TipoUsuario;
import deposito.api.model.Usuario;
import deposito.api.repository.ItemPedidoRepository;
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
@RequestMapping("/pedidos")
public class ItemPedidoController {

    @Autowired
    private ItemPedidoRepository itemPedidoRepository;

    @Autowired
    private UsuarioRepository usuarioRepository;

    @Autowired
    private ProdutoRepository produtoRepository;

    @Autowired
    private TokenService tokenService;

    @PostMapping
    @Transactional
    public ResponseEntity<DadosDetalhamentoItemPedido> cadastrar(
            @RequestBody @Valid DadosCadastroItemPedido dadosCadastroItemPedido,
            UriComponentsBuilder uriComponentsBuilder,
            HttpServletRequest request) {
        var tokenJWT = tokenService.getToken(request);
        var subject = tokenService.getSubject(tokenJWT);
        var usuario = (Usuario) usuarioRepository.findByEmail(subject);
        var produto = produtoRepository.getReferenceById(dadosCadastroItemPedido.idProduto());
        ItemPedido itemPedido = new ItemPedido(dadosCadastroItemPedido, usuario, produto);
        itemPedidoRepository.save(itemPedido);
        var uri = uriComponentsBuilder.path("pedidos/{id}").buildAndExpand(itemPedido.getId()).toUri();
        return ResponseEntity.created(uri).body(new DadosDetalhamentoItemPedido(itemPedido));
    }

    @GetMapping
    public ResponseEntity<List<DadosDetalhamentoItemPedido>> listar(Pageable pageable) {
        List<DadosDetalhamentoItemPedido> dadosDetalhamentoItemPedidoList = itemPedidoRepository.findAll(pageable)
                .stream().map(DadosDetalhamentoItemPedido::new).toList();
        return ResponseEntity.ok(dadosDetalhamentoItemPedidoList);
    }

    @PutMapping
    @Transactional
    public ResponseEntity<DadosDetalhamentoItemPedido> editar(
            @RequestBody @Valid DadosAtualizacaoItemPedido dadosAtualizacaoItemPedido
    ) {
        ItemPedido itemPedido = itemPedidoRepository.getReferenceById(dadosAtualizacaoItemPedido.id());
        itemPedido.atualizar(dadosAtualizacaoItemPedido);
        return ResponseEntity.ok(new DadosDetalhamentoItemPedido(itemPedido));
    }

    @DeleteMapping("/{id}")
    @Transactional
    public ResponseEntity<?> excluir(@PathVariable Long id) {
        ItemPedido itemPedido = itemPedidoRepository.getReferenceById(id);
        itemPedidoRepository.delete(itemPedido);
        return ResponseEntity.noContent().build();
    }

    @GetMapping("/{id}")
    public ResponseEntity<DadosDetalhamentoItemPedido> detalhar(@PathVariable Long id, HttpServletRequest request) {
        var tokenJWT = tokenService.getToken(request);
        var subject = tokenService.getSubject(tokenJWT);
        var usuario = (Usuario) usuarioRepository.findByEmail(subject);
        ItemPedido itemPedido = itemPedidoRepository.getReferenceById(id);
        if (usuario.equals(itemPedido.getUsuario()) || usuario.getTipoUsuario() == TipoUsuario.ADMIN) {
            return ResponseEntity.ok(new DadosDetalhamentoItemPedido(itemPedido));
        }
        throw new ValidacaoException("Visualização autorizada somente para usuário proprietário ou usuário ADMIN!");
    }
}
