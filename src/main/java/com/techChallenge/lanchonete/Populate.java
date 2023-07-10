package com.techChallenge.lanchonete;


import com.techChallenge.lanchonete.core.applications.Enum.CategoriaProduto;
import com.techChallenge.lanchonete.core.applications.dtos.in.ClienteDTO;
import com.techChallenge.lanchonete.core.applications.dtos.in.ProdutoDTO;
import com.techChallenge.lanchonete.core.applications.ports.interfaces.ClienteServicePort;
import com.techChallenge.lanchonete.core.applications.ports.interfaces.ProdutoServicePort;
import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

@Component
@AllArgsConstructor(onConstructor = @__(@Autowired))
public class Populate implements CommandLineRunner {

    private final ProdutoServicePort produtoRepository;
    private final ClienteServicePort clienteRepository;

    public void insertProdutos() {
        try {

            ArrayList<ProdutoDTO> produtos = new ArrayList<>();

            var produto = new ProdutoDTO();
            produto.setNomeProduto("Hamburguer Simples");
            produto.setDescricaoProduto("Pão de brioche, carne de 110g e mussarela");
            produto.setCategoria(CategoriaProduto.LANCHE);
            produto.setPreco(Float.valueOf(26));
            produto.setImagen("Hamburguer Simples");
            produtos.add(produto);

            produto.setNomeProduto("X-Bacon");
            produto.setDescricaoProduto("Pão de brioche, carne de 110g, mussarela e bacon");
            produto.setCategoria(CategoriaProduto.LANCHE);
            produto.setPreco(Float.valueOf(33));
            produto.setImagen("X-Bacon");
            produtos.add(produto);

            produto.setNomeProduto("Fritas");
            produto.setDescricaoProduto("Batata palito frita");
            produto.setCategoria(CategoriaProduto.ACOMPANHAMENTO);
            produto.setPreco(Float.valueOf(10));
            produto.setImagen("Fritas");
            produtos.add(produto);

            produto.setNomeProduto("Refrigerante");
            produto.setDescricaoProduto("Coca-cola, pepsi, fanta laranja, guaraná anterctica");
            produto.setCategoria(CategoriaProduto.BEBIDA);
            produto.setPreco(Float.valueOf(5));
            produto.setImagen("Bebida");
            produtos.add(produto);

            produto.setNomeProduto("Pudim");
            produto.setDescricaoProduto("Pudim de leite condensado");
            produto.setCategoria(CategoriaProduto.SOBREMESA);
            produto.setPreco(Float.valueOf(10));
            produto.setImagen("Pudim");
            produtos.add(produto);

            List<ProdutoDTO> allProdutos = produtoRepository.findAll();
            if (allProdutos.isEmpty() || allProdutos.size() == 0) {
                for (ProdutoDTO prod : produtos) {
                    produtoRepository.create(prod);
                }
            }

        } catch (Exception err) {
            System.out.println("erro ao criar produtos");
            System.out.println(err);
        }
    }

    public void insertClientes() {
        try {

            ArrayList<ClienteDTO> clientes = new ArrayList<>();

            var cliente = new ClienteDTO();
            cliente.setNomeCliente("José da Silva");
            cliente.setCpf("456.123.368-96");
            cliente.setEmail("joseSilva@gmail.com");
            clientes.add(cliente);

            cliente.setNomeCliente("Fernando de Sousa");
            cliente.setCpf("656.123.789-71");
            cliente.setEmail("fernadoSousa@gmail.com");
            clientes.add(cliente);

            List<ClienteDTO> allClientes = clienteRepository.findAll();
            if (allClientes.isEmpty() || allClientes.size() == 0) {
                for (ClienteDTO clie : clientes) {
                    clienteRepository.create(clie);
                }
            }

        } catch (Exception err) {
            System.out.println("erro ao criar produtos");
            System.out.println(err);
        }
    }

    @Override
    public void run(String... args) throws Exception {
        insertProdutos();
        insertClientes();
    }
}
