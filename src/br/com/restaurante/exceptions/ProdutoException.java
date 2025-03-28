package br.com.restaurante.exceptions;

public class ProdutoException extends RuntimeException {
    public ProdutoException(String message, Throwable cause) {
        super(message, cause);
    }

    public ProdutoException(String message) {
        super(message);
    }
}
