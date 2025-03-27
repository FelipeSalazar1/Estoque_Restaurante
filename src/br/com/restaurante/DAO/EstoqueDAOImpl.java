package br.com.restaurante.DAO;

import br.com.restaurante.entities.Estoque;
import br.com.restaurante.entities.Produto;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.util.List;

public class EstoqueDAOImpl implements EstoqueDAO {
    private Connection conn;

    public EstoqueDAOImpl(Connection conn) {
        this.conn = conn;
    }

    @Override
    public void adicionarProduto(Estoque estoque) {
        String sqlInsert = "INSERT INTO estoque (id_produto, quantidade) VALUES (?, ?)";
        try(PreparedStatement ps = conn.prepareStatement(sqlInsert)) {
            ps.setInt(1, estoque.getProduto().getId());
            ps.setInt(2, estoque.getQuantidade());
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    public Estoque buscarPorId(int id) {
        return null;
    }

    @Override
    public void atualizar(Estoque estoque) {

    }

    @Override
    public void deletar(int id) {

    }

    @Override
    public List<Produto> buscarTodos() {
        return List.of();
    }
}
