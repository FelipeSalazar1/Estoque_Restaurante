package br.com.restaurante.exceptions;

public class EstoqueException extends RuntimeException {

    // Construtor que aceita apenas a mensagem
    public EstoqueException(String message) {
        super(message);
    }

    // Construtor que aceita mensagem e a causa (exceção original)
    public EstoqueException(String message, Throwable cause) {
        super(message, cause);
    }
}
