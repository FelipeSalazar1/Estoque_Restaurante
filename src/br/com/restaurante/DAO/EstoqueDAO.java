package br.com.restaurante.DAO;

import br.com.restaurante.entities.Estoque;
import br.com.restaurante.entities.Produto;

import java.util.List;

public interface EstoqueDAO {
    void adicionarProduto(Estoque estoque);
    Estoque buscarPorId(int id);
    void atualizar(Estoque estoque);
    void deletar(int id);
    List<Produto> buscarTodos();
}
