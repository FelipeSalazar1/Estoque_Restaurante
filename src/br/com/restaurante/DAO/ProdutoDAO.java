package br.com.restaurante.DAO;

import br.com.restaurante.entities.Produto;

import java.util.List;

public interface ProdutoDAO {
    void adicionarProduto(Produto produto);
    void editarProduto(Produto produto);
    void removerProduto(int produtoId);
    Produto buscarProdutoPorId(int id);
    List<Produto> listarTodosProdutos();
}
