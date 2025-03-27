import br.com.restaurante.entities.Estoque;
import br.com.restaurante.entities.Produto;

//TIP To <b>Run</b> code, press <shortcut actionId="Run"/> or
// click the <icon src="AllIcons.Actions.Execute"/> icon in the gutter.
public class Main {
    public static void main(String[] args) {
        Produto produto = new Produto("Coca-Cola", "Refrigerante de cola", "Bebida");
        Estoque estoque = new Estoque(produto, 10);
        Estoque estoque1 = new Estoque(produto, 10);
    }
}