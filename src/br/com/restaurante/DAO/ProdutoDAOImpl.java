package br.com.restaurante.DAO;

import br.com.restaurante.entities.Produto;
import br.com.restaurante.exceptions.ProdutoException;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class ProdutoDAOImpl implements ProdutoDAO {

    private final Connection conn;

    public ProdutoDAOImpl(Connection conn) {
        this.conn = conn;
    }


    public boolean existeProdutoComNome(String nome) {
        String sql = "SELECT * FROM produto WHERE nome = ?";
        try (PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setString(1, nome);
            try (ResultSet rs = ps.executeQuery()) {
                if (rs.next()) {
                    return rs.getInt(1) > 0;
                }
            }
        } catch (SQLException e) {
            throw new ProdutoException("Erro ao verificar produto pelo nome", e);
        }
        return false;
    }

    @Override
    public void adicionarProduto(Produto produto) {
        if (existeProdutoComNome(produto.getNome())) {
            throw new ProdutoException("Já existe um produto com o nome: " + produto.getNome());
        }

        String sqlInsert = "INSERT INTO produto (nome, descricao, categoria) VALUES (?, ?, ?)";
        try (PreparedStatement ps = conn.prepareStatement(sqlInsert, new String[]{"ID"})) {
            ps.setString(1, produto.getNome());
            ps.setString(2, produto.getDescricao());
            ps.setString(3, produto.getCategoria());
            ps.executeUpdate();

            try (ResultSet rs = ps.getGeneratedKeys()) {
                if (rs.next()) {
                    int idGerado = rs.getInt(1);
                    produto.setId(idGerado);
                    System.out.println("Produto inserido com ID: " + idGerado);
                }
            }
        } catch (SQLException e) {
            throw new ProdutoException("Erro ao adicionar produto", e);
        }
    }

    @Override
    public void editarProduto(Produto produto) {
        String sqlUpdate = "UPDATE produto SET nome = ?, descricao = ?, categoria = ? WHERE id = ?";
        try (PreparedStatement ps = conn.prepareStatement(sqlUpdate)) {
            ps.setString(1, produto.getNome());
            ps.setString(2, produto.getDescricao());
            ps.setString(3, produto.getCategoria());
            ps.setInt(4, produto.getId());
            ps.executeUpdate();
            System.out.println("Produto atualizado com sucesso!");
        } catch (SQLException e) {
            throw new ProdutoException("Erro ao editar produto", e);
        }
    }

    @Override
    public void removerProduto(int produtoId) {
        String sqlDeleteEstoque = "DELETE FROM estoque WHERE id_produto = ?";
        try (PreparedStatement psEstoque = conn.prepareStatement(sqlDeleteEstoque)) {
            psEstoque.setInt(1, produtoId);
            psEstoque.executeUpdate();

            String sqlDeleteProduto = "DELETE FROM produto WHERE id = ?";
            try (PreparedStatement psProduto = conn.prepareStatement(sqlDeleteProduto)) {
                psProduto.setInt(1, produtoId);
                psProduto.executeUpdate();
            }
        } catch (SQLException e) {
            throw new ProdutoException("Erro ao remover produto e estoque", e);
        }
    }

    @Override
    public Produto buscarProdutoPorId(int id) {
        String sql = "SELECT id, nome, descricao, categoria FROM produto WHERE id = ?";
        try (PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setInt(1, id);
            try (ResultSet rs = ps.executeQuery()) {
                if (rs.next()) {
                    return new Produto(
                            rs.getInt("id"),
                            rs.getString("nome"),
                            rs.getString("descricao"),
                            rs.getString("categoria")
                    );
                }
            }
        } catch (SQLException e) {
            throw new ProdutoException("Erro ao buscar produto por ID", e);
        }
        return null;
    }

    @Override
    public List<Produto> listarTodosProdutos() {
        List<Produto> produtos = new ArrayList<>();
        String sql = "SELECT id, nome, descricao, categoria FROM produto";

        try (PreparedStatement ps = conn.prepareStatement(sql);
             ResultSet rs = ps.executeQuery()) {
            while (rs.next()) {
                Produto produto = new Produto(
                        rs.getInt("id"),
                        rs.getString("nome"),
                        rs.getString("descricao"),
                        rs.getString("categoria")
                );
                produtos.add(produto);
            }
        } catch (SQLException e) {
            throw new ProdutoException("Erro ao buscar todos os produtos", e);
        }

        return produtos;
    }
}
