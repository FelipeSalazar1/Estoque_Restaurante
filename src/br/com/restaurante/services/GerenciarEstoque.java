package br.com.restaurante.services;

import br.com.restaurante.DAO.EstoqueDAOImpl;
import br.com.restaurante.database.DatabaseConnection;
import br.com.restaurante.entities.Estoque;
import br.com.restaurante.entities.Produto;

import javax.xml.crypto.Data;
import java.sql.Connection;
import java.util.List;

public class GerenciarEstoque {
    private EstoqueDAOImpl estoqueDAO;

    public GerenciarEstoque() {
        DatabaseConnection databaseConnection = new DatabaseConnection();
        Connection conn = databaseConnection.getConnection();
        this.estoqueDAO = new EstoqueDAOImpl(conn);
    }

    public void adicionarProdutoNoEstoque(Produto produto, int quantidade) {
        System.out.println("Adicionando produto ao estoque...");
        estoqueDAO.adicionarRegistro(produto, quantidade);
        System.out.println("Produto adicionado com sucesso!");
    }

    public Produto adicionarNovoProduto(String nome, String descricao, String categoria) {
        Produto produto = new Produto(nome, descricao, categoria);
        estoqueDAO.adicionarProduto(produto);
        System.out.println("Novo produto cadastrado com sucesso: " + produto.getNome());
        return produto;
    }

    public Estoque buscarEstoquePorId(int id) {
        Estoque estoque = estoqueDAO.buscarPorId(id);
        if (estoque != null) {
            System.out.println("Estoque encontrado: " + estoque.getProduto().getNome() + ", Quantidade: " + estoque.getQuantidade());
        } else {
            System.out.println("Estoque não encontrado para o ID: " + id);
        }
        return estoque;
    }

    public void atualizarQuantidadeEstoque(int id, int novaQuantidade) {
        Estoque estoque = buscarEstoquePorId(id);
        if (estoque != null) {
            estoque.setQuantidade(novaQuantidade);
            estoqueDAO.atualizar(estoque);
            System.out.println("Quantidade atualizada com sucesso!");
        }
    }

    public void removerEstoque(int id) {
        System.out.println("Removendo estoque com ID: " + id);
        estoqueDAO.deletar(id);
        System.out.println("Estoque removido com sucesso!");
    }

    public void listarTodosOsProdutos() {
        List<Estoque> estoques = estoqueDAO.buscarTodos();
        if (estoques.isEmpty()) {
            System.out.println("Nenhum produto encontrado no estoque.");
        } else {
            System.out.println("Lista de produtos em estoque:");
            for (Estoque estoque : estoques) {
                System.out.println("ID: " + estoque.getId() +
                        " | Produto: " + estoque.getProduto().getNome() +
                        " | Quantidade: " + estoque.getQuantidade());
            }
        }
    }
}
