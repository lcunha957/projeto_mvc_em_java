package com.example.projetomvc.model;

import java.util.Date;
import java.util.List;

public class Venda {

    private long id;
    private Date dataDaVenda;
    private String nomeDoCliente; // tarefa pra casa, colocar o nome do cliente e salvar no banco
    private List<ItemDoCarrinho> itensDaVenda;
    private double totalVenda;
    private int qtdeItens;

    public Venda() {

    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public Date getDataDaVenda() {
        return dataDaVenda;
    }

    public void setDataDaVenda(Date dataDaVenda) {
        this.dataDaVenda = dataDaVenda;
    }

    public String getNomeDoCliente() {
        return nomeDoCliente;
    }

    public void setNomeDoCliente(String nomeDoCliente) {
        this.nomeDoCliente = nomeDoCliente;
    }

    public List<ItemDoCarrinho> getItensDaVenda() {
        return itensDaVenda;
    }

    public void setItensDaVenda(List<ItemDoCarrinho> itensDaVenda) {
        this.itensDaVenda = itensDaVenda;
    }

    public double getTotalVenda() {
        return totalVenda;
    }

    public void setTotalVenda(double totalVenda) {
        this.totalVenda = totalVenda;
    }

    public int getQtdeItens() {
        return qtdeItens;
    }

    public void setQtdeItens(int qtdeItens) {
        this.qtdeItens = qtdeItens;
    }

    @Override
    public String toString() {
        return "Venda{" +
                "id=" + id +
                ", dataDaVenda=" + dataDaVenda +
                ", nomeDoCliente='" + nomeDoCliente + '\'' +
                ", itensDaVenda=" + itensDaVenda +
                ", totalVenda=" + totalVenda +
                ", qtdeItens=" + qtdeItens +
                '}';
    }
}
