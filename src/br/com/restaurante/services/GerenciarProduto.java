package br.com.restaurante.services;

import br.com.restaurante.DAO.ProdutoDAOImpl;
import br.com.restaurante.database.DatabaseConnection;
import br.com.restaurante.entities.Produto;
import br.com.restaurante.exceptions.ProdutoException;

import java.sql.Connection;
import java.util.List;

public class GerenciarProduto {

    private ProdutoDAOImpl produtoDAO;

    public GerenciarProduto() {
        DatabaseConnection databaseConnection = new DatabaseConnection();
        Connection conn = databaseConnection.getConnection();
        this.produtoDAO = new ProdutoDAOImpl(conn);
    }

    public Produto adicionarNovoProduto(String nome, String descricao, String categoria) {
        if (produtoDAO.existeProdutoComNome(nome)) {
            throw new ProdutoException("Já existe um produto com o nome: " + nome);
        }

        Produto produto = new Produto(nome, descricao, categoria);
        produtoDAO.adicionarProduto(produto);
        System.out.println("Novo produto cadastrado com sucesso: " + produto.getNome());
        return produto;
    }

    public void editarProduto(int id, String nome, String descricao, String categoria) {
        Produto produto = new Produto(id, nome, descricao, categoria);

        if (!produtoDAO.existeProdutoComNome(nome)) {
            produtoDAO.editarProduto(produto);
            System.out.println("Produto com ID " + id + " atualizado com sucesso!");
        } else {
            throw new ProdutoException("Já existe um produto com o nome: " + nome);
        }
    }

    public void removerProduto(int produtoId) {
        System.out.println("Removendo produto com ID: " + produtoId);
        produtoDAO.removerProduto(produtoId);
        System.out.println("Produto removido com sucesso!");
    }

    public boolean verificarProdutoExistente(String nome) {
        return produtoDAO.existeProdutoComNome(nome);
    }

    public void listarProdutos() {
        List<Produto> produtos = produtoDAO.listarTodosProdutos();
        if (produtos.isEmpty()) {
            System.out.println("Nenhum produto encontrado.");
        } else {
            System.out.println("Produtos cadastrados:");
            for (Produto produto : produtos) {
                System.out.println("ID: " + produto.getId() + " | Nome: " + produto.getNome() + " | Categoria: " + produto.getCategoria());
            }
        }
    }
}
