package br.com.restaurante.exceptions;

import java.sql.SQLException;

public class EstoqueException extends RuntimeException {
    public EstoqueException(String message, SQLException e) {
        super(message);
    }
}
