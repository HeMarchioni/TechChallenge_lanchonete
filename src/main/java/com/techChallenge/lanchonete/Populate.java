package com.techChallenge.lanchonete;


import com.techChallenge.lanchonete.core.applications.Enum.CategoriaProduto;
import com.techChallenge.lanchonete.core.applications.dtos.in.ClienteDTO;
import com.techChallenge.lanchonete.core.applications.dtos.in.PedidoDTO;
import com.techChallenge.lanchonete.core.applications.dtos.in.ProdutoDTO;
import com.techChallenge.lanchonete.core.applications.dtos.out.PedidoOutDTO;
import com.techChallenge.lanchonete.core.applications.ports.interfaces.ClienteServicePort;
import com.techChallenge.lanchonete.core.applications.ports.interfaces.PedidoServicePort;
import com.techChallenge.lanchonete.core.applications.ports.interfaces.ProdutoServicePort;
import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;


//Classe usada para teste para Popular dados se o banco estiver vazio
@Component
@AllArgsConstructor(onConstructor = @__(@Autowired))
public class Populate implements CommandLineRunner {

    private final ProdutoServicePort produtoRepository;
    private final ClienteServicePort clienteRepository;
    private final PedidoServicePort pedidoServicePort;

    public boolean insertProdutos() {
        try {

            ArrayList<ProdutoDTO> produtos = new ArrayList<>();

            var produto = new ProdutoDTO();
            produto.setNomeProduto("Hamburguer Simples");
            produto.setDescricaoProduto("Pão de brioche, carne de 110g e mussarela");
            produto.setCategoria(CategoriaProduto.LANCHE);
            produto.setPreco(Float.valueOf(26));
            produto.setImagen("Hamburguer Simples");
            produtos.add(produto);

            produto = new ProdutoDTO();
            produto.setNomeProduto("X-Bacon");
            produto.setDescricaoProduto("Pão de brioche, carne de 110g, mussarela e bacon");
            produto.setCategoria(CategoriaProduto.LANCHE);
            produto.setPreco(Float.valueOf(33));
            produto.setImagen("X-Bacon");
            produtos.add(produto);

            produto = new ProdutoDTO();
            produto.setNomeProduto("Fritas");
            produto.setDescricaoProduto("Batata palito frita");
            produto.setCategoria(CategoriaProduto.ACOMPANHAMENTO);
            produto.setPreco(Float.valueOf(10));
            produto.setImagen("Fritas");
            produtos.add(produto);

            produto = new ProdutoDTO();
            produto.setNomeProduto("Refrigerante");
            produto.setDescricaoProduto("Coca-cola, pepsi, fanta laranja, guaraná anterctica");
            produto.setCategoria(CategoriaProduto.BEBIDA);
            produto.setPreco(Float.valueOf(5));
            produto.setImagen("Bebida");
            produtos.add(produto);

            produto = new ProdutoDTO();
            produto.setNomeProduto("Pudim");
            produto.setDescricaoProduto("Pudim de leite condensado");
            produto.setCategoria(CategoriaProduto.SOBREMESA);
            produto.setPreco(Float.valueOf(10));
            produto.setImagen("Pudim");
            produtos.add(produto);

            List<ProdutoDTO> allProdutos = produtoRepository.findAll();
            if (allProdutos.isEmpty()) {
                for (ProdutoDTO prod : produtos) {
                    produtoRepository.create(prod);
                }
                return true;
            }

        } catch (Exception err) {
            System.out.println("erro ao criar produtos");
            System.out.println(err);
        }
        return false;
    }

    public boolean insertClientes() {
        try {

            ArrayList<ClienteDTO> clientes = new ArrayList<>();

            var cliente = new ClienteDTO();
            cliente.setNomeCliente("José da Silva");
            cliente.setCpf("456.123.368-96");
            cliente.setEmail("joseSilva@gmail.com");
            clientes.add(cliente);

            cliente = new ClienteDTO();
            cliente.setNomeCliente("Fernando de Sousa");
            cliente.setCpf("656.123.789-71");
            cliente.setEmail("fernadoSousa@gmail.com");
            clientes.add(cliente);

            cliente = new ClienteDTO();
            cliente.setNomeCliente("Bianca Brenda Lara da Mata");
            cliente.setCpf("635.392.007-54");
            cliente.setEmail("bianca_damata@brws.com.br");
            clientes.add(cliente);

            cliente = new ClienteDTO();
            cliente.setNomeCliente("Nelson Bryan Diego Rodrigues");
            cliente.setCpf("189.167.254-10");
            cliente.setEmail("nelson_rodrigues@live.com.pt");
            clientes.add(cliente);

            cliente = new ClienteDTO();
            cliente.setNomeCliente("Carolina Rayssa Allana Pereira");
            cliente.setCpf("478.692.757-00");
            cliente.setEmail("carolina.rayssa.pereira@somma.net.br");
            clientes.add(cliente);

            List<ClienteDTO> allClientes = clienteRepository.findAll();
            if (allClientes.isEmpty()) {
                for (ClienteDTO clie : clientes) {
                    clienteRepository.create(clie);
                }
                return true;
            }

        } catch (Exception err) {
            System.out.println("erro ao criar Clientes");
            System.out.println(err);
        }
        return false;
    }

    public void createPedidos() {
        try {

            ArrayList<PedidoDTO> pedidos = new ArrayList<>();

            var pedido = new PedidoDTO();
            pedido.setCliente(1L);
            pedido.setListaProduto(Arrays.asList(1L, 3L, 4L));
            pedido.setObservacao("Sem Bacon");
            pedidos.add(pedido);

            pedido = new PedidoDTO();
            pedido.setListaProduto(Arrays.asList(2L, 3L));
            pedido.setObservacao("Sem Queijo");
            pedidos.add(pedido);

            pedido = new PedidoDTO();
            pedido.setCliente(2L);
            pedido.setListaProduto(List.of(5L));
            pedidos.add(pedido);

            pedido = new PedidoDTO();
            pedido.setCliente(3L);
            pedido.setListaProduto(Arrays.asList(1L, 1L, 4L));
            pedido.setObservacao("Sem Maionese");
            pedidos.add(pedido);


            List<PedidoOutDTO> allPedidos = pedidoServicePort.findAll();
            if (allPedidos.isEmpty()) {
                for (PedidoDTO pdi : pedidos) {
                    pedidoServicePort.create(pdi);
                }
            }

        } catch (Exception err) {
            System.out.println("erro ao criar Pedidos");
            System.out.println(err);
        }
    }

    @Override
    public void run(String... args) throws Exception {
        if (insertProdutos() && insertClientes()) createPedidos();
    }
}
