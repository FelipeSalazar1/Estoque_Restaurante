import br.com.restaurante.DAO.EstoqueDAOImpl;
import br.com.restaurante.database.DatabaseConnection;
import br.com.restaurante.entities.Estoque;
import br.com.restaurante.entities.Produto;
import br.com.restaurante.services.GerenciarEstoque;


public class Main {
    public static void main(String[] args) {
        GerenciarEstoque gerenciarEstoque = new GerenciarEstoque();

        gerenciarEstoque.adicionarNovoProduto("Pizza Margherita", "Pizza tradicional italiana", "Comida");

        Produto produto = new Produto(1, "Pizza Margherita", "Pizza tradicional italiana", "Comida");
        gerenciarEstoque.adicionarProdutoNoEstoque(produto, 10);

        gerenciarEstoque.atualizarQuantidadeEstoque(1, 15);

        gerenciarEstoque.listarTodosOsProdutos();

        gerenciarEstoque.removerEstoque(1);
    }
}
