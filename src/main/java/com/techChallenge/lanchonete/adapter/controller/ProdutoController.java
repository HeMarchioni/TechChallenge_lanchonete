package com.techChallenge.lanchonete.adapter.controller;

import com.techChallenge.lanchonete.core.applications.Enum.CategoriaProduto;
import com.techChallenge.lanchonete.core.applications.dtos.in.ProdutoDTO;
import com.techChallenge.lanchonete.core.applications.ports.interfaces.PedidoServicePort;
import com.techChallenge.lanchonete.core.applications.ports.interfaces.ProdutoServicePort;
import jakarta.validation.Valid;
import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/produto")
@AllArgsConstructor(onConstructor = @__(@Autowired))
public class ProdutoController implements ControllerInterface<ProdutoDTO, ProdutoDTO> {


    private final ProdutoServicePort produtoServicePort;
    private final PedidoServicePort pedidoServicePort;


    @Override
    @GetMapping
    public ResponseEntity<List<ProdutoDTO>> getAll() {
        return ResponseEntity.ok(produtoServicePort.findAll());
    }

    @Override
    @GetMapping("/{id}")
    public ResponseEntity<ProdutoDTO> get(@PathVariable("id")Long id) {
        ProdutoDTO produtoDTO = produtoServicePort.findById(id);

        if (produtoDTO != null) return ResponseEntity.ok(produtoDTO);

        return ResponseEntity.status(HttpStatus.NOT_FOUND).build();

    }

    @GetMapping("categoria/{categoria}")
    public ResponseEntity<List<ProdutoDTO>> get(@PathVariable("categoria")String categoria) {

        CategoriaProduto categoriaProduto;

        try {
            categoriaProduto = CategoriaProduto.valueOf(categoria.toUpperCase());
        } catch (IllegalArgumentException ex) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).build();
        }

        List<ProdutoDTO> produtos = produtoServicePort.buscarPorCategoria(categoriaProduto);

        if (produtos.isEmpty()) return ResponseEntity.noContent().build();

        return ResponseEntity.ok(produtos);

    }


    @Override
    @PostMapping("/create")
    public ResponseEntity<ProdutoDTO> post(@Valid @RequestBody ProdutoDTO produtoDTO) {
        produtoServicePort.create(produtoDTO);
        return ResponseEntity.ok(produtoDTO);
    }


    @Override
    @PutMapping
    public ResponseEntity<?>  put(@Valid @RequestBody ProdutoDTO produtoDTO) {

        try {

            if (produtoServicePort.update(produtoDTO)) {
                return ResponseEntity.ok(produtoDTO);
            }
            return ResponseEntity.status(HttpStatus.NOT_FOUND).build();  //-> id nao encontrado (reposta 404)
        }
        catch (Exception e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Erro na Solicitação!!  "+ e.getMessage()); // -> erro em geral, se mandar json errado (400)
        }

    }

    @Override
    @DeleteMapping("/{id}")
    public ResponseEntity<?> delete(@PathVariable("id")Long id) {
        try {

            if (pedidoServicePort.pedidoEmAbertoComProduto(id))
                throw new IllegalStateException("Não se pode apagar um Produto que esteja em uso em um pedido em Aberto não Finalzado");


            if (produtoServicePort.delete(id)) {
                return ResponseEntity.ok().build();
            }
        }
        catch (IllegalStateException e) {
            return ResponseEntity.status(HttpStatus.CONFLICT).body("Erro conflito de regra!!  "+ e.getMessage()); // -> erro de conflito, (409)
        }
        catch (Exception e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Erro na Solicitação!!  "+ e.getMessage()); // -> erro em geral, se mandar json errado (400)
        }
        return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
    }

}
