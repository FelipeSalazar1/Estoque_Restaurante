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
    public void adicionarProduto(Produto produto) {
        String sqlInsert = "INSERT INTO produto (nome, descricao, categoria) VALUES (?, ?, ?)";
        try (PreparedStatement ps = conn.prepareStatement(sqlInsert, new String[] {"ID"})) {
            ps.setString(1, produto.getNome());
            ps.setString(2, produto.getDescricao());
            ps.setString(3, produto.getCategoria());
            ps.executeUpdate();

            try (ResultSet rs = ps.getGeneratedKeys()) {
                if (rs.next()) {
                    int idGerado = rs.getInt(1);  // Agora o ID é gerado corretamente
                    produto.setId(idGerado);
                    System.out.println("Produto inserido com ID: " + idGerado);
                }
            }
        }catch (SQLException e) {
            throw new EstoqueException("Erro ao adicionar produto", e);
        }
    }

    @Override
    public Estoque buscarPorId(int id) {
        String sql = "SELECT e.id, e.quantidade, p.id AS produto_id, p.nome, p.descricao, p.categoria " +
                "FROM estoque e INNER JOIN produto p ON e.id_produto = p.id WHERE e.id = ?";
        try (PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setInt(1, id);
            try (ResultSet rs = ps.executeQuery()) {
                if (rs.next()) {
                    Produto produto = new Produto(
                            rs.getInt("produto_id"),
                            rs.getString("nome"),
                            rs.getString("descricao"),
                            rs.getString("categoria")
                    );
                    return new Estoque(rs.getInt("id"), produto, rs.getInt("quantidade"));
                }
            }
        } catch (SQLException e) {
            throw new EstoqueException("Erro ao buscar estoque por ID", e);
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
        String sql = "SELECT e.id, e.quantidade, p.id AS produto_id, p.nome, p.descricao, p.categoria " +
                "FROM estoque e INNER JOIN produto p ON e.id_produto = p.id";
        try (PreparedStatement ps = conn.prepareStatement(sql);
             ResultSet rs = ps.executeQuery()) {
            while (rs.next()) {
                Produto produto = new Produto(
                        rs.getInt("produto_id"),
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
