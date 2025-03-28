package br.com.restaurante.DAO;

import br.com.restaurante.entities.Estoque;
import br.com.restaurante.entities.Produto;

import java.util.List;

public interface EstoqueDAO {
    void adicionarRegistro(Produto produto, int quantidade);
    Estoque buscarPorId(int id);
    void atualizar(Estoque estoque, int quantidade);
    void deletar(int id);
    List<Estoque> buscarTodos();
}
