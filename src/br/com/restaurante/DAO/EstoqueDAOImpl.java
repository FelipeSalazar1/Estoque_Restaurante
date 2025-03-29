package br.com.restaurante.DAO;

import br.com.restaurante.entities.Estoque;
import br.com.restaurante.entities.Produto;
import br.com.restaurante.exceptions.EstoqueException;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class EstoqueDAOImpl implements EstoqueDAO {
    private final Connection conn;

    public EstoqueDAOImpl(Connection conn) {
        this.conn = conn;
    }

    @Override
    public void adicionarRegistro(Produto produto, int quantidade) {

        String verificaProdutoSQL = "SELECT COUNT(*) FROM produto WHERE id = ?";
        try (PreparedStatement ps = conn.prepareStatement(verificaProdutoSQL)) {
            ps.setInt(1, produto.getId());
            try (ResultSet rs = ps.executeQuery()) {
                if (rs.next() && rs.getInt(1) == 0) {
                    throw new EstoqueException("Produto com ID " + produto.getId() + " não existe na tabela produto.");
                }
            }
        } catch (SQLException e) {
            throw new EstoqueException("Erro ao verificar produto no estoque", e);
        }

        String sqlInsert = "INSERT INTO estoque (id_produto, quantidade) VALUES (?, ?)";
        try (PreparedStatement ps = conn.prepareStatement(sqlInsert)) {
            ps.setInt(1, produto.getId());
            ps.setInt(2, quantidade);
            ps.executeUpdate();
        } catch (SQLException e) {
            throw new EstoqueException("Erro ao adicionar registro no estoque", e);
        }
    }




    @Override
    public Estoque buscarPorId(int id) {
        String sql = "SELECT e.id, e.quantidade, p.id AS id_produto, p.nome, p.descricao, p.categoria " +
                "FROM estoque e INNER JOIN produto p ON e.id_produto = p.id WHERE p.id = ?";

        try (PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setInt(1, id);  // Agora o id é do produto, e não do estoque
            try (ResultSet rs = ps.executeQuery()) {
                if (rs.next()) {
                    Produto produto = new Produto(
                            rs.getInt("id_produto"),
                            rs.getString("nome"),
                            rs.getString("descricao"),
                            rs.getString("categoria")
                    );
                    return new Estoque(rs.getInt("id"), produto, rs.getInt("quantidade"));
                }
            }
        } catch (SQLException e) {
            throw new EstoqueException("Erro ao buscar estoque por ID do produto", e);
        }
        return null;
    }

    @Override
    public void atualizar(Estoque estoque, int quantidade) {
        String sqlUpdate = "UPDATE estoque SET quantidade = ? WHERE id = ?";
        try (PreparedStatement ps = conn.prepareStatement(sqlUpdate)) {
            ps.setInt(1, quantidade);
            ps.setInt(2, estoque.getId());
            ps.executeUpdate();
        } catch (SQLException e) {
            throw new EstoqueException("Erro ao atualizar estoque", e);
        }
    }

    @Override
    public void deletar(int id) {
        String sqlDelete = "DELETE FROM estoque WHERE id = ?";
        try (PreparedStatement ps = conn.prepareStatement(sqlDelete)) {
            ps.setInt(1, id);
            ps.executeUpdate();
        } catch (SQLException e) {
            throw new EstoqueException("Erro ao deletar estoque", e);
        }
    }

    @Override
    public List<Estoque> buscarTodos() {
        List<Estoque> estoques = new ArrayList<>();
        String sql = "SELECT e.id, e.quantidade, p.id AS id_produto, p.nome, p.descricao, p.categoria " +
                "FROM estoque e INNER JOIN produto p ON e.id_produto = p.id";

        try (PreparedStatement ps = conn.prepareStatement(sql);
             ResultSet rs = ps.executeQuery()) {
            while (rs.next()) {
                Produto produto = new Produto(
                        rs.getInt("id_produto"),
                        rs.getString("nome"),
                        rs.getString("descricao"),
                        rs.getString("categoria")
                );
                Estoque estoque = new Estoque(rs.getInt("id"), produto, rs.getInt("quantidade"));
                estoques.add(estoque);
            }

        } catch (SQLException e) {
            throw new EstoqueException("Erro ao buscar todos os registros de estoque", e);
        }

        return estoques;
    }
}
