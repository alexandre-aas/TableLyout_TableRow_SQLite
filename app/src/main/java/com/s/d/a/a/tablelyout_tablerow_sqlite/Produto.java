package com.s.d.a.a.tablelyout_tablerow_sqlite;

public class Produto {
    private int idProduto;
    private String nomeProduto;
    private int qtde;

    public Produto() {

    }

    public Produto(int idProduto, String nomeProduto, int quantidade) {
        this.idProduto = idProduto;
        this.nomeProduto = nomeProduto;
        this.qtde = quantidade;
    }

    public Produto(String nomeProduto, int quantidade) {
        this.nomeProduto = nomeProduto;
        this.qtde = quantidade;
    }

    public void setIDProduto(int id) {
        this.idProduto = id;
    }

    public int getIDProduto() {
        return this.idProduto;
    }

    public void setNomeProduto(String nomeProduto) {
        this.nomeProduto = nomeProduto;
    }

    public String getNomeProduto() {
        return this.nomeProduto;
    }

    public void setQuantidade(int quantidade) {
        this.qtde = quantidade;
    }

    public int getQuantidade() {
        return this.qtde;
    }
}
