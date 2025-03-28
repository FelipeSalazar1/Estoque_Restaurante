import br.com.restaurante.DAO.EstoqueDAOImpl;
import br.com.restaurante.database.DatabaseConnection;
import br.com.restaurante.entities.Estoque;
import br.com.restaurante.entities.Produto;
import br.com.restaurante.services.GerenciarEstoque;


public class Main {
    public static void main(String[] args) {
        GerenciarEstoque gerenciarEstoque = new GerenciarEstoque();

        Produto produto = gerenciarEstoque.adicionarNovoProduto("Pizza Portuguesa", "Pizzaaaaaa", "Comidaaaaaa");

        gerenciarEstoque.adicionarProdutoNoEstoque(produto, 10);

        gerenciarEstoque.atualizarQuantidadeEstoque(6, 15);

        gerenciarEstoque.removerEstoque(5);

        gerenciarEstoque.listarTodosOsProdutos();
    }
}
