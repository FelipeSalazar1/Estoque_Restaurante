import br.com.restaurante.entities.Produto;
import br.com.restaurante.entities.Estoque;
import br.com.restaurante.services.GerenciarEstoque;
import br.com.restaurante.services.GerenciarProduto;
import br.com.restaurante.DAO.ProdutoDAOImpl;
import br.com.restaurante.DAO.EstoqueDAOImpl;
import br.com.restaurante.database.DatabaseConnection;

import java.sql.Connection;

public class Main {
    public static void main(String[] args) {
        GerenciarProduto gerenciarProduto = new GerenciarProduto();
        GerenciarEstoque gerenciarEstoque = new GerenciarEstoque();

        Produto produto1 = gerenciarProduto.adicionarNovoProduto("Produto1", "Descrição do Produto1", "Categoria1");

        Produto produto2 = gerenciarProduto.adicionarNovoProduto("Produto2", "Descrição do Produto2", "Categoria2");

        gerenciarProduto.editarProduto(produto1.getId(), "Produto1 Editado", "Descrição do Produto1 Editado", "Categoria1 Editada");

        System.out.println("\nLista de todos os produtos:");
        gerenciarProduto.listarProdutos();

        System.out.println("\nRemover Produto:");
        gerenciarProduto.removerProduto(produto1.getId());

        System.out.println("\nNovo Produto:");
        Produto produto3 = gerenciarProduto.adicionarNovoProduto("Produto3", "Descrição do Produto1", "Categoria1");

        System.out.println("\nAdicionando ao estoque:");
        gerenciarEstoque.adicionarProdutoNoEstoque(produto2, 100);
        gerenciarEstoque.adicionarProdutoNoEstoque(produto3, 50);

        System.out.println("\nAtualização no estoque geral:");
        gerenciarEstoque.atualizarQuantidadeEstoque(produto3.getId(), 150);

        gerenciarEstoque.atualizarQuantidadeEstoque(produto3.getId(), 200);

        gerenciarEstoque.buscarEstoquePorId(produto3.getId());

        System.out.println("\nLista do estoque geral:");
        gerenciarEstoque.listarTodosOsProdutos();

        System.out.println("\nRemoção do Produto e do Estoque:");
        //gerenciarProduto.removerProduto(produto3.getId());
    }
}
