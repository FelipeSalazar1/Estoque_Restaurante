package br.com.restaurante.entities;

public class Produto {
    private int id;
    private String nome;
    private String descricao;
    private String categoria;

    // Construtor com ID
    public Produto(int id, String nome, String descricao, String categoria) {
        this.id = id;
        this.nome = nome;
        this.descricao = descricao;
        this.categoria = categoria;
    }

    // Construtor sem ID (para novos produtos)
    public Produto(String nome, String descricao, String categoria) {
        this.nome = nome;
        this.descricao = descricao;
        this.categoria = categoria;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) { // Adicionado setter para permitir atualização do ID
        this.id = id;
    }

    public String getNome() {
        return nome;
    }

    public String getDescricao() {
        return descricao;
    }

    public String getCategoria() {
        return categoria;
    }
}
