package com.example.projetomvc.model;

// classe responsável por armazenar os dados que depois irão para o adapter
public class ItemDoCarrinho {

    private long id;
    private String nome;
    private int quantidadeSelecionada;
    private long idProduto;
    private double precoProduto;
    private double precoDoItemDaVenda; // precoUnitario = quantidadeSelecionada * precoProduto;

    public long getIdProduto() {
        return idProduto;
    }

    public void setIdProduto(long idProduto) {
        this.idProduto = idProduto;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public int getQuantidadeSelecionada() {
        return quantidadeSelecionada;
    }

    public void setQuantidadeSelecionada(int quantidadeSelecionada) {
        this.quantidadeSelecionada = quantidadeSelecionada;
    }

    public double getPrecoProduto() {
        return precoProduto;
    }

    public void setPrecoProduto(double precoProduto) {
        this.precoProduto = precoProduto;
    }

    public double getprecoDoItemDaVenda() {
        return precoDoItemDaVenda;
    }

    public void setprecoDoItemDaVenda(double precoDoItemDaVenda) {
        this.precoDoItemDaVenda = precoDoItemDaVenda;
    }

    @Override
    public String toString() {
        return "ItemDoCarrinho{" +
                "id=" + id +
                ", nome='" + nome + '\'' +
                ", quantidadeSelecionada=" + quantidadeSelecionada +
                ", idProduto=" + idProduto +
                ", precoProduto=" + precoProduto +
                ", precoDoItemDaVenda=" + precoDoItemDaVenda +
                '}';
    }
}

